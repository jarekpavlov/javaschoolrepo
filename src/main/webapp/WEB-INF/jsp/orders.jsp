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
<div align="right" style="margin-right: 1.5em" >
    <%@ include file="fragments/cartInformation.jspf" %>
</div>
<div class="container" >
    <h1 style="margin-left: 0.5em;margin-top: 1em">Orders</h1>
    <table class="table">
        <thead class="thead-light">
            <tr align="center">
                <th scope="col">Number</th>
                <sec:authorize access="hasRole('EMPLOYEE')">
                    <th scope="col">Client</th>
                </sec:authorize>
                <th scope="col">Order Status</th>
                <th scope="col">Payment Status</th>
                <th scope="col">Products</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${orders}" var="order" varStatus="status">
                <tr align="center">
                    <th  scope="row">${status.index+1}</th>
                    <sec:authorize access="hasRole('EMPLOYEE')">
                        <td>${order.client.email}</td>
                    </sec:authorize>
                    <td>${order.orderStatus}</td>
                    <td>${order.paymentStatus}</td>
                    <td><a href="/MmsPr/order/products-in-order?id=${order.id}">products in order</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <sec:authorize access="hasRole('EMPLOYEE')">
        <form action="/MmsPr/admin/statistic" method="get">
            <div class="form-group row col-10 col-sm-9 col-md-7 col-lg-5" style="margin-left: 1em">
                <label for="statistic" class="col-5 col-sm-5 col-md-5 col-lg-5 col-form-label">Days before:</label>
                <div>
                    <input class="form-control" type="number" name="days" id="statistic">
                </div>
            </div>
            <div class="row" style="margin-bottom: 1.5em; margin-left: 2em">
                <div class="col-6">
                    <button type="submit" class="btn btn-primary">Get Statistic</button>
                </div>
            </div>
        </form>
    </sec:authorize>
</div>

</body>
</html>
