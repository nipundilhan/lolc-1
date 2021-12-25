/**
 *
 */
package com.fusionx.lending.transaction.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author RK
 *
 */
@Entity
@Table(name = "user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "active")
    private String active;

    @NotNull
    private boolean credentialsexpired = false;
    @Column(name = "expired")
    private boolean expired = false;
    @NotNull
    private boolean enabled = true;
    @NotNull
    private boolean locked = false;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;

    /**
     *
     */
    public Users() {
        super();
    }

    public Users(Users users) {
        this.active = users.getActive();
        this.email = users.getEmail();
        this.id = users.getId();
        this.lastName = users.getLastName();
        this.userName = users.getUserName();
        this.password = users.getPassword();
        this.roles = users.getRoles();
        this.enabled = users.isEnabled();
        this.expired = users.isExpired();
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * @return the credentialsexpired
     */
    public boolean isCredentialsexpired() {
        return credentialsexpired;
    }

    /**
     * @param credentialsexpired the credentialsexpired to set
     */
    public void setCredentialsexpired(boolean credentialsexpired) {
        this.credentialsexpired = credentialsexpired;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * @return the roles
     */
    public Set<Role> getRoles() {
        // try {
        // System.out.println("getRoles:"+roles.toString()); } catch(Exception e)
        // {e.printStackTrace();}
        return this.roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isExpired() {
        return this.expired;
    }

    /**
     * @param expired the expired to set
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

}
