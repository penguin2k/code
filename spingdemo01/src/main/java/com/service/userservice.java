package com.service;

import com.user.user;
import  com.dao.JDBCUtil;
import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;

public class userservice {
    public int login(String uname, String passwd) throws ServletException {
        Connection connection = null;
        PreparedStatement statement = null;
        ArrayList users = new ArrayList();
        try {
            connection =JDBCUtil.getConnection();
            statement = connection.prepareStatement("select * from user");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user user1 = new user();
                String sname = rs.getString("sname");
                String spasswd = rs.getString("spasswd");
                user1.setSname(sname);
                user1.setSpasswd(spasswd);
                users.add(user1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        int flag = -1;
        for (int i = 0; i < users.size(); i++) {
            user user2 = (user) users.get(i);
            if (uname.equals(user2.getSname()) && passwd.equals(user2.getSpasswd())) {
                flag = i;
                //System.out.println(uname.equals(user2.getSname())&& passwd.equals(user2.getSpasswd()));
            }
        }
        return flag;
    }
}
