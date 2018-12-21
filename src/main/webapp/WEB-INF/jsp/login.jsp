<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
<%@ include file="header.jsp" %>
<s:if test="hasActionErrors()">
    <div class="errorDiv">
        <s:actionerror/>
    </div>
</s:if>

<div class="col-sm-offset-3 col-sm-6 err-message">
    <c:if test="${successfullyRegistration != null}">
        <div class="alert alert-success" role="alert">
            User was successfully registered!
        </div>
    </c:if>
</div>

<s:form action="/login">
    <s:textfield name="user.login" id="exampleInputLogin" class="form-control" placeholder="Enter login" label="Name"
                 required="true"/>
    <s:password name="user.password" label="Password" class="form-control" id="exampleInputPassword1"
                placeholder="Enter password"/>
    <s:submit value="login" class="btn btn-primary"/>
</s:form>

</body>
</html>