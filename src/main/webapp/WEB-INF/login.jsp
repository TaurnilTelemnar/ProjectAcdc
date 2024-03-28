<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="parts/header.jsp"/>
<div class="container">
    <form class="form-horizontal" method="post">
        <fieldset>

            <!-- Form Name -->
            <legend>Войти в систему</legend>


            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="userLogin">Логин</label>
                <div class="col-md-4">
                    <input id="userLogin" name="user-login" type="text" placeholder="set login" class="form-control input-md"
                           required=""
                           value="admin">

                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="userPassword">Пароль</label>
                <div class="col-md-4">
                    <input id="userPassword" name="user-password" type="password" placeholder="pass req"
                           class="form-control input-md" required=""
                           value="admin">

                </div>
            </div>

            <!-- Button -->
            <div class=" form-group">
                <label class="col-md-4 control-label" for="submit"></label>
                <div class="col-md-4">
                    <button id="submit" name="loginButton"
                            class="btn btn-success">Войти
                    </button>
                </div>
            </div>

            <!-- Alert -->
                    <c:if test="${sessionScope.error != null}">
                        <script>
                            alert("${sessionScope.error}");
                            ${sessionScope.error = null}
                        </script>
                    </c:if>

        </fieldset>
    </form>
</div>
