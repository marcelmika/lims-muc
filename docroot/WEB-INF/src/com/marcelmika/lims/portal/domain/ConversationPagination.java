/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.entity.ConversationPaginationDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 25/10/14
 * Time: 15:05
 */
public class ConversationPagination {

    private Boolean readMore;
    private Integer pageSize;

    public ConversationPaginationDetails toConversationPaginationDetails() {
        ConversationPaginationDetails details = new ConversationPaginationDetails();

        details.setReadMore(readMore);
        details.setPageSize(pageSize);

        return details;
    }

    public Boolean getReadMore() {
        return readMore;
    }

    public void setReadMore(Boolean readMore) {
        this.readMore = readMore;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "ConversationPagination{" +
                "readMore=" + readMore +
                ", pageSize=" + pageSize +
                '}';
    }
}
