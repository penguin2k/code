package com.springmvc01.servlet;

import com.springmvc01.book.book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "JiesuanServlet", value = "/JiesuanServlet")
public class JiesuanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        List<book> cart = null;
        //2.得到用户自己的session
        HttpSession session = request.getSession(false);
        //3.自己定义一个标识，判断购物车里面是否有图书
          boolean cartFlag = true;
        if (session == null) {//购物车没有图书
            cartFlag = false;
            System.out.println(cartFlag);
        } else {
            cart = (List<book>) session.getAttribute("cart");
            if (cart == null) {
                cartFlag = false;
            }
        }
        double sum=0,t=0;
        for(int i=0;i<cart.size();i++)
        {
            t= Double.parseDouble(String.valueOf(cart.get(i).getBprice()));
            sum+=t;
            out.println( cart.get(i).getBprice()+"<br>" );
        }
        out.println("总共："+sum+"元");
        out.print("<a class=\"button\" href=\"Servletbook\">返回</a>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
