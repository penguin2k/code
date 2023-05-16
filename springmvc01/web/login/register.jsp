<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欢迎注册</title>
    <link rel="stylesheet" href="register.css">
</head>
<body>
<div id="registerDiv">
    <form action="registerServlet" method="post" id="registerForm">
        <h1 id="registerMsg">SIGN UP</h1>
        <span class="login">已有账号，去<a href="login.jsp">登录</a></span>
        <label for="username">
            <span>用户名：</span>
            <input type="text" name="username" id="username" placeholder="请输入用户名">
        </label>
        <br/>
        <label for="password">
            <span>密码：</span>
            <input type="password" name="password" id="password" placeholder="请输入密码">
        </label>
        <div id="subDiv">
            <input type="submit" class="button" value="注册">
        </div>
    </form>
</div>
<div><%=request.getAttribute("loginInfo")==null?"":request.getAttribute("loginInfo")%></div>
</body>
</html>

