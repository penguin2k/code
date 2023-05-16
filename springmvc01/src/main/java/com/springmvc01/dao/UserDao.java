package com.springmvc01.dao;

import java.util.ArrayList;

public interface UserDao {
    int login(String uname, String passwd);
    ArrayList getuser();
    void register(String uname, String passwd);
}
