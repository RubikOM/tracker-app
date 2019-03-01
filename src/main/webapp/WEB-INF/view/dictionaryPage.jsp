<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8"/>
    <title>English Helper</title>
    <link rel="icon" type="image/x-icon" href="../../static/img/favicon.ico">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="../../static/css/englishWordPage.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div class="main-wrapper">
    <div class="header">
        <%@include file="includes/header.jsp" %>
    </div>

    <div class="content">
        <div class="create-word-section">
            <div class="add-dictionaryElement">
                <form:form method="POST" action="/dictionary/createWord" modelAttribute="dictionaryElement">
                    <%@include file="includes/wordForm.jsp" %>

                    <div class="button-box col-lg-12 padding-top-required">
                        <p><form:button type="submit" class="btn btn-primary my-button">Add word</form:button>
                            <input type="button" class="btn btn-secondary my-button" onclick="history.back();"
                                   value="Cancel"/>
                        </p>
                    </div>
                </form:form>
            </div>
        </div>

        <div class="added-dictionaryElements">
            <c:if test="${todaysAddedElements != null}">
                <%@include file="includes/todayWordsTable.jsp" %>
            </c:if>

            <c:if test="${lastAddedElements != null}">
                <%@include file="includes/lastWordsTable.jsp" %>
            </c:if>
        </div>
    </div>
    <%@include file="includes/footer.jsp" %>

    <script src="${root}/static/scripts/buttons-logic.js"></script>
</div>
</body>
</html>