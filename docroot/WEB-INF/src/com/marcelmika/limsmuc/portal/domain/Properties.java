/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.domain;

import com.liferay.compat.portal.kernel.util.StringUtil;
import com.marcelmika.limsmuc.api.environment.Environment;

import java.util.Arrays;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 08/10/14
 * Time: 21:39
 */
public class Properties {

    private Environment.PropertiesSource propertiesSource;
    private Environment.BuddyListStrategy buddyListStrategy;
    private Environment.BuddyListSocialRelation[] buddyListSocialRelations;
    private Boolean buddyListIgnoreDefaultUser;
    private Boolean buddyListIgnoreDeactivatedUser;
    private Integer buddyListMaxBuddies;
    private Integer buddyListMaxSearch;
    private Integer conversationListMaxMessages;
    private Integer conversationFeedMaxConversations;
    private String[] buddyListSiteExcludes;
    private String[] buddyListGroupExcludes;
    private String urlHelp;
    private String urlUnsupportedBrowser;

    /**
     * Factory method that creates an instance of properties from the environment properties
     *
     * @return Properties
     */
    public static Properties fromEnvironment() {
        // Create new instance
        Properties properties = new Properties();

        // Map properties
        properties.propertiesSource = Environment.getPropertiesSource();
        properties.buddyListStrategy = Environment.getBuddyListStrategy();
        properties.buddyListSocialRelations = Environment.getBuddyListSocialRelations();
        properties.buddyListIgnoreDefaultUser = Environment.getBuddyListIgnoreDefaultUser();
        properties.buddyListIgnoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        properties.buddyListMaxBuddies = Environment.getBuddyListMaxBuddies();
        properties.buddyListMaxSearch = Environment.getBuddyListMaxSearch();
        properties.conversationListMaxMessages = Environment.getConversationListMaxMessages();
        properties.conversationFeedMaxConversations = Environment.getConversationFeedMaxConversations();
        properties.buddyListSiteExcludes = Environment.getBuddyListSiteExcludes();
        properties.buddyListGroupExcludes = Environment.getBuddyListGroupExcludes();
        properties.urlHelp = Environment.getUrlHelp();
        properties.urlUnsupportedBrowser = Environment.getUrlUnsupportedBrowser();

        return properties;
    }

    public Environment.PropertiesSource getPropertiesSource() {
        return propertiesSource;
    }

    public void setPropertiesSource(Environment.PropertiesSource propertiesSource) {
        this.propertiesSource = propertiesSource;
    }

    public Environment.BuddyListStrategy getBuddyListStrategy() {
        return buddyListStrategy;
    }

    public void setBuddyListStrategy(Environment.BuddyListStrategy buddyListStrategy) {
        this.buddyListStrategy = buddyListStrategy;
    }

    public Environment.BuddyListSocialRelation[] getBuddyListSocialRelations() {
        return buddyListSocialRelations;
    }

    public void setBuddyListSocialRelations(Environment.BuddyListSocialRelation[] buddyListSocialRelations) {
        this.buddyListSocialRelations = buddyListSocialRelations;
    }

    public Boolean getBuddyListIgnoreDefaultUser() {
        return buddyListIgnoreDefaultUser;
    }

    public void setBuddyListIgnoreDefaultUser(Boolean buddyListIgnoreDefaultUser) {
        this.buddyListIgnoreDefaultUser = buddyListIgnoreDefaultUser;
    }

    public Boolean getBuddyListIgnoreDeactivatedUser() {
        return buddyListIgnoreDeactivatedUser;
    }

    public void setBuddyListIgnoreDeactivatedUser(Boolean buddyListIgnoreDeactivatedUser) {
        this.buddyListIgnoreDeactivatedUser = buddyListIgnoreDeactivatedUser;
    }

    public Integer getBuddyListMaxBuddies() {
        return buddyListMaxBuddies;
    }

    public void setBuddyListMaxBuddies(Integer buddyListMaxBuddies) {
        this.buddyListMaxBuddies = buddyListMaxBuddies;
    }

    public Integer getBuddyListMaxSearch() {
        return buddyListMaxSearch;
    }

    public void setBuddyListMaxSearch(Integer buddyListMaxSearch) {
        this.buddyListMaxSearch = buddyListMaxSearch;
    }

    public Integer getConversationListMaxMessages() {
        return conversationListMaxMessages;
    }

    public void setConversationListMaxMessages(Integer conversationListMaxMessages) {
        this.conversationListMaxMessages = conversationListMaxMessages;
    }

    public Integer getConversationFeedMaxConversations() {
        return conversationFeedMaxConversations;
    }

    public void setConversationFeedMaxConversations(Integer conversationFeedMaxConversations) {
        this.conversationFeedMaxConversations = conversationFeedMaxConversations;
    }

    public String[] getBuddyListSiteExcludes() {
        return buddyListSiteExcludes;
    }

    public void setBuddyListSiteExcludes(String[] buddyListSiteExcludes) {
        this.buddyListSiteExcludes = buddyListSiteExcludes;
    }

    public String getBuddyListSiteExcludesFlatten() {
        return StringUtil.merge(buddyListSiteExcludes, ",");
    }

    public String[] getBuddyListGroupExcludes() {
        return buddyListGroupExcludes;
    }

    public void setBuddyListGroupExcludes(String[] buddyListGroupExcludes) {
        this.buddyListGroupExcludes = buddyListGroupExcludes;
    }

    public String getBuddyListGroupExcludesFlatten() {
        return StringUtil.merge(buddyListGroupExcludes, ",");
    }

    public String getUrlHelp() {
        return urlHelp;
    }

    public void setUrlHelp(String urlHelp) {
        this.urlHelp = urlHelp;
    }

    public String getUrlUnsupportedBrowser() {
        return urlUnsupportedBrowser;
    }

    public void setUrlUnsupportedBrowser(String urlUnsupportedBrowser) {
        this.urlUnsupportedBrowser = urlUnsupportedBrowser;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "propertiesSource=" + propertiesSource +
                ", buddyListStrategy=" + buddyListStrategy +
                ", buddyListSocialRelations=" + Arrays.toString(buddyListSocialRelations) +
                ", buddyListIgnoreDefaultUser=" + buddyListIgnoreDefaultUser +
                ", buddyListIgnoreDeactivatedUser=" + buddyListIgnoreDeactivatedUser +
                ", buddyListMaxBuddies=" + buddyListMaxBuddies +
                ", buddyListMaxSearch=" + buddyListMaxSearch +
                ", conversationListMaxMessages=" + conversationListMaxMessages +
                ", conversationFeedMaxConversations=" + conversationFeedMaxConversations +
                ", buddyListSiteExcludes=" + Arrays.toString(buddyListSiteExcludes) +
                ", buddyListGroupExcludes=" + Arrays.toString(buddyListGroupExcludes) +
                ", urlHelp='" + urlHelp + '\'' +
                ", urlUnsupportedBrowser='" + urlUnsupportedBrowser + '\'' +
                '}';
    }
}