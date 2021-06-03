<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 25.04.2021
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>MmsPr Start Page</title>
    <link href="resources/css/startPage.css" rel="stylesheet" type="text/css">
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
<div class="container">
    <div>
        <img style="width:100%;" src="<c:url value="resources/images/robot.jpg"/>"/>
    </div>
    <div class="top-right" >
        <%@ include file="fragments/cartInformation.jspf" %>
    </div>
</div>
<%@ include file="fragments/JS.jspf" %>
</body>
</html>
