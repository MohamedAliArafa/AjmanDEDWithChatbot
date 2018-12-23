package com.ajman.ded.ae.models.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("Building")
    @Expose
    private Building building;
    @SerializedName("workinghours")
    @Expose
    private List<String> workinghours = null;
    @SerializedName("phone")
    @Expose
    private List<String> phone = null;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("website")
    @Expose
    private List<String> website = null;
    @SerializedName("postalcode")
    @Expose
    private String postalcode;
    @SerializedName("customerservice")
    @Expose
    private String customerservice;
    @SerializedName("Whatsapp")
    @Expose
    private String whatsapp;
    @SerializedName("Facebook")
    @Expose
    private Facebook facebook;
    @SerializedName("Youtube")
    @Expose
    private Youtube youtube;
    @SerializedName("Instgram")
    @Expose
    private Instgram instgram;
    @SerializedName("Location")
    @Expose
    private Location location;
    @SerializedName("makaninumber")
    @Expose
    private String makaninumber;
    @SerializedName("ServicesList")
    @Expose
    private List<String> servicesList = null;
    @SerializedName("Twitter")
    @Expose
    private Object twitter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<String> getWorkinghours() {
        return workinghours;
    }

    public void setWorkinghours(List<String> workinghours) {
        this.workinghours = workinghours;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getWebsite() {
        return website;
    }

    public void setWebsite(List<String> website) {
        this.website = website;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCustomerservice() {
        return customerservice;
    }

    public void setCustomerservice(String customerservice) {
        this.customerservice = customerservice;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    public Youtube getYoutube() {
        return youtube;
    }

    public void setYoutube(Youtube youtube) {
        this.youtube = youtube;
    }

    public Instgram getInstgram() {
        return instgram;
    }

    public void setInstgram(Instgram instgram) {
        this.instgram = instgram;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMakaninumber() {
        return makaninumber;
    }

    public void setMakaninumber(String makaninumber) {
        this.makaninumber = makaninumber;
    }

    public List<String> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<String> servicesList) {
        this.servicesList = servicesList;
    }

    public Object getTwitter() {
        return twitter;
    }

    public void setTwitter(Object twitter) {
        this.twitter = twitter;
    }

}
