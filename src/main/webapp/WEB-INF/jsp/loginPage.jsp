<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 30.04.2021
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Login</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
<%@ include file="fragments/navbar.jspf" %>
<div class="container">
    <div class="card" style="margin-top: 1.5em">
        <div class="card-header"><h2>Please login</h2></div>
        <div class="card-body">
            <form name='login' action="" method='POST'>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <fieldset>
                    <c:if test="${param.error !=null}">
                        <div class="alert alert-danger" role="alert">Failed to login</div>
                        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION !=null}">
                            Reason:
                                <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                        </c:if>
                    </c:if>
                    <c:if test="${param.logout!=null}">
                        You have been logged out
                    </c:if>
                    <div class="form-group row">
                        <label for="username" class="col-sm-3 col-md-2 col-form-label">Username:</label>
                        <div class="col-sm-9 col-md-10 ">
                            <input type="text" class="form-control" id="username" name="username">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="password" class="col-sm-3 col-md-2 col-form-label">Password:</label>
                        <div class="col-sm-9 col-md-10 ">
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                    </div>
                    <sec:authorize access="!isAuthenticated()">
                        <div class="row">
                            <div class="col-6">
                                <button type="submit" class="btn btn-lg btn-primary">Login</button>
                            </div>
                            <div class="col-6">
                                <a href="/MmsPr/users/registration/register" class="btn btn-lg btn-info float-right">Register</a>
                            </div>
                        </div>
                    </sec:authorize>
                </fieldset>
            </form>
        </div>
        </div>
    </div>
</div>
</body>
</html>
