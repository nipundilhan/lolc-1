package com.fusionx.lending.product.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fusionx.lending.product.resources.BasicTcCalculatorResource;
import com.fusionx.lending.product.service.BasicTcCalculatorService;
import com.fusionx.lending.product.service.TcCommonCalcualtionService;
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

@Component
public class BasicTcCalculatorServiceImpl implements BasicTcCalculatorService {

	private TcCommonCalcualtionService tcCommonCalcualtionService;

	@Autowired
	public void setTcCommonCalcualtionService(TcCommonCalcualtionService tcCommonCalcualtionService) {
		this.tcCommonCalcualtionService = tcCommonCalcualtionService;
	}

	private static final int ROUND_OFF_VALUE1 = 40;
	private static final int ROUND_OFF_VALUE2 = 4;
	private static final int ROUND_OFF_VALUE3 = 2;

	@Override
	public BasicTcCalculatorResource calculateIrr(BasicTcCalculatorResource basicTcCalculatorResource) {
		BigDecimal irr = tcCommonCalcualtionService.calculateIRRforFixedInstallments(
				basicTcCalculatorResource.getLoanAmount(), basicTcCalculatorResource.getInstallment(),
				basicTcCalculatorResource.getTerm().intValue(), basicTcCalculatorResource.getFrequency(),
				basicTcCalculatorResource.getNoOfPrePayments().intValue());

		BasicTcCalculatorResource response = new BasicTcCalculatorResource();
		response.setFrequency(basicTcCalculatorResource.getFrequency());
		response.setIrr(irr);
		response.setLoanAmount(basicTcCalculatorResource.getLoanAmount());
		response.setInstallment(basicTcCalculatorResource.getInstallment());
		response.setTerm(basicTcCalculatorResource.getTerm());
		response.setAnnualInterestRate(basicTcCalculatorResource.getAnnualInterestRate());
		response.setNoOfPrePayments(basicTcCalculatorResource.getNoOfPrePayments());
		return response;
	}

	@Override
	public BasicTcCalculatorResource calculateNoOfPrePayments(BasicTcCalculatorResource basicTcCalculatorResource) {
		int noOfrePayments = 0;

		BigDecimal irr = tcCommonCalcualtionService.calculateIRRforFixedInstallments(
				basicTcCalculatorResource.getLoanAmount(), basicTcCalculatorResource.getInstallment(),
				basicTcCalculatorResource.getTerm().intValue(), basicTcCalculatorResource.getFrequency(),
				noOfrePayments);

		BigDecimal noOfPrePayments = this.calculateNoOfPrePayments(basicTcCalculatorResource.getLoanAmount(),
				basicTcCalculatorResource.getInstallment(), basicTcCalculatorResource.getAnnualInterestRate(),
				basicTcCalculatorResource.getTerm().intValue(), basicTcCalculatorResource.getFrequency());

		BasicTcCalculatorResource response = new BasicTcCalculatorResource();
		response.setFrequency(basicTcCalculatorResource.getFrequency());
		response.setIrr(irr);
		response.setNoOfPrePayments(noOfPrePayments);
		response.setLoanAmount(basicTcCalculatorResource.getLoanAmount());
		response.setInstallment(basicTcCalculatorResource.getInstallment());
		response.setTerm(basicTcCalculatorResource.getTerm());
		return response;
	}

	@Override
	public BasicTcCalculatorResource calculateLoanAmount(BasicTcCalculatorResource basicTcCalculatorResource) {

		BigDecimal loanAmount = this.calculateLoanAmount(basicTcCalculatorResource.getInstallment(),
				basicTcCalculatorResource.getAnnualInterestRate(), basicTcCalculatorResource.getTerm().intValue(),
				basicTcCalculatorResource.getFrequency(), basicTcCalculatorResource.getNoOfPrePayments().intValue());

		int noOfrePayments = 0;

		BigDecimal irr = tcCommonCalcualtionService.calculateIRRforFixedInstallments(loanAmount,
				basicTcCalculatorResource.getInstallment(), basicTcCalculatorResource.getTerm().intValue(),
				basicTcCalculatorResource.getFrequency(), noOfrePayments);

		BasicTcCalculatorResource response = new BasicTcCalculatorResource();
		response.setFrequency(basicTcCalculatorResource.getFrequency());
		response.setIrr(irr);
		response.setNoOfPrePayments(basicTcCalculatorResource.getNoOfPrePayments());
		response.setLoanAmount(loanAmount);
		response.setInstallment(basicTcCalculatorResource.getInstallment());
		response.setTerm(basicTcCalculatorResource.getTerm());
		return response;
	}

	@Override
	public BasicTcCalculatorResource calculatePeriodOfLoan(BasicTcCalculatorResource basicTcCalculatorResource) {

		BigDecimal term = this.calculatePeriodOfLoan(basicTcCalculatorResource.getLoanAmount().doubleValue(),
				basicTcCalculatorResource.getInstallment().doubleValue(),
				basicTcCalculatorResource.getAnnualInterestRate().doubleValue(),
				basicTcCalculatorResource.getFrequency(), basicTcCalculatorResource.getNoOfPrePayments().intValue());

		int noOfrePayments = 0;

		BigDecimal irr = tcCommonCalcualtionService.calculateIRRforFixedInstallments(
				basicTcCalculatorResource.getLoanAmount(), basicTcCalculatorResource.getInstallment(), term.intValue(),
				basicTcCalculatorResource.getFrequency(), noOfrePayments);

		BasicTcCalculatorResource response = new BasicTcCalculatorResource();
		response.setFrequency(basicTcCalculatorResource.getFrequency());
		response.setIrr(irr);
		response.setNoOfPrePayments(basicTcCalculatorResource.getNoOfPrePayments());
		response.setLoanAmount(basicTcCalculatorResource.getLoanAmount());
		response.setInstallment(basicTcCalculatorResource.getInstallment());
		response.setTerm(term);
		return response;
	}

	@Override
	public BigDecimal calculateNoOfPrePayments(BigDecimal loanAmount, BigDecimal installment,
			BigDecimal annualInterestRate, int term, int frequency) {

		// no of down payments = l-n

		// calculate l = costOfEquipment/installment
		BigDecimal l = loanAmount.divide(installment, ROUND_OFF_VALUE1, RoundingMode.CEILING);

		// calculate n = m/(annualInterestRate/frequency)]
		// calculate m = (1-(1+annualInterestRate/frequency)^-term)
		// x = annualInterestRate/frequency
		BigDecimal interest = annualInterestRate.multiply(BigDecimal.valueOf(0.01));
		BigDecimal x = interest.divide(BigDecimal.valueOf(frequency), ROUND_OFF_VALUE1, RoundingMode.CEILING);
		// y = 1+x
		BigDecimal y = BigDecimal.ONE.add(x);
		// z = y^term
		BigDecimal z = y.pow(term);
		// a = 1/z
		BigDecimal a = BigDecimal.ONE.divide(z, ROUND_OFF_VALUE1, RoundingMode.CEILING);
		// m = 1-a
		BigDecimal m = BigDecimal.ONE.subtract(a);
		// n = m/x
		BigDecimal n = m.divide(x, ROUND_OFF_VALUE1, RoundingMode.CEILING);

		// no of down payments = l-[(1-a)/x]
		BigDecimal noOfDownPayments = l.subtract(n);

		return noOfDownPayments.setScale(ROUND_OFF_VALUE2, RoundingMode.CEILING);
	}

	@Override
	public BigDecimal calculateLoanAmount(BigDecimal installment, BigDecimal annualInterestRate, int term,
			int frequency, int noOfPrePayments) {

		// x = annualInterestRate/frequency
		BigDecimal interest = annualInterestRate.multiply(BigDecimal.valueOf(0.01));
		BigDecimal x = interest.divide(BigDecimal.valueOf(frequency), ROUND_OFF_VALUE1, RoundingMode.CEILING);
		// y = 1+x
		BigDecimal y = BigDecimal.ONE.add(x);
		// z = term - noOfPrePaidRentals
		int z = term - noOfPrePayments;
		// a = y^z
		BigDecimal a = y.pow(z);
		// b = 1/a
		BigDecimal b = BigDecimal.ONE.divide(a, ROUND_OFF_VALUE1, RoundingMode.CEILING);
		// p = noOfPrePaidRentals*x
		BigDecimal p = BigDecimal.valueOf(noOfPrePayments).multiply(x);
		// q = p + 1
		BigDecimal q = p.add(BigDecimal.ONE);
		// r = q - b
		BigDecimal r = q.subtract(b);
		// s = installment*r
		BigDecimal s = r.multiply(installment);

		// Loan Amount = s/x
		BigDecimal loanAmount = s.divide(x, ROUND_OFF_VALUE1, RoundingMode.CEILING);

		return loanAmount.setScale(ROUND_OFF_VALUE3, RoundingMode.CEILING);
	}

	@Override
	public BigDecimal calculatePeriodOfLoan(Double loanAmount, Double installment, Double annualInterestRate,
			int frequency, int noOfPrePayments) {

		// x = annualInterestRate/frequency
		Double interest = annualInterestRate * (0.01);
		Double x = interest / frequency;
		// y = 1+x
		Double y = Double.sum(1, x);
		// z = LOG(y)
		Double z = Math.log(y);
		// a = loanAmount*x
		Double a = loanAmount * x;
		// b = a/installment
		Double b = a / installment;
		// c = noOfPrePayments*x
		Double c = noOfPrePayments * x;
		// d = 1-b+ c
		Double d = 1 - b + c;
		// f = LOG(d)
		Double f = Math.log(d);
		// g = f*(-1)
		Double g = f * (-1);
		// h = g/z
		Double h = g / z;

		// periodOfLoan = h + noOfPrePayments
		Double periodOfLoan = h + noOfPrePayments;

		return BigDecimal.valueOf(periodOfLoan).setScale(ROUND_OFF_VALUE3, RoundingMode.CEILING);
	}

}
