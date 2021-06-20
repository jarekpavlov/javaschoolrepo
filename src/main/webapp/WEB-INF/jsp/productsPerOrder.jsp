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
<%@ include file="fragments/navbar.jspf" %>
<div class="container" style="margin-top: 1.5em">
    <div style="float: right " >
        <%@ include file="fragments/cartInformation.jspf" %>
    </div>
    <h1 style="margin-left: 0.5em; margin-top: 1em">Products in order</h1>
    <sec:authorize access="hasRole('EMPLOYEE')">
        <form:form action="/MmsPr/admin/orders/save" method="post">
            <input type="hidden" value="${order.id}" name="id">
            <div class="input-group mb-3 col-10 col-sm-9 col-md-8 col-lg-4" align="left">
                <div class="input-group-prepend">
                    <label for="orderStatus" class="input-group-text">Order status&nbsp;&nbsp;&nbsp;&nbsp;</label>
                </div>
                <select class="custom-select" id="orderStatus" name="orderStatus">
                    <option disabled selected value>${order.orderStatus}</option>
                    <option value="PENDING_PAYMENT">PENDING_PAYMENT</option>
                    <option value="WAITING_FOR_SHIPMENT">WAITING_FOR_SHIPMENT</option>
                    <option value="SHIPPED">SHIPPED</option>
                    <option value="DELIVERED">DELIVERED</option>
                </select>
            </div>
            <div class="input-group mb-3 col-10 col-sm-9 col-md-8 col-lg-4" align="left">
                <div class="input-group-prepend">
                    <label for="paymentStatus" class="input-group-text">Payment status</label>
                </div>
                <select class="custom-select" id="paymentStatus" name="paymentStatus">
                    <option disabled selected value>${order.paymentStatus}</option>
                    <option value="PENDING_PAYMENT">PENDING_PAYMENT</option>
                    <option value="PAID">PAID</option>
                </select>
            </div>
            <div class="row">
                <div class="col-6" style="margin-left: 1.5em">
                    <button type="submit" class="btn btn-primary">Save Status</button>
                </div>
            </div>
        </form:form>
    </sec:authorize>
    <table class="table" style="text-align: center">
        <thead class="thead-light">
            <tr>
                <th scope="col">Number</th>
                <th scope="col">Title</th>
                <th scope="col">Brand</th>
                <th scope="col">Price</th>
                <th scope="col">Quantity</th>
                <th scope="col">Total</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${products}" var="product" varStatus="status">
                <tr>
                    <th scope="row">${status.index+1}</th>
                    <td>${product.product.title}</td>
                    <td>${product.product.brand}</td>
                    <td>${product.price}€</td>
                    <td>${product.quantity}</td>
                    <td>${product.quantity*product.price}€</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div style="text-align: right; margin-right: 2.5em ">
        <i style="font-size: 1.5em"><b><u>${total}€</u></b></i>
    </div>
    <sec:authorize access="hasRole('USER')">
        <form action="/MmsPr/order/repeat-order?orderId=${order.id}" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div style="float: right; margin-right: 2.5em; margin-top: 1.5em">
                <button type="submit" class="btn btn-primary">Repeat Order</button>
            </div>
        </form>
    </sec:authorize>

<%--    <sec:authorize access="hasRole('EMPLOYEE')">--%>
<%--        <div class="row">--%>
<%--            <div class="col-6" align="left" style="margin-left: 1.5em">--%>
<%--                <a href="/MmsPr/admin/orders/delete?id=${order.id}" class="btn btn-primary">Delete Order</a>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </sec:authorize>--%>
</div>
<%@ include file="fragments/JS.jspf" %>
</body>
</html>
