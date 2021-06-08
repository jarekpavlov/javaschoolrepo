<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 04.05.2021
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Products in Cart</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
<div class="container">
    <h1 style="margin-left: 0.5em; margin-top: 1em">Products in Cart</h1>
    <form:form action="/MmsPr/order/save-from-cart" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table class="table" style="text-align: center">
            <thead class="thead-light">
                <tr>
                    <th style="width: 17%" scope="col">Number</th>
                    <th style="width: 17%" scope="col">Brand</th>
                    <th style="width: 17%" scope="col">Title</th>
                    <th style="width: 17%" scope="col">Price</th>
                    <th style="width: 17%" scope="col">Quantity</th>
                    <th style="width: 17%" scope="col">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${cartIsEmpty==false}">
                    <c:forEach items="${productsInCart}" var="entity" varStatus="status">
                        <tr>
                            <th>${status.index+1}</th>
                            <td>${entity.product.brand}</td>
                            <td>${entity.product.title}</td>
                            <td>${entity.product.price}</td>
                            <td><input class="form-control" required="required" type="number" min="1" max="${entity.product.quantity}" value="${entity.quantity}" name="${entity.product.id}"></td>
                            <td>
                                <a href="/MmsPr/order/delete-from-cart?id=${entity.product.id}" class="btn btn-primary btn-sm" role="button">delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
        <c:if test="${cartIsEmpty==false}">
            <div class="input-group mb-3 col-10 col-sm-9 col-md-8 col-lg-4">
                <div class="input-group-prepend">
                    <label for="paymentMethod" class="input-group-text">Payment method:</label>
                </div>
                <select class="custom-select" name="paymentMethod" id="paymentMethod">
                    <option disabled selected value>Card</option>
                    <option value="Cash">Cash</option>
                    <option value="Card">Card</option>
                </select>
            </div>
            <div class="input-group mb-3 col-10 col-sm-9 col-md-8 col-lg-4">
                <div class="input-group-prepend">
                    <label for="deliveryMethod" class="input-group-text">Delivery method:&nbsp;</label>
                </div>
                <select id="deliveryMethod" class="custom-select" name="deliveryMethod">
                    <option disabled selected value>Delivery to the store</option>
                    <option value="Home delivery">Home delivery</option>
                    <option value="Delivery to the store">Delivery to the store</option>
                </select>
            </div>
            <div class="row" style="margin-left: 1.5em">
                <div class="col-6">
                    <button type="submit" class="btn btn-info btn-sm">Create Order</button>
                </div>
            </div>
        </c:if>
    </form:form>
</div>
<%@ include file="fragments/JS.jspf" %>
</body>
</html>
