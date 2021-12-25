package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.PayModes;
import com.fusionx.lending.product.enums.CommonStatus;

@Repository
public interface PayModesRepository extends JpaRepository<PayModes, Long>{

	List<PayModes> findByStatus(CommonStatus status);

	Optional<PayModes> findByCode(String code);
	
	List<PayModes> findByNameContaining(String name);
}
