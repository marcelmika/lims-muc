/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.service;

import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.buddy.*;
import com.marcelmika.limsmuc.persistence.domain.Buddy;
import com.marcelmika.limsmuc.persistence.domain.Presence;
import com.marcelmika.limsmuc.persistence.generated.model.Settings;
import com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.manager.SearchManager;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:56 PM
 */
public class BuddyPersistenceServiceImpl implements BuddyPersistenceService {

    // Dependencies
    SearchManager searchManager;

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

        try {
            // Update user's connection
            SettingsLocalServiceUtil.updateConnection(buddy.getBuddyId(), false);

            // Call success
            return LogoutBuddyResponseEvent.success("User successfully logged out", buddy.toBuddyDetails());

        } catch (Exception e) {
            // Call failure
            return LogoutBuddyResponseEvent.failure(e.getLocalizedMessage(), buddy.toBuddyDetails());
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
            return DeleteBuddyResponseEvent.success("Buddy " + buddy.getBuddyId() + " has been successfully " +
                    "removed from the persistence layer", buddy.toBuddyDetails());
        } catch (Exception e) {
            // Failure
            return DeleteBuddyResponseEvent.failure(
                    "Cannot remove buddy from persistence layer.",
                    buddy.toBuddyDetails()
            );
        }
    }

    /**
     * Reads buddy's presence
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadPresenceBuddyResponseEvent readPresence(ReadPresenceBuddyRequestEvent event) {
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
                return ReadPresenceBuddyResponseEvent.success(
                        "Presence successfully read", presence.toPresenceDetails()
                );
            } else {
                // Failure
                return ReadPresenceBuddyResponseEvent.failure(
                        new Exception(String.format("Cannot find settings for buddy with ID: %s", buddy.getBuddyId()))
                );
            }

        } catch (Exception e) {
            // Failure
            return ReadPresenceBuddyResponseEvent.failure(e);
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
}
