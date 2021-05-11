<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 22.04.2021
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Product page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
</head>
<body>
<div align="center">
    <h1>Products</h1>
    <sec:authorize access="hasRole('EMPLOYEE')">
        <h3><a href="admin/product/new">New Product</a></h3>
    </sec:authorize>
    <sec:authorize access="hasRole('USER')">
        <a href="/MmsPr/orders">Order History</a>
    </sec:authorize>
    <sec:authorize access="hasRole('USER') or isAnonymous()">
        <label>Products in cart:</label>
        <label>${productsInCart}</label>
        <a href="/MmsPr/order/products-in-cart" class="btn btn-primary btn-sm" role="button">Cart</a>
    </sec:authorize>
    <form:form action="/MmsPr/product/filter" method="post">
        <label>Color:</label>
        <input type="text" name="color"/>
        <label>Brand:</label>
        <input type="text" name="brand"/>
        <label>Title:</label>
        <input type="text" name="title"/>
        <input type="submit" value="Filter">
    </form:form>
    <table border="3" cellpadding="5">
        <tr>
            <th>Number</th>
            <th>Title</th>
            <th>Brand</th>
            <th>Color</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <c:forEach items="${products}" var="product" varStatus="status">
            <tr align="center">
                <th>${status.index+1}</th>
                <th>${product.title}</th>
                <th>${product.brand}</th>
                <th>${product.color}</th>
                <th>${product.price}</th>
                <th>${product.quantity}</th>
                <th>
                    <sec:authorize access="hasRole('EMPLOYEE')">
                        <a href="admin/product/edit?id=${product.id}">Edit</a>
                        <a href="admin/product/delete?id=${product.id}">Delete</a>
                    </sec:authorize>

                    <sec:authorize access="!hasRole('EMPLOYEE')">
                        <form:form action="/MmsPr/order/add-to-cart?id=${product.id}" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="number" name="numberForOrder"/>
                            <input type="submit" value="Add to Cart">
                        </form:form>
                    </sec:authorize>
                </th>

            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
