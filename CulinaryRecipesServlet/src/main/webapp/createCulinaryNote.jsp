<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Culinary Note</title>
</head>
<body>
    <h1>Create New Culinary Note</h1>
    <form action="${pageContext.request.contextPath}/createCulinaryNote" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required/><br/><br/>

        <label for="description">Description:</label><br/>
        <textarea id="description" name="description" rows="4" cols="50" required></textarea><br/><br/>

        <label for="instructions">Instructions:</label><br/>
        <textarea id="instructions" name="instructions" rows="4" cols="50" required></textarea><br/><br/>

        <label>Select ingredients:</label><br/>
        <c:forEach var="ingredient" items="${ingredients}">
            <input type="checkbox" id="${ingredient.idIngredient}" name="ingredients" value="${ingredient.idIngredient}"/>
            <label for="${ingredient.idIngredient}">${ingredient.name}</label>
            <label>Select unit of measurement:</label><br/>
            <select id="unit_${ingredient.idIngredient}" name="unit_${ingredient.idIngredient}">
                <c:forEach var="unit" items="${allUnitOfMeasurementNames}">
                    <option value="${unit}">${unit}</option>
                </c:forEach>
            </select>
            <input type="text" id="amount_${ingredient.idIngredient}" name="amount_${ingredient.idIngredient}" placeholder="Amount"/><br/>
        </c:forEach>

        <label>Select categories:</label><br/>
        <c:forEach var="category" items="${allCategoryNames}">
            <input type="checkbox" id="${category}" name="categories" value="${category}"/>
            <label for="${category}">${category}</label><br/>
        </c:forEach>

        <input type="submit" value="Create"/>
    </form>
</body>
</html>
