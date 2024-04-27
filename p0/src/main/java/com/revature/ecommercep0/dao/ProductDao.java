package com.revature.ecommercep0.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.model.Product;
import com.revature.ecommercep0.utils.ConnectionFactory;

public class ProductDao implements CrudDao<Product> {
    @Override
    public Product save(Product obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO products (id, name, description, price, category, is_Available) VALUES (?,?,?,?,?,?)")) {
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getName());
            ps.setString(3, obj.getDescription());
            ps.setString(4, obj.getPrice());
            ps.setString(5, obj.getCategory());
            ps.setBoolean(6, obj.isAvailable());
            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return obj;
    }

    @Override
    public Product update(Product product) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE products SET name = ?, description = ?, price = ?, category = ? WHERE id = ?")) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getPrice());
            ps.setString(4, product.getCategory());
            ps.setString(5, product.getId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RuntimeException("Failed to update product with ID: " + product.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return findById(product.getId());
    }

    @Override
    public Product delete(String id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM products");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product newProduct = new Product();
                newProduct.setId(rs.getString("id"));
                newProduct.setName(rs.getString("name"));
                newProduct.setDescription(rs.getString("description"));
                newProduct.setPrice(rs.getString("price"));
                newProduct.setCategory(rs.getString("category"));
                newProduct.setAvailable(rs.getBoolean("is_Available"));
                products.add(newProduct);
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return products;
    }

    public Product findByName(String name) {
        Product newProduct = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM products where name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newProduct = new Product();
                newProduct.setId(rs.getString("id"));
                newProduct.setName(rs.getString("name"));
                newProduct.setDescription(rs.getString("description"));
                newProduct.setPrice(rs.getString("price"));
                newProduct.setCategory(rs.getString("category"));
                newProduct.setAvailable(rs.getBoolean("is_Available"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return newProduct;
    }

    public Product updateProductColumnByName(String column, String newColumnValue, String name) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "UPDATE products SET " + column + " = ? where name = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, column);
            ps.setString(2, newColumnValue);
            ps.setString(3, name);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return findByName(name);
    }

    public boolean updateProductAvailabilityByName(String name, boolean newStatus) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE products SET is_Available = ? where name = ?");
            ps.setBoolean(1, newStatus);
            ps.setString(2, name);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
    }

    public boolean updateProductAvailabilityById(String id, boolean newStatus) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE products SET is_Available = ? WHERE id = ?");
            ps.setBoolean(1, newStatus);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
    }

    public Product updateProductColumnById(String columnName, String newColumnValue, Product product) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "UPDATE products SET " + columnName + " = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newColumnValue);
            ps.setString(2, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file: " + e.getMessage());
        }
        return findById(product.getId());
    }

    public Product updateProductDescriptionByName(String name, String newDescription) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE products SET description = ? where name = ?");
            ps.setString(1, newDescription);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return findByName(name);
    }

    @Override
    public Product findById(String product_id) {
        Product newProduct = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM products where id = ?");
            ps.setString(1, product_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newProduct = new Product();
                newProduct.setId(rs.getString("id"));
                newProduct.setName(rs.getString("name"));
                newProduct.setDescription(rs.getString("description"));
                newProduct.setPrice(rs.getString("price"));
                newProduct.setCategory(rs.getString("category"));
                newProduct.setAvailable(rs.getBoolean("is_Available"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return newProduct;
    }
}
