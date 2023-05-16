package com.spring04.service;

import com.spring04.dao.impl.UserDaoimpl;

public interface UserService {
    boolean login(String uname, String passwd);

    void register(String uname, String passwd);
}
