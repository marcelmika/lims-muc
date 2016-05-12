/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.security;

import com.liferay.portal.kernel.util.Base64;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 05/05/16
 * Time: 16:27
 */
public class SHAHash {

    private static final String HASH_ALGORITHM = "SHA-1";
    private static final String ENCODING = "UTF-8";

    /**
     * Creates hash of the given text
     *
     * @param text String
     * @return String
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String hash(String text) throws GeneralSecurityException, IOException {

        // Get the digest for the has algorithm
        MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);

        // Update digest from the secret
        messageDigest.update(text.getBytes(ENCODING));

        // Get the bytes from digest
        byte[] hash = messageDigest.digest();

        return Base64.encode(hash);
    }
}
