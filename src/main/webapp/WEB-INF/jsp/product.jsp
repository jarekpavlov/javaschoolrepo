<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 23.04.2021
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>New/Edit Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</head>
<body>
    <div align="center">
        <h1>Product</h1>
        <table>
            <form:form action="/MmsPr/product/save/" method="post" modelAttribute="product"  >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <form:hidden path = "id"/>
                <tr>
                    <td>Brand:</td>
                    <td><form:input path = "brand"/></td>
                </tr>
                <tr>
                    <td>Category:</td>
                    <td><form:input path = "category"/></td>
                </tr>
                <tr>
                    <td>Color:</td>
                    <td><form:input path = "color"/></td>
                </tr>
                <tr>
                    <td>Mass:</td>
                    <td><form:input path = "mass"/></td>
                </tr>
                <tr>
                    <td>Price:</td>
                    <td><form:input path = "price"/></td>
                </tr>
                <tr>
                    <td>Quantity:</td>
                    <td><form:input path = "quantity"/></td>
                </tr>
                <tr>
                    <td>Title:</td>
                    <td><form:input path = "title"/></td>
                </tr>
                <tr>
                    <td>Volume:</td>
                    <td><form:input path = "volume"/></td>
                </tr>
                <tr>
                    <input  type="submit" value="Save"/>
                </tr>
            </form:form>
        </table>

    </div>

</body>
</html>
