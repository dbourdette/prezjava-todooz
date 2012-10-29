<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
            <c:if test="${not empty flashMessage}">
                <div class="alert">${flashMessage}</div>
            </c:if>

            <legend>All tasks</legend>

			<c:forEach var="task" items="${tasks}">
				<widget:task task="${task}" />
			</c:forEach>
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