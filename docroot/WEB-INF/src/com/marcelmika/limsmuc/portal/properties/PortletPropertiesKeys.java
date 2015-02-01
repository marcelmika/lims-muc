/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.properties;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public interface PortletPropertiesKeys {

    // Version
    public static final String VERSION = "version";

    // Properties source
    public static final String PROPERTIES_SOURCE = "properties.source";

    // Excluded
    public static final String EXCLUDED_SITES = "excluded.sites";

    // Buddy list
    public static final String BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES = "buddy.list.allowed.social.relation.types";
    public static final String BUDDY_LIST_MAX_BUDDIES = "buddy.list.max.buddies";
    public static final String BUDDY_LIST_MAX_SEARCH = "buddy.list.max.search";
    public static final String BUDDY_LIST_SITE_EXCLUDES = "buddy.list.site.excludes";
    public static final String BUDDY_LIST_GROUP_EXCLUDES = "buddy.list.group.excludes";
    public static final String BUDDY_LIST_STRATEGY = "buddy.list.strategy";
    public static final String BUDDY_LIST_IGNORE_DEACTIVATED_USER = "buddy.list.ignore.deactivated.user";

    // Connection
    public static final String CONNECTION_LOST_THRESHOLD = "connection.lost.threshold";

    // Conversation
    public static final String CONVERSATION_LIST_MAX_MESSAGES = "conversation.list.max.messages";

    // Conversation Feed
    public static final String CONVERSATION_FEED_MAX_CONVERSATIONS = "conversation.feed.max.conversations";

    // Polling
    public static final String POLLING_SLOW_DOWN_THRESHOLD = "polling.slow.down.threshold";

    // Jabber
    public static final String JABBER_ENABLED = "jabber.enabled";
    public static final String JABBER_SECURITY_ENABLED = "jabber.security.enabled";
    public static final String JABBER_IMPORT_USER_ENABLED = "jabber.import.user.enabled";
    public static final String JABBER_HOST = "jabber.host";
    public static final String JABBER_PORT = "jabber.port";
    public static final String JABBER_SERVICE_NAME = "jabber.service.name";
    public static final String JABBER_RESOURCE = "jabber.resource";
    public static final String JABBER_RESOURCE_PRIORITY = "jabber.resource.priority";

    // IPC
    public static final String IPC_ENABLED = "ipc.enabled";

    // URLs
    public static final String URL_HELP = "url.help";
    public static final String URL_JABBER_HELP = "url.jabber.help";
    public static final String URL_IPC_HELP = "url.ipc.help";
    public static final String URL_SYNCHRONIZATION_HELP = "url.synchronization.help";
    public static final String URL_UNSUPPORTED_BROWSER = "url.unsupported.browser";

    // Error mode
    public static final String ERROR_MODE_ENABLED = "error.mode.enabled";
}