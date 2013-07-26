<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
<head>
<title>Sign Up</title>
<style type="text/css">
span.error {
	color: red;
}
</style>
</head>
<body>
	<h1>Форма для размещения объявления:</h1>

	<form:form method="post" commandName="advertisementPlacingForm">
		<table>
			<tr>
				<td>Заголовк:</td>
				<td><form:input path="title" /></td>
				<td><span class="error"><form:errors path="title" /></span></td>
			</tr>

			<tr>
				<td>Описание:</td>
				<td><form:input path="text" /></td>
				<td><span class="error"><form:errors path="text" /></span></td>
			</tr>

            <tr>
				<td>Фотография:</td>
				<td><form:input path="photoFile" /></td>
				<td><span class="error"><form:errors path="photoFile" /></span></td>
			</tr>

            <table>
                <tr>
                    <td>Категория :</td>
                    <td><form:radiobutton path="category" value="clothes" />Одежда</td>
                    <td><form:radiobutton path="category" value="notclothes" />Не одежда</td>
                    <td><form:errors path="category"/></td>
                </tr>
            </table>

			<tr>
				<td colspan="3"><input type="submit" value="Разместить" /></td>
			</tr>
		</table>
	</form:form>

</body>
</html>