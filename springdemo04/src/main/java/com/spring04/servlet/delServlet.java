package com.spring04.servlet;

import com.spring04.book.book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "delServlet", value = "/delServlet")
public class delServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        int id = Integer.parseInt(request.getParameter("id"));

        System.out.println("id:"+id);

        HttpSession session = request.getSession();
        List<book> list = (List<book>) session.getAttribute("cart");


        for(int i=0;i<list.size();i++) {
            System.out.println(list.get(i).getBid());
            if(id==list.get(i).getBid())
                list.remove(i);
        }

        session.setAttribute("cart", list);
        String url = "ShoppingCartServlet";
        response.sendRedirect(url);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
