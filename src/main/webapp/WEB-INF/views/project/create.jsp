<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<spring:form method="POST" commandName="projectEntry">
    <table>
        <tr>
            <td>Project name : </td>
            <td><spring:input path="name"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Create"> </td>
        </tr>
    </table>

</spring:form>