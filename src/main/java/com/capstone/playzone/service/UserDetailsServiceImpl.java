package com.capstone.playzone.service;

import com.capstone.playzone.model.User;
import com.capstone.playzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userRepository.findByUsername(username);
        if (appUser == null) throw new UsernameNotFoundException("User not found");

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(appUser.getPassword());
        builder.roles(appUser.getRole());
        return builder.build();
    }
}
