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
        <%@ include file="/WEB-INF/jsp/headInclude.jsp" %>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/viewStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/vkStyle.css"/>" />
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <script type="text/javascript" src='<c:url value="/resources/js/jquery.json-2.2.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/vkAuth.js"/>'  language="javascript"> </script>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/emailConfirm.css"/>" />
        <title>Вход в систему</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="center">
            <div class="viewTitle">
                <div class="title">
                     Вход в систему
                </div>
            </div>
            <div id=mainEntry>
                <div class="viewText"> Для размещения объявлений войдите или зарегистрируйтесь </div>
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
                        <a href='<c:url value="/advertisement/list.html"/>' class="close"> Отмена </a> <%-- id='close-window' --%>
                        <input type="submit" value="Войти" class="sendOk" />
                    </div>
                </form>
                <div class = "registrationButtonsContainer">
                    <div class = "vkEntryContainerLogin">
                        <c:url value="https://oauth.vk.com/authorize" var="vkAuth">
                            <c:param name="client_id" value="3862800"/>
                            <c:param name="scope" value="notify"/>
                            <c:param name="redirect_uri" value="http://naturalexchange.ru/VK/auth.html"/>
                            <c:param name="response_type" value="code"/>
                        </c:url>
                        <div class="vkEntry">
                            <div class="vkLogo"></div>
                            <div class="vkEntryText">Войти через Вконтакте</div>
                        </div>
                    </div>
                    <a href='<c:url value="/user/registration.html"/>'>
                        <div class="sendOk commonRegistration">Регистрация </div>
                    </a>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
        <%@ include file="/WEB-INF/jsp/advertisement/vkEmailConfirm.jsp" %>
    </body>
</html>