<%--
  Created by IntelliJ IDEA.
  User: tnghi
  Date: 6/4/2020
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <h4>Login</h4>
    <div>
        <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="row">
                <div class="four columns">
                    <label for="username">Username</label>
                    <input class="u-full-width" type="text" name="username" required="" placeholder="Your username" id="username">
                </div>
            </div>
            <div class="row">
                <div class="four columns">
                    <label for="password">Password</label>
                    <input class="u-full-width" type="password" required="" name="password" id="password">
                </div>
            </div>
            <div class="row">
                <input class="button-primary" type="submit" value="Log in">
            </div>
        </form>
    </div>
</div>
</body>
</html>
