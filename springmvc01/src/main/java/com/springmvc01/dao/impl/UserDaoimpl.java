package com.springmvc01.dao.impl;

import com.springmvc01.dao.UserDao;
import com.springmvc01.user.user;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
@Repository("userdao")
public class UserDaoimpl implements UserDao {
    // 驱动包名和数据库url
    private static String url ="jdbc:mysql://localhost:3306/web01?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static String driver ="com.mysql.cj.jdbc.Driver";
    private static String user = "root";
    private static String passwd = "penguin";

    public static String getUrl() {
        return url;
    }

    static{
            try {
              /*  //读取db.properties文件
                Properties prop = new Properties();
                InputStream in =  new FileInputStream("src\\main\\resources\\db.properties");
                //加载文件
                prop.load(in);
                //读取信息
                url = prop.getProperty("url");
                driver = prop.getProperty("driver");
                user = prop.getProperty("user");
                passwd = prop.getProperty("passwd");*/
                //注册驱动程序
                System.out.println(url);
                Class.forName(driver);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("驱程程序注册出错");
            }
        }

    public static Connection getConnection(){
        try {
            Connection conn = DriverManager.getConnection(url, user, passwd);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void close(Connection conn,Statement stmt,ResultSet rs){
        if(rs!=null)
            try {
                rs.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new RuntimeException(e1);
            }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
    public  int login(String uname, String passwd) {
        Connection connection = null;
        PreparedStatement statement = null;
        String url= UserDaoimpl.getUrl();
        System.out.println("url:"+url);
        ArrayList users = new ArrayList();
        try {
            connection = UserDaoimpl.getConnection();
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
public ArrayList getuser() {
    Connection conn=null;Statement stmt = null;
    ResultSet rs = null;
    ArrayList users=new ArrayList();
    conn= UserDaoimpl.getConnection();
    try {
        stmt=conn.createStatement();
        //准备sql操作语句
        String sql= "SELECT * FROM user";
        rs = stmt.executeQuery(sql);
        //从结果集中提取数据
        while(rs.next()){
            user user1=new user();
            String sname  = rs.getString("sname");
            String spasswd = rs.getString("spasswd");
          user1.setSname(sname);
          user1.setSpasswd(spasswd);
          users.add(user1);
        }
        rs.close();
        stmt.close();
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }finally{
        UserDaoimpl.close(conn, stmt, rs);
    }

    return users;
}
    public  void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList users=null;
        users=getuser();
        String username="penguin";
        String password="penguin";
       /* for(int i=0;i<users.size();i++)
        {
            user user1=(user) users.get(i);
            if(username.equals(user1.getSname())&&password.equals(user1.getSpasswd()))
            {
                System.out.println(username.equals(user1.getSname())&&password.equals(user1.getSpasswd()));
            }
            else
            System.out.println(username.equals(user1.getSname())&&password.equals(user1.getSpasswd()));
        }*/
        System.out.println("ceshi"+ login(username,password));

        conn= UserDaoimpl.getConnection();
        try {
            stmt=conn.createStatement();
            //准备sql操作语句
            String sql= "SELECT * FROM user";
            rs = stmt.executeQuery(sql);
            //从结果集中提取数据
            while(rs.next()){
                String sname  = rs.getString("sname");
                String spasswd = rs.getString("spasswd");
                System.out.print("ID: " + sname);
                System.out.print(", Age: " + spasswd);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            UserDaoimpl.close(conn, stmt, rs);
        }

    }
    public void register(String uname, String passwd) {
        Connection connection = null;
        PreparedStatement statement = null;
        user user0 = new user();
        try {
            connection = UserDaoimpl.getConnection();;
            String sql=("insert into user values(?,?)");
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,uname);
            ps.setString(2,passwd);
            int i=ps.executeUpdate();
            System.out.println(i);
            /*statement = connection.prepareStatement("insert into user values(?,?) ");
            ResultSet rs = statement.executeQuery();*/

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
    }
}
