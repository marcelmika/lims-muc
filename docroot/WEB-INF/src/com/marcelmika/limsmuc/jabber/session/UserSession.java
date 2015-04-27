/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.session;

import com.marcelmika.limsmuc.jabber.connection.ConnectionManager;
import com.marcelmika.limsmuc.jabber.conversation.multi.MultiUserConversationManager;
import com.marcelmika.limsmuc.jabber.conversation.multi.MultiUserConversationManagerFactory;
import com.marcelmika.limsmuc.jabber.conversation.single.SingleUserConversationManager;
import com.marcelmika.limsmuc.jabber.conversation.single.SingleUserConversationManagerFactory;
import com.marcelmika.limsmuc.jabber.group.GroupManager;
import com.marcelmika.limsmuc.jabber.group.GroupManagerFactory;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:34 PM
 */
public class UserSession {

    private Long sessionId;
    private ConnectionManager connectionManager;
    private GroupManager groupManager;
    private SingleUserConversationManager singleUserConversationManager;
    private MultiUserConversationManager multiUserConversationManager;

    /**
     * Constructor
     *
     * @param sessionId identifier
     */
    public UserSession(Long sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Factory method which creates user session form connection manager
     *
     * @param sessionId         id of the session
     * @param companyId         companyId related to the session,
     * @param connectionManager used to create session manager
     * @return UserSession object
     */
    public static UserSession fromConnectionManager(Long sessionId,
                                                    Long companyId,
                                                    ConnectionManager connectionManager) {
        // Create new user session
        UserSession userSession = new UserSession(sessionId);

        // Connection manager
        userSession.connectionManager = connectionManager;
        // Group manager
        userSession.groupManager = createGroupManager(companyId, connectionManager);
        // Single User Conversation manager
        userSession.singleUserConversationManager = createSingleUserConversationManager(companyId, connectionManager);

        // TODO: Multi user conversation is not implemented yet
        // Multi User Conversation manager
        // userSession.multiUserConversationManager = createMultiUserConversationManager(companyId, connectionManager);

        return userSession;
    }

    /**
     * Creates new group manager
     *
     * @param companyId         Long
     * @param connectionManager used to create group manager
     * @return Group manager
     */
    private static GroupManager createGroupManager(Long companyId, ConnectionManager connectionManager) {
        // Build group manager
        GroupManager groupManager = GroupManagerFactory.buildManager();
        groupManager.setRoster(connectionManager.getRoster());
        groupManager.setCompanyId(companyId);

        return groupManager;
    }

    /**
     * Create new single user conversation manager
     *
     * @param companyId         Long
     * @param connectionManager used to create single user conversation manager
     * @return SingleUserConversationManager
     */
    private static SingleUserConversationManager createSingleUserConversationManager(Long companyId,
                                                                                     ConnectionManager connectionManager) {

        // Build SUC manager
        SingleUserConversationManager singleManager = SingleUserConversationManagerFactory.buildManager();
        singleManager.setCompanyId(companyId);
        singleManager.setChatManager(connectionManager.getChatManager());

        return singleManager;
    }

    /**
     * Create new multi user conversation manager
     *
     * @param companyId         Long
     * @param connectionManager used to create multi user conversation manager
     * @return MultiUserConversationManager
     */
    private static MultiUserConversationManager createMultiUserConversationManager(Long companyId,
                                                                                   ConnectionManager connectionManager) {

        // Build MUC manager
        MultiUserConversationManager multiManager = MultiUserConversationManagerFactory.buildManager();
        multiManager.setCompanyId(companyId);
        multiManager.setConnection(connectionManager.getConnection());

        return multiManager;
    }


    public Long getSessionId() {
        return sessionId;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public SingleUserConversationManager getSingleUserConversationManager() {
        return singleUserConversationManager;
    }

    public void setSingleUserConversationManager(SingleUserConversationManager singleUserConversationManager) {
        this.singleUserConversationManager = singleUserConversationManager;
    }

    public MultiUserConversationManager getMultiUserConversationManager() {
        return multiUserConversationManager;
    }

    public void setMultiUserConversationManager(MultiUserConversationManager multiUserConversationManager) {
        this.multiUserConversationManager = multiUserConversationManager;
    }

}
