package com.spring04.dao.impl;

import com.spring04.book.book;
import com.spring04.dao.BookDao;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
@Repository("bookdao")
public class BookDaoimpl implements BookDao {
    private static String url ="jdbc:mysql://localhost:3306/web01?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static String driver ="com.mysql.cj.jdbc.Driver";
    private static String user = "root";
    private static String passwd = "penguin";
    static
    {
        try {
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
    public ArrayList getbooks()
    {
        Connection conn=null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList books=new ArrayList();
        conn= BookDaoimpl.getConnection();
        try {
            stmt=conn.createStatement();
            //准备sql操作语句
            String sql= "SELECT * FROM book";
            rs = stmt.executeQuery(sql);
            //从结果集中提取数据
            while(rs.next()){
                book book1= new book();
                String bname  = rs.getString("Bname");
                String bauthor = rs.getString("Bauthor");
                String pressname=rs.getString("Pressname");
                int bid= Integer.parseInt(rs.getString("Bid"));
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
            BookDaoimpl.close(conn, stmt, rs);
        }

        return books;
    }


    public  void main(String[] args) throws Exception {

       /* Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        conn=BookDao.getConnection();
        try {
            stmt=conn.createStatement();
            //准备sql操作语句
            String sql= "SELECT * FROM book";
            rs = stmt.executeQuery(sql);
            //从结果集中提取数据
            while(rs.next()){
                book book1= new book();
                String bname  = rs.getString("Bname");
                String bauthor = rs.getString("Bauthor");
                String pressname=rs.getString("Pressname");
                double bprice= Double.parseDouble(rs.getString("Bprice"));
                System.out.print("bname : " + bname );
                System.out.print(", bauthor: " + bauthor);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BookDao.close(conn, stmt, rs);
        }*/
        ArrayList<book> books=searchname("设计");
        for(int i=0;i<books.size();i++)
        {
            book book1= books.get(i);
            System.out.println("name:"+book1.getBname());
        }

    }
    public  ArrayList<book>  searchname(String name) {
        Connection conn=null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<book> books=new ArrayList();
        conn= BookDaoimpl.getConnection();
        try {
            stmt=conn.createStatement();
            //准备sql操作语句
            String sql= "SELECT * FROM book where  Bname like ?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,"%"+name+"%");
            rs = ps.executeQuery();
            while(rs.next()) {
                book book1=new book();
                book1.setBid(Integer.parseInt(rs.getString("bid")));
                book1.setBname(rs.getString("Bname"));
                book1.setBprice(Double.parseDouble(rs.getString("Bprice")));
                book1.setPressname(rs.getString("Pressname"));
                book1.setBauthor(rs.getString("Bauthor"));
                books.add(book1);
                System.out.println("nei:"+book1.getBname());
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BookDaoimpl.close(conn, stmt, rs);
        }
        return books;
    }

    public  book getbid(String id) {
        Connection conn=null;
        Statement stmt = null;
        ResultSet rs = null;
        book book1 = new book();
        conn= BookDaoimpl.getConnection();
        try {
            stmt=conn.createStatement();
            //准备sql操作语句
            String sql= "SELECT * FROM book where bid=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,id);
            rs = ps.executeQuery();
            while(rs.next()) {
                book1.setBid(Integer.parseInt(rs.getString("bid")));
                book1.setBname(rs.getString("Bname"));
                book1.setBprice(Double.parseDouble(rs.getString("Bprice")));
                book1.setPressname(rs.getString("Pressname"));
                book1.setBauthor(rs.getString("Bauthor"));

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BookDaoimpl.close(conn, stmt, rs);
        }
        System.out.println("price:"+book1.getBprice());
        System.out.println("price:"+book1.getBid());
        System.out.println("price:"+book1.getBname());
        return book1;
    }
}
