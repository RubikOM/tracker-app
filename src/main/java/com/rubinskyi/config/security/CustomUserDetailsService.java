package com.rubinskyi.config.security;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rubinskyi.entity.User;
import com.rubinskyi.service.UserService;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private UserService userService;

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findByLogin(login);
        UserBuilder builder = null;
        if (user == null) {
            LOGGER.error(login + " user not found");
            throw new UsernameNotFoundException(login + " username not found");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        builder = org.springframework.security.core.userdetails.User.withUsername(user.getLogin());
        builder.disabled(false);
        builder.password(user.getPassword());
        Set<GrantedAuthority> authorities = Collections.singleton(authority);

        builder.authorities(authorities);
        return builder.build();
    }
}