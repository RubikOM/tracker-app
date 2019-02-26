<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8"/>
    <title>English Helper</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="${root}/static/css/englishWordPage.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<div class="content">
    <div class="first-section">
        <div class="add-dictionaryElement">
            <p>Input English dictionaryElement here:</p>

            <form:form method="post" action="/dictionary/createWord" modelAttribute="englishWord">
                <div class="form-row">
                    <div class="col-md-3 mb-3">
                        <form:label path="word">Input English dictionaryElement *</form:label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipWord">
                            <i class="fas fa-edit"></i></span>
                            </div>
                            <form:input class="form-control" path="word" placeholder="Word in English"/>
                                <%--<form:errors path="word" class="error"/>--%>
                        </div>
                    </div>

                    <div class="col-md-3 mb-3">
                        <form:label path="transcription">Input Transcription </form:label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipTranscription">
                            <i class="fab fa-readme"></i></span>
                            </div>
                            <form:input class="form-control" path="transcription" placeholder="Transcription"/>
                                <%--<form:errors path="transcription" class="error"/>--%>
                        </div>
                    </div>

                    <div class="col-md-6 mb-3">
                        <form:label path="translation">Input Russian translation * </form:label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipTranslation">
                            <i class="fas fa-edit"></i></span>
                            </div>
                            <form:input class="form-control" path="translation" placeholder="Russian translation" />
                        </div>
                    </div>
                </div>

                <div class="padding-top-required">
                    <form:label path="example">Input Example on English </form:label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipExample">
                            <i class="fas fa-book-open"></i></span>
                        </div>
                        <form:input type="text" class="form-control" path="example" placeholder="Example in English"/>
                            <%--<form:errors path="example" class="error"/>--%>
                    </div>
                </div>

                <div class="padding-top-required">
                    <form:label path="examplesTranslation">Input Example's translation in Russian </form:label>
                    <div class="input-group ">
                        <div class="input-group-prepend ">
                        <span class="input-group-text" id="validationTooltipExampleTranslation">
                            <i class="fas fa-book-open"></i></span>
                        </div>
                        <form:input type="text" class="form-control" path="examplesTranslation" placeholder="Example's translation"/>
                            <%--<form:errors path="examplesTranslation" class="error"/>--%>
                    </div>
                </div>

                <div class="button-box col-lg-12 padding-top-required">
                    <p><form:button type="submit" class="btn btn-primary my-button">Ok</form:button>
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

<script src="${root}/static/scripts/buttons-logic.js"></script>
</body>
</html>