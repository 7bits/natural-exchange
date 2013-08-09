<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

            </div>
            <div id=logotype>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                <%--   <span>Натуральный обмен</span>
                <p> Помощь молодым родителям</p>
            </div>

            <div id=lk>

            </div>


        </header>
       <div class="center">

           <div style="color: red"> Упс. Неудача какая :( </div>



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