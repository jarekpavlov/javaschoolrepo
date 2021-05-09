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
</head>
<body>
<a href="/MmsPr/products">Products</a>
<a href="/MmsPr/admin/users">Users</a>
<a href="/MmsPr/users/registration/register">Registration</a>
<sec:authorize access="hasRole('EMPLOYEE')">
    <a href="/MmsPr/admin/orders">Admin information</a>
</sec:authorize>
<form name="logout" action="/MmsPr/login" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Logout</button>
</form>
<%--<img src="/productCart.jpg" />--%>


</body>
</html>
