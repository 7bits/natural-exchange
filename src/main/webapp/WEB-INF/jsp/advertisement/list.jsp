<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page session="false"%>

<!doctype html>
<html lang="ru">
    <head>
        <!-- Put this script tag to the <head> of your page -->
        <%@ include file="/WEB-INF/jsp/headInclude.jsp" %>

        <script type="text/javascript" src='<c:url value="/resources/js/saveSearch.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/checkbox.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/tableCut.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/yandexMetriс.js"/>'  language="javascript"> </script>

        <script type="text/javascript" src='<c:url value="/resources/js/vkAuth.js"/>'  language="javascript"> </script>

        <script type="text/javascript" src='<c:url value="/resources/js/jquery.json-2.2.js"/>'  language="javascript"> </script>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainAsideStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/emailConfirm.css"/>" />

        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'/>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'/>
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <!-- Put this script tag to the <head> of your page -->
        <script type="text/javascript" src="//vk.com/js/api/openapi.js?100"></script>
        <script type="text/javascript" src='<c:url value="/resources/js/vkScript.js"/>'  language="javascript"> </script>
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
            <div>

                <div class="vkAuth"> Войти через контакт </div>
            </div>
            <section>
                <table>
                    <thead>
                        <tr>
                            <!--th>
                                Автор
                            </th-->
                            <th class="date">

                                <%--<c:set var="sortDate" value="${!sortDate}"/> --%>
                                <c:url value="/advertisement/list.html" var="dateSortingUrl">
                                    <c:param name="sortedBy" value="${sortedByDate}"/>
                                    <c:param name="sortOrder" value="${sortOrderDate}"/>
                                    <c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                </c:url>
                                Дата
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
                            <th class="title">
                                <%--<c:set var="sortTitle" value="${!sortTitle}"/> --%>
                                <c:url value="/advertisement/list.html" var="titleSortingUrl">
                                    <c:param name="sortedBy" value="${sortedByTitle}"/>
                                    <c:param name="sortOrder" value="${sortOrderTitle}"/>
                                    <c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                </c:url>
                                Заголовок
                                <c:choose>
                                    <c:when  test="${sortOrderTitle eq 'ASCENDING'}" >
                                        <a href="${titleSortingUrl}">
                                            <img class="sort" src='<c:url value="/resources/images/sort_1.png"/>' alt="Сорт">
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${titleSortingUrl}">
                                            <img class="sort" src='<c:url value="/resources/images/sort_2.png"/>' alt="Сорт">
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </th>
                            <th class="text">
                                Описание
                            </th>
                            <th class="photo">
                                Фото
                            </th>
                            <th class="category">
                                Категория
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${advertisements}" var="advertisement" varStatus="status">
                            <c:choose>
                                <c:when test="${status.index%2==0}">
                                    <tr>
                                </c:when>
                                <c:otherwise>
                                    <tr class="tr1">
                                </c:otherwise>
                            </c:choose>
                                        <td class="date"><c:out value="${advertisement.createdDateFormat}"/></td>
                                        <td class="title">
                                            <c:url value="/advertisement/view.html" var="advertisementViewingUrl">
                                                <c:param name="id" value="${advertisement.id}"/>
                                                <c:param name="currentCategory" value="${currentCategory}"/>
                                            </c:url>
                                            <a href="${advertisementViewingUrl}"><c:out value="${advertisement.title}"/>  </a>
                                        </td>
                                        <td class="text" name="text" ><c:out value="${advertisement.text}"/></td>
                                        <td class="photo"><img src='<c:url value="/resources/images/user_images/${advertisement.photoFile}"/>' alt="Нет фото"/></td>
                                        <td class="category">
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
            <!-- ******************* pagination  ************************  -->
                <div>
                    <div>
                        <!-- выбор размера страницы -->
                        <p class="pagination1">  Объявлений на странице   </p>
                    </div>
                    <div>
                        <form action="${listUrl}" method="get" class="pagination">
                            <div class="pageSize">
                                <select name="pageSize" >
                                    <option value="${pageSize}"> <c:out value="${pageSize}"> </c:out> </option>
                                    <c:if test="${pageSize!=2}">
                                        <option value="${defaultPageSize}"><c:out value="${defaultPageSize}"></c:out></option>
                                    </c:if>
                                    <c:if test="${pageSize!=5}">
                                        <option value="5">5</option>
                                    </c:if>
                                    <c:if test="${pageSize!=15}">
                                        <option value="15">15</option>
                                    </c:if>
                                    <c:if test="${pageSize!=3}">
                                        <option value="3">3</option>
                                    </c:if>
                                </select>
                            </div>
                            <input type="hidden" name="sortedBy" value="${currentColumn}"/>
                            <input type="hidden" name="sortOrder" value="${currentSortOrder}"/>
                            <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                            <input type="hidden" name="currentKeyWords" value="${currentKeyWords}"/>
                            <input type="submit" value="OK" class="ok"/>
                        </form>
                    </div>

            <!-- ******************** переходы по страницам ******************** -->
                <%--               текущая страница: <em><c:out value="${currentPage}"/></em> --%>
                    <div class="pagination2">
                        <c:if test="${currentPage!=0}">
                            <c:url  value="/advertisement/list.html" var="prevPageUrl">
                                <c:param name="pageSize" value="${pageSize}"/>
                                <c:param name="currentPage" value="${currentPage-1}"/>
                                <c:param name="sortOrder" value="${currentSortOrder}"/>
                                <c:param name="sortedBy" value="${currentColumn}"/>
                                <c:param name="currentCategory" value="${currentCategory}"/>
                                <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                            </c:url>
                            <a href="${prevPageUrl}" class="going"> << </a>
                        </c:if>
                        <c:choose>
                            <c:when test="${currentPage==0}">
                                <span class="act"> 1 </span>
                                <c:if test="${noOfPage>1}">
                                    <c:url value="/advertisement/list.html" var="pageUrl" >
                                        <c:param name="pageSize" value="${pageSize}"/>
                                        <c:param name="sortOrder" value="${currentSortOrder}"/>
                                        <c:param name="sortedBy" value="${currentColumn}"/>
                                        <c:param name="currentPage" value="1"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                        <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                    </c:url>
                                    <a href="${pageUrl}" class="number"> 2 </a>
                                </c:if>
                                <c:if test="${noOfPage>3}">
                                    <span class="number"> ... </span>
                                </c:if>
                                <c:if test="${noOfPage>2}">
                                    <c:url value="/advertisement/list.html" var="pageUrl" >
                                        <c:param name="pageSize" value="${pageSize}"/>
                                        <c:param name="sortOrder" value="${currentSortOrder}"/>
                                        <c:param name="sortedBy" value="${currentColumn}"/>
                                        <c:param name="currentPage" value="${noOfPage-1}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                        <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                    </c:url>
                                    <a href="${pageUrl}" class="number"><c:out value="${noOfPage}"></c:out></a>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:url value="/advertisement/list.html" var="pageUrl" >
                                    <c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="sortOrder" value="${currentSortOrder}"/>
                                    <c:param name="sortedBy" value="${currentColumn}"/>
                                    <c:param name="currentPage" value="0"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                </c:url>
                                <a href="${pageUrl}" class="number"> 1</a>
                                <c:choose>
                                    <c:when test="${currentPage==1}">
                                        <span class="act"> 2 </span >
                                        <c:if test="${noOfPage>3}">
                                            <span class="number"> ... </span>
                                        </c:if>
                                        <c:if test="${noOfPage>2}">
                                            <c:url value="/advertisement/list.html" var="pageUrl" >
                                                <c:param name="pageSize" value="${pageSize}"/>
                                                <c:param name="sortOrder" value="${currentSortOrder}"/>
                                                <c:param name="sortedBy" value="${currentColumn}"/>
                                                <c:param name="currentPage" value="${noOfPage-1}"/>
                                                <c:param name="currentCategory" value="${currentCategory}"/>
                                                <c:param name="currentKeyWords" value="${currentKeyWords}"/>
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
                                                <c:url value="/advertisement/list.html" var="pageUrl" >
                                                    <c:param name="pageSize" value="${pageSize}"/>
                                                    <c:param name="sortOrder" value="${currentSortOrder}"/>
                                                    <c:param name="sortedBy" value="${currentColumn}"/>
                                                    <c:param name="currentPage" value="${noOfPage-1}"/>
                                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                                </c:url>
                                                <a href="${pageUrl}" class="number"><c:out value="${noOfPage}"></c:out></a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:url value="/advertisement/list.html" var="pageUrl" >
                                                    <c:param name="pageSize" value="${pageSize}"/>
                                                    <c:param name="sortOrder" value="${currentSortOrder}"/>
                                                    <c:param name="sortedBy" value="${currentColumn}"/>
                                                    <c:param name="currentPage" value="${noOfPage-2}"/>
                                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                                </c:url>
                                                <a href="${pageUrl}" class="number"><c:out value="${noOfPage-1}"></c:out></a>
                                                <span class="act"> <c:out value="${noOfPage}"></c:out></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                        <%--<c:forEach var="i" begin="0" end="${noOfPage-1}">
                            <c:choose>
                                <c:when test="${currentPage==i}">
                                    <span class="act"> <c:out value="${i+1}"></c:out></span>
                                </c:when>
                                <c:otherwise>
                                    <c:url value="/advertisement/list.html" var="pageUrl" >
                                        <c:param name="pageSize" value="${pageSize}"/>
                                        <c:param name="sortOrder" value="${currentSortOrder}"/>
                                        <c:param name="sortedBy" value="${currentColumn}"/>
                                        <c:param name="currentPage" value="${i}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                    </c:url>
                                    <a href="${pageUrl}"><c:out value="${i+1}"></c:out></a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>    --%>
                        <c:if test="${currentPage < (noOfPage-1)}">
                            <c:url  value="/advertisement/list.html" var="nextPageUrl">
                                <c:param name="pageSize" value="${pageSize}"/>
                                <c:param name="currentPage" value="${currentPage+1}"/>
                                <c:param name="sortOrder" value="${currentSortOrder}"/>
                                <c:param name="sortedBy" value="${currentColumn}"/>
                                <c:param name="currentCategory" value="${currentCategory}"/>
                                <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                            </c:url>
                            <a href="${nextPageUrl}" class="going next"> >> </a>
                        </c:if>
                    </div>
                </div>
            <!-- ******************* /pagination  ************************  -->
            </section>
            <%@ include file="/WEB-INF/jsp/advertisement/aside.jsp" %>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
        <div id="block">
            <%@ include file="/WEB-INF/jsp/advertisement/savingSearch.jsp" %>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/vkEmailConfirm.jsp" %>
    </body>
</html>
