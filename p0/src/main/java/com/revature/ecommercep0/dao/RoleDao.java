package com.revature.ecommercep0.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.model.Role;
import com.revature.ecommercep0.utils.ConnectionFactory;

public class RoleDao implements CrudDao<Role> {

    @Override
    public Role save(Role obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = conn
        .prepareStatement("INSERT INTO roles(id, name) VALUES (?, ?)")) {
            ps.setString(1, obj.getRole_id());
            ps.setString(2, "USER");
            ps.executeQuery();
            
        } catch(SQLException e) {
            throw new RuntimeException("Cannot connect to the database");
        } catch(IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return obj;
    }

    @Override
    public Role update(Role obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Role delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Role> findAll() {
        List<Role> allRoles = new ArrayList<>();    
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM roles");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role newRole = new Role();
                newRole.setRole_id(rs.getString("id"));
                newRole.setName(rs.getString("name"));
                allRoles.add(newRole);
            }
        } catch(SQLException e) {
            throw new RuntimeException("CAnnot connect to the database");
        } catch(IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }
        return allRoles;
    }

    @Override
    public Role findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    
}
