/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.ConversationCollectionDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/10/14
 * Time: 15:53
 */
public class ConversationCollection {

    private List<Conversation> conversations = Collections.synchronizedList(new LinkedList<Conversation>());
    private Integer pageSize;
    private Integer maxSize;
    private Date lastModified;

    /**
     * Mapper method that creates conversation collection details from conversation collection
     *
     * @return ConversationCollectionDetails
     */
    public ConversationCollectionDetails toConversationCollectionDetails() {
        // Create new collection
        ConversationCollectionDetails details = new ConversationCollectionDetails();
        // Map data
        details.setPageSize(pageSize);
        details.setMaxSize(maxSize);
        details.setLastModified(lastModified);

        // Relations
        List<ConversationDetails> conversationDetails = new LinkedList<ConversationDetails>();
        for (Conversation conversation : conversations) {
            conversationDetails.add(conversation.toConversationDetails());
        }
        details.setConversations(conversationDetails);

        return details;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
