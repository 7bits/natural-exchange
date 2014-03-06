<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js" language="javascript"></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.fancybox-1.3.4.pack.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.easing.1.3.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.mousewheel-3.0.4.pack.js"/>'></script>
        <link type="text/css" rel="stylesheet" href="<c:url value="/fancybox/jquery.fancybox-1.3.4.css"/>"  media="screen" />

        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <title>Регистрация</title>

        <script type="text/javascript" src="//vk.com/js/api/openapi.js?100"></script>
        <script type="text/javascript" src='<c:url value="/resources/js/vkScript.js"/>'  language="javascript"> </script>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />
        <script type="text/javascript" src='<c:url value="/resources/js/checkbox.js"/>'  language="javascript"> </script>
        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'/>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'/>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="centerR">
            <p class="pCenter">Профиль пользователя </p>
        </div>
        <div class="center">
            <form:form method="post" commandName="userRegistrationForm" enctype="multipart/form-data">
                <section>
                    <p class="pSay"> Поля, отмеченные звездочкой обязательны для заполнения </p>
                    <div class="places">
                        <div class="left"><label for="e1">E-mail:<span class="star">*</span></label> </div>
                        <div class="right"><form:input id="e1" type="text" path="email" class="placeTitle" /></div>

                    </div>
                    <span class="error2"><form:errors path="email" /> </span>
                    <div class="places">
                        <div class="left"><p>Пароль:<span class="star">*</span></p></div>
                        <div class="right"><form:input type="password" path="password" class="placeTitle" />   </div>

                    </div>
                    <span class="error2"><form:errors path="password" /> </span>
                    <div class="places">
                        <div class="left1"> Имя: </div>
                        <div class="right"><form:input  type="text" path="firstName"  placeholder="Введите Ваше имя" class="placeTitle"  maxlength="30"/></div>
                    </div>
                    <span class="error2"><form:errors path="firstName" /> </span>

                    <div class="places">
                        <div class="left">Фамилия: </div>
                        <div class="right"><form:input type="text" placeholder="Введите Вашу фамилию" path="lastName" class="placeTitle" /></div>

                    </div>
                    <span class="error2"><form:errors path="lastName" /> </span>
                    <%--<div class="places">
                        <div class="left"><p>Ссылка на профиль в контакте: </p></div>
                        <div class="right"><form:input type="text" path="vkLink" placeholder="Введите id Вашего аккаунта" class="placeTitle" /></div>
                        <span class="error2"><form:errors path="vkLink" /> </span>
                    </div>--%>
                    <div class="places">
                        <form:checkbox id="cate-reg" path="isReceiveNews"  />
                        <label for="cate-reg"><span id="cate-label"></span>Присылать мне новости проекта</label>
                    </div>
                    <div class="places">
                         <a href='<c:url value="/user/userProfile.html"/>' class="back"> Отмена </a>
                         <input type="submit" value="Сохранить изменения" class="send-reg"/>
                    </div>
                </section>
                <aside class = "registrationAside">
                    <div class = "regInfoTextContainer">
                    Редактирование профиля. <br/>
                    Подтверждения через e-mail не требуется.
                    </div>
                </aside>
            </form:form>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>