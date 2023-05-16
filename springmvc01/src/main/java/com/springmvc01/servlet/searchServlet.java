package com.springmvc01.servlet;

import com.springmvc01.book.book;
import com.springmvc01.config.beanconfig;
import com.springmvc01.service.BookService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "searchServlet", value = "/searchServlet")
public class searchServlet extends HttpServlet {
    BookService bookservice ;

    @Override
    public void init() throws ServletException {
        super.init();
        AnnotationConfigApplicationContext ctx= new AnnotationConfigApplicationContext(beanconfig.class);
        //ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        bookservice = (BookService) ctx .getBean("bookservice");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("doget");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String partname = request.getParameter("partname");//获取搜索框内容
        System.out.println(partname);
        ArrayList<book> books;
        try {
            books=bookservice.searchname(partname);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PrintWriter out = response.getWriter();
        //book book1=BookDao.search1("JAVA");
        //out.print(book1.getBname());
        System.out.println(books.size());
        out.print("以下是搜索的图书：" + "<br/><br/>");
        //3.遍历集合，图书显示
        for (book b : books) {
            //4.得到图书的名称
            String url = "purcharseServlet?id="+ b.getBid();
            //System.out.println(url);
            out.print("图书名称 ： 《" + b.getBname() + "》           " + "<a href =" + url + ">点击购买</a><br>");
        }

    }
}
