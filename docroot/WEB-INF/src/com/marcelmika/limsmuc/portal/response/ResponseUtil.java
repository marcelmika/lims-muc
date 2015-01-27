/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.response;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.portal.domain.ErrorMessage;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.serialization.SerializationUtil;

import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 5:41 PM
 */
public class ResponseUtil {

    // Log
    private static Log log = LogFactoryUtil.getLog(ResponseUtil.class);

    /**
     * Sets status code to the response. Use for no-content responses.
     *
     * @param statusCode HTTP Status code
     * @param response   Resource response
     */
    public static void writeResponse(HttpStatus statusCode, ResourceResponse response) {

        // We will pass additional error message as a content to make the server API more readable
        String content = null;

        // 400
        if (statusCode == HttpStatus.BAD_REQUEST) {
            content = ErrorMessage.badRequest().serialize();
        }
        // 401
        else if (statusCode == HttpStatus.UNAUTHORIZED) {
            content = ErrorMessage.unauthorized().serialize();
        }
        // 403
        else if (statusCode == HttpStatus.FORBIDDEN) {
            content = ErrorMessage.forbidden().serialize();
        }
        // 404
        else if (statusCode == HttpStatus.NOT_FOUND) {
            content = ErrorMessage.notFound().serialize();
        }
        // 409
        else if (statusCode == HttpStatus.CONFLICT) {
            content = ErrorMessage.conflict().serialize();
        }
        // 417
        else if (statusCode == HttpStatus.REQUEST_ENTITY_TOO_LARGE) {
            content = ErrorMessage.requestEntityTooLarge().serialize();
        }
        // 500
        else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
            content = ErrorMessage.internalServerError().serialize();
        }

        // Write content to the response
        writeResponse(content, statusCode, response);
    }

    /**
     * Takes the response and writes a content given in parameter and sets status code.
     *
     * @param content    Which will be written to the response
     * @param statusCode HTTP Status code
     * @param response   Resource response
     */
    public static void writeResponse(String content, HttpStatus statusCode, ResourceResponse response) {

        // Remove null properties to decrease response size
        content = SerializationUtil.excludeNullProperties(content);

        // Write the content to the output stream
        if (content != null) {
            // Get the writer
            PrintWriter writer = getResponseWriter(response);
            // If it fails it returns null. So write the content only if we have the writer.
            if (writer != null) {
                writer.print(content);
            }
        }

        // Set status code
        response.setProperty(ResourceResponse.HTTP_STATUS_CODE, statusCode.toString());

        // Disable caching. It needs to be here because Internet Explorer aggressively caches
        // ajax requests.
        // @see http://www.dashbay.com/2011/05/internet-explorer-caches-ajax/
        response.addProperty("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.addProperty("Pragma", "no-cache"); // HTTP 1.0
        response.addProperty("Expires", "0"); // Proxies

        // Log
        if (log.isDebugEnabled()) {
            log.debug(String.format("RESPONSE STATUS CODE: %s", statusCode.toString()));
        }
    }

    /**
     * Writes slow down header to response. Thanks to that the client knows that server is overwhelmed
     * with request so it should increase polling gap
     *
     * @param response Resource response
     */
    public static void writeSlowDownResponse(ResourceResponse response) {
        // Add the slow down header
        response.setProperty("X-Slow-Down", "true");
    }

    /**
     * Returns writer from response, null on error
     *
     * @param response ResourceResponse
     * @return PrintWriter, null on error
     */
    private static PrintWriter getResponseWriter(ResourceResponse response) {
        PrintWriter writer = null;

        try {
            // Get the writer from response
            writer = response.getWriter();
        }
        // Failure
        catch (IOException e) {
            // Log
            if (log.isErrorEnabled()) {
                log.error(e);
            }
        }

        return writer;
    }

}
