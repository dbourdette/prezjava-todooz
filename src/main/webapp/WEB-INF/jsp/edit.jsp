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
    <widget:navbar />
    
    <div class="row">
        <div class="span9">
            <legend>Edit</legend>

            <form:form commandName="task" action="/edit" method="post">
                <form:hidden path="id" />
                <label for="title">Title <form:errors path="title" cssStyle="color: red" /></label>
                <form:input id="title" path="title" class="span9" />
                <label for="date">
                    Date
                    <form:errors path="date"><span style="color:red">La date n'est pas correcte</span></form:errors>
                </label>
                <div class="input-append">
                    <form:input id="date" path="date" class="span3" />
                    <span class="add-on">dd/MM/yyyy</span>
                </div>
                <label for="tags">Tags <form:errors path="tags" cssStyle="color: red" /></label>
                <form:input id="tags" path="tags" class="span9" />
                <label for="text">Text <form:errors path="text" cssStyle="color: red" /></label>
                <form:textarea id="text" path="text" cols="60" rows="10" class="span9" />
                <button type="submit" class="btn">${empty task.id ? 'Add' : 'Update'}</button>
                <c:if test="${not empty task.id}">
                    <a href="/edit/${task.id}/delete" class="btn btn-danger">Delete</a>
                </c:if>
            </form:form>
        </div>
        <div class="span3">
            <div>
                <legend>Quick links</legend>
                <ul>
                    <li><a href="/today">Today's</a></li>
                    <li><a href="/tomorrow">Tomorrow's</a></li>
                </ul>
            </div>

            <div>
                <legend>Tags</legend>
                <c:forEach var="tag" items="${tagCloud.tags}">
                	<a href="/tag/${tag}" style="font-size:14px">${tag}</a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<script src="js/bootstrap.min.js"></script>
</body>
</html>