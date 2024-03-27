<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <form class="form-horizontal" method="post"
    >
        <fieldset>

            <!-- Form Name -->
            <legend>Регистрация Пользователя:</legend>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="user-name">Имя</label>
                <div class="col-md-4">
                    <input
                            id="user-name"
                            name="user-name"
                            type="text"
                            placeholder="Укажите ваше имя"
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
                            placeholder="Придумайте логин"
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
                           placeholder="Придумайте пароль"
                           class="form-control input-md"
                           required="">
                </div>
            </div>


            <!-- Button (Double) -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="update"></label>
                <div class="col-md-8">
                    <button id="update" name="update" class="btn btn-primary">Зарегистрироваться</button>
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