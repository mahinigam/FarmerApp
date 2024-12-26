<%@ page import="com.farmapp.model.Product" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Product Form</title>
</head>
<body>

<h2><%= request.getAttribute("product") == null ? "Add New Product" : "Edit Product" %></h2>

<form action="product" method="post">
    <input type="hidden" name="action" value="<%= request.getAttribute("product") == null ? "create" : "update" %>">
    <input type="hidden" name="id" value="<%= request.getAttribute("product") != null ? ((Product) request.getAttribute("product")).getId() : "" %>">

    <label for="name">Product Name:</label>
    <input type="text" id="name" name="name" value="<%= request.getAttribute("product") != null ? ((Product) request.getAttribute("product")).getName() : "" %>" required><br>

    <label for="category">Category:</label>
    <input type="text" id="category" name="category" value="<%= request.getAttribute("product") != null ? ((Product) request.getAttribute("product")).getCategory() : "" %>" required><br>

    <label for="price">Price:</label>
    <input type="text" id="price" name="price" value="<%= request.getAttribute("product") != null ? ((Product) request.getAttribute("product")).getPrice() : "" %>" required><br>

    <label for="quantity">Quantity:</label>
    <input type="text" id="quantity" name="quantity" value="<%= request.getAttribute("product") != null ? ((Product) request.getAttribute("product")).getQuantity() : "" %>" required><br>

    <label for="unit">Unit:</label>
    <input type="text" id="unit" name="unit" value="<%= request.getAttribute("product") != null ? ((Product) request.getAttribute("product")).getUnit() : "" %>" required><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description"><%= request.getAttribute("product") != null ? ((Product) request.getAttribute("product")).getDescription() : "" %></textarea><br>

    <label for="imageUrl">Image URL:</label>
    <input type="text" id="imageUrl" name="imageUrl" value="<%= request.getAttribute("product") != null ? ((Product) request.getAttribute("product")).getImageUrl() : "" %>" required><br>

    <input type="submit" value="Save Product">
</form>

<a href="product?action=list">Back to Product List</a>

</body>
</html>
