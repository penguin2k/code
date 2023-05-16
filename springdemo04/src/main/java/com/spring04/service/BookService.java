package com.spring04.service;

import com.spring04.book.book;
import com.spring04.dao.BookDao;

import java.util.ArrayList;
import java.util.Collection;

public interface BookService {
    BookDao bookdao = null;

    Collection<book> getbooks();

    book getbid(String id);

    ArrayList<book> searchname(String partname);
}
