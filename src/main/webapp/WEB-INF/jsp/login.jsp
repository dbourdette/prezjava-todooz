<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib tagdir="/WEB-INF/tags/widget" prefix="widget" %>

<!DOCTYPE html>
<html>
<head>
    <title>Todooz</title>
    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <legend>Login</legend>

    <form class="form-horizontal" action='/j_spring_security_check' method='POST'>
        <div class="control-group">
            <label class="control-label" for="username">Email</label>
            <div class="controls">
                <input type="text" id="username" name="j_username" placeholder="Username">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="password">Password</label>
            <div class="controls">
                <input type="password" id="password" name='j_password' placeholder="Password">
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">Login</button>
            </div>
        </div>
    </form>
</div>
<script src="js/bootstrap.min.js"></script>
</body>
</html>