<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Farmer Registration</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<div class="container">
    <h2>Farmer Registration</h2>

    <!-- Display success message -->
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="alert success">
                ${sessionScope.successMessage}
        </div>
        <c:remove var="successMessage"/>
    </c:if>

    <!-- Display error messages -->
    <c:if test="${not empty requestScope.errors}">
        <div class="alert error">
            <ul>
                <c:forEach var="error" items="${requestScope.errors}">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </div>
        <c:remove var="errors"/>
    </c:if>

    <!-- Registration Form -->
    <form action="register" method="POST">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required placeholder="Enter username">
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required placeholder="Enter password">
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Confirm your password">
        </div>

        <div class="form-group">
            <label for="fullName">Full Name:</label>
            <input type="text" id="fullName" name="fullName" required placeholder="Enter full name">
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required placeholder="Enter email address">
        </div>

        <div class="form-group">
            <label for="phone">Phone Number:</label>
            <input type="text" id="phone" name="phone" placeholder="Enter phone number (10 digits)">
        </div>

        <div class="form-group">
            <label for="address">Address:</label>
            <textarea id="address" name="address" rows="4" required placeholder="Enter address"></textarea>
        </div>

        <div class="form-group">
            <button type="submit">Register</button>
        </div>
    </form>

    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</div>

</body>
</html>
