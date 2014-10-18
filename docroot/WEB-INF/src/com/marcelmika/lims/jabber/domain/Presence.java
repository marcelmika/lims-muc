/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.jabber.domain;

import com.marcelmika.lims.api.entity.PresenceDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 3:11 PM
 */

public enum Presence {
    STATE_ACTIVE(0),
    STATE_AWAY(1),
    STATE_DND(2),
    STATE_OFFLINE(3),
    STATE_UNRECOGNIZED(4);

    private int code;

    private Presence(int code) {
        this.code = code;
    }

    /**
     * Factory method which creates new Presence from PresenceDetails
     *
     * @param presenceDetails PresenceDetails
     * @return Presence
     */
    public static Presence fromPresenceDetails(PresenceDetails presenceDetails) {

        if (presenceDetails == PresenceDetails.ACTIVE) {
            return Presence.STATE_ACTIVE;
        } else if (presenceDetails == PresenceDetails.AWAY) {
            return Presence.STATE_AWAY;
        } else if (presenceDetails == PresenceDetails.DND) {
            return Presence.STATE_DND;
        } else if (presenceDetails == PresenceDetails.OFFLINE) {
            return Presence.STATE_OFFLINE;
        } else {
            return Presence.STATE_UNRECOGNIZED;
        }
    }

    public static Presence fromSmackPresence(org.jivesoftware.smack.packet.Presence smackPresence) {
        // Offline
        if (smackPresence.getType().equals(org.jivesoftware.smack.packet.Presence.Type.unavailable)) {
            return Presence.STATE_OFFLINE;
        }

        // Active
        if (smackPresence.getType().equals(org.jivesoftware.smack.packet.Presence.Type.available) &&
                smackPresence.getMode() == null) {
            return Presence.STATE_ACTIVE;
        }
        // Mode == null but Type != available -> unrecognized
        if (smackPresence.getMode() == null) {
            return Presence.STATE_UNRECOGNIZED;
        }

        // Available types:
        // Available
        if (smackPresence.getMode().equals(org.jivesoftware.smack.packet.Presence.Mode.available)) {
            return Presence.STATE_ACTIVE;
        // Away
        } else if (smackPresence.getMode().equals(org.jivesoftware.smack.packet.Presence.Mode.away)) {
            return Presence.STATE_AWAY;
        // Dnd
        } else if (smackPresence.getMode().equals(org.jivesoftware.smack.packet.Presence.Mode.dnd)) {
            return Presence.STATE_DND;
        // Unrecognized
        } else {
            return Presence.STATE_UNRECOGNIZED;
        }
    }

    public int getCode() {
        return code;
    }

    /**
     * Maps Presence to PresenceDetails
     *
     * @return PresenceDetails
     */
    public PresenceDetails toPresenceDetails() {
        if (this == Presence.STATE_ACTIVE) {
            return PresenceDetails.ACTIVE;
        } else if (this == Presence.STATE_AWAY) {
            return PresenceDetails.AWAY;
        } else if (this == Presence.STATE_DND) {
            return PresenceDetails.DND;
        } else if (this == Presence.STATE_OFFLINE) {
            return PresenceDetails.OFFLINE;
        } else {
            return PresenceDetails.UNRECOGNIZED;
        }
    }

    public org.jivesoftware.smack.packet.Presence toSmackPresence() {

        // Get connection manager from store
        org.jivesoftware.smack.packet.Presence.Type type = org.jivesoftware.smack.packet.Presence.Type.available;
        String statusDescription = "";
        int priority = 100;
        org.jivesoftware.smack.packet.Presence.Mode mode = org.jivesoftware.smack.packet.Presence.Mode.xa;

        // If the Status is OFFLINE, the getPresence() method
        // returns null, because Presence hasn't state for OFFLINE
        // You have to logout Buddy instead of call getPresence()
        if (this == Presence.STATE_OFFLINE) {
            type = org.jivesoftware.smack.packet.Presence.Type.unavailable;
            return new org.jivesoftware.smack.packet.Presence(type, statusDescription, priority, mode);
        }

        // Active
        if (this == Presence.STATE_ACTIVE) {
            mode = org.jivesoftware.smack.packet.Presence.Mode.available;
        }
        // Away
        else if (this == Presence.STATE_AWAY) {
            mode = org.jivesoftware.smack.packet.Presence.Mode.away;
        }
        // Dnd
        else if (this == Presence.STATE_DND) {
            mode = org.jivesoftware.smack.packet.Presence.Mode.dnd;
        }

        return new org.jivesoftware.smack.packet.Presence(type, statusDescription, priority, mode);
    }

}
