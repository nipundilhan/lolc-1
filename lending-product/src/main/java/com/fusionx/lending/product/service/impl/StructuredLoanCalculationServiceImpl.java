package com.fusionx.lending.product.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.domain.TcAdhocCharges;
import com.fusionx.lending.product.domain.TcFixedCharges;
import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.domain.TcOptionsCharges;
import com.fusionx.lending.product.domain.TcRevolvingDetail;
import com.fusionx.lending.product.domain.TcRevolvingPaymentSchedule;
import com.fusionx.lending.product.domain.TcStructuredDetail;
import com.fusionx.lending.product.domain.TcStructuredPayment;
import com.fusionx.lending.product.domain.TcStructuredPaymentSchedule;
import com.fusionx.lending.product.enums.CalculateMethodEnum;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.RateOrAmount;
import com.fusionx.lending.product.enums.RepaymentCriteriaEnum;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ApplicationFrequencyRepository;
import com.fusionx.lending.product.repository.FeeChargeRepository;
import com.fusionx.lending.product.repository.ProductRepository;
import com.fusionx.lending.product.repository.RepaymentFrequencyRepository;
import com.fusionx.lending.product.repository.SubProductRepository;
import com.fusionx.lending.product.repository.TcAdhocChargesRepository;
import com.fusionx.lending.product.repository.TcFixedChargesRepository;
import com.fusionx.lending.product.repository.TcHeaderRepository;
import com.fusionx.lending.product.repository.TcOptionsChargesRepository;
import com.fusionx.lending.product.repository.TcRevolvingDetailRepository;
import com.fusionx.lending.product.repository.TcStructuredDetailRepository;
import com.fusionx.lending.product.repository.TcStructuredPaymentRepository;
import com.fusionx.lending.product.repository.TcStructuredPaymentScheduleRepository;
import com.fusionx.lending.product.resources.AdhocChargesListResource;
import com.fusionx.lending.product.resources.AdhocChargesResource;
import com.fusionx.lending.product.resources.Currency;
import com.fusionx.lending.product.resources.FixedChargesResource;
import com.fusionx.lending.product.resources.FrequencyResponse;
import com.fusionx.lending.product.resources.OptionalChargesListResource;
import com.fusionx.lending.product.resources.OptionalChargesResource;
import com.fusionx.lending.product.resources.RepaymentStructureResource;
import com.fusionx.lending.product.resources.StructuredLoanCalculationResource;
import com.fusionx.lending.product.resources.StructuredLoanCalculationResponseResource;
import com.fusionx.lending.product.resources.TcStructurePaymentScheduleResource;
import com.fusionx.lending.product.resources.TcStructurePaymentScheduleResponse;
import com.fusionx.lending.product.resources.TcStructuredDetailAddResource;
import com.fusionx.lending.product.service.StructuredLoanCalculationService;
import com.fusionx.lending.product.service.TcCommonCalcualtionService;
import com.fusionx.lending.product.service.ValidationService;


/**
 * 	Structured Loan Calculation Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-10-2021   			  	 FXL-993       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@Component
@Transactional(rollbackFor = Exception.class)
public class StructuredLoanCalculationServiceImpl extends MessagePropertyBase implements StructuredLoanCalculationService {

	private static final  int ROUND_OFF_SCALE = 40;
	
	@Autowired
	private TcCommonCalcualtionService tcCommonCalcualtionService;
	
	@Autowired
	private TcStructuredDetailRepository tcStructuredDetailRepository;
	
	@Autowired
	private TcStructuredPaymentRepository tcStructuredPaymentRepository;
	
	@Autowired
	private TcStructuredPaymentScheduleRepository tcStructuredPaymentScheduleRepository;

	@Autowired
	private ValidationService validationService;

	@Autowired
	TcHeaderRepository tcHeaderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SubProductRepository subProductRepository;
	
	@Autowired
	private RepaymentFrequencyRepository repaymentFrequencyRepository;
	
	@Autowired
	TcAdhocChargesRepository tcAdhocChargesRepository;
	
	@Autowired
	TcFixedChargesRepository tcFixedChargesRepository;
	
	@Autowired
	TcOptionsChargesRepository tcOptionsChargesRepository;

	@Autowired
	FeeChargeRepository feeChargeRepository;

	@Autowired
	ApplicationFrequencyRepository applicationFrequencyRepository;
	
	@Autowired
	TcRevolvingDetailRepository tcRevolvingDetailRepository;
	
	/*formula 
	 * 
	 * PV = 1/(1+(Annual Interest Rate/frequency))^ PeriodNo
	 */
	
	@Override
	public BigDecimal pvFactor(int periodFrom,int periodTo , int frequency,
			BigDecimal annualInterestRate) throws Exception {
		
		if(frequency <= 0) {
			throw new ArithmeticException(environment.getProperty(INVALID_FREQ));			
		}		
		if(periodFrom < 0 || periodTo < 0 ||periodFrom > periodTo) {
			throw new Exception(environment.getProperty(INVALID_PERIODS));			
		}
			BigDecimal ratePerFreq= (annualInterestRate.divide(new BigDecimal("100"))).divide(new BigDecimal(frequency),ROUND_OFF_SCALE ,RoundingMode.HALF_EVEN);
			BigDecimal pv = BigDecimal.ZERO;
			for(int i=periodFrom ; i<=periodTo; i++) {
				
				double divisor = Math.pow(BigDecimal.ONE.add(ratePerFreq).doubleValue(), i);
				pv =  pv.add(BigDecimal.ONE.divide(BigDecimal.valueOf(divisor),ROUND_OFF_SCALE ,RoundingMode.HALF_EVEN));
			}
			
		return pv;
	}
	
	/*formula 
	 *
	 * In Advance PV = 1/(1+(Annual Interest Rate/frequency))^ PeriodNo-1
	 */

	@Override
	public BigDecimal pvFactorForInAdvance(int periodFrom, int periodTo, int frequency,
			BigDecimal annualInterestRate) throws Exception {

		return pvFactor(periodFrom-1 ,periodTo-1 ,frequency,annualInterestRate);

	}

	/*formula 
	 *
	 * In Arrears PV = 1/(1+(Annual Interest Rate/frequency))^ PeriodNo
	 */
	@Override
	public BigDecimal pvFactorForInArrears(int periodFrom, int periodTo, int frequency,
			BigDecimal annualInterestRate) throws Exception {
		
		return pvFactor(periodFrom ,periodTo ,frequency,annualInterestRate);
	}

	@Override
	public BigDecimal npvCalculation(BigDecimal installment, BigDecimal pvFactor) {
		
		return installment.multiply(pvFactor);
	}

	/*formula 
	 *
	 * netRental = (Loan loanAmount Value - structuredNPV  - advancedPayment/(1+ Annual Interest Rate/frequency))/pv factor of fixed rentals
	 */
	@Override
	public BigDecimal netRental(BigDecimal loanAmount, BigDecimal structuredNPV, BigDecimal advancedPayment, int frequency,
			BigDecimal annualInterestRate, BigDecimal fixedPVFactor) {

		if(frequency <= 0) {
			throw new ArithmeticException(environment.getProperty(INVALID_FREQ));			
		}	
		if(fixedPVFactor.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ArithmeticException(environment.getProperty(INVALID_PVFACTOR));			
		}	
		
		BigDecimal ratePerFreq= (annualInterestRate.divide(new BigDecimal("100"))).divide(new BigDecimal(frequency),ROUND_OFF_SCALE ,RoundingMode.HALF_EVEN);
		
		BigDecimal divisor = BigDecimal.ONE.add(ratePerFreq);
		
		BigDecimal netRental = loanAmount.subtract(structuredNPV).subtract(advancedPayment.divide(divisor,ROUND_OFF_SCALE ,RoundingMode.HALF_EVEN));
		
		netRental = netRental.divide(fixedPVFactor,2,RoundingMode.HALF_EVEN);
		
		return netRental;
	}

	@Override
	public StructuredLoanCalculationResponseResource structuredLoanCalculator(String tenantId,
			StructuredLoanCalculationResource structuredLoanCalculationResource) throws NumberFormatException, Exception {
		
		StructuredLoanCalculationResponseResource response = new StructuredLoanCalculationResponseResource();
		
		response.setTotalPeriod(Integer.parseInt(structuredLoanCalculationResource.getPeriod()));
		
		response.setFixedInstallmentPeriod(calculateFixedInstallmentPeriod(structuredLoanCalculationResource.getRentalStructure(),
				Integer.parseInt(structuredLoanCalculationResource.getPeriod())));
		
		response.setNetInstallment(calculateNetRental(calculateLoanCalculationValue(new BigDecimal(structuredLoanCalculationResource.getLoanAmount()),
				new BigDecimal(structuredLoanCalculationResource.getPaidAmountInAdvance()),
				new BigDecimal(structuredLoanCalculationResource.getTaxesInAdvance()),
				new BigDecimal(structuredLoanCalculationResource.getTotalTaxes()),
				new BigDecimal(structuredLoanCalculationResource.getTaxesForSchedule()),
				new BigDecimal(structuredLoanCalculationResource.getTotalCharges()),
				new BigDecimal(structuredLoanCalculationResource.getDownPaymentCharges()),
				new BigDecimal(structuredLoanCalculationResource.getScheduleCharges()),
				Integer.parseInt(structuredLoanCalculationResource.getPeriod())), structuredLoanCalculationResource));
		
		response.setChargesPerInstallment(new BigDecimal(structuredLoanCalculationResource.getScheduleCharges()));
		
		response.setTaxesPerInstallment(new BigDecimal(structuredLoanCalculationResource.getTaxesForSchedule()));
		
		response.setTotalGrossInstallment(tcCommonCalcualtionService.calculateTotalGrossInstallment(response.getNetInstallment(), response.getChargesPerInstallment(), response.getTaxesPerInstallment()));
		
		response.setTotalDownpayment(calculateTotalDownpayment(new BigDecimal(structuredLoanCalculationResource.getPaidAmountInAdvance()),
				new BigDecimal(structuredLoanCalculationResource.getDownPaymentCharges()),
				Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments()),
				structuredLoanCalculationResource.getRentalStructure(),
				response.getNetInstallment()));
		
		response.setPrepaidInstallments(calculatePrepaidInstallments(Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments()),
				structuredLoanCalculationResource.getRentalStructure(),
				response.getNetInstallment()));
		
		response.setTotalTaxes(tcCommonCalcualtionService.calculateTotalTax(new BigDecimal(structuredLoanCalculationResource.getTaxesInAdvance()), 
				new BigDecimal(structuredLoanCalculationResource.getTotalTaxes()), 
				new BigDecimal(structuredLoanCalculationResource.getTaxesForSchedule()), 
				Integer.parseInt(structuredLoanCalculationResource.getPeriod())));
		
		response.setTotalCharges(tcCommonCalcualtionService.calculateTotalCharges(new BigDecimal(structuredLoanCalculationResource.getTotalCharges()), 
				new BigDecimal(structuredLoanCalculationResource.getDownPaymentCharges()), 
				new BigDecimal(structuredLoanCalculationResource.getScheduleCharges()), 
				Integer.parseInt(structuredLoanCalculationResource.getPeriod())));
		
		response.setLoanCalculationValue(calculateLoanCalculationValue(new BigDecimal(structuredLoanCalculationResource.getLoanAmount()),
				new BigDecimal(structuredLoanCalculationResource.getPaidAmountInAdvance()),
				new BigDecimal(structuredLoanCalculationResource.getTaxesInAdvance()),
				new BigDecimal(structuredLoanCalculationResource.getTotalTaxes()),
				new BigDecimal(structuredLoanCalculationResource.getTaxesForSchedule()),
				new BigDecimal(structuredLoanCalculationResource.getTotalCharges()),
				new BigDecimal(structuredLoanCalculationResource.getDownPaymentCharges()),
				new BigDecimal(structuredLoanCalculationResource.getScheduleCharges()),
				Integer.parseInt(structuredLoanCalculationResource.getPeriod())));
		
		response.setTotalReceivableWithoutChargesAndTaxes(calculateTotalReceivable(response.getNetInstallment(), response.getFixedInstallmentPeriod(),structuredLoanCalculationResource.getRentalStructure()));
		
		response.setTotalInterest(calculateTotalInterest(response.getTotalReceivableWithoutChargesAndTaxes(),response.getLoanCalculationValue()));
		
		response.setTotalReceivableWithChargesAndTaxes(calculateTotalReceivablewithTaxAndCharges(response.getTotalGrossInstallment(),
				response.getFixedInstallmentPeriod(),structuredLoanCalculationResource.getRentalStructure(),new BigDecimal(structuredLoanCalculationResource.getPaidAmountInAdvance()),
				new BigDecimal(structuredLoanCalculationResource.getTotalTaxes()),new BigDecimal(structuredLoanCalculationResource.getDownPaymentCharges())));
		
		
		response.setNetFixedInstallmentWithoutFactoredValues(calculateNetRental(calculateLoanCalculationValueWithoutFactoredValues(new BigDecimal(structuredLoanCalculationResource.getLoanAmount()),
				new BigDecimal(structuredLoanCalculationResource.getPaidAmountInAdvance()),
				new BigDecimal(structuredLoanCalculationResource.getTaxesInAdvance()),
				new BigDecimal(structuredLoanCalculationResource.getTotalTaxes()),
				new BigDecimal(structuredLoanCalculationResource.getTaxesForSchedule()),
				new BigDecimal(structuredLoanCalculationResource.getDownPaymentCharges()),
				new BigDecimal(structuredLoanCalculationResource.getScheduleCharges()),
				Integer.parseInt(structuredLoanCalculationResource.getPeriod())), structuredLoanCalculationResource));
		
		response.setFactoredValuesInInstallment(calculateFactoredValuesInInstallment(response.getNetInstallment(), response.getNetFixedInstallmentWithoutFactoredValues()));
		
		response.setIrr(tcCommonCalcualtionService.calculateIrrStructredInstallments(response.getLoanCalculationValue().subtract(response.getPrepaidInstallments()), generateScheduleStructure(structuredLoanCalculationResource,response),
				Integer.parseInt(structuredLoanCalculationResource.getPaymentFreq())));
		
		response.setRepaymentSchedule(generateScheduleDetails(structuredLoanCalculationResource, response));
		return response;
		
	}
	
	private BigDecimal calculateTotalDownpayment(BigDecimal paidAmountInAdvance , BigDecimal downPaymentCharges, int noOfPrePayments 
			, List<RepaymentStructureResource> rentalStructure, BigDecimal netInstallment) {
		
		BigDecimal totalDownpayment = paidAmountInAdvance.add(downPaymentCharges);
		
		totalDownpayment = totalDownpayment.add(calculatePrepaidInstallments(noOfPrePayments,rentalStructure,netInstallment));
		
		return totalDownpayment;
	}
	
	private BigDecimal calculatePrepaidInstallments(int noOfPrePayments 
			, List<RepaymentStructureResource> rentalStructure, BigDecimal netInstallment) {
		
		BigDecimal prepaymentAmount =  BigDecimal.ZERO;		
		if(noOfPrePayments > 0) {
			if(!rentalStructure.isEmpty()) {
				for(RepaymentStructureResource rentalStructureResource: rentalStructure) {
					
					if(rentalStructureResource.getAmount()!= null && !rentalStructureResource.getAmount().isEmpty()) {
						
						if(Integer.parseInt(rentalStructureResource.getPeriod()) >= noOfPrePayments) {
							prepaymentAmount = prepaymentAmount.add(new BigDecimal(rentalStructureResource.getAmount()).multiply(new BigDecimal(noOfPrePayments)));
							break;
						}else {
							prepaymentAmount = prepaymentAmount.add(new BigDecimal(rentalStructureResource.getAmount()).multiply(new BigDecimal(rentalStructureResource.getPeriod())));
							noOfPrePayments = noOfPrePayments - Integer.parseInt(rentalStructureResource.getPeriod());
						}
					}else {
						if(Integer.parseInt(rentalStructureResource.getPeriod()) >= noOfPrePayments) {
							prepaymentAmount = prepaymentAmount.add(netInstallment.multiply(new BigDecimal(noOfPrePayments)));
							break;
						}else {
							prepaymentAmount = prepaymentAmount.add(netInstallment).multiply(new BigDecimal(rentalStructureResource.getPeriod()));
							noOfPrePayments = noOfPrePayments - Integer.parseInt(rentalStructureResource.getPeriod());
						}
					}
			}
									
			}else {
				prepaymentAmount = prepaymentAmount.add(netInstallment.multiply(new BigDecimal(noOfPrePayments)));
			}
		}
		
		return prepaymentAmount;
	}
	
	private BigDecimal calculateFactoredValuesInInstallment(BigDecimal netInstallment , BigDecimal netFixedInstallmentWithoutFactoredValues) {
		BigDecimal factoredValuesInInstallment = netInstallment.subtract(netFixedInstallmentWithoutFactoredValues);
		return factoredValuesInInstallment;
	}
	
	private BigDecimal calculateTotalReceivablewithTaxAndCharges(BigDecimal grossInstallment, int fixedInstallmentPeriod, List<RepaymentStructureResource> rentalStructure, 
			BigDecimal paidAmountInAdvance, BigDecimal totalTaxes, BigDecimal downPaymentCharges) {
		
		BigDecimal totalReceivable = tcCommonCalcualtionService.calculateTotalReceivableWithCapitalAndInterest(grossInstallment, fixedInstallmentPeriod);
		
		if(rentalStructure != null && !rentalStructure.isEmpty()) {	
		
			for(RepaymentStructureResource rentalStructureResource : rentalStructure) {
				if(rentalStructureResource.getAmount() != null && !rentalStructureResource.getAmount().isEmpty()) {
					totalReceivable = totalReceivable.add(new BigDecimal(rentalStructureResource.getAmount()).multiply(new BigDecimal(rentalStructureResource.getPeriod())));
				}
			}		
			
		}
		totalReceivable = totalReceivable.add(paidAmountInAdvance)
				.add(totalTaxes)
				.add(downPaymentCharges);
		
		return totalReceivable;
	}
	
	private BigDecimal calculateTotalInterest(BigDecimal totalReceivable, BigDecimal loanCalculationValue) {
		
		BigDecimal totalInterest = totalReceivable.subtract(loanCalculationValue);
		return totalInterest;
	}
	
	private BigDecimal calculateTotalReceivable(BigDecimal netInstallment, int fixedInstallmentPeriod, List<RepaymentStructureResource> rentalStructure) {
		BigDecimal totalReceivable = tcCommonCalcualtionService.calculateTotalReceivableWithCapitalAndInterest(netInstallment, fixedInstallmentPeriod);
		
		if(rentalStructure != null && !rentalStructure.isEmpty()) {	
		
			for(RepaymentStructureResource rentalStructureResource : rentalStructure) {
				if(rentalStructureResource.getAmount() != null && !rentalStructureResource.getAmount().isEmpty()) {
					totalReceivable = totalReceivable.add(new BigDecimal(rentalStructureResource.getAmount()).multiply(new BigDecimal(rentalStructureResource.getPeriod())));
				}
			}		
			
		}
		return totalReceivable;
	}
	
	private int calculateFixedInstallmentPeriod(List<RepaymentStructureResource> rentalStructure , int period) {
		
		if(rentalStructure != null && !rentalStructure.isEmpty()) {	
			int fixedInstallmentCount = 0;
			int structuredRentalCount = 0;
			for(RepaymentStructureResource rentalStructureResource : rentalStructure) {
				if(rentalStructureResource.getAmount() == null || rentalStructureResource.getAmount().isEmpty()) {
					fixedInstallmentCount = fixedInstallmentCount + Integer.parseInt(rentalStructureResource.getPeriod());
				}else {
					structuredRentalCount = structuredRentalCount + Integer.parseInt(rentalStructureResource.getPeriod());
				}
			}
			fixedInstallmentCount = fixedInstallmentCount + (period - structuredRentalCount);
			return fixedInstallmentCount;
			
		}else {
			return period;
		}
	}
	
	private BigDecimal calculateLoanCalculationValue(BigDecimal loanAmount, BigDecimal paidAmountInAdvance,BigDecimal taxesInAdvance,
			BigDecimal totalTaxes, BigDecimal taxesForSchedule, BigDecimal totalCharges, BigDecimal downPaymentCharges , BigDecimal scheduleCharges, int period) {
			
		BigDecimal loanCalculationValue = loanAmount
						.subtract(paidAmountInAdvance)
						.add(tcCommonCalcualtionService.calculateTotalTax(taxesInAdvance, totalTaxes, taxesForSchedule,period))						
						.add(tcCommonCalcualtionService.calculateTotalCharges(totalCharges, downPaymentCharges, scheduleCharges, period));
				
		return loanCalculationValue;
	}
	private BigDecimal calculateLoanCalculationValueWithoutFactoredValues(BigDecimal loanAmount, BigDecimal paidAmountInAdvance,BigDecimal taxesInAdvance,
			BigDecimal totalTaxes, BigDecimal taxesForSchedule, BigDecimal downPaymentCharges , BigDecimal scheduleCharges, int period) {
		
		BigDecimal loanCalculationValueWithoutFactoredValues = calculateLoanCalculationValue( loanAmount,  paidAmountInAdvance, taxesInAdvance,
				 totalTaxes,  taxesForSchedule, BigDecimal.ZERO,  downPaymentCharges ,  scheduleCharges,  period);				
		return loanCalculationValueWithoutFactoredValues;
	}
	
	private BigDecimal calculateNetRental(BigDecimal loanCalculationValue, StructuredLoanCalculationResource structuredLoanCalculationResource) throws NumberFormatException, Exception {
		
		int installmentNo =0;
		BigDecimal fixedInstallmentPVFactor = BigDecimal.ZERO;
		BigDecimal structuredRentalNPV = BigDecimal.ZERO;
		
		if(structuredLoanCalculationResource.getRentalStructure() != null && !structuredLoanCalculationResource.getRentalStructure().isEmpty()) {		
			// in advance
			if(structuredLoanCalculationResource.getRepaymentCriteria().equals(RepaymentCriteriaEnum.IN_ADVANCE.toString())){		
				for(RepaymentStructureResource rentalStructureResource : structuredLoanCalculationResource.getRentalStructure()) {
					
					//structured rental
					if(rentalStructureResource.getAmount() != null && !rentalStructureResource.getAmount().isEmpty()) {
						
						BigDecimal structuredRentalPVFactor = pvFactorForInAdvance((installmentNo+1) ,
								installmentNo +Integer.parseInt(rentalStructureResource.getPeriod()),  Integer.parseInt(structuredLoanCalculationResource.getPaymentFreq()) , 
								new BigDecimal(structuredLoanCalculationResource.getAnnualInterestRate()));
						
						structuredRentalNPV = structuredRentalNPV.add(npvCalculation(new BigDecimal(rentalStructureResource.getAmount()), structuredRentalPVFactor));
					
					// fixed Installment
					}else {
						fixedInstallmentPVFactor = fixedInstallmentPVFactor.add(pvFactorForInAdvance((installmentNo+1) , 
								installmentNo + Integer.parseInt(rentalStructureResource.getPeriod()),  Integer.parseInt(structuredLoanCalculationResource.getPaymentFreq()) ,
								new BigDecimal(structuredLoanCalculationResource.getAnnualInterestRate())));
					}

					installmentNo = installmentNo + Integer.parseInt(rentalStructureResource.getPeriod());
					
					if((installmentNo + Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments())) > Integer.parseInt(structuredLoanCalculationResource.getPeriod())){
						throw new ValidateRecordException(environment.getProperty("invalid-period-count"), "message");
					}
				}
				
				
			}							
			// in arrears	
			else {
				for(RepaymentStructureResource rentalStructureResource : structuredLoanCalculationResource.getRentalStructure()) {
					
					//structured rental
					if(rentalStructureResource.getAmount() != null && !rentalStructureResource.getAmount().isEmpty()) {
						
						BigDecimal structuredRentalPVFactor = pvFactorForInArrears((installmentNo+1) ,
								installmentNo +Integer.parseInt(rentalStructureResource.getPeriod()),  Integer.parseInt(structuredLoanCalculationResource.getPaymentFreq()) , 
								new BigDecimal(structuredLoanCalculationResource.getAnnualInterestRate()));
						
						structuredRentalNPV = structuredRentalNPV.add(npvCalculation(new BigDecimal(rentalStructureResource.getAmount()), structuredRentalPVFactor));
					
					// fixed Installment
					}else {
						fixedInstallmentPVFactor = fixedInstallmentPVFactor.add(pvFactorForInArrears((installmentNo+1) , 
								installmentNo +Integer.parseInt(rentalStructureResource.getPeriod()),  Integer.parseInt(structuredLoanCalculationResource.getPaymentFreq()) ,
								new BigDecimal(structuredLoanCalculationResource.getAnnualInterestRate())));
					}

					installmentNo = installmentNo + Integer.parseInt(rentalStructureResource.getPeriod());
					
					if((installmentNo + Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments()))> Integer.parseInt(structuredLoanCalculationResource.getPeriod())){
						throw new ValidateRecordException(environment.getProperty("invalid-period-count"), "message");
					}
				}
			}
		}	
			
		
			// calculate PVFactor for remaining fixed installments
			if(installmentNo < Integer.parseInt(structuredLoanCalculationResource.getPeriod()) ) {
				// in advance
				if(structuredLoanCalculationResource.getRepaymentCriteria().equals(RepaymentCriteriaEnum.IN_ADVANCE.toString())) {
					fixedInstallmentPVFactor = fixedInstallmentPVFactor.add(pvFactorForInAdvance((installmentNo+1) , 
							Integer.parseInt(structuredLoanCalculationResource.getPeriod()),
							Integer.parseInt(structuredLoanCalculationResource.getPaymentFreq()) ,
							new BigDecimal(structuredLoanCalculationResource.getAnnualInterestRate())));
					
				}
				// in arrears	
				else {
					fixedInstallmentPVFactor = fixedInstallmentPVFactor.add(pvFactorForInArrears((installmentNo+1) , 
							Integer.parseInt(structuredLoanCalculationResource.getPeriod()),
							Integer.parseInt(structuredLoanCalculationResource.getPaymentFreq()) ,
							new BigDecimal(structuredLoanCalculationResource.getAnnualInterestRate())));
				}
				
			}
			
			return netRental(loanCalculationValue, 
					structuredRentalNPV, new BigDecimal(structuredLoanCalculationResource.getPaidAmountInAdvance()),  Integer.parseInt(structuredLoanCalculationResource.getPaymentFreq()) , 
					new BigDecimal(structuredLoanCalculationResource.getAnnualInterestRate()), fixedInstallmentPVFactor);
	}

	@Override
	public StructuredLoanCalculationResponseResource addTcStructuredDetail(String tenantId,
			TcStructuredDetailAddResource tcStructuredDetailAddResource)
			throws NumberFormatException, Exception {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
		
		TcStructuredDetail tcStructuredDetail = new TcStructuredDetail();
		tcStructuredDetail.setTenantId(tenantId);
		tcStructuredDetail.setStatus(CommonStatus.ACTIVE);
		tcStructuredDetail.setCreatedDate(validationService.getCreateOrModifyDate());
		tcStructuredDetail.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		tcStructuredDetail.setSyncTs(validationService.getSyncTs());
		tcStructuredDetail = tcStructuredDetailRepository.saveAndFlush(tcStructuredDetail);
		
		if(tcStructuredDetailAddResource.getRepaymentStructure() != null && !tcStructuredDetailAddResource.getRepaymentStructure().isEmpty()) {		
			for(RepaymentStructureResource rentalStructureResource : tcStructuredDetailAddResource.getRepaymentStructure()) {
				
				TcStructuredPayment tcStructuredPayment = new TcStructuredPayment();
				tcStructuredPayment.setTenantId(tenantId);
				tcStructuredPayment.setTcStructuredDetail(tcStructuredDetail);
				tcStructuredPayment.setSequence(Long.parseLong(rentalStructureResource.getSequence()));
				tcStructuredPayment.setNoOfPeriod(Long.parseLong(rentalStructureResource.getPeriod()));
				tcStructuredPayment.setAmount(new BigDecimal(rentalStructureResource.getAmount()));
				tcStructuredPayment.setStatus(CommonStatus.ACTIVE);
				tcStructuredPayment.setCreatedDate(validationService.getCreateOrModifyDate());
				tcStructuredPayment.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				tcStructuredPayment.setSyncTs(validationService.getSyncTs());
				tcStructuredPaymentRepository.saveAndFlush(tcStructuredPayment);
			}
			
		}
		
		
		Currency currency = validationService.validateCurrency(tenantId, tcStructuredDetailAddResource.getCurrencyId(), tcStructuredDetailAddResource.getCurrencyName(), EntityPoint.TC_STRUCTURED_CAL);
		
        Product product = validateProduct(validationService.stringToLong(tcStructuredDetailAddResource.getProductId()), tcStructuredDetailAddResource.getProductName());
		
		SubProduct subProduct = validateSubProduct(validationService.stringToLong(tcStructuredDetailAddResource.getSubProductId()), tcStructuredDetailAddResource.getSubProductName());
		
		RepaymentFrequency repaymentFrequency = validateRepaymentFrequency(validationService.stringToLong(tcStructuredDetailAddResource.getRepaymentFrequencyId()), tcStructuredDetailAddResource.getRepaymentFrequencyName());
		
		StructuredLoanCalculationResponseResource structuredLoanCalculationResponse  = structuredLoanCalculatorCaller(tenantId , tcStructuredDetailAddResource);
		
		// Save Schedule Details
		List<TcStructuredPaymentSchedule> tcStructuredPaymentScheduleList = new ArrayList<>();
		
		// Save factored charges
		tcStructuredPaymentScheduleList.addAll(saveScheduleCharges(tenantId,tcStructuredDetail, Long.valueOf(0),
				tcStructuredDetailAddResource, "AFFC"));
		
		// Save down payment charges
		tcStructuredPaymentScheduleList.addAll(saveScheduleCharges(tenantId,tcStructuredDetail, Long.valueOf(0),
				tcStructuredDetailAddResource, "AFUP"));	
		
		for(TcStructurePaymentScheduleResponse tcStructurePaymentSchedule : structuredLoanCalculationResponse.getRepaymentSchedule()) {

			TcStructuredPaymentSchedule tcStructuredPaymentScheduleCapital = new TcStructuredPaymentSchedule();
			tcStructuredPaymentScheduleCapital.setTenantId(tenantId);
			tcStructuredPaymentScheduleCapital.setTransactionTypeCode("Capital");
			tcStructuredPaymentScheduleCapital.setTcStructuredDetail(tcStructuredDetail);
			tcStructuredPaymentScheduleCapital.setSequence(Long.valueOf(tcStructurePaymentSchedule.getPeriod()));
			tcStructuredPaymentScheduleCapital.setAmount(tcStructurePaymentSchedule.getCapitalPortion());
			tcStructuredPaymentScheduleCapital.setStatus(CommonStatus.ACTIVE);
			tcStructuredPaymentScheduleCapital.setCreatedDate(validationService.getCreateOrModifyDate());
			tcStructuredPaymentScheduleCapital.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			tcStructuredPaymentScheduleCapital.setSyncTs(validationService.getSyncTs());
			tcStructuredPaymentScheduleList.add(tcStructuredPaymentScheduleCapital);
			
			if(tcStructurePaymentSchedule.getPeriod() != 0) {
		
				TcStructuredPaymentSchedule tcStructuredPaymentScheduleInterest = new TcStructuredPaymentSchedule();
				tcStructuredPaymentScheduleInterest.setTenantId(tenantId);
				tcStructuredPaymentScheduleInterest.setTransactionTypeCode("Interest");
				tcStructuredPaymentScheduleInterest.setTcStructuredDetail(tcStructuredDetail);
				tcStructuredPaymentScheduleInterest.setSequence(Long.valueOf(tcStructurePaymentSchedule.getPeriod()));
				tcStructuredPaymentScheduleInterest.setAmount(tcStructurePaymentSchedule.getInterestPortion());
				tcStructuredPaymentScheduleInterest.setStatus(CommonStatus.ACTIVE);
				tcStructuredPaymentScheduleInterest.setCreatedDate(validationService.getCreateOrModifyDate());
				tcStructuredPaymentScheduleInterest.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				tcStructuredPaymentScheduleInterest.setSyncTs(validationService.getSyncTs());
				tcStructuredPaymentScheduleList.add(tcStructuredPaymentScheduleInterest);
				
				// Save Schedule charges
				tcStructuredPaymentScheduleList.addAll(saveScheduleCharges(tenantId,tcStructuredDetail, Long.valueOf(tcStructurePaymentSchedule.getPeriod()),
						tcStructuredDetailAddResource, "AFIM"));
				
				
			}
			

		}

		tcStructuredPaymentScheduleRepository.saveAll(tcStructuredPaymentScheduleList);

		
		TcHeader tcHeader = new TcHeader();
		tcHeader.setTenantId(tenantId);
		tcHeader.setProduct(product);
		tcHeader.setSubProduct(subProduct);
		tcHeader.setRepaymentFrequency(repaymentFrequency);
		tcHeader.setCurrencyId(validationService.stringToLong(tcStructuredDetailAddResource.getCurrencyId()));
		tcHeader.setLeadId(validationService.stringToLong(tcStructuredDetailAddResource.getLeadId()));
		tcHeader.setCurrencyCode(currency.getCurrencyCode());
		tcHeader.setCurrencyCodeNumeric(currency.getCodeNumeric());
		tcHeader.setRepaymentCriteria(RepaymentCriteriaEnum.valueOf(tcStructuredDetailAddResource.getRepaymentCriteria()));
		tcHeader.setTcExpiryDate(validationService.getCreateOrModifyDate());
		
		//should remove this block after altering the table
		tcHeader.setDueDate(1l);
		tcHeader.setGracePeriod(1l);
		tcHeader.setPenalInterestRate(new BigDecimal("0.00"));
		
		tcHeader.setTerm(validationService.stringToLong(tcStructuredDetailAddResource.getTerm()));
		tcHeader.setCalculationMethod(CalculateMethodEnum.valueOf(tcStructuredDetailAddResource.getCalculationMethodName()));
		tcHeader.setInterestRate(validationService.stringToBigDecimal(tcStructuredDetailAddResource.getInterestRate()));
		tcHeader.setLoanAmount(validationService.stringToBigDecimal(tcStructuredDetailAddResource.getLoanAmount()));
		tcHeader.setInterestRate(validationService.stringToBigDecimal(tcStructuredDetailAddResource.getInterestRate()));
		tcHeader.setRewards(tcStructuredDetailAddResource.getRewardFlag() != null && !tcStructuredDetailAddResource.getRewardFlag().isEmpty() ? 
				YesNo.valueOf(tcStructuredDetailAddResource.getRewardFlag()):null);
		tcHeader.setRemarks(tcStructuredDetailAddResource.getRemarks());
		tcHeader.setTcStructuredDetail(tcStructuredDetail);
		
		tcHeader.setNoOfPrePayments(tcStructuredDetailAddResource.getNumberOfPrepayments() != null && !tcStructuredDetailAddResource.getNumberOfPrepayments().isEmpty() ? 
				Long.parseLong(tcStructuredDetailAddResource.getNumberOfPrepayments()):null);
		
		tcHeader.setAmountPaidInAdvance(tcStructuredDetailAddResource.getAmountPaidInAdvance() != null && !tcStructuredDetailAddResource.getAmountPaidInAdvance().isEmpty() ? 
				new BigDecimal(tcStructuredDetailAddResource.getAmountPaidInAdvance()):null);
		
		tcHeader.setResidualValue(tcStructuredDetailAddResource.getResidualValue() != null && !tcStructuredDetailAddResource.getResidualValue().isEmpty() ? 
				new BigDecimal(tcStructuredDetailAddResource.getResidualValue()):null);
		
		tcHeader.setTotalReceivable(structuredLoanCalculationResponse.getTotalReceivableWithChargesAndTaxes());
		tcHeader.setDownPaymentAmount(structuredLoanCalculationResponse.getTotalDownpayment());
		tcHeader.setIrr(structuredLoanCalculationResponse.getIrr());
		
		tcHeader.setCreatedDate(validationService.getCreateOrModifyDate()); 
		tcHeader.setSyncTs(validationService.getCreateOrModifyDate());
		tcHeader.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		tcHeader = tcHeaderRepository.saveAndFlush(tcHeader);
		
		
		//create fixed charges
		if(tcStructuredDetailAddResource.getFixedChargesDetails() != null && !tcStructuredDetailAddResource.getFixedChargesDetails().isEmpty()) {
			
			for(FixedChargesResource item : tcStructuredDetailAddResource.getFixedChargesDetails()) {
				
				FeeCharge feeCharge = validateFeeCharge(validationService.stringToLong(item.getChargeTypeId()), item.getChargeTypeName());
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				
				TcFixedCharges tcFixedCharges = new TcFixedCharges();
				tcFixedCharges.setTenantId(tenantId);
//				tcFixedCharges.setFeeCharge(feeCharge);
				tcFixedCharges.setCriteria(applicationFrequency);
				tcFixedCharges.setTcHeader(tcHeader);
				tcFixedCharges.setRateOrAmount(RateOrAmount.valueOf(item.getCalculationMethod()));
				tcFixedCharges.setRateAmount(validationService.stringToBigDecimal((item.getRateOrAmount())));
				tcFixedCharges.setTotalChargeAmount(validationService.stringToBigDecimal((item.getTotalChargeAmount())));
				tcFixedCharges.setStatus(CommonStatus.valueOf(item.getStatus()));
				tcFixedCharges.setCreatedDate(validationService.getCreateOrModifyDate());
				tcFixedCharges.setSyncTs(validationService.getCreateOrModifyDate());
				tcFixedCharges.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				tcFixedChargesRepository.save(tcFixedCharges);
			}
		}
		
		//create optional charges
		if(tcStructuredDetailAddResource.getOptionalChargesDetails() != null && !tcStructuredDetailAddResource.getOptionalChargesDetails().isEmpty()) {
			
			for(OptionalChargesResource item : tcStructuredDetailAddResource.getOptionalChargesDetails()) {
				
                FeeCharge feeCharge = validateFeeCharge(validationService.stringToLong(item.getChargeTypeId()), item.getChargeTypeName());
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				
				if(item.getOptionalChargesList() != null && !item.getOptionalChargesList().isEmpty()) {
					
					for(OptionalChargesListResource itm : item.getOptionalChargesList()) {
						
						TcOptionsCharges tcOptionsCharges = new TcOptionsCharges();
						tcOptionsCharges.setTenantId(tenantId);
//						tcOptionsCharges.setFeeCharge(feeCharge);
						tcOptionsCharges.setCriteria(applicationFrequency);
						tcOptionsCharges.setRateAmount(validationService.stringToBigDecimal((itm.getRateAmount())));
						tcOptionsCharges.setRateOrAmount(RateOrAmount.valueOf(itm.getCalculationMethod()));
						tcOptionsCharges.setTotalChargeAmount(validationService.stringToBigDecimal((itm.getTotalChargeAmount())));
						tcOptionsCharges.setOption(itm.getOption());
						tcOptionsCharges.setTcHeader(tcHeader);
						tcOptionsCharges.setStatus(CommonStatus.valueOf(item.getStatus()));
						tcOptionsCharges.setCreatedDate(validationService.getCreateOrModifyDate());
						tcOptionsCharges.setSyncTs(validationService.getCreateOrModifyDate());
						tcOptionsCharges.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
						tcOptionsChargesRepository.save(tcOptionsCharges);
					}
				}
				
				
			}
			
		}
		
		//create adhoc charges
		if(tcStructuredDetailAddResource.getAdhocChargesDetails() != null && !tcStructuredDetailAddResource.getAdhocChargesDetails().isEmpty()) {
			
           for(AdhocChargesResource item : tcStructuredDetailAddResource.getAdhocChargesDetails()) {
				
                FeeCharge feeCharge = validateFeeCharge(validationService.stringToLong(item.getChargeTypeId()), item.getChargeTypeName());
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				
				FrequencyResponse frequencyResponse = validationService.validateFrequency(tenantId, validationService.stringToLong(item.getFrequencyId()), item.getFrequencyName(), validationService.stringToLong(item.getUnit()), EntityPoint.TC_REVOLVING_CALCULATION);
				
				if(item.getAdhocChargesList() != null && !item.getAdhocChargesList().isEmpty()) {
					
					for(AdhocChargesListResource itm : item.getAdhocChargesList()) {
						
						TcAdhocCharges tcAdhocCharges = new TcAdhocCharges();
						tcAdhocCharges.setTenantId(tenantId);
//						tcAdhocCharges.setFeeCharge(feeCharge);
						tcAdhocCharges.setCriteria(applicationFrequency);
						tcAdhocCharges.setPeriod(itm.getPeriod());
						tcAdhocCharges.setTcHeader(tcHeader);
						tcAdhocCharges.setFrequencyId(validationService.stringToLong(item.getFrequencyId()));
						tcAdhocCharges.setFrequencyCode(frequencyResponse.getCode());
						tcAdhocCharges.setRateAmount(validationService.stringToBigDecimal((itm.getRateAmount())));
						tcAdhocCharges.setRateOrAmount(RateOrAmount.valueOf(itm.getCalculationMethod()));
						tcAdhocCharges.setTotalChargeAmount(validationService.stringToBigDecimal((itm.getTotalChargeAmount())));
						tcAdhocCharges.setStatus(CommonStatus.valueOf(item.getStatus()));
						tcAdhocCharges.setCreatedDate(validationService.getCreateOrModifyDate());
						tcAdhocCharges.setSyncTs(validationService.getCreateOrModifyDate());
						tcAdhocCharges.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
						tcAdhocChargesRepository.save(tcAdhocCharges);
					}
				}
				
				
			}
		}
		return structuredLoanCalculationResponse;
	}
	
    public Product validateProduct(Long id, String name) {
		
		Optional<Product> product = productRepository.findByIdAndProductNameAndStatus(id, name, CommonStatus.ACTIVE);
		if (!product.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(INVALID_ID), ServiceEntity.PRODUCT_ID, EntityPoint.TC_STRUCTURED_CAL);
		}
		
		return product.get();
	}
	
	public SubProduct validateSubProduct(Long id, String name) {
		
		Optional<SubProduct> subProduct = subProductRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
		if (!subProduct.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(INVALID_ID), ServiceEntity.SUB_PRODUCT_ID, EntityPoint.TC_STRUCTURED_CAL);
		}
		
		return subProduct.get();
	}
	
	private RepaymentFrequency validateRepaymentFrequency(Long id, String name) {
		
		Optional<RepaymentFrequency> repaymentFrequency = repaymentFrequencyRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		if (!repaymentFrequency.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(INVALID_ID), ServiceEntity.REPAYMENT_FREQUENCY_ID, EntityPoint.TC_STRUCTURED_CAL);
		}
		
		return repaymentFrequency.get();
	}
	
	private FeeCharge validateFeeCharge(Long id, String name) {
		
		Optional<FeeCharge> feeCharge = feeChargeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		if (!feeCharge.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(INVALID_ID), ServiceEntity.FEE_CHARGE_ID, EntityPoint.TC_STRUCTURED_CAL);
		}
		
		return feeCharge.get();
	}
	
    private ApplicationFrequency validateApplicationFrequency(Long id, String name) {
		
		Optional<ApplicationFrequency> applicationFrequency = applicationFrequencyRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		if (!applicationFrequency.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(INVALID_ID), ServiceEntity.CRITERIA_ID, EntityPoint.TC_STRUCTURED_CAL);
		}
		
		return applicationFrequency.get();
	}
    
    private BigDecimal getTcCharges(List<FixedChargesResource> fixedChargesResource , 
    		List<OptionalChargesResource> optionalChargesResource, List<AdhocChargesResource> adhocChargesResource, String applicationFreqCode) {
    	
    	BigDecimal totalCharge = BigDecimal.ZERO;
    	
    	if(fixedChargesResource != null && !fixedChargesResource.isEmpty()){
    		for(FixedChargesResource item : fixedChargesResource) {
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				if(applicationFrequency.getCode().equals(applicationFreqCode)) {
					totalCharge = totalCharge.add(new BigDecimal (item.getTotalChargeAmount()));
				}
				
				
    		}
    	}
    	
    	if(optionalChargesResource != null && !optionalChargesResource.isEmpty()){
    		for(OptionalChargesResource item : optionalChargesResource) {
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				if(applicationFrequency.getCode().equals(applicationFreqCode)) {
					
					if(item.getOptionalChargesList() != null && !item.getOptionalChargesList().isEmpty()) {
						for(OptionalChargesListResource optionalCharge : item.getOptionalChargesList()) {
							totalCharge = totalCharge.add(new BigDecimal (optionalCharge.getTotalChargeAmount()));
						}
					}
				}
				
				
    		}
    	}
    	
    	if(adhocChargesResource != null && !adhocChargesResource.isEmpty()){
    		for(AdhocChargesResource item : adhocChargesResource) {
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				if(applicationFrequency.getCode().equals(applicationFreqCode)) {
					
					if(item.getAdhocChargesList() != null && !item.getAdhocChargesList().isEmpty()) {
						for(AdhocChargesListResource adhocCharge : item.getAdhocChargesList()) {
							totalCharge = totalCharge.add(new BigDecimal (adhocCharge.getTotalChargeAmount()));
						}
					}
				}
				
				
    		}
    	}
    	
    	return totalCharge;
	
	}
    
    @Override
    public StructuredLoanCalculationResponseResource structuredLoanCalculatorCaller(String tenantId , TcStructuredDetailAddResource tcStructuredDetailAddResource) throws NumberFormatException, Exception {
    	StructuredLoanCalculationResource structuredLoanCalculationResource = new StructuredLoanCalculationResource();
    	
    	BigDecimal totalCharges = getTcCharges(tcStructuredDetailAddResource.getFixedChargesDetails() ,
				tcStructuredDetailAddResource.getOptionalChargesDetails(),
				tcStructuredDetailAddResource.getAdhocChargesDetails(),
				"AFFC");
		BigDecimal downPaymentCharges = getTcCharges(tcStructuredDetailAddResource.getFixedChargesDetails() ,
				tcStructuredDetailAddResource.getOptionalChargesDetails(),
				tcStructuredDetailAddResource.getAdhocChargesDetails(),
				"AFUP");
		BigDecimal scheduleCharges = getTcCharges(tcStructuredDetailAddResource.getFixedChargesDetails() ,
				tcStructuredDetailAddResource.getOptionalChargesDetails(),
				tcStructuredDetailAddResource.getAdhocChargesDetails(),
				"AFIM");
		
		structuredLoanCalculationResource.setLoanAmount(tcStructuredDetailAddResource.getLoanAmount()); 
		
		structuredLoanCalculationResource.setPaidAmountInAdvance(tcStructuredDetailAddResource.getAmountPaidInAdvance()!= null && 
				!tcStructuredDetailAddResource.getAmountPaidInAdvance().isEmpty() ? tcStructuredDetailAddResource.getAmountPaidInAdvance(): "0.00");  
		
		structuredLoanCalculationResource.setTaxesInAdvance("0.00"); 
		
		structuredLoanCalculationResource.setTotalTaxes("0.00");  
		
		structuredLoanCalculationResource.setTotalCharges(totalCharges.toString()); 
		
		structuredLoanCalculationResource.setDownPaymentCharges(downPaymentCharges.toString());
		
		structuredLoanCalculationResource.setScheduleCharges(scheduleCharges.toString());
		
		structuredLoanCalculationResource.setTaxesForSchedule("0.00");
		
		structuredLoanCalculationResource.setPeriod(tcStructuredDetailAddResource.getTerm());
		
		RepaymentFrequency repaymentFrequency = validateRepaymentFrequency(validationService.stringToLong(tcStructuredDetailAddResource.getRepaymentFrequencyId()), tcStructuredDetailAddResource.getRepaymentFrequencyName());
		structuredLoanCalculationResource.setPaymentFreq(repaymentFrequency.getOccurrencePerYear().toString());
		
		structuredLoanCalculationResource.setAnnualInterestRate(tcStructuredDetailAddResource.getInterestRate());
		
		structuredLoanCalculationResource.setResidualValue(tcStructuredDetailAddResource.getResidualValue()!= null && 
				!tcStructuredDetailAddResource.getResidualValue().isEmpty() ? tcStructuredDetailAddResource.getResidualValue(): "0.00");
		
		structuredLoanCalculationResource.setRepaymentCriteria(tcStructuredDetailAddResource.getRepaymentCriteria());
		
		structuredLoanCalculationResource.setNumberOfPrepayments(tcStructuredDetailAddResource.getNumberOfPrepayments()!= null && 
				!tcStructuredDetailAddResource.getNumberOfPrepayments().isEmpty() ? tcStructuredDetailAddResource.getNumberOfPrepayments(): "0");
		
		structuredLoanCalculationResource.setRentalStructure(tcStructuredDetailAddResource.getRepaymentStructure());
		
		return structuredLoanCalculator(tenantId,structuredLoanCalculationResource);
    }
    
    private List<TcStructurePaymentScheduleResource> generateScheduleStructure(StructuredLoanCalculationResource structuredLoanCalResource,
    		StructuredLoanCalculationResponseResource response){
    	
    	StructuredLoanCalculationResource structuredLoanCalculationResource = structuredLoanCalResource;
    	List<TcStructurePaymentScheduleResource> scheduleList = new ArrayList<>();
    	int period = 1; 	
    		
    	// repayment structure schedule remove prepayments
			if(structuredLoanCalculationResource.getRentalStructure() != null && !structuredLoanCalculationResource.getRentalStructure().isEmpty()) {  
				int noOfPrepayment = Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments());
    			
				for(RepaymentStructureResource rentalStructureResource : structuredLoanCalculationResource.getRentalStructure()) {
    					int newPeriod = Integer.parseInt(rentalStructureResource.getPeriod()) - noOfPrepayment;
    					if(newPeriod > 0) {
    						rentalStructureResource.setPeriod(String.valueOf(newPeriod));
    						break;
    					}else {
    						rentalStructureResource.setPeriod(String.valueOf(0));
        					noOfPrepayment = newPeriod  * (-1);
    					}
    					
    					
    			}
				// repayment structure schedule
    			for(RepaymentStructureResource rentalStructureResource : structuredLoanCalculationResource.getRentalStructure()) {
    				
    				for(int i= 0; i< Integer.parseInt(rentalStructureResource.getPeriod()); i++) {
	    				if(period > response.getTotalPeriod()) {
							throw new ValidateRecordException(environment.getProperty("invalid-period-count"), "message");
						}
	    				
	    				TcStructurePaymentScheduleResource tcStructurePaymentSchedule = new TcStructurePaymentScheduleResource();
						tcStructurePaymentSchedule.setPeriod(period);
	    				
						//structured rental
						if(rentalStructureResource.getAmount() != null && !rentalStructureResource.getAmount().isEmpty()) {
						
							tcStructurePaymentSchedule.setInstallment(new BigDecimal(rentalStructureResource.getAmount()));
						}
						//fixed installment
						else {
							tcStructurePaymentSchedule.setInstallment(response.getNetInstallment());
							
						}
						scheduleList.add(tcStructurePaymentSchedule);
						period++;		
    				}
					
    			}
    			
    		}
			
			// remaining fixed installment schedule
    		if((period + Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments()))  < response.getTotalPeriod()) {  		
    			int lastInstallmentNo = response.getTotalPeriod() - Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments());
    		
    			for(int i = period; i <= lastInstallmentNo; i++) {
    				TcStructurePaymentScheduleResource tcStructurePaymentSchedule = new TcStructurePaymentScheduleResource();
					tcStructurePaymentSchedule.setPeriod(i);				
    				tcStructurePaymentSchedule.setInstallment(response.getNetInstallment());
					scheduleList.add(tcStructurePaymentSchedule);
    			}
    			
    		}
    
    	
		return scheduleList;
    
    }
    
    private List<TcStructurePaymentScheduleResponse> generateScheduleDetails(StructuredLoanCalculationResource structuredLoanCalculationResource, StructuredLoanCalculationResponseResource response ) {
    	
    	List<TcStructurePaymentScheduleResponse> scheduleList = new ArrayList<>();
    	int period = 1;
		BigDecimal capitalBalance = response.getLoanCalculationValue();
		BigDecimal irr = (response.getIrr().divide(new BigDecimal(structuredLoanCalculationResource.getPaymentFreq()),ROUND_OFF_SCALE ,RoundingMode.HALF_EVEN )).divide(new BigDecimal("100"));
		BigDecimal totalReceivable = response.getTotalReceivableWithoutChargesAndTaxes();
			//prePayments
	    	if(Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments()) > 0) {
	    		
	    		for(int i = 0; i < Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments()); i++) {
    				TcStructurePaymentScheduleResponse tcStructurePaymentSchedule = new TcStructurePaymentScheduleResponse();
					tcStructurePaymentSchedule.setPeriod(0);
					tcStructurePaymentSchedule.setCharges(BigDecimal.ZERO);
					tcStructurePaymentSchedule.setTaxes(BigDecimal.ZERO);
					tcStructurePaymentSchedule.setInterestPortion(BigDecimal.ZERO);
					tcStructurePaymentSchedule.setGrossInstallment(BigDecimal.ZERO);
					
    				tcStructurePaymentSchedule.setInstallment(calculatePrepaidInstallments(1,structuredLoanCalculationResource.getRentalStructure(),response.getNetInstallment() ));
				
					BigDecimal capitalPortion = tcStructurePaymentSchedule.getInstallment();
					capitalBalance = capitalBalance.subtract(capitalPortion);
					
					tcStructurePaymentSchedule.setCapitalPortion(capitalPortion);
					tcStructurePaymentSchedule.setCapitalBalance(capitalBalance);
					tcStructurePaymentSchedule.setTotalReceivable(totalReceivable);
					
					scheduleList.add(tcStructurePaymentSchedule);
					
					totalReceivable = totalReceivable.subtract(tcStructurePaymentSchedule.getInstallment());
	    		
	    		}
	    	}
    	
    		// repayment structure schedule
			if(structuredLoanCalculationResource.getRentalStructure() != null && !structuredLoanCalculationResource.getRentalStructure().isEmpty()) {  
    			for(RepaymentStructureResource rentalStructureResource : structuredLoanCalculationResource.getRentalStructure()) {
    				
    				for(int i= 0; i < Integer.parseInt(rentalStructureResource.getPeriod()); i++) {
	    				if(period > response.getTotalPeriod()) {
							throw new ValidateRecordException(environment.getProperty("invalid-period-count"), "message");
						}
	    				
	    				TcStructurePaymentScheduleResponse tcStructurePaymentSchedule = new TcStructurePaymentScheduleResponse();
						tcStructurePaymentSchedule.setPeriod(period);
						tcStructurePaymentSchedule.setCharges(response.getChargesPerInstallment());
						tcStructurePaymentSchedule.setTaxes(response.getTaxesPerInstallment());
	    				
						//structured rental
						if(rentalStructureResource.getAmount() != null && !rentalStructureResource.getAmount().isEmpty()) {
						
							tcStructurePaymentSchedule.setInstallment(new BigDecimal(rentalStructureResource.getAmount()));
						}
						//fixed installment
						else {
							tcStructurePaymentSchedule.setInstallment(response.getNetInstallment());
							
						}
						BigDecimal interestPortion = capitalBalance.multiply(irr);
						interestPortion = interestPortion.setScale(2, RoundingMode.HALF_EVEN);
						BigDecimal capitalPortion = tcStructurePaymentSchedule.getInstallment().subtract(interestPortion);
						capitalBalance = capitalBalance.subtract(capitalPortion);
						BigDecimal grossInstallment = interestPortion.add(tcStructurePaymentSchedule.getCharges()).add(tcStructurePaymentSchedule.getTaxes());
						
						tcStructurePaymentSchedule.setInterestPortion(interestPortion);
						tcStructurePaymentSchedule.setCapitalPortion(capitalPortion.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO :capitalPortion);
						tcStructurePaymentSchedule.setCapitalBalance(capitalBalance);
						tcStructurePaymentSchedule.setTotalReceivable(totalReceivable);
						tcStructurePaymentSchedule.setGrossInstallment(grossInstallment);
						
						scheduleList.add(tcStructurePaymentSchedule);

						totalReceivable = totalReceivable.subtract(tcStructurePaymentSchedule.getInstallment());
						period++;		
    				}
					
    			}
    			
    		}
			
			// remaining fixed installment schedule
    		if((period + Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments()))  < response.getTotalPeriod()) {  		
    			int lastInstallmentNo = response.getTotalPeriod() - Integer.parseInt(structuredLoanCalculationResource.getNumberOfPrepayments());
    		
    			for(int i = period; i <= lastInstallmentNo; i++) {
    				TcStructurePaymentScheduleResponse tcStructurePaymentSchedule = new TcStructurePaymentScheduleResponse();
					tcStructurePaymentSchedule.setPeriod(i);
					tcStructurePaymentSchedule.setCharges(response.getChargesPerInstallment());
					tcStructurePaymentSchedule.setTaxes(response.getTaxesPerInstallment());
    				tcStructurePaymentSchedule.setInstallment(response.getNetInstallment());
				
					BigDecimal interestPortion = capitalBalance.multiply(irr);
					interestPortion = interestPortion.setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal capitalPortion = tcStructurePaymentSchedule.getInstallment().subtract(interestPortion);
					capitalBalance = capitalBalance.subtract(capitalPortion);
					BigDecimal grossInstallment = interestPortion.add(tcStructurePaymentSchedule.getCharges()).add(tcStructurePaymentSchedule.getTaxes());
					
					tcStructurePaymentSchedule.setInterestPortion(interestPortion);
					tcStructurePaymentSchedule.setCapitalPortion(capitalPortion.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO :capitalPortion);
					tcStructurePaymentSchedule.setCapitalBalance(capitalBalance);
					tcStructurePaymentSchedule.setTotalReceivable(totalReceivable);
					tcStructurePaymentSchedule.setGrossInstallment(grossInstallment);
					
					scheduleList.add(tcStructurePaymentSchedule);
					totalReceivable = totalReceivable.subtract(tcStructurePaymentSchedule.getInstallment());
    			}
    			
    		}
    
    	
		return scheduleList;
    
    	
    }
    
    private List<TcStructuredPaymentSchedule> saveScheduleCharges(String tenantId,TcStructuredDetail tcStructuredDetail, Long period, TcStructuredDetailAddResource tcStructuredDetailAddResource, String applicationFreqCode) {
		
    	List<TcStructuredPaymentSchedule> tcStructuredPaymentScheduleList = new ArrayList<>();
    	//fixed charges
		if(tcStructuredDetailAddResource.getFixedChargesDetails() != null && !tcStructuredDetailAddResource.getFixedChargesDetails().isEmpty()) {
			
			for(FixedChargesResource item : tcStructuredDetailAddResource.getFixedChargesDetails()) {
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				
				if(applicationFrequency.getCode().equals(applicationFreqCode)) {
					
					TcStructuredPaymentSchedule tcStructuredPaymentScheduleCharge = new TcStructuredPaymentSchedule();
					tcStructuredPaymentScheduleCharge.setTenantId(tenantId);
					tcStructuredPaymentScheduleCharge.setTransactionTypeCode("fixed charge1");
					tcStructuredPaymentScheduleCharge.setTcStructuredDetail(tcStructuredDetail);
					tcStructuredPaymentScheduleCharge.setSequence(period);
					tcStructuredPaymentScheduleCharge.setAmount(new BigDecimal(item.getTotalChargeAmount()));
					tcStructuredPaymentScheduleCharge.setStatus(CommonStatus.ACTIVE);
					tcStructuredPaymentScheduleCharge.setCreatedDate(validationService.getCreateOrModifyDate());
					tcStructuredPaymentScheduleCharge.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					tcStructuredPaymentScheduleCharge.setSyncTs(validationService.getSyncTs());
					tcStructuredPaymentScheduleList.add(tcStructuredPaymentScheduleCharge);
				}
				
			}
		}
		
		//optional charges 
		if(tcStructuredDetailAddResource.getOptionalChargesDetails() != null && !tcStructuredDetailAddResource.getOptionalChargesDetails().isEmpty()) {
			
			for(OptionalChargesResource item : tcStructuredDetailAddResource.getOptionalChargesDetails()) {
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
				
				if(applicationFrequency.getCode().equals(applicationFreqCode)) {
					
					if(item.getOptionalChargesList() != null && !item.getOptionalChargesList().isEmpty()) {
						
						for(OptionalChargesListResource itm : item.getOptionalChargesList()) {
					
							TcStructuredPaymentSchedule tcStructuredPaymentScheduleCharge = new TcStructuredPaymentSchedule();
							tcStructuredPaymentScheduleCharge.setTenantId(tenantId);
							tcStructuredPaymentScheduleCharge.setTransactionTypeCode("optional charge");
							tcStructuredPaymentScheduleCharge.setTcStructuredDetail(tcStructuredDetail);
							tcStructuredPaymentScheduleCharge.setSequence(period);
							tcStructuredPaymentScheduleCharge.setAmount(new BigDecimal(itm.getTotalChargeAmount()));
							tcStructuredPaymentScheduleCharge.setStatus(CommonStatus.ACTIVE);
							tcStructuredPaymentScheduleCharge.setCreatedDate(validationService.getCreateOrModifyDate());
							tcStructuredPaymentScheduleCharge.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
							tcStructuredPaymentScheduleCharge.setSyncTs(validationService.getSyncTs());
							tcStructuredPaymentScheduleList.add(tcStructuredPaymentScheduleCharge);
						}
					}
				}
				
			}
		}
		
		//adhoc charges 
		if(tcStructuredDetailAddResource.getAdhocChargesDetails() != null && !tcStructuredDetailAddResource.getAdhocChargesDetails().isEmpty()) {
			
			for(AdhocChargesResource item : tcStructuredDetailAddResource.getAdhocChargesDetails()) {
						
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName());
					
				if(applicationFrequency.getCode().equals(applicationFreqCode)) {
					
				if(item.getAdhocChargesList() != null && !item.getAdhocChargesList().isEmpty()) {
						
					for(AdhocChargesListResource itm : item.getAdhocChargesList()) {
							
					
							TcStructuredPaymentSchedule tcStructuredPaymentScheduleCharge = new TcStructuredPaymentSchedule();
							tcStructuredPaymentScheduleCharge.setTenantId(tenantId);
							tcStructuredPaymentScheduleCharge.setTransactionTypeCode("adhoc charge");
							tcStructuredPaymentScheduleCharge.setTcStructuredDetail(tcStructuredDetail);
							tcStructuredPaymentScheduleCharge.setSequence(period);
							tcStructuredPaymentScheduleCharge.setAmount(new BigDecimal(itm.getTotalChargeAmount()));
							tcStructuredPaymentScheduleCharge.setStatus(CommonStatus.ACTIVE);
							tcStructuredPaymentScheduleCharge.setCreatedDate(validationService.getCreateOrModifyDate());
							tcStructuredPaymentScheduleCharge.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
							tcStructuredPaymentScheduleCharge.setSyncTs(validationService.getSyncTs());
							tcStructuredPaymentScheduleList.add(tcStructuredPaymentScheduleCharge);
						}
					}
				}
				
			}
		}
		
		return tcStructuredPaymentScheduleList;
    }
    
    @Override
	public Optional<TcHeader> findById(Long id) {
		
		Optional<TcHeader> isPresentTcHeader = tcHeaderRepository.findById(id);
		
		if(isPresentTcHeader.isPresent()) {
			return Optional.of(setTcHeaderDetails(isPresentTcHeader.get()));
		}else {
			return Optional.empty();
		}
		
	}


	@Override
	public List<TcHeader> findByLeadId(Long leadId) {
		List<TcHeader> tcHeaderList = tcHeaderRepository.findByLeadId(leadId);
		
		if(!tcHeaderList.isEmpty()) {
			for(TcHeader tcHeader :tcHeaderList) {
				setTcHeaderDetails(tcHeader);
			}
		}
		
		return tcHeaderList;
		
	}
	
	private TcHeader setTcHeaderDetails(TcHeader tcHeader) {
		
		if(tcHeader.getTcStructuredDetail() != null) {
			
		List<TcStructuredPayment> tcStructuredPayment = tcStructuredPaymentRepository.findByTcStructuredDetailId(tcHeader.getTcStructuredDetail().getId());
		tcHeader.setTcStructuredPayment(tcStructuredPayment);
		
		List<TcStructuredPaymentSchedule> tcStructuredPaymentSchedule = tcStructuredPaymentScheduleRepository.findByTcStructuredDetailId(tcHeader.getTcStructuredDetail().getId());
		tcHeader.setTcStructuredPaymentSchedule(tcStructuredPaymentSchedule);
		
		}
		
		List<TcFixedCharges> tcFixedCharges = tcFixedChargesRepository.findByTcHeaderId(tcHeader.getId());
		List<TcOptionsCharges> tcOptionsCharges = tcOptionsChargesRepository.findByTcHeaderId(tcHeader.getId());
		List<TcAdhocCharges> tcAdhocCharges = tcAdhocChargesRepository.findByTcHeaderId(tcHeader.getId());
		
		tcHeader.setFixedChargesDetails(tcFixedCharges);
		tcHeader.setOptionsChargesDetails(tcOptionsCharges);
		tcHeader.setAdhocChargesDetails(tcAdhocCharges);
		
		return tcHeader;
	}

}
