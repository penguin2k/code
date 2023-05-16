package com.dao;

import com.book.book;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class bookDao {
    private static String url = null;
    private static String driver = null;
    // ���ݿ��û���������
    private static String user = null;
    private static String passwd = null;

    static{
        try {
            //��ȡdb.properties�ļ�
            Properties prop = new Properties();
            InputStream in =  new FileInputStream("src\\main\\resources\\db.properties");
            //�����ļ�
            prop.load(in);
            //��ȡ��Ϣ
            url = prop.getProperty("url");
            driver = prop.getProperty("driver");
            user = prop.getProperty("user");
            passwd = prop.getProperty("passwd");
            //ע����������
            System.out.println(url);
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("���̳���ע�����");
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

    public static void close(Connection conn, Statement stmt, ResultSet rs){
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
    public static ArrayList getbooks()
    {
        Connection conn=null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList books=new ArrayList();
        conn=bookDao.getConnection();
        try {
            stmt=conn.createStatement();
            //׼��sql�������
            String sql= "SELECT * FROM book";
            rs = stmt.executeQuery(sql);
            //�ӽ��������ȡ����
            while(rs.next()){
                book book1= new book();
                String bname  = rs.getString("Bname");
                String bauthor = rs.getString("Bauthor");
                String pressname=rs.getString("Pressname");
                int bid= Integer.parseInt(rs.getString("bid"));
                double bprice= Double.parseDouble(rs.getString("Bprice"));
                book1.setBname(bname);
                book1.setBauthor(bauthor);
                book1.setPressname(pressname);
                book1.setBprice(bprice);
                book1.setBid(bid);
                books.add(book1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            bookDao.close(conn, stmt, rs);
        }

        return books;
    }
    public static void main(String[] args) throws Exception {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList users=null;
        users=getbooks();
        String username="penguin";
        String password="penguin";
        conn=JDBCUtil.getConnection();
        try {
            stmt=conn.createStatement();
            //׼��sql�������
            String sql= "SELECT * FROM book";
            rs = stmt.executeQuery(sql);
            //�ӽ��������ȡ����
            while(rs.next()){
                book book1= new book();
                String bname  = rs.getString("Bname");
                String bauthor = rs.getString("Bauthor");
                String pressname=rs.getString("Pressname");
                int bid= Integer.parseInt(rs.getString("bid"));
                double bprice= Double.parseDouble(rs.getString("Bprice"));
                book1.setBname(bname);
                book1.setBauthor(bauthor);
                book1.setPressname(pressname);
                book1.setBprice(bprice);
                book1.setBid(bid);
                System.out.println(bname);
                System.out.println(bauthor);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            JDBCUtil.close(conn, stmt, rs);
        }

    }
}
