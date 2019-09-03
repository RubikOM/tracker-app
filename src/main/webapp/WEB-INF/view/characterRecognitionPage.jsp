<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>English Helper</title>
    <link rel="icon" type="image/x-icon" href="../../static/img/favicon.ico">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="../../static/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/filePage.css"/>
</head>
<body>
<div class = "global-wrapper">
    <form:form method="POST" action="/dictionary/uploadFile" enctype="multipart/form-data">
        <div class="form-group">
            <input type="file" name="file" class="form-control-file" id="exampleFormControlFile1" value="Choose file">
            <input type="submit" class="btn btn-primary" value="Submit"/>
        </div>
    </form:form>

    <div class="text-dark">
        <c:if test="${recognisedText != null}">
            <c:out value="Text in image: ${recognisedText}"/>
            <br>
            <c:out value="Translated text: ${russianTranslation}"/>
            <br>
            <c:out value="Suggested elements: "/>
            <c:forEach items="${suggestedElements}" var="suggestedElement">
                <br>
                <c:out value="word: ${suggestedElement.word}"/>
                <br>
                <c:out value="suggested translations: ${suggestedElement.translation}"/>
            </c:forEach>
        </c:if>
    </div>

    <c:if test="${error != null}">
        ${error}
    </c:if>
</div>
</body>
</html>
