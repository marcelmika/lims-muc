
/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.jabber.form;

import org.jivesoftware.smackx.FormField;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class PersistentRoomFormField extends FormField {

    public PersistentRoomFormField(boolean isPersistent) {
        super("muc#roomconfig_persistentroom");
        this.setType(FormField.TYPE_BOOLEAN);
        this.addValue(isPersistent ? "1" : "0");
        this.setRequired(true);
        this.setLabel("Make Room Persistent");
    }
}
