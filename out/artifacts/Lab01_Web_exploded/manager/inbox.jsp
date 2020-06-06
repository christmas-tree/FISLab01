<%--
  Created by IntelliJ IDEA.
  User: tnghi
  Date: 6/4/2020
  Time: 4:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inbox</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <h4>Inbox</h4>
    <c:if test="${error}">
        <p style="color: red">Error changing customer's status.</p>
    </c:if>
    <c:if test="${success}">
        <p style="color: green">Customer updated.<c:if test="${customerFile}"><a href="${pageContext.request.contextPath}/download?=${customerFile}">Download file</a>.</c:if></p>
    </c:if>
    <table class="u-full-width">
        <thead>
        <tr>
            <th>Customer ID</th>
            <th>Name</th>
            <th>Detail</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="customer" items="${customerPendingList}">
            <tr>
                <td>${customer.getCustomerId()}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/customer/verify?id=${customer.getCustomerId()}">${customer.getName()}</a>
                </td>
                <td>
                    <a class="button" href="${pageContext.request.contextPath}/customer/verify?id=${customer.getCustomerId()}">Detail</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form action="${pageContext.request.contextPath}/logout" method="POST">
        <button>Log out</button>
    </form>
</div>
</body>
</html>
