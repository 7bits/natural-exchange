<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
<head>
    <title>Размещение объявления</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
</head>
<body>
        <header class="centerTop">
            <div class="entryBlock">
                <a href='<c:url value="/user/login.html"/>' class="entry">  Вход на сайт </a>
                <a href='<c:url value="/advertisement/list.html"/>'class="registration">  Регистрация </a>
            </div>  --%>
            <div id=logotype>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                <%--   <span>Натуральный обмен</span>
                <p> Помощь молодым родителям</p>
            </div>

            <div id=lk>
                <%--*********   --%>
                    <form:form method="post" commandName="mailingNewsForm" class="lk">
                        <p><span class="errorLk"><form:errors path="email"  /> </span></p>
                        <p class="proLk">Узнавайте новости проекта первыми! </p>
                        <p><form:input path="email" size="30" class="lkMail" placeholder="Ваш e-mail"/></p>
                        <p><input type="submit" value="Подписаться" class="send" /></p>

                    </form:form>
                <%-- ********* --%>
            </div>


        </header>
       <div class="center">

           <div style="color: red">${message}</div>


           <form:form commandName="userEntity" method="POST">
         			<label for="j_username">E-mail: </label>
         		 	<form:input id="j_username" name="j_username" size="30" maxlength="50" type="text" />

         			<label for="j_password">Password: </label>
         			<form:input id="j_password" name="j_password" size="20" maxlength="50" type="password" />

         			<input type="submit" value="Войти" />
           </form:form>
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