package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.resource.FinancialStatementAddResource;
import com.fusionx.lending.origination.resource.FinancialStatementUpdateResource; 

@Service
public interface FinancialStatementTemplateService {

	/**
	 * 
	 * Find all FinancialStatement
	 * 
	 * @author Dilki
	 * @return -JSON array of all FinancialStatement
	 * 
	 */
	public List<FinancialStatementTemplate> getAll();

	/**
	 * 
	 * Find FinancialStatement by ID
	 * 
	 * @author Dilki
	 * @return -JSON array of FinancialStatement
	 * 
	 */
	public Optional<FinancialStatementTemplate> getById(Long id);

	/**
	 * 
	 * Find FinancialStatement by code
	 * 
	 * @author Dilki
	 * @return -JSON array of FinancialStatement
	 * 
	 */
	public Optional<FinancialStatementTemplate> getByCode(String code);

	/**
	 * 
	 * Find FinancialStatement by name
	 * 
	 * @author Dilki
	 * @return -JSON array of FinancialStatement
	 * 
	 */
	public Optional<FinancialStatementTemplate> getByName(String name);

	/**
	 * 
	 * Find FinancialStatement by status
	 * 
	 * @author Dilki
	 * @return -JSON array of FinancialStatement
	 * 
	 */
	public List<FinancialStatementTemplate> getByStatus(String status);

	/**
	 * 
	 * Insert FinancialStatement
	 * 
	 * @author Dilki
	 * @param - FinancialStatementAddResource
	 * @return - Successfully saved
	 * 
	 */
	public FinancialStatementTemplate addFinancialStatement(String tenantId, FinancialStatementAddResource FinancialStatementAddResource);

	/**
	 * 
	 * Update FinancialStatement
	 * 
	 * @author Dilki
	 * @param - FinancialStatementUpdateResource
	 * @return - Successfully updated
	 * 
	 */
	public FinancialStatementTemplate updateFinancialStatement(String tenantId, Long id,
			FinancialStatementUpdateResource FinancialStatementUpdateResource);
}
