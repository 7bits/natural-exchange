<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <title>Активация профиля</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
    </head>
    <body>
    <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="center">
            <a href="<c:url value="/advertisement/list.html" />">
                Главная
            </a><br/>
            Ваш профиль создан. Вам отправлено письмо с активационной ссылкой.
            Пройдите по ссылке, чтобы активировать свой аккаунт.
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>