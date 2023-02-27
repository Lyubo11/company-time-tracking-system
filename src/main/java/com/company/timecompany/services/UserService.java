package com.company.timecompany.services;

import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listAllUsers() {
        return (List<User>) userRepository.findAll();
    }

}
