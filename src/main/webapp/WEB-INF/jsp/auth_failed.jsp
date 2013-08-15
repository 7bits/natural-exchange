<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <title>Размещение объявления</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/errorPageStyle.css"/>" />
        <%@ include file="/WEB-INF/jsp/headInclude.jsp" %>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="center">
            <p class="comeback">
                Попробуйте еще раз, возможно Вы ввели неправильно пароль или логин. Проверьте CapsLock. <br> <br> <br>
                <a href='<c:url value="/advertisement/list.html"/>'>
                    На главную
                </a>
            </p>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>