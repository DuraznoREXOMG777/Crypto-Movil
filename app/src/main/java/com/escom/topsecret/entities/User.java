package com.escom.topsecret.entities;

/**
 * User Login Entity
 */
public class User {
    private String email;
    private String fullname;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private boolean loginOption;

    public User(String email, String fullName, String password, boolean loginOption) {
        this.email = email;
        this.fullname = fullName;
        this.loginOption = loginOption;
    }

    public User() {
        email = "";
        fullname = "";
        loginOption = false;
        token = "";
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
