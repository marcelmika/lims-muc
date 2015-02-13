/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.group;

import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.domain.GroupCollection;
import org.jivesoftware.smack.Roster;

import java.util.List;
import java.util.Set;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:09 PM
 */
public interface GroupManager {

    /**
     * Sets roster to group manager.
     *
     * @param roster Roster
     */
    public void setRoster(Roster roster);

    /**
     * Sets company id to the group manager
     *
     * @param companyId Long
     */
    public void setCompanyId(Long companyId);

    /**
     * Returns true if the roster has been loaded
     *
     * @return boolean
     */
    public boolean isRosterLoaded();

    /**
     * Reloads user's roster
     */
    public void loadRoster();

    /**
     * Get buddy's collection of groups.
     *
     * @return Buddy's collection of groups.
     */
    public GroupCollection getGroupCollection();

    /**
     * Search buddies from all groups based on the search query
     *
     * @param searchQuery String
     * @param size        size of the result
     * @return list of found buddies
     */
    public List<Buddy> searchBuddies(String searchQuery, Integer size);

    /**
     * Reads presences for the given buddies
     *
     * @param buddyIds set of buddy ids
     * @return list of buddies with presences
     */
    public List<Buddy> readPresences(Set<Long> buddyIds);

    /**
     * Destroys groups manager
     */
    public void destroy();

}
