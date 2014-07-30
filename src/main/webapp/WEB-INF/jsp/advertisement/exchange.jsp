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

    <script type="text/javascript" src='<c:url value="/resources/js/listScript.js"/>'  language="javascript"> </script>
    <script type="text/javascript" src='<c:url value="/resources/js/checkbox.js"/>'  language="javascript"> </script>
    <script type="text/javascript" src='<c:url value="/resources/js/yandexMetriс.js"/>'  language="javascript"> </script>

    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/viewStyle.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainAsideStyle.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
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
        <div class="categoryAndActions">
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
                <c:if test="${isNotAnonym}">
                    <c:url value="/advertisement/exchange.html" var="exchangeViewingUrl">
                        <c:param name="id" value="${advertisement.id}"/>
                        <c:param name="currentCategory" value="${currentCategory}"/>
                    </c:url>
                    <a href="${exchangeViewingUrl}">обмен</a>
                </c:if>
            </div>
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
        <form:form method="post" action="exchange.html" commandName="exchangeForm" onsubmit="return Validate();">
            <div class="viewText">На что меняем</div>
            <div class="user-advertisement-table">
                <table>
                    <form:input path="idExchangeOwnerAdvertisement" type="hidden"></form:input>
                    <tbody>
                    <c:forEach items="${adverts}" var="advertisement" varStatus="status">
                        <c:choose>
                            <c:when test="${status.index%2==0}">
                                <tr>
                            </c:when>
                            <c:otherwise>
                                <tr class="tr1">
                            </c:otherwise>
                        </c:choose>

                        <td>
                            <form:radiobutton path="idExchangeOfferAdvertisement" value="${advertisement.id}"></form:radiobutton>
                        </td>

                        <td>
                            <c:url value="/advertisement/view.html" var="advertisementViewingUrl">
                                <c:param name="id" value="${advertisement.id}"/>
                                <c:param name="currentCategory" value="${advertisement.category.name}"/>
                            </c:url>
                            <a href="${advertisementViewingUrl}"><c:out value="${advertisement.title}"/>  </a>
                        </td>
                        <td class="photo">
                            <img src='<c:url value="/resources/images/user_images/${advertisement.photoFile}"/>'
                                 alt="Нет фото"/>
                        </td>
                        <td>
                            <c:if test="${advertisement.category.name eq 'games'}">
                                Игры
                            </c:if>
                            <c:if test="${advertisement.category.name eq 'clothes'}">
                                Одежда
                            </c:if>
                            <c:if test="${advertisement.category.name eq 'notclothes'}">
                                Не одежда
                            </c:if>
                        </td>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <form:textarea path="exchangePropose" rows="10" cols="45" placeholder="Ваше сообщение владельцу вещи"></form:textarea>
            <p><input path="" type="submit" value="отправить"></p>
        </form:form>
    </section>
</div>
<%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
</body>
</html>