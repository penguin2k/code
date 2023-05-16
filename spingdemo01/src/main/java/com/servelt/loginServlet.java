package com.servelt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import com.service.userservice;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       PrintWriter pw =response.getWriter();
        pw.println("deget");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Login-Post");
         userservice lo= new userservice();
        //ǰ������request������˴��������ǰ��������Ӧ��response��
        //1.�����������Ӧ�ı����ʽ���Լ���Ӧ�ĸ�ʽ
        request.setCharacterEncoding("utf-8");  //��������ı����ʽΪ����
        response.setCharacterEncoding("utf-8");    //������Ӧ�ı����ʽ
        response.setContentType("text/html;charset=UTF-8");  //��ʲô���ĸ�ʽ ���ı�/��ҳ����Ӧ
        HttpSession session=request.getSession();
        //2.��ȡ����Ĳ���
        String uname = request.getParameter("username"); //���ݱ���name���Ի�ȡ�û������ֵ
        String passwd = request.getParameter("password");
        System.out.println("pw:"+passwd);
        if(lo.login(uname,passwd)!=-1)
        {
            String url = "Servletbook";
            response.sendRedirect(url);
            session.setAttribute("uname",uname);
            /*request.getRequestDispatcher("/book/Servletbook").forward(request,response);*/
        } else {
            PrintWriter pw=response.getWriter();
            pw.write("<script language='javascript'>alert('�û������������')</script>");
            response.sendRedirect("login/login.jsp");
          /*  request.setAttribute("loginInfo", "�û������������");
            request.getRequestDispatcher("login/lodinFailure.jsp").forward(request, response);*/
            /*request.getRequestDispatcher("login/lodinFailure.jsp").forward(request,response);*/
        }

    }
}
