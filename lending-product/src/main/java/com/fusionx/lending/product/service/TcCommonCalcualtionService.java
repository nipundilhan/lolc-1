package com.fusionx.lending.product.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.resources.FixedInstallmentPaymentScheduleResponse;
import com.fusionx.lending.product.resources.RepaymentStructureResource;
import com.fusionx.lending.product.resources.TcStructurePaymentScheduleResource;
/**
 * API Service IRR calculation.
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
public interface TcCommonCalcualtionService {

	/**
	 * Fixed installment Calculation.
	 *
	 * @param loanAmount - the loan amount
	 * @param loanPeriod - the loan period
	 * @param repaymentFrequency - the repayments frequency
	 * @param annualInterestRatePercentage - the annual interest rate
	 * @param residualValue - the residual value
	 * @param noOfPrePayments - the no of pre payments
	 * 
	 * @return the Fixed Installment
	 * @throws Exception 
	 */
	BigDecimal calculateFixedInstallment(BigDecimal loanAmount, int loanPeriod , int repaymentFrequency 
			,BigDecimal annualInterestRatePercentage ,BigDecimal residualValue , int noOfPrePayments  );
	
	/**
	 * Total receivable with capital and interest Calculation.
	 *
	 * @param fixedInstallment - the fixed installment amount
	 * @param loanPeriod - the loan period
	 * 
	 * @return the total receivable with capital and interest 
	 * @throws Exception 
	 */
	BigDecimal calculateTotalReceivableWithCapitalAndInterest(BigDecimal fixedInstallment, int loanPeriod );
	
	/**
	 * Total receivable with capital and interest Calculation.
	 *
	 * @param fixedInstallment - the fixed installment amount
	 * @param loanAmount - the loan amount
	 * 
	 * @return total factor 
	 * @throws Exception 
	 */
	BigDecimal calculateTotalFactor(BigDecimal loanAmount ,BigDecimal fixedInstallment  );	
	
	/**
	 * Total interest Calculation.
	 *
	 * @param totalReceivableWithCapitalAndInterest - the total receivable with capital and interest
	 * @param loanAmount - the loan amount
	 * 
	 * @return total interest 
	 * @throws Exception 
	 */
	BigDecimal calculateTotalInterest(BigDecimal loanAmount ,BigDecimal totalReceivableWithCapitalAndInterest  );
	
	/**
	 * Total gross installment Calculation.
	 *
	 * @param netFixedInstallment - the net fixed installment
	 * @param totalCharges - the total charges
	 * @param totalTaxes - the total taxes
	 * 
	 * @return total gross installment 
	 * @throws Exception 
	 */
	BigDecimal calculateTotalGrossInstallment(BigDecimal netFixedInstallment ,BigDecimal totalCharges ,BigDecimal totalTaxes  );
	
	/**
	 * pre paid installment Calculation.
	 *
	 * @param grossInstallment - the gross installment
	 * @param noOfPrePayments - the no of pre payments
	 * 
	 * @return pre paid installment 
	 * @throws Exception 
	 */
	BigDecimal calculatePrePaidInstallment(BigDecimal grossInstallment ,int noOfPrePayments );
	
	/**
	 * total down payment Calculation.
	 *
	 * @param grossInstallment - the gross installment
	 * @param noOfPrePayments - the no of pre payments
	 * @param chargesToBeCollectAtDownPayment - the charges to be collected at down payment
	 * @param amountPaidInAdvance - the amount paid in advance
	 * 
	 * @return total down payment
	 * @throws Exception 
	 */
	BigDecimal  calculateTotalDownPayment(BigDecimal grossInstallment ,int noOfPrePayments ,BigDecimal chargesToBeCollectAtDownPayment ,BigDecimal amountPaidInAdvance );
	
	/**
	 * total tax Calculation.
	 *
	 * @param taxesToBePainInAdvance - the taxes to be paid in advance
	 * @param taxesAddedToLoanAmount - the taxes added to loan amount
	 * @param taxesToBeCollectedInPeriod - the taxes to be collect through out the period
	 * @param period - the loan period
	 * 
	 * @return total tax
	 * @throws Exception 
	 */
	BigDecimal calculateTotalTax(BigDecimal taxesToBePainInAdvance ,BigDecimal  taxesAddedToLoanAmount ,BigDecimal   taxesToBeCollectedInPeriod , int period);

	/**
	 * total charge Calculation.
	 *
	 * @param totalCharges - the total charges
	 * @param chargesToBeCollectAtDownPayment - the charges to be collected at down payment
	 * @param chargesToBeCollectedInPeriod - the charges to be collect through out the period
	 * @param period - the loan period
	 * 
	 * @return total charges
	 * @throws Exception 
	 */
	BigDecimal calculateTotalCharges(BigDecimal totalCharges ,BigDecimal  chargesToBeCollectAtDownPayment ,BigDecimal   chargesToBeCollectedInPeriod , int period);

	/**
	 * total receivable with charges and taxes Calculation.
	 *
	 * @param totalGrossInstallment - the total gross installment
	 * @param chargesToBeCollectAtDownPayment - the charges to be collected at down payment
	 * @param amountPaidInAdvance - the amount paid in advance
	 * @param loanPeriod - the loan period
	 * 
	 * @return total receivable with charges and taxes
	 * @throws Exception 
	 */
	BigDecimal calculateTotalReceivableWithChargesAndTaxes(BigDecimal totalGrossInstallment, int loanPeriod ,BigDecimal chargesToBeCollectAtDownPayment ,BigDecimal amountPaidInAdvance );
	
	/**
	 * net fixed installment with out factored values Calculation.
	 *
	 * @param totalGrossInstallment - the total gross installment
	 * @param chargesToBeCollectAtDownPayment - the charges to be collected at down payment
	 * @param amountPaidInAdvance - the amount paid in advance
	 * @param loanPeriod - the loan period
	 * 
	 * @return total receivable with charges and taxes
	 * @throws Exception 
	 */
	BigDecimal calculateNetFixedInstallmentWithOutFactoredValues(BigDecimal netloanAmount, int loanPeriod , int repaymentFrequency 
			,BigDecimal annualInterestRatePercentage ,BigDecimal residualValue , int noOfPrePayments  );
	
	/**
	 * factored values installment Calculation.
	 *
	 * @param charges - the charges
	 * @param taxes - the taxes
	 * @param repaymentFrequency - the re payment frequency
	 * @param annualInterestRatePercentage - the annual interest rate percentage
	 * @param residualValue - the residual value
	 * @param noOfPrePayments - the no of pre payments
	 * 
	 * @return factored value installment
	 * @throws Exception 
	 */
	BigDecimal calculateFactoredValueInstallmet(BigDecimal charges ,BigDecimal taxes , int loanPeriod , int repaymentFrequency 
			,BigDecimal annualInterestRatePercentage ,BigDecimal residualValue , int noOfPrePayments  );
	
	/**
	 * loan factor Calculation.
	 *
	 * @param netLoanAmount - the net loan amount
	 * @param fixedInstallmentWithOutFactoredValues - the fixed installment with out factored values
	 * 
	 * @return loan factor
	 * @throws Exception 
	 */
	BigDecimal calculateLoanFactor(BigDecimal netLoanAmount ,BigDecimal fixedInstallmentWithOutFactoredValues  );
	
	/**
	 * charge factor Calculation.
	 *
	 * @param charges - the charges
	 * @param taxes - the taxes
	 * @param factoredValueInstallmet - the factored value installment
	 * 
	 * @return charge factor
	 * @throws Exception 
	 */	
	BigDecimal calculateChargeFactor(BigDecimal charges ,BigDecimal taxes ,BigDecimal factoredValueInstallmet  );
	
	
	
	
	/**
	 * IRR for fixed installment  Calculation.
	 *
	 * @param loanAmount - the loan amount
	 * @param installment - the installment
	 * @param loanPeriod - the loan period
	 * @param repaymentFrequency - the repayment frequency
	 * 
	 * @return IRR for fixed installment
	 * @throws Exception 
	 */	
	BigDecimal calculateIRRforFixedInstallments(BigDecimal loanAmount,BigDecimal installment, int loanPeriod , int repaymentFrequency ,  int noOfPrePayments);
	
	
	BigDecimal calculateIrrStructredInstallments(BigDecimal loanAmount,List<TcStructurePaymentScheduleResource> structuredPaymentScheduleList  , int repaymentFrequency );
	
	List<String> paymentScheduleTemp(BigDecimal loanAmount, int loanPeriod ,BigDecimal irr, int repaymentFrequency ,BigDecimal installmentAmount );
	
	List<FixedInstallmentPaymentScheduleResponse> paymentSchedule(BigDecimal loanAmount, int loanPeriod ,BigDecimal irr, int repaymentFrequency ,BigDecimal installmentAmount ,BigDecimal chargesThroughOutPeriod ,BigDecimal taxesThroughOutPeriod ,BigDecimal totalGrossInstallment);
	
	
	FixedInstallmentPaymentScheduleResponse createPaymentSequenceResponse(Long sequence,BigDecimal currentCapitalBalance,BigDecimal irrRate, int repaymentFrequency ,BigDecimal installmentAmount );
	
	BigDecimal calcualteInterestPortion(BigDecimal currentCapitalBalance,BigDecimal irrRate, int repaymentFrequency );
	
	BigDecimal calcualteCapitalPortion(BigDecimal installmentAmount ,BigDecimal InterestPortion );
	 
	BigDecimal calcualteNewCapitalBalance(BigDecimal currentCapitalBalance ,BigDecimal capitalPortion );
}
