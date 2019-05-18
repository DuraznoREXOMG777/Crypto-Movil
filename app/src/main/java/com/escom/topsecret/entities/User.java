package com.escom.topsecret.entities;

/**
 * User Login Entity
 */
public class User {
    private String email;
    private String fullName;
    private String password;
    private boolean loginOption;

    public User(String email, String fullName, String password, boolean loginOption) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.loginOption = loginOption;
    }

    public User() {
        email = "";
        fullName = "";
        password = "";
        loginOption = false;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoginOption() {
        return loginOption;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginOption(boolean loginOption) {
        this.loginOption = loginOption;
    }
}
