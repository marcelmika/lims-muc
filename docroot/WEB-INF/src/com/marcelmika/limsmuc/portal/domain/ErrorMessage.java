/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.domain;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.marcelmika.limsmuc.portal.http.HttpStatus;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 25/01/15
 * Time: 03:13
 */
public class ErrorMessage {

    private HttpStatus httpStatus;
    private String message;

    // Constants
    private static final String BAD_REQUEST = "Client provided wrong data";
    private static final String UNAUTHORIZED = "You are not authorized. Your session has expired.";
    private static final String FORBIDDEN = "You are not allowed to perform such operation";
    private static final String NOT_FOUND = "Resource you have request hasn't been found";
    private static final String CONFLICT = "Conflict. Such entity already exists.";
    private static final String REQUEST_ENTITY_TOO_LARGE = "Request entity too large";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error";

    /**
     * Creates error message for bad request
     *
     * @return ErrorMessage
     */
    public static ErrorMessage badRequest() {
        return badRequest(BAD_REQUEST);
    }

    /**
     * Create error message for bad request
     *
     * @param message String
     * @return ErrorMessage
     */
    public static ErrorMessage badRequest(String message) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.message = message;
        errorMessage.httpStatus = HttpStatus.BAD_REQUEST;

        return errorMessage;
    }

    /**
     * Create error message for unauthorized
     *
     * @return ErrorMessage
     */
    public static ErrorMessage unauthorized() {
        return unauthorized(UNAUTHORIZED);
    }

    /**
     * Creates error message for unauthorized
     *
     * @param message String
     * @return ErrorMessage
     */
    public static ErrorMessage unauthorized(String message) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.message = message;
        errorMessage.httpStatus = HttpStatus.UNAUTHORIZED;

        return errorMessage;
    }

    /**
     * Create error message for forbidden
     *
     * @return ErrorMessage
     */
    public static ErrorMessage forbidden() {
        return forbidden(FORBIDDEN);
    }

    /**
     * Creates error message for forbidden
     *
     * @param message String
     * @return ErrorMessage
     */
    public static ErrorMessage forbidden(String message) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.message = message;
        errorMessage.httpStatus = HttpStatus.FORBIDDEN;

        return errorMessage;
    }

    /**
     * Create error message for not found
     *
     * @return ErrorMessage
     */
    public static ErrorMessage notFound() {
        return notFound(NOT_FOUND);
    }

    /**
     * Create error message for not found
     *
     * @param message String
     * @return ErrorMessage
     */
    public static ErrorMessage notFound(String message) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.message = message;
        errorMessage.httpStatus = HttpStatus.NOT_FOUND;

        return errorMessage;
    }

    /**
     * Create error message for conflict
     *
     * @return ErrorMessage
     */
    public static ErrorMessage conflict() {
        return conflict(CONFLICT);
    }

    /**
     * Create error message for conflict
     *
     * @param message String
     * @return ErrorMessage
     */
    public static ErrorMessage conflict(String message) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.message = message;
        errorMessage.httpStatus = HttpStatus.CONFLICT;

        return errorMessage;
    }

    /**
     * Create error message for request entity too large
     *
     * @return ErrorMessage
     */
    public static ErrorMessage requestEntityTooLarge() {
        return requestEntityTooLarge(REQUEST_ENTITY_TOO_LARGE);
    }

    /**
     * Create error message for request entity too large
     *
     * @param message String
     * @return ErrorMessage
     */
    public static ErrorMessage requestEntityTooLarge(String message) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.message = message;
        errorMessage.httpStatus = HttpStatus.REQUEST_ENTITY_TOO_LARGE;

        return errorMessage;
    }

    /**
     * Creates error message for the internal server error
     *
     * @return ErrorMessage
     */
    public static ErrorMessage internalServerError() {
        return internalServerError(INTERNAL_SERVER_ERROR);
    }

    /**
     * Creates error message for the internal server error
     *
     * @param message String
     * @return ErrorMessage
     */
    public static ErrorMessage internalServerError(String message) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.message = INTERNAL_SERVER_ERROR;
        errorMessage.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return errorMessage;
    }

    /**
     * Returns serialized version of the object
     *
     * @return JSON serialized ErrorMessage
     */
    public String serialize() {
        return JSONFactoryUtil.looseSerialize(this);
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JSON(include = false)
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @JSON(include = true)
    public int getCode() {
        return httpStatus.value();
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
