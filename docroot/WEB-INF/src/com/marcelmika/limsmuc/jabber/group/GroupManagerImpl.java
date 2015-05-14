/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.group;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.domain.Group;
import com.marcelmika.limsmuc.jabber.domain.GroupCollection;
import com.marcelmika.limsmuc.jabber.domain.Page;
import com.marcelmika.limsmuc.jabber.domain.Presence;
import com.marcelmika.limsmuc.jabber.exception.JabberException;
import com.marcelmika.limsmuc.jabber.utils.Jid;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Group manager is responsible for the synchronization of groups and their entries. It keeps
 * fresh copy of Group Collection related to the user.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:13 PM
 */
public class GroupManagerImpl implements GroupManager, RosterListener {

    // Collection of groups related to the manager
    private GroupCollection groupCollection = new GroupCollection();
    // Represents a user's roster, which is the collection of users a person
    // receives presence updates for.
    private Roster roster;
    // We need the company id otherwise we cannot find users in liferay
    private Long companyId;
    // Flag that describes if the groups were modified. Default is true because the groups are always
    // modified at the beginning
    private boolean wasModified = true;
    // Roaster is lazy loaded. So we need to set a flag that will show us if the roaster was already reloaded.
    private boolean rosterReloaded = false;

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(GroupManagerImpl.class);

    /**
     * Constructor
     */
    public GroupManagerImpl() {
        // This is a jabber list strategy group collection
        groupCollection.setListStrategy(Environment.BuddyListStrategy.JABBER);
        // Since the roaster is lazy loaded, set the flag to true.
        // It will be set to false whenever the roster is loaded
        groupCollection.setLoading(true);
    }


    // -------------------------------------------------------------------------------------------
    // Override: GroupManager
    // -------------------------------------------------------------------------------------------

    /**
     * Sets roster to group manager.
     *
     * @param roster Roster
     */
    @Override
    public void setRoster(Roster roster) {
        this.roster = roster;
        // Set Roster's listener so we will be notified if
        // anything happens with buddies in groups
        roster.addRosterListener(this);
    }

    /**
     * Sets company id to the group manager
     *
     * @param companyId Long
     */
    @Override
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Returns true if the roster has been loaded
     *
     * @return boolean
     */
    @Override
    public boolean isRosterLoaded() {
        return rosterReloaded;
    }

    /**
     * Loads user's roster
     */
    @Override
    public void loadRoster() {
        reloadRoster();
    }

    /**
     * Get buddy's collection of groups.
     *
     * @param page Page
     * @return Buddy's collection of groups.
     */
    @Override
    public GroupCollection getGroupCollection(Page page) {

        // Lazy load the roaster
        if (!rosterReloaded) {
            reloadRoster();
        }

        // Map groups only if they were somehow modified
        if (wasModified) {
            mapGroupsFromRoster();
        }

        // Return a duplicate of the collection with the size of buddies based on the page
        return groupCollection.duplicate(page);
    }

    /**
     * Returns a particular group
     *
     * @param groupId      Long
     * @param listStrategy BuddyListStrategy
     * @param page         Page
     * @return list of groups
     */
    @Override
    public Group getGroup(Long groupId, Environment.BuddyListStrategy listStrategy, Page page) throws JabberException {

        // Unknown list strategy
        if (listStrategy != Environment.BuddyListStrategy.JABBER) {
            throw new JabberException("Unknown list strategy: " + listStrategy);
        }

        // Unknown group
        if (groupCollection.getGroups().size() < groupId) {
            throw new JabberException("Group with id: " + groupId + " is not presented");
        }

        // Lazy load the roaster
        if (!rosterReloaded) {
            reloadRoster();
        }

        // Map groups only if they were somehow modified
        if (wasModified) {
            mapGroupsFromRoster();
        }

        // Id of the group is a position in the list
        Group group = groupCollection.getGroups().get(groupId.intValue());

        // Return a duplicate of the group
        return group.duplicate(page);
    }

    /**
     * Search buddies from all groups based on the search query
     *
     * @param searchQuery String
     * @param size        size of the result
     * @return list of found buddies
     */
    @Override
    public List<Buddy> searchBuddies(String searchQuery, Integer size) {

        // Lazy load the roaster
        if (!rosterReloaded) {
            reloadRoster();
        }

        List<Buddy> buddies = new LinkedList<Buddy>();

        for (RosterEntry rosterEntry : roster.getEntries()) {

            // Map the user
            Buddy buddy = mapFromRosterEntry(rosterEntry);

            // If no such buddy exist in Liferay ignore the entry
            if (buddy == null) {
                continue;
            }

            // Search based on the full name
            String fullName = buddy.getFullName().toLowerCase();
            // Check if the name matches search query
            boolean matches = fullName.matches(String.format(".*%s.*", searchQuery.toLowerCase()));

            if (matches) {

                // Add buddy to result
                buddies.add(buddy);

                // Stop the search if we already excited the limit
                if (buddies.size() >= size) {
                    break;
                }
            }
        }

        return buddies;
    }

    /**
     * Reads presences for the given buddies
     *
     * @param buddyIds set of buddy ids
     * @return list of buddies with presences
     */
    @Override
    public List<Buddy> readPresences(Set<Long> buddyIds) {
        // Lazy load the roaster
        if (!rosterReloaded) {
            reloadRoster();
        }

        List<Buddy> buddies = new LinkedList<Buddy>();

        // Fetch presence for all buddies
        for (Long buddyId : buddyIds) {

            // Create buddy
            Buddy buddy = new Buddy();
            buddy.setBuddyId(buddyId);
            buddy.setPresence(Presence.STATE_UNRECOGNIZED); // Unrecognized by default

            try {
                // Fetch user based on the id
                User user = UserLocalServiceUtil.fetchUser(buddyId);

                // Do nothing if the user wasn't found
                if (user != null) {
                    // We need a JID to be able to search the roster
                    String jid = Jid.getJid(user.getScreenName());

                    // Check if such user was found in the roster
                    if (roster.contains(jid)) {
                        // Map the presence
                        Presence presence = Presence.fromSmackPresence(roster.getPresence(jid));
                        // Add presence to buddy
                        buddy.setPresence(presence);
                    }
                }
            }
            // Failure
            catch (SystemException e) {
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(e);
                }
            }

            // Add to list
            buddies.add(buddy);
        }

        return buddies;
    }

    /**
     * Destroys groups manager
     */
    @Override
    public void destroy() {
        roster.removeRosterListener(this);
    }

    // -------------------------------------------------------------------------------------------
    // Override: RosterListener
    // -------------------------------------------------------------------------------------------

    @Override
    public void entriesAdded(Collection<String> strings) {
        // Set was modified group to true. Thanks to that the groups will be mapped again whenever the
        // getGroups() method is called
        synchronized (this) {
            wasModified = true;
        }

        // Roaster is loaded
        groupCollection.setLoading(false);

        // Log
        if (log.isDebugEnabled()) {
            log.debug("Jabber entries added");
            log.debug(strings);
        }
    }

    @Override
    public void entriesUpdated(Collection<String> strings) {
        // Set was modified group to true. Thanks to that the groups will be mapped again whenever the
        // getGroups() method is called
        synchronized (this) {
            wasModified = true;
        }

        // Log
        if (log.isDebugEnabled()) {
            log.debug("Jabber entries updated");
            log.debug(strings);
        }
    }

    @Override
    public void entriesDeleted(Collection<String> strings) {
        // Set was modified group to true. Thanks to that the groups will be mapped again whenever the
        // getGroups() method is called
        synchronized (this) {
            wasModified = true;
        }

        // Log
        if (log.isDebugEnabled()) {
            log.debug("Jabber entries deleted");
            log.debug(strings);
        }
    }

    /**
     * Notify all listeners about changes.
     * Called when the presence of a roster entry is changed.
     * Care should be taken when using the presence data
     * delivered as part of this event. Specifically, when
     * a user account is online with multiple resources,
     * the UI should account for that. For example, say a user
     * is online with their desktop computer and mobile phone.
     * If the user logs out of the IM client on their mobile phone,
     * the user should not be shown in the roster (contact list)
     * as offline since they're still available as another resource.
     * To get the current "best presence" for a user after the presence update,
     * query the roster:
     * String user = presence.getFrom();
     * Presence bestPresence = roster.getPresence(user);
     */
    @Override
    public void presenceChanged(org.jivesoftware.smack.packet.Presence presence) {
        // Set was modified group to true. Thanks to that the groups will be mapped again whenever the
        // getGroups() method is called
        synchronized (this) {
            wasModified = true;
        }

        // Log
        if (log.isDebugEnabled()) {
            log.debug("Presence changed");
            log.debug(presence);
        }
    }

    // -------------------------------------------------------------------------------------------
    // Private methods
    // -------------------------------------------------------------------------------------------

    /**
     * Whenever called the method takes roster and maps its groups and entities to our groups and entities
     */
    private void mapGroupsFromRoster() {
        // Create temporary group list
        List<Group> groups = new ArrayList<Group>();

        long groupCounter = 0;

        // Go over all groups in roster
        for (RosterGroup rosterGroup : roster.getGroups()) {
            // Create new Group
            Group group = new Group();
            // Roster group doesn't have an ID so we need to make it by ourselves
            group.setGroupId(groupCounter++);
            group.setName(rosterGroup.getName());

            // Add buddies to Group
            for (RosterEntry entry : rosterGroup.getEntries()) {
                // Map buddy
                Buddy buddy = mapFromRosterEntry(entry);

                // No such buddy was found
                if (buddy == null) {
                    continue; // Ignore the fact
                }

                // Add buddy to the group
                group.addBuddy(buddy);
            }

            // Add Group to the collection
            groups.add(group);
        }

        // Clear global groups
        groupCollection.addGroups(groups);

        // Return modify flag
        wasModified = false;
    }

    /**
     * Maps buddy from roster entry. Returns null if no buddy was found
     *
     * @param entry RosterEntry
     * @return Buddy
     */
    private Buddy mapFromRosterEntry(RosterEntry entry) {
        // Map buddy
        Buddy buddy = Buddy.fromRosterEntry(entry);

        try {

            // Important: This is a leaking of the business logic (calling Liferay util).
            // Unfortunately there is no better place where such call can be added.
            User user = UserLocalServiceUtil.getUserByScreenName(companyId, buddy.getScreenName());

            // No such user was found, thus simply ignore this fact and return null
            if (user == null) {
                return null;
            }

            // Map the user
            buddy = Buddy.fromPortalUser(user);
        }
        // Failure
        catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e);
            }
            // Some error occurred, thus simply ignore this fact and return null
            return null;
        }

        // Map presence
        Presence presence = Presence.fromSmackPresence(roster.getPresence(entry.getUser()));
        buddy.setPresence(presence);

        // Map connection
        boolean isConnected = presence != Presence.STATE_OFFLINE && presence != Presence.STATE_UNRECOGNIZED;
        buddy.setConnected(isConnected);
        // Set the connection timestamp
        if (isConnected) {
            buddy.setConnectedAt(Calendar.getInstance().getTime());
        }

        return buddy;
    }

    /**
     * Reloads roaster. If fails does nothing.
     */
    private void reloadRoster() {
        try {
            // Reload roaster
            this.roster.reload();

            // Set the flag
            rosterReloaded = true;
        }
        // User not logged in
        catch (SmackException.NotLoggedInException e) {
            // Log
            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }
        // User not connected
        catch (SmackException.NotConnectedException e) {
            // Log
            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }
    }
}
