/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.entity.ConversationCollectionDetails;

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
