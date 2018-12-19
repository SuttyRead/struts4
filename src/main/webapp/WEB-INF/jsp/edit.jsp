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

<s:form action="/edit" class="form-horizontal">
    <table class="table table-light" style="width: auto;" align="center">
        <tbody>
        <tr>
            <s:textfield name="userForm.login" class="form-control" label="Login" placeholder="Enter login"
                         required="true"
                         value="%{user.login}" readonly="true"/>
        </tr>
        <tr>
            <s:textfield name="userForm.password" class="form-control" label="Password" placeholder="Enter password"
                         type="password"
                         required="true" value="%{user.password}"/>
        </tr>
        <tr>
            <s:textfield name="userForm.confirmPassword" class="form-control" label="Confirm Password" type="password"
                         placeholder="Enter confirm password" required="true" value="%{user.password}"/>
        </tr>
        <tr>
            <s:textfield name="userForm.email" class="form-control" label="Email" placeholder="Enter email"
                         required="true"
                         value="%{user.email}"/>
        </tr>
        <tr>
            <s:textfield name="userForm.firstName" class="form-control" label="First Name"
                         placeholder="Enter first name"
                         required="true" value="%{user.firstName}"/>
        </tr>
        <tr>
            <s:textfield name="userForm.lastName" class="form-control" label="Last Name" placeholder="Enter last name"
                         required="true" value="%{user.lastName}"/>
        </tr>
            <%--<s:date name="userForm.birthday" format="mm-dd-yyyy"/>--%>
            <%--<s:textfield name="userForm.birthday" class="form-control" label="Birthday:" type="date" format="yyyy-MM-dd"--%>
            <%--placeholder="Enter Birthday ('yyyy-mm-dd')" required="true" value="%{user.birthday}"/>--%>

        <tr>
            <s:textfield name="userForm.birthday" class="form-control" type="date"
                         id="birthday" label="Birthday">
                <s:param name="value">
                    <s:date name="%{user.birthday}" format="yyyy-MM-dd"/>
                </s:param>
            </s:textfield>
        </tr>
            <%--<s:datetextfield name="userForm.lastName" class="form-control" label="Birthday" format="dd-MM-yyyy" value="%{user.lastName}"/>--%>
        <tr>
            <s:select name="userForm.roleName" class="form-control" label="Role" list="{'USER', 'ADMIN'}"
                      required="true"/>
            <s:submit value="add" class="btn btn-primary"/>
        </tr>
        </tbody>
    </table>
</s:form>

</body>
</html>