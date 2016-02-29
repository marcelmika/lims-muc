/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.domain;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.marcelmika.limsmuc.api.entity.BuddyDetails;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:56 PM
 */
public class Buddy {

    // Log
    private static Log log = LogFactoryUtil.getLog(Buddy.class);

    // Properties
    private Long buddyId;
    private Long companyId;
    private Long portraitId;
    private String portraitImageToken;
    private String portraitToken;
    private Boolean male;
    private String fullName;
    private String screenName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private Boolean connected;
    private Boolean connectedJabber;
    private Date connectedAt;
    private Presence presence;
    private Settings settings;


    /**
     * Creates an instance of Buddy form RenderRequest
     *
     * @param request RenderRequest
     * @return Buddy
     */
    public static Buddy fromRenderRequest(RenderRequest request) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        return fromThemeDisplay(themeDisplay);
    }

    /**
     * Factory method which creates new Buddy object from the PollerRequest
     *
     * @param request request
     * @return Buddy
     */
    public static Buddy fromResourceRequest(ResourceRequest request) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        return fromThemeDisplay(themeDisplay);
    }

    /**
     * Creates buddy from themeDisplay
     *
     * @param themeDisplay ThemeDisplay
     * @return Buddy
     */
    private static Buddy fromThemeDisplay(ThemeDisplay themeDisplay) {
        Buddy buddy = new Buddy();
        User user = themeDisplay.getUser();
        buddy.buddyId = user.getUserId();
        buddy.companyId = user.getCompanyId();
        try {
            buddy.male = user.isMale();
        } catch (Exception e) {
            buddy.male = true;
        }
        buddy.portraitId = user.getPortraitId();
        try {
            buddy.portraitImageToken = HttpUtil.encodeURL(DigesterUtil.digest(user.getUserUuid()));
        } catch (SystemException e) {
            buddy.portraitImageToken = "";
        }
        buddy.portraitToken = WebServerServletTokenUtil.getToken(user.getPortraitId());
        buddy.screenName = user.getScreenName();
        buddy.fullName = user.getFullName();
        buddy.firstName = user.getFirstName();
        buddy.middleName = user.getMiddleName();
        buddy.lastName = user.getLastName();

        return buddy;
    }


    /**
     * Factory method which creates new Buddy object from portal User
     *
     * @param user User
     * @return Buddy
     */
    public static Buddy fromPortalUser(User user) {
        // Create new empty buddy
        Buddy buddy = new Buddy();

        buddy.buddyId = user.getUserId();
        buddy.companyId = user.getCompanyId();
        buddy.screenName = user.getScreenName();
        buddy.fullName = user.getFullName();
        buddy.firstName = user.getFirstName();
        buddy.middleName = user.getMiddleName();
        buddy.lastName = user.getLastName();
        buddy.password = user.getPasswordUnencrypted();

        return buddy;
    }

    /**
     * Factory method which creates new Buddy object from HttpServletRequest
     *
     * @param request HttpServletRequest
     * @return Buddy
     * @throws SystemException
     * @throws PortalException
     */
    public static Buddy fromPortalServletRequest(HttpServletRequest request) throws SystemException, PortalException {
        // Create new empty buddy
        Buddy buddy = new Buddy();
        // Get user from the request
        User user = PortalUtil.getUser(request);
        // Get password from request
        String password = PortalUtil.getUserPassword(request);

        buddy.buddyId = user.getUserId();
        buddy.companyId = user.getCompanyId();
        buddy.screenName = user.getScreenName();
        buddy.fullName = user.getFullName();
        buddy.password = password;

        return buddy;
    }

    /**
     * Factory method which creates new Buddy object from HttpSession
     *
     * @param session HttpSession
     * @return Buddy
     */
    public static Buddy fromHttpSession(HttpSession session) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Get user ID from http session
        buddy.buddyId = (Long) session.getAttribute(WebKeys.USER_ID);

        return buddy;
    }

    /**
     * Factory method which creates new Buddy object from BuddyDetails
     *
     * @param details BuddyDetails
     * @return Buddy
     */
    public static Buddy fromBuddyDetails(BuddyDetails details) {
        return fromBuddyDetails(details, true);
    }

    /**
     * Factory method which creates new Buddy object from BuddyDetails
     *
     * @param details     BuddyDetails
     * @param addUserData true if data from the portal user should be added to buddy object
     * @return Buddy
     */
    public static Buddy fromBuddyDetails(BuddyDetails details, boolean addUserData) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data to user details
        buddy.buddyId = details.getBuddyId();
        buddy.companyId = details.getCompanyId();
        buddy.fullName = details.getFullName();
        buddy.screenName = details.getScreenName();
        buddy.password = details.getPassword();
        buddy.connected = details.getConnected();
        buddy.connectedJabber = details.getConnectedJabber();
        buddy.connectedAt = details.getConnectedAt();

        if (addUserData && details.getBuddyId() != null) {
            try {
                // Add additional info from local service util if it's not set in buddy details
                User user = UserLocalServiceUtil.fetchUser(details.getBuddyId());

                if (user != null) {
                    if (buddy.screenName == null) {
                        buddy.screenName = user.getScreenName();
                    }

                    if (buddy.companyId == null) {
                        buddy.companyId = user.getCompanyId();
                    }

                    if (buddy.fullName == null) {
                        buddy.fullName = user.getFullName();
                    }

                    buddy.male = user.getMale();
                    buddy.portraitId = user.getPortraitId();
                    buddy.portraitImageToken = HttpUtil.encodeURL(DigesterUtil.digest(user.getUserUuid()));
                    buddy.portraitToken = WebServerServletTokenUtil.getToken(user.getPortraitId());
                    buddy.firstName = user.getFirstName();
                    buddy.middleName = user.getMiddleName();
                    buddy.lastName = user.getLastName();
                    buddy.fullName = user.getFullName();
                }
            }
            // Failure
            catch (Exception e) {
                // Debug
                if (log.isDebugEnabled()) {
                    // Do nothing
                    log.debug(e);
                }
            }
        }

        // Relations
        if (details.getPresenceDetails() != null) {
            buddy.presence = Presence.fromPresenceDetails(details.getPresenceDetails());
        }

        if (details.getSettingsDetails() != null) {
            buddy.settings = Settings.fromSettingsDetails(details.getSettingsDetails());
        }

        return buddy;
    }

    /**
     * Factory method which creates new list of Buddies from the list of BuddyDetails
     *
     * @param detailsList list of buddy details
     * @return List<Buddy> of buddies
     */
    public static List<Buddy> fromBuddyDetailsList(List<BuddyDetails> detailsList) {
        return fromBuddyDetailsList(detailsList, true);
    }

    /**
     * Factory method which creates new list of Buddies from the list of BuddyDetails
     *
     * @param detailsList list of buddy details
     * @param addUserData true if data from the portal user should be added to buddy object
     * @return List<Buddy> of buddies
     */
    public static List<Buddy> fromBuddyDetailsList(List<BuddyDetails> detailsList, boolean addUserData) {
        // Create new list of buddies
        List<Buddy> buddies = new ArrayList<Buddy>();

        // Iterate through details and create buddy based on that
        for (BuddyDetails details : detailsList) {
            buddies.add(Buddy.fromBuddyDetails(details, addUserData));
        }

        return buddies;
    }

    /**
     * Maps user to user details
     *
     * @return UserDetails
     */
    public BuddyDetails toBuddyDetails() {
        // Create new user details
        BuddyDetails details = new BuddyDetails();
        // Map data from user
        details.setBuddyId(buddyId);
        details.setCompanyId(companyId);
        details.setFullName(fullName);
        details.setScreenName(screenName);
        details.setPassword(password);
        details.setConnected(connected);
        details.setConnectedJabber(connectedJabber);
        details.setConnectedAt(connectedAt);

        if (presence != null) {
            details.setPresenceDetails(presence.toPresenceDetails());
        }

        if (settings != null) {
            details.setSettingsDetails(settings.toSettingsDetails());
        }

        return details;
    }

    /**
     * Mapping method
     *
     * @param buddies list of Buddies
     * @return list of BuddyDetails
     */
    public static List<BuddyDetails> toBuddyDetails(List<Buddy> buddies) {
        List<BuddyDetails> details = new LinkedList<BuddyDetails>();

        for (Buddy buddy : buddies) {
            details.add(buddy.toBuddyDetails());
        }

        return details;
    }


    public Long getBuddyId() {
        return buddyId;
    }

    public void setBuddyId(Long buddyId) {
        this.buddyId = buddyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(Long portraitId) {
        this.portraitId = portraitId;
    }

    public String getPortraitToken() {
        return portraitToken;
    }

    public void setPortraitToken(String portraitToken) {
        this.portraitToken = portraitToken;
    }

    public String getPortraitImageToken() {
        return portraitImageToken;
    }

    public void setPortraitImageToken(String portraitImageToken) {
        this.portraitImageToken = portraitImageToken;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @JSON(include = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Boolean getConnectedJabber() {
        return connectedJabber;
    }

    public void setConnectedJabber(Boolean connectedJabber) {
        this.connectedJabber = connectedJabber;
    }

    @JSON(include = false)
    public Date getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(Date connectedAt) {
        this.connectedAt = connectedAt;
    }

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "Buddy{" +
                "buddyId=" + buddyId +
                ", companyId=" + companyId +
                ", portraitId=" + portraitId +
                ", portraitImageToken='" + portraitImageToken + '\'' +
                ", portraitToken='" + portraitToken + '\'' +
                ", male=" + male +
                ", fullName='" + fullName + '\'' +
                ", screenName='" + screenName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", connected=" + connected +
                ", connectedJabber=" + connectedJabber +
                ", connectedAt=" + connectedAt +
                ", presence=" + presence +
                ", settings=" + settings +
                '}';
    }
}
