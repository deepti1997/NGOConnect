package com.example.bhai.startcheck;

/**
 * Created by bhai on 12/10/17.
 */

public class Activity_Info {

    String title;
    String desc;
    String locality;
    String date;
    String contact;
    String id;

    public Activity_Info(){

    }

    public Activity_Info(String title, String desc, String locality, String date, String contact,String id) {
        this.title = title;
        this.desc = desc;
        this.locality = locality;
        this.date = date;
        this.contact = contact;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
