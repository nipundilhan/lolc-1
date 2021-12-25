package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CustomerCultivationIncome;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface CustomerCultivationIncomeRepository extends JpaRepository<CustomerCultivationIncome, Long> {

	public List<CustomerCultivationIncome> findByCustomerId(Long customerId);
	
	public List<CustomerCultivationIncome> findByCustomerIdAndStatus(Long customerId, CommonStatus status);

	public Optional<CustomerCultivationIncome> findByCustomerIdAndCultivationCategoryIdAndStatusAndIdNotIn(Long customerId,
			Long cutiCatId, CommonStatus status, Long id);

	public Optional<CustomerCultivationIncome> findByCustomerIdAndCultivationCategoryIdAndStatus(Long customerId, Long cutiCatId,
			CommonStatus status);
}
