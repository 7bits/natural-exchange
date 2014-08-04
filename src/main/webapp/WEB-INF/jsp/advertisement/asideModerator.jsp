<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- for list.jsp & view.jsp --%>
<aside>
    <c:url  value="/advertisement/moderator/list.html" var="listPage">
        <c:param name="currentCategory" value="${currentCategory}"/>
    </c:url>
    <form:form method="GET" action="${listPage}" commandName="advertisementSearchingForm">
            <input type="hidden" name="sortedBy" value="${currentColumn}"/>
            <input type="hidden" name="sortOrder" value="${currentSortOrder}"/>
            <input type="hidden" name="pageSize" value="${pageSize}"/>
            <input type="hidden" name="currentCategory" value="${currentCategory}"/>
        <div>
            <form:input class="wordSearch" path="keyWords" placeholder="Поиск" name="wordSearch"/>
            <form:errors path="keyWords" />
        </div>
        <div class="cate" id="cate">
            <p class="pcate"><form:checkbox id="two2" path="categories" value="delete"/> <label for="two2"> <span class="moderator" name="cate"></span>Удаленные</label></p>
            <p><form:errors path="categories"/></p>
        </div>
        <div>
            <input type="submit" class="search" value="Найти"/>
        </div>
    </form:form>
</aside>