package com.farmapp.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();
    private static final String PROPERTY_FILE = "db.properties";

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream(PROPERTY_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTY_FILE);
            }
            properties.load(input);
            // Load the JDBC Driver class
            Class.forName(getDriverClass());
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver class not found", e);
        }
    }

    public static String getUrl() {
        String url = properties.getProperty("db.url");
        if (url == null || url.trim().isEmpty()) {
            throw new RuntimeException("Database URL not found in properties");
        }
        return url;
    }

    public static String getUsername() {
        String username = properties.getProperty("db.username");
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Database username not found in properties");
        }
        return username;
    }

    public static String getPassword() {
        String password = properties.getProperty("db.password");
        if (password == null) {
            throw new RuntimeException("Database password not found in properties");
        }
        return password;
    }

    public static String getDriverClass() {
        String driver = properties.getProperty("db.driver");
        if (driver == null || driver.trim().isEmpty()) {
            throw new RuntimeException("Database driver not found in properties");
        }
        return driver;
    }

    // Additional useful configuration methods
    public static int getMaxPoolSize() {
        return Integer.parseInt(properties.getProperty("db.maxPoolSize", "10"));
    }

    public static int getMinIdle() {
        return Integer.parseInt(properties.getProperty("db.minIdle", "5"));
    }

    public static long getMaxLifetime() {
        return Long.parseLong(properties.getProperty("db.maxLifetime", "1800000"));
    }

    public static long getConnectionTimeout() {
        return Long.parseLong(properties.getProperty("db.connectionTimeout", "30000"));
    }
}