<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>

<%@ include file="header.jsp" %>
<h1>Edit User</h1>

<s:form action="/edit" class="form-horizontal" method="POST">
    <s:textfield name="user.id" type="hidden"/>

    <s:textfield name="user.login" class="form-control" label="Login" placeholder="Enter login"
                 required="true" type="login" pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{1,20}$"
                 value="%{user.login}" readonly="true"/>


    <s:textfield name="user.password" class="form-control" label="Password" placeholder="Enter password"
                 pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                 type="password" required="true" value="%{user.password}"/>


    <s:textfield name="user.confirmPassword" class="form-control" label="Confirm Password" type="password"
                 pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                 placeholder="Enter confirm password" required="true" value="%{user.password}"/>


    <s:textfield name="user.email" class="form-control" label="Email" placeholder="Enter email" type="email"
                 required="true"
                 value="%{user.email}"/>


    <s:textfield name="user.firstName" class="form-control" label="First Name"
                 pattern="^[A-Z]{1}[a-z]{1,25}"
                 placeholder="Enter first name" required="true"/>

    <s:textfield name="user.lastName" class="form-control" label="Last Name" placeholder="Enter last name"
                 pattern="^[A-Z]{1}[a-z]{1,25}"
                 required="true" value="%{user.lastName}"/>

    <s:textfield name="user.birthday" class="form-control" type="date" label="Birthday">
        <s:param name="value">
            <s:date name="%{user.birthday}" format="yyyy-MM-dd"/>
        </s:param>
    </s:textfield>

    <s:select name="user.role.name" class="form-control" label="Role" list="{'USER', 'ADMIN'}"
              required="true"/>


    <s:submit value="Save" class="btn btn-primary"/>


</s:form>

<div class="col-sm-2 pl-0">
    <a href="/home" class="btn btn-primary">Cancel</a>
</div>

</body>
</html>