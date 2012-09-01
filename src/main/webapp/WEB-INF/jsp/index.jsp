<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib tagdir="/WEB-INF/tags/widget" prefix="widget" %>

<!DOCTYPE html>
<html>
<head>
    <title>Todooz</title>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <widget:navbar />
    
    <div class="row">
        <div class="span9">
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
                <a href="/tag/java" style="font-size:14px">java</a>
                <a href="/tag/java" style="font-size:20px">java</a>
                <a href="/tag/java" style="font-size:16px">java</a>
                <a href="/tag/java" style="font-size:12px">java</a>
                <a href="/tag/java" style="font-size:10px">java</a>
                <a href="/tag/java" style="font-size:22px">java</a>
                <a href="/tag/java" style="font-size:12px">java</a>
                <a href="/tag/java" style="font-size:14px">java</a>
                <a href="/tag/java" style="font-size:18px">java</a>
                <a href="/tag/java" style="font-size:24px">java</a>
                <a href="/tag/java" style="font-size:12px">java</a>
                <a href="/tag/java" style="font-size:10px">java</a>
                <a href="/tag/java" style="font-size:14px">java</a>
            </div>
        </div>
    </div>
</div>
<script src="js/bootstrap.min.js"></script>
</body>
</html>