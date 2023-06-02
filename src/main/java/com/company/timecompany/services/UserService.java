package com.company.timecompany.services;

import com.company.timecompany.entities.Role;
import com.company.timecompany.entities.User;
import com.company.timecompany.exceptions.UserNotFoundException;
import com.company.timecompany.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public List<User> listAllEmployees() {
        List<User> employees = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            for (Role role : user.getRoles()) {
                if (role.getName().equals("Employee")) {
                    employees.add(user);
                }
            }
        }
        return employees;
    }

    public boolean isUsernameUnique(String username, Integer id) {
        User userByUsername = userRepository.getUserByUsername(username);

        if (userByUsername == null) {
            return true;
        }

        return userByUsername.getId().equals(id);
    }


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.getUserByUsername(username);
    }

    public User save(User user) {
        boolean isUpdatingUser = (user.getId() != null);

        if (isUpdatingUser) {
            Optional<User> existingUser = userRepository.findById(user.getId());
            if (user.getPassword().isEmpty() && existingUser.isPresent()) {
                user.setPassword(existingUser.get().getPassword());
            }
        }
        setPasswordEncoder(user);
        return userRepository.save(user);
    }

    void setPasswordEncoder(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public User getUserId(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Could not find any user with ID: " + id));
    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        Integer countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepository.updateEnabledStatus(id, enabled);
    }
}
