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
        <header class="centerTop">
            <div id=logotype>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
            </div>
            <div id=lk> </div>
        </header>
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