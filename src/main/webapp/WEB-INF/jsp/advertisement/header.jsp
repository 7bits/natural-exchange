<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <header class="centerTop">
        <div class="entryBlock">
            <sec:authorize ifAnyGranted="ROLE_ADMIN">
                <a href="<c:url value='/advertisement/post.html'/>" > Рассылка новостей.</a>
            </sec:authorize>
            <sec:authorize  ifAnyGranted="ROLE_ADMIN, ROLE_USER" >
                <a href="<c:url value="/user/logout.html"/>" > Выйти </a>
            </sec:authorize>
        </div>
        <div id=logotype>
            <a href='<c:url value="/advertisement/list.html"/>'>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
            </a>
        </div>
        <div id=lk>
            <sec:authorize  ifAnyGranted="ROLE_ADMIN, ROLE_USER" >
                <div class="entry">
                    <sec:authentication property="principal.username"/>
                </div>
            </sec:authorize>
            <sec:authorize ifAnyGranted="IS_AUTHENTICATED_ANONYMOUSLY">
                <div class="entry">
                    <a class='login' href='<c:url value="/login.html"/>'>  Вход на сайт </a>
                </div>
                <div>
                    <a href='<c:url value="/user/registration.html"/>'>  Регистрация </a>
                </div>
            </sec:authorize>
            <a href='<c:url value="/advertisement/placing.html"/>' rel="nofollow" class="manage-panel">
                Разместить объявление
            </a>
        </div>
    </header>
