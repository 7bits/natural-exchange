<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<!doctype html>
<html lang="ru">
    <head>
        <script src='<c:url value="/resources/images/listScript.js"/>'> </script>
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script type="text/javascript" src='<c:url value="/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"/>'></script>
        <link rel="stylesheet" href="/fancybox/source/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
        <script type="text/javascript" src='<c:url value="/fancybox/source/jquery.fancybox.pack.js"/>'></script>
        <link rel="stylesheet" href="/fancybox/source/helpers/jquery.fancybox-buttons.css" type="text/css" media="screen" />
        <script type="text/javascript" src='<c:url value="/fancybox/source/helpers/jquery.fancybox-buttons.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/fancybox/source/helpers/jquery.fancybox-media.js"/>'></script>

        <link rel="stylesheet" href="/fancybox/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" type="text/css" media="screen" />
        <script type="text/javascript" src="/fancybox/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
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
                    <form:form method="get" commandName="mailingNewsForm" class="lk">
                        <p><span class="errorLk"><form:errors path="email"  /> </span></p>
                        <p class="proLk">Узнавайте новости проекта первыми! </p>
                        <p><form:input path="email" size="30" class="lkMail" placeholder="Ваш e-mail"/></p>
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
        <div class="center">
            <section>
                <div class="viewTitle">
                    <div class="title">
                        <c:out value="${advertisement.title}"/>
                    </div>
                    <div class="date">
                        <c:out value="${advertisement.createdDateFormat}"/>
                    </div>
                </div>
                <div class="viewCategory">

                        Категория:


                        <c:if test="${advertisement.category.name eq 'clothes'}">
                            <span class="viewCate" > Одежда </span>
                        </c:if>
                        <c:if test="${advertisement.category.name eq 'notclothes'}">
                            <span class="viewCate" > Не одежда </span>
                        </c:if>

                </div>
                <div class="viewText">
                    <c:out value="${advertisement.text}"/>
                </div>
                <div class="photoCollection">
                    <a class="fancybox" rel="group" href='<c:url value="/resources/images/${advertisement.photoFile}"/>'>
                        <img src='<c:url value="/resources/images/${advertisement.photoFile}"/>' alt="" />
                    </a>

                </div>
                <div class="contact">
                     <img src='<c:url value="/resources/images/face.png"/>'/>
                     <c:out value="${advertisement.user.firstName}"/>
                </div>
            </section>
            <aside>
                <%--
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:out value="${category}"/></td>
                        </tr>
                    </c:forEach>
                 --%>
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

            <%--<div class="commercialAdvert">   --%>

            <div>
                <p> Понравился проект? Расскажи друзьям! </p>
                <img  src='<c:url value="/resources/images/fixLike.png"/>' alt="Лайкни в вк"  />
            </div>
        </div>
        <footer class="centerTop">
                    <div class="social">
                        <p class="socialText"> Мы в социальных сетях: </p>

                       <%-- <!-- Put this div tag to the place, where the Like block will be -->
                        <div id="vk_like"></div>
                        <script type="text/javascript">
                        VK.Widgets.Like("vk_like", {type: "full"});
                        </script>       --%>

                        <img  src='<c:url value="/resources/images/social.png"/>' alt="Соц.сети"  />
                    </div>
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
