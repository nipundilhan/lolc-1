package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.Repayment;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Repayment Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-07-2021     FX-6620 		FX-6803     RavishikaS      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
	
	public List<Repayment> findByStatus(CommonStatus status);

	public Optional<Repayment> findByCode(String code);

	public Optional<Repayment> findByCodeAndIdNotIn(String code, Long id);
	
	public Optional<Repayment> findByIdAndStatus(Long id, CommonStatus status);

	public List<Repayment> findByName(String name);

}
