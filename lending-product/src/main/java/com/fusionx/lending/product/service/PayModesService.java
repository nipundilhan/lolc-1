package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.PayModes;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;

@Service
public interface PayModesService {
	/**
	 * 
	 * Find all PayModes
	 * @author DilhanJ
	 * @return -JSON array of all pay modes
	 * 
	 * */
	public List<PayModes> getAll();
	

	/**
	 * 
	 * Find PayModes by ID
	 * @author DilhanJ
	 * @return -JSON array of pay mode
	 * 
	 * */
	public Optional<PayModes> findById(Long id);
	
	/**
	 * 
	 * Find PayModes by code
	 * @author DilhanJ
	 * @return -JSON array of pay mode
	 * 
	 * */
	public Optional<PayModes> getByCode(String code);

	
	/**
	 * 
	 * Find PayModes by name
	 * @author DilhanJ
	 * @return -JSON array of pay modes
	 * 
	 * */ 
	public List<PayModes> getByName(String name);
	
	/**
	 * 
	 * Find PayModes by status
	 * @author DilhanJ
	 * @return -JSON array of pay modes
	 * 
	 * */
	public List<PayModes> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert PayModes
	 * @author DilhanJ
	 * @param  - CommonAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public PayModes addPayModes(String tenantId, CommonAddResource commonAddResource);
	

	/**
	 * 
	 * Update PayModes
	 * @author DilhanJ
	 * @param  - CommonUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public PayModes updatePayModes(String tenantId, Long id, CommonUpdateResource commonUpdateResource);
}
