<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="parts/header.jsp" %>
<!-- Форма редактирования квеста -->
<legend>Редактирование квеста:</legend>
<!-- Имя квеста -->
<div class="container">
    <form class="form-horizontal" method="post">
        <fieldset>
            <div class="form-group">
                <label class="col-md-4 control-label" for="questName">Имя квеста ${requestScope.quest.id}</label>
                <div class="col-md-4">
                    <input
                            id="questName"
                            name="questName"
                            type="text"
                            value="${requestScope.quest.name}"
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
                        <c:forEach var="questStartQuestion" items="${requestScope.quest.questions}">
                            <option value="${questStartQuestion.id}" ${questStartQuestion.id==requestScope.quest.startQuestionId?"selected":""}>${questStartQuestion.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <%@include file="parts/edit-question.jsp" %>


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