package com.fusionx.lending.product.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeDetailAdhoc;
import com.fusionx.lending.product.domain.FeeChargeDetailOptional;
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.domain.TcAdhocCharges;
import com.fusionx.lending.product.domain.TcAmortizationDetail;
import com.fusionx.lending.product.domain.TcAmortizationPaymentSchedule;
import com.fusionx.lending.product.domain.TcFixedCharges;
import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.domain.TcOptionsCharges;
import com.fusionx.lending.product.domain.TcRevolvingDetail;
import com.fusionx.lending.product.domain.TcRevolvingPaymentSchedule;
import com.fusionx.lending.product.domain.TcTaxDetail;
import com.fusionx.lending.product.enums.ApplicationFrequencyEnum;
import com.fusionx.lending.product.enums.CalculateMethodEnum;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.RateOrAmount;
import com.fusionx.lending.product.enums.RepaymentCriteriaEnum;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ListRecordPrimitiveValidateException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ApplicationFrequencyRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailAdhocRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailsRepository;
import com.fusionx.lending.product.repository.FeeChargeRepository;
import com.fusionx.lending.product.repository.OtherFeeTypeRepository;
import com.fusionx.lending.product.repository.ProductRepository;
import com.fusionx.lending.product.repository.RepaymentFrequencyRepository;
import com.fusionx.lending.product.repository.SubProductRepository;
import com.fusionx.lending.product.repository.TcAdhocChargesRepository;
import com.fusionx.lending.product.repository.TcAmortizationDetailRepository;
import com.fusionx.lending.product.repository.TcAmortizationPaymentScheduleRepository;
import com.fusionx.lending.product.repository.TcFixedChargesRepository;
import com.fusionx.lending.product.repository.TcHeaderRepository;
import com.fusionx.lending.product.repository.TcOptionsChargesRepository;
import com.fusionx.lending.product.repository.TcRevolvingDetailRepository;
import com.fusionx.lending.product.repository.TcTaxDetailRepository;
import com.fusionx.lending.product.resources.AdhocChargesListResource;
import com.fusionx.lending.product.resources.AdhocChargesResource;
import com.fusionx.lending.product.resources.BankTransactionSubCodeResponse;
import com.fusionx.lending.product.resources.Currency;
import com.fusionx.lending.product.resources.FixedChargesResource;
import com.fusionx.lending.product.resources.FixedInstallmentLoanCalculationDetailsResponse;
import com.fusionx.lending.product.resources.FixedInstallmentPaymentScheduleResponse;
import com.fusionx.lending.product.resources.FixedLoanCalculatorAddResource;
import com.fusionx.lending.product.resources.FrequencyResponse;
import com.fusionx.lending.product.resources.ListRecordValidateResource;
import com.fusionx.lending.product.resources.OptionalChargesListResource;
import com.fusionx.lending.product.resources.OptionalChargesResource;
import com.fusionx.lending.product.resources.RevolvingLoanCalculatorAddResource;
import com.fusionx.lending.product.resources.TaxCalculationResource;
import com.fusionx.lending.product.resources.TaxDetailsResponse;
import com.fusionx.lending.product.service.FixedLoanCalculatorService;
import com.fusionx.lending.product.service.RemoteService;
import com.fusionx.lending.product.service.StructuredLoanCalculationService;
import com.fusionx.lending.product.service.TaxCalculationService;
import com.fusionx.lending.product.service.TcCommonCalcualtionService;
import com.fusionx.lending.product.service.ValidationService;


/**
 * API Service Fixed Installment Calculation.
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
@Transactional(rollbackFor = Exception.class)
public class FixedLoanCalculatorServiceImpl extends MessagePropertyBase  implements FixedLoanCalculatorService{


	private TcCommonCalcualtionService tcCommonCalcualtionService;
	
	private ValidationService validationService;
	
	private TaxCalculationService taxCalculationService;
	
	private StructuredLoanCalculationService structuredLoanCalculationService;
	
    @Autowired
    public void setTcCommonCalcualtionService(TcCommonCalcualtionService tcCommonCalcualtionService) {
        this.tcCommonCalcualtionService = tcCommonCalcualtionService;
    }	
	
    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }
    
    @Autowired
    public void setTaxCalculationService(TaxCalculationService taxCalculationService) {
        this.taxCalculationService = taxCalculationService;
    }
    
    @Autowired
    public void setStructuredLoanCalculationServicee(StructuredLoanCalculationService structuredLoanCalculationService) {
        this.structuredLoanCalculationService = structuredLoanCalculationService;
    }
    
    
	
	@Autowired
	private FeeChargeDetailsRepository feeChargeDetailRepository;
	
	@Autowired
	private FeeChargeDetailAdhocRepository feeChargeDetailAdhocRepository;
	
	@Autowired
	private FeeChargeDetailOptionalRepository feeChargeDetailOptionalRepository;
	
	@Autowired
	private ApplicationFrequencyRepository applicationFrequencyRepository;
	
	@Autowired
	private TcAdhocChargesRepository tcAdhocChargesRepository;	
	
	@Autowired
	private TcFixedChargesRepository tcFixedChargesRepository;
	
	@Autowired
	private TcOptionsChargesRepository tcOptionsChargesRepository;
	
	@Autowired
	private RepaymentFrequencyRepository repaymentFrequencyRepository;
	
	@Autowired
	private TcAmortizationDetailRepository tcAmortizationDetailRepository;
	
	@Autowired
	private SubProductRepository subProductRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private TcHeaderRepository tcHeaderRepository;	
	
    @Autowired
    private OtherFeeTypeRepository otherFeeTypeRepository;
	
	@Autowired
	private TcAmortizationPaymentScheduleRepository tcAmortizationPaymentScheduleRepository;
	
	@Autowired
	private TcTaxDetailRepository tcTaxDetailRepository;
	

	
	private static final int ROUND_OFF_2_DECIMAL_POINTS = 2;
	private static final int ROUND_OFF_8_DECIMAL_POINTS = 8;
	
	@Override
	public FixedInstallmentLoanCalculationDetailsResponse calculateDetails(String tenantId,FixedLoanCalculatorAddResource request) {
		
		FixedInstallmentLoanCalculationDetailsResponse calculateDetailsResponse = calculateDetailsResponse( tenantId,request);
		
		FixedInstallmentLoanCalculationDetailsResponse roundOffResponse = roundOffResponse(calculateDetailsResponse);
		
		return roundOffResponse;		
		
	}
	
	
	public FixedInstallmentLoanCalculationDetailsResponse calculateDetailsResponse(String tenantId,FixedLoanCalculatorAddResource request) {
		
		
		BigDecimal netloanAmount = validationService.stringToBigDecimal(request.getLoanAmount());
		int repaymentFrequency = validRepaymentFrequency(validationService.stringToLong(request.getRepaymentFrequencyId()) , request.getRepaymentFrequencyName()).getOccurrencePerYear().intValue();
		BigDecimal interestRate = validationService.stringToBigDecimal(request.getAnnualInterestRate());		
		BigDecimal residualValue = validationService.stringToBigDecimal(request.getResidualValue());
		int noOfPrePayments = Integer.parseInt(request.getNoOfPrePayments());		
		BigDecimal amountPaidInAdvance = validationService.stringToBigDecimal(request.getAmountPaidInAdvance());//B3
		BigDecimal taxesToBePaidInAdvance = BigDecimal.ZERO;//B5
		int loanPeriod = Integer.parseInt(request.getTerm());//B12
		
		if((netloanAmount).compareTo(amountPaidInAdvance) < 1) {
			throw new ValidateRecordException(environment.getProperty("tc-calculation-amountPaidInAdvance.greaterThan.loanAmount"), MESSAGE);			
		}
		if (noOfPrePayments > loanPeriod) {
			throw new ValidateRecordException(environment.getProperty("tc-noOfPrePayments-loanPeriod.valid"), MESSAGE);	
		}		
		if (noOfPrePayments > 0) {
			if(!("IN_ADVANCE").equals(request.getRepaymentCriteria())){
				throw new ValidateRecordException(environment.getProperty("tc-repayment-criteria.inAdvance"), MESSAGE);	
			}
		} else {
			if(!("IN_ARRERS").equals(request.getRepaymentCriteria())){
				throw new ValidateRecordException(environment.getProperty("tc-repayment-criteria.inArrers"), MESSAGE);	
			}
		}
		
		Product product = validateProduct(validationService.stringToLong(request.getProductId()),request.getProductName());
		
		SubProduct subProduct = validateSubProduct(validationService.stringToLong(request.getSubProductId()), request.getSubProductName() , product.getId());
		
		
		BigDecimal tax = BigDecimal.ZERO;//B6	
		
		BigDecimal charge =BigDecimal.ZERO;//B7	
		BigDecimal periodicFactoredCharges = calculatePeriodicCharges( ApplicationFrequencyEnum.AFFC.toString(), request.getAdhocChargesDetails(),  noOfPrePayments ,  interestRate ,  repaymentFrequency , netloanAmount);
		BigDecimal otherFactoredcharges = calculateCharges(ApplicationFrequencyEnum.AFFC.toString(),netloanAmount, request.getFixedChargesDetails() , request.getOptionalChargesDetails());	
		charge = otherFactoredcharges.add(periodicFactoredCharges);
		
		BigDecimal chargesToBeCollectAtDownPayment = BigDecimal.ZERO;//B8	
		BigDecimal periodicChargesCollectAtDownPayment = calculatePeriodicCharges( ApplicationFrequencyEnum.AFUP.toString(), request.getAdhocChargesDetails(),  noOfPrePayments ,  interestRate ,  repaymentFrequency ,netloanAmount);
		BigDecimal otherChargesCollectAtDownPayment = calculateCharges(ApplicationFrequencyEnum.AFUP.toString(),netloanAmount, request.getFixedChargesDetails() , request.getOptionalChargesDetails());	
		chargesToBeCollectAtDownPayment =  otherChargesCollectAtDownPayment.add(periodicChargesCollectAtDownPayment);
		
		BigDecimal chargesCollectThroughOutPeriod = BigDecimal.ZERO;//B9	
		BigDecimal otherChargesCollectThroughOutPeriod = calculateCharges(ApplicationFrequencyEnum.AFIM.toString(),netloanAmount, request.getFixedChargesDetails() , request.getOptionalChargesDetails());
		BigDecimal periodicChargesCollectThroughOutPeriod = calculatePeriodicCharges( ApplicationFrequencyEnum.AFIM.toString(), request.getAdhocChargesDetails(),  noOfPrePayments ,  interestRate ,  repaymentFrequency , netloanAmount);		
		chargesCollectThroughOutPeriod =  otherChargesCollectThroughOutPeriod.add(periodicChargesCollectThroughOutPeriod);
		



				
		BigDecimal loanAmount = loanCalculationValue(netloanAmount,charge,tax,amountPaidInAdvance);
				
		BigDecimal fixedInstallment = tcCommonCalcualtionService.calculateFixedInstallment(loanAmount, loanPeriod, repaymentFrequency , interestRate, residualValue, noOfPrePayments);
		
		List<TaxDetailsResponse> taxDetailsList = getTaxDetailsList(tenantId , request ,fixedInstallment); 
		
		BigDecimal taxesCollectThroughOutPeriod = calculateTaxTotalsBasedOnApplicationFrequency(taxDetailsList,ApplicationFrequencyEnum.AFIM.toString());
		
		BigDecimal totalTaxes  =tcCommonCalcualtionService.calculateTotalTax(taxesToBePaidInAdvance,tax ,taxesCollectThroughOutPeriod ,loanPeriod);//B26
		BigDecimal totalGrossInstallment = tcCommonCalcualtionService.calculateTotalGrossInstallment(fixedInstallment, chargesCollectThroughOutPeriod, taxesCollectThroughOutPeriod);
		
		BigDecimal totalDownPayment =tcCommonCalcualtionService.calculateTotalDownPayment(totalGrossInstallment,noOfPrePayments,chargesToBeCollectAtDownPayment,amountPaidInAdvance);
		
		BigDecimal prePaidInstallment  =tcCommonCalcualtionService.calculatePrePaidInstallment(totalGrossInstallment,noOfPrePayments);
		
		BigDecimal totalReceivableWithChargesAndTaxes = tcCommonCalcualtionService.calculateTotalReceivableWithChargesAndTaxes(totalGrossInstallment, loanPeriod, chargesToBeCollectAtDownPayment, amountPaidInAdvance);		
		
		BigDecimal totalCharges  = tcCommonCalcualtionService.calculateTotalCharges(charge, chargesToBeCollectAtDownPayment, chargesCollectThroughOutPeriod, loanPeriod);//B27
		
		BigDecimal loanCalculationValue = loanAmount;
		
		BigDecimal totalReceivableWithCapitalAndInterest = tcCommonCalcualtionService.calculateTotalReceivableWithCapitalAndInterest(fixedInstallment, loanPeriod);
		
		BigDecimal totalInterest = tcCommonCalcualtionService.calculateTotalInterest(loanAmount, totalReceivableWithCapitalAndInterest);

		BigDecimal netFixedInstallmentWithOutFactoredValues = tcCommonCalcualtionService.calculateNetFixedInstallmentWithOutFactoredValues(netloanAmount, loanPeriod, repaymentFrequency, interestRate, residualValue, noOfPrePayments);
		
		BigDecimal factoredValueInstallmet = tcCommonCalcualtionService.calculateFactoredValueInstallmet(charge, tax, loanPeriod, repaymentFrequency, interestRate, residualValue, noOfPrePayments);
		
		BigDecimal loanFactor = tcCommonCalcualtionService.calculateLoanFactor(netloanAmount, netFixedInstallmentWithOutFactoredValues);
		BigDecimal totalFactor = tcCommonCalcualtionService.calculateTotalFactor(loanAmount ,fixedInstallment);		
		BigDecimal chargeFactor = BigDecimal.ZERO;
		
		
		if((charge.add(tax)).compareTo(BigDecimal.ZERO)>0) {
				chargeFactor = tcCommonCalcualtionService.calculateChargeFactor(charge, tax, factoredValueInstallmet);
		}
		
		
		fixedInstallment = fixedInstallment.setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP);
		
		BigDecimal irr = tcCommonCalcualtionService.calculateIRRforFixedInstallments(loanAmount, fixedInstallment, loanPeriod, repaymentFrequency ,noOfPrePayments);
		irr = irr.setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP);
		
		
		

		FixedInstallmentLoanCalculationDetailsResponse response = new FixedInstallmentLoanCalculationDetailsResponse();
		response.setFixedInstallment(fixedInstallment);
		response.setChargesCollectThroughOutPeriod(chargesCollectThroughOutPeriod);
		response.setTaxesCollectThroughOutPeriod(taxesCollectThroughOutPeriod);
		response.setTotalGrossInstallment(totalGrossInstallment);
		response.setTotalDownPayment(totalDownPayment);
		response.setPrePaidInstallment(prePaidInstallment);
		response.setTotalTaxes(totalTaxes);
		response.setTotalCharges(totalCharges);
		response.setLoanCalculationValue(loanCalculationValue);
		response.setTotalInterest(totalInterest);
		response.setTotalReceivableWithCapitalAndInterest(totalReceivableWithCapitalAndInterest);
		response.setTotalReceivableWithChargesAndTaxes(totalReceivableWithChargesAndTaxes);
		response.setNetFixedInstallmentWithOutFactoredValues(netFixedInstallmentWithOutFactoredValues);
		response.setFactoredValueInstallmet(factoredValueInstallmet);
		response.setLoanFactor(loanFactor);
		response.setChargeFactor(chargeFactor);
		response.setTotalFactor(totalFactor);
		response.setIrr(irr);		
		
		response.setCharge(charge);
		response.setChargesToBeCollectAtDownPayment(chargesToBeCollectAtDownPayment);
		
		response.setTaxDetailsList(taxDetailsList);
		return response;
		
	}
	
	
	public FixedInstallmentLoanCalculationDetailsResponse roundOffResponse(FixedInstallmentLoanCalculationDetailsResponse response) {
		
		response.setFixedInstallment(response.getFixedInstallment().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setChargesCollectThroughOutPeriod(response.getChargesCollectThroughOutPeriod().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setTaxesCollectThroughOutPeriod(response.getTaxesCollectThroughOutPeriod().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setTotalGrossInstallment(response.getTotalGrossInstallment().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setTotalDownPayment(response.getTotalDownPayment().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setPrePaidInstallment(response.getPrePaidInstallment().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setTotalTaxes(response.getTotalTaxes().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setTotalCharges(response.getTotalCharges().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setLoanCalculationValue(response.getLoanCalculationValue().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setTotalInterest(response.getTotalInterest().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setTotalReceivableWithCapitalAndInterest(response.getTotalReceivableWithCapitalAndInterest().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setTotalReceivableWithChargesAndTaxes(response.getTotalReceivableWithChargesAndTaxes().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setNetFixedInstallmentWithOutFactoredValues(response.getNetFixedInstallmentWithOutFactoredValues().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setFactoredValueInstallmet(response.getFactoredValueInstallmet().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setLoanFactor(response.getLoanFactor().setScale(ROUND_OFF_8_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setChargeFactor(response.getChargeFactor().setScale(ROUND_OFF_8_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setTotalFactor(response.getTotalFactor().setScale(ROUND_OFF_8_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setIrr(response.getIrr().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));		
		response.setCharge(response.getCharge().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		response.setChargesToBeCollectAtDownPayment(response.getChargesToBeCollectAtDownPayment().setScale(ROUND_OFF_2_DECIMAL_POINTS, RoundingMode.HALF_UP));
		
		return response;
	}
	
	
	
	@Override
	public List<String>  paymentScheduleTemp(String tenantId ,FixedLoanCalculatorAddResource request) {		
		
		BigDecimal netloanAmount = validationService.stringToBigDecimal(request.getLoanAmount());
		int repaymentFrequency = validRepaymentFrequency(validationService.stringToLong(request.getRepaymentFrequencyId()) , request.getRepaymentFrequencyName()).getOccurrencePerYear().intValue();
		int noOfPrePayments = Integer.parseInt(request.getNoOfPrePayments());
		int loanPeriod = Integer.parseInt(request.getTerm());
		BigDecimal amountPaidInAdvance = validationService.stringToBigDecimal(request.getAmountPaidInAdvance());
		BigDecimal tax = BigDecimal.ZERO;
		
		FixedInstallmentLoanCalculationDetailsResponse calculateDetailsResponse = calculateDetails(tenantId,request);

		
		BigDecimal loanAmount = loanCalculationValue(netloanAmount,calculateDetailsResponse.getCharge(),tax,amountPaidInAdvance);


		BigDecimal loanCalculationValue = loanAmount;
		
		List<String> paymentSchedule = tcCommonCalcualtionService.paymentScheduleTemp( loanCalculationValue.subtract((calculateDetailsResponse.getFixedInstallment()).multiply(BigDecimal.valueOf(noOfPrePayments))) ,  loanPeriod-noOfPrePayments , calculateDetailsResponse.getIrr(),  repaymentFrequency , calculateDetailsResponse.getFixedInstallment() );

		
		return paymentSchedule;		
	}
	
	
	public List<FixedInstallmentPaymentScheduleResponse> paymentSchedule(String tenantId ,FixedLoanCalculatorAddResource request) {
		
		
		BigDecimal netloanAmount = validationService.stringToBigDecimal(request.getLoanAmount());
		int repaymentFrequency = validRepaymentFrequency(validationService.stringToLong(request.getRepaymentFrequencyId()) , request.getRepaymentFrequencyName()).getOccurrencePerYear().intValue();
		int noOfPrePayments = Integer.parseInt(request.getNoOfPrePayments());
		int loanPeriod = Integer.parseInt(request.getTerm());
		BigDecimal amountPaidInAdvance = validationService.stringToBigDecimal(request.getAmountPaidInAdvance());
		BigDecimal tax = BigDecimal.ZERO;
		
		FixedInstallmentLoanCalculationDetailsResponse calculateDetailsResponse = calculateDetails(tenantId,request);

		
		BigDecimal loanAmount = loanCalculationValue(netloanAmount,calculateDetailsResponse.getCharge(),tax,amountPaidInAdvance);


		BigDecimal loanCalculationValue = loanAmount;
		
		BigDecimal disbursementDeductionCollectAtDownPayment = calculateCharges(ApplicationFrequencyEnum.AFDD.toString(),netloanAmount, request.getFixedChargesDetails() , request.getOptionalChargesDetails());//B8
		BigDecimal chargesToBeCollectAtDownPayment = calculateDetailsResponse.getChargesToBeCollectAtDownPayment();
		BigDecimal chargesCollectThroughOutPeriod = calculateDetailsResponse.getChargesCollectThroughOutPeriod();	
		
		BigDecimal taxesCollectThroughOutPeriod = calculateDetailsResponse.getChargesCollectThroughOutPeriod();	
		
		BigDecimal fixedInstallment = calculateDetailsResponse.getFixedInstallment();
		
		BigDecimal totalGrossInstallment =  calculateDetailsResponse.getTotalGrossInstallment();
		
		List<FixedInstallmentPaymentScheduleResponse> paymentSchedule =  new ArrayList<>();
		
		FixedInstallmentPaymentScheduleResponse initialPayment = new FixedInstallmentPaymentScheduleResponse();
		
		BigDecimal totalInitialPayment = (totalGrossInstallment.multiply(BigDecimal.valueOf(noOfPrePayments)) )
				.add(chargesToBeCollectAtDownPayment);
		
		initialPayment.setSequence(0l);
		initialPayment.setCapitalBalance(loanCalculationValue);
		initialPayment.setCapitalPortion(totalInitialPayment);
		initialPayment.setNetFixedInstallment(fixedInstallment);
		initialPayment.setInterestPortion(BigDecimal.ZERO);
		initialPayment.setChargesCollectThroughOutPeriod(chargesCollectThroughOutPeriod);
		initialPayment.setTaxesCollectThroughOutPeriod(taxesCollectThroughOutPeriod);
		initialPayment.setTotalGrossInstallment(totalGrossInstallment);
		
		paymentSchedule.add(initialPayment);
		
		paymentSchedule.addAll(tcCommonCalcualtionService.paymentSchedule( loanCalculationValue.subtract((calculateDetailsResponse.getFixedInstallment()).multiply(BigDecimal.valueOf(noOfPrePayments))) ,  loanPeriod-noOfPrePayments , calculateDetailsResponse.getIrr(),  repaymentFrequency , fixedInstallment
				,chargesCollectThroughOutPeriod , taxesCollectThroughOutPeriod , totalGrossInstallment ));
		
		return paymentSchedule;

	}
	
	
	public BigDecimal loanCalculationValue(BigDecimal netLoanAmount,BigDecimal totalCharges,BigDecimal totalTaxes,BigDecimal amountPaidInAdvance) {
		
		BigDecimal loanCalculationValue = netLoanAmount.add(totalCharges).add(totalTaxes).subtract(amountPaidInAdvance);
		return loanCalculationValue;
	}
	
	
	public BigDecimal calculateCharges( String addCriteria , BigDecimal loanAmount, List<FixedChargesResource> fixedChargesList,List<OptionalChargesResource> optionalChargesList ) {
		
		BigDecimal totalCharge  = BigDecimal.ZERO;
		

//				AFFC – Factored
//		
//				AFUP – Upfront / Downpay 
//
//				AFDD – Deduct from disbursement 
//
//				AFIM –Instalment 

		String root = "fixedLoanCalculatorAddResource";
		List<Long> fixedChargesAdded = new ArrayList<>();
		List<Long> optionalChargesAdded = new ArrayList<>();
		List<Long> adhocChargesAdded = new ArrayList<>();

		if((fixedChargesList != null) && (!fixedChargesList.isEmpty())){
			
			int level1 = 0;
			String listName = "fixedChargesList";
			
			for (FixedChargesResource resource : fixedChargesList) {

				String levelOne = "." + listName + "[" + level1 + "]";
				
				if(fixedChargesAdded.contains(validationService.stringToLong((resource.getChargeTypeId())))) {					
					throw new ListRecordPrimitiveValidateException("duplicate charge type ", root+levelOne  ,listName,level1,"chargeTypeId");
				}				
				fixedChargesAdded.add(validationService.stringToLong((resource.getChargeTypeId())));

				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(resource.getCriteriaId()), resource.getCriteriaName(),root+levelOne, listName, level1);
				
				if ((addCriteria).equals(applicationFrequency.getCode())) {
					if ((RateOrAmount.AMOUNT.toString()).equals(resource.getCalculationMethod())) {

						totalCharge = totalCharge.add(validationService.stringToBigDecimal(resource.getRateOrAmount()));

					} else {
						BigDecimal rate = validationService.stringToBigDecimal(resource.getRateOrAmount());
						
						
						if((BigDecimal.ZERO).compareTo(rate) < 0) {
							throw new ListRecordPrimitiveValidateException("rate should be greater than 0",
									root+levelOne, listName, level1, "rate");
						}
						
						BigDecimal totalAmount = validationService.stringToBigDecimal(resource.getTotalChargeAmount());
						BigDecimal chargeAmount = loanAmount.multiply(rate.divide(BigDecimal.valueOf(100.00)));

						if (totalAmount.compareTo(chargeAmount) != 0) {
							throw new ListRecordPrimitiveValidateException("amount incompatible",
									root+levelOne, listName, level1, "totalChargeAmount");
						}

						totalCharge = totalCharge.add(chargeAmount);
					}

				}
				
				level1++;
			}
		}
		
		
		if ((optionalChargesList != null) && (!optionalChargesList.isEmpty())) {

			int level1 = 0;
			String listName = "fixedChargesList";
			for (OptionalChargesResource resource : optionalChargesList) {

				String levelOne = "." + listName + "[" + level1 + "]";
				
				if(optionalChargesAdded.contains(validationService.stringToLong((resource.getChargeTypeId())))) {					
					throw new ListRecordPrimitiveValidateException("duplicate charge type ", root+levelOne  ,listName,level1,"chargeTypeId");
				}				
				optionalChargesAdded.add(validationService.stringToLong((resource.getChargeTypeId())));
				
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(resource.getCriteriaId()), resource.getCriteriaName(),root+levelOne, listName, level1);

				if ((addCriteria).equals(applicationFrequency.getCode())) {

						int level2 = 0;
						listName = "optionalChargesList";
						for (OptionalChargesListResource childResource : resource.getOptionalChargesList()) {

							String levelTwo = "." + listName + "[" + level2 + "]";

							if ((RateOrAmount.AMOUNT.toString()).equals(childResource.getCalculationMethod())) {
								totalCharge = totalCharge.add(validationService.stringToBigDecimal(childResource.getRateAmount()));
							} else {
								BigDecimal rate = validationService.stringToBigDecimal(childResource.getRateAmount());
								BigDecimal totalAmount = validationService.stringToBigDecimal(childResource.getTotalChargeAmount());
								
								
								if((BigDecimal.ZERO).compareTo(rate) < 0) {
									throw new ListRecordPrimitiveValidateException("rate should be greater than 0",
											root+levelOne+levelTwo , listName, level2,"rate");
								}
								
								BigDecimal chargeAmount = loanAmount.multiply(rate.divide(BigDecimal.valueOf(100.00)));

								if (totalAmount.compareTo(chargeAmount) != 0) {
									// throw new exception
									throw new ListRecordPrimitiveValidateException("amount incompatible",
											root+levelOne+levelTwo, listName, level2,"totalChargeAmount");
								}

								totalCharge = totalCharge.add(chargeAmount);
							}

							level2 += 1;
						}
					
				}
				level1 += 1;
			}
		}
		return totalCharge;		
	}
	
	
	public BigDecimal calculatePeriodicCharges( String addCriteria , List<AdhocChargesResource> adhocChargesList 
			, int noOfPrePayments , BigDecimal interestRate , int repaymentFrequency , BigDecimal loanAmount ) {
		
		BigDecimal totalCharge  = BigDecimal.ZERO;
				
		
		//		AFFC – Factored
		//
		//		AFUP – Upfront / Downpay 
		//
		//		AFDD – Deduct from disbursement 
		//
		//		AFIM –Instalment 
		String root = "fixedLoanCalculatorAddResource";


		int level1 = 0;
		String listName = "adhocCharges";
		
		List<Long> adhocChargesAdded = new ArrayList<>();
		
		
		if((adhocChargesList != null) && (!adhocChargesList.isEmpty())){
		for (AdhocChargesResource resource : adhocChargesList) {
			
			String levelOne = "." + listName + "[" + level1 + "]";
			
			if(adhocChargesAdded.contains(validationService.stringToLong((resource.getChargeTypeId())))) {					
				throw new ListRecordPrimitiveValidateException("duplicate charge type ", root+levelOne  ,listName,level1,"chargeTypeId");
			}

			adhocChargesAdded.add(validationService.stringToLong((resource.getChargeTypeId())));
			
			
			ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(resource.getCriteriaId()), resource.getCriteriaName(),root+levelOne, listName, level1);

			List<AdhocChargesListResource> adhocChargesAmountList = null;
			
			    RepaymentFrequency validRepaymentFrequency = validRepaymentFrequency(validationService.stringToLong(resource.getFrequencyId()) , resource.getFrequencyName());
				int repaymentFrequencyAdhoc = validRepaymentFrequency.getOccurrencePerYear().intValue();
				
									
				if ((repaymentFrequency) == repaymentFrequencyAdhoc) {

					if (!(ApplicationFrequencyEnum.AFFC.toString()).equals(addCriteria)) {

						if ((addCriteria).equals(applicationFrequency.getCode())) {

							adhocChargesAmountList = resource.getAdhocChargesList();

							if ((adhocChargesAmountList != null) && (!adhocChargesAmountList.isEmpty())) {
								int level2 = 0;
								listName = "adhocChargesList";
								for (AdhocChargesListResource childResource : adhocChargesAmountList) {
									
									String levelTwo = "." + listName + "[" + level2 + "]";														
									
									if ((RateOrAmount.AMOUNT.toString()).equals(childResource.getCalculationMethod())) {
										totalCharge = totalCharge
												.add(validationService.stringToBigDecimal(childResource.getRateAmount()));
									} else {
										BigDecimal rate = validationService.stringToBigDecimal(childResource.getRateAmount());
										BigDecimal totalAmount = validationService
												.stringToBigDecimal(childResource.getTotalChargeAmount());
										BigDecimal chargeAmount = loanAmount.multiply(rate.divide(BigDecimal.valueOf(100.00)));

										if (totalAmount.compareTo(chargeAmount) != 0) {
											// throw new exception
											throw new ListRecordPrimitiveValidateException("amount incompatible",
													root+levelOne+levelTwo, listName, level2,
													"totalChargeAmount");
										}

										totalCharge = totalCharge.add(chargeAmount);
									}
									
									level2 += 2;
								}

							}
						}
					} else {
						if ((ApplicationFrequencyEnum.AFFC.toString()).equals(applicationFrequency.getCode())) {
							adhocChargesAmountList = resource.getAdhocChargesList();

							if ((adhocChargesAmountList != null) && (!adhocChargesAmountList.isEmpty())) {
								int level2 = 0;
								listName = "adhocChargesList";
								for (AdhocChargesListResource childResource : adhocChargesAmountList) {

									String levelTwo = "." + listName + "[" + level2 + "]";
									
									if(!("INCEP").equals(validRepaymentFrequency.getCode())) {
									

										try {
											BigDecimal chargeAmount = BigDecimal.ONE;
											
											if ((RateOrAmount.AMOUNT.toString()).equals(childResource.getCalculationMethod())) {
												chargeAmount = validationService.stringToBigDecimal(childResource.getRateAmount());
											} else {
												BigDecimal rate = validationService.stringToBigDecimal(childResource.getRateAmount());
												BigDecimal totalAmount = validationService
														.stringToBigDecimal(childResource.getTotalChargeAmount());
												chargeAmount = loanAmount.multiply(rate.divide(BigDecimal.valueOf(100.00)));

												if (totalAmount.compareTo(chargeAmount) != 0) {
													// throw new exception
													throw new ListRecordPrimitiveValidateException("amount incompatible",
															root+levelOne+levelTwo, listName, level2,
															"totalChargeAmount");
												}
											}
																		
											BigDecimal pvFactor = BigDecimal.ONE;
											BigDecimal netPresentChargeAmount = BigDecimal.ZERO;
											if (noOfPrePayments > 0) {
												pvFactor = structuredLoanCalculationService.pvFactor(Integer.parseInt(childResource.getPeriod()) - 1,Integer.parseInt(childResource.getPeriod()) - 1,repaymentFrequency, interestRate);
												netPresentChargeAmount = structuredLoanCalculationService.npvCalculation(chargeAmount,pvFactor);
											} else {
												pvFactor = structuredLoanCalculationService.pvFactor(Integer.parseInt(childResource.getPeriod()),Integer.parseInt(childResource.getPeriod()), repaymentFrequency,interestRate);
												netPresentChargeAmount = structuredLoanCalculationService.npvCalculation(validationService.stringToBigDecimal(childResource.getTotalChargeAmount()),pvFactor);
											}

											totalCharge = totalCharge.add(netPresentChargeAmount);

										} catch (ArithmeticException e) {
											throw new ValidateRecordException("invalid repaymentFrequency", "message");
										} catch (Exception e) {
											throw new ListRecordPrimitiveValidateException("invalid period",
													root+levelOne+levelTwo , listName, level2,
													"period");
										}
									} else {
										
										if ((RateOrAmount.AMOUNT.toString()).equals(childResource.getCalculationMethod())) {
											totalCharge = totalCharge
													.add(validationService.stringToBigDecimal(childResource.getRateAmount()));
										} else {
											BigDecimal rate = validationService.stringToBigDecimal(childResource.getRateAmount());
											BigDecimal totalAmount = validationService
													.stringToBigDecimal(childResource.getTotalChargeAmount());
											BigDecimal chargeAmount = loanAmount.multiply(rate.divide(BigDecimal.valueOf(100.00)));

											if (totalAmount.compareTo(chargeAmount) != 0) {
												// throw new exception
												throw new ListRecordPrimitiveValidateException("amount incompatible",
														root+levelOne+levelTwo, listName, level2,
														"totalChargeAmount");
											}

											totalCharge = totalCharge.add(chargeAmount);
										}
										
									}

									level2 += 2;
								}

							}
						}

					}
				}else {
					throw new ListRecordPrimitiveValidateException("frequency id  incompatible with loan repayment frequency id",
							root+levelOne , listName, level1,"freequencyId");

				}
				
				}
			level1 += 1;
		}	
		return totalCharge;
	}	
	

	
	@Override
	public Long save(FixedLoanCalculatorAddResource request , String tenantId , String user) {
		

		RepaymentFrequency validRepaymentFrequency = validRepaymentFrequency(validationService.stringToLong(request.getRepaymentFrequencyId()) , request.getRepaymentFrequencyName());
		
		
		BigDecimal netloanAmount = validationService.stringToBigDecimal(request.getLoanAmount());
		int repaymentFrequency = validRepaymentFrequency.getOccurrencePerYear().intValue();
		BigDecimal interestRate = validationService.stringToBigDecimal(request.getAnnualInterestRate());		
		BigDecimal residualValue = validationService.stringToBigDecimal(request.getResidualValue());
		int noOfPrePayments = Integer.parseInt(request.getNoOfPrePayments());
		int loanPeriod = Integer.parseInt(request.getTerm());
		BigDecimal amountPaidInAdvance = validationService.stringToBigDecimal(request.getAmountPaidInAdvance());
		
		
		
		
		
		FixedInstallmentLoanCalculationDetailsResponse calculateDetailsResponse = calculateDetails(tenantId,request);
		
		
		
		Product product = validateProduct(validationService.stringToLong(request.getProductId()),request.getProductName());
		
		SubProduct subProduct = validateSubProduct(validationService.stringToLong(request.getSubProductId()), request.getSubProductName() , product.getId());
		
		if(subProduct.getFeeCharge()==null) {
			throw new ValidateRecordException(environment.getProperty("tc-calculation-subProdcut.feeCharge.notExists"),"subProductId");
		}
		Long feeChargeId = subProduct.getFeeCharge().getId();
		
		
		TcAmortizationDetail tcAmortizationDetail =  new TcAmortizationDetail();
		tcAmortizationDetail.setTenantId(tenantId);
		tcAmortizationDetail.setStatus(CommonStatus.ACTIVE);
		tcAmortizationDetail.setCreatedUser(user);
		tcAmortizationDetail.setCreatedDate(validationService.getCreateOrModifyDate());
		tcAmortizationDetail.setSyncTs(validationService.getCreateOrModifyDate());
		tcAmortizationDetail.setModifiedUser(user);
		tcAmortizationDetail.setModifiedDate(validationService.getCreateOrModifyDate());
		tcAmortizationDetail = tcAmortizationDetailRepository.save(tcAmortizationDetail);
		
		
		Currency currency = validationService.validateCurrency(tenantId, request.getCurrencyId(), request.getCurrencyName(), EntityPoint.TC_FIXED_CAL);
		
		
		TcHeader tcHeader = new TcHeader();
		tcHeader.setTenantId(tenantId);
		tcHeader.setLeadId(validationService.stringToLong(request.getLeadId()));
		tcHeader.setLoanAmount(netloanAmount);
		tcHeader.setInterestRate(interestRate);
		tcHeader.setTerm(Long.valueOf(loanPeriod));
		tcHeader.setCalculationMethod(CalculateMethodEnum.FIXD);
		tcHeader.setTcExpiryDate(validationService.getCreateOrModifyDate());
		tcHeader.setRepaymentCriteria(RepaymentCriteriaEnum.valueOf(request.getRepaymentCriteria()));
		tcHeader.setNoOfPrePayments(Long.valueOf(noOfPrePayments));
		tcHeader.setAmountPaidInAdvance(amountPaidInAdvance);
		tcHeader.setResidualValue(residualValue);
		tcHeader.setRewards(YesNo.valueOf(request.getRewards()));
		tcHeader.setRemarks("remark");		
		tcHeader.setLoanAmountWithTax(BigDecimal.TEN);
		tcHeader.setTotalReceivable(calculateDetailsResponse.getTotalReceivableWithCapitalAndInterest());
		tcHeader.setDownPaymentAmount(calculateDetailsResponse.getTotalDownPayment());
		tcHeader.setLeaseFactor(calculateDetailsResponse.getLoanFactor());
		tcHeader.setChargesFactor(calculateDetailsResponse.getChargeFactor());
		tcHeader.setTotalFactor(calculateDetailsResponse.getTotalFactor());
		tcHeader.setIrr(calculateDetailsResponse.getIrr());
		tcHeader.setDueDate(validationService.stringToLong(request.getDueDate()));
		tcHeader.setPenalInterestRate(BigDecimal.ZERO);
		tcHeader.setGracePeriod(validationService.stringToLong(request.getGracePeriod()));
		tcHeader.setCurrencyCode(currency.getCurrencyCode());
		tcHeader.setCurrencyCodeNumeric(currency.getCodeNumeric());
		tcHeader.setRepaymentFrequency(validRepaymentFrequency);
		tcHeader.setProduct(product);
		tcHeader.setSubProduct(subProduct);
		tcHeader.setCurrencyId(validationService.stringToLong(request.getCurrencyId()));
		tcHeader.setCreatedUser(user);
		tcHeader.setCreatedDate(validationService.getCreateOrModifyDate());
		tcHeader.setSyncTs(validationService.getCreateOrModifyDate());	
		tcHeader.setTcAmortizationDetail(tcAmortizationDetail);
		tcHeader =tcHeaderRepository.save(tcHeader);	

		
		
		List<TcAmortizationPaymentSchedule> chargesThroughOutThePeriodList = new ArrayList<>();
		
		List<TcAmortizationPaymentSchedule> chargesAtDownPayment = new ArrayList<>();
		
		List<TcAmortizationPaymentSchedule> adhocChargesFactoredList = new ArrayList<>();
		

		String root = "fixedLoanCalculatorAddResource";
	
		
		List<Long> fixedChargesAdded = new ArrayList<>();
		List<Long> optionalChargesAdded = new ArrayList<>();
		List<Long> adhocChargesAdded = new ArrayList<>();


		//create fixed charges
		if(request.getFixedChargesDetails() != null && !request.getFixedChargesDetails().isEmpty()) {
			
			int level1 = 0;
			String listName = "fixedChargesList";
			
			for(FixedChargesResource item : request.getFixedChargesDetails()) {				
				
				String levelOne= "."+listName+"["+level1+"]";				
				
				FeeChargeDetails  feeChargeDetail = validateFeeChargeDetail(validationService.stringToLong(item.getChargeTypeId()) , root+levelOne ,listName ,level1  );
				
				if(fixedChargesAdded.contains(feeChargeDetail.getId())) {					
					throw new ListRecordPrimitiveValidateException("duplicate charge type ", root+levelOne  ,listName,level1,"chargeTypeId");
				}
				if(!(feeChargeId).equals(feeChargeDetail.getFeeCharge().getId())) {
					throw new ListRecordPrimitiveValidateException(" incompatible fee charge id", root+levelOne  ,listName,level1,"chargeTypeId");
				}
				if(!("FETC").equals(feeChargeDetail.getCalculationFrequency().getCode())) {
					throw new ListRecordPrimitiveValidateException(" calculation frequency should be related to FETC – Loan trial calculation", root+levelOne  ,listName,level1,"chargeTypeId");
				}
				
				fixedChargesAdded.add(feeChargeDetail.getId());
				
				
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName(), root+levelOne ,listName ,level1);
				
				BigDecimal amount = BigDecimal.ZERO;
				
				if((RateOrAmount.AMOUNT.toString()).equals(item.getCalculationMethod())) {
					
					amount = validationService.stringToBigDecimal(item.getTotalChargeAmount());
					
				}else {
					
					BigDecimal rate = validationService.stringToBigDecimal(item.getRateOrAmount());
					BigDecimal totalAmount = validationService.stringToBigDecimal(item.getTotalChargeAmount());
					amount = netloanAmount.multiply(rate.divide(BigDecimal.valueOf(100.00)));
					
					if(totalAmount.compareTo(amount) != 0) {
						throw new ListRecordPrimitiveValidateException("amount incompatible", root+levelOne ,listName,level1,"totalChargeAmount");
					}
				}
				
				TcFixedCharges tcFixedCharges = new TcFixedCharges();
				tcFixedCharges.setTenantId(tenantId);
				tcFixedCharges.setFeeChargeDetails(feeChargeDetail);
				tcFixedCharges.setCriteria(applicationFrequency);
				tcFixedCharges.setTcHeader(tcHeader);
				tcFixedCharges.setRateOrAmount(RateOrAmount.valueOf(item.getCalculationMethod()));
				tcFixedCharges.setRateAmount(validationService.stringToBigDecimal((item.getRateOrAmount())));
				tcFixedCharges.setTotalChargeAmount(amount);
				tcFixedCharges.setStatus(CommonStatus.valueOf(item.getStatus()));
				tcFixedCharges.setCreatedDate(validationService.getCreateOrModifyDate());
				tcFixedCharges.setSyncTs(validationService.getCreateOrModifyDate());
				tcFixedCharges.setCreatedUser(user);
				tcFixedChargesRepository.save(tcFixedCharges);
				

				String transActionSubCode =findTransactionSubCode(feeChargeDetail.getOtherFeeType().getTransactionSubCodeId(),tenantId, root+levelOne,listName,level1);
				
				if((ApplicationFrequencyEnum.AFIM.toString()).equals(applicationFrequency.getCode())  ){
					TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,amount  ,null);
					chargesThroughOutThePeriodList.add(intermediateTcAmortizationPaymentSchedule);
				}
				
				if((ApplicationFrequencyEnum.AFUP.toString()).equals(applicationFrequency.getCode())  ){
					TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,amount  ,null);
					chargesAtDownPayment.add(intermediateTcAmortizationPaymentSchedule);
				}
				
				if((ApplicationFrequencyEnum.AFDD.toString()).equals(applicationFrequency.getCode())  ){
					TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,amount  ,null);
					chargesAtDownPayment.add(intermediateTcAmortizationPaymentSchedule);
				}
				
				level1 +=1;			
			}
		}
		
		
		//create optional charges
		if(request.getOptionalChargesDetails() != null && !request.getOptionalChargesDetails().isEmpty()) {
						
			int level1 = 0;
			String listName = "optionalChargeDetails";
			
			for(OptionalChargesResource item : request.getOptionalChargesDetails()) {				
				
				String levelOne= "."+listName+"["+level1+"]";				

				//FeeChargeDetails  feeChargeDetail = validateFeeChargeDetail(validationService.stringToLong(item.getChargeTypeId()) , root+levelOne ,listName ,level1  );
				FeeChargeDetailOptional feeChargeDetailOptional = validateFeeChargeDetailOptional(validationService.stringToLong(item.getChargeTypeId()) , root+levelOne ,listName ,level1);
				
				
				if(optionalChargesAdded.contains(feeChargeDetailOptional.getId())) {					
					throw new ListRecordPrimitiveValidateException("duplicate charge type ", root+levelOne  ,listName,level1,"chargeTypeId");
				}	
				if(!(feeChargeId).equals(feeChargeDetailOptional.getFeeCharge().getId())) {
					throw new ListRecordPrimitiveValidateException(" incompatible fee charge id", root+levelOne  ,listName,level1,"chargeTypeId");
				}
				if(!("FETC").equals(feeChargeDetailOptional.getCalculationFrequency().getCode())) {
					throw new ListRecordPrimitiveValidateException(" calculation frequency should be related to FETC – Loan trial calculation", root+levelOne  ,listName,level1,"chargeTypeId");
				}
				optionalChargesAdded.add(feeChargeDetailOptional.getId());
				
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName() , root+levelOne ,listName ,level1  );					
				
				if(item.getOptionalChargesList() != null && !item.getOptionalChargesList().isEmpty()) {
					int level2 = 0;
					listName = "optionalChargesList";
					
					for(OptionalChargesListResource itm : item.getOptionalChargesList()) {						
						
						
						String levelTwo= "."+listName+"["+level2+"]";
						
						BigDecimal amount = BigDecimal.ZERO;
						
						if((RateOrAmount.AMOUNT.toString()).equals(itm.getCalculationMethod())) {
							
							amount = validationService.stringToBigDecimal(itm.getTotalChargeAmount());
							
						}else {
							BigDecimal rate = validationService.stringToBigDecimal(itm.getRateAmount());
							BigDecimal totalAmount = validationService.stringToBigDecimal(itm.getTotalChargeAmount());
							amount = netloanAmount.multiply(rate.divide(BigDecimal.valueOf(100.00)));
							
							if(totalAmount.compareTo(amount) != 0) {
								//throw new exception
								throw new ListRecordPrimitiveValidateException("amount incompatible", root+levelOne+levelTwo ,listName,level2,null);
							}
						}
						
						TcOptionsCharges tcOptionsCharges = new TcOptionsCharges();
						tcOptionsCharges.setTenantId(tenantId);
						//tcOptionsCharges.setFeeChargeDetails(feeChargeDetail);
						tcOptionsCharges.setFeeChargeDetailOptional(feeChargeDetailOptional);
						tcOptionsCharges.setCriteria(applicationFrequency);
						tcOptionsCharges.setRateAmount(validationService.stringToBigDecimal((itm.getRateAmount())));
						tcOptionsCharges.setRateOrAmount(RateOrAmount.valueOf(itm.getCalculationMethod()));
						tcOptionsCharges.setTotalChargeAmount(amount);
						tcOptionsCharges.setOption(itm.getOption());
						tcOptionsCharges.setTcHeader(tcHeader);
						tcOptionsCharges.setStatus(CommonStatus.valueOf(item.getStatus()));
						tcOptionsCharges.setCreatedDate(validationService.getCreateOrModifyDate());
						tcOptionsCharges.setSyncTs(validationService.getCreateOrModifyDate());
						tcOptionsCharges.setCreatedUser(user);
						tcOptionsChargesRepository.save(tcOptionsCharges);
						
						
						String transActionSubCode =findTransactionSubCode(feeChargeDetailOptional.getOtherFeeType().getTransactionSubCodeId(),tenantId, root+levelOne+levelTwo ,listName,level1);
						
						if((ApplicationFrequencyEnum.AFIM.toString()).equals(applicationFrequency.getCode())  ){
							TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,amount  ,null);
							chargesThroughOutThePeriodList.add(intermediateTcAmortizationPaymentSchedule);
						}
						
						if((ApplicationFrequencyEnum.AFUP.toString()).equals(applicationFrequency.getCode())  ){
							TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,amount  ,null);
							chargesAtDownPayment.add(intermediateTcAmortizationPaymentSchedule);
						}
						
						if((ApplicationFrequencyEnum.AFDD.toString()).equals(applicationFrequency.getCode())  ){
							TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,amount  ,null);
							chargesAtDownPayment.add(intermediateTcAmortizationPaymentSchedule);
						}
						
						level2 += 1;
					}
				}
				
				level1 +=1;				
			}			
		}

		
		
		//create adhoc charges
		if(request.getAdhocChargesDetails() != null && !request.getAdhocChargesDetails().isEmpty()) {
			
	       int level1 = 0;
	       String listName = "adhocChargesDetails";
	       
           for(AdhocChargesResource item : request.getAdhocChargesDetails()) {
				
				String levelOne= "."+listName+"["+level1+"]";
        	   
				//FeeChargeDetails  feeChargeDetail = validateFeeChargeDetail(validationService.stringToLong(item.getChargeTypeId()) , root+levelOne ,listName ,level1  );
				FeeChargeDetailAdhoc feeChargeDetailAdhoc = validateFeeChargeDetailAdhoc(validationService.stringToLong(item.getChargeTypeId()) , root+levelOne ,listName ,level1);
				
				
				if(adhocChargesAdded.contains(feeChargeDetailAdhoc.getId())) {					
					throw new ListRecordPrimitiveValidateException("duplicate charge type ", root+levelOne  ,listName,level1,"chargeTypeId");
				}	
				if(!(feeChargeId).equals(feeChargeDetailAdhoc.getFeeCharge().getId())) {
					throw new ListRecordPrimitiveValidateException(" incompatible fee charge id", root+levelOne  ,listName,level1,"chargeTypeId");
				}
				if(!("FETC").equals(feeChargeDetailAdhoc.getCalculationFrequency().getCode())) {
					throw new ListRecordPrimitiveValidateException(" calculation frequency should be related to FETC – Loan trial calculation", root+levelOne  ,listName,level1,"chargeTypeId");
				}
				adhocChargesAdded.add(feeChargeDetailAdhoc.getId());
				
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequency(validationService.stringToLong(item.getCriteriaId()), item.getCriteriaName(), root+levelOne ,listName ,level1  );		
				
				int repaymentFrequencyAdhoc = validRepaymentFrequency(validationService.stringToLong(item.getFrequencyId()) , item.getFrequencyName()).getOccurrencePerYear().intValue();
				if ((repaymentFrequency) == repaymentFrequencyAdhoc) {
					if(item.getAdhocChargesList() != null && !item.getAdhocChargesList().isEmpty()) {
						int level2 = 0;
						
						for(AdhocChargesListResource itm : item.getAdhocChargesList()) {
							
							listName = "adhocChargesList";
							String levelTwo= "."+listName+"["+level2+"]";
							
							BigDecimal amount = BigDecimal.ZERO;
							
							
							if((RateOrAmount.AMOUNT.toString()).equals(itm.getCalculationMethod())) {
								
								amount = validationService.stringToBigDecimal(itm.getTotalChargeAmount());
								
							}else {
								BigDecimal rate = validationService.stringToBigDecimal(itm.getRateAmount());
								BigDecimal totalAmount = validationService.stringToBigDecimal(itm.getTotalChargeAmount());
								amount = netloanAmount.multiply(rate.divide(BigDecimal.valueOf(100.00)));
								
								if(totalAmount.compareTo(amount) != 0) {
									//throw new exception
									throw new ListRecordPrimitiveValidateException("amount incompatible", root+levelOne+levelTwo ,listName,level2,"totalChargeAmount");
								}
							}
							
							String transActionSubCode =findTransactionSubCode(feeChargeDetailAdhoc.getOtherFeeType().getTransactionSubCodeId(),tenantId, root+levelOne+levelTwo ,listName,level1);
							
							
							if((ApplicationFrequencyEnum.AFUP.toString()).equals(applicationFrequency.getCode())  ){
								TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,validationService.stringToBigDecimal(itm.getTotalChargeAmount())  ,null);
								chargesAtDownPayment.add(intermediateTcAmortizationPaymentSchedule);
							}
							if((ApplicationFrequencyEnum.AFDD.toString()).equals(applicationFrequency.getCode())  ){
								TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,validationService.stringToBigDecimal(itm.getTotalChargeAmount())  ,null);
								chargesAtDownPayment.add(intermediateTcAmortizationPaymentSchedule);
							}
							if((ApplicationFrequencyEnum.AFIM.toString()).equals(applicationFrequency.getCode())  ){
								TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,validationService.stringToBigDecimal(itm.getTotalChargeAmount())  ,null);
								chargesThroughOutThePeriodList.add(intermediateTcAmortizationPaymentSchedule);
							}							
	
	
							
							TcAdhocCharges tcAdhocCharges = new TcAdhocCharges();
							tcAdhocCharges.setTenantId(tenantId);
							//tcAdhocCharges.setFeeChargeDetails(feeChargeDetail);
							tcAdhocCharges.setFeeChargeDetailAdhoc(feeChargeDetailAdhoc);
							tcAdhocCharges.setCriteria(applicationFrequency);
							tcAdhocCharges.setPeriod(itm.getPeriod());
							tcAdhocCharges.setTcHeader(tcHeader);
							tcAdhocCharges.setFrequencyId(validRepaymentFrequency.getId());
							tcAdhocCharges.setFrequencyCode(validRepaymentFrequency.getCode());
							tcAdhocCharges.setRateAmount(validationService.stringToBigDecimal((itm.getRateAmount())));
							tcAdhocCharges.setRateOrAmount(RateOrAmount.valueOf(itm.getCalculationMethod()));
							tcAdhocCharges.setTotalChargeAmount(validationService.stringToBigDecimal((itm.getTotalChargeAmount())));
							tcAdhocCharges.setStatus(CommonStatus.valueOf(item.getStatus()));
							tcAdhocCharges.setCreatedDate(validationService.getCreateOrModifyDate());
							tcAdhocCharges.setSyncTs(validationService.getCreateOrModifyDate());
							tcAdhocCharges.setCreatedUser(user);
							tcAdhocChargesRepository.save(tcAdhocCharges);
							
							level2 += 1;
						}
					}
				}else {
					throw new ListRecordPrimitiveValidateException("frequency id  incompatible with loan repayment frequency id",
							root+levelOne , listName, level1,"freequencyId");

				}
				
				level1 +=1;
			}
		}
		
		
		// tax details - START
		

		List<TaxDetailsResponse> taxDetailsList = calculateDetailsResponse.getTaxDetailsList();
		
		List<TcAmortizationPaymentSchedule> taxesThroughOutThePeriodList = new ArrayList<>();
		
		List<TcAmortizationPaymentSchedule> taxesAtDownPayment = new ArrayList<>();
		
		if( (taxDetailsList != null) && (!taxDetailsList.isEmpty())) {
			
			for(TaxDetailsResponse resp : taxDetailsList) {
				
				
				ApplicationFrequency applicationFrequency = validateApplicationFrequencyForTax(resp.getApplicationFrequencyId());	
				
				TcTaxDetail tcTaxDetail = new TcTaxDetail();
				tcTaxDetail.setTenantId(tenantId);
				tcTaxDetail.setCriteria(applicationFrequency);
				tcTaxDetail.setTcHeader(tcHeader);
				tcTaxDetail.setCalculationMethod(RateOrAmount.valueOf(resp.getTaxAmountType()));
				tcTaxDetail.setRateAmount(resp.getTaxAmountRate());
				tcTaxDetail.setTotalChargeAmount(resp.getTotalTaxAmount());
				tcTaxDetail.setStatus(CommonStatus.ACTIVE);
				tcTaxDetail.setCreatedDate(validationService.getCreateOrModifyDate());
				tcTaxDetail.setSyncTs(validationService.getCreateOrModifyDate());
				tcTaxDetail.setCreatedUser(user);
				tcTaxDetailRepository.save(tcTaxDetail);
				
				
				String transActionSubCode = "tax";
				TcAmortizationPaymentSchedule intermediateTcAmortizationPaymentSchedule = createTcAmortizationPaymentSchedule(tenantId,transActionSubCode,tcAmortizationDetail,resp.getTotalTaxAmount()  ,null);
				
				if((ApplicationFrequencyEnum.AFIM.toString()).equals(applicationFrequency.getCode())  ){
					taxesThroughOutThePeriodList.add(intermediateTcAmortizationPaymentSchedule);
				}else if( (ApplicationFrequencyEnum.AFUP.toString()).equals(applicationFrequency.getCode())  ) {
					taxesAtDownPayment.add(intermediateTcAmortizationPaymentSchedule);
				}	
				else if( (ApplicationFrequencyEnum.AFDD.toString()).equals(applicationFrequency.getCode())  ) {
					taxesAtDownPayment.add(intermediateTcAmortizationPaymentSchedule);
				}
				
			}			
		}

		
		// tax details -  END
		
		
		
		
		BigDecimal  irrRate = (calculateDetailsResponse.getIrr()).multiply(BigDecimal.valueOf(0.01));
		
		List<TcAmortizationPaymentSchedule> finalSaveList = new ArrayList<>();
		
		BigDecimal currentCapitalBalance = calculateDetailsResponse.getLoanCalculationValue();
		
		if(noOfPrePayments > 0) {
			
			for(int i = 1 ; i < noOfPrePayments+1 ;i++) {
				
				TcAmortizationPaymentSchedule prepayment = createTcAmortizationPaymentSchedule(tenantId,"capital",tcAmortizationDetail,calculateDetailsResponse.getFixedInstallment(),0l);
				currentCapitalBalance = currentCapitalBalance.subtract(calculateDetailsResponse.getFixedInstallment());
				
				finalSaveList.add(prepayment);				
				
				for(TcAmortizationPaymentSchedule paymentScheduleCharges :chargesThroughOutThePeriodList) {
					TcAmortizationPaymentSchedule newPaymentScheduleCharge =  createTcAmortizationPaymentSchedule(tenantId,paymentScheduleCharges.getTransactionTypeCode(),tcAmortizationDetail,paymentScheduleCharges.getAmount()  ,0l);				
					finalSaveList.add(newPaymentScheduleCharge);
				}	
				
				
				for(TcAmortizationPaymentSchedule paymentScheduleTax :taxesThroughOutThePeriodList) {
					TcAmortizationPaymentSchedule newPaymentScheduleTax =  createTcAmortizationPaymentSchedule(tenantId,paymentScheduleTax.getTransactionTypeCode(),tcAmortizationDetail,paymentScheduleTax.getAmount()  ,0l);				
					finalSaveList.add(newPaymentScheduleTax);
				}	
					
			}			
		}

	
		
		for(TcAmortizationPaymentSchedule paymentScheduleCharges :chargesAtDownPayment) {
			TcAmortizationPaymentSchedule newPaymentScheduleCharge =  createTcAmortizationPaymentSchedule(tenantId,paymentScheduleCharges.getTransactionTypeCode(),tcAmortizationDetail,paymentScheduleCharges.getAmount()  ,0l);				
			finalSaveList.add(newPaymentScheduleCharge);
		}
			
				
		for(TcAmortizationPaymentSchedule paymentScheduleTax :taxesAtDownPayment) {
			TcAmortizationPaymentSchedule newPaymentScheduleCharge =  createTcAmortizationPaymentSchedule(tenantId,paymentScheduleTax.getTransactionTypeCode(),tcAmortizationDetail,paymentScheduleTax.getAmount()  ,0l);								finalSaveList.add(newPaymentScheduleCharge);
		}
		
		
	
		
		int period = loanPeriod - noOfPrePayments;
		
		for(int i = 1 ; i < period+1 ;i++) {
			
			Long sequence = Long.valueOf(i);
						
			FixedInstallmentPaymentScheduleResponse createPaymentSequenceResponse =tcCommonCalcualtionService.createPaymentSequenceResponse(sequence, currentCapitalBalance, irrRate, repaymentFrequency, calculateDetailsResponse.getFixedInstallment());
			
			TcAmortizationPaymentSchedule interest = createTcAmortizationPaymentSchedule(tenantId,"interest",tcAmortizationDetail,createPaymentSequenceResponse.getInterestPortion()  ,sequence);
			TcAmortizationPaymentSchedule capital = createTcAmortizationPaymentSchedule(tenantId,"capital",tcAmortizationDetail,createPaymentSequenceResponse.getCapitalPortion(),sequence);
			
			finalSaveList.add(interest);
			finalSaveList.add(capital);
			
			for(TcAmortizationPaymentSchedule paymentScheduleCharges :chargesThroughOutThePeriodList) {
				TcAmortizationPaymentSchedule newPaymentScheduleCharge =  createTcAmortizationPaymentSchedule(tenantId,paymentScheduleCharges.getTransactionTypeCode(),tcAmortizationDetail,paymentScheduleCharges.getAmount()  ,sequence);
				finalSaveList.add(newPaymentScheduleCharge);
			}
			
			
			for(TcAmortizationPaymentSchedule paymentScheduleTax :taxesThroughOutThePeriodList) {
				TcAmortizationPaymentSchedule newPaymentScheduleTax =  createTcAmortizationPaymentSchedule(tenantId,paymentScheduleTax.getTransactionTypeCode(),tcAmortizationDetail,paymentScheduleTax.getAmount()  ,sequence);				
				finalSaveList.add(newPaymentScheduleTax);
			}	
			
			
			currentCapitalBalance = tcCommonCalcualtionService.calcualteNewCapitalBalance(currentCapitalBalance,createPaymentSequenceResponse.getCapitalPortion());			
		}
	

		
		List<TcAmortizationPaymentSchedule> allPaymentScheduleList = tcAmortizationPaymentScheduleRepository.saveAll(finalSaveList);		

		
//		List<String> payShed = new ArrayList<>();
//		String msg = null;
//		for(TcAmortizationPaymentSchedule item:finalSaveList) {
//			msg = " ";
//			msg = " sequence = "+item.getSequence()+ " | type code  = "+ item.getTransactionTypeCode() + " | amount = "+ item.getAmount();
//			payShed.add(msg);
//		}
		
		return tcHeader.getId();		
	}
	

	
	
	public TcAmortizationPaymentSchedule createTcAmortizationPaymentSchedule(String tenantId,String transactionTypeCode
			,TcAmortizationDetail tcAmortizationDetail , BigDecimal amount , Long sequence) {
		
		TcAmortizationPaymentSchedule tcAmortizationPaymentSchedule = new TcAmortizationPaymentSchedule();
		tcAmortizationPaymentSchedule.setTenantId(tenantId);
		tcAmortizationPaymentSchedule.setTcAmortizationDetail(tcAmortizationDetail);
		tcAmortizationPaymentSchedule.setSequence(sequence);
		tcAmortizationPaymentSchedule.setTransactionTypeCode(transactionTypeCode);
		tcAmortizationPaymentSchedule.setDueDate(null);
		tcAmortizationPaymentSchedule.setAmount(amount);
		tcAmortizationPaymentSchedule.setDescription("ABCD");
		tcAmortizationPaymentSchedule.setStatus(CommonStatus.ACTIVE);
		tcAmortizationPaymentSchedule.setCreatedUser(tcAmortizationDetail.getCreatedUser());
		tcAmortizationPaymentSchedule.setCreatedDate(validationService.getCreateOrModifyDate());
		tcAmortizationPaymentSchedule.setSyncTs(validationService.getCreateOrModifyDate());
		
		return tcAmortizationPaymentSchedule;
		
		
	}
	
	
	@Override
	public Optional<TcHeader> findByTcHeaderId(Long tcHeaderId) {
		
		Optional<TcHeader> isPresentTcHeader = tcHeaderRepository.findById(tcHeaderId);
		
		if(isPresentTcHeader.isPresent()) {
			TcHeader tcHeader = isPresentTcHeader.get();
			
			Optional<TcAmortizationDetail> tcAmortizationDetailOptional = tcAmortizationDetailRepository.findById(tcHeader.getTcAmortizationDetail().getId());
			
			if(tcAmortizationDetailOptional.isPresent()) {
				TcAmortizationDetail tcAmortizationDetail = tcAmortizationDetailOptional.get();
				List<TcAmortizationPaymentSchedule> paymentScheduleList = tcAmortizationPaymentScheduleRepository.findAllByTcAmortizationDetailId(tcAmortizationDetail.getId());
				
				tcAmortizationDetail.setTcAmortizationPaymentScheduleList(paymentScheduleList);
				tcHeader.setTcAmortizeDetail(tcAmortizationDetail);
			
			}

			
			List<TcFixedCharges> tcFixedCharges = tcFixedChargesRepository.findByTcHeaderId(tcHeader.getId());
			List<TcOptionsCharges> tcOptionsCharges = tcOptionsChargesRepository.findByTcHeaderId(tcHeader.getId());
			List<TcAdhocCharges> tcAdhocCharges = tcAdhocChargesRepository.findByTcHeaderId(tcHeader.getId());
			
			tcHeader.setFixedChargesDetails(tcFixedCharges);
			tcHeader.setOptionsChargesDetails(tcOptionsCharges);
			tcHeader.setAdhocChargesDetails(tcAdhocCharges);
			
			return Optional.of(tcHeader);
		}else {
			return Optional.empty();
		}
		
	}
	
	
	@Override
	public Optional<TcHeader> findByLeadId(Long leadId) {
		
		List<TcHeader> activeTcHeaderList = tcHeaderRepository.findByLeadIdAndStatus(leadId, CommonStatus.ACTIVE);
		
		if((activeTcHeaderList != null) && (!activeTcHeaderList.isEmpty()) ) {
			TcHeader tcHeader = activeTcHeaderList.get(0);
			
			Optional<TcAmortizationDetail> tcAmortizationDetailOptional = tcAmortizationDetailRepository.findById(tcHeader.getTcAmortizationDetail().getId());
			
			if(tcAmortizationDetailOptional.isPresent()) {
				TcAmortizationDetail tcAmortizationDetail = tcAmortizationDetailOptional.get();
				List<TcAmortizationPaymentSchedule> paymentScheduleList = tcAmortizationPaymentScheduleRepository.findAllByTcAmortizationDetailId(tcAmortizationDetail.getId());
				
				tcAmortizationDetail.setTcAmortizationPaymentScheduleList(paymentScheduleList);
				tcHeader.setTcAmortizeDetail(tcAmortizationDetail);
			
			}

			
			List<TcFixedCharges> tcFixedCharges = tcFixedChargesRepository.findByTcHeaderId(tcHeader.getId());
			List<TcOptionsCharges> tcOptionsCharges = tcOptionsChargesRepository.findByTcHeaderId(tcHeader.getId());
			List<TcAdhocCharges> tcAdhocCharges = tcAdhocChargesRepository.findByTcHeaderId(tcHeader.getId());
			
			tcHeader.setFixedChargesDetails(tcFixedCharges);
			tcHeader.setOptionsChargesDetails(tcOptionsCharges);
			tcHeader.setAdhocChargesDetails(tcAdhocCharges);
			
			return Optional.of(tcHeader);
		}else {
			return Optional.empty();
		}
		
	}
	
	
	
	
	
	@Override
	public List<TaxDetailsResponse> getTaxDetails(String tenantId , FixedLoanCalculatorAddResource request ) {
		
		

		FixedInstallmentLoanCalculationDetailsResponse calculateDetailsResponse = calculateDetails(tenantId,request);
		List<TaxDetailsResponse> taxDetailsList = getTaxDetailsList(tenantId,request,calculateDetailsResponse.getFixedInstallment());
		
		
		return taxDetailsList;
		
	}
	
	public List<TaxDetailsResponse> getTaxDetailsList(String tenantId , FixedLoanCalculatorAddResource request ,BigDecimal installmentAmount) {
		
		
		Product product = validateProduct(validationService.stringToLong(request.getProductId()),request.getProductName());
		
		SubProduct subProduct = validateSubProduct(validationService.stringToLong(request.getSubProductId()), request.getSubProductName() , product.getId());
		
		
		TaxCalculationResource taxCalculationResource = new TaxCalculationResource();
		taxCalculationResource.setInstallmentAmount(installmentAmount);
		taxCalculationResource.setLoanAmount(validationService.stringToBigDecimal(request.getLoanAmount()));
		taxCalculationResource.setSubProductId(subProduct.getId());
		taxCalculationResource.setTaxTypeCode("LOAN");
		taxCalculationResource.setLeadId(validationService.stringToLong(request.getLeadId()));
		
		List<TaxDetailsResponse> taxDetailsList = taxCalculationService.calculateTaxDetailsResponse(tenantId, taxCalculationResource);
		return taxDetailsList;
		
	}
	
	public BigDecimal calculateTaxTotalsBasedOnApplicationFrequency(List<TaxDetailsResponse> taxDetailsList , String applicationFrequencyCode) {
		BigDecimal total = BigDecimal.ZERO;
		
		for(TaxDetailsResponse taxDetail :taxDetailsList) {
			
			ApplicationFrequency applicationFrequency = validateApplicationFrequencyForTax(taxDetail.getApplicationFrequencyId());
			
			if((applicationFrequencyCode).equals(applicationFrequency.getCode().toString())) {
				total =total.add(taxDetail.getTotalTaxAmount());
			}			
		}
		
		return total;

	}
	
	
	
		

	
	private FeeChargeDetails validateFeeChargeDetail(Long id,String path , String listName , int index) {
		
		Optional<FeeChargeDetails> feeChargeDetailOptional = feeChargeDetailRepository.findById(id);
		if(feeChargeDetailOptional.isPresent()) {			
			if(!(CommonStatus.ACTIVE.toString()).equals(feeChargeDetailOptional.get().getStatus().toString())) {
				throw new ListRecordPrimitiveValidateException(environment.getProperty("invalid-status"), path,listName,index,"feeChargeDetailId");
			}		
		} else {
			throw new ListRecordPrimitiveValidateException(environment.getProperty(INVALID_ID), path,listName,index,"feeChargeDetailId");
		}
		
		return feeChargeDetailOptional.get();
		
	}
	
	
	private FeeChargeDetailAdhoc validateFeeChargeDetailAdhoc(Long id,String path , String listName , int index) {
		
		Optional<FeeChargeDetailAdhoc> feeChargeDetailAdhocOptional = feeChargeDetailAdhocRepository.findById(id);
		if(feeChargeDetailAdhocOptional.isPresent()) {			
			if(!(CommonStatus.ACTIVE.toString()).equals(feeChargeDetailAdhocOptional.get().getStatus().toString())) {
				throw new ListRecordPrimitiveValidateException(environment.getProperty("invalid-status"), path,listName,index,"feeChargeDetailId");
			}		
		} else {
			throw new ListRecordPrimitiveValidateException(environment.getProperty(INVALID_ID), path,listName,index,"feeChargeDetailId");
		}
		
		return feeChargeDetailAdhocOptional.get();
		
	}
	
	private FeeChargeDetailOptional validateFeeChargeDetailOptional(Long id,String path , String listName , int index) {
		
		Optional<FeeChargeDetailOptional> FeeChargeDetailOptionalOptional = feeChargeDetailOptionalRepository.findById(id);
		if(FeeChargeDetailOptionalOptional.isPresent()) {			
			if(!(CommonStatus.ACTIVE.toString()).equals(FeeChargeDetailOptionalOptional.get().getStatus().toString())) {
				throw new ListRecordPrimitiveValidateException(environment.getProperty("invalid-status"), path,listName,index,"feeChargeDetailId");
			}		
		} else {
			throw new ListRecordPrimitiveValidateException(environment.getProperty(INVALID_ID), path,listName,index,"feeChargeDetailId");
		}
		
		return FeeChargeDetailOptionalOptional.get();
		
	}
	
    private ApplicationFrequency validateApplicationFrequency(Long id, String name ,String path , String listName , int index) {
		
		Optional<ApplicationFrequency> applicationFrequency = applicationFrequencyRepository.findById(id);
		
		if(applicationFrequency.isPresent()) {
			
			if(!(applicationFrequency.get().getName()).equals(name)) {
				throw new ListRecordPrimitiveValidateException(environment.getProperty("common-name.incompatible"), path,listName,index,"criteriaId");
			}
			if(!(CommonStatus.ACTIVE.toString()).equals(applicationFrequency.get().getStatus().toString())) {
				throw new ListRecordPrimitiveValidateException(environment.getProperty("invalid-status"), path,listName,index,"criteriaId");
			}		
		} else {
			throw new ListRecordPrimitiveValidateException(environment.getProperty(INVALID_ID), path,listName,index,"criteriaId");
		}		
		return applicationFrequency.get();
	}
    
    private ApplicationFrequency validateApplicationFrequencyForTax(Long id) {
		
		Optional<ApplicationFrequency> applicationFrequency = applicationFrequencyRepository.findById(id);
		
		if(applicationFrequency.isPresent()) {
			

			if(!(CommonStatus.ACTIVE.toString()).equals(applicationFrequency.get().getStatus().toString())) {
				throw new ValidateRecordException(environment.getProperty("invalid-status"), "applicationFrequencyId");
			}		
		} else {
			throw new ValidateRecordException(environment.getProperty(INVALID_ID),"applicationFrequency");
		}		
		return applicationFrequency.get();
	}
    
    private RepaymentFrequency validRepaymentFrequency(Long id ,String name) {
		
		Optional<RepaymentFrequency> repaymentFrequencyOptional = repaymentFrequencyRepository.findById(id);

		if(repaymentFrequencyOptional.isPresent()) {
			if(!(repaymentFrequencyOptional.get().getName()).equals(name)) {
				throw new ValidateRecordException(environment.getProperty("common-name.incompatible"),"repaymentFrequencyId");
			}
			if(!(CommonStatus.ACTIVE.toString()).equals(repaymentFrequencyOptional.get().getStatus().toString())) {
				throw new ValidateRecordException(environment.getProperty("invalid-status"),"repaymentFrequencyId");
			}
			return repaymentFrequencyOptional.get();
		
		} else {
			throw new ValidateRecordException(environment.getProperty(INVALID_ID),"repaymentFrequencyId");
		}
    }
	
    
    private String findTransactionSubCode(Long transactionSubCodeId,String tenantId,String path , String listName , int index) {
    	
    	BankTransactionSubCodeResponse bankTransactionSubCodeResponse = validationService.getTransactionTypeCode(tenantId,transactionSubCodeId.toString());
    	
		if (bankTransactionSubCodeResponse == null) { 
			throw new ListRecordPrimitiveValidateException(environment.getProperty(INVALID_ID), path,listName,index,"transactionSubCodeId");
		}
		if (bankTransactionSubCodeResponse.getServiceStatus() == null) {
			if (!bankTransactionSubCodeResponse.getStatus().equals(CommonStatus.ACTIVE.toString())) {
				throw new ListRecordPrimitiveValidateException(environment.getProperty("invalid-status"), path,listName,index,"transactionSubCode");
			}	
		}
		else {
//			serviceValidationExceptionHadle(userProfileResponse.getServiceStatus(), ServiceEntity.TRANSACTIONSUBCODE, null);
		}
		
		return bankTransactionSubCodeResponse.getSubCode();
    	
    	
    }
    
    
    public Product validateProduct(Long productId , String productName) {
    	
		Optional<Product> productOptional = productRepository.findById(productId);
		
		if(productOptional.isPresent()) {
			if(!(productOptional.get().getProductName()).equals(productName)) {
				throw new ValidateRecordException(environment.getProperty("common-name.incompatible"),"productId");
			}
			if(!(CommonStatus.ACTIVE.toString()).equals(productOptional.get().getStatus().toString())) {
				throw new ValidateRecordException(environment.getProperty("invalid-status"),"productId");
			}
		
		} else {
			throw new ValidateRecordException(environment.getProperty(INVALID_ID),"productId");
		}
		
		return productOptional.get();
    	
    }
    
    public SubProduct validateSubProduct(Long subProductId , String subProductName ,Long productId) {
    	
    	
		Optional<SubProduct> subProductOptional = subProductRepository.findById(subProductId);
		
		if(subProductOptional.isPresent()) {
			
			if(!(subProductOptional.get().getName()).equals(subProductName)) {
				throw new ValidateRecordException(environment.getProperty("common-name.incompatible"),"subProductId");
			}
			if(!(CommonStatus.ACTIVE.toString()).equals(subProductOptional.get().getStatus().toString())) {
				throw new ValidateRecordException(environment.getProperty("invalid-status"),"subProductId");
			}
			if(!(productId).equals(subProductOptional.get().getProduct().getId())) {
				throw new ValidateRecordException(environment.getProperty("subProduct-productId.incompatible"),"subProductId");
			}	
		} else {
			throw new ValidateRecordException(environment.getProperty(INVALID_ID),"subProductId");
		}
		
		return subProductOptional.get();
    	
    }
	
	
	
}
