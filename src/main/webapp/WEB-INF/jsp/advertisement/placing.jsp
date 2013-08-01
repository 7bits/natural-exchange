<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
<head>
<title>Размещение объявления</title>
<style type="text/css">
span.error {
	color: red;
}
</style>
</head>
<body>
        <header class="centerTop">
          <%--  <div class="entryBlock">
                <a href='<c:url value="/advertisement/list.html"/>' class="entry">  Вход на сайт </a>
                <a href='<c:url value="/advertisement/list.html"/>'class="registration">  Регистрация </a>
            </div>  --%>
            <div id=logotype>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                <%--   <span>Натуральный обмен</span>
                <p> Помощь молодым родителям</p>     --%>
            </div>
            <div id=lk>
                <%--*********   --%>
                    <p class="proLk">Узнавайте новости проекта первыми! </p>
                    <form:form method="get" commandName="mailingNewsForm">
                        <p><form:input path="email" size="30" class="lkMail" placeholder="Ваш e-mail"/></p>
                        <p><form:errors path="email" class="error" /></p>
                        <p><input type="submit" value="Подписаться" class="send" /></p>
                    </form:form>
                <%-- ********* --%>
            </div>
        </header>
            <div> Разместить объявление на сайте </div>
            <p> Поля, отмеченные звездочкой обязательны для заполнения </p>
        	<form:form method="post" commandName="advertisementPlacingForm">
        	    <div>
		            <p>Заголовок:</p>
				    <form:input path="title" />
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