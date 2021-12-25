/**
 *
 */
package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.domain.Users;

/**
 * @author Ranjith Kodikara
 *
 */
public interface UsersService {
    Users findByUserName(String username);
}
