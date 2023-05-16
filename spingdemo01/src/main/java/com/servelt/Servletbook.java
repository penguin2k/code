package com.servelt;

import com.book.book;
import com.dao.bookDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet(name = "Servletbook", value = "/Servletbook")
public class Servletbook extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Collection<book> books;
        try {
            books = bookDao.getbooks();
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
