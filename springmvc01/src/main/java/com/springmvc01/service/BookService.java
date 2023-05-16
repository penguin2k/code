package com.springmvc01.service;

import com.springmvc01.book.book;
import com.springmvc01.dao.BookDao;

import java.util.ArrayList;
import java.util.Collection;

public interface BookService {
    BookDao bookdao = null;

    Collection<book> getbooks();

    book getbid(String id);

    ArrayList<book> searchname(String partname);
}
