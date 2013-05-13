<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>

<html lang="ru">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Hello world</title>

    </head>
    <body class="">
        <div class="">
            Зарегистрируйтесь, пожалуйста!
        </div>
        <f:form method='POST' action='registrationRequest.html'>
        Имя пользователя: <input type='text' name='userName' value=''>
        <input type='submit' name='Submit'>
        </f:form>
    </body>
</html>
