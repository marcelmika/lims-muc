/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.request.parameters;

import com.marcelmika.lims.portal.domain.ConversationPagination;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/10/14
 * Time: 14:55
 */
public class ReadConversationsParameters {

    private ConversationPagination pagination;
    private Integer etag;

    public ConversationPagination getPagination() {
        // Don't let the pagination be null
        if (pagination == null) {
            pagination = new ConversationPagination();
        }
        return pagination;
    }

    public void setPagination(ConversationPagination pagination) {
        this.pagination = pagination;
    }

    public Integer getEtag() {
        return etag;
    }

    public void setEtag(Integer etag) {
        this.etag = etag;
    }

    @Override
    public String toString() {
        return "ReadConversationsParameters{" +
                "pagination=" + pagination +
                ", etag=" + etag +
                '}';
    }
}
