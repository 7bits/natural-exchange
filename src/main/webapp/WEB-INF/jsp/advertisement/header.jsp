<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--script type="text/javascript" src='<c:url value="/resources/js/loginWindow.js"/>'  language="javascript"> </script--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <header class="centerTop">
        <div class="entryBlock">
            <sec:authorize ifAnyGranted="ROLE_ADMIN">
                <a href="<c:url value='/advertisement/post.html'/>" > Рассылка новостей.</a>
            </sec:authorize>
            <sec:authorize  ifAnyGranted="ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR" >
                <a href="<c:url value="/user/logout.html"/>" > выйти </a>
                <a href='<c:url value="/user/userProfile.html"/>'>личный кабинет</a>
            </sec:authorize>
        </div>
        <div id=logotype>
            <a href='<c:url value="/advertisement/list.html"/>'>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
            </a>
        </div>
        <div id=lk>
            <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR" >
                <div class="entry">
                    <sec:authentication property="principal.username"/>
                </div>
            </sec:authorize>
            <sec:authorize ifAnyGranted="IS_AUTHENTICATED_ANONYMOUSLY">
                <div class="entry">
                    <a class='login' href='<c:url value="/login.html"/>'>  вход на сайт </a>
                </div>
            </sec:authorize>
            <sec:authorize ifAnyGranted= "ROLE_USER,IS_AUTHENTICATED_ANONYMOUSLY">
                <a href='<c:url value="/advertisement/placing.html"/>' rel="nofollow" class="manage-panel">
                    Разместить объявление
                </a>
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_MODERATOR">
                <a href='<c:url value="/advertisement/moderator/list.html"/>' rel="nofollow" class="manage-panel">
                    Все объявления
                </a>
            </sec:authorize>
        </div>
        <%--<%@ include file="/WEB-INF/jsp/advertisement/loginPopup.jsp" %>--%>
    </header>
