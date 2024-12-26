package com.farmapp.servlet;

import com.farmapp.dao.FarmerDAO;
import com.farmapp.model.Farmer;
import com.farmapp.util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private FarmerDAO farmerDAO;

    @Override
    public void init() throws ServletException {
        // Initialize the DAO with a database connection
        try {
            Connection connection = DatabaseUtil.getConnection();
            farmerDAO = new FarmerDAO(connection);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize LoginServlet: Unable to connect to database.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show login page
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMessage = null;

        // Check if username and password are not empty
        if (username != null && password != null && !username.trim().isEmpty() && !password.trim().isEmpty()) {
            // Verify the password using the DAO method
            if (farmerDAO.verifyPassword(username, password)) {
                // Fetch the farmer details if password matches
                Farmer farmer = farmerDAO.findByUsername(username).orElse(null);

                if (farmer != null) {
                    // Successful login, save farmer info in session
                    request.getSession().setAttribute("farmer", farmer);
                    response.sendRedirect("dashboard.jsp");  // Redirect to dashboard
                } else {
                    errorMessage = "Farmer not found!";
                    request.setAttribute("error", errorMessage);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                errorMessage = "Invalid username or password.";
                request.setAttribute("error", errorMessage);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            errorMessage = "Username and password must not be empty.";
            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
        // Note: Closing connection is optional if handled by a connection pool
    }
}
