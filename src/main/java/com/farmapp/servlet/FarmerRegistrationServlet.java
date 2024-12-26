package com.farmapp.servlet;

import com.farmapp.dao.FarmerDAO;
import com.farmapp.model.Farmer;
import com.farmapp.util.DatabaseUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/register")
public class FarmerRegistrationServlet extends HttpServlet {
    private FarmerDAO farmerDAO;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^\\d{10}$";

    @Override
    public void init() throws ServletException {
        // Initialize the DAO with a database connection
        try {
            farmerDAO = new FarmerDAO(DatabaseUtil.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to registration page
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();

        // Retrieve parameters from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Validate input
        if (!validateInput(username, password, confirmPassword, fullName, email, phone, address, errors)) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            // Check if username already exists
            if (farmerDAO.existsByUsername(username)) {
                errors.add("Username already exists. Please choose another one.");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Check if email already exists
            if (farmerDAO.existsByEmail(email)) {
                errors.add("Email is already registered.");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Create a new Farmer object
            Farmer farmer = new Farmer();
            farmer.setUsername(username);
            farmer.setPassword(hashPassword(password));
            farmer.setFullName(fullName);
            farmer.setEmail(email);
            farmer.setPhone(phone);
            farmer.setAddress(address);

            // Save the Farmer object to the database
            farmerDAO.save(farmer);

            // Set a success message and redirect to login page
            request.getSession().setAttribute("successMessage", "Registration successful! Please log in.");
            response.sendRedirect("login.jsp");

        } catch (Exception e) {
            // Log and display error
            getServletContext().log("Error during farmer registration", e);
            request.setAttribute("error", "System error occurred during registration. Please try again later.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private boolean validateInput(String username, String password, String confirmPassword,
                                  String fullName, String email, String phone, String address,
                                  List<String> errors) {
        boolean isValid = true;

        if (username == null || username.trim().length() < 3) {
            errors.add("Username must be at least 3 characters long.");
            isValid = false;
        }

        if (password == null || password.length() < 6) {
            errors.add("Password must be at least 6 characters long.");
            isValid = false;
        }

        if (!password.equals(confirmPassword)) {
            errors.add("Passwords do not match.");
            isValid = false;
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            errors.add("Full name is required.");
            isValid = false;
        }

        if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
            errors.add("Invalid email format.");
            isValid = false;
        }

        if (phone != null && !phone.trim().isEmpty() && !Pattern.matches(PHONE_REGEX, phone)) {
            errors.add("Phone number must be exactly 10 digits.");
            isValid = false;
        }

        if (address == null || address.trim().isEmpty()) {
            errors.add("Address is required.");
            isValid = false;
        }

        return isValid;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // Log and rethrow as runtime exception
            throw new RuntimeException("Error hashing password", e);
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if required
    }
}
