/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.properties;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListSocialRelation;
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListStrategy;
import com.marcelmika.limsmuc.api.environment.Environment.PropertiesSource;
import com.marcelmika.limsmuc.portal.domain.Properties;

import javax.portlet.PortletPreferences;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

/**
 * Updates portlet preferences based on the properties
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 13/10/14
 * Time: 15:25
 */
public class PropertiesManagerImpl implements PropertiesManager {

    // Set to true if the environment was already set up
    private boolean isSetup = false;

    // Buddy List Max Buddies
    private static final int BUDDY_LIST_MAX_BUDDIES_MIN = 10;
    private static final int BUDDY_LIST_MAX_BUDDIES_MAX = 30;
    private static final int BUDDY_LIST_MAX_BUDDIES_DEFAULT = 10;

    // Buddy List Max Search
    private static final int BUDDY_LIST_MAX_SEARCH_MIN = 7;
    private static final int BUDDY_LIST_MAX_SEARCH_MAX = 30;
    private static final int BUDDY_LIST_MAX_SEARCH_DEFAULT = 10;

    // Connection Lost
    private static final int CONNECTION_LOST_THRESHOLD_MIN = 1;
    private static final int CONNECTION_LOST_THRESHOLD_MAX = 1440;
    private static final int CONNECTION_LOST_THRESHOLD_DEFAULT = 2;

    // Conversation List Max Messages
    private static final int CONVERSATION_LIST_MAX_MESSAGES_MIN = 10;
    private static final int CONVERSATION_LIST_MAX_MESSAGES_MAX = 50;
    private static final int CONVERSATION_LIST_MAX_MESSAGES_DEFAULT = 10;

    // Conversation Feed Max Conversations
    private static final int CONVERSATION_FEED_MAX_CONVERSATIONS_MIN = 6;
    private static final int CONVERSATION_FEED_MAX_CONVERSATIONS_MAX = 20;
    private static final int CONVERSATION_FEED_MAX_CONVERSATIONS_DEFAULT = 7;

    // Polling Slow Down Threshold
    private static final int POLLING_SLOW_DOWN_THRESHOLD_MIN = 100;
    private static final int POLLING_SLOW_DOWN_THRESHOLD_MAX = 5000;
    private static final int POLLING_SLOW_DOWN_THRESHOLD_DEFAULT = 1000;

    // Jabber Host
    private static final String JABBER_HOST_DEFAULT = "127.0.0.1";

    // Jabber Port
    private static final int JABBER_PORT_MIN = 1;
    private static final int JABBER_PORT_MAX = 65536;
    private static final int JABBER_PORT_DEFAULT = 5222;

    // Jabber Service Name
    private static final String JABBER_SERVICE_NAME_DEFAULT = "jabber-service";

    // Jabber Resource
    private static final String JABBER_RESOURCE_DEFAULT = "LIMS";

    // Jabber Resource Priority
    private static final int JABBER_RESOURCE_PRIORITY_MIN = -128;
    private static final int JABBER_RESOURCE_PRIORITY_MAX = 128;
    private static final int JABBER_RESOURCE_PRIORITY_DEFAULT = 0;


    // Log
    private static Log log = LogFactoryUtil.getLog(PropertiesManagerImpl.class);

    /**
     * Sets up all portlet properties. Decides which source of properties should be taken into account.
     * It doesn't matter how many times the method is called. The setup will be called just once.
     *
     * @param preferences PortletPreferences
     */
    @Override
    public void setup(PortletPreferences preferences) {

        // Preferences cannot be null
        if (preferences == null) {
            // Log
            if (log.isErrorEnabled()) {
                log.error("Cannot load preferences");
            }
            // End here
            return;
        }

        // Setup just once, there is no need to set it on every call of the setup method
        if (!isSetup) {

            // Setup can be done just once at the beginning
            isSetup = true;

            // Log
            if (log.isDebugEnabled()) {
                log.debug("Setting the environment");
            }

            // Set properties source
            setupPropertiesSource();

            // Set the whole environment
            setupExcludedSites(preferences);
            setupBuddyListStrategy(preferences);
            setupBuddyListSocialRelations(preferences);
            setupBuddyListGroupSiteEnabled(preferences);
            setupBuddyListGroupSocialEnabled(preferences);
            setupBuddyListGroupUserEnabled(preferences);
            setupBuddyListIgnoreDeactivatedUser(preferences);
            setupBuddyListMaxBuddies();
            setupBuddyListMaxSearch();
            setupConversationListMaxMessages();
            setupConversationFeedMaxConversations();
            setupBuddyListSiteExcludes(preferences);
            setupBuddyListGroupExcludes(preferences);
            setupConnectionLostThreshold();
            setupPollingSlowDownThreshold();

            // Instance secret
            setupInstanceSecret(preferences);
            // Product key
            setupProductKey();

            // Mobile
            setupMobileUserScalableDisabled(preferences);

            // Set url properties
            setupUrlProperties();

            // Set jabber
            setupJabberEnabled(preferences);
            setupJabberSecurityEnabled(preferences);
            setupJabberImportUserEnabled(preferences);
            setupJabberHost(preferences);
            setupJabberPort(preferences);
            setupJabberServiceName(preferences);
            setupJabberResource(preferences);
            setupJabberResourcePriority();
            setupJabberSharedSecretEnabled(preferences);
            setupJabberSharedSecret(preferences);

            // Set IPC
            setupIPCEnabled(preferences);

            // Set Browser Notifications
            setupBrowserNotificationsRequireInteraction();
        }
    }

    /**
     * Updates portlet preferences based on the properties. Preferences are stored and updated
     * in current instance of Environment as well. Thanks to that the changes are visible immediately.
     * If the property in the properties object is null, nothing is updated.
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    @Override
    public void updatePortletPreferences(PortletPreferences preferences, Properties properties) throws Exception {

        // Excluded sites
        if (properties.getExcludedSites() != null) {
            updateExcludedSites(preferences, properties);
        }

        // Buddy list strategy
        if (properties.getBuddyListStrategy() != null) {
            updateBuddyListStrategy(preferences, properties);
        }

        // Buddy list social relations
        if (properties.getBuddyListSocialRelations() != null) {
            updateBuddyListSocialRelations(preferences, properties);
        }

        // Buddy list group site enabled
        if (properties.isBuddyListGroupSiteEnabled() != null) {
            updateBuddyListGroupSiteEnabled(preferences, properties);
        }

        // Buddy list group social enabled
        if (properties.isBuddyListGroupSocialEnabled() != null) {
            updateBuddyListGroupSocialEnabled(preferences, properties);
        }

        // Buddy list group user enabled
        if (properties.isBuddyListGroupUserEnabled() != null) {
            updateBuddyListGroupUserEnabled(preferences, properties);
        }

        // Buddy list ignore deactivated user
        if (properties.getBuddyListIgnoreDeactivatedUser() != null) {
            updateBuddyListIgnoreDeactivatedUser(preferences, properties);
        }

        // Buddy list site excludes
        if (properties.getBuddyListSiteExcludes() != null) {
            updateBuddyListSiteExcludes(preferences, properties);
        }

        // Buddy list group excludes
        if (properties.getBuddyListGroupExcludes() != null) {
            updateBuddyListGroupExcludes(preferences, properties);
        }

        // Mobile user scalable disabled
        if (properties.getMobileUserScalableDisabled() != null) {
            updateMobileUserScalableDisabled(preferences, properties);
        }

        // Jabber enabled
        if (properties.getJabberEnabled() != null) {
            updateJabberEnabled(preferences, properties);
        }

        // Jabber security enabled
        if (properties.getJabberSecurityEnabled() != null) {
            updateJabberSecurityEnabled(preferences, properties);
        }

        // Jabber import user enabled
        if (properties.getJabberImportUserEnabled() != null) {
            updateJabberImportUserEnabled(preferences, properties);
        }

        // Jabber host
        if (properties.getJabberHost() != null) {
            updateJabberHost(preferences, properties);
        }

        // Jabber port
        if (properties.getJabberPort() != null) {
            updateJabberPort(preferences, properties);
        }

        // Jabber service name
        if (properties.getJabberServiceName() != null) {
            updateJabberServiceName(preferences, properties);
        }

        // Jabber resource
        if (properties.getJabberResource() != null) {
            updateJabberResource(preferences, properties);
        }

        // Jabber shared secret enabled
        if (properties.getJabberSharedSecretEnabled() != null) {
            updateJabberSharedSecretEnabled(preferences, properties);
        }

        // Jabber shared secret
        if (properties.getJabberSharedSecret() != null) {
            updateJabberSharedSecret(preferences, properties);
        }

        // IPC enabled
        if (properties.getIpcEnabled() != null) {
            updateIPCEnabled(preferences, properties);
        }
    }

    /**
     * Sets properties source
     */
    private void setupPropertiesSource() {
        String value = PortletPropertiesValues.PROPERTIES_SOURCE;

        PropertiesSource propertiesSource;

        // Preferences
        if (value.equals("preferences")) {
            propertiesSource = PropertiesSource.PREFERENCES;
        }
        // Properties
        else if (value.equals("properties")) {
            propertiesSource = PropertiesSource.PROPERTIES;
        }
        // Unknown value
        else {
            log.error(String.format(
                    "Unknown properties source %s. Valid values are \"preferences\" or \"properties\". Since no valid " +
                            "property was provided \"preferences\" was chosen as a default. The value can be " +
                            "set in portlet-ext.properties file related to the LIMS portlet.", value
            ));
            // Fallback to default
            propertiesSource = PropertiesSource.PREFERENCES;
        }

        // Save to Environment
        Environment.setPropertiesSource(propertiesSource);
    }

    /**
     * Sets the excluded sites property
     *
     * @param preferences PortletPreferences
     */
    private void setupExcludedSites(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        String[] excludedSites;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            excludedSites = preferences.getValues(
                    PortletPropertiesKeys.EXCLUDED_SITES,
                    PortletPropertiesValues.EXCLUDED_SITES
            );
        }
        // Properties
        else {
            excludedSites = PortletPropertiesValues.EXCLUDED_SITES;
        }

        // Save in Environment
        Environment.setExcludedSites(excludedSites);
    }

    /**
     * Updates excluded sites property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateExcludedSites(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValues(
                PortletPropertiesKeys.EXCLUDED_SITES,
                properties.getExcludedSites()
        );

        // Persist
        preferences.store();

        // Save in Environment
        setupExcludedSites(preferences);
    }

    /**
     * Updates buddy list strategy preferences
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListStrategy(PortletPreferences preferences, Properties properties) throws Exception {

        // Reset previous preferences
        preferences.reset(PortletPropertiesKeys.BUDDY_LIST_STRATEGY);

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_STRATEGY,
                properties.getBuddyListStrategy().getDescription()
        );

        // Persist
        preferences.store();

        // Save in environment
        setupBuddyListStrategy(preferences);
    }

    /**
     * Sets the buddy list strategy
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListStrategy(PortletPreferences preferences) {
        // Get the property source
        PropertiesSource source = Environment.getPropertiesSource();

        String value;
        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preference. Value from properties is a default.
            value = preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_STRATEGY,
                    PortletPropertiesValues.BUDDY_LIST_STRATEGY
            );
        }
        // Properties
        else {
            // Take the value from properties
            value = PortletPropertiesValues.BUDDY_LIST_STRATEGY;
        }

        BuddyListStrategy buddyListStrategy;

        // All
        if (value.equals(BuddyListStrategy.ALL.getDescription())) {
            buddyListStrategy = BuddyListStrategy.ALL;
        }
        // Groups
        else if (value.equals(BuddyListStrategy.GROUPS.getDescription())) {
            buddyListStrategy = BuddyListStrategy.GROUPS;
        }
        // Jabber
        else if (value.equals(BuddyListStrategy.JABBER.getDescription())) {
            buddyListStrategy = BuddyListStrategy.JABBER;
        }

        // Unknown value
        else {
            // Fix the unknown value caused by the deprecation of list strategy values
            buddyListStrategy = fixDeprecatedBuddyListStrategy(preferences, value);
        }

        // Save in environment
        Environment.setBuddyListStrategy(buddyListStrategy);
    }

    /**
     * Since v1.3.0 the buddy list strategy no longer provides "sites", "social" and "sites,social" values.
     * The method takes the value and updates buddy list group value based on that. It also saves the new
     * value to preferences
     *
     * @param preferences PortletPreferences
     * @param value       String
     * @return BuddyListStrategy
     */
    private BuddyListStrategy fixDeprecatedBuddyListStrategy(PortletPreferences preferences, String value) {

        BuddyListStrategy buddyListStrategy;

        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        // The value is taken from PREFERENCES
        if (source == PropertiesSource.PREFERENCES) {

            try {

                // SITES
                if (value.equals("sites")) {
                    // Create new properties file with updated value
                    Properties properties = new Properties();
                    properties.setBuddyListGroupSiteEnabled(true);
                    properties.setBuddyListStrategy(BuddyListStrategy.GROUPS);

                    // Update in preferences
                    updateBuddyListGroupSiteEnabled(preferences, properties);
                    updateBuddyListStrategy(preferences, properties);

                    // Set buddy list strategy
                    buddyListStrategy = BuddyListStrategy.GROUPS;

                    // Log
                    log.info("Deprecated buddy list strategy: " + value + " was changed to GROUPS. Furthermore, " +
                            "buddy list group site was enabled.");
                }

                // SOCIAL
                else if (value.equals("social")) {
                    // Create new properties file with updated value
                    Properties properties = new Properties();
                    properties.setBuddyListGroupSocialEnabled(true);
                    properties.setBuddyListStrategy(BuddyListStrategy.GROUPS);

                    // Update in preferences
                    updateBuddyListGroupSocialEnabled(preferences, properties);
                    updateBuddyListStrategy(preferences, properties);

                    // Set buddy list strategy
                    buddyListStrategy = BuddyListStrategy.GROUPS;

                    // Log
                    log.info("Deprecated buddy list strategy: " + value + " was changed to GROUPS. Furthermore, " +
                            "buddy list group social was enabled.");
                }

                // SITES,SOCIAL
                else if (value.equals("sites,social")) {
                    // Create new properties file with updated value
                    Properties properties = new Properties();
                    properties.setBuddyListGroupSiteEnabled(true);
                    properties.setBuddyListGroupSocialEnabled(true);
                    properties.setBuddyListStrategy(BuddyListStrategy.GROUPS);

                    // Update in preferences
                    updateBuddyListGroupSiteEnabled(preferences, properties);
                    updateBuddyListGroupSocialEnabled(preferences, properties);
                    updateBuddyListStrategy(preferences, properties);

                    // Set buddy list strategy
                    buddyListStrategy = BuddyListStrategy.GROUPS;

                    // Log
                    log.info("Deprecated buddy list strategy: " + value + " was changed to GROUPS. Furthermore, " +
                            "buddy list group site and social was enabled.");
                }

                // UNKNOWN
                // This is probably not going to happen, however we should handle this state as well
                else {

                    // Create new properties file with updated value
                    Properties properties = new Properties();
                    properties.setBuddyListStrategy(BuddyListStrategy.GROUPS);

                    // Update in preferences
                    updateBuddyListStrategy(preferences, properties);

                    // Set buddy list strategy
                    buddyListStrategy = BuddyListStrategy.ALL;
                }

            }
            // Failure
            catch (Exception exception) {
                // Log
                log.error("List strategy deprecation fix failed", exception);

                // Set buddy list strategy
                buddyListStrategy = BuddyListStrategy.ALL;
            }
        }
        // The value is taken from PROPERTIES
        else {
            log.error(String.format(
                    "Unknown buddy list strategy: %s. Valid values are \"all\", \"groups\", \"jabber\". " +
                            "Since no valid property provided \"all\" was chosen as a default. The value " +
                            "can be set in portlet-ext.properties file related to the LIMS portlet.", value
            ));

            buddyListStrategy = BuddyListStrategy.ALL;
        }

        return buddyListStrategy;
    }

    /**
     * Updates buddy list social relations preferences
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListSocialRelations(PortletPreferences preferences, Properties properties) throws Exception {

        // Social relations needs to be mapped to the string alternatives so they can
        // be moved to the preferences
        Environment.BuddyListSocialRelation[] relations = properties.getBuddyListSocialRelations();
        String[] relationStrings = new String[relations.length];

        for (int i = 0; i < relationStrings.length; i++) {
            relationStrings[i] = String.valueOf(relations[i].getCode());
        }

        // Reset previous preferences
        preferences.reset(PortletPropertiesKeys.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES);

        // Set the value in portlet preferences
        preferences.setValues(
                PortletPropertiesKeys.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES,
                relationStrings
        );

        // Persist
        preferences.store();

        // Save in environment
        setupBuddyListSocialRelations(preferences);
    }

    /**
     * Sets the buddy list allowed social relations
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListSocialRelations(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        int[] relationsTypeCodes;
        // Preferences
        if (source == PropertiesSource.PREFERENCES) {

            // Take the value from preferences
            String[] values = preferences.getValues(
                    PortletPropertiesKeys.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES, null
            );

            // There is already some values set in preferences
            if (values != null) {
                // However, the values are stored as string values so we first need to parse them.
                // Create new array of integers with the same size as the values gotten from preferences.
                relationsTypeCodes = new int[values.length];

                // Iterate over relation codes
                for (int i = 0; i < relationsTypeCodes.length; i++) {
                    // And parse each code from string to integer
                    relationsTypeCodes[i] = Integer.parseInt(values[i]);
                }
            }
            // Nothing was set in preferences so take it from properties
            else {
                relationsTypeCodes = PortletPropertiesValues.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES;
            }

        }
        // Properties
        else {
            relationsTypeCodes = PortletPropertiesValues.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES;
        }

        // Create a set which will contain enums that represent relation types
        Set<BuddyListSocialRelation> relationTypeSet = new HashSet<BuddyListSocialRelation>();

        // Map integer values to relation types
        for (int value : relationsTypeCodes) {
            // Connection
            if (value == BuddyListSocialRelation.TYPE_BI_CONNECTION.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_CONNECTION);
            }
            // Coworker
            else if (value == BuddyListSocialRelation.TYPE_BI_COWORKER.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_COWORKER);
            }
            // Friend
            else if (value == BuddyListSocialRelation.TYPE_BI_FRIEND.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_FRIEND);
            }
            // Romantic partner
            else if (value == BuddyListSocialRelation.TYPE_BI_ROMANTIC_PARTNER.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_ROMANTIC_PARTNER);
            }
            // Sibling
            else if (value == BuddyListSocialRelation.TYPE_BI_SIBLING.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_SIBLING);
            }
            // Unknown value
            else {
                log.error(String.format("Unknown buddy list social relation type: %d. Valid values are \"12\", \"1\"," +
                        " \"2\", \"3\", \"4\". The value can be set in portlet-ext.properties file related to the " +
                        "LIMS portlet.", value));
            }
        }

        BuddyListSocialRelation[] buddyListSocialRelations;

        // Nothing was mapped at the end.
        // This means that no relation was selected or the relation code was wrong.
        if (relationTypeSet.size() == 0) {
            // Log error
            log.error("No buddy list social relation were mapped. This means that either no social relation was " +
                    "selected or it was wrong. Since the property is required \"12 - " +
                    "Connections\" was selected as default. The value can be set in portlet-ext.properties file " +
                    "related to the LIMS portlet.");

            // Connection type is default
            buddyListSocialRelations = new BuddyListSocialRelation[]{BuddyListSocialRelation.TYPE_BI_CONNECTION};
        } else {

            // Map set to array
            buddyListSocialRelations = relationTypeSet.toArray(new BuddyListSocialRelation[relationTypeSet.size()]);
        }

        // Save to environment
        Environment.setBuddyListSocialRelations(buddyListSocialRelations);
    }

    /**
     * Updates the buddy list groups sites enabled property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListGroupSiteEnabled(PortletPreferences preferences,
                                                 Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_GROUP_SITE_ENABLED,
                String.valueOf(properties.isBuddyListGroupSiteEnabled())
        );
        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListGroupSiteEnabled(preferences);
    }

    /**
     * Sets the buddy list group site enabled property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListGroupSiteEnabled(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean buddyListGroupSiteEnabled;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListGroupSiteEnabled = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_GROUP_SITE_ENABLED,
                    String.valueOf(PortletPropertiesValues.BUDDY_LIST_GROUP_SITE_ENABLED)
            ));
        }
        // Properties
        else {
            buddyListGroupSiteEnabled = PortletPropertiesValues.BUDDY_LIST_GROUP_SITE_ENABLED;
        }

        // Save in Environment
        Environment.setBuddyListGroupSiteEnabled(buddyListGroupSiteEnabled);
    }

    /**
     * Updates the buddy list groups social enabled property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListGroupSocialEnabled(PortletPreferences preferences,
                                                   Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_GROUP_SOCIAL_ENABLED,
                String.valueOf(properties.isBuddyListGroupSocialEnabled())
        );
        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListGroupSocialEnabled(preferences);
    }

    /**
     * Sets the buddy list group social enabled property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListGroupSocialEnabled(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean buddyListGroupSocialEnabled;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListGroupSocialEnabled = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_GROUP_SOCIAL_ENABLED,
                    String.valueOf(PortletPropertiesValues.BUDDY_LIST_GROUP_SOCIAL_ENABLED)
            ));
        }
        // Properties
        else {
            buddyListGroupSocialEnabled = PortletPropertiesValues.BUDDY_LIST_GROUP_SOCIAL_ENABLED;
        }

        // Save in Environment
        Environment.setBuddyListGroupSocialEnabled(buddyListGroupSocialEnabled);
    }

    /**
     * Updates the buddy list groups user enabled property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListGroupUserEnabled(PortletPreferences preferences,
                                                 Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_GROUP_USER_ENABLED,
                String.valueOf(properties.isBuddyListGroupUserEnabled())
        );
        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListGroupUserEnabled(preferences);
    }

    /**
     * Sets the buddy list group user enabled property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListGroupUserEnabled(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean buddyListGroupUserEnabled;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListGroupUserEnabled = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_GROUP_USER_ENABLED,
                    String.valueOf(PortletPropertiesValues.BUDDY_LIST_GROUP_USER_ENABLED)
            ));
        }
        // Properties
        else {
            buddyListGroupUserEnabled = PortletPropertiesValues.BUDDY_LIST_GROUP_USER_ENABLED;
        }

        // Save in Environment
        Environment.setBuddyListGroupUserEnabled(buddyListGroupUserEnabled);
    }

    /**
     * Updates the buddy list deactivated user property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListIgnoreDeactivatedUser(PortletPreferences preferences,
                                                      Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEACTIVATED_USER,
                String.valueOf(properties.getBuddyListIgnoreDeactivatedUser())
        );
        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListIgnoreDeactivatedUser(preferences);
    }

    /**
     * Sets the buddy list ignore deactivated user property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListIgnoreDeactivatedUser(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean buddyListIgnoreDeactivatedUser;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListIgnoreDeactivatedUser = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEACTIVATED_USER,
                    String.valueOf(PortletPropertiesValues.BUDDY_LIST_IGNORE_DEACTIVATED_USER)
            ));
        }
        // Properties
        else {
            buddyListIgnoreDeactivatedUser = PortletPropertiesValues.BUDDY_LIST_IGNORE_DEACTIVATED_USER;
        }

        // Save in Environment
        Environment.setBuddyListIgnoreDeactivatedUser(buddyListIgnoreDeactivatedUser);
    }

    /**
     * Sets the buddy list max buddies property
     */
    private void setupBuddyListMaxBuddies() {

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.BUDDY_LIST_MAX_BUDDIES,
                PortletPropertiesKeys.BUDDY_LIST_MAX_BUDDIES,
                BUDDY_LIST_MAX_BUDDIES_MIN,
                BUDDY_LIST_MAX_BUDDIES_MAX,
                BUDDY_LIST_MAX_BUDDIES_DEFAULT
        );

        // Save in Environment
        Environment.setBuddyListMaxBuddies(value);
    }

    /**
     * Sets the buddy list max search property
     */
    private void setupBuddyListMaxSearch() {

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.BUDDY_LIST_MAX_SEARCH,
                PortletPropertiesKeys.BUDDY_LIST_MAX_SEARCH,
                BUDDY_LIST_MAX_SEARCH_MIN,
                BUDDY_LIST_MAX_SEARCH_MAX,
                BUDDY_LIST_MAX_SEARCH_DEFAULT
        );

        // Save in Environment
        Environment.setBuddyListMaxSearch(value);
    }

    /**
     * Sets the conversation list max messages property
     */
    private void setupConversationListMaxMessages() {

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.CONVERSATION_LIST_MAX_MESSAGES,
                PortletPropertiesKeys.CONVERSATION_LIST_MAX_MESSAGES,
                CONVERSATION_LIST_MAX_MESSAGES_MIN,
                CONVERSATION_LIST_MAX_MESSAGES_MAX,
                CONVERSATION_LIST_MAX_MESSAGES_DEFAULT
        );

        // Save in Environment
        Environment.setConversationListMaxMessages(value);
    }

    /**
     * Sets the conversation list max conversations property
     */
    private void setupConversationFeedMaxConversations() {

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.CONVERSATION_FEED_MAX_CONVERSATIONS,
                PortletPropertiesKeys.CONVERSATION_FEED_MAX_CONVERSATIONS,
                CONVERSATION_FEED_MAX_CONVERSATIONS_MIN,
                CONVERSATION_FEED_MAX_CONVERSATIONS_MAX,
                CONVERSATION_FEED_MAX_CONVERSATIONS_DEFAULT
        );

        Environment.setConversationFeedMaxConversations(value);
    }

    /**
     * Sets connection lost threshold value
     */
    private void setupConnectionLostThreshold() {

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.CONNECTION_LOST_THRESHOLD,
                PortletPropertiesKeys.CONNECTION_LOST_THRESHOLD,
                CONNECTION_LOST_THRESHOLD_MIN,
                CONNECTION_LOST_THRESHOLD_MAX,
                CONNECTION_LOST_THRESHOLD_DEFAULT
        );

        // Set url properties
        Environment.setConnectionLostThreshold(value);
    }

    /**
     * Sets polling slow down threshold value
     */
    private void setupPollingSlowDownThreshold() {

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.POLLING_SLOW_DOWN_THRESHOLD,
                PortletPropertiesKeys.POLLING_SLOW_DOWN_THRESHOLD,
                POLLING_SLOW_DOWN_THRESHOLD_MIN,
                POLLING_SLOW_DOWN_THRESHOLD_MAX,
                POLLING_SLOW_DOWN_THRESHOLD_DEFAULT
        );

        // Set url properties
        Environment.setPollingSlowDownThreshold(value);
    }

    /**
     * Updates buddy list site excludes property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListSiteExcludes(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValues(
                PortletPropertiesKeys.BUDDY_LIST_SITE_EXCLUDES,
                properties.getBuddyListSiteExcludes()
        );

        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListSiteExcludes(preferences);
    }

    /**
     * Sets the buddy list site excludes property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListSiteExcludes(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        String[] buddyListSiteExcludes;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListSiteExcludes = preferences.getValues(
                    PortletPropertiesKeys.BUDDY_LIST_SITE_EXCLUDES,
                    PortletPropertiesValues.BUDDY_LIST_SITE_EXCLUDES
            );
        }
        // Properties
        else {
            buddyListSiteExcludes = PortletPropertiesValues.BUDDY_LIST_SITE_EXCLUDES;
        }

        // Save in Environment
        Environment.setBuddyListSiteExcludes(buddyListSiteExcludes);
    }

    /**
     * Updates buddy list group excludes property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListGroupExcludes(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValues(
                PortletPropertiesKeys.BUDDY_LIST_GROUP_EXCLUDES,
                properties.getBuddyListGroupExcludes()
        );

        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListGroupExcludes(preferences);
    }

    /**
     * Sets the buddy list group excludes property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListGroupExcludes(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        String[] buddyListGroupExcludes;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListGroupExcludes = preferences.getValues(
                    PortletPropertiesKeys.BUDDY_LIST_GROUP_EXCLUDES,
                    PortletPropertiesValues.BUDDY_LIST_GROUP_EXCLUDES
            );
        }
        // Properties
        else {
            buddyListGroupExcludes = PortletPropertiesValues.BUDDY_LIST_GROUP_EXCLUDES;
        }

        // Save in Environment
        Environment.setBuddyListGroupExcludes(buddyListGroupExcludes);
    }


    /**
     * Updates mobile user scalable disabled property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateMobileUserScalableDisabled(PortletPreferences preferences,
                                                  Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.MOBILE_USER_SCALABLE_DISABLED,
                String.valueOf(properties.getMobileUserScalableDisabled())
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupMobileUserScalableDisabled(preferences);
    }

    /**
     * Sets the mobile user scalable disabled property
     *
     * @param preferences PortletPreferences
     */
    private void setupMobileUserScalableDisabled(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean mobileUserScalableDisabled;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            mobileUserScalableDisabled = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.MOBILE_USER_SCALABLE_DISABLED,
                    String.valueOf(PortletPropertiesValues.MOBILE_USER_SCALABLE_DISABLED)
            ));
        }
        // Properties
        else {
            mobileUserScalableDisabled = PortletPropertiesValues.MOBILE_USER_SCALABLE_DISABLED;
        }

        // Save in Environment
        Environment.setMobileUserScalableDisabled(mobileUserScalableDisabled);
    }

    /**
     * Updates jabber enabled property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateJabberEnabled(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.JABBER_ENABLED,
                String.valueOf(properties.getJabberEnabled())
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupJabberEnabled(preferences);
    }

    /**
     * Sets the jabber enabled property
     *
     * @param preferences PortletPreferences
     */
    private void setupJabberEnabled(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean jabberEnabled;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            jabberEnabled = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.JABBER_ENABLED,
                    String.valueOf(PortletPropertiesValues.JABBER_ENABLED)
            ));
        }
        // Properties
        else {
            jabberEnabled = PortletPropertiesValues.JABBER_ENABLED;
        }

        // Save in Environment
        Environment.setJabberEnabled(jabberEnabled);
    }

    /**
     * Updates jabber import user enabled property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateJabberImportUserEnabled(PortletPreferences preferences,
                                               Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.JABBER_IMPORT_USER_ENABLED,
                String.valueOf(properties.getJabberImportUserEnabled())
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupJabberImportUserEnabled(preferences);
    }

    /**
     * Sets the jabber security enabled property
     *
     * @param preferences PortletPreferences
     */
    private void setupJabberSecurityEnabled(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean jabberSecurityEnabled;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            jabberSecurityEnabled = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.JABBER_SECURITY_ENABLED,
                    String.valueOf(PortletPropertiesValues.JABBER_SECURITY_ENABLED)
            ));
        }
        // Properties
        else {
            jabberSecurityEnabled = PortletPropertiesValues.JABBER_SECURITY_ENABLED;
        }

        // Save in Environment
        Environment.setJabberSecurityEnabled(jabberSecurityEnabled);
    }

    /**
     * Updates jabber security enabled property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateJabberSecurityEnabled(PortletPreferences preferences,
                                             Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.JABBER_SECURITY_ENABLED,
                String.valueOf(properties.getJabberSecurityEnabled())
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupJabberSecurityEnabled(preferences);
    }

    /**
     * Sets the jabber import user enabled property
     *
     * @param preferences PortletPreferences
     */
    private void setupJabberImportUserEnabled(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean jabberImportUserEnabled;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            jabberImportUserEnabled = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.JABBER_IMPORT_USER_ENABLED,
                    String.valueOf(PortletPropertiesValues.JABBER_IMPORT_USER_ENABLED)
            ));
        }
        // Properties
        else {
            jabberImportUserEnabled = PortletPropertiesValues.JABBER_IMPORT_USER_ENABLED;
        }

        // Save in Environment
        Environment.setJabberImportUserEnabled(jabberImportUserEnabled);
    }

    /**
     * Updates jabber host property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateJabberHost(PortletPreferences preferences,
                                  Properties properties) throws Exception {

        // Validate the value
        String value = validateDefaultString(
                properties.getJabberHost(),
                PortletPropertiesKeys.JABBER_HOST,
                JABBER_HOST_DEFAULT
        );

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.JABBER_HOST, value
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupJabberHost(preferences);
    }

    /**
     * Sets the jabber host property
     *
     * @param preferences PortletPreferences
     */
    private void setupJabberHost(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        // Validate the value
        String value = validateDefaultString(
                PortletPropertiesValues.JABBER_HOST,
                PortletPropertiesKeys.JABBER_HOST,
                JABBER_HOST_DEFAULT
        );

        String jabberHost;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            jabberHost = preferences.getValue(
                    PortletPropertiesKeys.JABBER_HOST, value
            );
        }
        // Properties
        else {
            jabberHost = value;
        }

        // Save in Environment
        Environment.setJabberHost(jabberHost);
    }

    /**
     * Updates jabber port property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateJabberPort(PortletPreferences preferences,
                                  Properties properties) throws Exception {

        // Get the value from properties
        Integer value = validateValueScope(
                properties.getJabberPort(),
                PortletPropertiesKeys.JABBER_PORT,
                JABBER_PORT_MIN,
                JABBER_PORT_MAX,
                JABBER_PORT_DEFAULT
        );

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.JABBER_PORT,
                String.valueOf(value)
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupJabberPort(preferences);
    }

    /**
     * Sets the jabber port property
     *
     * @param preferences PortletPreferences
     */
    private void setupJabberPort(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.JABBER_PORT,
                PortletPropertiesKeys.JABBER_PORT,
                JABBER_PORT_MIN,
                JABBER_PORT_MAX,
                JABBER_PORT_DEFAULT
        );

        Integer jabberPort;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            jabberPort = Integer.parseInt(preferences.getValue(
                    PortletPropertiesKeys.JABBER_PORT,
                    String.valueOf(value)
            ));
        }
        // Properties
        else {
            jabberPort = value;
        }

        // Save in Environment
        Environment.setJabberPort(jabberPort);
    }

    /**
     * Updates jabber service name property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateJabberServiceName(PortletPreferences preferences,
                                         Properties properties) throws Exception {

        // Validate the value
        String value = validateDefaultString(
                properties.getJabberServiceName(),
                PortletPropertiesKeys.JABBER_SERVICE_NAME,
                JABBER_SERVICE_NAME_DEFAULT
        );

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.JABBER_SERVICE_NAME, value
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupJabberServiceName(preferences);
    }

    /**
     * Sets the jabber service name property
     *
     * @param preferences PortletPreferences
     */
    private void setupJabberServiceName(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        // Get the value from properties
        String value = validateDefaultString(
                PortletPropertiesValues.JABBER_SERVICE_NAME,
                PortletPropertiesKeys.JABBER_SERVICE_NAME,
                JABBER_SERVICE_NAME_DEFAULT
        );

        String jabberServiceName;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            jabberServiceName = preferences.getValue(
                    PortletPropertiesKeys.JABBER_SERVICE_NAME, value
            );
        }
        // Properties
        else {
            jabberServiceName = value;
        }

        // Save in Environment
        Environment.setJabberServiceName(jabberServiceName);
    }

    /**
     * Updates jabber resource property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateJabberResource(PortletPreferences preferences,
                                      Properties properties) throws Exception {

        // Validate the value
        String value = validateDefaultString(
                properties.getJabberResource(),
                PortletPropertiesKeys.JABBER_RESOURCE,
                JABBER_RESOURCE_DEFAULT
        );


        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.JABBER_RESOURCE, value
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupJabberResource(preferences);
    }

    /**
     * Sets the jabber resource property
     *
     * @param preferences PortletPreferences
     */
    private void setupJabberResource(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        // Get the value from properties
        String value = validateDefaultString(
                PortletPropertiesValues.JABBER_RESOURCE,
                PortletPropertiesKeys.JABBER_RESOURCE,
                JABBER_RESOURCE_DEFAULT
        );

        String jabberResource;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            jabberResource = preferences.getValue(PortletPropertiesKeys.JABBER_RESOURCE, value);
        }
        // Properties
        else {
            jabberResource = value;
        }

        // Save in Environment
        Environment.setJabberResource(jabberResource);
    }

    /**
     * Sets the jabber resource priority property
     */
    private void setupJabberResourcePriority() {

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.JABBER_RESOURCE_PRIORITY,
                PortletPropertiesKeys.JABBER_RESOURCE_PRIORITY,
                JABBER_RESOURCE_PRIORITY_MIN,
                JABBER_RESOURCE_PRIORITY_MAX,
                JABBER_RESOURCE_PRIORITY_DEFAULT
        );

        // Save in Environment
        Environment.setJabberResourcePriority(value);
    }

    /**
     * Updates jabber shared secret enabled property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateJabberSharedSecretEnabled(PortletPreferences preferences,
                                                 Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.JABBER_SHARED_SECRET_ENABLED,
                String.valueOf(properties.getJabberSharedSecretEnabled())
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupJabberSharedSecretEnabled(preferences);
    }

    /**
     * Sets jabber shared secret enabled property
     *
     * @param preferences PortletPreferences
     */
    private void setupJabberSharedSecretEnabled(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean jabberSharedSecretEnabled;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            jabberSharedSecretEnabled = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.JABBER_SHARED_SECRET_ENABLED,
                    String.valueOf(PortletPropertiesValues.JABBER_SHARED_SECRET_ENABLED)
            ));
        }
        // Properties
        else {
            jabberSharedSecretEnabled = PortletPropertiesValues.JABBER_SHARED_SECRET_ENABLED;
        }

        // Save in Environment
        Environment.setJabberSharedSecretEnabled(jabberSharedSecretEnabled);
    }

    /**
     * Updates jabber shared secret property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateJabberSharedSecret(PortletPreferences preferences,
                                          Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.JABBER_SHARED_SECRET,
                properties.getJabberSharedSecret()
        );

        // Persist
        preferences.store();

        // Save in Environment
        setupJabberSharedSecret(preferences);
    }

    /**
     * Sets the jabber shared secret property
     *
     * @param preferences PortletPreferences
     */
    private void setupJabberSharedSecret(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        String jabberSharedSecret;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            jabberSharedSecret = preferences.getValue(
                    PortletPropertiesKeys.JABBER_SHARED_SECRET,
                    PortletPropertiesValues.JABBER_SHARED_SECRET
            );
        }
        // Properties
        else {
            jabberSharedSecret = PortletPropertiesValues.JABBER_SHARED_SECRET;
        }

        // Save in environment
        Environment.setJabberSharedSecret(jabberSharedSecret);
    }

    /**
     * Updates IPC enabled property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateIPCEnabled(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.IPC_ENABLED,
                String.valueOf(properties.getIpcEnabled())
        );
        // Persist
        preferences.store();

        // Setup Environment
        setupIPCEnabled(preferences);
    }

    /**
     * Sets the IPC enabled property
     *
     * @param preferences PortletPreferences
     */
    private void setupIPCEnabled(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean ipcEnabled;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            ipcEnabled = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.IPC_ENABLED,
                    String.valueOf(PortletPropertiesValues.IPC_ENABLED)
            ));
        }
        // Properties
        else {
            ipcEnabled = PortletPropertiesValues.IPC_ENABLED;
        }

        // Save in Environment
        Environment.setIpcEnabled(ipcEnabled);
    }

    /**
     * Sets Browser Notifications Require Interaction property
     */
    private void setupBrowserNotificationsRequireInteraction() {

        // Set url properties
        Environment.setBrowserNotificationsRequireInteraction(
                PortletPropertiesValues.BROWSER_NOTIFICATIONS_REQUEST_INTERACTION
        );
    }

    /**
     * Setups the product key
     */
    private void setupProductKey() {
        Environment.setProductKey(PortletPropertiesValues.PRODUCT_KEY);
    }

    /**
     * Setups instance secret. Generates a new one if no instance secret was created before
     *
     * @param preferences PortletPreferences
     */
    private void setupInstanceSecret(PortletPreferences preferences) {
        // Take the value from preferences
        String instanceSecret = preferences.getValue(PortletPropertiesKeys.INSTANCE_SECRET, "");

        // No secret set yet
        if (instanceSecret.isEmpty()) {
            // Generate new 32 letters instance secret
            instanceSecret = new BigInteger(130, new SecureRandom()).toString(32);

            try {
                // Set the value in portlet preferences
                preferences.setValue(PortletPropertiesKeys.INSTANCE_SECRET, instanceSecret);
                // Persist
                preferences.store();
            }
            // Value is read only, this shouldn't normally happen
            catch (Exception e) {
                log.error(e);
                // Temporally set to unknown
                instanceSecret = "unknown";
            }
        }

        // Set the instance secret
        Environment.setInstanceSecret(instanceSecret);
    }

    /**
     * Sets url related properties
     */
    private void setupUrlProperties() {

        // Set url properties
        Environment.setUrlHelp(PortletPropertiesValues.URL_HELP);
        Environment.setUrlJabberHelp(PortletPropertiesValues.URL_JABBER_HELP);
        Environment.setUrlIpcHelp(PortletPropertiesValues.URL_IPC_HELP);
        Environment.setUrlSynchronizationHelp(PortletPropertiesValues.URL_SYNCHRONIZATION_HELP);
        Environment.setUrlUnsupportedBrowser(PortletPropertiesValues.URL_UNSUPPORTED_BROWSER);
    }

    /**
     * Validates the value if it's in a proper scope. Returns the default
     * value if the provided value is not in the scope
     *
     * @param value        that should be examined
     * @param name         of the property that is represented by the value
     * @param min          scope minimum
     * @param max          scope maximum
     * @param defaultValue which is returned if the value is out of scope
     * @return validated value
     */
    private Integer validateValueScope(Integer value, String name, Integer min, Integer max, Integer defaultValue) {
        // Check the value scope
        if (value < min || value > max) {
            // Log
            if (log.isInfoEnabled()) {
                log.info(String.format(
                        "The value of %s property is out of scope: %d. Value should be between %d - %d. Setting" +
                                " the %s value to default: %d. To get rid of the message check the" +
                                " portlet.properties file and update the value.",
                        name, value, min, max, name, defaultValue));
            }

            // Return default
            return defaultValue;
        }

        // Value is ok
        return value;
    }

    /**
     * Validates the value. Returns the default value if the provided value is empty or null.
     *
     * @param value        that is examined
     * @param name         of the property that is represented by the value
     * @param defaultValue which is returned if the value is not set
     * @return validated value
     */
    private String validateDefaultString(String value, String name, String defaultValue) {
        // Check the value
        if (value == null || value.length() == 0) {
            // Log
            if (log.isInfoEnabled()) {
                log.info(String.format(
                        "The value of %s property is empty. Setting the %s value to default: %s. To get rid of the" +
                                " message check the portlet.properties file and update the value.",
                        name, name, defaultValue
                ));
            }

            // Return default
            return defaultValue;
        }

        // Value is ok
        return value;
    }
}
