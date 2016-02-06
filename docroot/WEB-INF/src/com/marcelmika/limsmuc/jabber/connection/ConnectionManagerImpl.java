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
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;
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
    // Remembered user presence
    private Presence presence;

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

        // There is no need to connect the user since the connection is already created and works
        if (connection != null && connection.isConnected()) {
            // Log
            if (log.isDebugEnabled()) {
                log.debug(String.format(
                        "User %s was already connected, skipping creation of connection", connection.getUser()
                ));
            }
        }

        // Connect to jabber
        connect();
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
     * Returns true if the connection is connected
     *
     * @return boolean
     */
    @Override
    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

    /**
     * Returns true if the connection is authenticated
     *
     * @return boolean
     */
    @Override
    public boolean isAuthenticated() {
        return connection != null && connection.isAuthenticated();
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
        // Error occurs on the XMPP protocol level
        catch (XMPPException.XMPPErrorException e) {
            throw new JabberException("LIMS can't update user's password on Jabber server. XMPP error occurred.", e);
        }
        // User is not connected
        catch (SmackException.NotConnectedException e) {
            throw new JabberException("LIMS can't update user's password on Jabber server because the user is " +
                    "not connected", e);
        }
        // No response from server
        catch (SmackException.NoResponseException e) {
            throw new JabberException("LIMS can't update user's password on Jabber server because we are not " +
                    "receiving any response from the server", e);
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

            if (this.presence == null ||
                    presence.getMode() != this.presence.getMode() ||
                    presence.getType() != this.presence.getType()) {
                // Send the presence packet
                connection.sendPacket(presence);
                // Remember the presence
                this.presence = presence;
            }
        }
        // Failure
        catch (SmackException.NotConnectedException e) {
            throw new JabberException("Presence cannot be updated because the user is not connected to Jabber", e);
        }
    }

    /**
     * Creates new connection and connects to it
     *
     * @throws JabberException
     */
    private void connect() throws JabberException {

        // Create new connection from the connection configuration
        connection = new XMPPTCPConnection(getConnectionConfiguration());

        // Connect
        try {
            connection.connect();
        }
        // Error occurs somewhere else besides XMPP protocol level.
        catch (SmackException e) {
            throw new JabberException(
                    "LIMS can't connect to Jabber server. Either the server is down or you provided wrong" +
                            " host, port or service name.", e);
        }
        // Fatal error
        catch (IOException e) {

            String message = e.getMessage();

            // Certificate error
            if (Validator.isNotNull(message) &&
                    message.contains("unable to find valid certification path to requested target")) {

                throw new JabberException("Unable to find valid certification path to request target. Either turn " +
                        "the Security via TLS encryption off or enable TLS encryption on the Jabber server.", e);
            }
            // Other error
            else {
                throw new JabberException("LIMS can't connect to Jabber server. Fatal I/O error. Check the log.", e);
            }
        }
        // Error occurs on the XMPP protocol level
        catch (XMPPException e) {
            throw new JabberException("LIMS can't connect to Jabber server. XMPP error occurred. Check the log.", e);
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

        // Connection cannot be empty
        if (connection == null) {
            if (log.isErrorEnabled()) {
                log.error("Connection was empty during login");
            }
            // End here
            return;
        }

        // There is no need to login the user again if he was already authenticated
        if (connection.isAuthenticated()) {
            // Log
            if (log.isDebugEnabled()) {
                log.debug(String.format(
                        "Skipping login of user %s. User was already authenticated.", buddy.getScreenName()
                ));
            }
            // End here
            return;
        }

        // Compose the password
        String password;

        // Take the password from the shared secret if enabled
        if (Environment.isJabberSharedSecretEnabled()) {
            password = Environment.getJabberSharedSecret();
        }
        // Take password from buddy
        else if (buddy.getPassword() != null) {
            password = buddy.getPassword();
        }
        // No password was passed
        else {
            throw new JabberException("Password was empty during login");
        }

        try {
            // Login with username and password
            connection.login(buddy.getScreenName(), password, Environment.getJabberResource());
        }
        // Failure
        catch (Exception e) {

            // Log the exception
            if (log.isDebugEnabled()) {
                log.debug(e);
            }

            // Get message returned from the Jabber server
            String message = e.getMessage();

            // Import user to server
            if (shouldImportUser && Validator.isNotNull(message) && message.contains("not-authorized")) {
                // Log
                if (log.isInfoEnabled()) {
                    log.info(String.format(
                            "User %s wasn't authenticated against the Jabber server. " +
                                    "LIMS will now try to import the user to Jabber.",
                            buddy.getScreenName()
                    ));
                }
                // Try to import user
                importUser(buddy, connection);
                // We need to recreate the connection here
                connect();
                // ... and login again. The second parameter must be false otherwise we could end up in the
                // infinite recursion
                login(buddy, false);
            }
            // User cannot be imported
            else if (Validator.isNotNull(message) && message.contains("not-authorized")) {

                // User wasn't authenticated because the password was empty
                if (buddy.getPassword() == null) {
                    // Log warning
                    if (log.isWarnEnabled()) {
                        log.warn("User password was null during the login to Jabber");
                    }
                }

                throw new JabberException(String.format(
                        "User %s wasn't authenticated against the Jabber server. It is possible that the user " +
                                "does not exist in Jabber or the password used to login " +
                                "to Liferay is different than the one used to login to Jabber. " +
                                "Try to enable the Jabber User Import. If the problem remains contact LIMS support.",
                        buddy.getScreenName()
                ));
            }
            // Failure
            else {
                // User wasn't authenticated because the password was empty
                if (buddy.getPassword() == null) {
                    // Log warning
                    if (log.isWarnEnabled()) {
                        log.warn("User password was null during the login to Jabber");
                    }
                }

                // Session Did Not Login
                throw new JabberException(e);
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
            if (Validator.isNotNull(message) && message.contains("conflict")) {
                throw new JabberException(String.format(
                        "User %s cannot be imported to Jabber because such user already exists " +
                                "but has a different password.",
                        buddy.getScreenName()
                ), e);
            }
            // Any other error message
            else {
                throw new JabberException(e);
            }
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

        // Disable the security mode if requested
        if (!Environment.getJabberSecurityEnabled()) {
            connectionConfiguration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        }

        // connectionConfiguration.setSocketFactory(new DummySSLSocketFactory());

        // Enable reconnection
        connectionConfiguration.setReconnectionAllowed(true);
        // Is the initial available presence going to be send to the server?
        connectionConfiguration.setSendPresence(false);
        // We need to load the roaster at login otherwise we don't get the presence updates
        connectionConfiguration.setRosterLoadedAtLogin(true);

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
