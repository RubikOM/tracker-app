<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Log in</title>
    <link rel="icon" type="image/x-icon" href="../../static/img/favicon.ico">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="../../static/css/login.css"/>
</head>
<body>
<form:form method="POST" action="login" class="form-signin">
    <img class="mb-4" src="../../static/img/robot-head-round.png" alt="" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
    <label for="inputEmail" class="sr-only">Login</label>
    <input type="text" name="login" id="inputEmail" class="form-control" placeholder="login" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="password" required>
    <%--TODO remember me--%>
    <%--TODO style it--%>
    <div class="checkbox mb-3">
        <input type="checkbox" id="remember" name="remember" checked>
        <label for="remember">Remember me</label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2019-2020</p>
</form:form>

</body>
</html>
