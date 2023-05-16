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
        out.print("�����Ǳ���վ������������ͼ�飺" + "<br/><br/>");
        //3.�������ϣ�ͼ����ʾ
        for (book b : books) {
            //4.�õ�ͼ�������
            String url = "purcharseServlet?id="+ b.getBid();
            //System.out.println(url);
            out.print("ͼ������ �� ��" + b.getBname() + "��           " + "<a href =" + url + ">�������</a><br>");
        }
        out.print("<form action=\"searchServlet\" method=\"post\">");
        out.print("<div class=\"indexSearch\">");
        out.print("<input type=\"text\" placeholder=\"������\" name=\"partname\"/>");
        out.print("<input type=\"submit\" value=\"����\"/>");
        out.print("</div>");
        out.print("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
