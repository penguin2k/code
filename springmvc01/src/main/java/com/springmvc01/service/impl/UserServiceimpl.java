package com.springmvc01.service.impl;

import com.springmvc01.config.beanconfig;
import com.springmvc01.dao.UserDao;
import com.springmvc01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service("userservice")
public class UserServiceimpl implements UserService {
    @Autowired
    @Qualifier("userdao")
    private UserDao userdao;
    public  boolean login(String uname, String passwd) {
      if(userdao.login(uname,passwd)!=-1)
          return true;
      else
          return false;
    }

    @Override
    public void register(String uname, String passwd) {
        userdao.register(uname,passwd);
    }

    public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(beanconfig.class);
    UserServiceimpl userserviceimpl = (UserServiceimpl) context.getBean("userservice");
    String username="penguin";
    String password="penguin";
    System.out.println(userserviceimpl.login(username,password));
}
}
