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

@WebServlet(name = " ShoppingCartServlet", value = "/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        List<book> cart = null;
        //2.得到用户自己的session
        HttpSession session = request.getSession(false);
        //3.自己定义一个标识，判断购物车里面是否有图书
        boolean cartFlag = true;
        if(session==null) {//购物车没有图书
            cartFlag = false;
        }
        else {
            cart = (List<book>) session.getAttribute("cart");
            if(cart==null) {
                cartFlag=false;
            }
        }
        //4.判断购物车有商品
        if(!cartFlag) {//没有书
            out.print("对不起，您的购物车没有图书，请去购买。");
        }
        else {
            //有图书，遍历图书
            out.print("以下是本网站所售卖的所有图书：" + "<br/><br/>");
            for(book b:cart) {
                String url = "delServlet?id="+ b.getBid();
                System.out.println(b.getBid());
                out.print("图书名称 ： 《" + b.getBname() + "》           " + "<a href =" + url + ">删除</a><br>");
            }

        }
        out.print("<a href=\"JiesuanServlet\"> 结算 </a>");
        out.print("<a href=\"clearServlet\"> 清空 </a>");
        out.print("<a class=\"button\" href=\"Servletbook\">返回</a>");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
