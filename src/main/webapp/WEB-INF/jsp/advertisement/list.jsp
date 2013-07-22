<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <title>Объявления</title>
    </head>
    
    <body>
        <h1>Меню</h1>
        <div class="page-wrapper">
            <div class='advertisement-table'>
                <table>
                    <thead>
                        <tr>
                            <!--th>
                                Автор
                            </th-->
                            <th>
<%--                                 <c:set var="sortTitle" value="${!sortTitle}"/> --%>
								<c:url value="/advertisement/list.html" var="titleSortingUrl">
									<c:param name="sortedBy" value="${sortedByTitle}"/>
									<c:param name="sortOrder" value="${sortOrderTitle}"/>
									<c:param name="pageSize" value="${pageSize}"/>
								</c:url>
								<a href="${titleSortingUrl}">Заголовок</a>
                            </th>
                            <th>
<%--                                 <c:set var="sortDate" value="${!sortDate}"/> --%>
                            	<c:url value="/advertisement/list.html" var="dateSortingUrl">
									<c:param name="sortedBy" value="${sortedByDate}"/>
                                    <c:param name="sortOrder" value="${sortOrderDate}"/>
									<c:param name="pageSize" value="${pageSize}"/>
								</c:url>
                                 <a href="${dateSortingUrl}">Дата</a>
                            </th>
                            <th>
                                Текст
                            </th>
                            <th>
                                Фото
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${advertisements}" var="advertisement">
                        <tr>
                            <td><c:out value="${advertisement.title}"/></td>
                            <td><c:out value="${advertisement.createdDateFormat}"/></td>
                            <td><c:out value="${advertisement.text}"/></td>
                            <td><img src='<c:url value="/resources/images/${advertisement.photoFile}"/>'/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
    </div>
             <!-- ******************* pagination  ************************  -->
            <div>
                <div>
                <!-- выбор размера страницы -->
                    <p>рамер страницы: <c:out value="${pageSize}"/></p>
                    <c:url value="/advertisement/list.html"  var="listUrl" >
                        <c:param name="sortOrder" value="${currentSortOrder}"/>
                        <c:param name="sortedBy" value="${currentColumn}"/>
                    </c:url>
                    <form action="${listUrl}">
                        <select name="pageSize">
                            <option value="${defaultPageSize}"><c:out value="${defaultPageSize}"></c:out></option>
                            <option value="5">5</option>
                            <option value="15">15</option>
                            <option value="25">25</option>
                        </select>
                        <input type="submit" value="выбрать"/>
                    </form>
                </div>
            <!-- ******************** переходы по страницам ******************** -->
                <%--               текущая страница: <em><c:out value="${currentPage}"/></em> --%>
                <div>
                    <c:if test="${currentPage!=0}">
                        <c:url  value="/advertisement/list.html" var="prevPageUrl">
                            <c:param name="pageSize" value="${pageSize}"/>
                            <c:param name="currentPage" value="${currentPage-1}"/>
                            <c:param name="sortOrder" value="${currentSortOrder}"/>
                            <c:param name="sortedBy" value="${currentColumn}"/>
                        </c:url>
                        <a href="${prevPageUrl}">назад</a>
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
                        </c:url>
                        <a href="${nextPageUrl}">вперед</a>
                    </c:if>
                </div>
            </div>
            <!-- ******************* /pagination  ************************  -->

            <div class="category-table">
                <table>
                    <thead>
                        <tr>
                            <th>Категории</th>
                        </tr>
                    </thead>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:out value="${category}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="manage-panel">
                <a href='<c:url value="/advertisement/placing.html"/>'>Создать</a>
            </div>
        </div>
    </body>
</html>
