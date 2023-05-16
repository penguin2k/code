package com.springmvc01.dao.impl;

import java.sql.*;

public class DBUtil {
    private static String url ="jdbc:mysql://localhost:3306/web01?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static String driver ="com.mysql.cj.jdbc.Driver";
    private static String user = "root";
    private static String passwd = "penguin";
    public boolean executeUpdate(String sql,Object [] params) throws SQLException {
        PreparedStatement pstmt=null;
        Connection con=null;
        try
        {
            Class.forName(driver);
            con= DriverManager.getConnection(url,user,passwd);
            pstmt=con.prepareStatement(sql);
            for(int i=0;i<params.length;i++)
            {
                pstmt.setObject(i+1,params[i]);
            }
            int count =pstmt.executeUpdate();
            if(count>0)
                return  true;
            else
                return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
           e.printStackTrace();
           return false;
        }
        finally {
            try
            {
                if(pstmt!=null) pstmt.close();
                if(con!=null) con.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

    }
    public ResultSet querryAllUser(String sql, Object [] params)
    {
        PreparedStatement pstmt=null;
        Connection con=null;
        ResultSet rs=null;
        try
        {
            Class.forName(driver);
            con= DriverManager.getConnection(url,user,passwd);
            pstmt=con.prepareStatement(sql);
            for(int i=0;i<params.length;i++)
            {
                pstmt.setObject(i+1,params[i]);
            }
            int count =pstmt.executeUpdate();
                return  rs;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
