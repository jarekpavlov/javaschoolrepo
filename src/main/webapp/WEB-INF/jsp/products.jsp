<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 22.04.2021
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Product page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</head>
<body>
    <div align="center">
        <h1>Products</h1>
        <h3><a href="product/new">New Product</a></h3>
        <table border="1" cellpadding="5">
            <tr >
                <th>Number</th>
                <th>Title</th>
                <th>Price</th>
                <th>Brand</th>
                <th>Quantity</th>
            </tr>
            <c:forEach items="${products}" var="product" varStatus="status">
                <tr align="center">
                    <th>${status.index+1}</th>
                    <th>${product.title}</th>
                    <th>${product.price}</th>
                    <th>${product.brand}</th>
                    <th>${product.quantity}</th>
                    <th>
                        <a href="product/edit?id=${product.id}">Edit</a>
                        <a href="product/delete?id=${product.id}">Delete</a>
                        <form:form action="/MmsPr/order/create?id=${product.id}" method="post">
                            <input type="text" name="numberForOrder"/>
                            <input type="submit" value="Create Order">
                        </form:form>
                    </th>

                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
