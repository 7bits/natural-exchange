<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/viewStyle.css"/>" />
    <%-- <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'>  --%>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'>
        <title>Просмотр объявления</title>
    </head>

    <body>
        <header class="centerTop">

            <div id=logotype>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
            </div>
            <div id=lk>
                <%--*********   --%>
                    <p class="proLk">Узнавайте новости проекта первыми! </p>
                    <form:form method="get" commandName="mailingNewsForm">
                        <p><form:input path="email" size="30" class="lkMail" placeholder="Ваш e-mail"/></p>
                        <p><form:errors path="email"  /></p>
                        <input type="hidden" name="id" value="${currentId}"/>
                        <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                        <p><input type="submit" value="Подписаться" class="send" /></p>
                    </form:form>
                <%-- ********* --%>
            </div>
        </header>
        <nav class="center">
            <a href='<c:url value="/advertisement/placing.html"/>' rel="nofollow" class="manage-panel">
                Разместить объявление
            </a>
        </nav>
      <%--    <div class="center">
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

                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:out value="${category}"/></td>
                        </tr>
                    </c:forEach>

                <c:url  value="/advertisement/list.html" var="listPage">
                    <c:param name="currentCategory" value="${currentCategory}"/>
                </c:url>
                <form:form method="get" action="${listPage}" commandName="advertisementSearchingForm">
                    <div>
                        <form:input class="wordSearch" path="keyWords" placeholder="Поиск" />
                        <form:errors path="keyWords" />
                    </div>
                    <button class="save"> Сохранить поиск  </button>
                    <input type="submit" class="search" value="Найти"/>
                    <div class="cate">
                        <p class="pcate"> <form:radiobutton  id="one" path="category" value="nothing"  /><label for="one">Не выбрано</label></p>
                        <p class="pcate"><form:radiobutton id="two" path="category" value="clothes" /><label for="two">Одежда</label></p>
                        <p class="pcate"> <form:radiobutton id="three" path="category" value="notclothes"  /><label for="three">Не одежда</label></p>
                        <p><form:errors path="category"/></p>
                    </div>
                        <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                </form:form>
            </aside>
        </div>
        <div class="plug">
            <div class="commercialAdvert">
            </div>
        </div>

        <footer class="centerTop">
                   <div id=likes> Соц. Сети </div>
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
        </footer>   --%>
    </body>
</html>
