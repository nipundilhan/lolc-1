package com.fusionx.lending.origination.service;

/**
 * Master Value Paire Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.MasterValuePaire;
import com.fusionx.lending.origination.resource.MasterValuePaireAddResource;
import com.fusionx.lending.origination.resource.MasterValuePaireUpdateResource;

@Service
public interface MasterValuePaireService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<MasterValuePaire> findAll();

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Master value paire
	 */
	public Optional<MasterValuePaire> findById(Long id);

	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the Master value paire
	 */

	public List<MasterValuePaire> findByStatus(String status);

	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the Master value paire
	 */
	public List<MasterValuePaire> findByName(String name);

	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<MasterValuePaire> findByCode(String code);

	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);

	/**
	 * Save and validate Master value paire.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long saveAndValidateValuePaire(String tenantId, String createdUser,
			MasterValuePaireAddResource masterValuePaireAddResource);

	/**
	 * Update and Master value paire.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
//	public Long updateAndValidateValuePaire(String tenantId, String createdUser, Long id,
//			MasterValuePaireUpdateResource masterValuePaireUpdateResource);

	public MasterValuePaire updateValuePaire(String tenantId, String createdUser,
			MasterValuePaireUpdateResource masterValuePaireUpdateResource, Long id);
}
