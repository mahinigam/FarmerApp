package com.farmapp.servlet;

import com.farmapp.dao.FarmerDAO;
import com.farmapp.model.Farmer;
import org.junit.jupiter.api.*;
import org.mockito.*;

import javax.servlet.*;
import javax.servlet.http.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FarmerRegistrationServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FarmerDAO farmerDAO;

    private FarmerRegistrationServlet farmerRegistrationServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        farmerRegistrationServlet = new FarmerRegistrationServlet();
    }

    @Test
    public void testDoPost() throws Exception {
        // Mock request parameters for farmer registration
        when(request.getParameter("name")).thenReturn("John Doe");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getParameter("location")).thenReturn("Farmville");
        when(request.getParameter("phoneNumber")).thenReturn("123-456-7890");

        // Call the servlet's doPost method
        farmerRegistrationServlet.doPost(request, response);

        // Verify that the farmer was saved in the database
        verify(farmerDAO).save(any(Farmer.class));

        // Verify that the response redirects to the correct page
        verify(response).sendRedirect("login.jsp");
    }

    @AfterEach
    public void tearDown() {
        farmerRegistrationServlet = null;
    }
}
