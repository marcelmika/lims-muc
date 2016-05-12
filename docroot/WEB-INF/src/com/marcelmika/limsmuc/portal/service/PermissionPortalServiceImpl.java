/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionRequestEvent;
import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionResponseEvent;
import com.marcelmika.limsmuc.api.events.permission.GetInstanceKeyRequestEvent;
import com.marcelmika.limsmuc.api.events.permission.GetInstanceKeyResponseEvent;
import com.marcelmika.limsmuc.portal.security.AESCipher;
import com.marcelmika.limsmuc.portal.security.RSACipher;
import com.marcelmika.limsmuc.portal.security.RSAKeyPair;
import com.marcelmika.limsmuc.portal.security.SHAHash;
import com.marcelmika.limsmuc.portal.util.IPAddressUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.Set;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 04/05/16
 * Time: 16:40
 */
public class PermissionPortalServiceImpl implements PermissionPortalService {

    // Constants
    private static final String PRIVATE_KEY_PATH = "/private.key";
    private static final String CLIENT_KEY_PATH = "/client.key";

    // Properties
    private String decryptedProductKey;

    // Log
    private static Log log = LogFactoryUtil.getLog(PermissionPortalServiceImpl.class);

    /**
     * Returns display permissions
     *
     * @param event Request Event
     * @return Response Event
     */
    @Override
    public GetDisplayPermissionResponseEvent getDisplayPermission(GetDisplayPermissionRequestEvent event) {

        // Get the product key
        String productKey = Environment.getProductKey();

        // Product key is not set
        if (productKey.isEmpty()) {
            // Failure
            return GetDisplayPermissionResponseEvent.failure(GetDisplayPermissionResponseEvent.Status.NOT_GRANTED);
        }

        try {

            // Decrypt product key
            String decryptedKey = decryptProductKey(event.getSecurityRealPath(), productKey);

            // Create has from the machine identifier
            String machineIdentifierHash = SHAHash.hash(getMachineIdentifier());

            // Permission is granted when decrypted key is the same as machine identifier hash
            if (machineIdentifierHash.equals(decryptedKey)) {
                return GetDisplayPermissionResponseEvent.success();
            }
            // Machine identifier doesn't match decrypted key -> Permission is not granted
            else {
                return GetDisplayPermissionResponseEvent.failure(GetDisplayPermissionResponseEvent.Status.NOT_GRANTED);
            }

        } catch (IOException e) {
            // Failure
            return GetDisplayPermissionResponseEvent.failure(
                    GetDisplayPermissionResponseEvent.Status.ERROR, e
            );
        } catch (GeneralSecurityException e) {
            // Failure
            return GetDisplayPermissionResponseEvent.failure(
                    GetDisplayPermissionResponseEvent.Status.ERROR, e
            );
        }
    }

    /**
     * Returns instance key
     *
     * @param event Request Event
     * @return Response Event
     */
    @Override
    public GetInstanceKeyResponseEvent getInstanceKey(GetInstanceKeyRequestEvent event) {

        try {

            // Get the instance key
            String instanceKey = getInstanceKey(event.getSecurityRealPath(), getMachineIdentifier());

            // Success
            return GetInstanceKeyResponseEvent.success(instanceKey);
        }
        // Failure
        catch (Exception e) {
            return GetInstanceKeyResponseEvent.failure(GetInstanceKeyResponseEvent.Status.ERROR, e);
        }
    }


    /**
     * Returns machine identifier related to the given instance
     *
     * @return String
     * @throws IOException
     */
    private String getMachineIdentifier() throws IOException {
        // Read IP addresses
        Set<String> ipAddresses = IPAddressUtil.getIpAddresses();

        // There are no IP addresses
        if (ipAddresses.isEmpty()) {
            throw new IOException("Machine doesn't return any IP Addresses");
        }

        // Create machine identifier from the IP addresses
        String machineIdentifier = StringUtil.merge(ipAddresses, ";");

        // Append instance secret to machine identifier
        machineIdentifier += ("|" + Environment.getInstanceSecret());

        return machineIdentifier;
    }

    /**
     * Returns instance key
     *
     * @param securityFolderPath Path to a folder with security keys
     * @param machineIdentifier  Machine Identifier
     * @return Instance Key
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private String getInstanceKey(final String securityFolderPath,
                                  final String machineIdentifier) throws IOException, GeneralSecurityException {

        // Compose path to the private key
        String path = securityFolderPath.concat(CLIENT_KEY_PATH);

        // Read the client key
        String clientSecret = FileUtil.read(path);

        // Encrypt instance key
        return AESCipher.encrypt(machineIdentifier, clientSecret);
    }

    /**
     * Decrypts product key
     *
     * @param securityFolderPath Path to a folder with security keys
     * @param productKey         Product Key
     * @return Decrypted product key
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private String decryptProductKey(final String securityFolderPath,
                                     final String productKey) throws IOException, GeneralSecurityException {

        // Product key must have exactly 172 characters
        if (productKey.length() != 172) {
            throw new GeneralSecurityException(
                    String.format("Product key must have 172 characters (%d)", productKey.length())
            );
        }

        // Product key needs to be decrypted just once
        if (decryptedProductKey == null) {

            // Compose path to the private key
            String path = securityFolderPath.concat(PRIVATE_KEY_PATH);

            // Read the file with private key
            File file = new File(path);

            // Read bytes from the file
            byte[] bytes = Files.readAllBytes(file.toPath());

            // Get the private key
            RSAKeyPair.Private privateKey = new RSAKeyPair.Private(bytes);

            // Decrypt key and store it
            decryptedProductKey = RSACipher.decrypt(productKey, privateKey.getKey());
        }

        return decryptedProductKey;
    }
}
