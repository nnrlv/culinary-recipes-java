<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Ingredient</title>
</head>
<body>
    <h1>Update Ingredient</h1>
    <form action="${pageContext.request.contextPath}/updateIngredient" method="post">
        <label for="name">Name:</label>
        <input type="hidden" name="idIngredient" value="${ingredient.idIngredient}"/>
        <input type="text" id="name" name="name" value="${ingredient.name}" required/><br/><br/>
        <input type="submit" value="Update"/>
    </form>
</body>
</html>
