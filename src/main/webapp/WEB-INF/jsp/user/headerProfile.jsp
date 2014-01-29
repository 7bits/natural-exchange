<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<script type="text/javascript" src='<c:url value="/resources/js/loginWindow.js"/>'  language="javascript"> </script>  --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <header class="centerTop">

        <div id=logotype>
            <a href='<c:url value="/advertisement/list.html"/>'>
                <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
            </a>
        </div>

        <div class="entryBlock">
            <sec:authorize ifAnyGranted="ROLE_ADMIN">
                <a href="<c:url value='/advertisement/post.html'/>" > Рассылка новостей.</a>
            </sec:authorize>
        </div>


    </header>
