package com.farmapp.servlet;

import com.farmapp.dao.ProductDAO;
import com.farmapp.model.Product;
import org.junit.jupiter.api.*;
import org.mockito.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.math.BigDecimal;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProductDAO productDAO;

    private ProductServlet productServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        productServlet = new ProductServlet();
    }

    @Test
    public void testDoPost() throws Exception {
        // Mocking the form parameters for product creation
        when(request.getParameter("name")).thenReturn("Apple");
        when(request.getParameter("description")).thenReturn("Fresh Red Apples");
        when(request.getParameter("category")).thenReturn("Fruit");
        when(request.getParameter("quantity")).thenReturn("100");
        when(request.getParameter("unit")).thenReturn("kg");
        when(request.getParameter("price")).thenReturn("3.5");
        when(request.getParameter("imageUrl")).thenReturn("image_url");

        // Call the servlet's doPost method
        productServlet.doPost(request, response);

        // Verify that the product was saved
        verify(productDAO).save(any(Product.class));

        // Verify that the response redirects to the product list page
        verify(response).sendRedirect("productList.jsp");
    }

    @Test
    public void testDoGet() throws Exception {
        // Mock request for viewing a product
        Long productId = 1L;
        Product product = new Product(productId, "Apple", "Fresh Red Apples", "Fruit", BigDecimal.valueOf(100), "kg", BigDecimal.valueOf(3.5), "image_url");

        when(request.getParameter("id")).thenReturn("1");
        when(productDAO.findById(productId)).thenReturn(java.util.Optional.of(product));

        // Call the servlet's doGet method
        productServlet.doGet(request, response);

        // Verify that the product is set as a request attribute and forwarded
        verify(request).setAttribute("product", product);
        verify(request.getRequestDispatcher("/productDetail.jsp")).forward(request, response);
    }

    @AfterEach
    public void tearDown() {
        productServlet = null;
    }
}
