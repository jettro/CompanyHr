<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>All projects</title>
</head>
<body>

<h1>Projects</h1>

<p><a href="${ctx}/project/create">Create new project</a></p>
<table>
    <c:forEach var="project" items="${projects}">
        <tr>
            <td><c:out value="${project.name}"/></td>
            <td><a href="${ctx}project/update/<c:out value="${project.id}"/>">change</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>