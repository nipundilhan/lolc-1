package com.fusionx.lending.product.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.TcAmortizationPaymentSchedule;
import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.FixedInstallmentLoanCalculationDetailsResponse;
import com.fusionx.lending.product.resources.FixedInstallmentPaymentScheduleResponse;
import com.fusionx.lending.product.resources.FixedLoanCalculatorAddResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.resources.TaxCustomerResponse;
import com.fusionx.lending.product.resources.TaxDetailsResponse;
import com.fusionx.lending.product.resources.TcFixedInterestRateCalculateRequest;
import com.fusionx.lending.product.service.FixedLoanCalculatorService;
import com.fusionx.lending.product.service.TaxCalculationService;
import com.fusionx.lending.product.service.TcCommonCalcualtionService;

/**
 * API Service Fixed Installment Calculation
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

@RestController
@RequestMapping(value = "/fixed-installment-calculator")
@CrossOrigin(origins = "*")
public class FixedLoanCalculatorController extends MessagePropertyBase{
	

	private FixedLoanCalculatorService fixedLoanCalculatorService;
		
    @Autowired
    public void setFixedLoanCalculatorService(FixedLoanCalculatorService fixedLoanCalculatorService) {
        this.fixedLoanCalculatorService = fixedLoanCalculatorService;
    }	
    
    @Autowired    
	private  TaxCalculationService taxCalculationService;
	
    /**
     * Gets fixed installment calculation details
     *
     * @param tenantId the id of tenant
     * @param req the FixedLoanCalculatorAddResource
     * @return the fixed installment calculation details response
     */
 	@PostMapping(value = "/{tenantId}/calculate-installment")
 	public ResponseEntity<Object> fixedInstallment(@PathVariable(value = "tenantId") String tenantId,
 			                                          @Valid @RequestBody FixedLoanCalculatorAddResource fixedLoanCalculatorAddResource) { 		
 		
 		FixedInstallmentLoanCalculationDetailsResponse calculateDetails = fixedLoanCalculatorService.calculateDetails(tenantId , fixedLoanCalculatorAddResource);
 		
		return new ResponseEntity<>(calculateDetails,HttpStatus.CREATED);
 	}
 	
    /**
     * Gets payment schedule
     *
     * @param tenantId the id of tenant
     * @param req the FixedLoanCalculatorAddResource
     * @return the payment schedule details response
     */
 	@PostMapping(value = "/{tenantId}/payment-schedule")
 	public ResponseEntity<Object> paymentSchedule(@PathVariable(value = "tenantId") String tenantId,
 			                                          @Valid @RequestBody FixedLoanCalculatorAddResource fixedLoanCalculatorAddResource) { 		
 		
 		List<FixedInstallmentPaymentScheduleResponse> paymentSchedule = fixedLoanCalculatorService.paymentSchedule(tenantId,fixedLoanCalculatorAddResource);
 		
		return new ResponseEntity<>(paymentSchedule,HttpStatus.CREATED);
 	}
 	
    /**
     * save fixed installment calculation details
     *
     * @param tenantId the id of tenant
     * @param req the FixedLoanCalculatorAddResource
     * @return tc header id
     */
 	@PostMapping(value = "/{tenantId}/")
 	public ResponseEntity<Object> save(@PathVariable(value = "tenantId") String tenantId,
 			                                          @Valid @RequestBody FixedLoanCalculatorAddResource fixedLoanCalculatorAddResource) { 		
 		
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        
        Long tcHeaderId = fixedLoanCalculatorService.save(fixedLoanCalculatorAddResource, tenantId, LogginAuthentcation.getInstance().getUserName());
 		

		return new ResponseEntity<>(tcHeaderId,HttpStatus.CREATED);
 	}
 	
    /**
     * Gets the tc header by id.
     *
     * @param tenantId the tenant id
     * @param id the id
     * @return the tc header
     */
 	@GetMapping(value = "/{tenantId}/tc-header/{id}")
 	public ResponseEntity<Object> getByTcHeaderId(@PathVariable(value = "tenantId") String tenantId,
 			@PathVariable(value = "id") Long id) { 		
 		

 		Optional<TcHeader> tcHeaderOptional = fixedLoanCalculatorService.findByTcHeaderId(id);
        
        if (tcHeaderOptional.isPresent()) {
            return new ResponseEntity<>(tcHeaderOptional.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
 	}
 	
    /**
     * Gets the tc header by lead id.
     *
     * @param tenantId the tenant id
     * @param id the lead id
     * @return the tc header
     */
 	@GetMapping(value = "/{tenantId}/lead/{id}")
 	public ResponseEntity<Object> getByLeadId(@PathVariable(value = "tenantId") String tenantId,
 			@PathVariable(value = "id") Long id) { 		
 		

 		Optional<TcHeader> tcHeaderOptional = fixedLoanCalculatorService.findByLeadId(id);
        
        if (tcHeaderOptional.isPresent()) {
            return new ResponseEntity<>(tcHeaderOptional.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
 	}
 	
 	
 	@PostMapping(value = "/{tenantId}/payment-schedule-temp")
 	public ResponseEntity<Object> paymentScheduleTemp(@PathVariable(value = "tenantId") String tenantId,
 			                                          @Valid @RequestBody FixedLoanCalculatorAddResource fixedLoanCalculatorAddResource) { 		
 		
 		List<String> paymentSchedule = fixedLoanCalculatorService.paymentScheduleTemp(tenantId , fixedLoanCalculatorAddResource);
 		
		return new ResponseEntity<>(paymentSchedule,HttpStatus.CREATED);
 	}
 	
 	
 	@PostMapping(value = "/{tenantId}/taxDetails")
 	public ResponseEntity<Object> getTaxDetails(@PathVariable(value = "tenantId") String tenantId,
 			@Valid @RequestBody FixedLoanCalculatorAddResource fixedLoanCalculatorAddResource) { 		
 		

 		List<TaxDetailsResponse> taxDetailsList = fixedLoanCalculatorService.getTaxDetails(tenantId, fixedLoanCalculatorAddResource);     

        return new ResponseEntity<>(taxDetailsList, HttpStatus.OK);

 	}
 	
 	
    /**
     * Gets the tc header by lead id.
     *
     * @param tenantId the tenant id
     * @param id the lead id
     * @return the tc header
     */
 	@GetMapping(value = "/{tenantId}/customer-lead/{id}")
 	public ResponseEntity<Object> getCustByLeadId(@PathVariable(value = "tenantId") String tenantId,
 			@PathVariable(value = "id") Long id) { 		
 		

 		Optional<TaxCustomerResponse> customerOptional = taxCalculationService.findCustomerDetails(id.toString(), tenantId);
        
        if (customerOptional.isPresent()) {
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
 	}
 	

}
