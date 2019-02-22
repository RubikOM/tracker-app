<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.sql.Date, java.time.LocalDate, java.time.Period" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello admin</title>
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
   <link href="<c:url value="/WEB-INF/view/css/adminPage.css" />" rel="stylesheet">
</head>
<body>

<div class = "header">
<h2  align="right" > ${userForSession.firstName} ${userForSession.lastName} (<a href="logout">logout</a>) </h2>
</div>
<div class= "content">
<p> <a href="create">add new user</a> </p>
<table class="table table-condensed table-striped">
            <tr>
                <td>Login</td>
                <td>Email</td>
                <td>First name</td>
                <td>Last name</td>
                <td>Age</td>
                <td>Role</td>
                <td>Actions</td>
            </tr>
            <c:forEach items="${userList}" var="user">
                <tr class="table">
                    <td hidden> ${user.id}</td>
                    <td> ${user.login}</td>
                    <td hidden > ${user.password}</td>
                    <td > ${user.email}</td>
                    <td > ${user.firstName}</td>
                    <td> ${user.lastName}</td>
                    <c:set var="birthday" value="${user.birthday}"/>
                       <%
                       LocalDate birthday = (LocalDate) pageContext.getAttribute("birthday");
                       int age = Period.between(birthday, LocalDate.now()).getYears();
                       if (age < 0) {
                          age = 0;
                       }
                       %>
                    <td><%= age %></td>
                    <td > ${user.getRole().getName()}</td>
                    <td><a href="editUser/${user.id}">Edit    </a>
                     <input type="button" class="delete-button btn-danger" delete-id = "${user.login}" value="Delete"></td>
                    </td>
                </tr>
            </c:forEach>
        </table>

         <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
                <script>
                    $(document).ready(function () {
                       $(".delete-button").click(function () {
                       var confirmText = "Are you sure you want to delete this user?";
                       if(confirm(confirmText)) {
                           var deleteId = $(this).attr("delete-id");
                           $.ajax({
                               type: "DELETE",
                               url: "/admin/deleteUser/" + deleteId,
                               success: function () {
                                   location.reload();
                               }
                           })
                       }
                     })
                    })
                </script>
</div>
</body>
</html>