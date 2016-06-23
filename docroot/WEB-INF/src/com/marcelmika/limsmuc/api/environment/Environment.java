/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.environment;

/**
 * Environment contains all settings and properties related to the portlet
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/3/14
 * Time: 11:41 AM
 */
public class Environment {

    // Environment properties
    private static PropertiesSource propertiesSource = PropertiesSource.PREFERENCES;
    private static String[] excludedSites = new String[]{};
    private static BuddyListStrategy buddyListStrategy = BuddyListStrategy.ALL;
    private static BuddyListSocialRelation[] buddyListSocialRelations = new BuddyListSocialRelation[]{};
    private static Boolean buddyListGroupSiteEnabled = false;
    private static Boolean buddyListGroupSocialEnabled = false;
    private static Boolean buddyListGroupUserEnabled = false;
    private static Boolean buddyListIgnoreDeactivatedUser = false;
    private static Integer buddyListMaxBuddies = 0;
    private static Integer buddyListMaxSearch = 0;
    private static Integer connectionLostThreshold = 0;
    private static Integer conversationListMaxMessages = 0;
    private static Integer conversationFeedMaxConversations = 0;
    private static String[] buddyListSiteExcludes = new String[]{};
    private static String[] buddyListGroupExcludes = new String[]{};
    private static Integer pollingSlowDownThreshold = 0;
    private static Boolean jabberEnabled = false;
    private static Boolean jabberSecurityEnabled = true;
    private static Boolean jabberImportUserEnabled = false;
    private static String jabberHost = "";
    private static Integer jabberPort = 0;
    private static String jabberServiceName = "";
    private static String jabberResource = "";
    private static Integer jabberResourcePriority = 0;
    private static Boolean jabberSharedSecretEnabled = false;
    private static String jabberSharedSecret = "";
    private static Boolean ipcEnabled = false;
    private static Boolean browserNotificationsRequireInteraction = false;
    private static Boolean mobileUserScalableDisabled = false;
    private static String urlHelp = "";
    private static String urlUnsupportedBrowser = "";
    private static String urlJabberHelp = "";
    private static String urlIpcHelp = "";
    private static String urlSynchronizationHelp = "";
    private static String instanceSecret = "";
    private static String productKey = "";

    /**
     * Enum for properties source
     */
    public enum PropertiesSource {

        /**
         * Properties are loaded from the PortletPreferences
         */
        PREFERENCES,
        /**
         * Properties are loaded from the portal.properties file
         */
        PROPERTIES
    }

    /**
     * Returns source of properties
     *
     * @return PropertiesSource
     */
    public static PropertiesSource getPropertiesSource() {
        return propertiesSource;
    }

    /**
     * Set properties source
     *
     * @param propertiesSource PropertiesSource
     */
    public static void setPropertiesSource(PropertiesSource propertiesSource) {
        Environment.propertiesSource = propertiesSource;
    }

    /**
     * Returns an array of sites names where the portlet shouldn't be displayed
     *
     * @return String[]
     */
    public static String[] getExcludedSites() {
        return excludedSites;
    }

    /**
     * Sets the excluded sites property
     *
     * @param excludedSites String[]
     */
    public static void setExcludedSites(String[] excludedSites) {
        Environment.excludedSites = excludedSites;
    }

    /**
     * Enum for buddy list strategy
     */
    public enum BuddyListStrategy {

        /**
         * All buddies in the system
         */
        ALL("all"),

        /**
         * Grouped buddies (@see BuddyListGroups)
         */
        GROUPS("groups"),

        /**
         * Buddies loaded from jabber
         */
        JABBER("jabber");


        // String description of relation type
        private String description;

        /**
         * Private constructor
         *
         * @param description string description of the list strategy
         */
        BuddyListStrategy(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Returns strategy for buddy list generation
     *
     * @return BuddyListStrategy
     */
    public static BuddyListStrategy getBuddyListStrategy() {
        return buddyListStrategy;
    }

    /**
     * Sets the buddy list strategy property
     *
     * @param buddyListStrategy BuddyListStrategy
     */
    public static void setBuddyListStrategy(BuddyListStrategy buddyListStrategy) {
        Environment.buddyListStrategy = buddyListStrategy;
    }

    /**
     * Enum for buddy list groups
     */
    public enum BuddyListGroup {

        /**
         * Buddies related to the sites where the users participates
         */
        SITE("site"),

        /**
         * Buddies listed based on the social relations
         */
        SOCIAL("social"),

        /**
         * Buddies shown based on the user groups where the user belongs
         */
        USER("user");

        // String description of enum
        private String description;

        /**
         * Private constructor
         *
         * @param description string description of enum
         */
        BuddyListGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Returns true if the buddy list site group is enabled
     *
     * @return Boolean
     */
    public static Boolean isBuddyListGroupSiteEnabled() {
        return buddyListGroupSiteEnabled;
    }

    /**
     * Set the buddy list group site enabled property
     *
     * @param buddyListGroupSiteEnabled Boolean
     */
    public static void setBuddyListGroupSiteEnabled(Boolean buddyListGroupSiteEnabled) {
        Environment.buddyListGroupSiteEnabled = buddyListGroupSiteEnabled;
    }

    /**
     * Returns true if the buddy list social group is enabled
     *
     * @return Boolean
     */
    public static Boolean isBuddyListGroupSocialEnabled() {
        return buddyListGroupSocialEnabled;
    }

    /**
     * Set the buddy list groups social enabled property
     *
     * @param buddyListGroupSocialEnabled Boolean
     */
    public static void setBuddyListGroupSocialEnabled(Boolean buddyListGroupSocialEnabled) {
        Environment.buddyListGroupSocialEnabled = buddyListGroupSocialEnabled;
    }

    /**
     * Returns true if the buddy list user group is enabled
     *
     * @return Boolean
     */
    public static Boolean isBuddyListGroupUserEnabled() {
        return buddyListGroupUserEnabled;
    }

    /**
     * Set the buddy list user group enabled property
     *
     * @param buddyListGroupUserEnabled Boolean
     */
    public static void setBuddyListGroupUserEnabled(Boolean buddyListGroupUserEnabled) {
        Environment.buddyListGroupUserEnabled = buddyListGroupUserEnabled;
    }

    /**
     * Enum for buddy list social relation
     */
    public enum BuddyListSocialRelation {

        /**
         * Unknown type of relation
         */
        TYPE_BI_UNKNOWN(0, "Unknown relation"),

        /**
         * Bi-directional connection
         */
        TYPE_BI_CONNECTION(12, "Connections"),

        /**
         * Coworker relation
         */
        TYPE_BI_COWORKER(1, "Coworkers"),

        /**
         * Friend relation
         */
        TYPE_BI_FRIEND(2, "Friends"),

        /**
         * Romantic partner relations
         */
        TYPE_BI_ROMANTIC_PARTNER(3, "Romantic Partners"),

        /**
         * Sibling relation
         */
        TYPE_BI_SIBLING(4, "Siblings");


        // Integer code which uniquely describes relation
        private int code;
        // String description of relation type
        private String description;

        /**
         * Private constructor
         *
         * @param code        that uniquely represents relation type
         * @param description string description of relation
         */
        BuddyListSocialRelation(final int code, final String description) {
            this.code = code;
            this.description = description;
        }

        /**
         * Factory method which creates buddy list social relation enum from given code
         *
         * @param code that uniquely represent relation type
         * @return BuddyListSocialRelation
         */
        public static BuddyListSocialRelation fromCode(int code) {
            // Connection
            if (code == TYPE_BI_CONNECTION.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_CONNECTION;
            }
            // Coworker
            else if (code == TYPE_BI_COWORKER.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_COWORKER;
            }
            // Friend
            else if (code == TYPE_BI_FRIEND.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_FRIEND;
            }
            // Romantic Partner
            else if (code == TYPE_BI_ROMANTIC_PARTNER.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_ROMANTIC_PARTNER;
            }
            // Sibling
            else if (code == TYPE_BI_SIBLING.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_SIBLING;
            }
            // Unknown
            else {
                return BuddyListSocialRelation.TYPE_BI_UNKNOWN;
            }
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Returns an array of allowed social relation types enums
     *
     * @return BuddyListSocialRelation[]
     */
    public static BuddyListSocialRelation[] getBuddyListSocialRelations() {
        return buddyListSocialRelations;
    }

    /**
     * Sets the buddy list allowed social relations
     *
     * @param relations BuddyListSocialRelation[]
     */
    public static void setBuddyListSocialRelations(BuddyListSocialRelation[] relations) {
        Environment.buddyListSocialRelations = relations;
    }

    /**
     * Returns maximal buddies count in list
     *
     * @return int
     */
    public static int getBuddyListMaxBuddies() {
        return buddyListMaxBuddies;
    }

    /**
     * Sets the buddy list max buddies property
     *
     * @param buddyListMaxBuddies Integer
     */
    public static void setBuddyListMaxBuddies(Integer buddyListMaxBuddies) {
        Environment.buddyListMaxBuddies = buddyListMaxBuddies;
    }

    /**
     * Returns maximal number of search result in buddy list
     *
     * @return int
     */
    public static int getBuddyListMaxSearch() {
        return buddyListMaxSearch;
    }

    /**
     * Sets the buddy list max search property
     *
     * @param buddyListMaxSearch Integer
     */
    public static void setBuddyListMaxSearch(Integer buddyListMaxSearch) {
        Environment.buddyListMaxSearch = buddyListMaxSearch;
    }

    /**
     * Returns an array of sites names which should be excluded from
     * the buddy list
     *
     * @return String[]
     */
    public static String[] getBuddyListSiteExcludes() {
        return buddyListSiteExcludes;
    }

    /**
     * Sets the buddy list site excludes property
     *
     * @param buddyListSiteExcludes String[]
     */
    public static void setBuddyListSiteExcludes(String[] buddyListSiteExcludes) {
        Environment.buddyListSiteExcludes = buddyListSiteExcludes;
    }

    /**
     * Returns an array of group names which should be excluded from
     * the buddy list
     *
     * @return String[]
     */
    public static String[] getBuddyListGroupExcludes() {
        return buddyListGroupExcludes;
    }

    /**
     * Sets the buddy list group excludes property
     *
     * @param buddyListGroupExcludes String[]
     */
    public static void setBuddyListGroupExcludes(String[] buddyListGroupExcludes) {
        Environment.buddyListGroupExcludes = buddyListGroupExcludes;
    }

    /**
     * Returns true if the deactivated user should be ignored. Deactivated user is the user which has
     * the status column set to 0 in database.
     *
     * @return boolean
     */
    public static boolean getBuddyListIgnoreDeactivatedUser() {
        return buddyListIgnoreDeactivatedUser;
    }

    /**
     * Sets the buddy list ignore deactivated user property
     *
     * @param buddyListIgnoreDeactivatedUser Boolean
     */
    public static void setBuddyListIgnoreDeactivatedUser(Boolean buddyListIgnoreDeactivatedUser) {
        Environment.buddyListIgnoreDeactivatedUser = buddyListIgnoreDeactivatedUser;
    }

    /**
     * Connection lost threshold
     *
     * @return Integer
     */
    public static Integer getConnectionLostThreshold() {
        return connectionLostThreshold;
    }

    /**
     * Sets the connection lost threshold property
     *
     * @param connectionLostThreshold Integer, 0 if the automatic connection check should be switched off
     */
    public static void setConnectionLostThreshold(Integer connectionLostThreshold) {
        Environment.connectionLostThreshold = connectionLostThreshold;
    }

    /**
     * Maximal count of messages in the conversation.
     *
     * @return int
     */
    public static int getConversationListMaxMessages() {
        return conversationListMaxMessages;
    }

    /**
     * Sets the conversation list max messages property
     *
     * @param conversationListMaxMessages Integer
     */
    public static void setConversationListMaxMessages(Integer conversationListMaxMessages) {
        Environment.conversationListMaxMessages = conversationListMaxMessages;
    }

    /**
     * Maximal count of conversations
     *
     * @return int
     */
    public static int getConversationFeedMaxConversations() {
        return conversationFeedMaxConversations;
    }

    /**
     * Sets the conversation feed max conversations property
     *
     * @param conversationFeedMaxConversations Integer
     */
    public static void setConversationFeedMaxConversations(Integer conversationFeedMaxConversations) {
        Environment.conversationFeedMaxConversations = conversationFeedMaxConversations;
    }

    /**
     * Returns polling slow down threshold
     *
     * @return Integer
     */
    public static Integer getPollingSlowDownThreshold() {
        return pollingSlowDownThreshold;
    }

    /**
     * Sets polling slow down threshold property
     *
     * @param pollingSlowDownThreshold Integer
     */
    public static void setPollingSlowDownThreshold(Integer pollingSlowDownThreshold) {
        Environment.pollingSlowDownThreshold = pollingSlowDownThreshold;
    }

    /**
     * Return true if communication via jabber is enabled
     *
     * @return boolean
     */
    public static boolean isJabberEnabled() {
        return jabberEnabled;
    }

    /**
     * Sets jabber enabled property
     *
     * @param jabberEnabled Boolean
     */
    public static void setJabberEnabled(Boolean jabberEnabled) {
        Environment.jabberEnabled = jabberEnabled;
    }

    /**
     * Returns true if jabber security mode is enabled
     *
     * @return boolean
     */
    public static boolean getJabberSecurityEnabled() {
        return jabberSecurityEnabled;
    }

    /**
     * Sets jabber security enabled property
     *
     * @param jabberSecurityEnabled boolean
     */
    public static void setJabberSecurityEnabled(Boolean jabberSecurityEnabled) {
        Environment.jabberSecurityEnabled = jabberSecurityEnabled;
    }

    /**
     * Returns jabber server host property
     *
     * @return String jabber host
     */
    public static String getJabberHost() {
        return jabberHost;
    }

    /**
     * Sets jabber host property
     *
     * @param jabberHost String
     */
    public static void setJabberHost(String jabberHost) {
        Environment.jabberHost = jabberHost;
    }

    /**
     * Returns jabber server port property
     *
     * @return int jabber port
     */
    public static int getJabberPort() {
        return jabberPort;
    }

    /**
     * Sets jabber port property
     *
     * @param jabberPort Integer
     */
    public static void setJabberPort(Integer jabberPort) {
        Environment.jabberPort = jabberPort;
    }

    /**
     * Returns jabber server service name property
     *
     * @return String jabber service name
     */
    public static String getJabberServiceName() {
        return jabberServiceName;
    }

    /**
     * Sets jabber service name property
     *
     * @param jabberServiceName String
     */
    public static void setJabberServiceName(String jabberServiceName) {
        Environment.jabberServiceName = jabberServiceName;
    }

    /**
     * Returns jabber server resource property
     *
     * @return String jabber resource
     */
    public static String getJabberResource() {
        return jabberResource;
    }

    /**
     * Sets jabber resource property
     *
     * @param jabberResource String
     */
    public static void setJabberResource(String jabberResource) {
        Environment.jabberResource = jabberResource;
    }

    /**
     * Returns jabber resource priority
     *
     * @return Integer
     */
    public static Integer getJabberResourcePriority() {
        return jabberResourcePriority;
    }

    /**
     * Sets jabber resource priority property
     *
     * @param jabberResourcePriority Integer
     */
    public static void setJabberResourcePriority(Integer jabberResourcePriority) {
        Environment.jabberResourcePriority = jabberResourcePriority;
    }

    /**
     * Returns true if the Jabber Import User mechanism is enabled
     *
     * @return boolean
     */
    public static boolean isJabberImportUserEnabled() {
        return jabberImportUserEnabled;
    }

    /**
     * Sets jabber import user enabled property
     *
     * @param jabberImportUserEnabled Boolean
     */
    public static void setJabberImportUserEnabled(Boolean jabberImportUserEnabled) {
        Environment.jabberImportUserEnabled = jabberImportUserEnabled;
    }

    /**
     * Sets jabber shared secret enabled property
     *
     * @param jabberSharedSecretEnabled Boolean
     */
    public static void setJabberSharedSecretEnabled(Boolean jabberSharedSecretEnabled) {
        Environment.jabberSharedSecretEnabled = jabberSharedSecretEnabled;
    }

    /**
     * Returns true if the jabber shared secret is enabled
     *
     * @return boolean
     */
    public static boolean isJabberSharedSecretEnabled() {
        return jabberSharedSecretEnabled;
    }

    /**
     * Sets jabber shared secret property
     *
     * @param jabberSharedSecret String
     */
    public static void setJabberSharedSecret(String jabberSharedSecret) {
        Environment.jabberSharedSecret = jabberSharedSecret;
    }

    /**
     * Sets jabber shared secret property
     *
     * @return String
     */
    public static String getJabberSharedSecret() {
        return jabberSharedSecret;
    }

    /**
     * Return true if the Inter Portlet Communication is enabled
     *
     * @return Boolean
     */
    public static Boolean getIpcEnabled() {
        return ipcEnabled;
    }

    /**
     * Sets the Inter Portlet Communication enabled property
     *
     * @param ipcEnabled Boolean
     */
    public static void setIpcEnabled(Boolean ipcEnabled) {
        Environment.ipcEnabled = ipcEnabled;
    }

    /**
     * Returns true if the Browser Notifications Require Interaction is enabled
     *
     * @return Boolean
     */
    public static Boolean getBrowserNotificationsRequireInteraction() {
        return browserNotificationsRequireInteraction;
    }

    /**
     * Sets the Browser Notifications Require Interaction property
     *
     * @param browserNotificationsRequireInteraction Boolean
     */
    public static void setBrowserNotificationsRequireInteraction(Boolean browserNotificationsRequireInteraction) {
        Environment.browserNotificationsRequireInteraction = browserNotificationsRequireInteraction;
    }

    /**
     * Returns true if the Mobile User Scalable Disabled property is enabled
     *
     * @return Boolean
     */
    public static Boolean getMobileUserScalableDisabled() {
        return mobileUserScalableDisabled;
    }

    /**
     * Sets the Mobile User Scalable Disabled property
     *
     * @param mobileUserScalableDisabled Boolean
     */
    public static void setMobileUserScalableDisabled(Boolean mobileUserScalableDisabled) {
        Environment.mobileUserScalableDisabled = mobileUserScalableDisabled;
    }

    /**
     * Returns url string for the unsupported browser message
     *
     * @return String
     */
    public static String getUrlUnsupportedBrowser() {
        return urlUnsupportedBrowser;
    }

    /**
     * Sets url string for the unsupported browser
     *
     * @param urlUnsupportedBrowser String
     */
    public static void setUrlUnsupportedBrowser(String urlUnsupportedBrowser) {
        Environment.urlUnsupportedBrowser = urlUnsupportedBrowser;
    }

    /**
     * Returns url string for the help message
     *
     * @return String
     */
    public static String getUrlHelp() {
        return urlHelp;
    }

    /**
     * Set the url string for the help message
     *
     * @param urlHelp String
     */
    public static void setUrlHelp(String urlHelp) {
        Environment.urlHelp = urlHelp;
    }

    /**
     * Returns url string for the Jabber help
     *
     * @return String
     */
    public static String getUrlJabberHelp() {
        return urlJabberHelp;
    }

    /**
     * Set the url string for the Jabber help
     *
     * @param urlJabberHelp String
     */
    public static void setUrlJabberHelp(String urlJabberHelp) {
        Environment.urlJabberHelp = urlJabberHelp;
    }

    /**
     * Returns url string for the IPC help
     *
     * @return String
     */
    public static String getUrlIpcHelp() {
        return urlIpcHelp;
    }

    /**
     * Set the url string for the IPC help
     *
     * @param urlIpcHelp String
     */
    public static void setUrlIpcHelp(String urlIpcHelp) {
        Environment.urlIpcHelp = urlIpcHelp;
    }

    /**
     * Returns url string for the Synchronization help
     *
     * @return String
     */
    public static String getUrlSynchronizationHelp() {
        return urlSynchronizationHelp;
    }

    /**
     * Set the url string for the Synchronization help
     *
     * @param urlSynchronizationHelp String
     */
    public static void setUrlSynchronizationHelp(String urlSynchronizationHelp) {
        Environment.urlSynchronizationHelp = urlSynchronizationHelp;
    }

    /**
     * Returns instance secret
     *
     * @return String
     */
    public static String getInstanceSecret() {
        return instanceSecret;
    }

    /**
     * Sets instance secret
     *
     * @param instanceSecret String
     */
    public static void setInstanceSecret(String instanceSecret) {
        Environment.instanceSecret = instanceSecret;
    }

    /**
     * Returns product key
     *
     * @return
     */
    public static String getProductKey() {
        return productKey;
    }

    /**
     * Sets product key
     *
     * @param productKey
     */
    public static void setProductKey(String productKey) {
        Environment.productKey = productKey;
    }
}
