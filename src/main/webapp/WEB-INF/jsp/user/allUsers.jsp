<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page session="false"%>

<!doctype html>
<html lang="ru">
    <head>
        <!-- Put this script tag to the <head> of your page -->
        <%@ include file="/WEB-INF/jsp/headInclude.jsp" %>
        <script type="text/javascript" src='<c:url value="/resources/js/checkboxModerator.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/tableCut.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/yandexMetriс.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/vkAuth.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/jquery.json-2.2.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/calendar.js"/>' language="javascript"> </script>

        <!-- to download, for calendar -->
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainAsideStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/emailConfirm.css"/>" />

        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'/>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'/>
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <title>Натуральный обмен</title>
    </head>
    <body>
    <!-- Yandex.Metrika counter -->
    <noscript><div><img src="//mc.yandex.ru/watch/22070809" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
    <!-- /Yandex.Metrika counter -->
    <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
    <div class="center">
        <c:url value="https://oauth.vk.com/authorize" var="vkAuth">
            <c:param name="client_id" value="3862800"/>
            <c:param name="scope" value="notify"/>
            <c:param name="redirect_uri" value="http://naturalexchange.ru/VK/auth.html"/>
            <c:param name="response_type" value="code"/>
        </c:url>
        <section>
            <table>
                <thead>
                    <tr>
                    <!--th>
                    Автор
                    </th-->
                        <th class="date">

                            <%--<c:set var="sortDate" value="${!sortDate}"/> --%>
                            <c:url value="/moderator/userlist.html" var="dateSortingUrl">
                                <c:param name="sortOrder" value="${sortOrderDate}"/>
                            </c:url>
                            Дата регистрации
                            <c:choose>
                                <c:when  test="${sortOrderDate eq 'ASCENDING'}" >
                                    <a href="${dateSortingUrl}">
                                        <img class="sort" src='<c:url value="/resources/images/sort_1.png"/>' alt="Сорт">
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${dateSortingUrl}">
                                        <img class="sort" src='<c:url value="/resources/images/sort_2.png"/>' alt="Сорт">
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </th>
                        <th class="first-name">
                            Имя
                        </th>
                        <th class="email">
                            Email
                        </th>
                        <th class="ban-button">
                            Забанить/ <br>
                            Разбанить
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="currentUser" varStatus="status">
                        <c:choose>
                            <c:when test="${status.index%2==0}">
                                <tr>
                            </c:when>
                            <c:otherwise>
                                <tr class="tr1">
                            </c:otherwise>
                        </c:choose>
                            <td class="date"><c:out value="${currentUser.createdDateFormat}"/></td>
                            <td class="first-name">
                                <c:out value="${currentUser.firstName} ${currentUser.lastName}"/>
                            </td>
                            <td class="email"><c:out value="${currentUser.email}"/></td>
                            <td class="ban-button">
                                <c:choose>
                                    <c:when test="${currentUser.isBanned == false}">
                                        <form action = "<c:url value='/moderator/banuser.html'>
                                                            <c:param name='email' value='${currentUser.email}'/>
                                                            <c:param name="ban" value=""/>
                                                        </c:url>" method="POST">
                                            <input type="submit" name="ban-action" value="Забанить" />
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action = "<c:url value='/moderator/banuser.html'>
                                                            <c:param name='email' value='${currentUser.email}'/>
                                                            <c:param name="ban" value="banned"/>
                                                        </c:url>" method="POST">
                                            <input type="submit" name="ban-action" value="Разбанить" />
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!-- ******************* pagination  ************************  -->
            <div>
                <div>
                <!-- выбор размера страницы -->
                <p class="pagination1">  Пользователей на странице   </p>
                </div>
                <div>
                    <form action="${listUrl}" method="get" class="pagination">
                        <div class="pageSize">
                            <select name="pageSize">
                                <option <c:if test="${pageSize == 3}">selected</c:if> value="3">3</option>
                                <option <c:if test="${pageSize == 5}">selected</c:if> value="5">5</option>
                                <option <c:if test="${pageSize == 7}">selected</c:if> value="7">7</option>
                                <option <c:if test="${pageSize == 15}">selected</c:if> value="15">15</option>
                            </select>
                        </div>
                        <input type="hidden" name="sortOrder" value="${sortOrderDate}"/>
                        <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                        <input type="hidden" name="currentKeyWords" value="${currentKeyWords}"/>
                        <input type="submit" value="OK" class="ok"/>
                    </form>
                </div>

                <!-- ******************** переходы по страницам ******************** -->
                <%--               текущая страница: <em><c:out value="${currentPage}"/></em> --%>
                <div class="pagination2">
                    <c:if test="${currentPage!=0}">
                        <c:url  value="/moderator/userlist.html" var="prevPageUrl">
                            <c:param name="sortOrder" value="${sortOrderDate}"/>
                            <c:param name="pageSize" value="${pageSize}"/>
                            <c:param name="currentPage" value="${currentPage - 1}"/>
                            <c:param name="currentCategory" value="${currentCategory}"/>
                            <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                            <c:param name="currentDateFrom" value="${currentDateFrom}"/>
                            <c:param name="currentDateTo" value="${currentDateTo}"/>
                        </c:url>
                        <a href="${prevPageUrl}" class="going"> << </a>
                    </c:if>
                    <c:choose>
                        <c:when test="${currentPage==0}">
                            <span class="act"> 1 </span>
                            <c:if test="${noOfPage>1}">
                                <c:url value="/moderator/userlist.html" var="pageUrl" >
                                    <c:param name="sortOrder" value="${sortOrderDate}"/>
                                    <c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="currentPage" value="1"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                    <c:param name="currentDateFrom" value="${currentDateFrom}"/>
                                    <c:param name="currentDateTo" value="${currentDateTo}"/>
                                </c:url>
                                <a href="${pageUrl}" class="number"> 2 </a>
                            </c:if>
                            <c:if test="${noOfPage>3}">
                                <span class="number"> ... </span>
                            </c:if>
                            <c:if test="${noOfPage>2}">
                                <c:url value="/moderator/userlist.html" var="pageUrl" >
                                    <c:param name="sortOrder" value="${sortOrderDate}"/>
                                    <c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="currentPage" value="${noOfPage - 1}"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                    <c:param name="currentDateFrom" value="${currentDateFrom}"/>
                                    <c:param name="currentDateTo" value="${currentDateTo}"/>
                                </c:url>
                                <a href="${pageUrl}" class="number"><c:out value="${noOfPage}"></c:out></a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:url value="/moderator/userlist.html" var="pageUrl" >
                                <c:param name="sortOrder" value="${sortOrderDate}"/>
                                <c:param name="pageSize" value="${pageSize}"/>
                                <c:param name="currentPage" value="0"/>
                                <c:param name="currentCategory" value="${currentCategory}"/>
                                <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                <c:param name="currentDateFrom" value="${currentDateFrom}"/>
                                <c:param name="currentDateTo" value="${currentDateTo}"/>
                            </c:url>
                            <a href="${pageUrl}" class="number"> 1</a>
                            <c:choose>
                                <c:when test="${currentPage==1}">
                                    <span class="act"> 2 </span >
                                    <c:if test="${noOfPage>3}">
                                        <span class="number"> ... </span>
                                    </c:if>
                                    <c:if test="${noOfPage>2}">
                                        <c:url value="/moderator/userlist.html" var="pageUrl" >
                                            <c:param name="sortOrder" value="${sortOrderDate}"/>
                                            <c:param name="pageSize" value="${pageSize}"/>
                                            <c:param name="currentPage" value="${noOfPage - 1}"/>
                                            <c:param name="currentCategory" value="${currentCategory}"/>
                                            <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                            <c:param name="currentDateFrom" value="${currentDateFrom}"/>
                                            <c:param name="currentDateTo" value="${currentDateTo}"/>
                                        </c:url>
                                        <a href="${pageUrl}" class="number"><c:out value="${noOfPage}"></c:out></a>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${noOfPage>3}">
                                        <span class="number"> ... </span>
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${currentPage!=(noOfPage-1)}">
                                            <span class="act"> <c:out value="${currentPage+1}"></c:out></span>
                                            <c:if test="${currentPage!=(noOfPage-2)}">
                                                <span class="number"> ... </span>
                                            </c:if>
                                                <c:url value="/moderator/userlist.html" var="pageUrl" >
                                                    <c:param name="sortOrder" value="${sortOrderDate}"/>
                                                    <c:param name="pageSize" value="${pageSize}"/>
                                                    <c:param name="currentPage" value="${noOfPage - 1}"/>
                                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                                    <c:param name="currentDateFrom" value="${currentDateFrom}"/>
                                                    <c:param name="currentDateTo" value="${currentDateTo}"/>
                                                </c:url>
                                                <a href="${pageUrl}" class="number"><c:out value="${noOfPage}"></c:out></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:url value="/moderator/userlist.html" var="pageUrl" >
                                                <c:param name="sortOrder" value="${sortOrderDate}"/>
                                                <c:param name="pageSize" value="${pageSize}"/>
                                                <c:param name="currentPage" value="${noOfPage - 2}"/>
                                                <c:param name="currentCategory" value="${currentCategory}"/>
                                                <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                                <c:param name="currentDateFrom" value="${currentDateFrom}"/>
                                                <c:param name="currentDateTo" value="${currentDateTo}"/>
                                            </c:url>
                                            <a href="${pageUrl}" class="number"><c:out value="${noOfPage-1}"></c:out></a>
                                            <span class="act"> <c:out value="${noOfPage}"></c:out></span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${currentPage < (noOfPage-1)}">
                        <c:url  value="/moderator/userlist.html" var="nextPageUrl">
                            <c:param name="sortOrder" value="${sortOrderDate}"/>
                            <c:param name="pageSize" value="${pageSize}"/>
                            <c:param name="currentPage" value="${currentPage + 1}"/>
                            <c:param name="currentCategory" value="${currentCategory}"/>
                            <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                            <c:param name="currentDateFrom" value="${currentDateFrom}"/>
                            <c:param name="currentDateTo" value="${currentDateTo}"/>
                        </c:url>
                        <a href="${nextPageUrl}" class="going next"> >> </a>
                    </c:if>
                </div>
            </div>
            <!-- ******************* /pagination  ************************  -->
        </section>
        <%@ include file="/WEB-INF/jsp/user/asideModerator.jsp" %>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>
