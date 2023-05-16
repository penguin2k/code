package com.springmvc01.dao;

import com.springmvc01.book.book;

import java.util.ArrayList;

public interface BookDao {
    ArrayList getbooks() ;
    ArrayList<book>  searchname(String name);
    book getbid(String id);

}
