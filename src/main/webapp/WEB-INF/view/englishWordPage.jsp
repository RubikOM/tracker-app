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
    <%--TODO this script to js file--%>
    <%--<script>
        $(document).ready(function () {
            $(".get-file-button").click(function () {
                $.ajax({
                    type: "GET",
                    url: "/getTxtFile",
                    success: function () {
                        location.reload();
                    }
                })
            })
        })
    </script>--%>
</head>
<body>
<div class="content">
    <div class="first-section">
        <div class="add-word">
            <p>Input English word here:</p>

            <form:form method="post" action="createWord" modelAttribute="englishWord">
                <div class="form-row">
                    <div class="col-md-3 mb-3">
                        <label>Input English word *</label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipWord">
                            <i class="fas fa-edit"></i></span>
                            </div>
                            <form:input class="form-control" path="wordInEnglish" placeholder="Word"/>
                                <%--<form:errors path="wordInEnglish" class="error"/>--%>
                        </div>
                    </div>

                        <%--TODO form label--%>
                    <div class="col-md-3 mb-3">
                        <label>Input Transcription </label>
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
                        <label>Input Russian translation * </label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipTranslation">
                            <i class="fas fa-edit"></i></span>
                            </div>
                            <form:input type="text" class="form-control" id="validationTooltipUsername"
                                        placeholder="Russian translation" path="translation"/>
                        </div>
                    </div>
                </div>

                <div class="padding-top-required">
                    <label>Input Example on English </label>
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
                    <label>Input Example's translation in Russian </label>
                    <div class="input-group ">
                        <div class="input-group-prepend ">
                        <span class="input-group-text" id="validationTooltipExampleTranslation">
                            <i class="fas fa-book-open"></i></span>
                        </div>
                        <form:input type="text" class="form-control" path="exampleTranslation"
                                    placeholder="Example's translation"/>
                            <%--<form:errors path="exampleTranslation" class="error"/>--%>
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

    <div class="added-words">
        <p>Your last added words down here
            <%--<button type="submit" class="btn btn-primary get-file-button">Get text file</button>--%>
            <a href="${root}/getTxtFile">Get .txt file with all your today's words </a>
        </p>
        <%--TODO limit by some number (1-10)--%>
        <table class="table table-condensed table-striped">
            <tr>
                <td>Word</td>
                <td>Transcription</td>
                <td>Translation</td>
                <td>Example</td>
                <td>Example translation</td>
                <td>Created</td>
                <%--<td>Actions</td>--%>
            </tr>
            <c:forEach items="${words}" var="word">
                <tr class="table">
                    <td> ${word.wordInEnglish}</td>
                    <td> ${word.transcription}</td>
                    <td> ${word.translation}</td>
                    <td> ${word.example}</td>
                    <td> ${word.exampleTranslation}</td>
                    <td> ${word.creationDate}</td>
                    <%--<td> ${word.creationDate}</td>--%>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>