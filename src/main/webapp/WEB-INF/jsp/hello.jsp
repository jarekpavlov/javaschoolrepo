<%--
  Created by IntelliJ IDEA.
  User: z004331k
  Date: 14.04.2021
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<div align="center">
    <h1>Clients</h1>
    <h3><a hreh="new">New client</a></h3>
    <table border="1" cellpadding="5">
        <tr>
            <th>Number</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th>address</th>
            <th>Phone</th>
            <th>Date of birth</th>
        </tr>
        <c:forEach items="${clients}" var="client" varStatus="status">
            <tr>
                <th>${status.index+1}</th>
                <th>${client.name}</th>
                <th>${client.surname}</th>
                <th>${client.email}</th>
                <th>${client.address}</th>
                <th>${client.phone}</th>
                <th>${client.dateofbirth}</th>
            </tr>
        </c:forEach>

    </table>
</div>

</body>
</html>
