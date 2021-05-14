<%--
  Created by IntelliJ IDEA.
  User: z004331k
  Date: 14.04.2021
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Clients</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
<div align="center">
    <h1>Clients</h1>
    <table border="3" cellpadding="5">
        <tr align="center">
            <th>Number</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th>address</th>
            <th>Phone</th>
            <th>Date of birth</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${userList}" var="user" varStatus="status">
            <tr align="center">
                <th>${status.index+1}</th>
                <th>${user.name}</th>
                <th>${user.surname}</th>
                <th>${user.email}</th>

                <th>${user.address.city}</th>
                <th>${user.phone}</th>
                <th>${user.dateOfBirth}</th>
                <th>
                    <a href="/MmsPr/admin/users/delete?id=${user.id}">delete</a>
                    &nbsp;&nbsp;
                    <a href="/MmsPr/users/edit?id=${user.id}">edit</a>
                </th>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
