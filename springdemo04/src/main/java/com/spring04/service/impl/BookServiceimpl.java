package com.spring04.service.impl;

import com.spring04.aop.MyLog;
import com.spring04.book.book;
import com.spring04.dao.BookDao;
import com.spring04.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service("bookservice")
public class BookServiceimpl implements BookService {
    @Autowired(required = false)
    BookDao bookdao;
    public void setBookdao(BookDao bookdao) {
        this.bookdao = bookdao;
    }

    public Collection<book> getbooks() {
        return bookdao.getbooks();
    }
    @Override
    public book getbid(String id) {
        return bookdao.getbid(id);
    }

    @Override
    public ArrayList<book> searchname(String partname) {
        return bookdao.searchname(partname);
    }
}
