<%@include file="parts/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- Форма игры-->
<legend>Игра</legend>
<div class="container">

    <jsp:useBean id="user" scope="session" type="com.javarush.kojin.entity.User"/>
    <jsp:useBean id="question" scope="request" type="com.javarush.kojin.entity.Question"/>
    <jsp:useBean id="game" scope="request" type="com.javarush.kojin.entity.Game"/>

    <h1>${question.text}</h1>
    <form method="post">
        <ul>
            <input type="hidden" name="questId" value="${game.questId}">
            <c:if test="${game.gameState=='PLAY'}">
            <c:forEach var="answer" items="${question.answers}">
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="answerNextQuestionId"
                           value="${answer.nextQuestionId}"
                           id="answer${answer.id}">
                    <label class="form-check-label" for="answer${answer.id}">
                            ${answer.text}
                    </label>
                </div>
            </c:forEach>

        </ul>

        <!-- Кнопка подтверждения выбранного вопроса -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="/play"></label>
            <div class="col-md-8">
                <button id="/play" name="/play" class="btn btn-success">Отправить</button>
            </div>
        </div>
        </c:if>
        <c:if test="${game.gameState!='PLAY'}">
            <!-- Кнопка начала новой игры -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="newGame"></label>
                <div class="col-md-8">
                    <button id="newGame" name="newGame" class="btn btn-info">Начать заново</button>
                </div>
            </div>
        </c:if>
    </form>
</div>
<%@include file="parts/footer.jsp" %>