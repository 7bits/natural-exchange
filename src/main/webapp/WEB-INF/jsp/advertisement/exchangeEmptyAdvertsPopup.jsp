<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="exchange">
    <form id="exchangePopup" name="exchangePopup" action="#" method="get">
        <div class="titleSave"> Добавьте свои объявления!</div>
        <div class="say"> Вы не можете обмениваться с пользователями пока у вас нет своих объявлений.</div>
        <div class="say"> Вы можете добавить объявление прямо сейчас</div>
        <div class="buttonArea">
            <a href="<c:url value="/advertisement/placing.html"/>" class="sendOk">Разместить объявление</a>
        </div>
        <div class="buttonArea">
            <button id="reject" class="close"> Отмена</button>
        </div>
    </form>
</div>