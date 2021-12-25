package com.fusionx.lending.origination.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.HouseHoldExpenseCategory;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface HouseHoldExpenseCategoryRepository extends JpaRepository<HouseHoldExpenseCategory, Long> {

	public Optional<HouseHoldExpenseCategory> findByIdAndNameAndStatus(Long id, String name, String status);
	
	public List<HouseHoldExpenseCategory> findByStatus(String status);

	public List<HouseHoldExpenseCategory> findByNameContaining(String name);

	public Optional<HouseHoldExpenseCategory> findByCode(String code);

	public Optional<HouseHoldExpenseCategory> findByCodeAndIdNotIn(String code, Long id);

	public Boolean existsByCodeAndStatus(String code, String status);
	
	public Optional<HouseHoldExpenseCategory> findByIdAndStatus(Long id, String status);

}
