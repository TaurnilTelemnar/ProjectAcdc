<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <form class="form-horizontal" method="post"
    >
        <fieldset>

            <!-- Form Name -->
            <legend>Изменение данных Пользователя:</legend>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="user-name">Имя</label>
                <div class="col-md-4">
                    <input
                            id="user-name"
                            name="user-name"
                            type="text"
                            value="${requestScope.user.name}"
                            class="form-control input-md"
                            required="">
                    <span class="help-block">Имя пользователя</span>
                </div>
            </div>
            <br>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="user-login">Логин</label>
                <div class="col-md-4">
                    <input
                            id="user-login"
                            name="user-login"
                            type="text"
                            value="${requestScope.user.login}"
                            class="form-control input-md"
                            required="">
                    <span class="help-block">Логин пользователя</span>
                </div>
            </div>
            <br>
            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="user-password">Пароль</label>
                <div class="col-md-4">
                    <input id="user-password"
                           name="user-password"
                           type="password"
                           value="${requestScope.user.password}"
                           class="form-control input-md"
                           required="">
                    <span class="help-block">Текущий пароль пользователя</span>
                </div>
            </div>
            <br>

            <!-- Select Basic -->
            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <div class="form-group">
                    <label class="col-md-4 control-label" for="user-role">Роль</label>
                    <div class="col-md-4">
                        <select id="user-role" name="user-role" class="form-control">
                            <c:forEach var="role" items="${applicationScope.roles}">
                                <c:if test="${role!='GUEST'}">
                                    <option value="${role}" ${role==requestScope.user.role?"selected":""}>${role}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <span class="help-block">Укажите роль пользователя</span>
                    </div>
                </div>
            </c:if>
            <!-- Button (Double) -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="update"></label>
                <div class="col-md-8">
                    <button id="update" name="update" class="btn btn-primary">Обновить</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>
<%@include file="parts/footer.jsp" %>