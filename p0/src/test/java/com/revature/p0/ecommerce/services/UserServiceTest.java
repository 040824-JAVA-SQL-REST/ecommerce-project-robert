package com.revature.p0.ecommerce.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockedStatic;
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

    @Test
    public void loginShouldReturnNullIfCredentialsAreNotValid() {
        String email = "robert@revature.net";
        String password = "password999";

        List<User> mockedUsers = List.of(new User("robert@revature.net", "rob", "j", "password123"), new User("edeard@yahoo.net", "23", "#4", "3dfdfgdfgdfg4"));
        Mockito.when(userDao.findAllWithRole()).thenReturn(mockedUsers);

        try (MockedStatic<BCrypt> mockedBcrypt = Mockito.mockStatic(BCrypt.class)) {
            mockedBcrypt.when(() -> BCrypt.checkpw(email, mockedUsers.get(0).getPassword()))
            .thenReturn(true);

             Optional<User> expected = userService.login(email, password);
             assertTrue(expected.isEmpty());
        }
       /// boolean expectedResult = (expected.isEmpty()) ? false : true;
    }
    @Test
    public void loginShouldReturnUserIfCredentialsAreValid() {
        String email = "test@example.com";
        String password = "password";
        User user = new User( email, "John ", "John Doe", BCrypt.hashpw(password, BCrypt.gensalt()));

        when(userDao.findAllWithRole()).thenReturn(Arrays.asList(user));

        Optional<User> result = userService.login(email, password);

        assertEquals(Optional.of(user), result);
    }
    @Test
    public void isAdminShouldReturnFalseIfNotAdmin() {
        User test = new User("robert@revature.net", "rob", "j", "password123");

        boolean actual = userService.isAdmin(test);

        assertFalse(actual);
    }
    @Test
    public void isAdminShouldReturnTrueIfAdmin() {
        User test = new User("ADMIN");

        boolean actual = userService.isAdmin(test);

        assertTrue(actual);
    }

    @Test
    public void findAllReturnsListIfNotEmpty() {
        List<User> mockedUsers = List.of(new User("robert@revature.net", "rob", "j", "password123"), new User("edeard@yahoo.net", "23", "#4", "3dfdfgdfgdfg4"));
        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);

        assertEquals(mockedUsers, userService.findAll());


    }
    @Test
    public void saveShouldReturnUserIfSaveIsSuccesful() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFname("John");
        user.setLname("Doe");

        when(roleService.getRoleIdByName("USER")).thenReturn("1");

        when(userDao.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals("1", savedUser.getRole_id());
        assertTrue(BCrypt.checkpw("password", savedUser.getPassword()));
    }

}
