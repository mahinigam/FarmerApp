package com.farmapp.dao;

import com.farmapp.model.Product;
import com.farmapp.util.DatabaseUtil;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ProductDAOTest {

    private ProductDAO productDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<DatabaseUtil> mockDatabaseUtil;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockDatabaseUtil = mockStatic(DatabaseUtil.class);
        mockDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);

        productDAO = new ProductDAO();
    }

    @Test
    public void testSaveProduct() throws SQLException {
        Product product = new Product();
        product.setFarmerId(1L);
        product.setName("Apple");
        product.setDescription("Fresh Red Apples");
        product.setCategory("Fruit");
        product.setQuantity(BigDecimal.valueOf(100));
        product.setUnit("kg");
        product.setPrice(BigDecimal.valueOf(3.5));
        product.setImageUrl("image_url");

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(1L);

        productDAO.save(product);

        verify(mockPreparedStatement).setLong(1, product.getFarmerId());
        verify(mockPreparedStatement).setString(2, product.getName());
        verify(mockPreparedStatement).setString(3, product.getDescription());
        verify(mockPreparedStatement).setString(4, product.getCategory());
        verify(mockPreparedStatement).setBigDecimal(5, product.getQuantity());
        verify(mockPreparedStatement).setString(6, product.getUnit());
        verify(mockPreparedStatement).setBigDecimal(7, product.getPrice());
        verify(mockPreparedStatement).setString(8, product.getImageUrl());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testFindProductById() throws SQLException {
        Long productId = 1L;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        // Mock all the required fields from mapResultSetToProduct
        when(mockResultSet.getLong("id")).thenReturn(productId);
        when(mockResultSet.getLong("farmer_id")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("Apple");
        when(mockResultSet.getString("description")).thenReturn("Fresh Red Apples");
        when(mockResultSet.getString("category")).thenReturn("Fruit");
        when(mockResultSet.getBigDecimal("quantity")).thenReturn(BigDecimal.valueOf(100));
        when(mockResultSet.getString("unit")).thenReturn("kg");
        when(mockResultSet.getBigDecimal("price")).thenReturn(BigDecimal.valueOf(3.5));
        when(mockResultSet.getString("image_url")).thenReturn("image_url");
        when(mockResultSet.getTimestamp("created_date")).thenReturn(new Timestamp(System.currentTimeMillis()));

        Optional<Product> product = productDAO.findById(productId);

        assertTrue(product.isPresent());
        assertEquals(productId, product.get().getId());
        assertEquals("Apple", product.get().getName());
    }

    @Test
    public void testFindProductsByCategory() throws SQLException {
        String category = "Fruit";
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); // Returns true once, then false

        // Mock all the required fields from mapResultSetToProduct
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getLong("farmer_id")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("Apple");
        when(mockResultSet.getString("description")).thenReturn("Fresh Red Apples");
        when(mockResultSet.getString("category")).thenReturn(category);
        when(mockResultSet.getBigDecimal("quantity")).thenReturn(BigDecimal.valueOf(100));
        when(mockResultSet.getString("unit")).thenReturn("kg");
        when(mockResultSet.getBigDecimal("price")).thenReturn(BigDecimal.valueOf(3.5));
        when(mockResultSet.getString("image_url")).thenReturn("image_url");
        when(mockResultSet.getTimestamp("created_date")).thenReturn(new Timestamp(System.currentTimeMillis()));

        List<Product> products = productDAO.findByCategory(category);

        assertFalse(products.isEmpty());
        assertEquals(category, products.get(0).getCategory());
    }

    @Test
    public void testSearchProducts() throws SQLException {
        String searchTerm = "Apple";
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);

        // Mock all the required fields
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getLong("farmer_id")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("Apple");
        when(mockResultSet.getString("description")).thenReturn("Fresh Red Apples");
        when(mockResultSet.getString("category")).thenReturn("Fruit");
        when(mockResultSet.getBigDecimal("quantity")).thenReturn(BigDecimal.valueOf(100));
        when(mockResultSet.getString("unit")).thenReturn("kg");
        when(mockResultSet.getBigDecimal("price")).thenReturn(BigDecimal.valueOf(3.5));
        when(mockResultSet.getString("image_url")).thenReturn("image_url");
        when(mockResultSet.getTimestamp("created_date")).thenReturn(new Timestamp(System.currentTimeMillis()));

        List<Product> products = productDAO.searchProducts(searchTerm);

        assertFalse(products.isEmpty());
        assertTrue(products.get(0).getName().contains(searchTerm) ||
                products.get(0).getDescription().contains(searchTerm));
    }

    @Test
    public void testDeleteProduct() throws SQLException {
        Long productId = 1L;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = productDAO.delete(productId);

        assertTrue(result);
        verify(mockPreparedStatement).setLong(1, productId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        mockConnection.close();
        mockDatabaseUtil.close();
    }
}