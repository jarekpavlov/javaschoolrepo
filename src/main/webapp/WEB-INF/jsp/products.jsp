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
    <link rel="stylesheet" type="text/css" href="/MmsPr/resources/css/custom-css.css">
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
<div align="right" style="margin-right: 1.5em" >
    <%@ include file="fragments/cartInformation.jspf" %>
</div>
<div align="center">
    <h1>Products</h1>
    <sec:authorize access="hasRole('EMPLOYEE')">
        <h3><a href="admin/product/new">New Product</a></h3>
    </sec:authorize>
    <form:form action="/MmsPr/product/filter" method="post">
        <label>Color:</label>
        <input type="text" name="color"/>
        <label>Brand:</label>
        <input type="text" name="brand"/>
        <label>Title:</label>
        <input type="text" name="title"/>
        <input type="submit" class="btn btn-primary btn-sm" value="Filter">
    </form:form>

    <div class="card-columns">
        <c:forEach items="${products}" var="product" varStatus="status">
            <div class="card" style="width: 18rem;">
                <img class="card-img-top"  src="<c:url value="/pictures/${product.imgName}"/>" alt="Card image cap">
                <div class="card-body">
                    ${product.title}
                    ${product.brand}
                    ${product.price}
                    ${product.quantity}
                    <sec:authorize access="hasRole('EMPLOYEE')">
                        <a class="btn btn-info btn-sm" href="/MmsPr/admin/product/edit?id=${product.id}">Edit</a>
                        <a class="btn btn-info btn-sm" href="/MmsPr/admin/product/delete?id=${product.id}">Delete</a>
                    </sec:authorize>

                    <sec:authorize access="!hasRole('EMPLOYEE')">
                        <form:form action="/MmsPr/order/add-to-cart?id=${product.id}" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input class="btn btn-info btn-sm" type="submit" value="Add to Cart">
                        </form:form>
                    </sec:authorize>
                </div>

            </div>
        </c:forEach>
    </div>

    <nav aria-label="Page navigation    ">
        <ul class="pagination justify-content-center">
            <a class="page-link" href="#" tabindex="-1">Previous</a>
            <c:forEach begin = "1" end = "${pageQuantity}" var="page">
                <li class="page-item"><a class="page-link" href="/MmsPr/products?page=${page}">${page}</a></li>
            </c:forEach>
            <a class="page-link" href="#">Next</a>
            </li>
        </ul>
    </nav>
</div>
</body>
</html>
