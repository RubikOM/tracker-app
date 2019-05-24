<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8"/>
    <title>English Helper</title>
    <link rel="icon" type="image/x-icon" href="../../static/img/favicon.ico">

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="../../static/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/dictionaryPage.css"/>
    <link type="text/css" rel="stylesheet" href="../../static/css/table.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<%@include file="includes/header.jsp" %>

<div class="content">
    <%@include file="includes/wordForm.jsp" %>

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

<script src="${root}/static/scripts/word-form-buttons-logic.js"></script>
</body>
</html>