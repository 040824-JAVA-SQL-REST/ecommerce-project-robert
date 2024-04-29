package com.revature.p0.ecommerce.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
    private UserDao userDao;

    @Before
    public void setup() {
        roleService = Mockito.mock(RoleService.class);
        userDao = Mockito.mock(UserDao.class);
        userService = new UserService(userDao, roleService);
    }
    @Test
    public void isValidEmailShouldReturnTrueIfEmailisValid() {
        // AAA , Arrange, Act, Assert

        // Arrange
        String validEmail = "robert@yahoo.net";
        boolean expected = true;

        // Actual
        boolean actual = userService.isValidEmail(validEmail);

        // Assert
        // assertEquals(expected, actual);

        // In this case we dont need expected because we know it needs to be true so
        // just use AssertTRue()
        assertTrue(actual);
    }

    @Test
    public void isValidEmailShouldReturnFalseIfEmailisBlank() {
        String validEmail = "";

        boolean actual = userService.isValidEmail(validEmail);

         assertFalse(actual);
    }
    @Test
    public void isUniqueEmailShouldReturnTrueIfEmailIsUnique() {

        String uniqueUsername = "robert@yahoo.net";

        List<User> mockedUsers = List.of(new User("lol@yahoo.net", "1", "2", "3"), new User("23", "23", "#4", "34"));

        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);

        boolean actual = userService.isUniqueEmail(uniqueUsername);

        assertTrue(actual);
    }
    @Test
    public void isUniqueEmailShouldReturnFalseIfEmailIsNotUnique() {
        String notUniqueEmail = "robert@yahoo.net";


        List<User> mockedUsers = List.of(new User("robert@yahoo.net", "1", "2", "3"), new User("23", "23", "#4", "34"));
        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);

        boolean actual = userService.isUniqueEmail(notUniqueEmail);

        assertFalse(actual);

    }
    @Test
    public void isValidPasswordShouldReturnTrueIfPasswordIsValid() {
        String validPassword = "password123";

        boolean actual = userService.isValidPassword(validPassword);

        assertTrue(actual);
    }

    @Test
    public void isValidPasswordShouldReturnFalseIfPasswordIsNotValid() {
        String notValidPassword = "";

        boolean actual = userService.isValidPassword(notValidPassword);

        assertFalse(actual);
    }
}
