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
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
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
                <label>Payment method:</label>
                <select name="paymentMethod">
                    <option disabled selected value>--select an option--</option>
                    <option value="Cash">Cash</option>
                    <option value="Card">Card</option>
                </select>
                <label>Delivery method:</label>
                <select name="deliveryMethod">
                    <option disabled selected value>--select an option--</option>
                    <option value="Home delivery">Home delivery</option>
                    <option value="Delivery to the store">Delivery to the store</option>
                </select>
                <input type="submit" value="Create Order"/>
            </form:form>
        </c:if>

    </table>
</div>

</body>
</html>
