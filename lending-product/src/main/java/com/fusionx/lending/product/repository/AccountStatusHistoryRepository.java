package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.AccountStatusHistory;

/**
 * Account Status History Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021      		     FXL-789	Dilhan        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface AccountStatusHistoryRepository extends JpaRepository<AccountStatusHistory, Long>{

}
