package com.farmapp.servlet;

import com.farmapp.dao.ProductDAO;
import com.farmapp.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        // Initialize ProductDAO
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                // Display all products (Read All)
                List<Product> products = productDAO.findAll();
                request.setAttribute("products", products);
                request.getRequestDispatcher("product_list.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                // Edit an existing product (Read by ID)
                Long id = Long.parseLong(request.getParameter("id"));
                Optional<Product> productOpt = productDAO.findById(id);
                if (productOpt.isPresent()) {
                    request.setAttribute("product", productOpt.get());
                    request.getRequestDispatcher("product_form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } else if (action.equals("delete")) {
                // Delete a product (Delete)
                Long id = Long.parseLong(request.getParameter("id"));
                boolean deleted = productDAO.delete(id);
                if (deleted) {
                    response.sendRedirect("product?action=list");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } else if (action.equals("search")) {
                // Search products by name or description
                String searchTerm = request.getParameter("searchTerm");
                List<Product> products = productDAO.searchProducts(searchTerm);
                request.setAttribute("products", products);
                request.getRequestDispatcher("product_list.jsp").forward(request, response);
            } else {
                response.sendRedirect("product?action=list");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occurred while fetching products.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action.equals("create")) {
                // Create new product (Create)
                Long farmerId = Long.parseLong(request.getParameter("farmerId"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String category = request.getParameter("category");
                BigDecimal quantity = new BigDecimal(request.getParameter("quantity"));
                String unit = request.getParameter("unit");
                BigDecimal price = new BigDecimal(request.getParameter("price"));
                String imageUrl = request.getParameter("imageUrl");

                Product product = new Product(farmerId, name, description, category, quantity, unit, price, imageUrl);
                productDAO.save(product);

                response.sendRedirect("product?action=list");
            } else if (action.equals("update")) {
                // Update existing product (Update)
                Long id = Long.parseLong(request.getParameter("id"));
                Long farmerId = Long.parseLong(request.getParameter("farmerId"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String category = request.getParameter("category");
                BigDecimal quantity = new BigDecimal(request.getParameter("quantity"));
                String unit = request.getParameter("unit");
                BigDecimal price = new BigDecimal(request.getParameter("price"));
                String imageUrl = request.getParameter("imageUrl");

                Product product = new Product(id, farmerId, name, description, category, quantity, unit, price, imageUrl);
                productDAO.update(product);

                response.sendRedirect("product?action=list");
            } else {
                response.sendRedirect("product");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occurred while processing the product.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cleanup resources if needed
    }
}
