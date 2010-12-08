<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<h1>Projects</h1>

<p><a href="project/create">Create new project</a></p>
<table>
    <c:forEach var="project" items="${projects}">
        <tr>
            <td><c:out value="${project.name}"/></td>
            <td><a href="project/update/<c:out value="${project.id}"/>">change</a></td>
        </tr>
    </c:forEach>
</table>
