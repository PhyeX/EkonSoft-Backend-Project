package io.project.ekonsoft.Models;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if ("EkonSoft_Admin_8mXPDXWT".equals(username))
        {
            return new User("EkonSoft_Admin_8mXPDXWT", "$2a$10$etQ9eFRwQdJdGOFHTEg6Cew6I6aPjv9ja5n.Df7UbbiiO8CGLnToO",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }

}