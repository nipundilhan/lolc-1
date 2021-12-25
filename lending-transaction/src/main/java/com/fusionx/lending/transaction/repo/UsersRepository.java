/**
 *
 */
package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

/**
 * @author RK
 *
 */


public interface UsersRepository extends CrudRepository<Users, String>, QueryByExampleExecutor<Users> {
    Optional<Users> findByUserName(String userName);

    Optional<Users> findByUserNameIgnoreCaseOrPassword(String userName, String password);
}