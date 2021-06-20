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
<div class="container">
    <h1 style="margin-left: 0.5em;margin-top: 1em">Clients</h1>
    <table class="table">
        <thead class="thead-light">
            <tr align="center">
                <th scope="col">Number</th>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col">Surname</th>
                <th scope="col">Email</th>
                <th scope="col">address</th>
                <th scope="col">Phone</th>
                <th scope="col">Date of birth</th>
                <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${userList}" var="user" varStatus="status">
                <tr align="center">
                    <th scope="row">${status.index+1}</th>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.email}</td>
                    <td>${user.address.city}</td>
                    <td>${user.phone}</td>
                    <td>${user.dateOfBirth}</td>
                    <td>
                        <a href="/MmsPr/users/edit?id=${user.id}">edit</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <a class="page-link" href="#" tabindex="-1">Previous</a>
            <c:forEach begin = "1" end = "${pageQuantity}" var="page">
                <li class="page-item"><a class="page-link" href="/MmsPr/admin/users?page=${page}">${page}</a></li>
            </c:forEach>
            <a class="page-link" href="#">Next</a>
        </ul>
    </nav>
</div>
<%@ include file="fragments/JS.jspf" %>
</body>
</html>
