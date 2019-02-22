<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello user</title>
    <link href="<c:url value="/view/css/userPage.css" />" rel="stylesheet">
</head>
<body>
<h1 align="center"> Hello, ${userForSession.firstName}!</h1>
<p/>
<hr/>
<p/>
<h3 align="center"> Click <a href = "logout">here</a> to logout </h2>

</body>
</html>