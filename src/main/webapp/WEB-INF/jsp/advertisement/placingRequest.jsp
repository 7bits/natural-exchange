<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>

<html lang="ru">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
         <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
         <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' >

        <title>Регистрация</title>

    </head>
    <body>
        <header class="centerTop">
                    <div id=logotype>
                        <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                        <%--   <span>Натуральный обмен</span>
                        <p> Помощь молодым родителям</p>     --%>
                    </div>

        </header>
        <div class="centerR">
              <p class="pCenter"> Ваше объявление очень важно для нас!  </p>
        </div>
         <div class="center">


        <br>
        <a href='<c:url value="/advertisement/list.html"/>'> Вернуться на главную </a>
        <br>
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
