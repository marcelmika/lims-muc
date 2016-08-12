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
import com.marcelmika.limsmuc.api.environment.License;
import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionRequestEvent;
import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionResponseEvent;
import com.marcelmika.limsmuc.api.events.permission.GetInstanceKeyRequestEvent;
import com.marcelmika.limsmuc.api.events.permission.GetInstanceKeyResponseEvent;
import com.marcelmika.limsmuc.portal.security.AESCipher;
import com.marcelmika.limsmuc.portal.security.RSACipher;
import com.marcelmika.limsmuc.portal.security.RSAKeyPair;
import com.marcelmika.limsmuc.portal.security.SHAHash;
import com.marcelmika.limsmuc.portal.util.IPAddressUtil;

import javax.crypto.BadPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    private static final DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    // Properties
    private String decryptedProductKey;
    private String decryptedMachineIdentifierHash;
    private boolean isInstanceUnlimited;
    private boolean isExpirationUnlimited;
    private Date expirationDate;
    private boolean isUserUnlimited;
    private Integer userLimit;

    // Log
    @SuppressWarnings("unused")
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
            GetDisplayPermissionResponseEvent.Status status = GetDisplayPermissionResponseEvent.Status.NOT_GRANTED;
            status.reason = GetDisplayPermissionResponseEvent.Status.Reason.NO_PRODUCT_KEY;
            // Failure
            return GetDisplayPermissionResponseEvent.failure(status);
        }

        try {

            // Read the security path
            String securityPath = License.getSecurityPath();

            // Security path must be set
            if (securityPath == null) {
                throw new Exception("Security path is not set");
            }

            // Decrypt product key
            decryptProductKey(securityPath, productKey);

            // Permission is granted by default
            GetDisplayPermissionResponseEvent.Status status = GetDisplayPermissionResponseEvent.Status.GRANTED;

            // Check the time limitation if not unlimited
            if (isAfterExpirationDate()) {
                status = GetDisplayPermissionResponseEvent.Status.NOT_GRANTED;
                status.reason = GetDisplayPermissionResponseEvent.Status.Reason.EXPIRED;
            }

            // Check machine identifier
            if (!isInstanceUnlimited) {
                // Create hash from the machine identifier
                String machineIdentifierHash = SHAHash.hash(getMachineIdentifier());

                // Hashes must match
                if (!machineIdentifierHash.equals(decryptedMachineIdentifierHash)) {
                    status = GetDisplayPermissionResponseEvent.Status.NOT_GRANTED;
                    status.reason = GetDisplayPermissionResponseEvent.Status.Reason.WRONG_INSTANCE_KEY;
                }
            }

            return GetDisplayPermissionResponseEvent.success(
                    status,
                    isInstanceUnlimited,
                    isExpirationUnlimited,
                    isUserUnlimited,
                    expirationDate,
                    userLimit
            );

        }
        catch (BadPaddingException e) {
            GetDisplayPermissionResponseEvent.Status status = GetDisplayPermissionResponseEvent.Status.ERROR;
            status.reason = GetDisplayPermissionResponseEvent.Status.Reason.CORRUPTED_PRODUCT_KEY;
            // Failure
            return GetDisplayPermissionResponseEvent.failure(status, e);
        }
        catch (Exception e) {
            GetDisplayPermissionResponseEvent.Status status = GetDisplayPermissionResponseEvent.Status.ERROR;
            status.reason = GetDisplayPermissionResponseEvent.Status.Reason.GENERAL_ERROR;
            // Failure
            return GetDisplayPermissionResponseEvent.failure(status, e);
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

            // Read the security path
            String securityPath = License.getSecurityPath();

            // Security path must be set
            if (securityPath == null) {
                throw new Exception("Security path is not set");
            }

            // Get the instance key
            String instanceKey = getInstanceKey(securityPath, getMachineIdentifier());

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
     */
    private String getMachineIdentifier() throws IOException {
        // Read IP addresses
        List ipAddresses = new ArrayList<String>(IPAddressUtil.getIpAddresses());

        // Sort ip addresses
        Collections.sort(ipAddresses);

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
     */
    private void decryptProductKey(final String securityFolderPath,
                                   final String productKey) throws IOException, GeneralSecurityException, ParseException {

        // Product key must have exactly 172 characters
        if (productKey.length() != 344) {
            throw new GeneralSecurityException(
                    String.format("Product key must have 344 characters (%d)", productKey.length())
            );
        }

        // Product key needs to be decrypted just once
        if (decryptedProductKey == null) {

            // Compose path to the private key
            String path = securityFolderPath.concat(PRIVATE_KEY_PATH);

            // Read the file with private key
            File file = new File(path);

            // Read bytes from the file
            byte[] bytes = FileUtil.getBytes(file);

            // Get the private key
            RSAKeyPair.Private privateKey = new RSAKeyPair.Private(bytes);

            // Decrypt key and store it
            decryptedProductKey = RSACipher.decrypt(productKey, privateKey.getKey());

            // Get indexes
            int endOfInstance = decryptedProductKey.indexOf("|");
            int endOfExpiration = decryptedProductKey.indexOf("|", endOfInstance + 1);
            int endOfUserLimit = decryptedProductKey.indexOf("|", endOfExpiration + 1);

            // Parse instance
            String parsedInstance = decryptedProductKey.substring(0, endOfInstance);
            isInstanceUnlimited = parsedInstance.equals("U");

            // Parse expiration
            String parsedExpiration = decryptedProductKey.substring(endOfInstance + 1, endOfExpiration);
            // Unlimited
            if (parsedExpiration.equals("U")) {
                isExpirationUnlimited = true;
                expirationDate = null;
            }
            // Limited
            else {
                isExpirationUnlimited = false;
                expirationDate = formatter.parse(parsedExpiration);
            }

            // Parse user limit
            String parsedUserLimit = decryptedProductKey.substring(endOfExpiration + 1, endOfUserLimit);
            if (parsedUserLimit.equals("U")) {
                isUserUnlimited = true;
                userLimit = null;
            } else {
                isUserUnlimited = false;
                userLimit = Integer.parseInt(parsedUserLimit);
            }

            // Parse machine identifier
            if (!isInstanceUnlimited) {
                decryptedMachineIdentifierHash = decryptedProductKey.substring(
                        endOfUserLimit + 1, decryptedProductKey.length()
                );
            }
        }
    }

    /**
     * Returns true if the current date is after the expiration date.
     * In other words when the license has expired.
     *
     * @return boolean
     */
    private boolean isAfterExpirationDate() {
        // If the expiration is unlimited the license cannot expire
        if (isExpirationUnlimited) {
            return false;
        }

        // If expiration date is not set grant permission, because this shouldn't happen anyway
        if (expirationDate == null) {
            return false;
        }

        // Get current calendar instance
        Calendar calendar = Calendar.getInstance();

        // Set the calendar to start of today
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date today = calendar.getTime();

        return today.after(expirationDate);
    }
}
