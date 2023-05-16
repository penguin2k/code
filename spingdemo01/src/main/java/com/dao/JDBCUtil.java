package com.dao;
import com.user.user;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class JDBCUtil {
    // 驱动包名和数据库url
    private static String url = null;
    private static String driver = null;
    // 数据库用户名和密码
    private static String user = null;
    private static String passwd = null;

    static{
        try {
            //读取db.properties文件
            Properties prop = new Properties();
            InputStream in =  new FileInputStream("src\\main\\resources\\db.properties");
            //加载文件
            prop.load(in);
            //读取信息
            url = prop.getProperty("url");
            driver = prop.getProperty("driver");
            user = prop.getProperty("user");
            passwd = prop.getProperty("passwd");
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
public static ArrayList getuser()throws Exception
{
    Connection conn=null;Statement stmt = null;
    ResultSet rs = null;
    ArrayList users=new ArrayList();
    conn=JDBCUtil.getConnection();
    try {
        stmt=conn.createStatement();
        //准备sql操作语句
        String sql= "SELECT * FROM user";
        rs = stmt.executeQuery(sql);
        //从结果集中提取数据
        while(rs.next()){
            com.user.user user1=new user();
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
        JDBCUtil.close(conn, stmt, rs);
    }

    return users;
}
    public static void main(String[] args) throws Exception {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList users=null;
        users=getuser();
        String username="penguin";
        String password="penguin";
        for(int i=0;i<users.size();i++)
        {
            user user1=(user) users.get(i);
            if(username.equals(user1.getSname())&&password.equals(user1.getSpasswd()))
            {
                System.out.println(username.equals(user1.getSname())&&password.equals(user1.getSpasswd()));
            }
            else
            System.out.println(username.equals(user1.getSname())&&password.equals(user1.getSpasswd()));
        }
        conn=JDBCUtil.getConnection();
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
            JDBCUtil.close(conn, stmt, rs);
        }

    }
}