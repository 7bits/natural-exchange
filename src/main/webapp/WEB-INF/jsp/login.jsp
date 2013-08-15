<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%-- !doctype html--%>
<html lang="ru">
    <head>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/viewStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' >
        <script type="text/javascript" src='<c:url value="/resources/js/loginWindow.js"/>'  language="javascript"> </script>
        <title>Вход в систему</title>
    </head>
    <body>
        <div id=mainEntry>
            <form method="POST"   action="<c:url value="/j_spring_security_check"/>"  >
                <div class="places">
                    <div class="left1"><p> E-mail: </p></div>
                    <div class="right"><input type="text" name="j_username"  class="logus"/></div>
                </div>
                <div class="places">
                    <div class="left1"> <p>Пароль: </p> </div>
                    <div class="right"><input type="password" name="j_password"  class="logus"/></div>
                </div>
                <div class="buttonArea">
                    <p class="close" id='close-window'> Отмена </p>
                    <input type="submit" value="Войти" class="sendOk" />
                </div>
            </form>
        </div>
    </body>
</html>