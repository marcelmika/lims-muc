/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.security;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Arrays;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 05/05/16
 * Time: 14:32
 */
public class AESCipher {

    // Constants
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String ENCODING = "UTF-8";

    // Log
    private static Log log = LogFactoryUtil.getLog(AESCipher.class);

    /**
     * Encrypts text with the given secret. The resulting string is encoded in Base64.
     *
     * @param text   String
     * @param secret String
     * @return String
     * @throws GeneralSecurityException
     */
    public static String encrypt(final String text,
                                 final String secret) throws GeneralSecurityException, IOException {

        // Get the key
        Key key = generateKeyFromString(secret);

        // Create cypher
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);

        // Init cipher with the key
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // Encrypt
        byte[] encryptedText = cipher.doFinal(text.getBytes(ENCODING));

        // Return string representation of encrypted text
        return Base64.encode(encryptedText);
    }

    /**
     * Decrypts cipher text with given secret
     *
     * @param cipherText String
     * @param secret     String
     * @return String
     * @throws GeneralSecurityException
     */
    public static String decrypt(final String cipherText,
                                 final String secret) throws GeneralSecurityException, IOException {

        // Get the key
        Key key = generateKeyFromString(secret);

        // Create cipher
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);

        // Init cipher with the key
        cipher.init(Cipher.DECRYPT_MODE, key);

        // Decrypt
        byte[] decryptedText = cipher.doFinal(Base64.decode(cipherText));

        // Create string from bytes with the given encoding
        return new String(decryptedText, ENCODING);
    }

    /**
     * Generates Key form secret
     *
     * @param secret String
     * @return Key
     */
    private static Key generateKeyFromString(final String secret) throws GeneralSecurityException, IOException {

        // Hash the secret so it's always going to have a same size
        String hash = SHAHash.hash(secret);

        // Get the bytes from digest
        byte[] key = hash.getBytes(ENCODING);

        // Use only first 128 bits since AES uses only such long key
        key = Arrays.copyOf(key, 16);

        return new SecretKeySpec(key, ENCRYPTION_ALGORITHM);
    }
}
