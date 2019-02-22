<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Add user</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
<link href="<c:url value="/WEB-INF/view/css/addUserPage.css" />" rel="stylesheet">
 <style>
 .error {
 	color: red;
 }
 </style>
</head>

<body>
 <div class ="header">
 </div>
 <h1 align ="center ">Add user</h1>
 <div class="container">
 <div class="row main-form">

 <form:form method="post" action="create" modelAttribute="user">
 <div class="form-group">
 <label for="name" class="cols-sm-2 control-label">Login</label>
 <div class="cols-sm-10">
 <div class="input-group">
 <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
 <form:input type="login" class="form-control" path="login" placeholder="Login"/>
 </div>
 </div>
 </div>
 <form:errors path="login" class="error"/>

  <div class="form-group">
  <label for="password" class="cols-sm-2 control-label">Password</label>
  <div class="cols-sm-10">
  <div class="input-group">
  <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
  <form:input type="password" class="form-control" path="password" placeholder="Password" id="password"/>
  </div>
  </div>
  </div>
  <form:errors path="password" class="error"/>

  <div class="form-group">
  <label for="confirmPassword" class="cols-sm-2 control-label">Password again</label>
  <div class="cols-sm-10">
  <div class="input-group">
  <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
  <form:input type="password" class="form-control" path="confirmPassword" placeholder="Confirm Password" id="confirmPassword"/>
  </div>
  </div>
  </div>
  <form:errors path="confirmPassword" class="error"/>

 <div class="form-group">
 <label for="email" class="cols-sm-2 control-label">Email</label>
 <div class="cols-sm-10">
 <div class="input-group">
 <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
 <form:input type="text" class="form-control" path="email" placeholder="Email"/>
 </div>
 </div>
 </div>
 <form:errors path="email" class="error"/>

 <div class="form-group">
 <label for="username" class="cols-sm-2 control-label">First Name</label>
 <div class="cols-sm-10">
 <div class="input-group">
 <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
 <form:input type="text" class="form-control" path="firstName" placeholder="First Name"/>
 </div>
 </div>
 </div>
 <form:errors path="firstName" class="error"/>

  <div class="form-group">
  <label for="username" class="cols-sm-2 control-label">Last Name</label>
  <div class="cols-sm-10">
  <div class="input-group">
  <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
  <form:input type="text" class="form-control" path="lastName" placeholder="Last Name"/>
  </div>
  </div>
  </div>
   <form:errors path="lastName" class="error"/>

  <div class="form-group">
  <label for="username" class="cols-sm-2 control-label">Birthday</label>
  <div class="cols-sm-10">
  <div class="input-group">
  <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
  <form:input type="date" class="form-control" path="birthday" />
  </div>
  </div>
  </div>
   <form:errors path="birthday" class="error"/>

  <div class="form-group">
  <label for="username" class="cols-sm-2 control-label">Role</label>
  <div class="cols-sm-10">
  <div class="input-group">
  <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
      <form:select class="my-select" path="role" size="1">
          <form:option value="user">user</form:option>
          <form:option value="admin">admin</form:option>
      </form:select>
  </div>
  </div>
  </div>

 <div class="button-box col-lg-12">
 <p><form:button type="submit" class="btn btn-primary my-button" >Ok</form:button>
     <input type="button" class="btn btn-secondary my-button" onclick="history.back();" value="Cancel"/></p>
 </div>

 </form:form>
 </div>
 </div>
 </body>
 </html>