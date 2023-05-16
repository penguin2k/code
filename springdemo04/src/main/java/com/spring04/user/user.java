package com.spring04.user;
public class user {
    String sname;
    String spasswd;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSpasswd() {
        return spasswd;
    }

    public void setSpasswd(String spasswd) {
        this.spasswd = spasswd;
    }

    public user(String sname, String spasswd) {
        this.sname = sname;
        this.spasswd = spasswd;
    }
    public user() {
        this.sname = null;
        this.spasswd = null;
    }
}
