<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <title>Размещение объявления</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
    </head>
    <body>
        <header class="centerTop">
            <div class="entryBlock">
                <p> <c:out value="${userDB.email}"/></p>
            </div>  --%>
            <div id=logotype>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                <%--   <span>Натуральный обмен</span>
                <p> Помощь молодым родителям</p> --%>
            </div>
            <div id=lk>
                <%--*********   --%>
                <form:form method="post" commandName="mailingNewsForm" class="lk">
                    <p><span class="errorLk"><form:errors path="emailNews"  /> </span></p>
                    <p class="proLk">Узнавайте новости проекта первыми! </p>
                    <p><form:input path="emailNews" size="30" class="lkMail" placeholder="Ваш e-mail"/></p>
                    <p><input type="submit" value="Подписаться" class="send" /></p>
                </form:form>
                <%-- ********* --%>
            </div>
        </header>
        <div class="center">
            <div style="color: red">Вы успешно вошли в систему. Очень Вам рады :)</div>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>