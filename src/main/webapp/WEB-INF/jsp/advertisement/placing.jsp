<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
          <%--  <div class="entryBlock">
                <a href='<c:url value="/advertisement/list.html"/>' class="entry">  Вход на сайт </a>
                <a href='<c:url value="/advertisement/list.html"/>'class="registration">  Регистрация </a>
            </div>  --%>
            <div id=logotype>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                <%--   <span>Натуральный обмен</span>
                <p> Помощь молодым родителям</p>     --%>
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
        <div class="centerR">
            <p class="pCenter">Разместить объявление на сайте  </p>
        </div>
        <div class="center">
            <form:form method="post" commandName="advertisementPlacingForm">
                <section>

                    <p class="pSay"> Поля, отмеченные звездочкой обязательны для заполнения </p>

        	        <div class="places">
		                <div class="left1">

		                    <p>Заголовок: <span class="star">*</span></p>
		                </div>
		                <div class="right">
				            <form:input path="title" class="placeTitle" />

				        </div>
				        <span class="error2"><form:errors path="title" /></span>
                    </div>
                    <div class="places">
                        <div class="left">

				            <p>Описание:<span class="star">*</span></p>
				        </div>
				        <div class="right">
				            <form:textarea path="text" class="placeText"/>

				        </div>
				        <span class="error2"><form:errors path="text" /> </span>
				    </div>
				    <div class="places">
				        <div class="left">

				            <p>Фотография:<span class="star">*</span></p>
				        </div>
                        <div class="right">
				            <p><form:input path="photoFile" class="placeTitle" /></p>

                         </div>
                         <span class="error2"><form:errors path="photoFile" /></span>

                    <div class="think">
                             <input type="reset" value="Отмена" class="no"/>
                             <input type="submit" value="Разместить" class="sendAdv"/>
                     </div>
                   </div>
                </section>
                <aside>
                    <div>
                        Выберите категорию: <span class="star">* </span>
                    </div>
                    <div class="cate1">
                        <p class="pcate1"><form:radiobutton id="two" path="category" value="clothes" /><label for="two">Одежда</label></p>
                        <p class="pcate1"> <form:radiobutton id="three" path="category" value="notclothes"  /><label for="three">Не одежда</label></p>
                        <p> <span class="error"><form:errors path="category"/></span></p>
                    </div>

	            </aside>
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