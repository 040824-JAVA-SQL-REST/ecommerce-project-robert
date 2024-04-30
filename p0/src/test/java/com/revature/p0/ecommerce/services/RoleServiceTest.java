package com.revature.p0.ecommerce.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.revature.ecommercep0.dao.RoleDao;
import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.model.Role;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.UserService;

import io.javalin.security.Roles;

public class RoleServiceTest {
    private RoleService roleService;
    private RoleDao roleDao;

    @Before
    public void setup() {
        roleDao = Mockito.mock(RoleDao.class);
        roleService = new RoleService(roleDao);
    }

    @Test
    public void getRoleIdByNameShouldReturnIdIfNameIsValid() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("1", "ADMIN"));
        roles.add(new Role("2", "User"));
        when(roleDao.findAll()).thenReturn(roles);

        String roleId = roleService.getRoleIdByName("ADMIN");
        assertEquals("1", roleId);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetRoleIdByNameWithInvalidName() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("1", "Admin"));
        roles.add(new Role("2", "User"));
        when(roleDao.findAll()).thenReturn(roles);

        assertNull(roleService.getRoleIdByName("Moderator"));
    }
    @Test
    public void testSaveRole() {
        Role role = new Role();
        role.setRole_id("1");
        role.setName("TestRole");

        when(roleDao.save(any(Role.class))).thenReturn(role);

        Role savedRole = roleService.save(new Role());

        verify(roleDao, times(1)).save(any(Role.class));

        assertEquals("1", savedRole.getRole_id());
        assertEquals("TestRole", savedRole.getName());
    }
}
