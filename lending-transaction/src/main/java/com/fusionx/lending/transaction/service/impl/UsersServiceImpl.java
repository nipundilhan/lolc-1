/**
 *
 */
package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.domain.Users;
import com.fusionx.lending.transaction.repo.UsersRepository;
import com.fusionx.lending.transaction.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ranjith Kodikara
 *
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    /**
     *
     * This returns the user object related to the given user name
     * @param String username This is the only parameter to findByUserName method
     * @return Users This returns the user object for given username
     */
    public Users findByUserName(String username) {
        Users u = null;
        try {
            //System.out.println("calling userRepository...");
            if (usersRepository.findByUserName(username).isPresent())
                u = usersRepository.findByUserName(username).get();
            //System.out.println(u.getUserName()+"Password:"+u.getPassword());
            //System.out.println(u.getPassword());
        } catch (NullPointerException ne) {
            System.out.println("No username found in UsersServiceImpl.findByUserName" + ne.getMessage());
        } catch (Exception e) {
            System.out.println("Other error in UsersServiceImpl.findByUserName" + e.getMessage());
        }
        return u;
    }

}
