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
          <%--  <div class="vhod">
                <a href='<c:url value="/advertisement/list.html"/>' class="vhod1">  Вход на сайт </a>
                 <a href='<c:url value="/advertisement/list.html"/>'class="vhod2">  Регистрация </a>
            </div>
            <div id=logotype> Натуральный обмен </div>
            <div id=lk>  Лк</div>          --%>
        </header>
        <nav class="center">
            <a href='<c:url value="/advertisement/placing.html"/>' rel="nofollow" class="manage-panel">
                Разместить объявление
            </a>
        </nav>
        <div class="center">
            <section>
                <table>
                    <thead>
                        <tr>
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
                            <th class="title">
<%--                                 <c:set var="sortTitle" value="${!sortTitle}"/> --%>
								<c:url value="/advertisement/list.html" var="titleSortingUrl">
									<c:param name="sortedBy" value="${sortedByTitle}"/>
									<c:param name="sortOrder" value="${sortOrderTitle}"/>
									<c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
								</c:url>
								<a href="${titleSortingUrl}">Заголовок</a>
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
                        <c:if test="${status.index%2==0}">
                            <tr>
                                <td class="date"><c:out value="${advertisement.createdDateFormat}"/></td>
                                <td class="title">
                                    <c:url value="/advertisement/view.html" var="advertisementViewingUrl">
                                        <c:param name="id" value="${advertisement.id}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                    </c:url>
                                    <a href="${advertisementViewingUrl}"><c:out value="${advertisement.title}"/>  </a>
                                </td>
                                <td class="text"><c:out value="${advertisement.text}"/></td>
                                <td class="photo"><img src='<c:url value="/resources/images/${advertisement.photoFile}"/>' alt="Нет фото"/></td>
                                <td class="category"><c:out value="${advertisement.category.name}"/></td>
                             </tr>
                        </c:if>
                        <c:if test="${status.index%2==1}">
                            <tr class="tr1">
                                <td class="date"><c:out value="${advertisement.createdDateFormat}"/></td>
                                <td class="title">
                                    <c:url value="/advertisement/view.html" var="advertisementViewingUrl">
                                        <c:param name="id" value="${advertisement.id}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                     </c:url>
                                    <a href="${advertisementViewingUrl}"><c:out value="${advertisement.title}"/>  </a>
                                </td>
                                <td class="text"><c:out value="${advertisement.text}"/></td>
                                <td class="photo"><img src='<c:url value="/resources/images/${advertisement.photoFile}"/>' alt="Нет фото"/></td>
                                <td class="category"><c:out value="${advertisement.category.name}"/></td>
                             </tr>
                        </c:if>
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
                                <c:if test="${pageSize!=3}">
                                    <option value="3">3</option>
                                </c:if>
                            </select>
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
                            <a href="${prevPageUrl}" class="going"><</a>
                        </c:if>
                        <c:choose>
                            <c:when test="${currentPage==0}">
                                <em> 1 </em>
                                <c:if test="${noOfPage>1}">
                                    <c:url value="/advertisement/list.html" var="pageUrl" >
                                        <c:param name="pageSize" value="${pageSize}"/>
                                        <c:param name="sortOrder" value="${currentSortOrder}"/>
                                        <c:param name="sortedBy" value="${currentColumn}"/>
                                        <c:param name="currentPage" value="1"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                        <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                    </c:url>
                                    <a href="${pageUrl}"> 2 </a>
                                </c:if>
                                <c:if test="${noOfPage>3}">
                                    <span> ... </span>
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
                                    <a href="${pageUrl}"><c:out value="${noOfPage}"></c:out></a>
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
                                <a href="${pageUrl}"> 1</a>
                                <c:choose>
                                    <c:when test="${currentPage==1}">
                                        <em> 2 </em>
                                        <c:if test="${noOfPage>3}">
                                            <span> ... </span>
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
                                            <a href="${pageUrl}"><c:out value="${noOfPage}"></c:out></a>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${noOfPage>3}">
                                            <span> ... </span>
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${currentPage!=(noOfPage-1)}">
                                                <em> <c:out value="${currentPage+1}"></c:out></em>
                                                <c:if test="${currentPage!=(noOfPage-2)}">
                                                    <span> ... </span>
                                                </c:if>
                                                <c:url value="/advertisement/list.html" var="pageUrl" >
                                                    <c:param name="pageSize" value="${pageSize}"/>
                                                    <c:param name="sortOrder" value="${currentSortOrder}"/>
                                                    <c:param name="sortedBy" value="${currentColumn}"/>
                                                    <c:param name="currentPage" value="${noOfPage-1}"/>
                                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                                </c:url>
                                               <a href="${pageUrl}"><c:out value="${noOfPage}"></c:out></a>
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
                                                <a href="${pageUrl}"><c:out value="${noOfPage-1}"></c:out></a>
                                                <em> <c:out value="${noOfPage}"></c:out></em>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    <%--<c:forEach var="i" begin="0" end="${noOfPage-1}">
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
                            <a href="${nextPageUrl}" class="going"> > </a>
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
                    <div>
                        <form:input path="keyWords" />
                        <form:errors path="keyWords" />
                    </div>
                    <input type="submit" class="search" value="Найти"/>
                    <div class="cate">
                        <p class="pcate"> <form:radiobutton class="incate" path="category" value="nothing" />Не выбрано</p>
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
            </div>
        </div>
        <footer class="centerTop">
              <%--      <div id=likes> Соц. Сети </div>
                    <div class="footerLink1">
                        <a href='<c:url value="/advertisement/list.html"/>'> О проекте </a>
                    </div>
                    <div class="footerLink">
                        <a href='<c:url value="/advertisement/list.html"/>'>  Как обмениваться </a>
                    </div>
                    <div class="footerLink">
                        <a href='<c:url value="/advertisement/list.html"/>'>  Помощь проекту </a>
                    </div>
                    <p class="niz"> © 2013 Natural Exchange</p>               --%>
        </footer>
    </body>
</html>
