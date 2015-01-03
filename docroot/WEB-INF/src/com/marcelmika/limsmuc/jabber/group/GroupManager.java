/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.group;

import com.marcelmika.limsmuc.jabber.domain.GroupCollection;
import org.jivesoftware.smack.Roster;

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
     * Get buddy's collection of groups.
     *
     * @return Buddy's collection of groups.
     */
    public GroupCollection getGroupCollection();

    /**
     * Destroys groups manager
     */
    public void destroy();

}
