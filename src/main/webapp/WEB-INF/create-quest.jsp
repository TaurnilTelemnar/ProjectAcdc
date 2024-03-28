<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <form class="form-horizontal" method="post"
    >
        <fieldset>

            <!-- Форма создания квеста -->
            <legend>Создание квеста:</legend>

            <!-- Имя квеста-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="quest-name">Имя</label>
                <div class="col-md-4">
                    <input
                            id="quest-name"
                            name="quest-name"
                            type="text"
                            value="${requestScope.draft.name}"
                            class="form-control input-md"
                            required="">
                    <span class="help-block">Имя квеста</span>
                </div>
            </div>
            <br>

            <!-- Имя начального вопроса квеста-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="quest-start-question">Роль</label>
                <div class="col-md-4">
                    <select id="quest-start-question" name="quest-start-question" class="form-control">
                        <c:forEach var="question" items="${requestScope.draft.questions}">
                            <option value="${question.name}" ${question.id==requestScope.draft.startQuestionId?"selected":""}>${question.name}</option>
                        </c:forEach>
                    </select>
                    <span class="help-block">Укажите начальный вопрос квеста</span>
                </div>
            </div>
            <br>

            <!-- Вопросы квеста-->
            <c:forEach var="question" items="${requestScope.draft.questions}">
                <!-- Имя вопроса квеста-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="quest-question-name">Имя</label>
                    <div class="col-md-4">
                        <input
                                id="quest-question-name"
                                name="quest-question-name"
                                type="text"
                                value="${question.name}"
                                class="form-control input-md"
                                required="">
                        <span class="help-block">Имя вопроса</span>
                    </div>
                </div>
                <!-- Текст вопроса квеста-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="quest-question-text">Имя</label>
                    <div class="col-md-4">
                        <input
                                id="quest-question-text"
                                name="quest-question-text"
                                type="text"
                                value="${question.text}"
                                class="form-control input-md"
                                required="">
                        <span class="help-block">Текст вопроса</span>
                    </div>
                </div>
                <!-- Состояние вопроса квеста-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="quest-question-state">Роль</label>
                    <div class="col-md-4">
                        <select id="quest-question-state" name="quest-question-state" class="form-control">
                            <c:forEach var="state" items="${applicationScope.states}">
                                <option value="${state}" ${state==question.gameState?"selected":""}>${state}</option>
                            </c:forEach>
                        </select>
                        <span class="help-block">Укажите состояние вопроса</span>
                    </div>
                </div>

                <!-- Ответы вопроса квеста-->
                <c:forEach var="answer" items="${question.answers}">
                    <!-- Текст ответа на вопрос квеста-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="question-answer-text">Имя</label>
                        <div class="col-md-4">
                            <input
                                    id="question-answer-text"
                                    name="question-answer-text"
                                    type="text"
                                    value="${answer.text}"
                                    class="form-control input-md"
                                    required="">
                            <span class="help-block">Текст ответа</span>
                        </div>
                    </div>
                    <!-- Имя вопроса квеста, на который осуществляется переход-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="answer-next-question">Роль</label>
                        <div class="col-md-4">
                            <select id="answer-next-question" name="answer-next-question" class="form-control">
                                <c:forEach var="question" items="${requestScope.draft.questions}">
                                    <option value="${question.name}" ${question.id==answer.nextQuestionId?"selected":""}>${question.name}</option>
                                </c:forEach>
                            </select>
                            <span class="help-block">Укажите переход на следующий вопрос квеста</span>
                            <!-- Данные по ID ответа, который хотим удалить, брать из поля answerId -->
                            <input type="hidden" name="answerId" value="${answer.id}">
                        </div>
                    </div>
                </c:forEach>


                <!-- Кнопки добавления/удаления ответа на вопрос квеста -->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="addAnswer"></label>
                    <div class="col-md-8">
                        <!-- Данные по ID вопроса, к которому добавляется ответ, брать из поля questionId -->
                        <input type="hidden" name="questionId" value="${question.id}">
                        <button id="addAnswer" name="update" class="btn btn-primary">Добавить ответ к вопросу квеста</button>
                        <button id="deleteAnswer" name="delete" class="btn btn-primary">Удалить ответ к вопросу квеста</button>
                    </div>
                </div>
                <br>
            </c:forEach>
            <!-- Кнопки добавления/удаления вопроса квеста -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="addQuestion"></label>
                <div class="col-md-8">
                    <button id="addQuestion" name="update" class="btn btn-primary">Добавить вопрос квеста</button>
                    <button id="deleteQuestion" name="delete" class="btn btn-primary">Удалить вопрос квеста</button>
                </div>
            </div>
            <br><br>
            <!-- Кнопки сохранения/публикации квеста -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="save"></label>
                <div class="col-md-8">
                    <button id="save" name="save" class="btn btn-primary">Сохранить черновик квеста</button>
                    <button id="publish" name="publish" class="btn btn-primary">Опубликовать квест</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<%@include file="parts/footer.jsp" %>