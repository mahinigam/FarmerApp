<%@ page import="java.util.List" %>
<%@ page import="com.farmapp.model.Product" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
</head>
<body>

<h2>Product List</h2>

<!-- Search Form -->
<form action="product?action=search" method="get">
    <input type="text" name="searchTerm" placeholder="Search by name or description">
    <input type="submit" value="Search">
</form>

<!-- Displaying Product Table -->
<table border="1">
    <thead>
    <tr>
        <th>Name</th>
        <th>Category</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        for (Product product : products) {
    %>
    <tr>
        <td><%= product.getName() %></td>
        <td><%= product.getCategory() %></td>
        <td><%= product.getPrice() %></td>
        <td><%= product.getQuantity() %></td>
        <td>
            <a href="product?action=edit&id=<%= product.getId() %>">Edit</a> |
            <a href="product?action=delete&id=<%= product.getId() %>">Delete</a>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>

<a href="product_form.jsp">Add New Product</a>

</body>
</html>
