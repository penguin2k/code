package com.spring03.service.impl;

import com.spring03.dao.BookDao;
import com.spring03.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bookservice")
public class BookServiceimpl implements BookService {
    @Autowired
    BookDao bookdao;
    public void setBookdao(BookDao bookdao) {
        this.bookdao = bookdao;
    }
}
