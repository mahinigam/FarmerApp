package com.farmapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/farmapp"; // Change to your DB URL
    private static final String USER = "root"; // Change to your DB username
    private static final String PASSWORD = "root"; // Change to your DB password
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver"; // MySQL driver class

    // Load the MySQL driver and establish the connection
    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    /**
     * This method establishes a connection to the database.
     *
     * @return a connection to the database
     * @throws SQLException if there is a database error
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Establishing a connection to the database
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Handle any errors that may have occurred
            throw new SQLException("Error while connecting to the database", e);
        }
    }

    /**
     * This method closes the connection and other resources.
     *
     * @param conn the connection to close
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method closes the connection, statement, and result set.
     *
     * @param conn the connection to close
     * @param stmt the statement to close
     * @param rs   the result set to close
     */
    public static void closeResources(Connection conn, java.sql.Statement stmt, java.sql.ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
