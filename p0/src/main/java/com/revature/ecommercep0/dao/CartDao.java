package com.revature.ecommercep0.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.model.Cart;
import com.revature.ecommercep0.utils.ConnectionFactory;

public class CartDao implements CrudDao<Cart> {

    @Override
    public Cart save(Cart obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO cart (id, user_id, total_cost, is_CheckedOut) VALUES (?,?,?,?)")) {
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUser_id());
            ps.setString(3, obj.getTotal_cost());
            ps.setBoolean(4, obj.isIs_CheckedOut());
            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return obj;
    }

    public void updateCartPriceById(String id, String newPrice) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE cart SET total_cost = ? where id = ?");
            ps.setString(1, newPrice);
            ps.setString(2, id);
            int result = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        // Cart temp = findById(id);
        // return temp;
    }

    @Override
    public Cart update(Cart obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Cart delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Cart> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public List<Cart> findAllCartsByUserId (String user_id) {
        List<Cart> allCartsOfUser = new ArrayList<>();    
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cart where user_id = ?");
            ps.setString(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart newCart = new Cart(rs.getString("id"), user_id,rs.getString("total_cost"), rs.getBoolean("is_checkedout") );
                allCartsOfUser.add(newCart);
            }
        } catch(SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch(IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allCartsOfUser;
    }

    @Override
    public Cart findById(String cart_id) {
        Cart newCart = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cart where id = ?");
            ps.setString(1, cart_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newCart = new Cart(rs.getString("id"), rs.getString("user_id"), rs.getString("total_cost"), rs.getBoolean("is_CheckedOut"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return newCart;
    }

    public Cart findByUserId(String user_id) {
        Cart newCart = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cart where user_id = ?");
            ps.setString(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newCart = new Cart(rs.getString("id"), user_id, rs.getString("total_cost"),
                        rs.getBoolean("is_CheckedOut"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return newCart;
        // return null;
    }

}
