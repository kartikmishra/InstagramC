package com.example.kartikmishra.instagramc.models;

public class UserSettings {

    private User user;
    private User_account_settings settings;

    public UserSettings(User user, User_account_settings settings) {
        this.user = user;
        this.settings = settings;
    }
    public UserSettings() {
     }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User_account_settings getSettings() {
        return settings;
    }

    public void setSettings(User_account_settings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "user=" + user +
                ", settings=" + settings +
                '}';
    }
}
