<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 04.05.2021
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Products in Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</head>
<body>
<div align="center">
    <h1>Products in Cart</h1>
    <table cellpadding="3" >
        <tr>
            <th>Number</th>
            <th>Brand</th>
            <th>Title</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <c:if test="${cartIsEmpty==false}">
            <form:form action="/MmsPr/order/save-from-cart" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <c:forEach items="${productsInCart}" var="entity" varStatus="status">
                    <tr>
                        <th>${status.index+1}</th>
                        <th>${entity.product.brand}</th>
                        <th>${entity.product.title}</th>
                        <th>${entity.product.price}</th>
                        <th><input type="number" value="${entity.quantity}" name="${entity.product.id}"></th>
                        <th>
                            <a href="/MmsPr/order/delete-from-cart?id=${entity.product.id}" class="btn btn-primary btn-sm" role="button">delete</a>
                        </th>
                    </tr>
                </c:forEach>
                <input type="submit" value="Create Order"/>
            </form:form>
        </c:if>

    </table>
</div>

</body>
</html>
