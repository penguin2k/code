package com.spring04.dao;

import com.spring04.book.book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface BookDao {
    ArrayList getbooks() ;
    ArrayList<book>  searchname(String name);
    book getbid(String id);

}
