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
    <div class="container">
        <div class="row">
            <div class="col-6 col-sm-5 col-md-3 col-lg-3 col-xl-3 ">
                <h1>Products</h1>
                <sec:authorize access="hasRole('EMPLOYEE')">
                    <h3><a href="admin/product/new">New Product</a></h3>
                </sec:authorize>
                <form:form action="/MmsPr/product/filter" method="post">
                <div class="form-group row">
                    <label for="color" class="col-sm-3 col-md-2 col-form-label">Color:</label>
                    <div class="col-sm-9 col-md-10">
                        <input class="form-control" id="color" type="text" name="color"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="brand" class="col-sm-3 col-md-2 col-form-label">Brand:</label>
                    <div class="col-sm-9 col-md-10">
                        <input class="form-control" id="brand" type="text" name="brand"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="title" class="col-sm-3 col-md-2 col-form-label">Title:</label>
                    <div class="col-sm-9 col-md-10">
                        <input class="form-control" id="title" type="text" name="title"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-6">
                        <input type="submit" class="btn btn-primary btn-sm" value="Filter">
                    </div>
                </div>
                </form:form>
            </div>
            <div class="col-6 col-sm-7 col-md-9 col-lg-9 col-xl-9">
                <div class="card-columns">
                    <c:forEach items="${products}" var="product" varStatus="status">
                        <div class="card text-left" style="width: 14rem">
                            <c:if test="${product.imgName!=null}">
                                <img class="card-img-top"  src="<c:url value="/pictures/${product.imgName}"/>" alt="Card image cap">
                            </c:if>
                            <c:if test="${product.imgName==null}">
                                <img class="card-img-top"  src="<c:url value="/resources/images/picture-tag.png"/>" alt="Card image cap">
                            </c:if>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6>${product.brand}</h6>
                                            ${product.price}€
                                    </div>
                                    <div class="col">
                                        <h6 class="text-muted">${product.title}</h6>
                                        <small>quantity:&nbsp;${product.quantity}</small>
                                    </div>
                                </div>
                            </div>
                            <div >
                                <sec:authorize access="hasRole('EMPLOYEE')">
                                    <div class="row" >
                                        <div class="col">
                                            <a class="btn btn-info btn-sm" href="/MmsPr/admin/product/edit?id=${product.id}">Edit</a>
                                        </div>
                                        <div class="col">
                                            <a class="btn btn-info btn-sm" href="/MmsPr/admin/product/delete?id=${product.id}">Delete</a>
                                        </div>
                                    </div>
                                </sec:authorize>
                                <sec:authorize access="!hasRole('EMPLOYEE')">
                                    <form:form action="/MmsPr/order/add-to-cart?id=${product.id}" method="post">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <c:if test="${product.quantity!=0}">
                                                <div class="row">
                                                    <div class="col">
                                                        <input class="btn btn-info btn-sm"  type="submit" value="Add to Cart" >
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${product.quantity==0}">
                                                <input class="btn btn-info btn-sm" type="submit" value="Add to Cart" disabled>
                                            </c:if>
                                    </form:form>
                                </sec:authorize>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <a class="page-link" href="#" tabindex="-1">Previous</a>
                        <c:forEach begin = "1" end = "${pageQuantity}" var="page">
                            <li class="page-item"><a class="page-link" href="/MmsPr/products?page=${page}">${page}</a></li>
                        </c:forEach>
                        <a class="page-link" href="#">Next</a>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>
