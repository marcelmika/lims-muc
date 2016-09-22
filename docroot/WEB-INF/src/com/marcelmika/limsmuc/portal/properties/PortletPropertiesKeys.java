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
    String VERSION = "version";

    // Properties source
    String PROPERTIES_SOURCE = "properties.source";

    // Excluded
    String EXCLUDED_SITES = "excluded.sites";

    // Buddy list
    String BUDDY_LIST_STRATEGY = "buddy.list.strategy";
    String BUDDY_LIST_GROUP_SITE_ENABLED = "buddy.list.group.site.enabled";
    String BUDDY_LIST_GROUP_SOCIAL_ENABLED = "buddy.list.group.social.enabled";
    String BUDDY_LIST_GROUP_USER_ENABLED = "buddy.list.group.user.enabled";

    String BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES = "buddy.list.allowed.social.relation.types";
    String BUDDY_LIST_MAX_BUDDIES = "buddy.list.max.buddies";
    String BUDDY_LIST_MAX_SEARCH = "buddy.list.max.search";
    String BUDDY_LIST_SITE_EXCLUDES = "buddy.list.site.excludes";
    String BUDDY_LIST_GROUP_EXCLUDES = "buddy.list.group.excludes";
    String BUDDY_LIST_IGNORE_DEACTIVATED_USER = "buddy.list.ignore.deactivated.user";

    // Connection
    String CONNECTION_LOST_THRESHOLD = "connection.lost.threshold";

    // Conversation
    String CONVERSATION_LIST_MAX_MESSAGES = "conversation.list.max.messages";

    // Conversation Feed
    String CONVERSATION_FEED_MAX_CONVERSATIONS = "conversation.feed.max.conversations";

    // Websocket
    String WEBSOCKET_SERVER_HOSTNAME = "websocket.server.hostname";
    String WEBSOCKET_SERVER_PORT = "websocket.server.port";
    String WEBSOCKET_ENDPOINT = "websocket.endpoint";
    String WEBSOCKET_CLIENT_PORT = "websocket.client.port";
    String WEBSOCKET_SECURED = "websocket.secured";

    // Polling
    String POLLING_SLOW_DOWN_THRESHOLD = "polling.slow.down.threshold";

    // Jabber
    String JABBER_ENABLED = "jabber.enabled";
    String JABBER_SECURITY_ENABLED = "jabber.security.enabled";
    String JABBER_IMPORT_USER_ENABLED = "jabber.import.user.enabled";
    String JABBER_HOST = "jabber.host";
    String JABBER_PORT = "jabber.port";
    String JABBER_SERVICE_NAME = "jabber.service.name";
    String JABBER_RESOURCE = "jabber.resource";
    String JABBER_RESOURCE_PRIORITY = "jabber.resource.priority";
    String JABBER_SHARED_SECRET_ENABLED = "jabber.shared.secret.enabled";
    String JABBER_SHARED_SECRET = "jabber.shared.secret";

    // Custom license
    String INSTANCE_SECRET = "instance.secret";
    String PRODUCT_KEY = "product.key";

    // IPC
    String IPC_ENABLED = "ipc.enabled";

    // Browser Notifications
    String BROWSER_NOTIFICATIONS_REQUEST_INTERACTION = "browser.notifications.require.interaction";

    // Mobile
    String MOBILE_USER_SCALABLE_DISABLED = "mobile.user.scalable.disabled";

    // URLs
    String URL_HELP = "url.help";
    String URL_JABBER_HELP = "url.jabber.help";
    String URL_IPC_HELP = "url.ipc.help";
    String URL_SYNCHRONIZATION_HELP = "url.synchronization.help";
    String URL_UNSUPPORTED_BROWSER = "url.unsupported.browser";
}