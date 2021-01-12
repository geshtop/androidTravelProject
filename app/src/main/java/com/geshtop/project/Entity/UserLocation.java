package com.geshtop.project.Entity;

import android.location.Location;

import androidx.annotation.NonNull;

public class UserLocation {
    private Double lat;
    private Double lon;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    private String origin;
    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public UserLocation(double lat, double lon, String org) {
        this.lat = lat;
        this.lon = lon;
        this.origin = org;
    }

    public UserLocation() {
    }

    @NonNull
    @Override
    public String toString() {
        return origin;
    }
    //    public UserLocation convertFromLocation(Location location){
//        if (location==null)
//            return null;
//        return new UserLocation(location.getLatitude(),location.getLongitude());
//    }
}
