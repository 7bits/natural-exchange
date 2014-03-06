<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<!doctype html>
<html lang="ru">
    <head>
        <%@ include file="/WEB-INF/jsp/headInclude.jsp" %>
        <script type="text/javascript" src='<c:url value="/resources/js/saveSearch.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/checkbox.js"/>'  language="javascript"> </script>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainAsideStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />
        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'/>
        <link href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' rel='stylesheet' type='text/css' />
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <title>Редактировать поиск</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="editSearchVars">
        <c:url value="/user/editSearchVariant.html" var="listPage">
            <c:param name="oldKeys" value="${oldKeys}" />
            <c:param name="oldCategories" value="${oldCategories}"/>
        </c:url>
        <form:form method="post" action="${listPage}" commandName="advertisementSearchingForm">
            <%--    <input type="hidden" name="oldKeys" value="${oldKeys}" />
            <input type="hidden" name="oldCategories" value="${oldCategories}"/>--%>
                <div>
                    <form:input  path="keyWords" class="wordSearch" placeholder="поиск" name="wordSearch"/>
                    <form:errors path="keyWords" />
                </div>
                    <div class="cate" id="cate">
                        <p class="pcate"><form:checkbox id="one1" path="categories" value="games"/><label for="one1"> <span name="cate"></span>Игры</label></p>
                        <p class="pcate"><form:checkbox id="two2" path="categories" value="clothes"/> <label for="two2"> <span name="cate"></span>Одежда</label></p>
                        <p class="pcate"><form:checkbox id="three3" path="categories" value="notclothes"/> <label for="three3"><span name="cate"></span>Не одежда</label></p>
                        <p><form:errors path="categories"/></p>
                    </div>
                 <input type="submit" value="Сохранить" class="sendAdv" />
        </form:form>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>
