<%@include file="parts/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="container">
    <form class="form-horizontal" method="post">
        <fieldset>

            <!-- Form Name -->
            <legend>${sessionScope.error}</legend>

            <!-- Button -->
            <div class=" form-group">
                <label class="col-md-4 control-label" for="ok"></label>
                <div class="col-md-4">
                    <button id="ok" name="ok"
                            class="btn btn-success">ОК
                    </button>
                </div>
            </div>


        </fieldset>
    </form>
</div>

<%@include file="parts/footer.jsp" %>
