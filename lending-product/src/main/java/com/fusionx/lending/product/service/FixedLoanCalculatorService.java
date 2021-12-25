package com.fusionx.lending.product.service;

import java.util.List;


import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.TcAmortizationPaymentSchedule;
import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.resources.FixedInstallmentLoanCalculationDetailsResponse;
import com.fusionx.lending.product.resources.FixedInstallmentPaymentScheduleResponse;
import com.fusionx.lending.product.resources.FixedLoanCalculatorAddResource;
import com.fusionx.lending.product.resources.TaxDetailsResponse;


/**
 *  API Service Fixed Installment Calculation.
 *
 * @author  Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        07-10-2021      -               -           Nipun Dilhan      		initial development
 * <p>
 */

@Service
public interface FixedLoanCalculatorService {
	
	
    /**
     * calculate fixed installment loan details.
     *
     * @param request the fixed calculator add  resource
     * @return the fixed installment loan calculation details response
     */
	FixedInstallmentLoanCalculationDetailsResponse calculateDetails(String tenantId,FixedLoanCalculatorAddResource request);
	
	
    /**
     * view payment schedule details.
     *
     * @param request the fixed calculator add  resource
     * @return list of payment schedule details
     */
	List<FixedInstallmentPaymentScheduleResponse> paymentSchedule(String tenantId ,FixedLoanCalculatorAddResource request);
	
	
    /**
     * get details by tc header id.
     *
     * @param tcHeaderId the id of tc header
     * @return optional tc header
     */
	Optional<TcHeader> findByTcHeaderId(Long tcHeaderId);
	
    /**
     * save fixed installment loan details
     *
     * @param request the id fixed loan calculator add resource
     * @param tenantId the id tenant
     * @param user the name of the logged user
     * @return  tc header
     */
	Long save(FixedLoanCalculatorAddResource request , String tenantId , String user);
	
	Optional<TcHeader> findByLeadId(Long leadId);
	
	List<TaxDetailsResponse> getTaxDetails(String tenantId , FixedLoanCalculatorAddResource request );
	
	
	List<String>  paymentScheduleTemp(String tenantId,FixedLoanCalculatorAddResource request);
	


}
