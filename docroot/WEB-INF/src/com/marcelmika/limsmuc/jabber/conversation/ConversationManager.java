/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.conversation;



import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 */
public class ConversationManager {

//    public void buildConversations(long userId, Connection connection) {
//
//        // Remove ghosts
//        if (ConversationStore.getInstance().getConversationContainer(userId) != null) {
//            ConversationStore.getInstance().removeConversationContainer(userId);
//        }
//
////        // MUC Conversation container
////        ConversationContainer container = new ConversationContainer();
////        Buddy owner = BuddyLocalServiceUtil.getBuddyByUserId(userId);
//
//        // Find all conversations that belongs to the user
//
//        List<Conversation> conversations = null;
////        try {
//////            conversations = ConversationLocalServiceUtil.getAllConversations(userId);
////        } catch (SystemException e) {
////            // TODO: log
////            return;
////        }
//
//        // Create conversations from all rooms that belong to the user
//        for (Conversation conversation : conversations) {
//            // Multi user conversation
//            if (conversation.getConversationType().equals(ConversationKeys.CONVERSATION_TYPE_MULTI_USER)) {
////                MUConversation muConversation = buildMUConversation(owner, connection, conversation);
//                // Add to container
////                container.addConversation(muConversation.getConversationId(), muConversation);
////                System.out.println("[CONNECTION][MANAGER][BUILDING][" + muConversation.getConversationName() + "[DONE]");
//            }
//
//            // Single user conversation
//            if (conversation.getConversationType().equals(ConversationKeys.CONVERSATION_TYPE_SINGLE_USER)) {
//                // @todo: Not implemented in v0.2
////                SUConversation suConversation = buildSUConversation(owner, connection);
////                // Add to container
////                container.addConversation(suConversation.getConversationId(), suConversation);
////                System.out.println("[CONNECTION][MANAGER][BUILDING][" + suConversation.getConversationName() + "[DONE]");
//            }
//        }
//
////        if (PortletPropsValues.JABBER_PP_ENABLED) {
////            JabberMultiUserChatUpdater mucUpdater = new JabberMultiUserChatUpdater();
////            mucUpdater = new JabberMultiUserChatUpdater();
////            mucUpdater.setConnection(connection);
////            mucUpdater.start();
////
////
////        }
//
////        ConversationStore.getInstance().putConversationContainer(userId, container);
//    }

//    protected MUConversation buildMUConversation(com.marcelmika.limsmuc.model.Buddy owner, Connection connection, Conversation conversation) {
//        // Conversation id is the same like the room id on the jabber side
//        String roomId = JabberUtil.getFullRoomId(conversation.getConversationId());
//        // Prepare multi user chat
//        MultiUserChat muc = new MultiUserChat(connection, roomId);
//
//        // Prepare conversation
//        MUConversation muConversation = null;
//        muConversation = new MUConversation(conversation.getConversationId(), owner, muc);
//
//        return muConversation;
//    }
//
//    protected void buildSUConversation(com.marcelmika.limsmuc.model.Buddy owner, Connection connection) {
//        // Listener for single user messaging
//        ChatManager chatManager = connection.getChatManager();
//        ChatManagerListener chatMessageListener = new JabberChatManagerListener(owner.getCompanyId(), owner.getUserId());
//        chatManager.addChatListener(chatMessageListener);
//    }
//

}
