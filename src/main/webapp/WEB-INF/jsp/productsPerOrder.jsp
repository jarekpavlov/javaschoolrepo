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
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<body>
<div align="center">
    <%@ include file="fragments/navbar.jspf" %>
    <div align="right" style="margin-right: 1.5em" >
        <%@ include file="fragments/cartInformation.jspf" %>
    </div>
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
                <th>${product.price}</th>
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
