package com.company.timecompany.services.authorization;

import com.company.timecompany.configs.MyUserDetails;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);
        if(user!=null){
            return new MyUserDetails(user);
        }

        throw new UsernameNotFoundException("Could not find user with username: " + username);
    }
}
