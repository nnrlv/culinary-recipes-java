<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Ingredient</title>
</head>
<body>
    <h1>Create New Ingredient</h1>
    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <form action="${pageContext.request.contextPath}/createIngredient" method="post">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required/><br/><br/>
            <c:if test="${not empty requestScope.error}">
                                <p style="color: red;"><c:out value="${requestScope.error}"/></p>
            </c:if>
            <input type="submit" value="create"/>
        </form>
    </c:if>
</body>
</html>
