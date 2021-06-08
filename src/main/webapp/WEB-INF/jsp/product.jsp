<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 23.04.2021
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>New/Edit Product</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
<div class="container" >
    <div class = "card" style="margin-top: 1.5em">
        <div class="card-header">
            <h4>Client Information</h4>
        </div>
        <div class="card-body">
            <form:form action="/MmsPr/product/save?${_csrf.parameterName}=${_csrf.token}" method="post" modelAttribute="product" enctype="multipart/form-data"  >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" value="${product.id}" name= "id"/>
                <input type="hidden" value="${product.imgName}" name= "imgName"/>
                <div class="form-group row">
                    <label for="brand" class="col-sm-3 col-md-2 col-form-label">Brand:</label>
                    <div class="col-sm-9 col-md-10">
                        <input type="text" required="required" minlength="2" maxlength="20" class="form-control" id="brand" value="${product.brand}" name = "brand"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="category" class="col-sm-3 col-md-2 col-form-label">Category:</label>
                    <div class="col-sm-9 col-md-10">
                        <input type="text" required="required" minlength="2" maxlength="20" class="form-control" id="category" value="${product.category}" name = "category"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="color" class="col-sm-3 col-md-2 col-form-label">Color:</label>
                    <div class="col-sm-9 col-md-10">
                        <input type="text" required="required" minlength="2" class="form-control" maxlength="20" id="color" value="${product.color}" name = "color"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="mass" class="col-sm-3 col-md-2 col-form-label">Mass:</label>
                    <div class="col-sm-9 col-md-10">
                        <input type="number" required="required" min="1" max="50000" class="form-control" id="mass" value="${product.mass}" name = "mass"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="price" class="col-sm-3 col-md-2 col-form-label">Price:</label>
                    <div class="col-sm-9 col-md-10">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">€</span>
                            </div>
                            <input type="number" required="required" min="0" max="10000000" class="form-control" id="price" value="${product.price}" name = "price"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="quantity" class="col-sm-3 col-md-2 col-form-label">Quantity:</label>
                    <div class="col-sm-9 col-md-10">
                        <input type="number" required="required" min="1" max="50000" class="form-control" id="quantity" value="${product.quantity}" name = "quantity"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="title" class="col-sm-3 col-md-2 col-form-label">Title:</label>
                    <div class="col-sm-9 col-md-10">
                        <input type="text" required="required" minlength="2" maxlength="20" class="form-control" id="title" value="${product.title}" name = "title"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="volume" class="col-sm-3 col-md-2 col-form-label">Volume:</label>
                    <div class="col-sm-9 col-md-10">
                        <input type="number" required="required" min="1" max="50000" class="form-control" id="volume" value="${product.volume}" name = "volume"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="productPicture" class="col-sm-3 col-md-2 col-form-label">Picture:</label>
                    <div class="col-sm-9 col-md-10">
                        <input type="file" name="productPicture"  id="productPicture" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-6">
                        <button class="btn btn-lg btn-primary"  type="submit">Save</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<%@ include file="fragments/JS.jspf" %>
</body>
</html>
