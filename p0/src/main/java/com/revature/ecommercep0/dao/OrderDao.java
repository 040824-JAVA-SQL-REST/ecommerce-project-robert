package com.revature.ecommercep0.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.model.Order;
import com.revature.ecommercep0.model.Product;
import com.revature.ecommercep0.utils.ConnectionFactory;

public class OrderDao implements CrudDao<Order> {

    @Override
    public Order save(Order obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO orders (id, total_price, order_status, user_id) VALUES (?,?,?,?)")) {
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getTotalPrice());
            ps.setString(3, obj.getStatus());
            ps.setString(4, obj.getUser_id());
          //  ps.setString(5, obj.getUser_id());
            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return obj;

    }

    public Order update(Order obj) {
        return null;

    }

    public Order delete(String id) {
        return null;

    }

    public List<Order> findAll() {
        List<Order> allOrders = new ArrayList<>();    
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order newOrder = new Order();
                newOrder.setId(rs.getString("id"));
                newOrder.setTotalPrice(rs.getString("total_price"));
                newOrder.setStatus(rs.getString("order_status"));
                newOrder.setOrderDate(rs.getDate("order_date"));
                newOrder.setUser_id(rs.getString("user_id"));
                allOrders.add(newOrder);
            }
        } catch(SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch(IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allOrders;

    }

    public Order findById(String id) {
        return null;

    }

}
