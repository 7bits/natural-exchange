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
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js" language="javascript"></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.fancybox-1.3.4.pack.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.easing.1.3.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.mousewheel-3.0.4.pack.js"/>'></script>
        <link type="text/css" rel="stylesheet" href="<c:url value="/fancybox/jquery.fancybox-1.3.4.css"/>"  media="screen" />
        <script type="text/javascript" src='<c:url value="/resources/js/saveSearch.js"/>'  language="javascript"> </script>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' >
            <title>Вход в систему</title>
    </head>
    <body>
        <header class="centerTop">
            <div class="entryBlock">
                <a href='<c:url value="/login.html"/>' class="entry">  Вход на сайт </a>
                <%--  <a href='<c:url value="/user/registration.html"/>'class="registration">  Регистрация </a> --%>
            </div>
            <div id=logotype>
                <a href='<c:url value="/advertisement/list.html"/>'>
                   <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                </a>
                <%--   <span>Натуральный обмен</span>
                <p> Помощь молодым родителям</p>     --%>
            </div>
            <div id=lk>
            </div>
        </header>
        <div class="center">
            <a href='<c:url value="/advertisement/list.html" />'>
                Главная
            </a>
            <br/>
            <h2> Доступ запрещен.</h2>
        </div>
        <div class="plug">
            <%--<div class="commercialAdvert">   --%>
            <div>
                <p> Понравился проект? Расскажи друзьям! </p>
                <img  src='<c:url value="/resources/images/fixLike.png"/>' alt="Лайкни в вк"  />
            </div>
        </div>
        <footer class="centerTop">
            <div class="social">
                <p class="socialText"> Мы в социальных сетях: </p>
                <%-- <!-- Put this div tag to the place, where the Like block will be -->
                <div id="vk_like"></div>
                <script type="text/javascript">
                VK.Widgets.Like("vk_like", {type: "full"});
                </script>       --%>
                <img  src='<c:url value="/resources/images/social.png"/>' alt="Соц.сети"  />
            </div>
            <div class="footerLink1">
                <a href='<c:url value="/advertisement/list.html"/>'> О проекте </a>
            </div>
            <div class="footerLink">
                <a href='<c:url value="/advertisement/list.html"/>'>  Как обмениваться </a>
            </div>
            <div class="footerLink">
                <a href='<c:url value="/advertisement/list.html"/>'>  Помощь проекту </a>
            </div>
            <p class="sign"> © 2013 Natural Exchange</p>
        </footer>
    </body>
</html>