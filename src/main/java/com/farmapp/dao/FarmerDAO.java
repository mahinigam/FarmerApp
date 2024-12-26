package com.farmapp.dao;

import com.farmapp.model.Farmer;
import com.farmapp.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FarmerDAO {

    private Connection connection;

    // Constructor with Connection parameter
    public FarmerDAO(Connection connection) {
        this.connection = connection;
    }

    // Save (Create)
    public void save(Farmer farmer) {
        String sql = "INSERT INTO farmers (username, password, full_name, email, phone, address) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, farmer.getUsername());
            stmt.setString(2, farmer.getPassword());
            stmt.setString(3, farmer.getFullName());
            stmt.setString(4, farmer.getEmail());
            stmt.setString(5, farmer.getPhone());
            stmt.setString(6, farmer.getAddress());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    farmer.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving farmer", e);
        }
    }

    // Find by ID (Read)
    public Optional<Farmer> findById(Long id) {
        String sql = "SELECT * FROM farmers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToFarmer(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding farmer by ID", e);
        }
        return Optional.empty();
    }

    // Find by Username (Read)
    public Optional<Farmer> findByUsername(String username) {
        String sql = "SELECT * FROM farmers WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToFarmer(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding farmer by username", e);
        }
        return Optional.empty();
    }

    // Verify Password (for login)
    public boolean verifyPassword(String username, String password) {
        Optional<Farmer> farmerOpt = findByUsername(username);
        if (farmerOpt.isPresent()) {
            Farmer farmer = farmerOpt.get();
            return farmer.getPassword().equals(password); // Compare plain-text passwords (not recommended in production)
        }
        return false;
    }

    // Find All (Read)
    public List<Farmer> findAll() {
        String sql = "SELECT * FROM farmers";
        List<Farmer> farmers = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                farmers.add(mapResultSetToFarmer(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all farmers", e);
        }
        return farmers;
    }

    // Update (Edit)
    public void update(Farmer farmer) {
        String sql = "UPDATE farmers SET username = ?, password = ?, full_name = ?, " +
                "email = ?, phone = ?, address = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, farmer.getUsername());
            stmt.setString(2, farmer.getPassword());
            stmt.setString(3, farmer.getFullName());
            stmt.setString(4, farmer.getEmail());
            stmt.setString(5, farmer.getPhone());
            stmt.setString(6, farmer.getAddress());
            stmt.setLong(7, farmer.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating farmer failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating farmer", e);
        }
    }

    // Delete
    public boolean delete(Long id) {
        String sql = "DELETE FROM farmers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting farmer", e);
        }
    }

    // Check if Username Exists
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM farmers WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if username exists", e);
        }
        return false;
    }

    // Check if Email Exists
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM farmers WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if email exists", e);
        }
        return false;
    }

    // Helper Method to Map ResultSet to Farmer
    private Farmer mapResultSetToFarmer(ResultSet rs) throws SQLException {
        Farmer farmer = new Farmer();
        farmer.setId(rs.getLong("id"));
        farmer.setUsername(rs.getString("username"));
        farmer.setPassword(rs.getString("password"));
        farmer.setFullName(rs.getString("full_name"));
        farmer.setEmail(rs.getString("email"));
        farmer.setPhone(rs.getString("phone"));
        farmer.setAddress(rs.getString("address"));
        farmer.setRegistrationDate(rs.getTimestamp("registration_date"));
        return farmer;
    }
}