package com.springmvc01.service;

public interface UserService {
    boolean login(String uname, String passwd);

    void register(String uname, String passwd);
}
