<!doctype html>
<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if lt IE 7]> <html class="b-error-page no-js ie6 oldie" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="b-error-page no-js ie7 oldie" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="b-error-page no-js ie8 oldie" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="b-error-page no-js" lang="en"> <!--<![endif]-->
<%@ include file="/WEB-INF/jsp/include.jsp" %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@ include file="/WEB-INF/jsp/headInclude.jsp" %>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <title>400</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="center">
           400 Bad Request
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>