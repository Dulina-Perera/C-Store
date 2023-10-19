package com.cstore.configuration;

import com.cstore.dao.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final UserDao userDao;
    private final static String USER_NOT_FOUND_MSG = "User with user id %s not found.";

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userDao
            .findRegUserById(Long.parseLong(username))
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format(USER_NOT_FOUND_MSG, username)
            ));
    }
}
