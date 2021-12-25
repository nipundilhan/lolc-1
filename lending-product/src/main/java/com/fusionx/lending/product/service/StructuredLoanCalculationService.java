package com.fusionx.lending.product.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.resources.StructuredLoanCalculationResource;
import com.fusionx.lending.product.resources.StructuredLoanCalculationResponseResource;
import com.fusionx.lending.product.resources.TcStructuredDetailAddResource;

/**
 * 	Structured Loan Calculation Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-10-2021   			  	 FXL-993       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@Service
public interface StructuredLoanCalculationService {
	
	/**
	 * PV Factor Calculation.
	 *
	 * @param periodFrom - the periodFrom
	 * @param periodTo - the periodTo
	 * @param frequency - the frequency
	 * @param annualInterestRate - the annualInterestRate
	 * 
	 * @return the PV Factor
	 * @throws Exception 
	 */
	BigDecimal pvFactor(int periodFrom,int periodTo, int frequency, BigDecimal annualInterestRate) throws Exception;
	
	/**
	 * PV Factor Calculation for In Advance
	 *
	 * @param periodFrom - the periodFrom
	 * @param periodTo - the periodTo
	 * @param frequency - the frequency
	 * @param annualInterestRate - the annualInterestRate
	 * 
	 * @return the PV Factor
	 * @throws Exception 
	 */
	BigDecimal pvFactorForInAdvance(int periodFrom,int periodTo, int frequency, BigDecimal annualInterestRate) throws Exception;
	
	/**
	 * PV Factor Calculation for In Advance
	 *
	 * @param periodFrom - the periodFrom
	 * @param periodTo - the periodTo
	 * @param frequency - the frequency
	 * @param annualInterestRate - the annualInterestRate
	 * 
	 * @return the PV Factor
	 * @throws Exception 
	 */
	BigDecimal pvFactorForInArrears(int periodFrom,int periodTo, int frequency, BigDecimal annualInterestRate) throws Exception;
	

	/**
	 * NPV Calculation.
	 *
	 * @param installment - the installment
	 * @param pvFactor - the pvFactor
	 * 
	 * @return the PV Factor
	 */
	BigDecimal npvCalculation(BigDecimal installment, BigDecimal pvFactor);
	
	/**
	 * net rental Calculation.
	 *
	 * @param loanAmount - the loanAmount
	 * @param structuredNPV - the structuredNPV
	 * @param advancedPayment - the advancedPayment
	 * @param frequency - the frequency
	 * @param annualInterestRate - the annualInterestRate
	 * @param fixedPVFactor - the fixedPVFactor
	 * @return the net rental
	 */
	BigDecimal netRental(BigDecimal loanAmount, BigDecimal structuredNPV, BigDecimal advancedPayment, int frequency,
			BigDecimal annualInterestRate, BigDecimal fixedPVFactor);
	/**
	 * Structured Loan Calculation.
	 *
	 * @param tenantId - the tenantId
	 * @param structuredLoanCalculationResource - the structuredLoanCalculationResource
	 * @return structuredLoanCalculationResponseResource - structuredLoanCalculationResponseResource
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	StructuredLoanCalculationResponseResource structuredLoanCalculator(String tenantId, StructuredLoanCalculationResource structuredLoanCalculationResource) throws NumberFormatException, Exception;

	/**
	 * add Tc Structured Detail.
	 *
	 * @param tenantId - the tenantId
	 * @param tcStructuredDetailAddResource - the tcStructuredDetailAddResource
	 * @return structuredLoanCalculationResponseResource = structuredLoanCalculationResponseResource
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	StructuredLoanCalculationResponseResource addTcStructuredDetail(String tenantId, TcStructuredDetailAddResource tcStructuredDetailAddResource) throws NumberFormatException, Exception;

	/**
	 * structured Loan Calculator Caller
	 *
	 * @param tenantId - the tenantId
	 * @param tcStructuredDetailAddResource - the tcStructuredDetailAddResource
	 * @return structuredLoanCalculationResponseResource = structuredLoanCalculationResponseResource
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	StructuredLoanCalculationResponseResource structuredLoanCalculatorCaller(String tenantId , TcStructuredDetailAddResource tcStructuredDetailAddResource) throws NumberFormatException, Exception;
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	public Optional<TcHeader> findById(Long id);
	
	/**
	 * Find by lead id.
	 *
	 * @param leadId the lead id
	 * @return the List
	 */
	public  List<TcHeader> findByLeadId(Long leadId);
}
