<%@ page contentType="text/html;charset=UTF-8" language="java" %>
            <div id="main">
                <form id="contact" name="contact" action="#" method="get">
                    <div class="titleSave"> Сохранить параметры поиска </div>
                    <div class="say"> Введите свой e-mail, и Вы получите письмо с ссылкой на сохраненные <br>параметры Вашего поиска</div>
                    <input type="email" id="emailSave" name="emailSave" class="mail placeTitle" placeholder="E-mail"/>
                    <div class="say"> Введите, пожалуйста, знаки с картинки, чтобы убедиться, что Вы не робот</div>
                    <div class="capt">
                        <img id="captchaImg" src='<c:url value="/advertisement/makeCaptcha.html"/>' />
                        <p><img id="refresh" class="refresh" src='<c:url value="/resources/images/refresh.png"/>' ></p>
                        <input type="text" class="captchIn" name="captchaInput"/>
                    </div>
                    <div id="message">  </div>
                    <div class="buttonArea">
                        <button id="close" class="close"> Отмена </button>
                        <button id="send" class="sendOk">Готово</button>
                    </div>
                </form>
                <div id="saving" class="saving">
                 Поиск сохранен! на ваш e-mail было отправлено письмо со ссылкой на сохраненный поиск.
                </div>
            </div>