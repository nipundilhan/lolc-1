package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.StatementTemplateDetail;

/**
 * Statement Template Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-473	      FXL-426    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Repository
public interface StatementTemplateDetailRepository extends JpaRepository<StatementTemplateDetail, Long>{

}
