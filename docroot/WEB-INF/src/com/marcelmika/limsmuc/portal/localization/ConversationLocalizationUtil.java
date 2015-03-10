/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.localization;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.marcelmika.limsmuc.portal.domain.Buddy;
import com.marcelmika.limsmuc.portal.domain.Conversation;
import com.marcelmika.limsmuc.portal.domain.ConversationType;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 03/11/14
 * Time: 14:59
 */
public class ConversationLocalizationUtil {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ConversationLocalizationUtil.class);

    // Maximal size of the buddy's name the title
    private static final int BUDDY_TITLE_MAX_SIZE = 10;
    // Language keys
    private static final String MUC_TITLE_TWO_PARTICIPANTS_KEY = "panel-conversation-muc-title-two-participants";
    private static final String MUC_TITLE_OTHERS_PARTICIPANTS_KEY = "panel-conversation-muc-title-others-participants";

    /**
     * Localizes conversation properties
     *
     * @param conversation  Conversation
     * @param renderRequest RenderRequest
     * @return Conversation localized
     */
    public static Conversation localizeConversation(Conversation conversation,
                                                    RenderRequest renderRequest) {

        // Get the portlet config and locale from the request
        PortletConfig portletConfig = (PortletConfig) renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
        Locale locale = renderRequest.getLocale();

        return localizeConversation(conversation, portletConfig, locale);
    }

    /**
     * Localizes conversation properties
     *
     * @param conversation    Conversation
     * @param resourceRequest ResourceRequest
     * @return Conversation localized
     */
    public static Conversation localizeConversation(Conversation conversation,
                                                    ResourceRequest resourceRequest) {

        // Get the portlet config and locale from the request
        PortletConfig portletConfig = (PortletConfig) resourceRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
        Locale locale = resourceRequest.getLocale();

        return localizeConversation(conversation, portletConfig, locale);
    }

    /**
     * Localizes a list of conversations
     *
     * @param conversations list of conversations
     * @param renderRequest RenderRequest
     * @return localized list of conversations
     */
    public static List<Conversation> localizeConversationList(List<Conversation> conversations,
                                                              RenderRequest renderRequest) {
        List<Conversation> localizedList = new LinkedList<Conversation>();

        for (Conversation conversation : conversations) {
            localizedList.add(localizeConversation(conversation, renderRequest));
        }

        return localizedList;
    }

    /**
     * Localizes a list of conversations
     *
     * @param conversations   list of conversations
     * @param resourceRequest ResourceRequest
     * @return localized list of conversations
     */
    public static List<Conversation> localizeConversationList(List<Conversation> conversations,
                                                              ResourceRequest resourceRequest) {
        List<Conversation> localizedList = new LinkedList<Conversation>();

        for (Conversation conversation : conversations) {
            localizedList.add(localizeConversation(conversation, resourceRequest));
        }

        return localizedList;
    }

    /**
     * Localizes conversation
     *
     * @param conversation  Conversation
     * @param portletConfig PortletConfig
     * @param locale        Locale
     * @return localized conversation
     */
    private static Conversation localizeConversation(Conversation conversation,
                                                     PortletConfig portletConfig,
                                                     Locale locale) {

        // Neither of these can be null
        if (portletConfig == null || locale == null) {
            return conversation;
        }

        String title = "-"; // Default title

        // Current user needs to be filtered of the list of participants
        List<Buddy> filteredParticipants = filterParticipants(conversation.getParticipants(), conversation.getBuddy());

        // If the conversation has no participants return the current user
        if (filteredParticipants.size() == 0) {
            title = conversation.getBuddy().getFullName();
        }
        // Single user conversation title
        else if (conversation.getConversationType() == ConversationType.SINGLE_USER) {
            title = generateSUCTitle(filteredParticipants);
        }
        // Multi user conversation title
        else if (conversation.getConversationType() == ConversationType.MULTI_USER) {
            title = generateMUCTitle(filteredParticipants, conversation.getBuddy(), portletConfig, locale);
        }

        // Set the localized conversation title
        conversation.setTitle(title);

        return conversation;
    }

    /**
     * Generates single user chat title from the list of participants
     *
     * @param participants a list of participants
     * @return SUC title or "-" if an empty participants list was passed
     */
    private static String generateSUCTitle(List<Buddy> participants) {
        if (participants.size() == 0) {
            return "-";
        }

        return participants.get(0).getFullName();
    }

    /**
     * Generates multi user chat title from the list of participants
     *
     * @param participants a list of participants
     * @param buddy        used when no participants are in the conversation
     * @return MUC title of "-" if an empty list of participants was passed
     */
    private static String generateMUCTitle(List<Buddy> participants,
                                           Buddy buddy,
                                           PortletConfig portletConfig,
                                           Locale locale) {

        String title;

        // No participants means that the user is left alone in the conversation
        if (participants.size() == 0) {
            title = buddy.getFullName();
        }

        // One participants in the MUC conversation means that
        // the other participants have left
        else if (participants.size() == 1) {
            title = participants.get(0).getFullName();
        }

        // We have exactly two participants
        else if (participants.size() == 2) {

            Object[] titles = new Object[2];
            titles[0] = generateBuddyTitleName(participants.get(0));
            titles[1] = generateBuddyTitleName(participants.get(1));

            title = LanguageUtil.format(portletConfig, locale, MUC_TITLE_TWO_PARTICIPANTS_KEY, titles);
        }
        // We have have more than two participants
        else {

            Object[] titles = new Object[2];
            titles[0] = generateBuddyTitleName(participants.get(0));
            titles[1] = (participants.size() - 1); // Since participants includes also you

            title = LanguageUtil.format(portletConfig, locale, MUC_TITLE_OTHERS_PARTICIPANTS_KEY, titles);
        }

        return title;
    }


    /**
     * Filter buddy given in parameter
     *
     * @param participants a list of participants
     * @param buddy        that will be filtered out
     * @return list of participants
     */
    private static List<Buddy> filterParticipants(List<Buddy> participants, Buddy buddy) {

        List<Buddy> filteredParticipants = new LinkedList<Buddy>();

        for (Buddy participant : participants) {
            // Single user conversation has two participants. Title is "the other" participant than myself.
            if (!participant.getBuddyId().equals(buddy.getBuddyId())) {
                filteredParticipants.add(participant);
            }
        }

        return filteredParticipants;
    }

    /**
     * Generates title from buddy
     *
     * @param buddy Buddy
     * @return title
     */
    private static String generateBuddyTitleName(Buddy buddy) {
        // Default dash if no name is set
        String name = StringPool.QUESTION;

        // If the user has first name, use that
        if (buddy.getFirstName() != null) {
            name = buddy.getFirstName();
        }
        // If not use the last name
        else if (buddy.getLastName() != null) {
            name = buddy.getLastName();
        }
        // If none of those were set, use the full name
        else if (buddy.getFullName() != null) {
            name = buddy.getFullName();
        }

        // Limit the maximal size of the message to 10
        if (name.length() > BUDDY_TITLE_MAX_SIZE) {
            name = name.substring(BUDDY_TITLE_MAX_SIZE);
        }

        return name;
    }

}
