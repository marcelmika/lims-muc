/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.domain;

import com.marcelmika.limsmuc.api.entity.BuddyCollectionDetails;
import com.marcelmika.limsmuc.api.entity.BuddyDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Container which holds a collection of buddies
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 7:11 AM
 */
public class BuddyCollection {

    // Properties
    private Map<Long, Buddy> buddiesMap = new HashMap<Long, Buddy>();

    /**
     * Factory method that creates a buddy collection from buddy collection details
     *
     * @param details BuddyCollectionDetails
     * @return BuddyCollection
     */
    public static BuddyCollection fromBuddyCollectionDetails(BuddyCollectionDetails details) {
        // Create new instance
        BuddyCollection buddyCollection = new BuddyCollection();

        for (BuddyDetails buddyDetails : details.getBuddies()) {
            buddyCollection.addBuddy(Buddy.fromBuddyDetails(buddyDetails));
        }

        return buddyCollection;
    }

    /**
     * Maps buddy collection to details
     *
     * @return BuddyCollectionDetails
     */
    public BuddyCollectionDetails toBuddyCollectionDetails() {
        BuddyCollectionDetails buddyCollectionDetails = new BuddyCollectionDetails();

        for (Buddy buddy : new ArrayList<Buddy>(buddiesMap.values())) {
            buddyCollectionDetails.addBuddyDetails(buddy.toBuddyDetails());
        }

        return buddyCollectionDetails;
    }

    /**
     * Get the collection of buddies
     *
     * @return Collection of buddies
     */
    public Collection<Buddy> getBuddies() {
        return buddiesMap.values();
    }

    /**
     * Add buddy to the collection
     *
     * @param buddy Buddy to be added
     */
    public void addBuddy(Buddy buddy) {
        if (buddy.getBuddyId() == null) {
            throw new RuntimeException("Cannot store buddy without buddy ID");
        }

        // Add
        this.buddiesMap.put(buddy.getBuddyId(), buddy);
    }

    /**
     * Remove buddy from the collection.
     *
     * @param buddy Buddy to be removed
     */
    public void removeBuddy(Buddy buddy) {
        if (buddy.getBuddyId() == null) {
            throw new RuntimeException("Cannot remove buddy without buddy ID");
        }

        // Remove
        this.buddiesMap.remove(buddy.getBuddyId());
    }
}