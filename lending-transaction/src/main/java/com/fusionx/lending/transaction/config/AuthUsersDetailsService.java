package com.fusionx.lending.transaction.config;

import com.fusionx.lending.transaction.domain.Role;
import com.fusionx.lending.transaction.domain.Users;
import com.fusionx.lending.transaction.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ranjith Kodikara
 */
@Service
public class AuthUsersDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The AccountService business service.
     */
    @Autowired
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersService.findByUserName(username);
        if (user == null) {
            logger.info("Username not found");
            throw new UsernameNotFoundException("User " + username + " not found.");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            logger.info("No roles granted");
            throw new UsernameNotFoundException("User not have the applicable roles.");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            logger.info("user.getRoles(): {}", user.getRoles().size());
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        User userDetails = new User(user.getUserName(), user.getPassword(), user.isEnabled(), !user.isExpired(),
                !user.isCredentialsexpired(), !user.isLocked(), grantedAuthorities);
        logger.info(
                "User Details password: {}, userName : {}, granted auth : {}, isNonExpired : {}, isNonLocked : {}, credentialsNonExpired : {}, enabled : {}",
                new String[]{userDetails.getPassword().toString(), userDetails.getUsername().toString(),
                        Integer.toString(grantedAuthorities.size()),
                        Boolean.toString(userDetails.isAccountNonExpired()),
                        Boolean.toString(userDetails.isAccountNonLocked()),
                        Boolean.toString(userDetails.isCredentialsNonExpired()),
                        Boolean.toString(userDetails.isEnabled())});
        return userDetails;
    }

}
