<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
<head>
<title>Рассылка новостей сайта</title>
<style type="text/css">
span.error {
	color: red;
}
</style>
</head>
<body>
	<h1>Рассылка новостей:</h1>

	<form:form method="post" commandName="NewsPostingForm">
	    Событие:
				<form:input path="newsText" />
				<span class="error"><form:errors path="newsText" /></span>
				<input type="submit" value="Отправить" />

	</form:form>

</body>
</html>