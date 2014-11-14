/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.domain;

import com.marcelmika.limsmuc.api.entity.MessagePaginationDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 8:15 PM
 */
public class MessagePagination {

    private Boolean readMore;
    private Long stopperId;

    public MessagePaginationDetails toMessagePaginationDetails() {
        MessagePaginationDetails details = new MessagePaginationDetails();

        details.setReadMore(readMore);
        details.setStopperId(stopperId);

        return details;
    }

    public static MessagePagination fromMessagePaginationDetails(MessagePaginationDetails details) {
        MessagePagination messagePagination = new MessagePagination();

        messagePagination.readMore = details.getReadMore();
        messagePagination.stopperId = details.getStopperId();

        return messagePagination;
    }

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
        return "MessagePagination{" +
                "readMore=" + readMore +
                ", stopperId=" + stopperId +
                '}';
    }
}
