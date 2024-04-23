package com.revature.ecommercep0.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.model.CartHistory;
import com.revature.ecommercep0.utils.ConnectionFactory;


public class CartHistoryDao implements CrudDao<CartHistory> {
    @Override
    public CartHistory save(CartHistory obj) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO cart_product (cart_id, product_id, quantity) VALUES (?,?,?)")) {
            ps.setString(1, obj.getCart_id());
            ps.setString(2, obj.getProduct_id());
            ps.setString(3, obj.getQuantity());
            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return obj;
    }

    @Override
    public CartHistory update(CartHistory obj) {
        return null;

    }

    @Override
    public CartHistory delete(String id) {
        return null;

    }

    @Override
    public List<CartHistory> findAll() {
        return null;

    }

    @Override
    public CartHistory findById(String cart_id) {
        CartHistory newCartHistory = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cart_product where cart_id = ?");
            ps.setString(1, cart_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newCartHistory = new CartHistory(cart_id, rs.getString("product_id"), rs.getString("quantity"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return newCartHistory;
    }
    public List<CartHistory> findCartHistoryById(String cart_id) {
        List<CartHistory> newCartHistory = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cart_product where cart_id = ?");
            ps.setString(1, cart_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartHistory newCartEntry = new CartHistory(cart_id, rs.getString("product_id"), rs.getString("quantity"));
                newCartHistory.add(newCartEntry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return newCartHistory;
    }

}
