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
        <div class="entryBlock">
            <sec:authorize ifAnyGranted='IS_AUTHENTICATED_ANONYMOUSLY'>
                <a href='<c:url value="/login.html"/>' class="entry">  Вход на сайт </a>
                <a href='<c:url value="/user/registration.html"/>'class="registration">  Регистрация </a>
            </sec:authorize>
        </div>
        <div id=logotype>
            <a href='<c:url value="/advertisement/list.html"/>'>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
            </a>
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
                        <div class="left"><label for="e1">E-mail:<span class="star">*</span></div>
                        <div class="right"><form:input id="e1" type="text" path="email" class="placeTitle" /></div>
                        <span class="error2"><form:errors path="email" /> </span>
                    </div>
                    <div class="places">
                        <div class="left"><p>Пароль:<span class="star">*</span></p></div>
                        <div class="right"><form:input type="password" path="password" class="placeTitle" />   </div>
                        <span class="error2"><form:errors path="password" /> </span>
                    </div>
                    <div class="places">
                        <div class="left1"> Имя: </div>
                        <div class="right"><form:input  type="text" path="firstName"  placeholder="Введите Ваше имя" class="placeTitle"  maxlength="30"/></div>
                        <span class="error2"><form:errors path="firstName" /> </span>
                    </div>

                    <div class="places">
                        <div class="left">Фамилия: </div>
                        <div class="right"><form:input type="text" placeholder="Введите Вашу фамилию" path="lastName" class="placeTitle" /></div>
                        <span class="error2"><form:errors path="lastName" /> </span>
                    </div>
                <%--<div class="places">
                        <div class="left"><p>Ссылка на профиль в контакте: </p></div>
                        <div class="right"><form:input type="text" path="vkLink" placeholder="Введите id Вашего аккаунта" class="placeTitle" /></div>
                        <span class="error2"><form:errors path="vkLink" /> </span>
                    </div>--%>
                    <div class="places">
                        <form:checkbox id="one1" path="isReceiveNews" value="true"  />
                        <label for="one1">Присылать мне новости проекта</label>
                    </div>
                    <div class="think">
                         <input type="reset" value="Очистить" class="no"/>
                         <input type="submit" value="Зарегистрироваться" class="sendAdv"/>
                    </div>
                </section>
            </form:form>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
</body>
</html>