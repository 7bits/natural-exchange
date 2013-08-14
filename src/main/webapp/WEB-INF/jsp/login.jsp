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
        <title>Вход в систему</title>
    </head>
    <body>
        <header class="centerTop">
            <div id=logotype>
                <a href='<c:url value="/advertisement/list.html"/>'>
                    <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                </a>
            </div>
        </header>

        <div class="centerR">
            <p class="pCenter titleSave"> Вход в систему </p>
        </div>
        <div class="center">
            <form method="POST"   action="<c:url value="/j_spring_security_check"/>"  >
                <div class="places">
                    <div class="left1">
                        <p>Пароль:</p>
                    </div>
                    <div class="right">
                        <input type="password" name="j_password"  class="logus"/>
                    </div>
                </div>
                <div class="places">
                    <div class="left1">
                        <p>E-mail:</p>
                    </div>
                    <div class="right">
                        <input type="text" name="j_username"  class="logus"/>
                    </div>
                </div>
                <div class="buttonArea">
                    <input type="reset" value="Отмена" class="close" />
                    <input type="submit" value="Войти" class="sendOk" />
                </div>
            </form>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>