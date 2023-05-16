
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.net.URLEncoder" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>Login</title>
    <link type="text/css" href="css/login.css" rel="stylesheet" />
    <link type="text/css" href="css/smoothness/jquery-ui-1.7.2.custom.html" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="js/easyTooltip.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
</head>
<body>
<%--<%
    request.setCharacterEncoding("utf-8");
    // 首先判断用户是否勾选了记住账户信息,如果勾选了值默认是on,也可以通过input的value属性设置
    String[] r = request.getParameterValues("isChecked");
    if(r != null && r.length > 0) {
        // 获取输入的username和password,进行编码
        String username1 = request.getParameter("username");
        String password1 = URLEncoder.encode(request.getParameter("password"), "utf-8");
        // 新建cookie对象
        Cookie usernameCookie = new Cookie("usernameCookie", username1);
        Cookie passwordCookie = new Cookie("passwordCookie", password1);
        // 设置cookie的有效期7天，单位为秒
        usernameCookie.setMaxAge(604800);
        passwordCookie.setMaxAge(604800);
        // 写入cookie对象
        response.addCookie(usernameCookie);
        response.addCookie(passwordCookie);
    } else {
        //如果用户取消了记住账户信息，则应该对cookie里的信息进行清理
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0) {
            for(Cookie c:cookies) {
                if(c.getName().equals("usernameCookie") || c.getName().equals("passwordCookie")) {
                    // 使cookie过期
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
            }
        }
    }
%>--%>
<div id="container">
    <div id="box">
        <%
            String loginUser = "";
            if(session.getAttribute("loginUser")!=null)
            {
                loginUser = session.getAttribute("loginUser").toString();
            }
        %>
        欢迎您<font color="red"><%=loginUser%></font>,登录成功！
    </div>
</div>
</body>
</html>
