package com.revature.ecommercep0.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.model.Role;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.utils.ConnectionFactory;

public class UserDao implements CrudDao<User> {

    @Override
    public User findById(String id) { //doesnt work need to implement it.
        User user = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT * FROM users WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
            }

         
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return findById(id);
    }

    public User makeUserAdmin(String user_id, String admin_role_id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE users SET role_id = ? WHERE id = ?")) {
            ps.setString(1, admin_role_id);
            ps.setString(2, user_id);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RuntimeException("Failed to update product with ID: " + user_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return findById(user_id);

    }

    public List<User> findAllWithRole() {
        List<User> allUsers = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users_with_role");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User newUser = new User();
                Role role = new Role();
                newUser.setId(rs.getString("id"));
                newUser.setEmail(rs.getString("email"));
                newUser.setPassword(rs.getString("password"));
                newUser.setFname(rs.getString("firstname"));
                newUser.setLname(rs.getString("lastname"));
                newUser.setRole_id(rs.getString("role_id"));
                role.setRole_id(rs.getString("role_id"));
                role.setName(rs.getString("role_name"));
                newUser.setRole(role);
                allUsers.add(newUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allUsers;
    }

    @Override
    public User save(User obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO users (id, email, password, firstname, lastname, role_id) VALUES (?,?,?,?,?,?)")) {
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getEmail());
            ps.setString(3, obj.getPassword());
            ps.setString(4, obj.getFname());
            ps.setString(5, obj.getLname());
            ps.setString(6, obj.getRole_id());
            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return obj;
    }

    @Override
    public User update(User obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public User delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User newUser = new User();
                newUser.setId(rs.getString("id"));
                newUser.setEmail(rs.getString("email"));
                newUser.setPassword(rs.getString("password"));
                newUser.setFname(rs.getString("firstname"));
                newUser.setLname(rs.getString("lastname"));
                newUser.setRole_id(rs.getString("role_id"));
                allUsers.add(newUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allUsers;
    }
}
