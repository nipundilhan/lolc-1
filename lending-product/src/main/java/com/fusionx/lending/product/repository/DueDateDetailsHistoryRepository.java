package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.DueDateDetailsHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Due Date Details History Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021    FXL-139  	 FXL-926	Piyumi      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface DueDateDetailsHistoryRepository extends JpaRepository<DueDateDetailsHistory, Long> {

}
