package com.farmapp.dao;

import com.farmapp.model.Product;
import com.farmapp.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {

    // Create
    public void save(Product product) throws SQLException {
        String sql = "INSERT INTO products (farmer_id, name, description, category, quantity, unit, price, image_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, product.getFarmerId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getCategory());
            stmt.setBigDecimal(5, product.getQuantity());
            stmt.setString(6, product.getUnit());
            stmt.setBigDecimal(7, product.getPrice());
            stmt.setString(8, product.getImageUrl());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    // Read by ID
    public Optional<Product> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToProduct(rs));
                }
            }
        }
        return Optional.empty();
    }

    // Read all products by farmer ID
    public List<Product> findByFarmerId(Long farmerId) throws SQLException {
        String sql = "SELECT * FROM products WHERE farmer_id = ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, farmerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        }
        return products;
    }

    // Read all products
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        }
        return products;
    }

    // Read products by category
    public List<Product> findByCategory(String category) throws SQLException {
        String sql = "SELECT * FROM products WHERE category = ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        }
        return products;
    }

    // Update
    public void update(Product product) throws SQLException {
        String sql = "UPDATE products SET farmer_id = ?, name = ?, description = ?, category = ?, " +
                "quantity = ?, unit = ?, price = ?, image_url = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, product.getFarmerId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getCategory());
            stmt.setBigDecimal(5, product.getQuantity());
            stmt.setString(6, product.getUnit());
            stmt.setBigDecimal(7, product.getPrice());
            stmt.setString(8, product.getImageUrl());
            stmt.setLong(9, product.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
        }
    }

    // Delete
    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete all products by farmer ID
    public boolean deleteByFarmerId(Long farmerId) throws SQLException {
        String sql = "DELETE FROM products WHERE farmer_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, farmerId);

            return stmt.executeUpdate() > 0;
        }
    }

    // Helper method to map ResultSet to Product object
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setFarmerId(rs.getLong("farmer_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setCategory(rs.getString("category"));
        product.setQuantity(rs.getBigDecimal("quantity"));
        product.setUnit(rs.getString("unit"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setImageUrl(rs.getString("image_url"));
        product.setCreatedDate(rs.getTimestamp("created_date"));
        return product;
    }

    // Search products by name or description
    public List<Product> searchProducts(String searchTerm) throws SQLException {
        String sql = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        }
        return products;
    }
}