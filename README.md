# Kisaan Mitra

Kisaan Mitra is an innovative e-commerce platform designed specifically for farmers. It helps farmers directly list and sell their produce to consumers, reducing middlemen and ensuring fair pricing. The platform also connects farmers to essential services like cold storage and transportation, streamlining the agricultural supply chain.

## Features
- **Direct Selling**: Farmers can list their products with descriptions, prices, and images.
- **Cold Storage Connectivity**: A network of cold storage options to preserve perishable goods.
- **Transport Systems Integration**: Linking farmers with reliable transportation services.
- **Customer Interaction**: Tools to communicate directly with buyers.

## Technology Stack
- **Backend**: Java, JSP
- **Database**: MySQL (or other database used)
- **Frontend**: HTML, CSS, JavaScript
- **Frameworks/Libraries**: (List any frameworks or libraries used)

## How to Run the Project
1. Clone the repository: `git clone <repo-url>`
2. Set up the database and configure `db.properties`.
3. Build the project using Maven: `mvn clean install`
4. Deploy the app to a servlet container (e.g., Apache Tomcat).
5. Access the platform through `http://localhost:8080/farmerapp`.
   
## Project Structure

The project structure follows the standard Maven-based project organization:

- `src/`: Contains all the source code files.
  - `java/`: Java classes including configurations, DAOs, servlets, and utility classes.
  - `resources/`: Configuration files (e.g., `db.properties`).
  - `webapp/`: JSP files, CSS, and JS files for the front-end.

- `test/`: Contains unit and integration tests for the application.

FarmerApp/
├── pom.xml                          # Maven project file
├── src/                              # Source code directory
│   ├── main/
│   │   ├── java/                     # Java source files
│   │   │   └── com/
│   │   │       └── farmapp/
│   │   │           ├── config/       # Configuration files
│   │   │           │   └── DatabaseConfig.java
│   │   │           ├── dao/          # Data Access Object classes
│   │   │           │   ├── FarmerDAO.java
│   │   │           │   └── ProductDAO.java
│   │   │           ├── model/        # Model classes (e.g., Farmer, Product)
│   │   │           │   ├── Farmer.java
│   │   │           │   └── Product.java
│   │   │           ├── servlet/      # Servlet classes
│   │   │           │   ├── FarmerRegistrationServlet.java
│   │   │           │   ├── LoginServlet.java
│   │   │           │   └── ProductServlet.java
│   │   │           └── util/         # Utility classes
│   │   │               └── DatabaseUtil.java
│   │   ├── resources/                # Resource files
│   │   │   └── db.properties         # Database connection properties
│   │   └── webapp/                   # Web application files
│   │       ├── WEB-INF/              # Web configuration files
│   │       │   └── web.xml           # Web.xml configuration
│   │       ├── css/                  # CSS files
│   │       │   └── style.css         # Main stylesheet
│   │       ├── js/                   # JavaScript files
│   │       │   └── validation.js     # Validation script
│   │       ├── index.jsp             # Home page
│   │       ├── login.jsp             # Login page
│   │       ├── register.jsp          # Registration page
│   │       ├── product_form.jsp      # Product listing form
│   │       ├── product_list.jsp      # Display products list
│   │       └── dashboard.jsp         # User dashboard
│   └── test/                         # Test source directory
│       └── java/
│           └── com/
│               └── farmmarket/
│                   └── dao/          # DAO test classes
│                       ├── FarmerDAOTest.java
│                       └── ProductDAOTest.java
│                   └── model/        # Model test classes
│                       ├── FarmerTest.java
│                       └── ProductTest.java
│                   └── servlet/      # Servlet test classes
│                       ├── FarmerRegistrationServletTest.java
│                       ├── LoginServletTest.java
│                       └── ProductServletTest.java
│                   └── util/         # Utility test classes
│                       └── DatabaseUtilTest.java
