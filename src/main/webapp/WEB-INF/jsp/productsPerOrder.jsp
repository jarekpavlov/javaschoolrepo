<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 05.05.2021
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Products in order</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
</head>
<body>
<body>
<div align="center">
    <h1>Products in order</h1>
    <sec:authorize access="hasRole('EMPLOYEE')">
        <form:form action="/MmsPr/admin/orders/save" method="post">
            <input type="hidden" value="${order.id}" name="id">
            <div>
                <label>Order status:</label>
                <select name="orderStatus">
                    <option disabled selected value>${order.orderStatus}</option>
                    <option value="PENDING_PAYMENT">PENDING_PAYMENT</option>
                    <option value="WAITING_FOR_SHIPMENT">WAITING_FOR_SHIPMENT</option>
                    <option value="SHIPPED">SHIPPED</option>
                    <option value="DELIVERED">DELIVERED</option>
                </select>
            </div>
            <div>
                <label>Payment status:</label>
                <select name="paymentStatus">
                    <option disabled selected value>${order.paymentStatus}</option>
                    <option value="PENDING_PAYMENT">PENDING_PAYMENT</option>
                    <option value="PAID">PAID</option>
                </select>
            </div>
            <input type="submit" value="Save"/>
        </form:form>
    </sec:authorize>
    <table border="3" cellpadding="5">
        <tr>
            <th>Number</th>
            <th>Title</th>
            <th>Brand</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <c:forEach items="${products}" var="product" varStatus="status">
            <tr align="center">
                <th>${status.index+1}</th>
                <th>${product.product.title}</th>
                <th>${product.product.brand}</th>
                <th>${product.product.price}</th>
                <th>${product.quantity}</th>
            </tr>
        </c:forEach>
    </table>
    <sec:authorize access="hasRole('EMPLOYEE')">
        <a href="/MmsPr/admin/orders/delete?id=${order.id}" class="btn btn-primary">Delete order</a>
    </sec:authorize>

</div>
</body>
</body>
</html>
