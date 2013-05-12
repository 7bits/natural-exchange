<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <title>Объявления</title>
    </head>
    
    <body>
        <h1>Меню</h1>
        <table style="float:left" width="90%" border="1" cellspacing="0" cellpadding="4">
            <thead>
                <tr>
                    <th>
                        Автор
                    </th>
                    <th>
                        Заголовок
                    </th>
                    <th>
                        Дата
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
                    <td><c:out value="${advertisement.userId}"/></td>
                    <td><c:out value="${advertisement.title}"/></td>
                    <td><c:out value="${advertisement.createdDate}"/></td>
                    <td><c:out value="${advertisement.text}"/></td>
                    <td><c:out value="${advertisement.photoFile}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <table style="float:right" width="10%" border="1">
            <thead>
                <tr>
                    <th>Категории</th>
                </tr>
            </thead>
            <c:forEach items="${categories}" var="category">
                <tr>
                    <td align="center"><c:out value="${category}"/></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
