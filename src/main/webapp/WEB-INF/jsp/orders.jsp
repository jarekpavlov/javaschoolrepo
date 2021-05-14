<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 23.04.2021
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Orders</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
<div align="center">
    <h1>Orders</h1>
    <table border="3" cellpadding="5">
        <tr>
            <th>Number</th>
            <sec:authorize access="hasRole('EMPLOYEE')">
                <th>Client</th>
            </sec:authorize>
            <th>Order Status</th>
            <th>Payment Status</th>
            <th>Products</th>
        </tr>
        <c:forEach items="${orders}" var="order" varStatus="status">
            <tr>
                <th>${status.index+1}</th>
                <sec:authorize access="hasRole('EMPLOYEE')">
                    <th>${order.client.email}</th>
                </sec:authorize>
                <th>${order.orderStatus}</th>
                <th>${order.paymentStatus}</th>
                <th><a href="/MmsPr/order/products-in-order?id=${order.id}">products in order</a></th>
            </tr>
        </c:forEach>
    </table>
    <sec:authorize access="hasRole('EMPLOYEE')">
        <form action="/MmsPr/admin/statistic" method="get">
            <label>Days before:</label>
            <input type="number" name="days" id="days">
            <input type="submit" value="Get Statistic" class="btn btn-primary"/>
        </form>
    </sec:authorize>
</div>

</body>
</html>
