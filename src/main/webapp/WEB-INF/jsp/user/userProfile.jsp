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

        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'/>
        <link rel="shortcut icon" type="image/x-icon" href="<c:url value='/resources/images/favicon.ico'/>" >
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="userLk">Личный кабинет</div>
        <div class="all-tabs">
            <ul id="tabs">
                <li><a href="#">Профиль</a></li>
                <li><a href="#">Мои объявления</a></li>
                <li><a href="#">Управление рассылками</a></li>
            </ul>

            <div id="tabsText">
                <div class="profile-tabs">
                    <div class="avatar">
                        <img src='<c:url value="/resources/images/user_images/image1.jpg"/>'
                             alt="Нет фото" />
                    </div>
                    <div class="personal-data">
                        <p><c:out value="${username}" /></p>
                        <div class="control-group">
                            <label class="text-group" for="email">
                                E-mail:
                            </label>
                            <div class="controls">
                                <p id="email"><sec:authentication property="principal.username"/></p>
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
                        <p>Сохраненные параметры поиска</p>
                        <table>
                            <tbody>
                                <c:forEach items="${searchVarsList}" var="searchings" varStatus="status">
                                    <tr>
                                        <td>
                                            <c:url value="/advertisement/list.html" var="searchResult">
                                                <c:param name="currentKeyWords" value="${searchings.keyWords}"/>
                                                <c:param name="currentCategory" value="${searchings.allCategory}" />
                                            </c:url>
                                            <a href="${searchResult}"> <c:out value="${searchings.keyWords}" /> </a>
                                         </td>
                                        <td>
                                            <c:forEach items="${searchings.categories}" var="cat">
                                                 <div class="circle"></div>
                                                 <div class="nose"></div>
                                                 <div class="category">
                                                     <c:out value="${cat}" />
                                                 </div>
                                             </c:forEach>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="profile-tabs">
                    <p>Мои объявления</p>
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
                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                </c:url>
                                <a href="${advertisementViewingUrl}"><c:out value="${advertisement.title}"/>  </a>
                            </td>
                            <td class="photo"><img src='<c:url value="/resources/images/user_images/${advertisement.photoFile}"/>'
                                                   alt="Нет фото"/>
                            </td>
                            <td>
                                <div class="circle"></div>
                                <div class="nose"></div>
                                <div class="category">
                                    <c:if test="${advertisement.category.name eq 'games'}">
                                        Игры
                                    </c:if>
                                    <c:if test="${advertisement.category.name eq 'clothes'}">
                                        Одежда
                                    </c:if>
                                    <c:if test="${advertisement.category.name eq 'notclothes'}">
                                        Не одежда
                                    </c:if>
                                </div>
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