package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.RiskCalculation;
import com.fusionx.lending.origination.resource.CreditScoreRootResource;
/**
 * API Service related to Credit Score calculation
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
 * 1        08-12-2021      -                        Nipun Dilhan             Created
 * <p>
 *
 */

@Service
public interface CreditScoreCalculationService {
	
    /**
     * calculate credit score
     *
     * @param creditScoreRootResource the object to calculate
     * @return the risk calculation
     */	
	RiskCalculation calculateCreditScore(CreditScoreRootResource creditScoreRootResource );
	
    /**
     * Saves the financial statement
     *
     * @param tenantId                  the tenant id
     * @param financeStatementAddRequest the object to save
     * @param user                     the user
     * @return the long
     */	
	Long saveCreditScoreCalculation(CreditScoreRootResource calculateCreditScore ,String tenantId ,Long leadInfoId ,String user);

	/**
	 * Find by by risk calculation id.
	 *
	 * @param riskCalculationId - the id of the risk calculation
	 * @return the Optional<RiskCalculation> 
	 */
	Optional<RiskCalculation> getDetailsByRiskCalculationId(Long riskCalculationId);
}
