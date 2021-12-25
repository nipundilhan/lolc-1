package com.fusionx.lending.origination.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.IncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {
	
	List<IncomeType> findByStatus(String status);
	
	Optional<IncomeType> findByCode(String code);

	Optional<IncomeType> findByIdAndStatus(Long id, String status);
	
	List<IncomeType> findByNameContaining(String name);

	Optional<IncomeType> findByCodeAndIdNotIn(String code, Long id);

	List<IncomeType> findByBusinessType(BusinessType businessType);
}
