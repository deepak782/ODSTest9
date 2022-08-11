package com.example.odstest9;

public class LoginModel {

    String name;
    String mobile;
    String mail;
    String password;

    public LoginModel(String name, String mobile, String mail, String password) {
        this.name = name;
        this.mobile = mobile;
        this.mail = mail;
        this.password = password;
    }

    public LoginModel(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
