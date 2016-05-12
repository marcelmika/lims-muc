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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 05/05/16
 * Time: 11:02
 */
@SuppressWarnings("Duplicates")
public class RSAKeyPair {

    // Properties
    private Private privateKey;
    private Public publicKey;

    // Constants
    private final static int KEY_LENGTH = 1024;
    private final static String ALGORITHM = "RSA";

    // Log
    private static Log log = LogFactoryUtil.getLog(RSAKeyPair.class);

    /**
     * Private Key
     */
    public static class Private {

        // Properties
        private PrivateKey key;

        /**
         * Creates an instance from the PrivateKey
         *
         * @param key PrivateKey
         */
        public Private(PrivateKey key) {
            this.key = key;
        }

        /**
         * Creates Private key from byte representation of the key
         *
         * @param key byte representation of the key
         * @throws GeneralSecurityException
         */
        public Private(byte[] key) throws GeneralSecurityException {

            // Create key spec form the key
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);

            // Get factory for the given algorithm
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

            // Create the private key
            this.key = keyFactory.generatePrivate(keySpec);
        }

        /**
         * Saves the private key to the file at the given path
         *
         * @param path String
         * @throws IOException
         */
        public void saveToFile(String path) throws IOException {

            FileOutputStream outputStream = null;

            try {
                // Create file at the path
                File file = new File(path);
                // Create output stream
                outputStream = new FileOutputStream(file);
                // Write the key
                outputStream.write(key.getEncoded());

            } finally {

                if (outputStream != null) {
                    try {
                        // Close output stream
                        outputStream.close();

                    } catch (IOException e) {
                        // Do nothing
                        log.error(e);
                    }
                }
            }
        }

        public PrivateKey getKey() {
            return key;
        }
    }

    /**
     * Public Key
     */
    public static class Public {

        // Properties
        private PublicKey key;

        /**
         * Creates an instance from PublicKey
         *
         * @param key PublicKey
         */
        public Public(PublicKey key) {
            this.key = key;
        }

        /**
         * Creates Public key from byte representation of the key
         *
         * @param key byte representation of the key
         * @throws GeneralSecurityException
         */
        public Public(byte[] key) throws GeneralSecurityException {

            // Create key spec from the key
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);

            // Get factory for the given algorithm
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

            // Create the public key
            this.key = keyFactory.generatePublic(keySpec);
        }

        /**
         * Saves the public key to the file at the given path
         *
         * @param path String
         * @throws IOException
         */
        public void saveToFile(String path) throws IOException {

            FileOutputStream outputStream = null;

            try {
                // Create file at the path
                File file = new File(path);
                // Create output stream
                outputStream = new FileOutputStream(file);
                // Write the key
                outputStream.write(key.getEncoded());

            } finally {

                if (outputStream != null) {
                    try {
                        // Close output stream
                        outputStream.close();

                    } catch (IOException e) {
                        // Do nothing
                        log.error(e);
                    }
                }
            }
        }

        public PublicKey getKey() {
            return key;
        }
    }

    /**
     * Generates private and public keys
     *
     * @throws GeneralSecurityException
     */
    public void generate() throws GeneralSecurityException {

        // Init generator
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_LENGTH);

        // Generate key pair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        this.privateKey = new Private(keyPair.getPrivate());
        this.publicKey = new Public(keyPair.getPublic());
    }

    public Public getPublicKey() {
        return publicKey;
    }

    public Private getPrivateKey() {
        return privateKey;
    }
}
