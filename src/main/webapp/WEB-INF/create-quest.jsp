<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="parts/header.jsp" %>
<!-- Форма создания квеста -->
<legend>Создание квеста:</legend>
<!-- Имя квеста -->
<div class="container">
    <form class="form-horizontal" method="post">
        <fieldset>
            <div class="form-group">
                <label class="col-md-4 control-label" for="questName">Имя квеста ${sessionScope.draft.id}</label>
                <div class="col-md-4">
                    <input
                            id="questName"
                            name="questName"
                            type="text"
                            value="${sessionScope.draft.name}"
                            class="form-control input-md"
                            required="">
                </div>
            </div>

            <!-- Начальный вопрос квеста-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="questStartQuestionId">Начальный вопрос квеста</label>
                <div class="col-md-4">
                    <select id="questStartQuestionId" name="questStartQuestionId"
                            class="form-control">
                        <c:forEach var="questStartQuestion" items="${sessionScope.draft.questions}">
                            <option value="${questStartQuestion.id}" ${questStartQuestion.id==sessionScope.draft.startQuestionId?"selected":""}>${questStartQuestion.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <%@include file="parts/question.jsp" %>


            <!-- Кнопка добавления вопроса квеста -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="addQuestion"></label>
                <div class="col-md-8">
                    <button id="addQuestion" name="addQuestion" class="btn btn-info">Добавить вопрос квеста</button>
                </div>
            </div>
            <!-- Кнопка сохранения квеста -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="saveQuestParams"></label>
                <div class="col-md-8">
                    <button id="saveQuestParams" name="saveQuestParams" class="btn btn-success">Сохранить квест
                    </button>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<%@include file="parts/footer.jsp" %>