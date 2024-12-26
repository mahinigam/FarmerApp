package com.farmapp.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {

    private Long id;
    private Long farmerId;
    private String name;
    private String description;
    private String category;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal price;
    private String imageUrl;
    private Timestamp createdDate;

    // Default Constructor
    public Product() {
    }

    // Constructor for creating new Product (without id)
    public Product(Long farmerId, String name, String description, String category,
                   BigDecimal quantity, String unit, BigDecimal price, String imageUrl) {
        this.farmerId = farmerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Constructor for updating existing Product (with id)
    public Product(Long id, Long farmerId, String name, String description, String category,
                   BigDecimal quantity, String unit, BigDecimal price, String imageUrl) {
        this.id = id;
        this.farmerId = farmerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", farmerId=" + farmerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    // equals and hashCode methods for object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id != null ? id.equals(product.id) : product.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
