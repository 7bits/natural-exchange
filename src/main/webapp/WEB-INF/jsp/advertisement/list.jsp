<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <title>Объявления</title>
    </head>
    
    <body>
        <header class="centerTop">
            <div class="vhod">
                <a href='<c:url value="/advertisement/list.html"/>' class="vhod1">  Вход на сайт </a>
                 <a href='<c:url value="/advertisement/list.html"/>'class="vhod2">  Регистрация </a>
            </div>
       <%--     <div id=logotype> Натуральный обмен </div>      --%>
            <div id=lk>  Лк</div>
        </header>
        <nav class="center">
            <button class="manage-panel">
                <a href='<c:url value="/advertisement/placing.html"/>'>Разместить объявление</a>
            </button>
        </nav>
        <div class="center">
            <section>
                <table class="page-wrapper">
                    <thead>
                        <tr class="headu">
                            <!--th>
                                Автор
                            </th-->
                            <th class="date">
<%--                                 <c:set var="sortDate" value="${!sortDate}"/> --%>
                                <c:url value="/advertisement/list.html" var="dateSortingUrl">
                             			<c:param name="sortedBy" value="${sortedByDate}"/>
                                        <c:param name="sortOrder" value="${sortOrderDate}"/>
                             			<c:param name="pageSize" value="${pageSize}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                </c:url>
                                 <a href="${dateSortingUrl}">Дата</a>
                            </th>
                            <th>
<%--                                 <c:set var="sortTitle" value="${!sortTitle}"/> --%>
								<c:url value="/advertisement/list.html" var="titleSortingUrl">
									<c:param name="sortedBy" value="${sortedByTitle}"/>
									<c:param name="sortOrder" value="${sortOrderTitle}"/>
									<c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
								</c:url>
								<a href="${titleSortingUrl}">Заголовок</a>
                            </th>

                            <th>
                                Описание
                            </th>

                            <th class="date">
                                Фото
                            </th>
                            <th>
                                Категория
                            </th>
                        </tr>
                    </thead>
                    <tbody>
            <%--        <c:set var="stroki" value="1">      --%>
                    <c:forEach items="${advertisements}" var="advertisement">
                        <tr>
                            <td><c:out value="${advertisement.createdDateFormat}"/></td>
                            <td>
                                <c:url value="/advertisement/view.html" var="advertisementViewingUrl">
                                    <c:param name="id" value="${advertisement.id}"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                </c:url>
                               <a href="${advertisementViewingUrl}"><c:out value="${advertisement.title}"/>  </a>
                            </td>
                            <td><c:out value="${advertisement.text}"/></td>
                            <td><img src='<c:url value="/resources/images/${advertisement.photoFile}"/>'/></td>
                            <td><c:out value="${advertisement.category.name}"/></td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            <!-- ******************* pagination  ************************  -->
                <div>
                    <div>
                    <!-- выбор размера страницы -->
                       <p class="pagination1">  Объявлений на странице:   </p>
                        <c:url value="/advertisement/list.html"  var="listUrl" />
                <%--      <c:param name="sortOrder" value="${currentSortOrder}"/>
                          <c:param name="sortedBy" value="${currentColumn}"/>
                          </c:url>
                --%>
                        <form action="${listUrl}" method="get" class="pagination">
                            <select name="pageSize" class="pageSize">
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
                                <c:if test="${pageSize!=25}">
                                    <option value="25">25</option>
                                </c:if>
                            </select>
                            <input type="hidden" name="sortedBy" value="${currentColumn}"/>
                            <input type="hidden" name="sortOrder" value="${currentSortOrder}"/>
                            <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                            <input type="submit" value="Ok" class="ok"/>
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
                            </c:url>
                            <button class="going">
                                <a href="${prevPageUrl}"><</a>
                            </button>
                        </c:if>
                        <c:forEach var="i" begin="0" end="${noOfPage-1}">
                            <c:choose>
                                <c:when test="${currentPage==i}">
                                    <em> <c:out value="${i+1}"></c:out></em>
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
                        </c:forEach>
                        <c:if test="${currentPage < (noOfPage-1)}">
                            <c:url  value="/advertisement/list.html" var="nextPageUrl">
                                <c:param name="pageSize" value="${pageSize}"/>
                                <c:param name="currentPage" value="${currentPage+1}"/>
                                <c:param name="sortOrder" value="${currentSortOrder}"/>
                                <c:param name="sortedBy" value="${currentColumn}"/>
                                <c:param name="currentCategory" value="${currentCategory}"/>
                            </c:url>
                            <button class="going">
                                <a href="${nextPageUrl}"> > </a>
                            </button>
                        </c:if>
                    </div>
                </div>
            <!-- ******************* /pagination  ************************  -->
            </section>
            <aside>
            <!--div class="category-table">
                <table class="page-wrapper">
                    <thead>
                        <tr class="headu">
                            <th>Категории</th>
                        </tr>
                    </thead>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:out value="${category}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div-->
                    <div class="poisk"> Поиск </div>
                    <button class="save"> Сохранить поиск  </button>
                <form:form method="get" commandName="advertisementSearchingForm">
                    <input type="submit" class="search" value="Найти"/>
                    <div class="cate">
                        <p class="pcate"> <form:radiobutton path="category" value="nothing" />Не выбрано</p>
                        <p class="pcate"><form:radiobutton path="category" value="clothes" />Одежда</p>
                        <p class="pcate"> <form:radiobutton path="category" value="notclothes" />Не одежда</p>
                        <p><form:errors path="category"/></p>
                    </div>
                        <input type="hidden" name="sortedBy" value="${currentColumn}"/>
                        <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                        <input type="hidden" name="sortOrder" value="${currentSortOrder}"/>
                        <input type="hidden" name="pageSize" value="${pageSize}"/>
                </form:form>
            </aside>
        </div>
        <div class="center">
            <div class="commercialAdvert">
                Реклама
            </div>
        </div>
        <footer class="centerTop">
                    <div id=likes> Соц. Сети </div>
                    <div class="footerLink1">
                        <a href='<c:url value="/advertisement/list.html"/>'> О проекте </a>
                    </div>
                    <div class="footerLink">
                        <a href='<c:url value="/advertisement/list.html"/>'>  Как обмениваться </a>
                    </div>
                    <div class="footerLink">
                        <a href='<c:url value="/advertisement/list.html"/>'>  Помощь проекту </a>
                    </div>
                    <p class="niz"> © 2013 Natural Exchange</p>
        </footer>
    </body>
</html>
