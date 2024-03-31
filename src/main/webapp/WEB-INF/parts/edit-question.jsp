<%@ page contentType="text/html;charset=UTF-8" %>
<c:forEach var="question" items="${requestScope.quest.questions}">
    <div class="container">
        <form class="form-horizontal" method="post">
            <fieldset>
                <legend>Добавление вопроса квеста:</legend>
                <!-- Имя вопроса-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="questionName">Имя вопроса ${question.id}</label>
                    <div class="col-md-4">
                        <input
                                id="questionName"
                                name="questionName"
                                type="text"
                                value="${question.name}"
                                class="form-control input-md"
                                required="">
                    </div>
                </div>
                <!-- Текст вопроса-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="questionText">Текст вопроса ${question.id}</label>
                    <div class="col-md-4">
                        <input
                                id="questionText"
                                name="questionText"
                                type="text"
                                value="${question.text}"
                                class="form-control input-md"
                                required="">
                    </div>
                </div>
                <!-- Состояние вопроса квеста-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="questionGameState">Состояние вопроса</label>
                    <div class="col-md-4">
                        <select id="questionGameState" name="questionGameState" class="form-control">
                            <c:forEach var="state" items="${applicationScope.gameStates}">
                                <option value="${state}" ${state==question.gameState?"selected":""}>${state}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <!-- Кнопки сохранения и удаления вопроса квеста -->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="saveQuestion"></label>
                    <div class="col-md-8">
                        <!-- Данные по ID question-->
                        <input type="hidden" name="questionId" value="${question.id}">
                        <button id="saveQuestion" name="saveQuestion" class="btn btn-success">Сохранить вопрос квеста
                        </button>
                        <button id="deleteQuestion" name="deleteQuestion" class="btn btn-danger">Удалить вопрос квеста
                        </button>
                    </div>
                </div>
                <%@include file="edit-answer.jsp" %>
                <!-- Кнопка добавления ответа квеста -->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="addAnswer"></label>
                    <div class="col-md-8">
                        <button id="addAnswer" name="addAnswer" class="btn btn-primary">Добавить ответ квеста
                        </button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</c:forEach>
