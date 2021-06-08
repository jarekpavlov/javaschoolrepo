<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 10.05.2021
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Change Password</title>
    <%@ include file="fragments/bootstrap.jspf" %>
</head>
<body>
    <%@ include file="fragments/navbar.jspf" %>
    <div class="container">
        <div class = "card" style="margin-top: 1.5em">
            <div class="card-header">
                <h2>Change Password</h2>
            </div>
            <div class="card-body">
                <form action="/MmsPr/user/registration/change-password" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group row">
                        <label for="newPassword1" class="col-sm-4 col-md-5 col-form-label">Enter a new password:</label>
                        <div class="col-sm-9 col-md-12">
                            <input class="form-control" required="required" minlength="2" maxlength="30" id = "newPassword1" type="password" name="newPassword1">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="newPassword2" class="col-sm-4 col-md-5 col-form-label">Repeat the new password:</label>
                        <div class="col-sm-9 col-md-12">
                            <input id="newPassword2" required="required" minlength="2" maxlength="30" class="form-control" type="password" name="newPassword2">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <button class="btn btn-lg btn-primary" type="submit" class="btn btn-primary">Change Password</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="fragments/JS.jspf" %>
</body>
</html>
