<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<h1>Projects</h1>
<c:forEach var="project" items="${projects}">
    <c:out value="${project.name}"/>
</c:forEach>