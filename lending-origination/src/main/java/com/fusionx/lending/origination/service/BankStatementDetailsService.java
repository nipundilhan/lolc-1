package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BankStatementDetails;
import com.fusionx.lending.origination.resource.BankStatementDetailsAddResource;
import com.fusionx.lending.origination.resource.BankStatementDetailsUpdateResource;

@Service
public interface BankStatementDetailsService {

	/**
	 * Find all.
	 *
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<BankStatementDetails> findAll(Pageable pageable);

	/**
	 * Find by id.
	 *
	 * @param id       - the id
	 * @param tenantId
	 * @return the BusinessType
	 */
	public Optional<BankStatementDetails> findById(Long id, String tenantId);

	/**
	 * Find by status.
	 *
	 * @param status   - the status
	 * @param tenantId
	 * @return the ApprovalCategory
	 */

	public List<BankStatementDetails> findByStatus(String status, String tenantId);

	/**
	 * 
	 * Insert BankStatementDetails
	 * 
	 * @param - BankStatementDetailsAddResource
	 * @return - Successfully saved
	 * 
	 */
	public BankStatementDetails addBankStatementDetails(String tenantId, String createdUser,
			BankStatementDetailsAddResource bankStatementDetailsAddResource);

	/**
	 * 
	 * Update BankStatementDetails
	 * 
	 * @param - BankStatementDetailsUpdateResource
	 * @return - Successfully updated
	 * 
	 */
	public BankStatementDetails updateBankStatementDetails(String tenantId, Long id, String createdUser,
			BankStatementDetailsUpdateResource bankStatementDetailsUpdateResource);

	public List<BankStatementDetails> getByCustometId(Long customerId);

	public List<BankStatementDetails> findAll();

}
