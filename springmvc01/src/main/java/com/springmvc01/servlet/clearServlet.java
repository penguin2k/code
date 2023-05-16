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

@WebServlet(name = "clearServlet", value = "/clearServlet")
public class clearServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        List<book> cart = null;
        //2.得到用户自己的session
        HttpSession session = request.getSession(false);
        //3.自己定义一个标识，判断购物车里面是否有图书
 /*       boolean cartFlag = true;
        if (session == null) {//购物车没有图书
            cartFlag = false;
            System.out.println(cartFlag);
        } else {
            cart = (List<book>) session.getAttribute("cart");
            if (cart == null) {
                cartFlag = false;
            }
        }*/
        cart = (List<book>) session.getAttribute("cart");
            cart.clear();
            session.setAttribute("cart", cart);
            String url = "ShoppingCartServlet";
            response.sendRedirect(url);
        //4.判断购物车有商品
       /* if (!cartFlag) {//没有书
            out.print("对不起，您的购物车没有图书，请去购买。");
        } else {
            //有图书，遍历图书
            out.print("购买图书如下： " + "<br/>");
            for (book b : cart) {
                out.print("购买的图书：" + b.getBname() + "<br/>");
            }
        }*/
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
