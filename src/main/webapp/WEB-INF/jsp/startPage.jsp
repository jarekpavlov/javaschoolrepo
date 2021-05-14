<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 25.04.2021
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>MmsPr Start Page</title>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/"><img src="<c:url value="/resources/images/T-Systems.png"/>"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarText">
    <sec:authorize access="isAuthenticated()">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/">Dashboard </a>
            </li>
        </ul>
    </sec:authorize>

        <div class="navbar-nav ml-auto">
            <sec:authorize access="!isAuthenticated()">
                <a href="/MmsPr/login" class="btn btn-primary btn-lg" >Log in</a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                    <form action="/MmsPr/login" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <button class="btn btn-secondary btn-lg float-right" type="submit" >Logout</button>
                    </form>
            </sec:authorize>
        </div>
    </div>
</nav>
<a href="/MmsPr/products">Products</a>
<a href="/MmsPr/admin/users">Users</a>
<a href="/MmsPr/users/registration/register">Registration</a>
<sec:authorize access="hasRole('EMPLOYEE')">
    <a href="/MmsPr/admin/orders">Admin information</a>
</sec:authorize>

<%--<img src="/productCart.jpg" />--%>


</body>
</html>
