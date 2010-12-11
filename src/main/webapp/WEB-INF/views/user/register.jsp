<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Registration</title>
</head>
<body>
<p>Hi <sec:authentication property="principal.displayName"/>,<br/> Not a very nice name to display is it? Please register
with this site so you we now how to address you and can keep your projects. We do not store any google information here
    besides what is needed to identify you. Therefore we store your email as well as you unique identifier.</p>
<table>
    <tr>
        <td>Email</td>
        <td><sec:authentication property="principal.email"/></td>
    </tr>
</table>
<spring:form method="POST" commandName="userEntry">
    <table>
        <tr>
            <td>Display name :</td>
            <td><spring:input path="displayName"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Register"></td>
        </tr>
    </table>
</spring:form>
</body>
</html>
