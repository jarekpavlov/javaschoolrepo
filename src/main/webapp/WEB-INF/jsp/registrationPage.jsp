<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 25.04.2021
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</head>
<body>
<div align="center">
    <h1>Client</h1>
    <table>
        <form:form action="/MmsPr/users/registration/save/" method="post" modelAttribute="client"  >
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <form:hidden path = "address.id"/>
            <form:hidden path = "id"/>
            <tr>
                <td>Name:</td>
                <td><form:input path = "name"/></td>
            </tr>
            <tr>
                <td>Surname:</td>
                <td><form:input path = "surname"/></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><form:input path = "email"/></td>
            </tr>
            <tr>
                <td>clientAddress:</td>
                <td><form:input path = "address.city"/></td>
            </tr>
            <tr>
                <td>Date of birth:</td>
                <td><form:input path = "dateOfBirth"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><form:input path = "password"/></td>
            </tr>
            <tr>
                <input  type="submit" value="Save"/>
            </tr>

        </form:form>
    </table>

</div>
</body>
</html>
