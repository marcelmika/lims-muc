/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.connection;

import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.exception.JabberException;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Presence;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 4:10 PM
 */
public interface ConnectionManager {

    /**
     * Creates new connection with jabber server
     *
     * @throws JabberException if connection creation fails
     */
    public void createConnection() throws JabberException;

    /**
     * Log user in
     *
     * @param buddy Buddy
     * @throws JabberException if login fails
     */
    public void login(Buddy buddy) throws JabberException;

    /**
     * Logout buddy
     */
    public void logout();

    /**
     * Returns connection of the user
     */
    public XMPPConnection getConnection();

    /**
     * Returns buddy's roster
     *
     * @return Roster
     */
    public Roster getRoster();

    /**
     * Returns buddy's chat manager
     *
     * @return ChatManager
     */
    public ChatManager getChatManager();

    /**
     * Updates user's password
     *
     * @param password String
     * @throws JabberException
     */
    public void updatePassword(String password) throws JabberException;

    /**
     * Set or updates buddy's presence
     *
     * @param presence Presence of the concrete buddy.
     * @throws JabberException
     */
    public void setPresence(final Presence presence) throws JabberException;

}
