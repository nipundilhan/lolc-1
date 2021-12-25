package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.AccountStatus;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Account Status Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021      		     FXL-789	Dilhan        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatus, Long>{

	public List<AccountStatus> findByStatus(CommonStatus status);

	public List<AccountStatus> findByNameContaining(String name);

	public Optional<AccountStatus> findByCode(String code);
}
