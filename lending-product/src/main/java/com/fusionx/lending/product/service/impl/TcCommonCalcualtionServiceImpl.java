package com.fusionx.lending.product.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.FixedInstallmentPaymentScheduleResponse;
import com.fusionx.lending.product.resources.RepaymentStructureResource;
import com.fusionx.lending.product.resources.TcStructurePaymentScheduleResource;
import com.fusionx.lending.product.service.TcCommonCalcualtionService;
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

@Component
public class TcCommonCalcualtionServiceImpl extends MessagePropertyBase implements TcCommonCalcualtionService {
	
	private static final int ROUND_OFF_DECIMAL_POINTS = 40;

	@Override
	public BigDecimal calculateFixedInstallment(BigDecimal loanAmount, int loanPeriod , int repaymentFrequency 
			,BigDecimal annualInterestRatePercentage ,BigDecimal residualValue , int noOfPrePayments  ) {
		
		
		validationsForInstallmentCalculation(loanAmount,loanPeriod,repaymentFrequency,annualInterestRatePercentage,residualValue,noOfPrePayments);
		
		
		//convert percentage to out of 100
		BigDecimal  interestRate = annualInterestRatePercentage.multiply(BigDecimal.valueOf(0.01));	
		
		BigDecimal interestRatePerPeriod = interestRate.divide(BigDecimal.valueOf(repaymentFrequency),ROUND_OFF_DECIMAL_POINTS,RoundingMode.HALF_EVEN);
		int noOfPaymentsToBePaid = loanPeriod - noOfPrePayments;

		//calculate main dividend
		BigDecimal dividend = loanAmount;
	
		if(( residualValue ).compareTo(BigDecimal.ZERO) != 0) {			
			BigDecimal preDividend = ((BigDecimal.ONE).add(interestRatePerPeriod)).pow(loanPeriod);
			preDividend =residualValue.divide(preDividend,ROUND_OFF_DECIMAL_POINTS,RoundingMode.CEILING);
			
			dividend = loanAmount.subtract(preDividend);
		}
		
		
		//calculate main divisor
		BigDecimal preDivisor = ((BigDecimal.ONE).add(interestRatePerPeriod)).pow(noOfPaymentsToBePaid);		
		preDivisor =(BigDecimal.ONE).divide(preDivisor,ROUND_OFF_DECIMAL_POINTS,RoundingMode.CEILING);		
		preDivisor =((BigDecimal.ONE).subtract(preDivisor)).divide(interestRatePerPeriod,ROUND_OFF_DECIMAL_POINTS,RoundingMode.FLOOR);
		
		BigDecimal divisor = (preDivisor).add(BigDecimal.valueOf(noOfPrePayments));

		
		
		//calculate installment amount per period
		BigDecimal installmentAmount = dividend.divide(divisor,ROUND_OFF_DECIMAL_POINTS,RoundingMode.CEILING);
		
		return installmentAmount;		
	}

	
	@Override	
	public BigDecimal calculateNetFixedInstallmentWithOutFactoredValues(BigDecimal netloanAmount, int loanPeriod , int repaymentFrequency 
			,BigDecimal annualInterestRatePercentage ,BigDecimal residualValue , int noOfPrePayments  ) {
		
		validDecimalGreaterThanZero(netloanAmount,environment.getProperty("tc-calculation-loanAmount.invalid"));
	
		return calculateFixedInstallment(netloanAmount,loanPeriod,repaymentFrequency,annualInterestRatePercentage,residualValue,noOfPrePayments);
	}	
	
	
	@Override	
	public BigDecimal calculateFactoredValueInstallmet(BigDecimal charges ,BigDecimal taxes , int loanPeriod , int repaymentFrequency 
			,BigDecimal annualInterestRatePercentage ,BigDecimal residualValue , int noOfPrePayments  ) {
				
		BigDecimal totalChargesAndTaxes = charges.add(taxes);
		
		return calculateFixedInstallment(totalChargesAndTaxes,loanPeriod,repaymentFrequency,annualInterestRatePercentage,residualValue,noOfPrePayments);
	}	
	
	@Override	
	public BigDecimal calculateTotalReceivableWithCapitalAndInterest(BigDecimal fixedInstallment, int loanPeriod ) {

		BigDecimal totalReceivableWithCapitalAndInterest = fixedInstallment.multiply(BigDecimal.valueOf(loanPeriod));
	
		return totalReceivableWithCapitalAndInterest;		
	}
	
	@Override	
	public BigDecimal calculateTotalReceivableWithChargesAndTaxes(BigDecimal totalGrossInstallment, int loanPeriod ,BigDecimal chargesToBeCollectAtDownPayment ,BigDecimal amountPaidInAdvance ) {

		BigDecimal totalReceivableWithChargesAndTaxes = (totalGrossInstallment.multiply(BigDecimal.valueOf(loanPeriod))).add(chargesToBeCollectAtDownPayment).add(amountPaidInAdvance);
	
		return totalReceivableWithChargesAndTaxes;		
	}

	@Override	
	public BigDecimal calculateTotalInterest(BigDecimal loanAmount ,BigDecimal totalReceivableWithCapitalAndInterest  ) {
				
		BigDecimal totalInterest = totalReceivableWithCapitalAndInterest.subtract(loanAmount);		
		return totalInterest;		
	}
	
	@Override	
	public BigDecimal calculateLoanFactor(BigDecimal netLoanAmount ,BigDecimal fixedInstallmentWithOutFactoredValues  ) {
		
		validDecimalGreaterThanZero(fixedInstallmentWithOutFactoredValues,environment.getProperty("tc-calculation-installment.invalid"));
		validDecimalGreaterThanZero(netLoanAmount,environment.getProperty("tc-calculation-loanAmount.invalid"));		
		
		BigDecimal loanFactor = (fixedInstallmentWithOutFactoredValues.divide(netLoanAmount,ROUND_OFF_DECIMAL_POINTS,RoundingMode.CEILING)).multiply(BigDecimal.valueOf(100));	
		return loanFactor;			
	}

	@Override	
	public BigDecimal calculateTotalFactor(BigDecimal loanAmount ,BigDecimal fixedInstallment  ) {
			
		validDecimalGreaterThanZero(fixedInstallment,environment.getProperty("tc-calculation-installment.invalid"));
		validDecimalGreaterThanZero(loanAmount,environment.getProperty("tc-calculation-loanAmount.invalid"));	
		
		BigDecimal totalFactor = (fixedInstallment.divide(loanAmount,ROUND_OFF_DECIMAL_POINTS,RoundingMode.CEILING)).multiply(BigDecimal.valueOf(100));	
		return totalFactor;			
	}
	
	@Override	
	public BigDecimal calculateChargeFactor(BigDecimal charges ,BigDecimal taxes ,BigDecimal factoredValueInstallmet  ) {
		
		BigDecimal taxPlusCharges = taxes.add(charges);
		
		validDecimalGreaterThanZero(taxPlusCharges,environment.getProperty("tc-calculation-chargesAndTaxes.invalid"));
		validDecimalGreaterThanZero(factoredValueInstallmet,environment.getProperty("tc-calculation-installment.invalid"));
		
		BigDecimal loanFactor = (factoredValueInstallmet.divide(taxPlusCharges,ROUND_OFF_DECIMAL_POINTS,RoundingMode.CEILING)).multiply(BigDecimal.valueOf(100));	
		return loanFactor;			
	}
	
	
	@Override
	public BigDecimal calculateTotalTax(BigDecimal taxesToBePainInAdvance ,BigDecimal  taxesAddedToLoanAmount ,BigDecimal   taxesToBeCollectedInPeriod , int period) {
		
		validDecimalGreaterThanOrEqualZero(taxesToBePainInAdvance,environment.getProperty("tc-calculation-taxAmountPaidInAdvance.invalid"));
		validDecimalGreaterThanOrEqualZero(taxesToBeCollectedInPeriod,environment.getProperty("tc-calculation-taxThroughOutPeriod.invalid"));
		validDecimalGreaterThanOrEqualZero(taxesAddedToLoanAmount,environment.getProperty("tc-calculation-taxAddedToLoanAmount.invalid"));	
		
		
		validNumberGreaterThanOrEqualZero(period,environment.getProperty("tc-calculation-loanPeriod.invalid"));
		
		BigDecimal totalTax = (taxesToBeCollectedInPeriod.multiply(BigDecimal.valueOf(period))).add(taxesAddedToLoanAmount).add(taxesToBePainInAdvance);
		return totalTax;
	}
	
	@Override
	public BigDecimal calculateTotalCharges(BigDecimal totalCharges ,BigDecimal  chargesToBeCollectAtDownPayment ,BigDecimal   chargesToBeCollectedInPeriod , int period) {
		
		validNumberGreaterThanOrEqualZero(period,environment.getProperty("tc-calculation-loanPeriod.invalid"));
		
		validDecimalGreaterThanOrEqualZero(chargesToBeCollectAtDownPayment,environment.getProperty("tc-calculation-totalDownPayment.invalid"));
		validDecimalGreaterThanOrEqualZero(totalCharges,environment.getProperty("tc-calculation-totalCharge.invalid"));
		validDecimalGreaterThanOrEqualZero(chargesToBeCollectedInPeriod,environment.getProperty("tc-calculation-chargeThroughOutPeriod.invalid"));
		
		BigDecimal totalTax = (chargesToBeCollectedInPeriod.multiply(BigDecimal.valueOf(period))).add(totalCharges).add(chargesToBeCollectAtDownPayment);
		return totalTax;
	}
	
	

	
	
	@Override	
	public BigDecimal calculateTotalGrossInstallment(BigDecimal netFixedInstallment ,BigDecimal totalCharges ,BigDecimal totalTaxes  ) {
			
		validDecimalGreaterThanZero(netFixedInstallment,environment.getProperty("tc-calculation-fixedInstallmentAmount.invalid"));
		validDecimalGreaterThanOrEqualZero(totalCharges,environment.getProperty("tc-calculation-totalTax.invalid"));
		validDecimalGreaterThanOrEqualZero(totalTaxes,environment.getProperty("tc-calculation-totalCharge.invalid"));
		
		BigDecimal totalGrossInstallment = netFixedInstallment.add(totalCharges).add(totalTaxes);
		return totalGrossInstallment;			
	}
	
	
	@Override	
	public BigDecimal calculatePrePaidInstallment(BigDecimal grossInstallment ,int noOfPrePayments ) {
			
		validDecimalGreaterThanZero(grossInstallment,environment.getProperty("tc-calculation-grossInstallmentAmount.invalid"));	
		validNumberGreaterThanOrEqualZero(noOfPrePayments,environment.getProperty("tc-calculation-noOfPrePayments.invalid"));

		BigDecimal prePaindInstallment = grossInstallment.multiply(BigDecimal.valueOf(noOfPrePayments));
		return prePaindInstallment;			
	}

	
	@Override	
	public BigDecimal calculateTotalDownPayment(BigDecimal grossInstallment ,int noOfPrePayments ,BigDecimal chargesToBeCollectAtDownPayment ,BigDecimal amountPaidInAdvance ) {

		validDecimalGreaterThanZero(grossInstallment,environment.getProperty("tc-calculation-grossInstallmentAmount.invalid"));	
		validNumberGreaterThanOrEqualZero(noOfPrePayments,environment.getProperty("tc-calculation-noOfPrePayments.invalid"));
		validDecimalGreaterThanOrEqualZero(chargesToBeCollectAtDownPayment,environment.getProperty("tc-calculation-chargeAmountPaidInAdvance.invalid"));
		validDecimalGreaterThanOrEqualZero(amountPaidInAdvance,environment.getProperty("tc-calculation-amountPaidInAdvance.invalid"));
		
		BigDecimal tempTotalDownPayment = grossInstallment.multiply(BigDecimal.valueOf(noOfPrePayments));
		BigDecimal totalDownPayment = tempTotalDownPayment.add(chargesToBeCollectAtDownPayment).add(amountPaidInAdvance);
		
		return totalDownPayment;			
	}
	
	
	public void validDecimalGreaterThanOrEqualZero(BigDecimal amount,String message) {		
		if(amount == null) {
			throw new ValidateRecordException(message, MESSAGE);
		}
		if(((amount).compareTo(BigDecimal.ZERO) < 0)) {
			throw new ValidateRecordException(message, MESSAGE);
		}
	}
	
	public void validDecimalGreaterThanZero(BigDecimal amount,String message) {		
		if(amount == null) {
			throw new ValidateRecordException(message, MESSAGE);
		}
		if(((amount).compareTo(BigDecimal.ZERO) < 0)) {
			throw new ValidateRecordException(message, MESSAGE);
		}		
	}
	
	public void validNumberGreaterThanOrEqualZero(int num,String message) {
		
		if((num < 0)) {
			throw new ValidateRecordException(message, MESSAGE);
		}		
	}
	
	
	public void validNumberGreaterThanZero(int num,String message) {
		
		if((num <= 0)) {
			throw new ValidateRecordException(message, MESSAGE);
		}		
	}
	
	

	
	
	
	public void validationsForInstallmentCalculation(BigDecimal loanAmount, int loanPeriod , int repaymentFrequency 
			,BigDecimal annualInterestRatePercentage ,BigDecimal residualValue , int noOfPrePayments) {
		

		
		validDecimalGreaterThanZero(annualInterestRatePercentage,environment.getProperty("tc-calculation-interestRate.invalid"));
		validDecimalGreaterThanZero(loanAmount,environment.getProperty("tc-calculation-loanAmount.invalid"));
		validDecimalGreaterThanOrEqualZero(residualValue,environment.getProperty("tc-calculation-residualValue.invalid"));
		

		validNumberGreaterThanOrEqualZero(noOfPrePayments,environment.getProperty("tc-calculation-noOfPrePayments.invalid"));
		validNumberGreaterThanZero(repaymentFrequency,environment.getProperty("tc-calculation-repaymentFrequency.invalid"));
		validNumberGreaterThanZero(loanPeriod,environment.getProperty("tc-calculation-loanPeriod.invalid"));		
		
	}
	
	

//=============================== payment schedule calculations ===================================================	
//-------------------------start	
	
	
	
	@Override
	public List<String> paymentScheduleTemp(BigDecimal loanAmount, int loanPeriod ,BigDecimal irr, int repaymentFrequency ,BigDecimal installmentAmount ) {
		
		
		BigDecimal  irrRate = irr.multiply(BigDecimal.valueOf(0.01));
		
		List<String> paymentScheduleList = new ArrayList<>();
		
		int maxLengthInterestPortion = String.valueOf(installmentAmount.longValue()).length();
		
		BigDecimal currentCapitalBalance = loanAmount;
		for(int i = 1 ; i < loanPeriod+1 ;i++) {
			
			BigDecimal interestPortion = calcualteInterestPortion(currentCapitalBalance,irrRate,repaymentFrequency);
			interestPortion = interestPortion.setScale(3, RoundingMode.FLOOR);
			interestPortion = interestPortion.setScale(2, RoundingMode.HALF_DOWN);

			BigDecimal capitalPortion = calcualteCapitalPortion(installmentAmount,interestPortion);			
			
			int lengthInterestPortion = String.valueOf(interestPortion.longValue()).length();
			String period = i <10 ? ("0"+i) :String.valueOf(i);
			
			String InterestPortionString = "";
			for(int j = maxLengthInterestPortion-lengthInterestPortion ; j > 0 ;j--) {
				InterestPortionString = InterestPortionString+" ";
			}
			
			
			String message = 	"period - "+ period   + " | capital portion - "+capitalPortion + " | interest portion - " + InterestPortionString+ interestPortion  +" | capital balance - " +currentCapitalBalance.toString();
			currentCapitalBalance = calcualteNewCapitalBalance(currentCapitalBalance,capitalPortion);
			paymentScheduleList.add(message);
			
			
			
		}
		
		return paymentScheduleList;		
	}
	
	@Override
	public List<FixedInstallmentPaymentScheduleResponse> paymentSchedule(BigDecimal loanAmount, int loanPeriod ,BigDecimal irr, int repaymentFrequency ,BigDecimal installmentAmount ,BigDecimal chargesThroughOutPeriod ,BigDecimal taxesThroughOutPeriod ,BigDecimal totalGrossInstallment ) {
		
		
		BigDecimal  irrRate = irr.multiply(BigDecimal.valueOf(0.01));
		
		List<FixedInstallmentPaymentScheduleResponse> paymentSchedule =  new ArrayList<>();
		
		BigDecimal currentCapitalBalance = loanAmount;
		for(int i = 1 ; i < loanPeriod+1 ;i++) {
			
			FixedInstallmentPaymentScheduleResponse createPaymentSequenceResponse = createPaymentSequenceResponse(Long.valueOf(i),currentCapitalBalance,irrRate,repaymentFrequency,installmentAmount);
			createPaymentSequenceResponse.setChargesCollectThroughOutPeriod(chargesThroughOutPeriod);
			createPaymentSequenceResponse.setTaxesCollectThroughOutPeriod(taxesThroughOutPeriod);
			createPaymentSequenceResponse.setTotalGrossInstallment(totalGrossInstallment);
			createPaymentSequenceResponse.setNetFixedInstallment(installmentAmount);
			paymentSchedule.add(createPaymentSequenceResponse);
			currentCapitalBalance = calcualteNewCapitalBalance(currentCapitalBalance,createPaymentSequenceResponse.getCapitalPortion());			
		}
		
		return paymentSchedule;		
	}
	
	@Override
	public FixedInstallmentPaymentScheduleResponse createPaymentSequenceResponse(Long sequence,BigDecimal currentCapitalBalance,BigDecimal irrRate, int repaymentFrequency ,BigDecimal installmentAmount ) {
		
		BigDecimal interestPortion = calcualteInterestPortion(currentCapitalBalance,irrRate,repaymentFrequency);
		interestPortion = interestPortion.setScale(3, RoundingMode.FLOOR);
		interestPortion = interestPortion.setScale(2, RoundingMode.HALF_DOWN);

		BigDecimal capitalPortion = calcualteCapitalPortion(installmentAmount,interestPortion);			
					
		
		FixedInstallmentPaymentScheduleResponse response = new FixedInstallmentPaymentScheduleResponse();
		response.setSequence(sequence);
		response.setInterestPortion(interestPortion);
		response.setCapitalPortion(capitalPortion);
		response.setCapitalBalance(currentCapitalBalance);
		
		return response;
		
		
	}
	
	
	public BigDecimal calcualteInterestPortion(BigDecimal currentCapitalBalance,BigDecimal irrRate, int repaymentFrequency ) {
		
		BigDecimal tempInterestPortion =irrRate.divide(BigDecimal.valueOf(repaymentFrequency),ROUND_OFF_DECIMAL_POINTS,RoundingMode.FLOOR);
		BigDecimal interestPortion = currentCapitalBalance.multiply(tempInterestPortion);
		
		return interestPortion;
	}
	
	public BigDecimal calcualteCapitalPortion(BigDecimal installmentAmount ,BigDecimal InterestPortion ) {
		BigDecimal capitalPortion = installmentAmount.subtract(InterestPortion);
		
		return capitalPortion;
	}
	
	public BigDecimal calcualteNewCapitalBalance(BigDecimal currentCapitalBalance ,BigDecimal capitalPortion ) {
		return currentCapitalBalance.subtract(capitalPortion);		
	}
	
//-------------------------end	
//=============================== payment schedule calculations ===================================================	
			
	
	
	
	
	
	

	
	
	
//================================  IRR calculation =========================================================
//---------------------------start	
	
	
	@Override
	public BigDecimal calculateIRRforFixedInstallments(BigDecimal loanAmount,BigDecimal installment, int loanPeriod , int repaymentFrequency , int noOfPrePayments) {
		
		int noOfIterations = 8;
		
		BigDecimal finalAnswer = BigDecimal.ZERO;
		BigDecimal initialGuessR = BigDecimal.valueOf(0.00); 
		
		int period = loanPeriod-noOfPrePayments;
		BigDecimal amount =  loanAmount.subtract(installment.multiply(BigDecimal.valueOf(noOfPrePayments)));
		
		BigDecimal npv = calculateNpvTemp(installment,1,period,initialGuessR);
		
		
		if(npv.compareTo(amount) >= 0) {
									
			int currentIteration = 1;
			BigDecimal guess = BigDecimal.ZERO;
			
			while(currentIteration <= noOfIterations) {
								
				guess = iteration(amount,installment,period,guess,currentIteration);				
				currentIteration +=1;
				
			}
			
			finalAnswer = guess.multiply(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(repaymentFrequency));
				
		}
		
		return finalAnswer;			
	}
	
	
	

	public BigDecimal iteration(BigDecimal loanAmount,BigDecimal installment, int loanPeriod , BigDecimal guess , int iterationNo) {
		
		BigDecimal finalAnswer = BigDecimal.ONE;
		int start = 1;
		
		
		BigDecimal i = guess;
		
		BigDecimal iterationValue = (BigDecimal.ONE).divide(  (BigDecimal.TEN).pow(iterationNo)   );
		
		BigDecimal limit = guess.add(iterationValue.multiply(BigDecimal.TEN));
		
				
		while ((limit).compareTo(i) > 0) {
			
			BigDecimal tempNpv = calculateNpvTemp(installment, start, loanPeriod, i);

			if (loanAmount.compareTo(tempNpv) >= 0) {
				finalAnswer = i.subtract(iterationValue);
				break;
			}
			
			finalAnswer = i;
			i = i.add(iterationValue);
		}

		return finalAnswer;		
	}
	
	
	
	public BigDecimal calculateNpvTemp(BigDecimal installment, int start , int end ,BigDecimal guess ) {
		
		BigDecimal divisor = (BigDecimal.ONE).add((guess));
		BigDecimal npv = BigDecimal.ZERO;
		
		for(int i  = start ; i < end+1 ; i++) {
			
			BigDecimal divisorFinal =  divisor.pow(i);
			BigDecimal tempNpv = installment.divide(divisorFinal,ROUND_OFF_DECIMAL_POINTS,RoundingMode.HALF_EVEN);
				
			npv = npv.add(tempNpv);						
		}
		
		return npv;
	}
	
	@Override
	public BigDecimal calculateIrrStructredInstallments(BigDecimal loanAmount,List<TcStructurePaymentScheduleResource> structuredPaymentScheduleList  , int repaymentFrequency ) {

		
		BigDecimal finalAnswer = BigDecimal.ZERO;
		

			
		int noOfIterations = 10;
		int currentIteration = 1;
		BigDecimal guess = BigDecimal.ZERO;

		while (currentIteration <= noOfIterations) {
			guess = iterationStructred(loanAmount, structuredPaymentScheduleList, guess, currentIteration, repaymentFrequency);
			currentIteration += 1;
		}
		

		finalAnswer = guess.multiply(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(repaymentFrequency));
		
		return finalAnswer;
		
		
	}

	
	
	
	public BigDecimal iterationStructred(BigDecimal loanAmount,List<TcStructurePaymentScheduleResource> structuredPaymentScheduleList , BigDecimal guess , int upto ,  int repaymentFrequency) {
		
		BigDecimal finalAnswer = BigDecimal.ONE;

		BigDecimal i = guess;
		
		BigDecimal iterationValue = (BigDecimal.ONE).divide(  (BigDecimal.TEN).pow(upto)   );
		
		BigDecimal limit = guess.add(iterationValue.multiply(BigDecimal.TEN));

		while ((limit).compareTo(i) > 0) {
			BigDecimal tempNpv = calculateNpvStructed(structuredPaymentScheduleList,1,structuredPaymentScheduleList.size()+1, i);

			if (loanAmount.compareTo(tempNpv) >= 0) {
				finalAnswer = i.subtract(iterationValue);
				break;
			}
			finalAnswer = i;
			i = i.add(iterationValue);
		}

		return finalAnswer;
		
	}
	

	
	
	public BigDecimal calculateNpvStructed(	List<TcStructurePaymentScheduleResource> structuredPaymentScheduleList, int start , int end ,BigDecimal guess  ) {
		
		BigDecimal divisor = (BigDecimal.ONE).add((guess));
		BigDecimal npvPeriod = BigDecimal.ZERO;
		for(int i  = start ; i < end ; i++) {
			
			BigDecimal divisorFinal =  divisor.pow(i);
			BigDecimal tempNpv = (structuredPaymentScheduleList.get(i-1).getInstallment()).divide(divisorFinal,ROUND_OFF_DECIMAL_POINTS,RoundingMode.HALF_EVEN);
			
			npvPeriod = npvPeriod.add(tempNpv);
						
		}
		
		return npvPeriod;
	}
		
	
	
//-----------------------------end		
//================================  IRR calculation =========================================================
	
	
	
	
	
}
