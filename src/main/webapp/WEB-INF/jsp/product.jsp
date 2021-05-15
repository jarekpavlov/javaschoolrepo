<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 23.04.2021
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>New/Edit Product</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
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
