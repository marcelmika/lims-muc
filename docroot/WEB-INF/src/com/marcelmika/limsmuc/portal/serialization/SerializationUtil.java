/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.serialization;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 27/01/15
 * Time: 14:51
 */
public class SerializationUtil {

    /**
     * This method removes all the key:value pairs from the Json String for which the value equals null
     *
     * @param json with null properties
     * @return json without null properties
     */
    public static String excludeNullProperties(String json) {

        // Validate input
        if (json == null || json.length() == 0) {
            return json;
        }

        // Patter used to find the null properties
        Pattern pattern = Pattern.compile("([,]?\"[^\"]*\":null[,]?)+");
        // Matcher finds the pattern in json
        Matcher matcher = pattern.matcher(json);

        // Prepare string buffer used to build the output string
        StringBuffer newString = new StringBuffer(json.length());

        // Go over the whole string
        while (matcher.find()) {
            if (matcher.group().startsWith(",") & matcher.group().endsWith(",")) {
                matcher.appendReplacement(newString, ",");
            } else {
                matcher.appendReplacement(newString, "");
            }
        }
        // Append the tail to matcher
        matcher.appendTail(newString);

        // Return json without null properties
        return newString.toString();
    }

}
