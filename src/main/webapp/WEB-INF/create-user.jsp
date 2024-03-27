<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <form class="form-horizontal" method="post"
    >
        <fieldset>

            <!-- Form Name -->
            <legend>Создание Пользователя:</legend>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="user-name">Имя</label>
                <div class="col-md-4">
                    <input
                            id="user-name"
                            name="user-name"
                            type="text"
                            placeholder="Укажите имя пользователя"
                            class="form-control input-md"
                            required="">
                </div>
            </div>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="user-login">Логин</label>
                <div class="col-md-4">
                    <input
                            id="user-login"
                            name="user-login"
                            type="text"
                            placeholder="Укажите логин пользователя"
                            class="form-control input-md"
                            required="">
                </div>
            </div>
            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="user-password">Пароль</label>
                <div class="col-md-4">
                    <input id="user-password"
                           name="user-password"
                           type="password"
                           placeholder="Укажите пароль пользователя"
                           class="form-control input-md"
                           required="">
                </div>
            </div>


            <!-- Select Basic -->
            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <div class="form-group">
                    <label class="col-md-4 control-label" for="user-role">Роль</label>
                    <div class="col-md-4">
                        <select id="user-role" name="user-role" class="form-control">
                            <c:forEach var="role" items="${applicationScope.roles}">
                                <c:if test="${role!='GUEST'}">
                                    <option>${role}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <span class="help-block">Укажите роль пользователя</span>
                    </div>
                </div>
            </c:if>

            <!-- Button (Double) -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="create"></label>
                <div class="col-md-8">
                    <button id="create" name="create" class="btn btn-primary">Создать пользователя</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>
<!-- Alert -->
<c:if test="${sessionScope.error != null}">
    <script>
        alert("${sessionScope.error}");
        ${sessionScope.error = null}
    </script>
</c:if>
<%@include file="parts/footer.jsp" %>