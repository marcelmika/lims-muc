/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.entity;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 7:52 PM
 */
public class MessagePaginationDetails {

    private Boolean readMore;
    private Long stopperId;

    public Boolean getReadMore() {
        return readMore;
    }

    public void setReadMore(Boolean readMore) {
        this.readMore = readMore;
    }

    public Long getStopperId() {
        return stopperId;
    }

    public void setStopperId(Long stopperId) {
        this.stopperId = stopperId;
    }

    @Override
    public String toString() {
        return "MessagePaginationDetails{" +
                "readMore=" + readMore +
                ", stopperId=" + stopperId +
                '}';
    }
}
