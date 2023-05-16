package com.springmvc01.servlet;

import com.springmvc01.book.book;
import com.springmvc01.config.beanconfig;
import com.springmvc01.service.BookService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "purcharseServlet", value = "/purcharseServlet")
public class purcharseServlet extends HttpServlet {
    BookService bookservice ;

    @Override
    public void init() throws ServletException {
        super.init();
        AnnotationConfigApplicationContext ctx= new AnnotationConfigApplicationContext(beanconfig.class);
        //ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        bookservice = (BookService) ctx.getBean("bookservice");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        //判断id是否存在
        System.out.println("goumai:"+id);
        if(id==null) {
            //没有 购买图书
            String url = "Servletbook";
            response.sendRedirect(url);
            return;
        }
        book book = bookservice.getbid(id);
        //3.把图书添加至购物车
        HttpSession session = request.getSession();
        List<book> list = (List<book>) session.getAttribute("cart");
        //4.判断购物车是否存在：是不是首次购买图书
        if(list==null) {//首次购买
            //创建购物车
            list = new ArrayList<book>();
            //把购物车放进session
            session.setAttribute("cart", list);
        }
        //5.把图书添加到购物车
        list.add(book);
        //6.把请求跳转到CartServlet
        //session基于自己创建的cookie完成效果：在浏览器关闭后，cookie对象还存在/目的
        Cookie cookie = new Cookie("JESSIONID", session.getId());
        cookie.setMaxAge(60*30);//1h
        cookie.setPath("/chapter05.session");
        response.addCookie(cookie);

        //跳转到cartServlet里面
       /* response.sendRedirect("/chapter03/cartServlet");*/
        String url = "ShoppingCartServlet";
        response.sendRedirect(url);
       /* response.sendRedirect("/ ShoppingCartServlet");*/
        /**
         * String url = "cartServlet";
         response.sendRedirect(url);
         */

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
