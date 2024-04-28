package com.revature.ecommercep0.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.dto.response.AdminOrderItemResponse;
import com.revature.ecommercep0.dto.response.UserOrderItemResponse;
import com.revature.ecommercep0.model.OrderHistory;
import com.revature.ecommercep0.model.Product;
import com.revature.ecommercep0.utils.ConnectionFactory;

public class OrderHistoryDao implements CrudDao<OrderHistory> {

    @Override
    public OrderHistory save(OrderHistory obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO order_product (order_id, product_id, quantity) VALUES (?,?,?)")) {
            ps.setString(1, obj.getOrderId());
            ps.setString(2, obj.getProductId());
            ps.setString(3, obj.getQuantity());
            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "CAnnot connect to the database, or item is already in cart please update by quantity.");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return obj;
    }

    @Override
    public OrderHistory update(OrderHistory obj) {
        return obj;
    }

    @Override
    public OrderHistory delete(String id) {
        return null;
    }

    @Override
    public List<OrderHistory> findAll() {
        List<OrderHistory> allHistories = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM view_order_history");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderHistory newProductEntry = new OrderHistory(rs.getString("cart_id"), rs.getString("order_id"),
                        rs.getString("quantity"));
                allHistories.add(newProductEntry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allHistories;
    }
    
 
    public List<OrderHistory> findAllOrdersById(String order_id) {
        List<OrderHistory> allProductsByOrders = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM view_order_history where order_id = ?");
            ps.setString(1, order_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderHistory newProductEntry = new OrderHistory(rs.getString("cart_id"), rs.getString("order_id"),
                        rs.getString("quantity"));
                allProductsByOrders.add(newProductEntry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allProductsByOrders;
    }
    public List<UserOrderItemResponse> findAllOrdersByUser(String user_id) {
        List<UserOrderItemResponse> allProductsByOrders = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM view_order_history where user_id = ?");
            ps.setString(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserOrderItemResponse newProductEntry = new UserOrderItemResponse(rs.getString("order_id"), rs.getString("product_name"),
                        rs.getString("product_quantity"), rs.getString("product_price"), rs.getDate("order_date"), rs.getString("order_status"));
                allProductsByOrders.add(newProductEntry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allProductsByOrders;
    }
    public List<String> getAllProductsIdsByOrder (String order_id) {
        List<String> allProductIdsByOrder = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM order_product where order_id = ?");
            ps.setString(1, order_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String product_id = rs.getString("product_id");
                allProductIdsByOrder.add(product_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allProductIdsByOrder;
    }
    public List<AdminOrderItemResponse> findAllOrdersForAdmin() {
        List<AdminOrderItemResponse> allOrders = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT * FROM view_order_history_for_admin");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                AdminOrderItemResponse newOrderEntry = new AdminOrderItemResponse(rs.getString("order_id"), name, rs.getString("total_price"),
                        rs.getString("order_status"));
                allOrders.add(newOrderEntry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allOrders;
    }

    @Override
    public OrderHistory findById(String id) {
        return null;
    }

}
