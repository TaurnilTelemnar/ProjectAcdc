<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Header-JSP</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<header>
    <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
        <li><a href="home" class="nav-link px-2 link-secondary">Домой</a></li>
        <c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
            <li><a href="list-user" class="nav-link px-2">Пользователи</a></li>
        </c:if>
    </ul>
    <ul class="nav col-md-3 text-end">
        <c:if test="${sessionScope.user != null}">
            <c:if test="${sessionScope.user.role != 'GUEST'}">
                <li><a href="profile" class="nav-link px-2 link-dark">Профиль</a></li>
                <li><a href="logout" class="nav-link px-2 link-dark">Выйти</a></li>
            </c:if>
            <c:if test="${sessionScope.user.role == 'GUEST'}">
                <li><a href="login" class="nav-link px-2 link-dark">Войти</a></li>
                <li><a href="signup" class="nav-link px-2 link-dark">Зарегистрироваться</a></li>
            </c:if>
        </c:if>
    </ul>
</header>