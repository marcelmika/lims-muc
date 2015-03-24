/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.group;

import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/03/15
 * Time: 16:36
 */
public class GetGroupRequestEvent extends RequestEvent {

    private BuddyDetails buddy;
    private Long groupId;
    private Environment.BuddyListStrategy listStrategy;
    private Integer number;

    public GetGroupRequestEvent(final BuddyDetails buddy,
                                final Long groupId,
                                final Environment.BuddyListStrategy listStrategy,
                                final Integer number) {
        this.buddy = buddy;
        this.groupId = groupId;
        this.listStrategy = listStrategy;
        this.number = number;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Environment.BuddyListStrategy getListStrategy() {
        return listStrategy;
    }

    public Integer getNumber() {
        return number;
    }
}
