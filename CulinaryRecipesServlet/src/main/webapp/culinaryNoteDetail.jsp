<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Culinary Note Details</title>
</head>
<body>
    <h1><c:out value="${culinaryNote.name}"/> Details</h1>
    <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.idUser == culinaryNote.user.idUser}">
                        <form action="${pageContext.request.contextPath}/updateCulinaryNote" method="get">
                            <input type="hidden" name="action" value="update"/>
                            <input type="hidden" name="idCulinaryNote" value="${culinaryNote.idCulinaryNote}"/>
                            <input type="submit" value="Update"/>
                        </form>
                        <form action="${pageContext.request.contextPath}/culinaryNotes" method="post">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="idCulinaryNote" value="${culinaryNote.idCulinaryNote}"/>
                            <input type="submit" value="Delete"/>
                        </form>
    </c:if>
    <form action="${pageContext.request.contextPath}/culinaryNotes" method="get">
                            <input type="hidden" name="action" value="viewAll"/>
                            <input type="submit" value="View all"/>
    </form>
    <ul>
        <li>Author: <c:out value="${culinaryNote.user.firstName}"/> <c:out value="${culinaryNote.user.lastName}"/> </li>
        <li>Ingredients:
             <ul>
                 <c:forEach var="ingredientInCulinaryNote" items="${culinaryNote.ingredientsInCulinaryNote}">
                     <li> <c:out value="${ingredientInCulinaryNote.ingredient.name}"/>
                          <c:out value="${ingredientInCulinaryNote.amount}"/>
                          <c:out value="${ingredientInCulinaryNote.unitOfMeasurement.name()}"/>
                     </li>
                 </c:forEach>
             </ul>
        </li>
        <li>Description: <c:out value="${culinaryNote.description}"/></li>
        <li>Instructions: <c:out value="${culinaryNote.instructions}"/></li>
        <li>Categories:
            <c:forEach var="category" items="${culinaryNote.categories}">
                <c:out value="${category}"/>
            </c:forEach>
        </li>
    </ul>
</body>
</html>
