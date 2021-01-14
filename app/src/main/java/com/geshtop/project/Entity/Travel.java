package com.geshtop.project.Entity;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.firebase.database.Exclude;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

@Entity(tableName = "travels")
public class Travel {
    public float getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(float currentDistance) {
        this.currentDistance = currentDistance;
    }

    @NonNull
    @PrimaryKey
    private String travelId ;
    private String ClientName;
    private String ClientPhone;
    private String Title;



    private String clientEmail;
    private String acceptedByEmail;
    private String acceptedByName;
    @Exclude
    private float currentDistance;
    @TypeConverters(Convertors.DateConverter.class)
    private Date acceptedDate;
    @TypeConverters(Convertors.UserLocationConverter.class)
    private UserLocation travelLocation;
    @TypeConverters(RequestType.class)
    private RequestType requestType;
    @TypeConverters(Convertors.DateConverter.class)
    private Date travelDate;
    @TypeConverters(Convertors.DateConverter.class)
    private Date arrivalDate;
    private int passengers;
    @TypeConverters(Convertors.CompaniesConverter.class)
    private List<TravelCompany> companies;

    @TypeConverters(Convertors.DestinationsConverter.class)
    private List<UserLocation> Destinations;
    @TypeConverters(Convertors.DateConverter.class)
    private Date creationDate;




    public Travel() {
        creationDate = new Date();
        requestType = RequestType.Created;
        Destinations = new ArrayList<>();
        //company = new HashMap<String, Boolean>();
        companies  = new ArrayList<>();
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }



    public List<UserLocation> getDestinations() {
        return Destinations;
    }

    public void setDestinations(List<UserLocation> destinations) {
        Destinations = destinations;
    }


    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientPhone() {
        return ClientPhone;
    }

    public void setClientPhone(String clientPhone) {
        ClientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public UserLocation getTravelLocation() {
        return travelLocation;
    }

    public void setTravelLocation(UserLocation travelLocation) {
        this.travelLocation = travelLocation;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }


    public String getAcceptedByEmail() {
        return acceptedByEmail;
    }

    public void setAcceptedByEmail(String acceptedByEmail) {
        this.acceptedByEmail = acceptedByEmail;
    }

    public String getAcceptedByName() {
        return acceptedByName;
    }

    public void setAcceptedByName(String acceptedByName) {
        this.acceptedByName = acceptedByName;
    }

    public Date getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public void addSingleCompany(TravelCompany company, boolean status)
    {
        if(status) { // the company w
            boolean addCompany = true;
            if (companies.size() > 0) {
                for (TravelCompany c : companies) {
                    if (c.getEmail().equals(company.getEmail())) {
                        //this company already exist
                        addCompany = false;
                    }
                }

            }
            if (addCompany) {
                companies.add(company);
            }
        }else{
            //remove the company if exists and if not approved
            for (TravelCompany c : companies) {
                if (c.getEmail().equals(company.getEmail())) { // the company exists
                    if(!c.getApproved()){ //the company does not approved yet
                        companies.remove(c);
                    }

                }
            }
        }
        //company.put(selectedCompany, status);
    }

    public List<TravelCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(List<TravelCompany> companies) {
        this.companies = companies;
    }

    public TravelCompany companyExists(String email){
        for (TravelCompany c : companies) {
            if (c.getEmail().equals(email)) {
                //this company already exist
               return c;
            }
        }
        return null;
    }


    public Location getLocationFromTravel(){
        Location l =new Location(travelId);
       l.setLatitude(travelLocation.getLat());
       l.setLongitude(travelLocation.getLon());
       return  l;
    }


    public void addDestionationLocation (UserLocation location){
        if(location != null)
            Destinations.add(location);
    }

    public boolean isValid() {
        //add validation
        return ClientName != null;
    }



}

