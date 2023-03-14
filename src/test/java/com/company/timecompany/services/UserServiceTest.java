package com.company.timecompany.services;

import com.company.timecompany.entities.Role;
import com.company.timecompany.entities.User;
import com.company.timecompany.exceptions.UserNotFoundException;
import com.company.timecompany.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;


    @Test
    void testListAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("user", "password,", "ivan", "ivanov"));
        users.add(new User("user1", "password,", "ivan1", "ivanov1"));

        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.listAllUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(users, result);
    }

    @Test
    public void testIsUsernameUnique() {

        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.getUserByUsername(username)).thenReturn(user);
        boolean result = userService.isUsernameUnique(username,user.getId());
        assertFalse(result);
    }

    @Test
    public void testGetAllEmployeesLIst() {

        Role employeeRole = mock(Role.class);
        Mockito.when(employeeRole.getName()).thenReturn("Employee");
        User user1 = mock(User.class);
        Mockito.when(user1.getRoles()).thenReturn(Collections.singleton(employeeRole));
        User user2 = mock(User.class);
        Mockito.when(user2.getRoles()).thenReturn(Collections.emptySet());
        User user3 = mock(User.class);
        Mockito.when(user3.getRoles()).thenReturn(Collections.singleton(employeeRole));

        List<User> allUsers = Arrays.asList(user1, user2, user3);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        List<User> employees = userService.listAllEmployees();

        assertEquals(2, employees.size());
        assertTrue(employees.contains(user1));
        assertTrue(employees.contains(user3));
    }

    @Test
    public void testSaveUser() {

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("");

        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("testuser");
        existingUser.setPassword("password");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User savedUser = i.getArgument(0);
            savedUser.setPassword("password");
            return savedUser;
        });

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals(existingUser.getPassword(), savedUser.getPassword());
        Mockito.verify(userRepository).save(user);
        Mockito.verify(userRepository).findById(1);
    }

    @Test
    public void testSetPasswordEncoder() {
        User user = new User();
        user.setPassword("testPassword");

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode("testPassword")).thenReturn(encodedPassword);
        userService.setPasswordEncoder(user);

        verify(passwordEncoder, times(1)).encode("testPassword");
        assertEquals(encodedPassword, user.getPassword());
    }

    @Test
    public void testGetUserId() throws UserNotFoundException {
        User user = new User();
        user.setId(1);
        Optional<User> optionalUser = Optional.of(user);

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        User result = userService.getUserId(1);
        assertEquals(user, result);
    }

    @Test
    void testGetUserIdThrowsUserNotFoundException() {
        Integer id = 123;
        when(userRepository.findById(id)).thenThrow(new NoSuchElementException());

        assertThrows(UserNotFoundException.class, () -> userService.getUserId(id));
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {
        User user = new User();
        user.setId(1);
        user.setUsername("John");
        user.setPassword("password");

        when(userRepository.countById(1)).thenReturn(1);
        userService.deleteUser(1);
        verify(userRepository).deleteById(1);
    }

    @Test
    void testDeleteUserWithInvalidId() {
        Integer id = 1;
        when(userRepository.countById(id)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
        verify(userRepository, times(1)).countById(id);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testDeleteUserNotFoundException() {
        Integer id = 1;
        Long nullCount = null;
        doReturn(nullCount).when(userRepository).countById(id);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
        verify(userRepository, times(1)).countById(id);
        verify(userRepository, never()).deleteById(id);
    }

    @Test
    public void testUpdateUserEnabledStatus() {
        Integer userId = 1;
        boolean enabled = true;
        userService.updateUserEnabledStatus(userId, enabled);

        verify(userRepository, times(1)).updateEnabledStatus(userId, enabled);
    }
}