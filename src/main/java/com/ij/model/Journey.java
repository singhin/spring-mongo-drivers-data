package com.ij.model;

import java.util.ArrayList;


/**
 * 
 * @author Indy
 *
 */
public class Journey {

    private String journeyId = null;
    private ArrayList<JourneyDetail> journeyDetails = null;

    public Journey() {
        journeyDetails = new ArrayList<>();
    }

    public String getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(String journeyId) {
        this.journeyId = journeyId;
    }

    public ArrayList<JourneyDetail> getJourneyDetails() {
        return journeyDetails;
    }

    public void setJourneyDetails(ArrayList<JourneyDetail> journeyDetails) {
        this.journeyDetails = journeyDetails;
    }
}
