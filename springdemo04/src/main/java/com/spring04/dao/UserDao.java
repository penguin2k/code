package com.spring04.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface UserDao {
    int login(String uname, String passwd);
    ArrayList getuser();
    void register(String uname, String passwd);
}
