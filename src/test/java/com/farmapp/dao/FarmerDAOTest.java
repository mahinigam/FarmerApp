package com.farmapp.dao;

import com.farmapp.model.Farmer;
import com.farmapp.util.DatabaseUtil;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.Optional;

public class FarmerDAOTest {

    private FarmerDAO farmerDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        farmerDAO = new FarmerDAO(mockConnection);
    }

    @Test
    public void testSaveFarmer() throws SQLException {
        Farmer farmer = new Farmer("johndoe", "password123", "John Doe", "john@example.com", "123-456-7890", "Farmville");

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);

        farmerDAO.save(farmer);

        verify(mockPreparedStatement).setString(1, farmer.getUsername());
        verify(mockPreparedStatement).setString(2, farmer.getPassword());
        verify(mockPreparedStatement).setString(3, farmer.getFullName());
        verify(mockPreparedStatement).setString(4, farmer.getEmail());
        verify(mockPreparedStatement).setString(5, farmer.getPhone());
        verify(mockPreparedStatement).setString(6, farmer.getAddress());

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testFindFarmerById() throws SQLException {
        Long farmerId = 1L;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("id")).thenReturn(farmerId);
        when(mockResultSet.getString("username")).thenReturn("johndoe");
        when(mockResultSet.getString("full_name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com");
        when(mockResultSet.getString("phone")).thenReturn("123-456-7890");
        when(mockResultSet.getString("address")).thenReturn("Farmville");
        when(mockResultSet.getTimestamp("registration_date")).thenReturn(new Timestamp(System.currentTimeMillis()));

        Optional<Farmer> farmer = farmerDAO.findById(farmerId);

        assertTrue(farmer.isPresent());
        assertEquals(farmerId, farmer.get().getId());
        assertEquals("johndoe", farmer.get().getUsername());
        assertEquals("John Doe", farmer.get().getFullName());
        assertEquals("john@example.com", farmer.get().getEmail());
        assertEquals("123-456-7890", farmer.get().getPhone());
        assertEquals("Farmville", farmer.get().getAddress());
    }

    @Test
    public void testUpdateFarmer() throws SQLException {
        Farmer farmer = new Farmer();
        farmer.setId(1L);
        farmer.setUsername("johndoe");
        farmer.setPassword("newpassword");
        farmer.setFullName("John Doe");
        farmer.setEmail("john@example.com");
        farmer.setPhone("987-654-3210");
        farmer.setAddress("New Farmville");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        farmerDAO.update(farmer);

        verify(mockPreparedStatement).setString(1, farmer.getUsername());
        verify(mockPreparedStatement).setString(2, farmer.getPassword());
        verify(mockPreparedStatement).setString(3, farmer.getFullName());
        verify(mockPreparedStatement).setString(4, farmer.getEmail());
        verify(mockPreparedStatement).setString(5, farmer.getPhone());
        verify(mockPreparedStatement).setString(6, farmer.getAddress());
        verify(mockPreparedStatement).setLong(7, farmer.getId());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteFarmer() throws SQLException {
        Long farmerId = 1L;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        boolean result = farmerDAO.delete(farmerId);

        assertTrue(result);
        verify(mockPreparedStatement).setLong(1, farmerId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        mockConnection.close();
    }
}