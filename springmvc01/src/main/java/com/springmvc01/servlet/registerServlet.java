package com.springmvc01.servlet;

import com.springmvc01.config.beanconfig;
import com.springmvc01.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/registerServlet")
public class registerServlet extends HttpServlet {
    UserService userservice;

    @Override
    public void init() throws ServletException {
        super.init();
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(beanconfig.class);
//        ServletContext servletContext = this.getServletContext();
//        WebApplicationContext ctx =    WebApplicationContextUtils.getWebApplicationContext(servletContext);
        //ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        userservice = (UserService) ctx.getBean("userservice");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Login-get");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Login-Post");
        //前端请求（request），后端处理后，最后给前端做出响应（response）
        //1.设置请求和响应的编码格式，以及响应的格式
        request.setCharacterEncoding("utf-8");  //设置请求的编码格式为中文
        response.setCharacterEncoding("utf-8");    //设置响应的编码格式
        response.setContentType("text/html;charset=UTF-8");  //以什么样的格式 （文本/网页）响应

        //2.获取请求的参数
        String uname = request.getParameter("username"); //根据表单的name属性获取用户输入的值
        String passwd = request.getParameter("password");
        System.out.println("得到的:"+uname);
        System.out.println(passwd);
// 7. 判断该用户名是否符合注册条件
        //response.sendRedirect("login/register.jsp");
       /* request.getRequestDispatcher("login/register.jsp").forward(request, response);*/
        if (uname.isEmpty()||passwd.isEmpty()) {
            request.setAttribute("loginInfo", "用户名和密码不能为空");
            request.getRequestDispatcher("login/register.jsp").forward(request, response);
            /*response.sendRedirect("login/register.jsp");*/
        }
        if( userservice.login(uname,passwd)==true)
        {
            request.setAttribute("loginInfo", "用户已经存在");
            request.getRequestDispatcher("login/register.jsp").forward(request, response);
            /*response.sendRedirect("login/register.jsp");*/
        }
        userservice.register(uname,passwd);
        response.sendRedirect("login/register.jsp");
    }
}
