<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!doctype html>
<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if lt IE 7]> <html class="b-error-page no-js ie6 oldie" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="b-error-page no-js ie7 oldie" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="b-error-page no-js ie8 oldie" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="b-error-page no-js" lang="en"> <!--<![endif]-->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@ include file="/WEB-INF/jsp/headInclude.jsp" %>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/errorPageStyle.css"/>" />
        <title>400</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="center">
            <div class="oopses">
                <div class="error-title">
                   Ошибка 400
                </div>
                <div class="error-text">
                    Упс! Что-то пошло не так :-(   <br>
                    Но мы делаем все возможное,    <br>
                    чтобы это исправить
                </div>
                <img src='<c:url value="/resources/images/fixLike.png"/>' class="oops" />
            </div>
            <p class="comeback">
                Вы можете вернуться
                <a href='<c:url value="/advertisement/list.html"/>'>
                    На главную
                </a>
                :-)
            </p>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>