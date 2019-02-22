<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="shortcut icon" href="/WEB-INF/view/img/Question.png" type="image/png">

    <title>Authorization</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
     integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
     <link href="<c:url value="/WEB-INF/view/css/authenticationPage.css" />" rel="stylesheet">
</head>
<body class="text-center">
<form method="POST" action = "login" class="form-signin">
    <img class="mb-4" src="/WEB-INF/view/img/Question.png" alt="" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
    <label for="inputEmail" class="sr-only">Login</label>
    <input type="text" name="login" id="inputEmail" class="form-control" placeholder="login" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="password" required>
    <div class="checkbox mb-3">
    <p style="color:#ff0000">${error}</p>
    <label style="color: red"><c:if test="${param.error != null}">Wrong login or password</c:if></label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
     <a href="register"> Register now </a>
    <p class="mt-5 mb-3 text-muted">&copy; 2018-2019</p>
</form>
</body>
</html>