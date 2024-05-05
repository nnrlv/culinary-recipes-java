<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ingredients</title>
</head>
<body>
<h1>Ingredients</h1>

<c:if test="${sessionScope.user.role eq 'ADMIN'}">
    <!-- Добавление ингредиента доступно только администраторам -->
    <form action="${pageContext.request.contextPath}/createIngredient" method="get">
        <input type="hidden" name="action" value="create"/>
        <input type="submit" value="Add Ingredient"/>
    </form>
</c:if>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ingredient" items="${ingredients}">
        <tr>
            <td><c:out value="${ingredient.idIngredient}"/></td>
            <td><c:out value="${ingredient.name}"/></td>
            <td>
                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                    <form action="${pageContext.request.contextPath}/updateIngredient" method="get">
                        <input type="hidden" name="action" value="update"/>
                        <input type="hidden" name="idIngredient" value="${ingredient.idIngredient}"/>
                        <input type="submit" value="Edit"/>
                    </form>
                    <form action="${pageContext.request.contextPath}/ingredients" method="post">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="idIngredient" value="${ingredient.idIngredient}"/>
                        <input type="submit" value="Delete"/>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
