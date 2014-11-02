/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.jabber.domain;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 */
public interface Conversation {

//    // Buddies
//    public com.marcelmika.lims.model.Buddy getOwner();
//
//    public List<com.marcelmika.lims.model.Buddy> getParticipants();
//
//    public void addParticipant(com.marcelmika.lims.model.Buddy participant);

    // Metadata
    public String getConversationId();

    public String getConversationName();

    public String getConversationType();

    public String getConversationVisibility();

    // Restart
    public void restart();

    // Message related stuff
    public List<MessageDeprecated> getMessages();

    public MessageDeprecated getLastMessage();

    public int getLastMessageSent();

    public void setLastMessageSent(int lastMessageSent);

    public int getIndexOfLastMessage();

    public int getUnreadMessages();

    // @todo: Move to separate json mapper
    public JSONObject toJSON();

    public JSONObject toFullJSON();
}
