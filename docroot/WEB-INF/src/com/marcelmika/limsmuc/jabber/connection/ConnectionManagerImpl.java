/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.connection;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.exception.JabberException;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

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
public class ConnectionManagerImpl implements ConnectionManager {

    // Log
    private static Log log = LogFactoryUtil.getLog(ConnectionManagerImpl.class);
    // Configuration to use while establishing the connection to the server.
    private ConnectionConfiguration connectionConfiguration;
    // Connection to the server
    private XMPPConnection connection;

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
        connection = new XMPPTCPConnection(getConnectionConfiguration());

        // Register for SASL Mechanism if enabled
        if (Environment.isSaslPlainEnabled()) {
            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
        }

        try {
            // Connect
            connection.connect();
        }
        // Failure
        catch (Exception e) {
            log.error(e);
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
        try {
            if (log.isDebugEnabled()) {
                log.debug("Disconnecting connection");
            }

            // Disconnect
            connection.disconnect();
        }
        // Failure
        catch (SmackException.NotConnectedException e) {
            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }
    }

    /**
     * Returns connection of the user
     */
    @Override
    public XMPPConnection getConnection() {
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
        return ChatManager.getInstanceFor(connection);
    }

    /**
     * Updates user's password
     *
     * @param password String
     * @throws JabberException
     */
    @Override
    public void updatePassword(String password) throws JabberException {
        // Get account manager
        AccountManager accountManager = AccountManager.getInstance(connection);

        try {
            // Update password
            accountManager.changePassword(password);
        }
        // Failure
        catch (Exception e) {
            throw new JabberException("Password cannot be updated", e);
        }
    }

    /**
     * Set or updates buddy's presence
     *
     * @param presence Presence of the concrete buddy.
     * @throws JabberException
     */
    @Override
    public void setPresence(Presence presence) throws JabberException {
        try {
            // Send the presence packet
            connection.sendPacket(presence);
        }
        // Failure
        catch (SmackException.NotConnectedException e) {
            throw new JabberException("Presence cannot be updated", e);
        }
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
    private void importUser(Buddy buddy, XMPPConnection connection) throws JabberException {

        // Get account manager
        AccountManager accountManager = AccountManager.getInstance(connection);

        // Check if the server supports account creation
        try {
            if (!accountManager.supportsAccountCreation()) {
                throw new JabberException("Jabber server does not support account creation");
            }
        }
        // Failure
        catch (Exception e) {
            throw new JabberException(e);
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
        catch (Exception e) {
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

        // Disable the security mode since we have no certificate
        connectionConfiguration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        // Enable reconnection
        connectionConfiguration.setReconnectionAllowed(true);
        // Is the initial available presence going to be send to the server?
        connectionConfiguration.setSendPresence(true);

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
