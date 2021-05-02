<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 30.04.2021
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h1>Login Form</h1>

<form name='login' action="" method='POST'>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <fieldset>
        <legend>Please login</legend>
        <c:if test="${param.error !=null}">
            <div>
                Failed to login
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION !=null}">
                    Reason: <c:out
                        value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                </c:if>
            </div>
        </c:if>
        <c:if test="${param.logout!=null}">
            <div>You have been logged out</div>
        </c:if>
        <p>
            <label for="username">Username</label><input type="text" id="username" name="username">
        </p>
        <p>
            <label for="password">Password</label><input type="password" id="password" name="password">
        </p>
        <div>
            <button type="submit" class="btn">Login</button>
        </div>
    </fieldset>
</form>
</body>
</html>
