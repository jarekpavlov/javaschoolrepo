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
    <title>Registration Page</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
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
                <td>Country:</td>
                <td><form:input path = "address.country"/></td>
            </tr>
            <tr>
                <td>City:</td>
                <td><form:input path = "address.city"/></td>
            </tr>
            <tr>
                <td>Street:</td>
                <td><form:input path = "address.street"/></td>
            </tr>
            <tr>
                <td>House:</td>
                <td><form:input path = "address.house"/></td>
            </tr>
            <tr>
                <td>Flat:</td>
                <td><form:input path = "address.flat"/></td>
            </tr>
            <tr>
                <td>Postal code:</td>
                <td><form:input path = "address.postCode"/></td>
            </tr>
            <tr>
                <td>Date of birth:</td>
                <td><form:input path = "dateOfBirth"/></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><form:input path = "phone"/></td>
            </tr>
            <sec:authorize access="isAnonymous()">
                <tr>
                    <td>Password:</td>
                    <td><form:password path = "password"/></td>
                </tr>
            </sec:authorize>
            <tr>
                <input  type="submit" value="Save"/>
            </tr>
        </form:form>
    </table>
    <sec:authorize access="hasRole('USER')">
        <a href="/MmsPr/user/registration/change-password">Change Password</a>
    </sec:authorize>
</div>
</body>
</html>
