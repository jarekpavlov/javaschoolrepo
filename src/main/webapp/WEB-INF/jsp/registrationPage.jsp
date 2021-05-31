<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 25.04.2021
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>User Information</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
<div align="right" style="margin-right: 1.5em" >
    <%@ include file="fragments/cartInformation.jspf" %>
</div>
<div class="container">
    <div class = "card" style="margin-top: 1.5em">
        <div class="card-header">
            <h2>Client Information</h2>
        </div>
        <div class="card-body">
            <form:form action="/MmsPr/users/registration/save/" method="post" modelAttribute="client"  >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <form:hidden path = "address.id"/>
                <form:hidden path = "id"/>
                <div class="form-group row">
                    <label for="name" class="col-sm-3 col-md-2 col-form-label">Name:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id="name" path = "name"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="surname" class="col-sm-3 col-md-2 col-form-label">Surname:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id="surname" path = "surname"/>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="email" class="col-sm-3 col-md-2 col-form-label">Email:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id = "email" path = "email"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="country" class="col-sm-3 col-md-2 col-form-label">Country:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id = "country" path = "address.country"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="city" class="col-sm-3 col-md-2 col-form-label">City:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id = "city" path = "address.city"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="street" class="col-sm-3 col-md-2 col-form-label">Street:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id = "street" path = "address.street"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="house" class="col-sm-3 col-md-2 col-form-label">House:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id = "house" path = "address.house"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="flat" class="col-sm-3 col-md-2 col-form-label">Flat:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id = "flat" path = "address.flat"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="postCode" class="col-sm-3 col-md-2 col-form-label">Postal code:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id = "postCode" path = "address.postCode"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="dateOfBirth" class="col-sm-3 col-md-2 col-form-label">Date of birth:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id = "dateOfBirth" path = "dateOfBirth"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="phone" class="col-sm-3 col-md-2 col-form-label">Phone:</label>
                    <div class="col-sm-9 col-md-10">
                        <form:input class="form-control" id = "phone" path = "phone"/>
                    </div>
                </div>
                <sec:authorize access="isAnonymous()">
                    <div class="form-group row">
                        <label for="password" class="col-sm-3 col-md-2 col-form-label">Password:</label>
                        <div class="col-sm-9 col-md-10">
                            <form:password class="form-control" id = "password" path = "password"/>
                        </div>
                    </div>
                </sec:authorize>
                <div class="row">
                    <div class="col-6">
                        <button type="submit" class="btn btn-lg btn-primary">Save</button>
                    </div>
                </div>
            </form:form>
        </div>

    </div>
    <sec:authorize access="isAuthenticated()">
        <div style="margin-bottom: 1.5em">
            <a href="/MmsPr/user/registration/change-password">Change Password</a>
        </div>
    </sec:authorize>
</div>
</body>
</html>
