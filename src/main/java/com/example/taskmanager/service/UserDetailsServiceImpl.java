package com.example.taskmanager.service;

import com.example.taskmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.taskmanager.model.User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserBuilder builder = User.withUsername(user.getUsername());
        builder.password(user.getPassword());
        builder.roles("USER");

        return builder.build();
    }
}
