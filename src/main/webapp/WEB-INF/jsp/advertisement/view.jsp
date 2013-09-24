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
        <script type="text/javascript" src='<c:url value="/resources/js/yandexMetriс.js"/>'  language="javascript"> </script>

        <script type="text/javascript" src="//vk.com/js/api/openapi.js?100"></script>
        <script type="text/javascript" src='<c:url value="/resources/js/vkScript.js"/>'  language="javascript"> </script>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/viewStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainAsideStyle.css"/>" />
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'>
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <title>Просмотр объявления</title>
    </head>
    <body>
        <noscript><div><img src="//mc.yandex.ru/watch/22070809" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
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
                    <c:choose>
                        <c:when test="${advertisement.photoFile eq 'no_photo.png'}">
                            <img src='<c:url value="/resources/images/user_images/${advertisement.photoFile}"/>' alt="" />
                        </c:when>
                        <c:otherwise>
                            <a class="fancybox"  href='<c:url value="/resources/images/user_images/${advertisement.photoFile}"/>'>
                               <img src='<c:url value="/resources/images/user_images/${advertisement.photoFile}"/>' alt="" />
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="viewTitle">
                    Объявление от
                    <p class="contact">
                    <c:out value="${advertisement.user.email}"/>
                    </p>
                </div>
                <div class="viewTitle">
                    Создать такое же объявление
                    <c:url value="/advertisement/placing.html" var="placingLike">
                        <c:param name="id" value="${currentId}"/>
                    </c:url>
                    <a href='${placingLike}'> Тык! </a>
                </div>
                <div>
                    <c:url value="/advertisement/delete.html" var="delete">
                        <c:param name="id" value="${currentId}"/>
                    </c:url>
                    <sec:authorize ifAnyGranted="ROLE_MODERATOR">
                        <a href='${delete}' > Удалить</a>
                    </sec:authorize>
                    <sec:authorize  ifAnyGranted="ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR" >
                        <sec:authentication property="principal.username" var="advertisementOwner"/>
                        <c:if test="${advertisementOwner == advertisement.user.email}">
                            <a href='${delete}' > Удалить</a>
                        </c:if>
                    </sec:authorize>
                </div>
                <div>
                    <c:url value="/advertisement/approve.html" var="approve">
                        <c:param name="id" value="${currentId}"/>
                    </c:url>
                    <c:if test="${advertisement.is_new}">
                        <sec:authorize ifAnyGranted="ROLE_MODERATOR">
                            <a href='${approve}' > Одобрить</a>
                        </sec:authorize>
                    </c:if>
                </div>
                <div id="vk_like_1"></div>
                <script type="text/javascript">
                    VK.Widgets.Like("vk_like_1", {type: "full"});
                </script>
            </section>
            <%@ include file="/WEB-INF/jsp/advertisement/aside.jsp" %>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
        <div id="block">
            <%@ include file="/WEB-INF/jsp/advertisement/savingSearch.jsp" %>
        </div>
    </body>
</html>


















