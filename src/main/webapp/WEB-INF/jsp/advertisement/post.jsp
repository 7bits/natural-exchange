<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <%-- <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'>  --%>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'>
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <title>Объявления</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="centerR">
            <p class="pCenter"> Рассылка новостей сайта  </p>
        </div>
        <div class="center">
            <p class="pSay"> Чтобы отправить сообщение об обновлениях сайта проекта всем подписавшимся пользователям, заполните форму ниже.</p>
            <form:form method="post" commandName="newsPostingForm">
                <div class="places postPlace">
                    <div class="left1">
                        <p>Заголовок письма:</p>
                    </div>
                    <div class="right">
                        <form:input path="newsTitle" class="placeTitle postText" placeholder="Введите текст"/>
                    </div>
                        <span class="error2"><form:errors path="newsTitle" /></span>
                    </div>
                    <div class="places postPlace">
                        <div class="left">
                            <p>Текст письма:</p>
                        </div>
                        <div class="right">
                            <form:textarea path="newsText" class="placeText postText" placeholder="Введите текст" cols="82"/>
                        </div>
                        <span class="error2"><form:errors path="newsText" /> </span>
                    </div>
                <div class="think">
                    <input type="submit" value="Отправить" class="sendAdv" />
                </div>
            </form:form>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>
