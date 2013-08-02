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
        <title>Объявления</title>
    </head>

    <body>
        <header class="centerTop">
            <div id=logotype>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
            </div>
        </header>
<!-- *****************************/header***************************** -->
	<h1>Рассылка новостей сайта</h1>

чтобы отправить сообщение об обновлениях сайта проекта всем подписавшимся пользователям, заполните форму ниже.
	<form:form method="post" commandName="newsPostingForm">
	Заголовок письма:
        <form:input path="newsTitle" />
        <span class="error"><form:errors path="newsTitle" /></span>

    Текст письма:
        <form:input type="textarea" path="newsText" />
        <span class="error"><form:errors path="newsText" /></span>
        <input type="submit" value="Отправить" />

	</form:form>


<!-- *****************************footer***************************** -->

  <footer class="centerTop">
                    <div id=likes>
                    <p text-align="right" > Мы в социальных сетях </p>

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
