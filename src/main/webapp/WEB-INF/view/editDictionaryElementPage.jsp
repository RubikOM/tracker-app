<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Edit word</title>
</head>
<body>
<form:form method="PATCH" action="/dictionary/editWord" modelAttribute="dictionaryElement">
    <%@include file="includes/wordForm.jsp" %>

    <div class="button-box col-lg-12 padding-top-required">
        <p><form:button type="submit" class="btn btn-primary my-button">Edit word</form:button>
            <input type="button" class="btn btn-secondary my-button" onclick="history.back();"
                   value="Cancel"/>
        </p>
    </div>
</form:form>
</body>
</html>
