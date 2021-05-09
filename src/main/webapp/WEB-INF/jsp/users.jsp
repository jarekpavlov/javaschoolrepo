<%--
  Created by IntelliJ IDEA.
  User: z004331k
  Date: 14.04.2021
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Clients</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</head>
<body>
<div align="center">
    <h1>Clients</h1>
    <table border="3" cellpadding="5">
        <tr>
            <th>Number</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th>address</th>
            <th>Phone</th>
            <th>Date of birth</th>
        </tr>
        <c:forEach items="${userList}" var="user" varStatus="status">
            <tr>
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
