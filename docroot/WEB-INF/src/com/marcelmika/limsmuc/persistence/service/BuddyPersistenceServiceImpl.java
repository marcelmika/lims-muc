/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.buddy.DeleteBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.DeleteBuddyResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.LoginBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.LoginBuddyResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.LogoutBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.LogoutBuddyResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadBuddiesPresenceRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadBuddiesPresenceResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadBuddyPresenceRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadBuddyPresenceResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadPresenceChangeRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadPresenceChangeResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.SearchBuddiesRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.SearchBuddiesResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePresenceBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePresenceBuddyResponseEvent;
import com.marcelmika.limsmuc.persistence.domain.Buddy;
import com.marcelmika.limsmuc.persistence.domain.Presence;
import com.marcelmika.limsmuc.persistence.generated.model.Settings;
import com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.manager.SearchManager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:56 PM
 */
public class BuddyPersistenceServiceImpl implements BuddyPersistenceService {

    // Dependencies
    SearchManager searchManager;

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(BuddyPersistenceServiceImpl.class);

    /**
     * Constructor
     *
     * @param searchManager SearchManager
     */
    public BuddyPersistenceServiceImpl(final SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    /**
     * Login buddy to System
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event) {
        // Get buddy from buddy details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        try {

            // Update user's connection
            SettingsLocalServiceUtil.updateConnection(buddy.getBuddyId(), true);

            // Call success
            return LoginBuddyResponseEvent.success(buddy.toBuddyDetails());

        } catch (Exception exception) {
            // Call failure
            return LoginBuddyResponseEvent.failure(
                    LoginBuddyResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Logout buddy from System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event) {
        // Get buddy from buddy details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        // Check parameters
        if (buddy.getBuddyId() == null) {
            return LogoutBuddyResponseEvent.failure(
                    LogoutBuddyResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        try {
            // Update user's connection
            SettingsLocalServiceUtil.updateConnection(buddy.getBuddyId(), false);

            // Success
            return LogoutBuddyResponseEvent.success(buddy.toBuddyDetails());
        }
        // Failure
        catch (Exception e) {
            // Call failure
            return LogoutBuddyResponseEvent.failure(
                    LogoutBuddyResponseEvent.Status.ERROR_PERSISTENCE, e
            );
        }
    }

    /**
     * Completely removes buddy from Persistence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public DeleteBuddyResponseEvent removeBuddy(DeleteBuddyRequestEvent event) {
        // Get buddy from buddy details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        try {
            // Remove settings from database
            Settings settings = SettingsLocalServiceUtil.getSettingsByUser(buddy.getBuddyId());
            if (settings != null) {
                // Delete only if the user is in the system otherwise do nothing
                SettingsLocalServiceUtil.deleteSettings(settings);
            }
            // Success
            return DeleteBuddyResponseEvent.success(buddy.toBuddyDetails());
        }
        // Failure
        catch (Exception e) {
            return DeleteBuddyResponseEvent.failure(
                    DeleteBuddyResponseEvent.Status.ERROR_PERSISTENCE, e
            );
        }
    }

    /**
     * Reads presence of a single buddy
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadBuddyPresenceResponseEvent readBuddyPresence(ReadBuddyPresenceRequestEvent event) {
        // Get buddy from buddy details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());

        try {
            // Take presence from user settings
            Settings settings = SettingsLocalServiceUtil.getSettingsByUser(buddy.getBuddyId());

            if (settings != null) {

                Presence presence;
                // User is connected
                if (settings.isConnected()) {
                    // Create Presence from string
                    presence = Presence.fromDescription(settings.getPresence());
                } else {
                    // User is not connected this means that he's offline
                    presence = Presence.OFFLINE;
                }

                // Success
                return ReadBuddyPresenceResponseEvent.success(presence.toPresenceDetails());
            }
            // Failure
            else {
                return ReadBuddyPresenceResponseEvent.failure(
                        ReadBuddyPresenceResponseEvent.Status.ERROR_PERSISTENCE
                );
            }

        } catch (Exception e) {
            // Failure
            return ReadBuddyPresenceResponseEvent.failure(
                    ReadBuddyPresenceResponseEvent.Status.ERROR_PERSISTENCE, e
            );
        }
    }

    /**
     * Reads presence of buddies
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadBuddiesPresenceResponseEvent readBuddiesPresence(ReadBuddiesPresenceRequestEvent event) {
        // Map the list of buddies
        Set<Long> buddies = event.getBuddies();
        List<BuddyDetails> details = new LinkedList<BuddyDetails>();

        try {

            // Read their settings
            for (Long buddyId : buddies) {
                // Take presence form user settings
                Settings settings = SettingsLocalServiceUtil.fetchByUserId(buddyId);
                // Prepare the presence
                Presence presence;

                // User has already logged in
                if (settings != null) {
                    // User is connected
                    if (settings.isConnected()) {
                        // Create Presence from string
                        presence = Presence.fromDescription(settings.getPresence());
                    }
                    // User is offline
                    else {
                        presence = Presence.OFFLINE;
                    }
                }
                // User never logged to the portal
                else {
                    presence = Presence.UNRECOGNIZED;
                }

                // Create new buddy
                BuddyDetails buddy = new BuddyDetails();
                buddy.setBuddyId(buddyId);
                buddy.setPresenceDetails(presence.toPresenceDetails());

                // Add to details
                details.add(buddy);
            }

            // Success
            return ReadBuddiesPresenceResponseEvent.success(details);
        }
        // Failure
        catch (SystemException e) {
            return ReadBuddiesPresenceResponseEvent.failure(
                    ReadBuddiesPresenceResponseEvent.Status.ERROR_PERSISTENCE, e
            );
        }
    }

    /**
     * Change buddy's presence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdatePresenceBuddyResponseEvent updatePresence(UpdatePresenceBuddyRequestEvent event) {
        // Get presence
        Presence presence = Presence.fromPresenceDetails(event.getPresenceDetails());

        try {
            // Save to settings
            SettingsLocalServiceUtil.changePresence(event.getBuddyId(), presence.getDescription());
            // Success
            return UpdatePresenceBuddyResponseEvent.success();

        } catch (Exception exception) {
            // Failure
            return UpdatePresenceBuddyResponseEvent.failure(
                    UpdatePresenceBuddyResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Search buddies in the system
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public SearchBuddiesResponseEvent searchBuddies(SearchBuddiesRequestEvent event) {
        // Map buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());

        // Check params
        if (buddy.getBuddyId() == null) {
            return SearchBuddiesResponseEvent.failure(SearchBuddiesResponseEvent.Status.ERROR_WRONG_PARAMETERS);
        }

        try {
            // Define boundaries
            int start = 0;
            int end = Environment.getBuddyListMaxSearch();

            // Get buddies from manager
            List<Buddy> buddies = searchManager.searchBuddies(buddy.getBuddyId(), event.getSearchQuery(), start, end);

            // Create buddy details list from the buddy list
            List<BuddyDetails> buddyDetails = new LinkedList<BuddyDetails>();
            for (Buddy searchedBuddy : buddies) {
                buddyDetails.add(searchedBuddy.toBuddyDetails());
            }

            // Call success
            return SearchBuddiesResponseEvent.success(buddyDetails);
        }
        // Failure
        catch (Exception exception) {
            return SearchBuddiesResponseEvent.failure(
                    SearchBuddiesResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Reads buddies that have changed their presence since the particular time
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadPresenceChangeResponseEvent readPresenceChange(ReadPresenceChangeRequestEvent event) {

        Date since = event.getSince();
        List<BuddyDetails> details = new LinkedList<BuddyDetails>();

        // Map parameters
        if (since == null) {
            return ReadPresenceChangeResponseEvent.failure(
                    ReadPresenceChangeResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        try {
            // Load the settings
            List<Settings> settings = SettingsLocalServiceUtil.findByPresenceUpdatedSince(since);

            for (Settings setting : settings) {

                Presence presence;

                // User is connected
                if (setting.isConnected()) {
                    // Create Presence from string
                    presence = Presence.fromDescription(setting.getPresence());
                }
                // User is offline
                else {
                    presence = Presence.OFFLINE;
                }


                // Create new buddy
                BuddyDetails buddy = new BuddyDetails();
                buddy.setBuddyId(setting.getUserId());
                buddy.setConnected(setting.getConnected());
                buddy.setPresenceDetails(presence.toPresenceDetails());

                details.add(buddy);
            }

            // Success
            return ReadPresenceChangeResponseEvent.success(details);
        }
        // Failure
        catch (SystemException exception) {
            return ReadPresenceChangeResponseEvent.failure(
                    ReadPresenceChangeResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }
}
