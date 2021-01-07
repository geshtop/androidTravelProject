package com.geshtop.project.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

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
    @NonNull
    @PrimaryKey
    private String travelId ;
    private String ClientName;
    private String ClientPhone;
    private String clientEmail;


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
//    @TypeConverters(Convertors.CompanyConverter.class)
//    private HashMap<String, Boolean> company;

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

//    public HashMap<String, Boolean> getCompany() {
//        return company;
//    }
//
//    public void setCompany(HashMap<String, Boolean> company) {
//        this.company = company;
//    }


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




    public void addDestionationLocation (UserLocation location){
        if(location != null)
            Destinations.add(location);
    }

    public boolean isValid() {
        //add validation
        return ClientName != null;
    }



}

