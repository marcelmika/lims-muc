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

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 05/05/16
 * Time: 11:01
 */
public class RSACipher {

    // Constants
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String ENCRYPTION_ALGORITHM = "RSA";
    private static final String ENCODING = "UTF-8";

    /**
     * Encrypts text with the given public key. The resulting string is in Base64.
     *
     * @param text      String
     * @param publicKey PublicKey
     * @return String in Base64
     * @throws GeneralSecurityException
     */
    public static String encrypt(final String text,
                                 final PublicKey publicKey) throws GeneralSecurityException, IOException {

        // Create key spec from public key
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getEncoded());

        // Create cipher
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        // Init cipher with the key spec
        cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance(ENCRYPTION_ALGORITHM).generatePublic(keySpec));

        // Encrypt text
        byte[] encryptedText = cipher.doFinal(text.getBytes(ENCODING));

        // Return string representation of encrypted text
        return Base64.encode(encryptedText);
    }

    /**
     * Decrypts cipher text with given private key. The text must be in Base64.
     *
     * @param cipherText String in Base64
     * @param privateKey PrivateKey
     * @return Decrypted text
     * @throws GeneralSecurityException
     */
    public static String decrypt(final String cipherText,
                                 final PrivateKey privateKey) throws GeneralSecurityException, IOException {

        // Create key spec from private key
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());

        // Create cipher
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        // Init cipher with the key spec
        cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance(ENCRYPTION_ALGORITHM).generatePrivate(keySpec));

        // Decrypted text
        byte[] decryptedText = cipher.doFinal(Base64.decode(cipherText));

        // Create string from bytes with the given encoding
        return new String(decryptedText, ENCODING);
    }
}
