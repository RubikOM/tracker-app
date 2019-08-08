<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload your image</title>
    <link type="text/css" rel="stylesheet" href="../../static/css/bootstrap.min.css"/>
</head>
<body>
<form:form method="POST" action="/dictionary/uploadFile" enctype="multipart/form-data">
    <div class="input-group">
        <div class="input-group-prepend">
            <span class="input-group-text" id="inputGroupFileAddon01">Upload your image here</span>
        </div>
        <div class="custom-file">
            <input type="file" class="custom-file-input" id="inputGroupFile01"
                   aria-describedby="inputGroupFileAddon01">
            <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
        </div>
    </div>
    <input class="btn btn-primary" type="submit" value="Submit"/>
</form:form>

<c:if test="${recognisedText != null}">
    <h2>English input: </h2>
    <p>${recognisedText}</p>
    <h2>Russian translation: </h2>
    <p>${russianTranslation}</p>
</c:if>

<c:if test="${error != null}">
    ${error}
</c:if>
</body>
</html>
