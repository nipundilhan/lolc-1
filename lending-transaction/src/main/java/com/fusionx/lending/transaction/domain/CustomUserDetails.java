/**
 *
 */
package com.fusionx.lending.transaction.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author RK
 *
 */
public class CustomUserDetails extends Users implements UserDetails {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param users
     */
    public CustomUserDetails(String username, String password, boolean enabled, String email, Set<Role> roles, String active) {
        this.setUserName(username);
        this.setPassword(password);
        this.setActive(active);
        this.setEnabled(enabled);
        setEnabled(enabled);
        this.setEmail(email);
        this.setRoles(roles);

    }

    public CustomUserDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     *
     *
     */
    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return super.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
/*		java.util.List<SimpleGrantedAuthority> list = getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))

				.collect(Collectors.toList());
		return list;*/
        Collection<? extends GrantedAuthority> authList;
        authList = getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole())
        ).collect(Collectors.toList());
        System.out.println("inside getAuthorities:" + authList.toString());
        return authList;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return super.getActive().equalsIgnoreCase("Y") ? true : false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        System.out.println("just before checking whether enabled ...");
        return (super.isEnabled());
    }

}
