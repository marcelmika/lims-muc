/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.domain;

import com.marcelmika.limsmuc.api.entity.ConversationCollectionDetails;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/10/14
 * Time: 15:42
 */
public class ConversationCollection {

    private List<Conversation> conversations = Collections.synchronizedList(new LinkedList<Conversation>());
    private Date lastModified;
    private Integer pageSize;
    private Integer maxSize;
    private int etag;

    /**
     * Factory method that creates conversation collection from conversation collection details
     *
     * @param details ConversationCollectionDetails
     * @return ConversationCollection
     */
    public static ConversationCollection fromConversationCollectionDetails(ConversationCollectionDetails details) {
        // Create new collection
        ConversationCollection conversationCollection = new ConversationCollection();
        // Map data
        conversationCollection.pageSize = details.getPageSize();
        conversationCollection.maxSize = details.getMaxSize();
        conversationCollection.lastModified = details.getLastModified();


        if (details.getLastModified() != null) {
            // Etag is made of the last modification date. Thus if the conversation collection changes
            // its modification date the client will download newer version
            conversationCollection.etag = details.getLastModified().hashCode();
        }
        // No modification date was set yet, for example user has no conversations yet
        else {
            // Set the has code from 0 because the user has no conversations yet. However, client should
            // remember the etag anyway since it's going to change whenever the user appear in any of the
            // conversations
            conversationCollection.etag = new Integer(0).hashCode();
        }

        // Relations
        if (details.getConversations() != null) {
            conversationCollection.conversations = Conversation.fromConversationDetailsList(details.getConversations());
        }

        return conversationCollection;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public int getEtag() {
        return etag;
    }

    public void setEtag(int etag) {
        this.etag = etag;
    }
}
