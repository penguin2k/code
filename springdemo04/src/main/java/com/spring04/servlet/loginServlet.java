package com.spring04.servlet;

import com.spring04.aop.MyLog;
import com.spring04.config.beanconfig;
import com.spring04.service.UserService;
import com.spring04.service.impl.UserServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class loginServlet extends HttpServlet {
//    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//
//    UserService userservice = (UserService) ctx.getBean("userservice");
//    WebApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
//    UserService userservice = (UserService) ctx.getBean("userservice");
        UserService userservice;

    @Override
    public void init() throws ServletException {
        super.init();
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(beanconfig.class);
//        ServletContext servletContext = this.getServletContext();
//        WebApplicationContext ctx =    WebApplicationContextUtils.getWebApplicationContext(servletContext);
        userservice = (UserService) ctx.getBean("userservice");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          System.out.println("Login-Get");
        PrintWriter pw =response.getWriter();
        String username="penguin";
        String password="penguin";
        pw.println("deget");
        System.out.println(userservice.login(username,password));
    }

    @Override
    @MyLog(methods = "login",module = "login")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Login-Post");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();

        String uname = request.getParameter("username");
        String passwd = request.getParameter("password");
        System.out.println("pw:"+passwd);
        if(userservice.login(uname,passwd)!=false)
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
