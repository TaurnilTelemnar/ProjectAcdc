<%@include file="parts/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="container">
    <h1 class="text-center">Выберите квест</h1>
    <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
        <c:forEach var="quest" items="${requestScope.quests}">
            <c:if test="${quest.isDraft==true && (sessionScope.user.id==quest.authorId)}">
            <div class="feature col">
                <h3 class="fs-2">${quest.name}</h3>
                <a href="create-quest" class="icon-link d-inline-flex align-items-center">
                    Продолжить создание
                    <svg class="bi" width="1em" height="1em">
                        <use xlink:href="#chevron-right"></use>
                    </svg>
                </a>
                <a href="delete-quest?id=${quest.id}" class="icon-link d-inline-flex align-items-center">
                    Удалить
                    <svg class="bi" width="1em" height="1em">
                        <use xlink:href="#chevron-right"></use>
                    </svg>
                </a>
            </div>
            </c:if>
            <c:if test="${quest.isDraft==false}">
                <div class="feature col">
                    <h3 class="fs-2">${quest.name}</h3>
                    <a href="play?id=${quest.id}" class="icon-link d-inline-flex align-items-center">
                        Играть
                        <svg class="bi" width="1em" height="1em">
                            <use xlink:href="#chevron-right"></use>
                        </svg>
                    </a>
                    <c:if test='${(sessionScope.user.role=="ADMIN") || (sessionScope.user.role=="USER" && sessionScope.user.id==quest.authorId)}'>
                        <a href="edit-quest?id=${quest.id}" class="icon-link d-inline-flex align-items-center">
                            Редактировать
                            <svg class="bi" width="1em" height="1em">
                                <use xlink:href="#chevron-right"></use>
                            </svg>
                        </a>
                        <a href="delete-quest?id=${quest.id}" class="icon-link d-inline-flex align-items-center">
                            Удалить
                            <svg class="bi" width="1em" height="1em">
                                <use xlink:href="#chevron-right"></use>
                            </svg>
                        </a>
                    </c:if>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>
<%@include file="parts/footer.jsp" %>


