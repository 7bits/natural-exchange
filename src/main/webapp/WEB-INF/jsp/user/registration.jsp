<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <title>Регистрация</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
    </head>
    <body>
        <header class="centerTop">
            <%--  <div class="entryBlock">
                <a href='<c:url value="/login.html"/>' class="entry">  Вход на сайт </a>
                <a href='<c:url value="/user/registration.html"/>'class="registration">  Регистрация </a>
            </div>  --%>
            <div id=logotype>
                <a href='<c:url value="/advertisement/list.html"/>'>
                    <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                </a>
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
        <div class="centerR">
            <p class="pCenter">Регистрация </p>
        </div>
        <div class="center">
            <form:form method="post" commandName="userRegistrationForm" enctype="multipart/form-data">
                <section>
                    <p class="pSay"> Поля, отмеченные звездочкой обязательны для заполнения </p>
                    <div class="places">
                        <div class="left1">
                            <p>Имя: <span class="star">*</span></p>
                        </div>
                        <div class="right">
                            <form:input  type="text" path="firstName"  class="placeTitle"  maxlength="30"/>

                        </div>
                        <span class="error2"><form:errors path="firstName" /></span>
                    </div>
                    <div class="places">
                        <div class="left">
                            <p>Фамилия:<span class="star">*</span></p>
                        </div>
                        <div class="right">
                            <form:input type="text" path="lastName" class="placeTitle" />
                        </div>
                        <span class="error2"><form:errors path="lastName" /> </span>
                    </div>
                    <div class="places">
                        <div class="left">

                            <p>E-mail:<span class="star">*</span></p>
                        </div>
                        <div class="right">
                            <form:input type="text" path="email" class="placeTitle" />

                        </div>
                        <span class="error2"><form:errors path="email" /> </span>
                    </div>
                    <div class="places">
                        <div class="left">
                            <p>Пароль:<span class="star">*</span></p>
                        </div>
                        <div class="right">
                            <form:input type="password" path="password" class="placeTitle" />
                        </div>
                        <span class="error2"><form:errors path="password" /> </span>
                    </div>
                    <div class="places">
                        <div class="left">
                            <p>Подтвердите пароль:<span class="star">*</span></p>
                        </div>
                        <div class="right">
                            <form:input type="password" path="confirmPassword" class="placeTitle" />
                        </div>
                        <span class="error2"><form:errors path="confirmPassword" /> </span>
                    </div>
                    <div class="places">
                        <div class="left">
                            <p>ссылка на профиль в контакте:<span class="star"></span></p>
                        </div>
                        <div class="right">
                            <form:input path="vkLink" class="placeTitle" />
                        </div>
                        <span class="error2"><form:errors path="vkLink" /> </span>
                    </div>
                    <div class="think">
                         <input type="reset" value="Очистить" class="no"/>
                         <input type="submit" value="Зарегистрироваться" class="sendAdv"/>
                    </div>
                </section>
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