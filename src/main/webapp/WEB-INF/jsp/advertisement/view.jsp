<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/viewStyle.css"/>" />
        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' >
        <title>Просмотр объявления</title>
    </head>

    <body>
        <header class="centerTop">
            <div class="vhod">
                <a href='<c:url value="/advertisement/list.html"/>' class="vhod1">  Вход на сайт </a>
                <a href='<c:url value="/advertisement/list.html"/>'class="vhod2">  Регистрация </a>
            </div>
            <div id=logotype>
                <img src='<c:url value="/resources/images/logo.gif"/>' alt="ex4ange)"/>
                Натуральный обмен
            </div>
            <div id=lk>  Лк</div>

            <%--********* --%>
             <div>
            <p>Подписка на новости</p>
            <form:form method="get" commandName="mailingNewsForm">
            		<p>e-mail:<form:input path="email" size="64" /></p>
            		<p><span class="error"><form:errors path="email" /></span></p>
            		<p><input type="submit" value="Подписаться" /></p>
           	</form:form>
           	</div>

            <%--********* --%>

            <a href='<c:url value="/advertisement/list.html"/>' class="vhod1">  Вход на сайт </a>
        </header>
        <nav class="center">
            <a href='<c:url value="/advertisement/placing.html"/>' rel="nofollow" class="manage-panel">
                Разместить объявление
            </a>
        </nav>
        <div class="center">
            <section>
                <div class="viewTitle">
                    <c:out value="${advertisement.title}"/>
                    <c:out value="${advertisement.createdDateFormat}"/>
                </div>
                <div class="viewCategory">
                    Категория:
                    <c:url  value="/advertisement/list.html" var="listPage">
                        <c:param name="currentCategory" value="${currentCategory}"/>
                    </c:url>
                    <form:form method="get" action="${listPage}" commandName="advertisementSearchingForm">
                </div>
                <div>
                    <c:out value="${advertisement.text}"/>
                </div>
                <div class="photoCollection">
                    <img src='<c:url value="/resources/images/${advertisement.photoFile}"/>'/>
                </div>
                <div class="contact">
                     <c:out value="${advertisement.user.firstName}"/>
                </div>
            </section>
            <aside>
            <%--div class="category-table">
                <table class="page-wrapper">
                    <thead>
                        <tr class="headu">
                            <th>Категории</th>
                        </tr>
                    </thead>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:out value="${category}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div--%>
                <form:form method="get" commandName="advertisementSearchingForm">
                    <div>
                        <form:input class="wordSearch" path="keyWords" placeholder="Поиск" />
                        <form:errors path="keyWords" />
                    </div>
                    <button class="save"> Сохранить поиск  </button>
                    <input type="submit" class="search" value="Найти"/>
                    <div class="cate">
                        <p class="pcate"> <form:radiobutton  path="category" value="nothing" /><span></span>Не выбрано</p>
                        <p class="pcate"><form:radiobutton path="category" value="clothes" /><span></span>Одежда</p>
                        <p class="pcate"> <form:radiobutton path="category" value="notclothes" /><span></span>Не одежда</p>
                        <p><form:errors path="category"/></p>
                    </div>
                        <input type="hidden" name="sortedBy" value="${currentColumn}"/>
                        <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                        <input type="hidden" name="sortOrder" value="${currentSortOrder}"/>
                        <input type="hidden" name="pageSize" value="${pageSize}"/>
                </form:form>
            </aside>
        </div>
        <div class="center">
            <div class="commercialAdvert">
            </div>
        </div>
        <footer class="centerTop">
              <%--      <div id=likes> Соц. Сети </div>    --%>
                    <div class="footerLink1">
                        <a href='<c:url value="/advertisement/list.html"/>'> О проекте </a>
                    </div>
                    <div class="footerLink">
                        <a href='<c:url value="/advertisement/list.html"/>'>  Как обмениваться </a>
                    </div>
                    <div class="footerLink">
                        <a href='<c:url value="/advertisement/list.html"/>'>  Помощь проекту </a>
                    </div>
                    <p class="sign"> © 2013 Natural Exchange</p>
        </footer>
    </body>
</html>
