<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<!doctype html>
<html lang="ru">
    <head>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js" language="javascript"></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.fancybox-1.3.4.pack.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.easing.1.3.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.mousewheel-3.0.4.pack.js"/>'></script>
        <link type="text/css" rel="stylesheet" href="<c:url value="/fancybox/jquery.fancybox-1.3.4.css"/>"  media="screen" />

        <script type="text/javascript" src='<c:url value="/resources/js/saveSearch.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/listScript.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/checkbox.js"/>'  language="javascript"> </script>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/viewStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainAsideStyle.css"/>" />
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'>
        <title>Просмотр объявления</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="center">
            <section>
                <div class="viewTitle">
                    <div class="title">
                        <c:out value="${advertisement.title}"/>
                    </div>
                    <div class="date">
                        <c:out value="${advertisement.createdDateFormat}"/>
                    </div>
                </div>
                <div class="viewCategory">
                    Категория:
                    <c:if test="${advertisement.category.name eq 'games'}">
                          <a href='<c:url value="/advertisement/list.html?keyWords=&currentCategory=+games+"/>' class="viewCate" > Игры </a>
                    </c:if>
                    <c:if test="${advertisement.category.name eq 'clothes'}">
                        <a href='<c:url value="/advertisement/list.html?keyWords=&currentCategory=+clothes+"/>' class="viewCate" > Одежда </a>
                    </c:if>
                    <c:if test="${advertisement.category.name eq 'notclothes'}">
                        <a href='<c:url value="/advertisement/list.html?keyWords=&currentCategory=+notclothes+"/>' class="viewCate" > Не одежда </a>
                    </c:if>
                </div>
                <div class="viewText">
                    <c:out value="${advertisement.text}"/>
                </div>
                <div class="photoCollection">
                    <a class="fancybox"  href='<c:url value="/resources/images/${advertisement.photoFile}"/>'>
                        <img src='<c:url value="/resources/images/${advertisement.photoFile}"/>' alt="" />
                    </a>
                </div>
                <div class="contact">
                    <img src='<c:url value="/resources/images/face.png"/>'/>
                    <c:out value="${advertisement.user.firstName}"/>
                </div>
            </section>
            <%@ include file="/WEB-INF/jsp/advertisement/aside.jsp" %>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
        <div id="block">
            <%@ include file="/WEB-INF/jsp/advertisement/savingSearch.jsp" %>
        </div>
    </body>
</html>


















