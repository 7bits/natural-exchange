<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <title>Регистрация - 2</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="center">
            Либо Вашего профиля еще нет, либо он уже аннулирован (просрочена дата). Но это еще не конец света,
            попробуйте зарегистрироваться у нас еще раз. Мы будем рады!
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>