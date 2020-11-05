package com.example.mobilesocialhub.eventcard;

import android.graphics.drawable.Drawable;

public class Comment {
    //private String id;
    private String username;
    private String date;
    private String content;

    public Comment(String username, String date, String content) {
        this.username = username;
        this.date = date;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

