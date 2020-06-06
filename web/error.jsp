<%--
  Created by IntelliJ IDEA.
  User: tnghi
  Date: 6/4/2020
  Time: 11:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Oops!</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="container">
        <h1 style="color: crimson">${errorCode}</h1>
        <h5>${errorMessage}</h5>
        <a class="button" href="${pageContext.request.contextPath}">Back to home</a>
    </div>
</body>
</html>
