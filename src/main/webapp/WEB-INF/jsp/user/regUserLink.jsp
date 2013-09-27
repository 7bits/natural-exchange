<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <title>Активация профиля</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'/>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'/>
        <script type="text/javascript" src='<c:url value="/resources/js/reloadScript.js"/>'  language="javascript"> </script>
    </head>
    <body>
    <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="center">
            Ваш профиль создан. Вам отправлено письмо с активационной ссылкой.
            Пройдите по ссылке, чтобы активировать свой аккаунт.
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>