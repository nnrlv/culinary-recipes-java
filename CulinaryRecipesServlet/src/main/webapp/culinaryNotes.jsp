<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Culinary Notes</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        h1, h2 {
            text-align: center;
            color: #333;
        }
        .add-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px 0;
            cursor: pointer;
        }
        .culinaryNote-item {
            background-color: #f9f9f9;
            padding: 20px;
            margin: 10px 0;
        }
        .auth-button-container {
            text-align: center;
            margin-top: 20px;
        }
        .auth-button {
            background-color: #008CBA;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px 0;
            cursor: pointer;
        }
        .auth-button:hover, .add-button:hover {
            background-color: #0073e6;
        }
        hr {
            border: 0;
            border-top: 1px solid #ccc;
            margin: 10px 0;
        }
    </style>
</head>

<body>

<h1>Culinary Notes Web App</h1><br/>

<div class="auth-button-container">
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <!-- If the user is authenticated, show the "Logout" button -->
            <p>Welcome, <c:out value="${sessionScope.user.email}"/></p>
            <form action="${pageContext.request.contextPath}/logout" method="get">
                <input type="submit" value="Logout" class="auth-button"/>
            </form>
        </c:when>
        <c:otherwise>
            <!-- If the user is not authenticated, show the "Login" button -->
            <form action="${pageContext.request.contextPath}/login" method="post">
                <input type="submit" value="Login" class="auth-button"/>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<h2>All Available Culinary Notes</h2><br/>

<c:if test="${sessionScope.user.role == 'USER'}">
    <form action="${pageContext.request.contextPath}/addCulinaryNote" method="get">
        <button type="submit" class="add-button">Add Recipe</button>
    </form>
</c:if>

<c:choose>
    <c:when test="${not empty requestScope.culinaryNotes}">
        <c:forEach var="culinaryNote" items="${requestScope.culinaryNotes}">
            <div class="culinaryNote-item">
                <ul>
                    <li>Name: <c:out value="${culinaryNote.name}"/></li>
                    <li>Description: <c:out value="${culinaryNote.description}"/></li>
                    <li>Instructions: <c:out value="${culinaryNote.instructions}"/></li>
                </ul>
                <!-- Additional actions for administrators -->
                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                    <form action="${pageContext.request.contextPath}/updateCulinaryNote" method="get">
                        <button type="submit" class="change-button" name="id" value="${culinaryNote.idCulinaryNote}">Update</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/deleteCulinaryNote" method="get">
                        <button type="submit" class="delete-button" name="id" value="${culinaryNote.idCulinaryNote}">Delete</button>
                    </form>
                </c:if>
            </div>
            <hr/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>No culinary notes found.</p>
    </c:otherwise>
</c:choose>

</body>
</html>
