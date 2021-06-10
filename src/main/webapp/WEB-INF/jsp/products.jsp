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
    <%@ include file="fragments/bootstrap.jspf" %>
    <link href="resources/css/productPage.css" rel="stylesheet" type="text/css">

</head>
<body>
    <%@ include file="fragments/navbar.jspf" %>
    <div class="container">

        <div class="row">
            <div class="col-6 col-sm-5 col-md-3 col-lg-3 col-xl-3 ">
                <div class="bottom-left">
                    <img src="<c:url value="/resources/images/fire.png"/>">
                </div>
                <sec:authorize access="hasRole('EMPLOYEE')">
                    <div class="aboveAll">
                        <h3><a  href="admin/product/new" class="btn btn-primary btn-lg">New Product</a></h3>
                    </div>
                </sec:authorize>
                <form id="form1" action="/MmsPr/products" method="get">
                    <div class = "card" style="margin-top: 1.5em">
                        <div class="card-header">
                            <h5>Products Filter</h5>
                        </div>
                        <div id="filter" class="card-body">
                            <div class="form-group">
                                <label for="color">Color:</label>
                                <input class="form-control" id="color" value="${color}" type="text" name="color" placeholder="Enter a product color"/>
                            </div>
                            <div class="form-group">
                                <label for="brand" >Brand:</label>
                                <input class="form-control" id="brand" value="${brand}" type="text" name="brand" placeholder="Enter a product brand"/>
                            </div>
                            <div class="form-group">
                                <label for="title" >Title:</label>
                                <input class="form-control" id="title" value="${title}" type="text" name="title" placeholder="Enter a product title"/>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <input type="submit" class="btn btn-primary btn-sm" value="Filter">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-6 col-sm-7 col-md-9 col-lg-9 col-xl-9">
                <div class="top-right" >
                    <%@ include file="fragments/cartInformation.jspf" %>
                </div>
                <div id="cards" class="card-columns" style="text-align: center; margin-top: 1.5em">
                    <c:forEach  items="${products}" var="product" varStatus="status">
                        <div class="card text-left" style="width: 14rem">
                            <c:if test="${product.imgName==null || product.imgName==''}">
                                <img class="card-img-top"  src="<c:url value="/resources/images/picture-tag.png"/>" alt="Card image cap">
                            </c:if>
                            <c:if test="${product.imgName!=null && product.imgName!=''}">
                                <img class="card-img-top"  src="<c:url value="/pictures/${product.imgName}"/>" alt="Card image cap">
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
                                                        <button class="btn btn-info btn-sm" id="cartBtn-${product.id}">Add to Cart</button>
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
                                <li class="page-item"><input type="button" id = "pgBtn-${page}" class="page-link" value="${page}"/></li>
                            </c:forEach>
                            <a class="page-link" href="#">Next</a>
                        </ul>
                    </nav>
            </div>
        </div>

    </div>
<%@ include file="fragments/JS.jspf" %>
<script type="text/javascript" src="/MmsPr/resources/JS/filter.js"></script>
</body>
</html>
