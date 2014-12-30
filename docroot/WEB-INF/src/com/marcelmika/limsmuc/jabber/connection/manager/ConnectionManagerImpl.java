/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.connection.manager;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.jabber.JabberException;
import com.marcelmika.limsmuc.jabber.connection.sasl.LiferaySaslMechanism;
import com.marcelmika.limsmuc.jabber.domain.Buddy;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages user's connection to the server
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class ConnectionManagerImpl implements ConnectionManager, ConnectionListener {

    // Log
    private static Log log = LogFactoryUtil.getLog(ConnectionManagerImpl.class);
    // Configuration to use while establishing the connection to the server.
    private ConnectionConfiguration connectionConfiguration;
    // Connection to the server
    private Connection connection;

    // -------------------------------------------------------------------------------------------
    // Override: ConnectionManager
    // -------------------------------------------------------------------------------------------

    /**
     * Creates new connection with jabber server
     *
     * @throws JabberException if connection creation fails
     */
    @Override
    public void createConnection() throws JabberException {
        // Create new connection from the connection configuration
        connection = new XMPPConnection(getConnectionConfiguration());

        // Register for SASL Mechanism if enabled
        if (Environment.isSaslPlainEnabled()) {
            SASLAuthentication.registerSASLMechanism("PLAIN", LiferaySaslMechanism.class);
            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
        }

        try {
            // Connect
            connection.connect();
            // Register connection listener
            connection.addConnectionListener(this);
        } catch (XMPPException e) {
            // Disconnect
            connection.disconnect();
            throw new JabberException("Cannot connect to the jabber server", e);
        }
    }

    /**
     * Login buddy
     *
     * @param buddy Buddy
     * @throws JabberException if login fails
     */
    @Override
    public void login(Buddy buddy) throws JabberException {
        // Check if user import is enabled
        boolean isImportUserEnabled = Environment.isJabberImportUserEnabled();
        // Login user
        login(buddy, isImportUserEnabled);
    }

    /**
     * Logout buddy
     */
    @Override
    public void logout() {
        // Disconnect
        connection.disconnect();
        // Un-register connection listener
        connection.removeConnectionListener(this);
    }

    /**
     * Returns connection of the user
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * Returns buddy's roster
     *
     * @return Roster
     */
    @Override
    public Roster getRoster() {
        return connection.getRoster();
    }

    /**
     * Returns buddy's chat manager
     *
     * @return ChatManager
     */
    @Override
    public ChatManager getChatManager() {
        return connection.getChatManager();
    }

    /**
     * Set or updates buddy's presence
     *
     * @param presence Presence of the concrete buddy.
     */
    @Override
    public void setPresence(Presence presence) {
        connection.sendPacket(presence);
    }

    /**
     * Login user with username and password
     *
     * @param buddy            Buddy
     * @param shouldImportUser true if the user should be imported to the jabber if login fails
     * @throws JabberException
     */
    private void login(Buddy buddy, boolean shouldImportUser) throws JabberException {

        try {
            // If the SASL is enabled login with username, password and resource
            if (Environment.isSaslPlainEnabled()) {
                // Login via SASL
                connection.login(
                        buddy.getScreenName(), Environment.getSaslPlainPassword(), Environment.getJabberResource()
                );
            } else {
                // Login with username and password
                connection.login(buddy.getScreenName(), buddy.getPassword(), Environment.getJabberResource());
            }
        }
        // Failure
        catch (Exception e) {
            // Get message returned from the Jabber server
            String message = e.getMessage();

            // Import user to server
            if (shouldImportUser && Validator.isNotNull(message) && message.contains("not-authorized")) {
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(String.format(
                            "Session for user %s did not authorize. Trying to import a user to Jabber.",
                            buddy.getScreenName()
                    ));
                }
                // Try to import user
                importUser(buddy, connection);
                // ... and login again. The second parameter must be false otherwise we could end up in the
                // infinite recursion
                login(buddy, false);
            }
            // Failure
            else {
                // Session Did Not Login
                throw new JabberException(String.format("Cannot log in user %s", buddy.getScreenName()), e);
            }
        }
    }

    /**
     * Imports user to the Jabber server
     *
     * @param buddy Buddy
     * @throws JabberException
     */
    private void importUser(Buddy buddy, Connection connection) throws JabberException {

        // Get account manager
        AccountManager accountManager = connection.getAccountManager();
        // Check if the server supports account creation
        if (!accountManager.supportsAccountCreation()) {
            throw new JabberException("Jabber server does not support account creation");
        }

        // Map params
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("name", buddy.getFullName());

        // Create an account
        if (log.isDebugEnabled()) {
            log.debug(String.format("Importing user %s to Jabber", buddy.getScreenName()));
        }
        try {
            // Create account
            accountManager.createAccount(buddy.getScreenName(), buddy.getPassword(), attributes);
        }
        // Failure
        catch (XMPPException e) {
            String message = e.getMessage();
            // Conflict
            if (Validator.isNotNull(message) && message.contains("conflict(409)")) {
                throw new JabberException(String.format(
                        "User %s already exists but has a different password", buddy.getScreenName()
                ));
            }

            throw new JabberException("New account cannot be created", e);
        }
    }


    // -------------------------------------------------------------------------------------------
    // Override: ConnectionListener
    // -------------------------------------------------------------------------------------------

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectionClosedOnError(Exception e) {

    }

    @Override
    public void reconnectingIn(int i) {

    }

    @Override
    public void reconnectionSuccessful() {

    }

    @Override
    public void reconnectionFailed(Exception e) {

    }


    // -------------------------------------------------------------------------------------------
    // Private methods
    // -------------------------------------------------------------------------------------------

    /**
     * Returns Connection configuration.
     * Sets proper values of the configuration if they are not set yet
     *
     * @return ConnectionConfiguration
     */
    private ConnectionConfiguration getConnectionConfiguration() {
        // If there is one already return it
        if (connectionConfiguration != null) {
            return connectionConfiguration;
        }

        // Create new configuration
        connectionConfiguration = new ConnectionConfiguration(
                Environment.getJabberHost(),
                Environment.getJabberPort(),
                Environment.getJabberServiceName());

        // Init configuration values
        // SASL Plain auth
        connectionConfiguration.setSASLAuthenticationEnabled(Environment.isSaslPlainEnabled());
        // Is the initial available presence going to be send to the server?
        connectionConfiguration.setSendPresence(true);

        // Sets the socket factory used to create new xmppConnection sockets.
        // This is useful when connecting through SOCKS5 proxies.
        SmackConfiguration.setLocalSocks5ProxyEnabled(Environment.isJabberSock5ProxyEnabled());
        SmackConfiguration.setLocalSocks5ProxyPort(Environment.getJabberSock5ProxyPort());

        return connectionConfiguration;
    }


    /**
     * Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("Connection manager for connection: [%s]", this.connection);
    }
}
