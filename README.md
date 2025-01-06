# Kisaan Mitra

An innovative e-commerce platform developed using Java, JSP, JSTL, JUnit, JDBC, and MySQL inside Maven. This project aims to connect farmers directly with consumers while providing essential agricultural services like cold storage and transportation.

## Table of Contents

- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Setup Instructions](#setup-instructions)
- [Features](#features)
- [How to Use](#how-to-use)
- [Testing](#testing)
- [Contributing](#contributing)

## Introduction

Kisaan Mitra is an e-commerce platform designed specifically for farmers. It helps farmers directly list and sell their produce to consumers, reducing middlemen and ensuring fair pricing. The platform also connects farmers to essential services like cold storage and transportation, streamlining the agricultural supply chain.

## Technologies Used

- Java
- JSP (Java Server Pages)
- JSTL (JavaServer Pages Standard Tag Library)
- JUnit
- JDBC (Java Database Connectivity)
- MySQL
- HTML
- CSS
- Bootstrap
- JavaScript

## Project Structure

The project follows a standard Maven-based project organization:
```
FarmerApp/
├── pom.xml                          
├── src/                              
│   ├── main/
│   │   ├── java/                     
│   │   │   └── com/
│   │   │       └── farmapp/
│   │   │           ├── config/       
│   │   │           │   └── DatabaseConfig.java
│   │   │           ├── dao/          
│   │   │           │   ├── FarmerDAO.java
│   │   │           │   └── ProductDAO.java
│   │   │           ├── model/        
│   │   │           │   ├── Farmer.java
│   │   │           │   └── Product.java
│   │   │           ├── servlet/      
│   │   │           │   ├── FarmerRegistrationServlet.java
│   │   │           │   ├── LoginServlet.java
│   │   │           │   └── ProductServlet.java
│   │   │           └── util/         
│   │   │               └── DatabaseUtil.java
│   │   ├── resources/                
│   │   │   └── db.properties        
│   │   └── webapp/                   
│   │       ├── WEB-INF/            
│   │       │   └── web.xml           
│   │       ├── css/                 
│   │       │   └── style.css         
│   │       ├── js/                  
│   │       │   └── validation.js     
│   │       ├── index.jsp             
│   │       ├── login.jsp             
│   │       ├── register.jsp          
│   │       ├── product_form.jsp      
│   │       ├── product_list.jsp      
│   │       └── dashboard.jsp         
│   └── test/                         
│       └── java/
│           └── com/
│               └── farmmarket/
│                   └── dao/          
│                       ├── FarmerDAOTest.java
│                       └── ProductDAOTest.java
│                   └── model/        
│                       ├── FarmerTest.java
│                       └── ProductTest.java
│                   └── servlet/      
│                       ├── FarmerRegistrationServletTest.java
│                       ├── LoginServletTest.java
│                       └── ProductServletTest.java
│                   └── util/         
│                       └── DatabaseUtilTest.java

```

## Database Schema

The database schema is designed to handle farmer management, product listings, and service integrations:

```sql
CREATE DATABASE farmapp;

USE farmapp;

CREATE TABLE farmers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) DEFAULT NULL
);

CREATE TABLE products (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    farmer_id BIGINT DEFAULT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT DEFAULT NULL,
    category VARCHAR(100) DEFAULT NULL,
    FOREIGN KEY (farmer_id) REFERENCES farmers(id)
);

```

## Setup Instructions

1. **Clone the repository:**
```bash
git clone https://github.com/yourusername/FarmApp.git
```

2. **Set up the development environment:**
   - Install JDK (Java Development Kit)
   - Set up your IDE (Eclipse, IntelliJ, etc.)
   - Install Apache Tomcat server

3. **Configure the database:**
   - Install MySQL
   - Create the database and tables using the provided schema
   - Update the `src/main/resources/db.properties` file with your database credentials

4. **Build and deploy:**
   - Build using Maven: `mvn clean install`
   - Deploy the WAR file to Tomcat's webapps directory
   - Access the application at `http://localhost:8080/FarmApp`

## Features

### Farmer Features
- User registration and authentication
- Product listing management
- Storage facility booking
- Transportation service requests
- Order management and tracking
- Direct customer communication

### Service Integration
- Cold storage facility connections
- Transportation service bookings
- Payment processing
- Inventory management

### Customer Features
- Product browsing and search
- Direct purchasing from farmers
- Order tracking
- Farmer communications

## How to Use

1. **For Farmers:**
   - Register as a farmer
   - List your products with details and images
   - Manage inventory and orders
   - Book storage and transport services

2. **For Customers:**
   - Browse available products
   - Contact farmers directly
   - Place orders
   - Track deliveries

3. **For Administrators:**
   - Manage user accounts
   - Monitor transactions
   - Oversee service integrations
   - Generate reports

## Testing

The project includes comprehensive unit tests using JUnit. To run the tests:

1. Navigate to the project directory:
```bash
cd FarmApp
```

2. Run the tests:
```bash
mvn test
```

Test coverage includes:
- DAO layer tests
- Service layer tests
- Controller tests
- Integration tests

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/improvement`)
3. Make your changes
4. Write or update tests as needed
5. Commit your changes (`git commit -m 'Add feature/improvement'`)
6. Push to the branch (`git push origin feature/improvement`)
7. Create a Pull Request

---

For additional information or support, please contact the development team or create an issue in the repository.
