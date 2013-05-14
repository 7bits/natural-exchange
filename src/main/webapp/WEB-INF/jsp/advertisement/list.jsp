<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
		<link rel="stylesheet" type="text/css" href="../../resources/css/tableAdv.css" />
        <title>Объявления</title>
    </head>
    
    <body>
        <h1>Меню</h1>
        <table class='tableAdvertisement'>
            <thead>
                <tr>
                    <th class='tableAdvertisements'>
                        Автор
                    </th>
                    <th class='tableAdvertisements'>
                        Заголовок
                    </th>
                    <th  class='tableAdvertisements'>
                        Дата
                    </th>
                    <th>
                        Текст
                    </th>
                    <th  class='tableAdvertisements'>
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
                    <td><img src="..\..\resources\images\${advertisement.photoFile}"  /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <table  class='tableCategories'>
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
