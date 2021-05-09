<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 08.05.2021
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Statistic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
</head>
<body>
    <div align="center">
        <h1>Statistic for the last ${daysAgo} days</h1>
        <h3>Best client</h3>
        <table cellpadding="3" border="5" >
            <tr>
                <th>Number</th>
                <th>Client</th>
                <th>Total amount per client</th>
            </tr>
            <c:forEach items="${bestClientTree}" var="bestClient" varStatus="status">
                <tr align="center">
                    <th>${status.index+1}</th>
                    <th>${bestClient.client_id}</th>
                    <th>${bestClient.resultAmount}</th>
                </tr>
            </c:forEach>
        </table>
        <h3>Best selling product</h3>
        <table cellpadding="3" border="5">
            <tr>
                <th>Number</th>
                <th>Product</th>
                <th>Total amount per product</th>
            </tr>
            <c:forEach items="${bestProductTree}" var="bestProduct" varStatus="status">
                <tr align="center">
                    <th>${status.index+1}</th>
                    <th>${bestProduct.product_id}</th>
                    <th>${bestProduct.resultAmount}</th>
                </tr>
            </c:forEach>
        </table>
        <h3>Sales amount</h3>
        <h4>${total.resultSum}</h4>
    </div>

</body>
</html>
