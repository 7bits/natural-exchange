<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <title>Регистрация</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="center">
            <div style="color: red">Вы успешно вошли в систему. Очень Вам рады :)</div>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>