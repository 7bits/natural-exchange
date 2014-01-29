<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <%@ include file="/WEB-INF/jsp/headInclude.jsp" %>
        <title>Личный кабинет</title>

        <script type="text/javascript" src="//vk.com/js/api/openapi.js?100"></script>
        <script type="text/javascript" src='<c:url value="/resources/js/jquery-2.0.3.min.js"/>' language="javascript"></script>
        <script type="text/javascript" src='<c:url value="/resources/js/vkScript.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src="<c:url value="/resources/js/userProfile.js"/>" language="javascript"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.tools.min.js"/>"></script>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/userProfile.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainAsideStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />

        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'/>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'/>
        <link rel="shortcut icon" type="image/x-icon" href="<c:url value='/resources/images/favicon.ico'/>" >
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/user/headerProfile.jsp" %>
        <div class="lk-profile">
            <img src='<c:url value="/resources/images/avatars/${userAvatar}"/>' alt="Нет фото" />
            <div>
                <p id="username-profile"><c:out value="${username}" /></p>
                <p class="profile">Личный кабинет</p>
            </div>

        </div>
        <div class="userLk">
            <div class="profile">Личный кабинет</div>
            <a class="userLk-out" href="<c:url value="/user/logout.html"/>" > Выйти </a>
        </div>
        <div class="all-tabs">
            <ul id="tabs">
                <li><a href="#">Профиль</a></li>
                <li><a href="#">Мои объявления</a></li>
                <li><a href="#">Управление рассылками</a></li>
            </ul>

            <div id="tabsText">
                <div class="profile-tabs">
                    <div class="avatar">
                        <img src='<c:url value="/resources/images/avatars/${userAvatar}"/>' alt="Нет фото" />
                    </div>
                    <div class="personal-data">
                        <p class="username"><c:out value="${username}" /></p>
                        <div class="control-group">
                            <label class="text-group" for="email">
                                E-mail:
                            </label>
                            <div class="controls">
                                <p id="email" class="userEmail">
                                    <sec:authentication property="principal.username"/>
                                </p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="text-group" for="phone">
                                Телефон:
                            </label>
                            <div class="controls">
                                <p id="phone">8-(908)-000-0000</p>
                            </div>
                        </div>
                    </div>
                    <div class="search-vars">
                        <p class="mediumSizeFont">Сохраненные параметры поиска</p>
                        <table>
                            <tbody>
                                <c:forEach items="${searchVarsList}" var="searchings" varStatus="status">
                                    <c:choose>
                                        <c:when test="${status.index%2==0}">
                                            <tr class="row">
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>
                                        <div>
                                        <img class="flower-row"
                                             src='<c:url value="/resources/images/flower.png"/>'
                                             alt=""/>
                                        </div>

                                            <c:url value="/advertisement/list.html" var="searchResult">
                                                <c:param name="currentKeyWords" value="${searchings.keyWords}"/>
                                                <c:param name="currentCategory" value="${searchings.allCategory}" />
                                            </c:url>
                                            <a class="userLk-href" href="${searchResult}" >
                                                <c:out value="${searchings.keyWords}" />
                                            </a>

                                        <c:forEach items="${searchings.categories}" var="cat">
                                            <c:choose>
                                                <c:when test="${status.index%2==0}">
                                                    <div class="circle-row"></div>
                                                    <div class="nose-row"></div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="circle"></div>
                                                    <div class="nose"></div>
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="category">
                                                <c:if test="${cat eq 'games'}">
                                                    Игры
                                                </c:if>
                                                <c:if test="${cat eq 'clothes'}">
                                                    Одежда
                                                </c:if>
                                                <c:if test="${cat eq 'notclothes'}">
                                                    Не одежда
                                                </c:if>
                                            </div>
                                        </c:forEach>
                                    </td>
                                    <td class="td-del-kWrds">
                                        <c:url value="/user/delKeyWords.html" var="delete">
                                            <c:param name="currentKeyWords" value="${searchings.keyWords}"/>
                                            <c:param name="currentCategory" value="${searchings.allCategory}" />
                                        </c:url>
                                        <c:url value="/user/editKeyWords.html" var="edit">
                                            <c:param name="currentKeyWords" value="${searchings.keyWords}"/>
                                            <c:param name="currentCategory" value="${searchings.allCategory}" />
                                        </c:url>
                                        <div class="controlButtons">
                                            <a class="deleteButtonImage" href='${delete}' ></a>
                                            <a class="editButtonImage" href='${edit}' ></a>
                                        </div>
                                    </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="profile-tabs">
                    <div>Мои объявления</div>
                    <a href='<c:url value="/advertisement/placing.html"/>' rel="nofollow" class="tab-panel">
                            Разместить объявление
                    </a>

                    <table>
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
                                    <td class="title">
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
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="profile-tabs">
                    <div class="pCenter">
                        Управление рассылками
                    </div>
                </div>
            </div>
        </div>

        <div class="footer-bottom">
            <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
        </div>
    </body>
</html>