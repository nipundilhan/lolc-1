package com.fusionx.lending.product.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.resources.BasicTcCalculatorResource;
/**
 * Basic Tc Calculator
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-10-2021   FXL-78        FXL-983    Dilki        Created
 *    2	  05-10-2021   FXL-78        FXL-985    Dilki        Created
 *    3   07-10-2021   FXL-78        FXL-1008   Dilki        Created
 *    4	  08-10-2021   FXL-78        FXL-984    Dilki        Created
 *    
 ********************************************************************************************************
 */

@Service
public interface BasicTcCalculatorService {

	/**
	 * Calculate IRR
	 * 
	 * @param basicTcCalculatorResource
	 * @return BasicTcCalculatorResource
	 */
	BasicTcCalculatorResource calculateIrr(BasicTcCalculatorResource basicTcCalculatorResource);

	/**
	 * Calculate No Of PrePayments
	 * 
	 * @param basicTcCalculatorResource
	 * @return Calculated No Of PrePayments
	 */
	BasicTcCalculatorResource calculateNoOfPrePayments(BasicTcCalculatorResource basicTcCalculatorResource);

	/**
	 * Calculate Loan Amount
	 * 
	 * @param basicTcCalculatorResource
	 * @return BasicTcCalculatorResource
	 */
	BasicTcCalculatorResource calculateLoanAmount(BasicTcCalculatorResource basicTcCalculatorResource);

	/**
	 * Calculate Period Of Loan
	 * 
	 * @param basicTcCalculatorResource
	 * @return BasicTcCalculatorResource
	 */
	BasicTcCalculatorResource calculatePeriodOfLoan(BasicTcCalculatorResource basicTcCalculatorResource);

	/**
	 * Calculate No of PrePayments
	 * 
	 * @param loanAmount, installment, annualInterestRate, term, frequency
	 * @return Calculated No of PrePayments
	 */
	BigDecimal calculateNoOfPrePayments(BigDecimal loanAmount, BigDecimal installment, BigDecimal annualInterestRate,
			int term, int frequency);

	/**
	 * Calculate Loan Amount
	 * 
	 * @param noOfPrePayments, installment, annualInterestRate, term, frequency
	 * @return Calculated Loan Amount
	 */
	BigDecimal calculateLoanAmount(BigDecimal installment, BigDecimal annualInterestRate, int term, int frequency,
			int noOfPrePayments);

	/**
	 * Calculate Period of Loan
	 * 
	 * @param noOfPrePayments, installment, annualInterestRate, loanAmount,
	 *                         frequency
	 * @return Calculated Period of Loan
	 */
	BigDecimal calculatePeriodOfLoan(Double loanAmount, Double installment, Double annualInterestRate, int frequency,
			int noOfPrePayments);

}
