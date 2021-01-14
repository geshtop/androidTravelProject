package com.geshtop.project.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.geshtop.project.Entity.Travel;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class GeoHelper {
    public static LatLng  getLocationFromAddress(String strAddress, Context ctx){


        Geocoder coder = new Geocoder(ctx);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.size()==0) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    public static float GetDistance(Location locationA,  Location locationB ){
        float distance = locationA.distanceTo(locationB);
        return  distance;
    }




}
