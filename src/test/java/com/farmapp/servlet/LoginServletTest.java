package com.farmapp.servlet;

import com.farmapp.dao.FarmerDAO;
import com.farmapp.model.Farmer;
import org.junit.jupiter.api.*;
import org.mockito.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private FarmerDAO farmerDAO;

    private LoginServlet loginServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        loginServlet = new LoginServlet();

        // Use reflection to inject the mock FarmerDAO
        java.lang.reflect.Field farmerDAOField = LoginServlet.class.getDeclaredField("farmerDAO");
        farmerDAOField.setAccessible(true);
        farmerDAOField.set(loginServlet, farmerDAO);

        // Setup common mock behavior
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoPost_SuccessfulLogin() throws Exception {
        // Prepare test data
        String username = "johndoe";
        String password = "password123";

        Farmer farmer = new Farmer();
        farmer.setId(1L);
        farmer.setUsername(username);
        farmer.setPassword(password);
        farmer.setFullName("John Doe");
        farmer.setEmail("john@example.com");
        farmer.setPhone("123-456-7890");

        // Setup mock behavior
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(farmerDAO.verifyPassword(username, password)).thenReturn(true);
        when(farmerDAO.findByUsername(username)).thenReturn(Optional.of(farmer));

        // Execute the test
        loginServlet.doPost(request, response);

        // Verify the results
        verify(session).setAttribute("farmer", farmer);
        verify(response).sendRedirect("dashboard.jsp");
        verify(request, never()).setAttribute(eq("error"), anyString());
    }

    @Test
    public void testDoPost_InvalidPassword() throws Exception {
        // Prepare test data
        String username = "johndoe";
        String password = "wrongpassword";

        // Setup mock behavior
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(farmerDAO.verifyPassword(username, password)).thenReturn(false);

        // Execute the test
        loginServlet.doPost(request, response);

        // Verify the results
        verify(request).setAttribute("error", "Invalid username or password.");
        verify(requestDispatcher).forward(request, response);
        verify(session, never()).setAttribute(eq("farmer"), any(Farmer.class));
    }

    @Test
    public void testDoPost_EmptyCredentials() throws Exception {
        // Setup mock behavior
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");

        // Execute the test
        loginServlet.doPost(request, response);

        // Verify the results
        verify(request).setAttribute("error", "Username and password must not be empty.");
        verify(requestDispatcher).forward(request, response);
        verify(session, never()).setAttribute(eq("farmer"), any(Farmer.class));
    }

    @Test
    public void testDoPost_DatabaseError() throws Exception {
        // Prepare test data
        String username = "johndoe";
        String password = "password123";

        // Setup mock behavior to simulate SQL exception
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(farmerDAO.verifyPassword(username, password)).thenThrow(new SQLException("Database error"));

        // Execute the test
        loginServlet.doPost(request, response);

        // Verify the results
        verify(request).setAttribute("error", "An error occurred during login. Please try again.");
        verify(requestDispatcher).forward(request, response);
        verify(session, never()).setAttribute(eq("farmer"), any(Farmer.class));
    }

    @Test
    public void testDoGet() throws Exception {
        // Execute the test
        loginServlet.doGet(request, response);

        // Verify forward to login page
        verify(requestDispatcher).forward(request, response);
    }

    @AfterEach
    public void tearDown() {
        loginServlet = null;
    }
}