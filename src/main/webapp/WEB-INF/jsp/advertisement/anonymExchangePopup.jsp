<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="anonymPopup">
    <form id="anonymExchangePopup" action="#" method="get">
        <div class="titleSave">Вы незарегестрированы</div>
        <div class="say"> Вы не можете обмениваться с пользователями пока вы незарегестрированы.</div>
        <div class="buttonArea">
            <a href="<c:url value="/user/registration.html"/>" class="sendOk">Зарегестрироваться</a>
        </div>
        <div class="buttonArea">
            <button id="reject" class="close"> Отмена</button>
        </div>
    </form>
</div>