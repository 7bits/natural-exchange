<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <title>Просмотр объявления</title>
    </head>

    <body>
        <h1>Объявление </h1>
        <div class="page-wrapper">
            <div class='advertisement-table'>
                <table>
                    <thead>
                        <tr>
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
                                Пользователь
                            </th>
                            <th>
                                Фото
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><c:out value="${advertisement.title}"/></td>
                            <td><c:out value="${advertisement.createdDateFormat}"/></td>
                            <td><c:out value="${advertisement.text}"/></td>
                            <td><c:out value="${advertisement.user.firstName}"/></td>
                            <td><img src='<c:url value="/resources/images/${advertisement.photoFile}"/>'/></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <c:url  value="/advertisement/list.html" var="listPage">
                <c:param name="currentCategory" value="${currentCategory}"/>
            </c:url>
            <form:form method="get" action="${listPage}" commandName="advertisementSearchingForm">

                <table>
                    <tr>
                        <td>Категория :</td>
                        <td><form:radiobutton path="category" value="nothing" />Не выбрано</td>
                        <td><form:radiobutton path="category" value="clothes" />Одежда</td>
                        <td><form:radiobutton path="category" value="notclothes" />Не одежда</td>
                        <td><form:errors path="category"/></td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                            <input type="submit" value="Поиск"/>
                        </td>
                    </tr>
                </table>
            </form:form>

            <div class="manage-panel">
                <a href='<c:url value="/advertisement/placing.html"/>'>Создать</a>
            </div>
        </div>
    </body>
</html>
