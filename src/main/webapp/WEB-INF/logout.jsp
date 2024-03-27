<%@include file="parts/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="container">


    <div class="px-4 py-5 my-5 text-center">
        <p class="lead mb-4">

        <div class="col-lg-6 mx-auto">
            <form class="form-horizontal" method="post">
                <legend>Вы действительно хотите выйти?</legend>
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <button type="submit" name="/home" class="btn btn-primary btn-lg px-4 gap-3">Отмена</button>
                    <button type="submit" name="/logout" class="btn btn-outline-secondary btn-lg px-4">Выход</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="parts/footer.jsp" %>