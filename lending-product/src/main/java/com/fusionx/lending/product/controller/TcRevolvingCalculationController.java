package com.fusionx.lending.product.controller;

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
import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.RevolvingLoanCalculatorAddResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.TcRevolvingCalculationService;

/**
 * API Service related to Tc Revolving Calculation.
 *
 * @author Dilhan Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        05-10-2021      -               FXL-994     Dilhan Jayasinghe        Created
 * <p>
 */
@RestController
@RequestMapping(value = "/tc-revolving-calculation")
@CrossOrigin(origins = "*")
public class TcRevolvingCalculationController extends MessagePropertyBase{

	/** The tc revolving calculation service. */
	private TcRevolvingCalculationService tcRevolvingCalculationService;
	
	/**
	 * Sets the tc revolving calculation service.
	 *
	 * @param tcRevolvingCalculationService the new tc revolving calculation service
	 */
	@Autowired
	public void setTcRevolvingCalculationService(TcRevolvingCalculationService tcRevolvingCalculationService) {
		this.tcRevolvingCalculationService = tcRevolvingCalculationService;
	}

    /**
     * Save tc revolving calculator.
     *
     * @param tenantId the tenant id
     * @param revolvingLoanCalculatorAddResource the revolving loan calculator add resource
     * @return the response entity
     */
    @PostMapping(value = "/{tenantId}")
    public ResponseEntity<Object> saveTcRevolvingCalculator(@PathVariable(value = "tenantId") String tenantId,
                                                    @Valid @RequestBody RevolvingLoanCalculatorAddResource revolvingLoanCalculatorAddResource) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(COMMON_USER_NOT_FOUND));
        }
        SuccessAndErrorDetails successAndErrorDetails;
        TcHeader tcHeader = tcRevolvingCalculationService.saveRevolvingDetails(tenantId, revolvingLoanCalculatorAddResource);
        successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty(RECORD_CREATED), Long.toString(tcHeader.getId()));
        return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
    }
    
    
   
   
    /**
     * Gets the revolving details by tc header id.
     *
     * @param tenantId the tenant id
     * @param id the id
     * @return the revolving details by tc header id
     */
    @GetMapping(value = "/{tenantId}/tc-header/{id}")
    public ResponseEntity<Object> getRevolvingDetailsByTcHeaderId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {
       
        Optional<TcHeader> tcHeader = tcRevolvingCalculationService.findByTcHeaderId(id);
        if (tcHeader.isPresent()) {
            return new ResponseEntity<>(tcHeader.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Gets the revolving details by lead id.
     *
     * @param tenantId the tenant id
     * @param id the id
     * @return the revolving details by lead id
     */
    @GetMapping(value = "/{tenantId}/lead/{id}")
    public ResponseEntity<Object> getRevolvingDetailsByLeadId(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "id") Long id) {
       
        Optional<TcHeader> tcHeader = tcRevolvingCalculationService.findByLeadId(id);
        if (tcHeader.isPresent()) {
            return new ResponseEntity<>(tcHeader.get(), HttpStatus.OK);
        } else {
        	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }
}    
