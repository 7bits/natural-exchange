<!doctype html>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- usersBase - это экземпляр класса UserDaoHibernate из пакета it.sevenbits.service -->
<jsp:useBean id="usersBase" class="it.sevenbits.service.UserDaoImpl" />
<html class="b-error-page no-js" lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Users</title>
    </head>
	
    <body class="">
	<div>Users:</div>
	<!--В переменную users помещаем значение, которое вернул геттер UserDaoHibernate.getAllUsers(), а затем выводим значения -->
	<c:set var="users" value="${usersBase.allUsers}" />
	<c:forEach var="id" items="${users}">
		<c:out value="${id}<br/>" escapeXml="false" />
	</c:forEach>
    </body>
</html>
