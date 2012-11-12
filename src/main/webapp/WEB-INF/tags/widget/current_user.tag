<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize ifAllGranted="ROLE_USER">
    <div>
        <legend><sec:authentication property="principal.username" /></legend>
        <a href="/logout">Logout <i class="icon-off"></i></a>
    </div>
</sec:authorize>