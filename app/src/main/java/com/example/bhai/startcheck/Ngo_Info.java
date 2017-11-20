package com.example.bhai.startcheck;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bhai on 9/10/17.
 */

public class Ngo_Info implements Serializable{

    String ngoname;
    String category;
    String desc;
    String co_ord;
    String co_ordemail;
    String phno;
    String lat;
    String lang;
    String id;
    ArrayList<String> volunteers;
    ArrayList<String> activities = new ArrayList<String>(8);


    public Ngo_Info(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ngo_Info(String ngoname, String category, String desc, String co_ord, String phno, String lat, String lang, String co_ordemail, String id) {

        this.ngoname = ngoname;
        this.category = category;
        this.desc = desc;
        this.co_ord = co_ord;
        this.co_ordemail = co_ordemail;
        this.phno = phno;
        this.lat = lat;
        this.lang = lang;
        this.volunteers = new ArrayList<String>(8);
        volunteers.add(" ");
        this.id = id;


    }

    public String getNgoname() {
        return ngoname;
    }

    public void setNgoname(String ngoname) {
        this.ngoname = ngoname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCo_ord() {
        return co_ord;
    }

    public void setCo_ord(String co_ord) {
        this.co_ord = co_ord;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ArrayList<String> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(ArrayList<String> volunteers) {
        this.volunteers = volunteers;
    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }

    public String getCo_ordemail() {
        return co_ordemail;
    }

    public void setCo_ordemail(String co_ordemail) {
        this.co_ordemail = co_ordemail;
    }

}
