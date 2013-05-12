<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <title>Объявления для <c:out value="${userName}"/> </title>
    </head>
    
    <body>
        <h1>Меню</h1>
        <table style="float:left" width="90%" border="1" cellspacing="0" cellpadding="4" rules="cols">
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
            <tr>
            </tr>
            </tbody>
        </table>
        <table style="float:right" width="10%" border="1">
            <thead>
                <tr>
                    <th>Категории</th>
                </tr>
            </thead>
            <tr>
                <td align="center">Игрушки</td>
            </tr>
            <tr>
                <td align="center">Одежда</td>
            </tr>
            <tr>
                <td align="center">Мебель</td>
            </tr>
        </table>
    </body>
</html>
