package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.RepaymentType;

@Repository
public interface RepaymentTypeRepository extends JpaRepository<RepaymentType, Long> {
	
	List<RepaymentType> findByStatus(String status);
	
	Optional<RepaymentType> findByCode(String code);

	List<RepaymentType> findByName(String name);

	Optional<RepaymentType> findByCodeAndIdNotIn(String code, Long id);

	Optional<RepaymentType> findByIdAndNameAndStatus(Long id, String name, String status);

}
