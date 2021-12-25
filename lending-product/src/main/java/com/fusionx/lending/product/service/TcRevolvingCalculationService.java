package com.fusionx.lending.product.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.resources.RevolvingLoanCalculatorAddResource;
/**
 * API Service related to Tc Revolving gCalculation
 *
 * @author Dilhan Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        05-10-2021      -               FXL-994     Dilhan Jayasinghe        Created
 * <p>
 */
@Service
public interface TcRevolvingCalculationService {
	
	
	/**
	 * Find by tc header id.
	 *
	 * @param tcHeaderId the tc header id
	 * @return the optional
	 */
	public Optional<TcHeader> findByTcHeaderId(Long tcHeaderId);
	
	/**
	 * Find by lead id.
	 *
	 * @param leadId the lead id
	 * @return the optional
	 */
	public Optional<TcHeader> findByLeadId(Long leadId);

	/**
	 * Save revolving details.
	 *
	 * @param tenantId the tenant id
	 * @param revolvingLoanCalculatorAddResource the revolving loan calculator add resource
	 * @return the tc header
	 */
	TcHeader saveRevolvingDetails(String tenantId, RevolvingLoanCalculatorAddResource revolvingLoanCalculatorAddResource);
}
