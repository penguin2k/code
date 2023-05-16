package com.springmvc01.servlet;

import com.springmvc01.aop.MyLog;
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
import java.util.Collection;

@WebServlet(name = "Servletbook", value = "/Servletbook")
public class Servletbook extends HttpServlet {
//    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//    BookService bookservice = (BookService) context.getBean("bookservice");
    BookService bookservice ;

    @Override
    public void init() throws ServletException {
        super.init();
        //ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AnnotationConfigApplicationContext ctx= new AnnotationConfigApplicationContext(beanconfig.class);
        bookservice = (BookService) ctx .getBean("bookservice");
    }

    @Override
    @MyLog(methods = "获取图书",module = "图书")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Collection<book> books;
        try {
            books = bookservice.getbooks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PrintWriter out = response.getWriter();
        out.print("以下是本网站所售卖的所有图书：" + "<br/><br/>");
        //3.遍历集合，图书显示
        for (book b : books) {
            //4.得到图书的名称
            String url = "purcharseServlet?id="+ b.getBid();
            //System.out.println(url);
            out.print("图书名称 ： 《" + b.getBname() + "》           " + "<a href =" + url + ">点击购买</a><br>");
        }
        out.print("<form action=\"searchServlet\" method=\"post\">");
        out.print("<div class=\"indexSearch\">");
        out.print("<input type=\"text\" placeholder=\"请输入\" name=\"partname\"/>");
        out.print("<input type=\"submit\" value=\"搜索\"/>");
        out.print("</div>");
        out.print("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
