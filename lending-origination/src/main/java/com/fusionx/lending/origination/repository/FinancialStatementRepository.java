package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.FinancialStatement;
/**
 * API Service related to Financial Statement.
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        17-09-2021      -               -           Nipun Dilhan      Created
 * <p>
 *
 */

@Repository
public interface FinancialStatementRepository
extends JpaRepository<FinancialStatement, Long> 
{
	
	
	List<FinancialStatement> findAllByCustomerId(Long customerId);
	
	
}
