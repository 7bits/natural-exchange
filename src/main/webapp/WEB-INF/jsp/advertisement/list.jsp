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
                                 <c:url var="url" value="list.html">
                                     <c:param name="sortedBy" value='${"title"}'/>
                                     <c:param name="sortOrder" value='${sortByTitleOrderNew}'/>
                                 </c:url>
                                 <a href="<c:out value='${url}'/>">Заголовок</a>
                            </th>
                            <th>
                                <c:url var="url" value="list.html">
                                    <c:param name="sortedBy" value='${"createdDate"}'/>
                                    <c:param name="sortOrder" value='${sortByDateOrderNew}'/>
                                </c:url>
                                <a href="<c:out value='${url}'/>">Дата</a>
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
                            <td><img src="<c:url value="/resources/images/${advertisement.photoFile}"/>"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
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
        </div>
    </body>
</html>
