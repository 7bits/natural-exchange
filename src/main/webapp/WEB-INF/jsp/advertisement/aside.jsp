<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- for list.jsp & view.jsp --%>
            <aside>
                <c:url  value="/advertisement/list.html" var="listPage">
                    <c:param name="currentCategory" value="${currentCategory}"/>
                </c:url>
                <form:form method="get" action="${listPage}" commandName="advertisementSearchingForm">
                        <input type="hidden" name="sortedBy" value="${currentColumn}"/>
                        <input type="hidden" name="sortOrder" value="${currentSortOrder}"/>
                        <input type="hidden" name="pageSize" value="${pageSize}"/>
                        <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                    <div>
                        <form:input class="wordSearch" path="keyWords" placeholder="Поиск" name="wordSearch"/>
                        <form:errors path="keyWords" />
                    </div>
                    <div class="cate" id="cate">
                        <p class="pcate"> <form:checkbox id="one1" path="categories" value="games"/> <label for="one1"> <span name="cate"></span>Игры</label></p>
                        <p class="pcate"><form:checkbox id="two2" path="categories" value="clothes"/> <label for="two2"> <span name="cate"></span>Одежда</label></p>
                        <p class="pcate"> <form:checkbox id="three3" path="categories" value="notclothes"/> <label for="three3"><span name="cate"></span>Не одежда</label></p>
                        <p><form:errors path="categories"/></p>
                    </div>
                    <div>
                        <a class="save" href="#main" >Сохранить поиск</a>
                        <input type="submit" class="search" value="Найти"/>
                    </div>
                </form:form>

                <c:url  value="/advertisement/view.html" var="viewPage">
                    <c:param name="id" value="${currentId}"/>
                    <c:param name="currentCategory" value="${currentCategory}"/>
                </c:url>
                <form:form method="get" action="${viewPage}" commandName="mailingNewsForm" class="lk">
                    <p><span class="errorLk"><form:errors path="emailNews"  /> </span></p>
                    <p class="proLk">Узнавайте новости проекта первыми! </p>
                    <p><form:input path="emailNews" size="30" class="lkMail" placeholder="Ваш e-mail"/></p>
                    <input type="hidden" name="id" value="${currentId}"/>
                    <input type="hidden" name="currentCategory" value="${currentCategory}"/>
                    <p><input type="submit" value="Подписаться" class="send" /></p>
                </form:form>
            </aside>