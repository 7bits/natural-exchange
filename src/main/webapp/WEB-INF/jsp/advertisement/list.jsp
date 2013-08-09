<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page session="false"%>

<!doctype html>
<html lang="ru">
    <head>

    <!-- Put this script tag to the <head> of your page -->
    <script type="text/javascript" src="//vk.com/js/api/openapi.js?98"></script>

<script type="text/javascript">
  VK.init({apiId: 3783611, onlyWidgets: true});
</script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js" language="javascript"></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.fancybox-1.3.4.pack.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.easing.1.3.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/fancybox/jquery.mousewheel-3.0.4.pack.js"/>'></script>
        <link type="text/css" rel="stylesheet" href="<c:url value="/fancybox/jquery.fancybox-1.3.4.css"/>"  media="screen" />

        <script type="text/javascript" src='<c:url value="/resources/js/saveSearch.js"/>'  language="javascript"> </script>
        <script type="text/javascript" src='<c:url value="/resources/js/checkbox.js"/>'  language="javascript"> </script>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tableAdv.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/placingStyle.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/inputStyle.css"/>" />
        <link href='http://fonts.googleapis.com/css?family=Lobster&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=PT+Sans&subset=latin,cyrillic' >
        <title>Объявления</title>
    </head>
    
    <body>

        <header class="centerTop">
            <div class="entryBlock">

                <sec:authorize ifAnyGranted='ROLE_USER, ROLE_ADMIN'>
                    <sec:authentication property="principal.username" />
                    <a href="<c:url value="/user/logout.html"/>" >Выйти</a>
                </sec:authorize>
                <sec:authorize ifAnyGranted="ROLE_ADMIN">
                    <a href="<c:url value='/advertisement/post.html'/>" > Рассылка новостей.</a>
                </sec:authorize>

                <sec:authorize ifAnyGranted='IS_AUTHENTICATED_ANONYMOUSLY'>
                    <a href='<c:url value="/login.html"/>' class="entry">  Вход на сайт </a>
                <%--    <a href='<c:url value="/user/registration.html"/>'class="registration">  Регистрация </a>--%>
                </sec:authorize>

            </div>
            <div id=logotype>
                <a href='<c:url value="/advertisement/list.html"/>'>
                    <img src='<c:url value="/resources/images/logoAll.png"/>' alt="ex4ange)"/>
                </a>
                <%--   <span>Натуральный обмен</span>
                <p> Помощь молодым родителям</p>     --%>
            </div>

            <div id=lk>
                <%--*********   --%>
                    <form:form method="get" commandName="mailingNewsForm" class="lk">
                        <p><span class="errorLk"><form:errors path="email"  /> </span></p>
                        <p class="proLk">Узнавайте новости проекта первыми! </p>
                        <p><form:input path="email" size="30" class="lkMail" placeholder="Ваш e-mail"/></p>
                        <p><input type="submit" value="Подписаться" class="send" /></p>

                    </form:form>
                <%-- ********* --%>
            </div>
        </header>
        <nav class="center">
            <a href='<c:url value="/advertisement/placing.html"/>' rel="nofollow" class="manage-panel">
                Разместить объявление
            </a>
        </nav>
        <div class="center">
            <section>
                <table>
                    <thead>
                        <tr>
                            <!--th>
                                Автор
                            </th-->
                            <th class="date">
<%--                                 <c:set var="sortDate" value="${!sortDate}"/> --%>
                                <c:url value="/advertisement/list.html" var="dateSortingUrl">
                             			<c:param name="sortedBy" value="${sortedByDate}"/>
                                        <c:param name="sortOrder" value="${sortOrderDate}"/>
                             			<c:param name="pageSize" value="${pageSize}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                        <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                </c:url>
                                 Дата
                                 <c:choose>
                                     <c:when  test="${sortOrderDate eq 'ASCENDING'}" >
                                         <a href="${dateSortingUrl}">
                                              <img class="sort" src='<c:url value="/resources/images/sort_1.png"/>' alt="Сорт">
                                         </a>
                                     </c:when>
                                     <c:otherwise>
                                         <a href="${dateSortingUrl}">
                                             <img class="sort" src='<c:url value="/resources/images/sort_2.png"/>' alt="Сорт">
                                         </a>
                                     </c:otherwise>
                                 </c:choose>

                            </th>
                            <th class="title">
<%--                                 <c:set var="sortTitle" value="${!sortTitle}"/> --%>
								<c:url value="/advertisement/list.html" var="titleSortingUrl">
									<c:param name="sortedBy" value="${sortedByTitle}"/>
									<c:param name="sortOrder" value="${sortOrderTitle}"/>
									<c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
								</c:url>
								Заголовок
                                 <c:choose>
                                     <c:when  test="${sortOrderTitle eq 'ASCENDING'}" >
                                         <a href="${titleSortingUrl}">
                                              <img class="sort" src='<c:url value="/resources/images/sort_1.png"/>' alt="Сорт">
                                         </a>
                                     </c:when>
                                     <c:otherwise>
                                         <a href="${titleSortingUrl}">
                                             <img class="sort" src='<c:url value="/resources/images/sort_2.png"/>' alt="Сорт">
                                         </a>
                                     </c:otherwise>
                                 </c:choose>
                            </th>

                            <th class="text">
                                Описание
                            </th>

                            <th class="photo">
                                Фото
                            </th>
                            <th class="category">
                                Категория
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${advertisements}" var="advertisement" varStatus="status">
                        <c:if test="${status.index%2==0}">
                            <tr>
                                <td class="date"><c:out value="${advertisement.createdDateFormat}"/></td>
                                <td class="title">
                                    <c:url value="/advertisement/view.html" var="advertisementViewingUrl">
                                        <c:param name="id" value="${advertisement.id}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                    </c:url>
                                    <a href="${advertisementViewingUrl}"><c:out value="${advertisement.title}"/>  </a>
                                </td>
                                <td class="text"><c:out value="${advertisement.text}"/></td>
                                <td class="photo"><img src='<c:url value="/resources/images/user_images/${advertisement.photoFile}"/>' alt="Нет фото"/></td>
                                <td class="category">
                                      <c:if test="${advertisement.category.name eq 'games'}">
                                              Игры
                                      </c:if>
                                      <c:if test="${advertisement.category.name eq 'clothes'}">
                                              Одежда
                                      </c:if>
                                      <c:if test="${advertisement.category.name eq 'notclothes'}">
                                              Не одежда
                                      </c:if>
                                </td>
                             </tr>
                        </c:if>
                        <c:if test="${status.index%2==1}">
                            <tr class="tr1">
                                <td class="date"><c:out value="${advertisement.createdDateFormat}"/></td>
                                <td class="title">
                                    <c:url value="/advertisement/view.html" var="advertisementViewingUrl">
                                        <c:param name="id" value="${advertisement.id}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                     </c:url>
                                    <a href="${advertisementViewingUrl}"><c:out value="${advertisement.title}"/>  </a>
                                </td>
                                <td class="text"><c:out value="${advertisement.text}"/></td>
                                <td class="photo"><img src='<c:url value="/resources/images/user_images/${advertisement.photoFile}"/>' alt="Нет фото"/></td>
                                <td class="category">
                                      <c:if test="${advertisement.category.name eq 'games'}">
                                              Игры
                                      </c:if>
                                      <c:if test="${advertisement.category.name eq 'clothes'}">
                                              Одежда
                                      </c:if>
                                      <c:if test="${advertisement.category.name eq 'notclothes'}">
                                              Не одежда
                                      </c:if>

                                 </td>
                             </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>

            <!-- ******************* pagination  ************************  -->
                <div>
                    <div>
                    <!-- выбор размера страницы -->
                       <p class="pagination1">  Объявлений на странице   </p>
                       <c:url value="/advertisement/list.html"  var="listUrl" />
                <%--      <c:param name="sortOrder" value="${currentSortOrder}"/>
                          <c:param name="sortedBy" value="${currentColumn}"/>
                          </c:url>
                --%></div>
                    <div>
                        <form action="${listUrl}" method="get" class="pagination">
                            <select name="pageSize" class="pageSize">
                                <option value="${pageSize}"> <c:out value="${pageSize}"> </c:out> </option>
                                <c:if test="${pageSize!=2}">
                                    <option value="${defaultPageSize}"><c:out value="${defaultPageSize}"></c:out></option>
                                </c:if>
                                <c:if test="${pageSize!=5}">
                                    <option value="5">5</option>
                                </c:if>
                                <c:if test="${pageSize!=15}">
                                    <option value="15">15</option>
                                </c:if>
                                <c:if test="${pageSize!=3}">
                                    <option value="3">3</option>
                                </c:if>
                            </select>
                            <input type="hidden" name="sortedBy" value="${currentColumn}"/>
                            <input type="hidden" name="sortOrder" value="${currentSortOrder}"/>
                            <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                            <input type="hidden" name="currentKeyWords" value="${currentKeyWords}"/>
                            <input type="submit" value="OK" class="ok"/>
                        </form>
                    </div>

            <!-- ******************** переходы по страницам ******************** -->
                <%--               текущая страница: <em><c:out value="${currentPage}"/></em> --%>
                    <div class="pagination2">
                        <c:if test="${currentPage!=0}">
                            <c:url  value="/advertisement/list.html" var="prevPageUrl">
                                <c:param name="pageSize" value="${pageSize}"/>
                                <c:param name="currentPage" value="${currentPage-1}"/>
                                <c:param name="sortOrder" value="${currentSortOrder}"/>
                                <c:param name="sortedBy" value="${currentColumn}"/>
                                <c:param name="currentCategory" value="${currentCategory}"/>
                                <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                            </c:url>
                            <a href="${prevPageUrl}" class="going"> << </a>
                        </c:if>
                        <c:choose>
                            <c:when test="${currentPage==0}">
                                <span class="act"> 1 </span>
                                <c:if test="${noOfPage>1}">
                                    <c:url value="/advertisement/list.html" var="pageUrl" >
                                        <c:param name="pageSize" value="${pageSize}"/>
                                        <c:param name="sortOrder" value="${currentSortOrder}"/>
                                        <c:param name="sortedBy" value="${currentColumn}"/>
                                        <c:param name="currentPage" value="1"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                        <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                    </c:url>
                                    <a href="${pageUrl}" class="number"> 2 </a>
                                </c:if>
                                <c:if test="${noOfPage>3}">
                                    <span class="number"> ... </span>
                                </c:if>
                                <c:if test="${noOfPage>2}">
                                    <c:url value="/advertisement/list.html" var="pageUrl" >
                                        <c:param name="pageSize" value="${pageSize}"/>
                                        <c:param name="sortOrder" value="${currentSortOrder}"/>
                                        <c:param name="sortedBy" value="${currentColumn}"/>
                                        <c:param name="currentPage" value="${noOfPage-1}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                        <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                    </c:url>
                                    <a href="${pageUrl}" class="number"><c:out value="${noOfPage}"></c:out></a>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:url value="/advertisement/list.html" var="pageUrl" >
                                    <c:param name="pageSize" value="${pageSize}"/>
                                    <c:param name="sortOrder" value="${currentSortOrder}"/>
                                    <c:param name="sortedBy" value="${currentColumn}"/>
                                    <c:param name="currentPage" value="0"/>
                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                </c:url>
                                <a href="${pageUrl}" class="number"> 1</a>
                                <c:choose>
                                    <c:when test="${currentPage==1}">
                                        <span class="act"> 2 </span >
                                        <c:if test="${noOfPage>3}">
                                            <span class="number"> ... </span>
                                        </c:if>
                                        <c:if test="${noOfPage>2}">
                                            <c:url value="/advertisement/list.html" var="pageUrl" >
                                                <c:param name="pageSize" value="${pageSize}"/>
                                                <c:param name="sortOrder" value="${currentSortOrder}"/>
                                                <c:param name="sortedBy" value="${currentColumn}"/>
                                                <c:param name="currentPage" value="${noOfPage-1}"/>
                                                <c:param name="currentCategory" value="${currentCategory}"/>
                                                <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                            </c:url>

                                                <a href="${pageUrl}" class="number"><c:out value="${noOfPage}"></c:out></a>

                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${noOfPage>3}">
                                            <span class="number"> ... </span>
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${currentPage!=(noOfPage-1)}">
                                                <span class="act"> <c:out value="${currentPage+1}"></c:out></span>
                                                <c:if test="${currentPage!=(noOfPage-2)}">
                                                    <span class="number"> ... </span>
                                                </c:if>
                                                <c:url value="/advertisemen


t/list.html" var="pageUrl" >
                                                    <c:param name="pageSize" value="${pageSize}"/>
                                                    <c:param name="sortOrder" value="${currentSortOrder}"/>
                                                    <c:param name="sortedBy" value="${currentColumn}"/>
                                                    <c:param name="currentPage" value="${noOfPage-1}"/>
                                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                                </c:url>
                                               <a href="${pageUrl}" class="number"><c:out value="${noOfPage}"></c:out></a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:url value="/advertisement/list.html" var="pageUrl" >
                                                    <c:param name="pageSize" value="${pageSize}"/>
                                                    <c:param name="sortOrder" value="${currentSortOrder}"/>
                                                    <c:param name="sortedBy" value="${currentColumn}"/>
                                                    <c:param name="currentPage" value="${noOfPage-2}"/>
                                                    <c:param name="currentCategory" value="${currentCategory}"/>
                                                    <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                                                </c:url>
                                                <a href="${pageUrl}" class="number"><c:out value="${noOfPage-1}"></c:out></a>
                                                <span class="act"> <c:out value="${noOfPage}"></c:out></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    <%--<c:forEach var="i" begin="0" end="${noOfPage-1}">
                           <c:choose>
                                <c:when test="${currentPage==i}">
                                    <span class="act"> <c:out value="${i+1}"></c:out></span>
                                </c:when>
                                <c:otherwise>
                                    <c:url value="/advertisement/list.html" var="pageUrl" >
                                        <c:param name="pageSize" value="${pageSize}"/>
                                        <c:param name="sortOrder" value="${currentSortOrder}"/>
                                        <c:param name="sortedBy" value="${currentColumn}"/>
                                        <c:param name="currentPage" value="${i}"/>
                                        <c:param name="currentCategory" value="${currentCategory}"/>
                                    </c:url>
                                    <a href="${pageUrl}"><c:out value="${i+1}"></c:out></a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>    --%>
                        <c:if test="${currentPage < (noOfPage-1)}">
                            <c:url  value="/advertisement/list.html" var="nextPageUrl">
                                <c:param name="pageSize" value="${pageSize}"/>
                                <c:param name="currentPage" value="${currentPage+1}"/>
                                <c:param name="sortOrder" value="${currentSortOrder}"/>
                                <c:param name="sortedBy" value="${currentColumn}"/>
                                <c:param name="currentCategory" value="${currentCategory}"/>
                                <c:param name="currentKeyWords" value="${currentKeyWords}"/>
                            </c:url>
                            <a href="${nextPageUrl}" class="going next"> >> </a>
                        </c:if>
                    </div>
                </div>
            <!-- ******************* /pagination  ************************  -->
            </section>
            <aside>
            <%--div class="category-table">
                <table class="page-wrapper">
                    <thead>
                        <tr class="headu">
                            <th>Категории</th>
                        </tr>
                    </thead>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:out value="${category}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div--%>
                <form:form method="get" commandName="advertisementSearchingForm">
                    <div>
                        <form:input class="wordSearch" path="keyWords" placeholder="Поиск" name="wordSearch"/>
                        <form:errors path="keyWords" />
                    </div>
                    <a class="save" href="#main" >Сохранить поиск</a>

                    <input type="submit" class="search" value="Найти"/>
                    <div class="cate" id="cate">
                        <p class="pcate"> <form:checkbox  id="one1" path="categories" value="games"  /><label  for="one1"> <span name="cate"></span> Игры</label></p>
                        <p class="pcate"><form:checkbox id="two2" path="categories" value="clothes"  /><label  for="two2"> <span name="cate"></span>Одежда</label></p>
                        <p class="pcate"> <form:checkbox id="three3" path="categories" value="notclothes"   /><label  for="three3"><span name="cate"></span>Не одежда</label></p>
                        <p><form:errors path="categories"/></p>
                    </div>
                        <input type="hidden" name="sortedBy" value="${currentColumn}"/>
                        <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                        <input type="hidden" name="sortOrder" value="${currentSortOrder}"/>
                        <input type="hidden" name="pageSize" value="${pageSize}"/>
                </form:form>
            </aside>
        </div>


        <div class="plug">

            <%--<div class="commercialAdvert">   --%>

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


      </div>
       <div class="plug">

        </footer>

        <div id="block">
             <div id="main">
                 <form id="contact" name="contact" action="#" method="get">
                     <div class="titleSave"> Сохранить параметры поиска </div>
                     <div class="say"> Введите свой e-mail, и Вы получите письмо с ссылкой на сохраненные параметры Вашего поиска</div>
                     <input type="email" id="emailSave" name="emailSave" class="placeTitle mail" placeholder="E-mail">
                     <div class="say"> Введите, пожалуйста, знаки с картинки, чтобы убедиться, что Вы не робот</div>
                     <div class="capt">
                         <img id="captchaImg" src='<c:url value="/advertisement/makeCaptcha.html"/>' >
                         <p > <img id="refresh" class="refresh" src='<c:url value="/resources/images/refresh.png"/>' ></p>
                         <input type="text" class="captchIn" name="captchaInput">

                     </div>
                     <div id="message">  </div>
                     <div class="buttonArea">
                         <button id="close" class="close"> Отмена </button>
                         <button id="send" class="sendOk">Готово</button>
                     </div>
                 </form>
               </div>
         </div>

    </body>
</html>
