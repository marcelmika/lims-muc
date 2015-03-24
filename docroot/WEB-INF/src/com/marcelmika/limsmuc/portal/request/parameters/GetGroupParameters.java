/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.request.parameters;

import com.marcelmika.limsmuc.api.environment.Environment;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/03/15
 * Time: 16:25
 */
public class GetGroupParameters {

    private Long groupId;
    private Environment.BuddyListStrategy listStrategy;
    private Integer number;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Environment.BuddyListStrategy getListStrategy() {
        return listStrategy;
    }

    public void setListStrategy(Environment.BuddyListStrategy listStrategy) {
        this.listStrategy = listStrategy;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "GetGroupParameters{" +
                "listStrategy=" + listStrategy +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
