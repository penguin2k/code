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
        //前端请求（request），后端处理后，最后给前端做出响应（response）
        //1.设置请求和响应的编码格式，以及响应的格式
        request.setCharacterEncoding("utf-8");  //设置请求的编码格式为中文
        response.setCharacterEncoding("utf-8");    //设置响应的编码格式
        response.setContentType("text/html;charset=UTF-8");  //以什么样的格式 （文本/网页）响应
        HttpSession session=request.getSession();
        //2.获取请求的参数
        String uname = request.getParameter("username"); //根据表单的name属性获取用户输入的值
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
            pw.write("<script language='javascript'>alert('用户名或密码错误')</script>");
            response.sendRedirect("login/login.jsp");
          /*  request.setAttribute("loginInfo", "用户名或密码错误");
            request.getRequestDispatcher("login/lodinFailure.jsp").forward(request, response);*/
            /*request.getRequestDispatcher("login/lodinFailure.jsp").forward(request,response);*/
        }

    }
}
