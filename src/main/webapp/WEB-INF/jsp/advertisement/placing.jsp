<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>
<html lang="ru">
    <head>
        <title>Размещение объявления</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <script type="text/javascript" src='<c:url value="/resources/js/yandexMetriс.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/badImage.js"/>'  language="javascript"> </script>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/advertisement/header.jsp" %>
        <div class="centerR">
            <p class="pCenter">Разместить объявление на сайте  </p>
        </div>
        <div class="center">
            <form:form method="post" commandName="advertisementPlacingForm" enctype="multipart/form-data" onSubmit="return Validate();">
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
                         <input type="submit" value="Разместить" class="sendAdv"/>
                    </div>
                </section>
                <aside>
                    <div>
                        Выберите категорию: <span class="star">* </span>
                    </div>
                    <div class="cate1">
                        <p class="pcate1"><form:radiobutton id="one" path="category" value="games" /><label for="one">Игры</label></p>
                        <p class="pcate1"><form:radiobutton id="two" path="category" value="clothes" /><label for="two">Одежда</label></p>
                        <p class="pcate1"> <form:radiobutton id="three" path="category" value="notclothes"  /><label for="three">Не одежда</label></p>
                        <p> <span class="error"><form:errors path="category"/></span></p>
                    </div>
                </aside>
            </form:form>
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/footerAll.jsp" %>
    </body>
</html>