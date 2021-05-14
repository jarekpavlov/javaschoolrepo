<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 10.05.2021
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Change Password</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
    <%@ include file="fragments/navbar.jspf" %>
    <form action="/MmsPr/user/registration/change-password" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <h1>Changing the password</h1>
        <label>Enter a new password:</label>
        <input type="password" name="newPassword1">
        <label>Repeat the new password:</label>
        <input type="password" name="newPassword2">
        <input type="submit" class="btn btn-primary" value="Change Password"/>
    </form>
</body>
</html>
