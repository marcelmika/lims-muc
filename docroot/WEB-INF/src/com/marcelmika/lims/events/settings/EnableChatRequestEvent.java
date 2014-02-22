package com.marcelmika.lims.events.settings;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.events.details.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 4:24 PM
 */
public class EnableChatRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;

    public EnableChatRequestEvent(BuddyDetails buddyDetails) {
        this.buddyDetails = buddyDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }
}
