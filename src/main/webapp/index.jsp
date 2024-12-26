<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FarmApp - Homepage</title>
    <link rel="stylesheet" href="styles.css"> <!-- Add your CSS file link here -->
</head>
<body>

<header>
    <h1>Welcome to FarmApp</h1>
    <nav>
        <ul>
            <li><a href="login.jsp">Login</a></li>
            <li><a href="register.jsp">Register</a></li>
        </ul>
    </nav>
</header>

<main>
    <h2>Your gateway to a sustainable farming experience</h2>

    <!-- Display login form -->
    <div class="login-form">
        <form action="login" method="post">
            <h3>Login</h3>

            <!-- Display login error message if available -->
            <c:if test="${not empty requestScope.loginError}">
                <div class="alert error">
                        ${requestScope.loginError}
                </div>
            </c:if>

            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <button type="submit">Login</button>
            </div>
        </form>
    </div>

    <p>Don't have an account? <a href="register.jsp">Register here</a></p>
</main>

<footer>
    <p>&copy; 2024 FarmApp. All rights reserved.</p>
</footer>

</body>
</html>
<html>
<body>
<h2>Hello World!</h2>
</body>
</html>
