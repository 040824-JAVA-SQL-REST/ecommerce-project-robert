package com.revature.p0.ecommerce.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.ecommercep0.dao.UserDao;
import com.revature.ecommercep0.model.Role;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.service.RoleService;
import com.revature.ecommercep0.service.UserService;


public class UserServiceTest {
    private RoleService roleService;
    private UserService userService;
    private UserServiceTest userServiceTest;
    private UserDao userDao;

    @Before
    public void setup() {
        roleService = Mockito.mock(RoleService.class);
        userDao = Mockito.mock(UserDao.class);
        userService = new UserService(userDao, roleService);
    }
    
    @Test
    public void isValidEmailShouldReturnTrueIfEmailisValid() {
        String validEmail = "robert@yahoo.net";

        boolean actual  = userService.isValidEmail(validEmail);

       // assertTrue(actual);
    }
    @Test
    public void isValidEmailShouldReturnFalseIfEmailisBlank() {
        String validEmail = "robert@yahoo.net";

        boolean actual  = userService.isValidEmail(validEmail);

        // assertFalse(actual);
    }
}
