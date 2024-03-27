<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="parts/header.jsp"/>
<div class="container">

    <c:forEach var="user" items="${requestScope.users}">

        Изменить пользователя <a href="edit-user?id=${user.id}">${user.name}</a>
        Удалить пользователя <a href="delete-user?id=${user.id}">${user.name}</a> <br> <br> <br>

    </c:forEach>

    <p>
        <a href="create-user" class="top-nav">Создать пользователя</a>
    </p>

</div>
<c:import url="parts/footer.jsp"/>
