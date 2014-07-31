<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
        <title>Размещение объявления</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <%--<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />--%>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/radio.css"/>" />

        <script type="text/javascript" src="//vk.com/js/api/openapi.js?100"></script>
        <script type="text/javascript" src='<c:url value="/resources/js/vkScript.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/yandexMetriс.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/badImage.js"/>'  language="javascript"> </script>
        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'/>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic'/>
    </head>
    <body>
        <noscript><div><img src="//mc.yandex.ru/watch/22070809" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="centerR">
            <p class="pCenter">Разместить объявление на сайте  </p>
        </div>
        <div class="center">
            <form:form method="post" action="placing.html" commandName="advertisementPlacingForm" enctype="multipart/form-data" onSubmit="return Validate();">
                <section>
                    <p class="pSay"> Поля, отмеченные звездочкой обязательны для заполнения </p>
                    <div class="places">
                        <div class="left1">
                            <p>Заголовок: <span class="star">*</span></p>
                            <span class="error2">не более 16 символов <br> <form:errors path="title" /></span>
                        </div>
                        <div class="right">
                            <form:input path="title" class="placeTitle"  maxlength="16"/>
                        </div>
                    </div>
                    <div class="places">
                        <div class="left">
                            <p>Описание:<span class="star">*</span></p>
                            <span class="error2"><form:errors path="text" /> </span>
                        </div>
                        <div class="right">
                            <form:textarea path="text" class="placeText" />
                        </div>
                    </div>
                    <div class="placeы">
                        <div class="right">
                            <p>Введите теги:</p>
                            <span class="error2"><form:errors path="text" /> </span>
                            <form:input path="tags" class="placeTags" />
                        </div>
                    </div>
                    <div class="places">
                        <div class="left">
                            <p>Фотография:</p>
                        </div>
                        <div class="right">
                            <p><input name="image" type="file" value="Загрузить" id="image" /></p>
                        </div>
                    </div>
                    <div class="think">
                         <input type="reset" value="Отмена" class="no" id="closeWindow" />
                        <c:if test="${isEditing}">
                            <input type="submit" value="Обновить" class="sendAdv"/>
                            <input type="hidden" name="editingAdvertisementId" value="${advertisementId}"/>
                        </c:if>
                        <c:if test="${!isEditing}">
                            <input type="submit" value="Разместить" class="sendAdv"/>
                        </c:if>
                    </div>
                </section>
                <aside>
                    <div>
                        Выберите категорию: <span class="star">* </span>
                    </div>
                    <div class="cate1">
                        <p class="pcate1"><form:radiobutton id="one" path="category" value="games" class="radiobutton"/>Игры</p>
                        <p class="pcate1"><form:radiobutton id="two" path="category" value="clothes" class="radiobutton"/>Одежда</p>
                        <p class="pcate1"> <form:radiobutton id="three" path="category" value="notclothes" class="radiobutton"/>Не одежда</p>
                        <p> <span class="error"><form:errors path="category"/></span></p>
                    </div>
                </aside>
            </form:form>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>