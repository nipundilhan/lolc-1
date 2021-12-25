package com.fusionx.lending.transaction.config;

/**
 * @author Ranjith Kodikara
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class FXAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private AuthUsersDetailsService uds;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        try {

            System.out.println("at additionalAuthenticationChecks...");

            if (token.getCredentials() == null || userDetails.getPassword() == null) {
                System.out.println("Empty username password.");
                throw new BadCredentialsException("Empty username password.");
            }

            if (!(token.getCredentials() == userDetails.getPassword())) {
                System.out.println("Wrong username password combination token.getCredentials()|"
                        + token.getCredentials().toString() + "| password in db:|" + userDetails.getPassword() + "|");
                throw new BadCredentialsException("Wrong username password combination");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getLocalizedMessage());
        }

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        System.out.println("at retrieveUser...");
        UserDetails ud = uds.loadUserByUsername(username);

        return ud;
    }

}
