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
        <p class="filter">
            Фильтр по времени
        </p>
        <p>
            <label for="from">От</label>
            <form:input type="text" id="from" name="currentDateFrom" path="dateFrom" value = "${dateFrom}"/>
            <span class="moderator-error"><br><form:errors path="dateFrom" /> </span>
        </p>
        <p>
            <label for="to">До</label>
            <form:input type="text" id="to" name="currentDateTo" path="dateTo" value = "${dateTo}"/>
            <span class="moderator-error"><br><form:errors path="dateTo" /> </span>
        </p>
        <div>
            <input type="submit" class="search" value="Найти"/>
        </div>

    </form:form>
</aside>