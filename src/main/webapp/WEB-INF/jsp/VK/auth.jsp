<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<%-- !doctype html--%>
<html lang="ru">
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainAsideStyle.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/viewStyle.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/emailConfirm.css"/>"/>
    <%@ include file="/WEB-INF/jsp/headInclude.jsp" %>
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
</head>
<body>
<form:form method="post" action="auth.html" commandName="VkEntryEmailForm" onsubmit="return Validate();">
    <div class="vkConfirmText"> Введите свой e-mail, и Ваша регистрация будет завершена.</div>
    <form:input type="email" class="mail placeTitle" path="email"></form:input>
    <p><span class="errorLk"><form:errors path="email"/></span></p>
    <div class="buttonArea">
        <input type="submit" class="sendEmailConfirm" value="Готово"></input>
        <button class="closeEmailConfirm">Отмена</button>
    </div>
    <form:input path="first_name" type="hidden"></form:input>
    <form:input path="last_name" type="hidden"></form:input>
    <form:input path="vk_link" type="hidden"></form:input>
</form:form>
</body>
</html>
