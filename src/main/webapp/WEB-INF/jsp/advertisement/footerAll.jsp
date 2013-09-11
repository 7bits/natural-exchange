<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <div class="plug">
        <div>
            <p> Понравился проект? Расскажи друзьям! </p>
            <div id="vk_like_main"></div>
            <script type="text/javascript" src='<c:url value="/resources/js/vkWigetScript.js"/>'  language="javascript"> </script>
        </div>
    </div>
    <footer>
        <div class="centerTop">
            <div class="social">
                <p class="socialText"> Мы в социальных сетях: </p>
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
        </div>
        <%@ include file="/WEB-INF/jsp/advertisement/vkEmailConfirm.jsp" %>
    </footer>