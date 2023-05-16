<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欢迎登录</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
<div id="loginDiv">
    <form action=${pageContext.request.contextPath}/loginServlet method="post" id="loginForm">
        <h1 id="loginMsg">SIGN IN</h1>
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
            <input type="submit" class="button" value="登录">
            <input type="reset" class="button" value="重置">
            <br>
            <a href="register.jsp">没有账号？点击注册</a>
        </div>
    </form>
</div>
</body>
</html>

