package com.service.impl;

import com.dao.impl.UserDaoimpl;

import javax.servlet.ServletException;

public class UserServiceimpl {
    UserDaoimpl userdao=new UserDaoimpl();
    public static boolean login(String uname, String passwd) {
      if(UserDaoimpl.login(uname,passwd)!=-1)
          return true;
      else
          return false;
    }

    public static void main(String[] args) throws ServletException {

    }
}
