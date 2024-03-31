<%@ page contentType="text/html;charset=UTF-8" %>
<c:forEach var="answer" items="${question.answers}">
    <div class="container">
        <form class="form-horizontal" method="post">
            <fieldset>
                <legend>Добавление ответа на вопрос квеста:</legend>
                <!-- Текст ответа-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="answerText">Текст
                        ответа ${answer.id} вопроса ${question.name}</label>
                    <div class="col-md-4">
                        <input
                                id="answerText"
                                name="answerText"
                                type="text"
                                value="${answer.text}"
                                class="form-control input-md"
                                required="">
                    </div>
                </div>
                <!-- Следующий вопрос ответа-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="answerNextQuestionId">Переход на следующий
                        вопрос</label>
                    <div class="col-md-4">
                        <select id="answerNextQuestionId" name="answerNextQuestionId"
                                class="form-control">
                            <c:forEach var="answerNextQuestion" items="${requestScope.quest.questions}">
                                <option value="${answerNextQuestion.id}" ${answerNextQuestion.id==answer.nextQuestionId?"selected":""}>${answerNextQuestion.name}</option>
                            </c:forEach>


                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="saveAnswer"></label>
                    <div class="col-md-8">
                        <!-- Данные по ID answer-->
                        <input type="hidden" name="answerId" value="${answer.id}">
                        <button id="saveAnswer" name="saveAnswer" class="btn btn-warning">Сохранить ответ квеста
                        </button>
                        <button id="deleteAnswer" name="deleteAnswer" class="btn btn-danger">Удалить ответ квеста
                        </button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</c:forEach>
