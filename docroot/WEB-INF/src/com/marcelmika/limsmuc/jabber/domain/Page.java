/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.domain;

import com.marcelmika.limsmuc.api.entity.PageDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 07/04/15
 * Time: 22:21
 */
public class Page {

    // Current page umber
    private Integer number;
    // Size of the page
    private Integer size;
    // Number of all elements
    private Integer totalElements;
    // Number of all pages
    private Integer totalPages;

    /**
     * Factory method
     *
     * @param details PageDetails
     * @return Page
     */
    public static Page fromPageDetails(PageDetails details) {
        Page page = new Page();

        // Properties
        page.number = details.getNumber();
        page.size = details.getSize();
        page.totalElements = details.getTotalElements();
        page.totalPages = details.getTotalPages();

        return page;
    }

    /**
     * Mapping method
     *
     * @return PageDetails
     */
    public PageDetails toPageDetails() {
        PageDetails details = new PageDetails();

        // Properties
        details.setNumber(number);
        details.setSize(size);
        details.setTotalElements(totalElements);
        details.setTotalPages(totalPages);

        return details;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "Page{" +
                "number=" + number +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                '}';
    }
}
