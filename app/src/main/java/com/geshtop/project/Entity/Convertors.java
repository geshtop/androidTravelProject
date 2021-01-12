package com.geshtop.project.Entity;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Convertors {

    public static class DateConverter {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        @TypeConverter
        public Date fromTimestamp(String date) throws ParseException {
            return (date == null ? null : format.parse(date));
        }

        @TypeConverter
        public String dateToTimestamp(Date date) {
            return date == null ? null : format.format(date);
        }
    }

    public static class UserLocationConverter {
        @TypeConverter
        public UserLocation fromString(String value) {
            if (value == null || value.equals(""))
                return null;

            return new Gson().fromJson(value, UserLocation.class);
        }

        @TypeConverter
        public String asString(UserLocation userLocation) {
            return new Gson().toJson(userLocation);
        }
    }


//    public static class CompanyConverter {
//        @TypeConverter
//        public HashMap<String, Boolean> fromString(String value) {
//            if (value == null || value.isEmpty())
//                return null;
//            String[] mapString = value.split(","); //split map into array of (string,boolean) strings
//            HashMap<String, Boolean> hashMap = new HashMap<>();
//            for (String s1 : mapString) //for all (string,boolean) in the map string
//            {
//                if (!s1.isEmpty()) {//is empty maybe will needed because the last char in the string is ","
//                    String[] s2 = s1.split(":"); //split (string,boolean) to company string and boolean string.
//                    Boolean aBoolean = Boolean.parseBoolean(s2[1]);
//                    hashMap.put(/*company string:*/s2[0], aBoolean);
//                }
//            }
//            return hashMap;
//        }
//
//        @TypeConverter
//        public String asString(HashMap<String, Boolean> map) {
//            if (map == null)
//                return null;
//            StringBuilder mapString = new StringBuilder();
//            for (Map.Entry<String, Boolean> entry : map.entrySet())
//                mapString.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
//            return mapString.toString();
//        }
//    }


    public static class DestinationsConverter{
        @TypeConverter
        public List<UserLocation> fromString(String value) {
            if (value == null || value.equals(""))
                return null;
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<UserLocation>>(){}.getType();
            List<UserLocation> lstObject = gson.fromJson( value, listType);
            return lstObject;
        }

        @TypeConverter
        public String asString(List<UserLocation> warehouseUserLocation) {
            return warehouseUserLocation == null ? "" :   new GsonBuilder().create().toJson(warehouseUserLocation);
        }
    }

    public static class CompaniesConverter{
        @TypeConverter
        public List<TravelCompany> fromString(String value) {
            if (value == null || value.equals(""))
                return null;
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<TravelCompany>>(){}.getType();
            List<TravelCompany> lstObject = gson.fromJson( value, listType);
            return lstObject;
        }

        @TypeConverter
        public String asString(List<TravelCompany> companies) {
            return companies == null ? "" :   new GsonBuilder().create().toJson(companies);
        }
    }
}
