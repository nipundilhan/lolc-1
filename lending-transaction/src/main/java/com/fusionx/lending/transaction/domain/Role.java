/**
 *
 */
package com.fusionx.lending.transaction.domain;

import javax.persistence.*;


/**
 * @author RK
 *
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private long roleId;

    @Column(name = "role")
    private String role;

    /**
     *
     */
    public Role() {
        super();
    }

    /**
     * @return the roleId
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     *            the roleId to set
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

}
