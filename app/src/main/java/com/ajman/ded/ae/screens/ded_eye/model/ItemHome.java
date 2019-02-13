package com.ajman.ded.ae.screens.ded_eye.model;

public class ItemHome {

    public ItemHome(int count, String title) {
        this.count = count;
        this.title = title;
    }

    int count;
    String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
