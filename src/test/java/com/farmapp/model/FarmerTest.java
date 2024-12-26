package com.farmapp.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

public class FarmerTest {

    @Test
    public void testFarmerDefaultConstructor() {
        Farmer farmer = new Farmer();
        assertNotNull(farmer);
    }

    @Test
    public void testFarmerConstructorWithAddress() {
        String username = "johndoe";
        String password = "password123";
        String fullName = "John Doe";
        String email = "john@example.com";
        String phone = "123-456-7890";
        String address = "Farmville";

        Farmer farmer = new Farmer(username, password, fullName, email, phone, address);

        assertEquals(username, farmer.getUsername());
        assertEquals(password, farmer.getPassword());
        assertEquals(fullName, farmer.getFullName());
        assertEquals(email, farmer.getEmail());
        assertEquals(phone, farmer.getPhone());
        assertEquals(address, farmer.getAddress());
    }

    @Test
    public void testFarmerConstructorWithoutAddress() {
        String username = "johndoe";
        String password = "password123";
        String fullName = "John Doe";
        String email = "john@example.com";
        String phone = "123-456-7890";

        Farmer farmer = new Farmer(username, password, fullName, email, phone);

        assertEquals(username, farmer.getUsername());
        assertEquals(password, farmer.getPassword());
        assertEquals(fullName, farmer.getFullName());
        assertEquals(email, farmer.getEmail());
        assertEquals(phone, farmer.getPhone());
        assertNull(farmer.getAddress());
    }

    @Test
    public void testSettersAndGetters() {
        Farmer farmer = new Farmer();

        Long id = 1L;
        String username = "johndoe";
        String password = "password123";
        String fullName = "John Doe";
        String email = "john@example.com";
        String phone = "123-456-7890";
        String address = "Farmville";
        Timestamp registrationDate = new Timestamp(System.currentTimeMillis());

        farmer.setId(id);
        farmer.setUsername(username);
        farmer.setPassword(password);
        farmer.setFullName(fullName);
        farmer.setEmail(email);
        farmer.setPhone(phone);
        farmer.setAddress(address);
        farmer.setRegistrationDate(registrationDate);

        assertEquals(id, farmer.getId());
        assertEquals(username, farmer.getUsername());
        assertEquals(password, farmer.getPassword());
        assertEquals(fullName, farmer.getFullName());
        assertEquals(email, farmer.getEmail());
        assertEquals(phone, farmer.getPhone());
        assertEquals(address, farmer.getAddress());
        assertEquals(registrationDate, farmer.getRegistrationDate());
    }

    @Test
    public void testToString() {
        Farmer farmer = new Farmer("johndoe", "password123", "John Doe", "john@example.com", "123-456-7890", "Farmville");
        farmer.setId(1L);
        Timestamp registrationDate = new Timestamp(System.currentTimeMillis());
        farmer.setRegistrationDate(registrationDate);

        String toString = farmer.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("username='johndoe'"));
        assertTrue(toString.contains("fullName='John Doe'"));
        assertTrue(toString.contains("email='john@example.com'"));
        assertTrue(toString.contains("phone='123-456-7890'"));
        assertTrue(toString.contains("address='Farmville'"));
        assertTrue(toString.contains("registrationDate=" + registrationDate));
    }

    @Test
    public void testEqualsAndHashCode() {
        Farmer farmer1 = new Farmer("johndoe", "password123", "John Doe", "john@example.com", "123-456-7890");
        Farmer farmer2 = new Farmer("johndoe", "password123", "John Doe", "john@example.com", "123-456-7890");
        Farmer farmer3 = new Farmer("janedoe", "password456", "Jane Doe", "jane@example.com", "987-654-3210");

        farmer1.setId(1L);
        farmer2.setId(1L);
        farmer3.setId(2L);

        // Test equals
        assertTrue(farmer1.equals(farmer2));
        assertFalse(farmer1.equals(farmer3));
        assertTrue(farmer1.equals(farmer1)); // reflexivity
        assertFalse(farmer1.equals(null));
        assertFalse(farmer1.equals(new Object()));

        // Test hashCode
        assertEquals(farmer1.hashCode(), farmer2.hashCode());
        assertNotEquals(farmer1.hashCode(), farmer3.hashCode());
    }
}