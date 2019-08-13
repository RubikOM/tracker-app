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
    <p>Text in image: </p>
    <p> ${recognisedText}</p>
    <p>Translated text</p>
    <p>${russianTranslation}</p>
    <p>Suggested elements</p>
    <c:forEach items="${suggestedElements}" var="suggestedElement">
<%--        <c:if test="${suggestedElement.translation != null}">--%>
            ${dictionaryElement.word}
            ${dictionaryElement.translation}
<%--        </c:if>--%>
    </c:forEach>
</c:if>

<c:if test="${error != null}">
    ${error}
</c:if>
</body>
</html>
