package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefinitionHistory;
/**
Master Definition 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-07-2021   FXL-1         FXL-5      Dilki        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface MasterDefinitionHistoryRepository extends JpaRepository<MasterDefinitionHistory, Long> {

}
