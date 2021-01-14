package com.geshtop.project.Entity;

import java.util.Date;

public class TravelCompany {

    private  String email;
    private String name;



    private String cid;
    private Date createdDate;
    private Boolean approved;
    private Date approvedDate;

    public TravelCompany() {

    }
    public TravelCompany(String cid,  String name ,String email) {
        this.cid = cid;
        this.email = email;
        this.name = name;
        this.createdDate = new Date();
        this.approved = false;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }



    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

}
