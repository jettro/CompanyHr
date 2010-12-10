<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>Change project</title>
</head>
<body>
<spring:form method="POST" commandName="projectEntry" action="/project/update">
    <spring:hidden path="id"/>
    <spring:hidden path="identifier"/>
    <table>
        <tr>
            <td>Old project name:</td>
            <td><c:out value="${projectEntry.name}"/></td>
        </tr>
        <tr>
            <td>New project name : </td>
            <td><spring:input path="name"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Update"> </td>
        </tr>
    </table>
</spring:form>
</body>
</html>