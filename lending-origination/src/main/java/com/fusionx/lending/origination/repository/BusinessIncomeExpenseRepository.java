package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessIncomeExpense;
import com.fusionx.lending.origination.enums.CommonStatus;
@Repository
public interface BusinessIncomeExpenseRepository extends JpaRepository<BusinessIncomeExpense, Long> {

	public List<BusinessIncomeExpense> findByCustomerId(Long customerId);
	
	public List<BusinessIncomeExpense> findByGuarantorId(Long guarantorId);
	
	public List<BusinessIncomeExpense> findByCustomerIdAndStatus(Long customerId, CommonStatus status);
	
	public List<BusinessIncomeExpense> findByGuarantorIdAndStatus(Long guarantorId, CommonStatus status);

	public Optional<BusinessIncomeExpense> findByCustomerIdAndBusinessTypesIdAndStatus(Long customerId, Long businessTypeId, CommonStatus status);

	public Optional<BusinessIncomeExpense> findByCustomerIdAndBusinessTypesIdAndStatusAndIdNotIn(Long customerId, Long businessTypeId,
			CommonStatus status, Long id);
	
	public List<BusinessIncomeExpense> findByGuarantorIdAndBusinessTypesId(Long guarantorId, Long businessTypeId);
	
	public List<BusinessIncomeExpense> findByGuarantorIdAndBusinessTypesIdAndIdNotIn(Long guarantorId, Long businessTypeId, Long id);
	
	List<BusinessIncomeExpense> findByStatus(String status);
}
