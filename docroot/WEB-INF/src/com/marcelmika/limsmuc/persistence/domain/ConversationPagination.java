/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.domain;

import com.marcelmika.limsmuc.api.entity.ConversationPaginationDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 25/10/14
 * Time: 15:36
 */
public class ConversationPagination {

    private Boolean readMore;
    private Integer pageSize;

    /**
     * Factory method creates conversation pagination from details
     *
     * @param details ConversationPaginationDetails
     * @return ConversationPagination
     */
    public static ConversationPagination fromConversationPaginationDetails(ConversationPaginationDetails details) {
        // Create new instance
        ConversationPagination pagination = new ConversationPagination();
        // Map values
        pagination.setReadMore(details.getReadMore());
        pagination.setPageSize(details.getPageSize());

        return pagination;
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
                "pageSize=" + pageSize +
                ", readMore=" + readMore +
                '}';
    }
}
