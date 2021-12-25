package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.UserDaLevelMap;
import com.fusionx.lending.origination.resource.UserDaLevelMapAddResource;
import com.fusionx.lending.origination.resource.UserDaLevelMapUpdateResource;

/**
 * User DA Level map Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *     1   06-05-2021      		     FX-6269	Amal       Created
 *    
 ********************************************************************************************************
 */

@Service
public interface UserDaLevelMapService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	/*public Page<?>  findAll(Pageable pageable, Predicate predicate);*/
	public  List<UserDaLevelMap>  findAll();

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the UserDaLevelMap
	 */
	public Optional<UserDaLevelMap> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the UserDaLevelMap List
	 */
	
	public List<UserDaLevelMap> findByStatus(String status);
	
	/**
	 * Find by group id and level.
	 *
	 * @param status - the status
	 * @return the UserDaLevelMap List
	 */
	
	public List<UserDaLevelMap> findByDelegationAuthorityGroupIdAndDaLevel(Long groupid,String level);

	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
	
	/**
	 * Save and validate User DA Level Map
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param UserDaLevelMapAddResource - the DA Limit user map add resource
	 * @return the id
	 */
	public Long saveAndValidateUserDaLevelMap(String tenantId, String createdUser, UserDaLevelMapAddResource userDaLevelMapAddResource);
	
	/**
	 * Update and validate DA Limit user map
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param UserDaLevelMapResource - the DA Limit user map update resource
	 * @return the id
	 */
	public Long updateAndValidateUserDaLevelMap(String tenantId, String createdUser, Long id, UserDaLevelMapUpdateResource userDaLevelMapResource);

	
}
