package com.ij.model;

import java.util.Date;

/**
 * 
 * @author Indy
 *
 */
public class JourneyDetail {

    private Date time;
    private String lat;
    private String lon;
    private String speed;
    private String bearing;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getBearing() {
        return bearing;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }
}
