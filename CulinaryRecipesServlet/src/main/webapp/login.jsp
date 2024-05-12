<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Authentication</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .login-form-container {
            width: 50%;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"],
        input[type="password"],
        button {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        .message {
            color: blue;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="login-form-container">
<h1>Culinary Notes Web App</h1><br/>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>
            Email
            <input type="text" name="email">
        </label>
        <br>
        <label>
            Password
            <input type="password" name="password">
        </label>
        <br>
        <button type="submit">Authorize</button>
    </form>
    <c:if test="${not empty requestScope.error}">
        <p style="color: red;"><c:out value="${requestScope.error}"/></p>
    </c:if>
    <form action="${pageContext.request.contextPath}/register" method="get">
        <button type="submit" class="register-btn">Register</button>
    </form>
</div>
</body>
</html>
