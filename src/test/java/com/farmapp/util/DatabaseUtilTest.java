package com.farmapp.util;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseUtilTest {

    @Mock
    private Connection mockConnection;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetConnection_Success() throws SQLException {
        // Mock the DriverManager's getConnection method to return a mock connection
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Call the method to test
            Connection conn = DatabaseUtil.getConnection();

            // Verify that the connection was successfully returned
            assertNotNull(conn);
            assertSame(mockConnection, conn);
        }
    }

    @Test
    public void testGetConnection_Failure() throws SQLException {
        // Mock DriverManager to throw an SQLException
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenThrow(new SQLException("Database connection error"));

            // Assert that an SQLException is thrown
            SQLException exception = assertThrows(SQLException.class, () -> {
                DatabaseUtil.getConnection();
            });

            assertEquals("Error while connecting to the database", exception.getMessage());
        }
    }

    @Test
    public void testCloseConnection_Success() throws SQLException {
        // Verify that the closeConnection method works without throwing exceptions
        DatabaseUtil.closeConnection(mockConnection);

        // Verify that the close method is called on the mock connection
        verify(mockConnection).close();
    }

    @Test
    public void testCloseResources_Success() throws SQLException {
        // Create mock Statement and ResultSet
        java.sql.Statement mockStatement = mock(java.sql.Statement.class);
        java.sql.ResultSet mockResultSet = mock(java.sql.ResultSet.class);

        // Call the closeResources method
        DatabaseUtil.closeResources(mockConnection, mockStatement, mockResultSet);

        // Verify that all resources were closed
        verify(mockResultSet).close();
        verify(mockStatement).close();
        verify(mockConnection).close();
    }

    @Test
    public void testCloseResources_ConnectionClosedWithoutExceptions() throws SQLException {
        // Mock the connection to be already closed
        when(mockConnection.isClosed()).thenReturn(true);

        // Call closeResources and verify no exceptions are thrown
        DatabaseUtil.closeResources(mockConnection, null, null);

        // Verify that close() on the connection is not called due to the connection being closed
        verify(mockConnection, never()).close();
    }

    @AfterEach
    public void tearDown() {
        // Clean up resources if necessary
    }
}
