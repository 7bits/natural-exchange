<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%-- !doctype html--%>
<html lang="ru">
<head>
         <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
         <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/viewStyle.css"/>" />
         <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
         <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
         <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' >

          <title>Вход в систему</title>
</head>
<body>
      <header class="centerTop">
               <div id=logotype>
                   <a href='<c:url value="/advertisement/list.html"/>'>
                       <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                   </a>
               </div>
      </header>

      <div class="centerR">
          <p class="pCenter titleSave"> Вход в систему </p>
      </div>
      <div class="center">
          <form method="POST"   action="<c:url value="/j_spring_security_check"/>"  >
               <div class="places">
         	       <div class="left1">
                      <p>Пароль:</p>
         	       </div>
                   <div class="right">
         			   <input type="password" name="j_password"  class="logus"/>
                    </div>
               </div>
               <div class="places">
         	       <div class="left1">
                      <p>E-mail:</p>
         	       </div>
                   <div class="right">
         			  <input type="text" name="j_username"  class="logus"/>
                   </div>
               </div>
               <div class="buttonArea">
         		   <input type="reset" value="Отмена" class="close" />
         		   <input type="submit" value="Войти" class="sendOk" />
               </div>

         </form>
      </div>
      <div class="plug">
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