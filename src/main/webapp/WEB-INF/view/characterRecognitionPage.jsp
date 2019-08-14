<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload your image</title>
</head>
<body>
<form:form method="POST" action="/dictionary/uploadFile" enctype="multipart/form-data">
    <table>
        <tr>
            <td><label>Select a file to upload</label></td>
            <td><input type="file" name="file"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>

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

<c:if test="${error != null}">
    ${error}
</c:if>
</body>
</html>
