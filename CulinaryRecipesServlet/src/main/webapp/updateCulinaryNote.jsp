<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update Culinary Note</title>
</head>
<body>
    <h1>Update Culinary Note</h1>
    <form action="${pageContext.request.contextPath}/updateCulinaryNote" method="post">
        <input type="hidden" name="idCulinaryNote" value="${culinaryNote.idCulinaryNote}"/>

        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${culinaryNote.name}" required/><br/><br/>

        <label for="description">Description:</label><br/>
        <textarea id="description" name="description" rows="4" cols="50" required>${culinaryNote.description}</textarea><br/><br/>

        <label for="instructions">Instructions:</label><br/>
        <textarea id="instructions" name="instructions" rows="4" cols="50" required>${culinaryNote.instructions}</textarea><br/><br/>

        <label>Select ingredients:</label><br/>
                <c:forEach var="ingredient" items="${allIngredients}">
                    <input type="checkbox" id="${ingredient.idIngredient}" name="ingredients" value="${ingredient.idIngredient}"
                        <c:if test="${ingredientsInCulinaryNote.contains(ingredient.idIngredient)}">checked</c:if>/>
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
            <input type="checkbox" id="${category}" name="categories" value="${category}"
                   <c:if test="${categoriesOfCulinaryNote.contains(category)}">checked</c:if>/>
            <label for="${category}">${category}</label><br/>
        </c:forEach>
        <c:if test="${not empty requestScope.error}">
                            <p style="color: red;"><c:out value="${requestScope.error}"/></p>
        </c:if>
        <input type="submit" value="update"/>
    </form>
</body>
</html>
