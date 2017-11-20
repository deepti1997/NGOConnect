package com.example.bhai.startcheck;

import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * Created by bhai on 7/10/17.
 */

public class User_Info {

    private String fullname;
    private String uname;
    private String email;
    private String password;
    private String uid;
    private ArrayList<String> ngo_voluteer = new ArrayList<String>();
    private ArrayList<String> my_activities = new ArrayList<String>();

    public void setNgo_voluteer(ArrayList<String> ngo_voluteer) {
        this.ngo_voluteer = ngo_voluteer;
    }

    public ArrayList<String> getMy_activities() {
        return my_activities;
    }

    public void setMy_activities(ArrayList<String> my_activities) {
        this.my_activities = my_activities;
    }

    public User_Info(String fullname, String uname, String email, String password, String uid) {
        this.fullname = fullname;
        this.uname = uname;
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.ngo_voluteer.add("");
        this.my_activities.add("");

    }

    public String getFullname() {
        return fullname;
    }

    public String getUname() {
        return uname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUid() {
        return uid;
    }


}
