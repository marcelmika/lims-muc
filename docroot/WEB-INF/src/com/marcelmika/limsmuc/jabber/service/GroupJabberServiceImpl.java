/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.buddy.PresenceChangeBusEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupResponseEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.limsmuc.core.bus.BuddyEventBus;
import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.domain.Group;
import com.marcelmika.limsmuc.jabber.domain.GroupCollection;
import com.marcelmika.limsmuc.jabber.domain.Page;
import com.marcelmika.limsmuc.jabber.domain.Presence;
import com.marcelmika.limsmuc.jabber.exception.JabberException;
import com.marcelmika.limsmuc.jabber.group.GroupListener;
import com.marcelmika.limsmuc.jabber.group.GroupManager;
import com.marcelmika.limsmuc.jabber.session.UserSession;
import com.marcelmika.limsmuc.jabber.session.store.UserSessionStore;
import com.marcelmika.limsmuc.jabber.session.store.UserSessionStoreListener;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:22 PM
 */
public class GroupJabberServiceImpl implements GroupJabberService, UserSessionStoreListener, GroupListener {

    // Dependencies
    private UserSessionStore userSessionStore;
    private BuddyEventBus buddyEventBus;

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(GroupJabberServiceImpl.class);

    /**
     * Constructor
     *
     * @param userSessionStore UserSessionStore
     */
    public GroupJabberServiceImpl(final UserSessionStore userSessionStore,
                                  final BuddyEventBus buddyEventBus) {
        this.userSessionStore = userSessionStore;
        this.buddyEventBus = buddyEventBus;
        // Add listeners
        userSessionStore.addUserSessionStoreListener(this);
    }

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event) {
        // Get buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return GetGroupsResponseEvent.failure(
                    GetGroupsResponseEvent.Status.ERROR_NO_SESSION,
                    new JabberException(String.format("No session for user %d found", buddyId))
            );
        }

        // Get groups manager related to buddy
        GroupManager groupManager = userSession.getGroupManager();

        // Create page
        Page page = new Page();
        page.setNumber(0); // We always start from the beginning of the list
        page.setSize(Environment.getBuddyListMaxBuddies());

        // Get a list of groups
        GroupCollection groupCollection = groupManager.getGroupCollection(page);
        // Return success
        return GetGroupsResponseEvent.success(groupCollection.toGroupCollectionDetails());
    }

    /**
     * Returns a particular group
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    @Override
    public GetGroupResponseEvent getGroup(GetGroupRequestEvent event) {
        // Get buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddy());
        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return GetGroupResponseEvent.failure(
                    GetGroupResponseEvent.Status.ERROR_NO_SESSION,
                    new JabberException(String.format("No session for user %d found", buddyId))
            );
        }

        // Get groups manager related to buddy
        GroupManager groupManager = userSession.getGroupManager();

        // Create page
        Page page = new Page();
        page.setNumber(event.getNumber());
        page.setSize(Environment.getBuddyListMaxBuddies());

        try {

            // Get the group
            Group group = groupManager.getGroup(event.getGroupId(), event.getListStrategy(), page);

            // Success
            return GetGroupResponseEvent.success(group.toGroupDetails());

        }
        // Failure
        catch (JabberException e) {
            return GetGroupResponseEvent.failure(
                    GetGroupResponseEvent.Status.ERROR_JABBER, e
            );
        }
    }


    // -------------------------------------------------------------------------------------------
    // User Session Store Listener
    // -------------------------------------------------------------------------------------------

    /**
     * Called whenever the user session is added to the user session store
     *
     * @param userSession UserSession
     */
    @Override
    public void userSessionAdded(UserSession userSession) {
        userSession.getGroupManager().addGroupListener(this);
    }

    /**
     * Called whenever the user session with the particular id is removed from the user session store
     *
     * @param id of the user session
     */
    @Override
    public void userSessionRemoved(Long id) {
        // Do nothing
    }


    // -------------------------------------------------------------------------------------------
    // Group Listener
    // -------------------------------------------------------------------------------------------

    /**
     * Called when the presence changed for the given user
     *
     * @param buddy    Buddy
     * @param presence Presence
     */
    @Override
    public void presenceChanged(Buddy buddy, Presence presence) {
        // Publish the event
        buddyEventBus.publish(
                new PresenceChangeBusEvent(buddy.toBuddyDetails(), presence.toPresenceDetails())
        );
    }
}
