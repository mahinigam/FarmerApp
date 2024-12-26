<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Farmer Dashboard</title>
    <link rel="stylesheet" href="styles.css"> <!-- Add your CSS file link here -->
</head>
<body>

<header>
    <h1>Welcome to the Farmer Dashboard</h1>
    <nav>
        <ul>
            <li><a href="manage-products.jsp">Manage Products</a></li>
            <li><a href="view-orders.jsp">View Orders</a></li>
            <li><a href="profile.jsp">My Profile</a></li>
            <li><a href="logout.jsp">Logout</a></li>
        </ul>
    </nav>
</header>

<main>
    <h2>Dashboard Overview</h2>

    <!-- Display success or error message -->
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="alert success">
                ${sessionScope.successMessage}
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.errorMessage}">
        <div class="alert error">
                ${sessionScope.errorMessage}
        </div>
    </c:if>

    <!-- Add some basic stats or content -->
    <div class="overview">
        <div class="stat">
            <h3>Total Products</h3>
            <p>${totalProducts}</p>
        </div>
        <div class="stat">
            <h3>Total Orders</h3>
            <p>${totalOrders}</p>
        </div>
        <div class="stat">
            <h3>Active Listings</h3>
            <p>${activeListings}</p>
        </div>
    </div>
</main>

<footer>
    <p>&copy; 2024 FarmApp. All rights reserved.</p>
</footer>

</body>
</html>
