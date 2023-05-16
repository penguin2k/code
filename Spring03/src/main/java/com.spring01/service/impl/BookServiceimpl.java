package com.spring01.service.impl;

import com.dao.BookDao;
import com.service.BookService;
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
