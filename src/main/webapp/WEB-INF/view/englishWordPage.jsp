<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">
    <title>English Helper</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link type="text/css" rel="stylesheet" href="${root}/static/css/englishWordPage.css"/>
</head>
<body>
<div class="content">
    <p>Input English word here:</p>

    <form:form method="post" action="createWord" modelAttribute="englishWord">
        <div class="form-group">
            <label>Input English word *</label>
            <form:input class="form-control" path="wordInEnglish" placeholder="Word"/>
            <%--<form:errors path="wordInEnglish" class="error"/>--%>
        </div>

        <div class="form-group">
            <%--TODO form label--%>
            <label>Input Transcription </label>
            <form:input class="form-control" path="transcription" placeholder="Transcription"/>
            <%--<form:errors path="transcription" class="error"/>--%>
        </div>

        <div class="form-group">
            <label>Input Russian translation *
                You can input several translations, please separate them with space or comma</label>
            <form:input type="text" class="form-control" path="translation" placeholder="Translation"/>
            <%--<form:errors path="translation" class="error"/>--%>
        </div>

        <div class="form-group">
            <label>Input Example on English </label>
            <form:input type="text" class="form-control" path="example" placeholder="Example in English"/>
            <%--<form:errors path="example" class="error"/>--%>
        </div>

        <div class="form-group">
            <label>Input Example's translation in Russian </label>
            <form:input type="text" class="form-control" path="exampleTranslation" placeholder="example's translation"/>
            <%--<form:errors path="exampleTranslation" class="error"/>--%>
        </div>

        <div class="button-box col-lg-12">
            <p><form:button type="submit" class="btn btn-primary my-button">Ok</form:button>
                <input type="button" class="btn btn-secondary my-button" onclick="history.back();" value="Cancel"/></p>
        </div>
    </form:form>

    <%--<p>Your last added words:</p>
     &lt;%&ndash;TODO limit by some number (1-10)&ndash;%&gt;
    <table class="table table-condensed table-striped">
        <tr>
            <td>Word</td>
            <td>Transcription</td>
            <td>Translation</td>
            <td>Example</td>
            <td>Example translation</td>
            <td>Created</td>
        </tr>
        <c:forEach items="${words}" var="word">
            <tr class="table">
                <td> ${word.wordInEnglish}</td>
                <td > ${word.transcription}</td>
                <td > ${word.translation}</td>
                <td> ${word.example}</td>
                <td > ${word.exampleTranslation}</td>
                <td > ${word.creationDate}</td>
            </tr>
        </c:forEach>
    </table>--%>
</div>
</body>
</html>