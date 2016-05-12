/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/05/16
 * Time: 19:33
 */
public class IPAddressUtil {

    // Holds machine IP addresses
    private static final Set<String> ipAddresses;

    static {
        // Load machine IP addresses
        ipAddresses = getIPAddresses();
    }

    // Log
    private static Log log = LogFactoryUtil.getLog(IPAddressUtil.class);


    /**
     * Returns list of machine IP addresses
     *
     * @return Set
     */
    public static Set<String> getIpAddresses() {
        return ipAddresses;
    }

    /**
     * Loads machine IP addresses from the network interface.
     * If no addresses are found returns empty set
     *
     * @return Set of IP addresses
     */
    private static Set<String> getIPAddresses() {
        Set<String> ipAddresses = new HashSet<String>();

        try {
            // Get all network interfaces
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface networkInterface : networkInterfaces) {
                // Get list of inet addresses
                List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());

                for (InetAddress inetAddress : inetAddresses) {

                    // Test if address should be added or not
                    boolean isLinkLocalAddress = inetAddress.isLinkLocalAddress();
                    boolean isLoopbackAddress = inetAddress.isLoopbackAddress();
                    boolean isInet4AddressInstance = (inetAddress instanceof Inet4Address);
                    if (isLinkLocalAddress || isLoopbackAddress || !isInet4AddressInstance) {
                        continue;
                    }

                    // Add address
                    ipAddresses.add(inetAddress.getHostAddress());
                }
            }
        }
        // Failure
        catch (Exception e) {
            log.error("Unable to read local server's IP addresses", e);
        }

        return Collections.unmodifiableSet(ipAddresses);
    }


}
