/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.request.parameters;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/12/14
 * Time: 8:14 PM
 */
public class GetGroupListParameters {

    private Integer etag;

    public Integer getEtag() {
        return etag;
    }

    public void setEtag(Integer etag) {
        this.etag = etag;
    }

    @Override
    public String toString() {
        return "GetGroupListParameters{" +
                "etag=" + etag +
                '}';
    }
}
