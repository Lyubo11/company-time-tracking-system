package com.company.timecompany.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.company.timecompany.entities.Role;
import com.company.timecompany.entities.User;
import com.company.timecompany.exceptions.UserNotFoundException;
import com.company.timecompany.repositories.RoleRepository;
import com.company.timecompany.services.UserService;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    BindingResult bindingResult;
    @InjectMocks
    private UserController userController;
    @Mock
    private RedirectAttributes redirectAttributes;
    @Mock
    private UserService userService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private Model model;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    @DisplayName("Should get list of all userss")
    void testShouldGetListOfAllUsers() {
        List<User> listUsers = new ArrayList<>();

        when(userService.listAllUsers()).thenReturn(listUsers);

        String result = userController.listAll(model);

        assertEquals("user/users", result);
    }

    @Test
    @DisplayName("Should create a new user")
    void testShouldCreateNewUser() {
        User testUser = new User();
        testUser.setEnabled(false);
        List<Role> listRoles = new ArrayList<>();

        when(roleRepository.findAll()).thenReturn(listRoles);

        String result = userController.newUser(testUser, model);

        assertTrue(testUser.isEnabled());
        assertEquals("/user/user-form", result);
    }

    @Test
    @DisplayName("Should not submit save user due to error")
    void testShouldNotSaveNewUserHasToReturnUserForm() {
        User testUser = new User();
        List<Role> listRoles = new ArrayList<>();

        when(bindingResult.hasErrors()).thenReturn(true);
        when(roleRepository.findAll()).thenReturn(listRoles);

        ModelAndView result = userController.saveUser(testUser, model, bindingResult, redirectAttributes);

        assertEquals("user/user-form", result.getViewName());
    }

    @Test
    @DisplayName("Should NOt submit saveUser, due to user already exists")
    void testShouldNotSaveNewUser() {
        User testUser = User.builder().id(1).username("user1").build();
        Date date = mock(Date.class);

        testUser.setCreatedAt(date);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(!userService.isUsernameUnique(testUser.getUsername(), testUser.getId())).thenReturn(false);

        ModelAndView result = userController.saveUser(testUser, model, bindingResult, redirectAttributes);

        assertEquals("user/user-form", result.getViewName());
    }

    @Test
    @DisplayName("Should submit saveUser")
    void testShouldSaveNewUser() {
        User testUser = User.builder().id(1).username("user1").build();
        Date date = mock(Date.class);

        testUser.setCreatedAt(date);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(!userService.isUsernameUnique(testUser.getUsername(), testUser.getId())).thenReturn(true);

        ModelAndView result = userController.saveUser(testUser, model, bindingResult, redirectAttributes);

        assertEquals("redirect:/users", result.getViewName());
    }

    @Test
    @DisplayName("Should edit user")
    void testShouldEditUser() throws UserNotFoundException {
        int id = 1;
        User testUser = User.builder().id(id).build();
        List<Role> listRoles = new ArrayList<>();

        when(userService.getUserId(id)).thenReturn(testUser);
        when(roleRepository.findAll()).thenReturn(listRoles);

        String result = userController.editUser(id, redirectAttributes, model);

        assertEquals("user/user-form", result);
    }

    @Test
    void testEditUserException() throws UserNotFoundException {

        when(userService.getUserId(1)).thenThrow(new UserNotFoundException("User not found"));
        String viewName = userController.editUser(1, redirectAttributes, model);

        verify(redirectAttributes).addFlashAttribute(Mockito.eq("message"), stringArgumentCaptor.capture());
        assertEquals("redirect:/user/users", viewName);
        assertEquals("User not found", stringArgumentCaptor.getValue());
    }

    @Test
    void testShouldDeleteUser() {
        int id = 1;
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        String result = userController.deleteUser(id, redirectAttributes);

        assertEquals("redirect:/users", result);
    }

    @Test
    void testShouldThrowExceptionIfDeleteUser() throws UserNotFoundException {
        doThrow(new UserNotFoundException("User not found")).when(userService).deleteUser(1);
        String viewName = userController.deleteUser(1, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute(Mockito.eq("message"), stringArgumentCaptor.capture());
        assertEquals("User not found", stringArgumentCaptor.getValue());

    }

    @Test
    void testUpdateUserEnabledStatus() {
        String viewName = userController.updateUserEnabledStatus(1, true, redirectAttributes);

        verify(userService).updateUserEnabledStatus(1, true);
        verify(redirectAttributes).addFlashAttribute(Mockito.eq("message"), stringArgumentCaptor.capture());

        assertEquals("The User ID 1 has been enabled", stringArgumentCaptor.getValue());
        assertEquals("redirect:/users", viewName);
    }
}