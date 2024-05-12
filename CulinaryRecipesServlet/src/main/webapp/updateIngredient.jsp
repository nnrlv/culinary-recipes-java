<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Ingredient</title>
</head>
<body>
    <h1>Update Ingredient</h1>
    <form action="${pageContext.request.contextPath}/updateIngredient" method="post">
        <input type="hidden" name="idIngredient" value="${ingredient.idIngredient}"/>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${ingredient.name}" required/><br/><br/>
        <input type="submit" value="update"/>
        <input type="hidden" name="error" value="${requestScope.error}" />
    </form>
    <c:if test="${not empty requestScope.error}">
        <p style="color: red;"><c:out value="${requestScope.error}"/></p>
    </c:if>
</body>
</html>
