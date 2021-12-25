package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.Segments;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;

/**
 * Segment Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-2880   	 FX-6515	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface SegmentsService {
	
	/**
	 * @author Senitha
	 * 
	 * Find all Segment
	 * @return -JSON array of all Segments
	 * 
	 * */
	public List<Segments> getAll();
	
	/**
	 * @author Senitha
	 * 
	 * Find Segment by ID
	 * @return -JSON array of Segments
	 * 
	 * */
	public Optional<Segments> getById(Long id);
	
	/**
	 * @author Senitha
	 * 
	 * Find Segment by code
	 * @return -JSON array of Segments
	 * 
	 * */
	public Optional<Segments>getByCode(String code);
	
	/**
	 * @author Senitha
	 * 
	 * Find Segment by name
	 * @return -JSON array of Segments
	 * 
	 * */
	public List<Segments> getByName(String name);
	
	/**
	 * @author Senitha
	 * 
	 * Find Segment status
	 * @return -JSON array of Segments
	 * 
	 * */
	public List<Segments> getByStatus(String status);
	
	/**
	 * @author Senitha
	 * 
	 * Insert Segments
	 * @param  - AddBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	public Segments addSegments(String tenantId, AddBaseRequest addBaseRequest);
	
	/**
	 * @author Senitha
	 * 
	 * Update Segments
	 * @param  - UpdateBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	public Segments updateSegments(String tenantId, UpdateBaseRequest updateBaseRequest) ;

}
