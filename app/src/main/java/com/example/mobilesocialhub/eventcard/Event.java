package com.example.mobilesocialhub.eventcard;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private String id;
    private String usernamePublished;
    private String datePublished;
    private String eventDate;
    private String eventTime;
    private String address;
    private String activityName;
    private Map<String, String> attendent;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Event(String usernamePublished, String datePublished, String eventDate, String eventTime, String address, String id,String activityName) {
        this.usernamePublished = usernamePublished;
        this.datePublished = datePublished;
//        this.activity = activity;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.address = address;
        this.activityName=activityName;
        this.id = id;
        this.attendent = new HashMap<>();
        attendent.put(usernamePublished, "1");
    }

    public String getUsernamePublished() {
        return usernamePublished;
    }

    public void setUsernamePublished(String usernamePublished) {
        this.usernamePublished = usernamePublished;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

//    public void setActivity(Drawable activity) {
//        this.activity = activity;
//    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}

