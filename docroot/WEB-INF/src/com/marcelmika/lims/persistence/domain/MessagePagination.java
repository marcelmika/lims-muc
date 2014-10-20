/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.MessagePaginationDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 8:15 PM
 */
public class MessagePagination {

    private Boolean readMore;
    private Long stopperId;

    public MessagePaginationDetails toMessagePaginationDetails() {
        MessagePaginationDetails details = new MessagePaginationDetails();

        details.setReadMore(readMore);
        details.setStopperId(stopperId);

        return details;
    }

    public static MessagePagination fromMessagePaginationDetails(MessagePaginationDetails details) {
        MessagePagination messagePagination = new MessagePagination();

        messagePagination.readMore = details.getReadMore();
        messagePagination.stopperId = details.getStopperId();

        return messagePagination;
    }

    public Boolean getReadMore() {
        return readMore;
    }

    public void setReadMore(Boolean readMore) {
        this.readMore = readMore;
    }

    public Long getStopperId() {
        return stopperId;
    }

    public void setStopperId(Long stopperId) {
        this.stopperId = stopperId;
    }

    @Override
    public String toString() {
        return "MessagePagination{" +
                "readMore=" + readMore +
                ", stopperId=" + stopperId +
                '}';
    }
}
