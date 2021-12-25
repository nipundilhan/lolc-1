package com.fusionx.lending.origination.controller;

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

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.RiskCalculation;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.CommonListAddResource;
import com.fusionx.lending.origination.resource.CreditScoreRootResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.CreditScoreCalculationService;
import com.fusionx.lending.origination.service.ValidateService;
/**
 * API Service related to Credit Score calculation
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        08-12-2021      -                        Nipun Dilhan             Created
 * <p>
 *
 */


@RestController
@RequestMapping(value = "/credit-score-calculation")
@CrossOrigin(origins = "*")
public class CreditScoreCalculationController  extends MessagePropertyBase {
	
	private String userNotFound = "common.user-not-found";
	private String commonSaved = "common.saved";
	
	private CreditScoreCalculationService creditScoreCalculationService;
    @Autowired
    public void setValidateService( CreditScoreCalculationService creditScoreCalculationService) {
        this.creditScoreCalculationService = creditScoreCalculationService;
    }
	
	
	/**
	 * calculate credit score details.
	 *
	 * @param tenantId - the tenant id
	 * @param creditScoreRootResource - the credit score calculation main request
	 * @return the SuccessAndErrorDetailsResource
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> calculateDetails(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody CreditScoreRootResource creditScoreRootResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		RiskCalculation calculateCreditScoreList = creditScoreCalculationService.calculateCreditScore(creditScoreRootResource );
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), calculateCreditScoreList);
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	
	/**
	 * save calculated credit score details.
	 *
	 * @param tenantId - the tenant id
	 * @param creditScoreRootResource - the credit score calculation main request
	 * @return the SuccessAndErrorDetailsResource
	 */
	@PostMapping(value = "/{tenantId}/lead-info/{id}")
	public ResponseEntity<Object> save(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody CreditScoreRootResource creditScoreRootResource
			,@PathVariable(value = "id") Long id) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		
		Long creditScoreId = creditScoreCalculationService.saveCreditScoreCalculation(creditScoreRootResource, tenantId, id, LogginAuthentcation.getInstance().getUserName());
		SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonSaved), creditScoreId);
		return new ResponseEntity<>(successDetailsDto,HttpStatus.CREATED);
	}
	
	/**
	 * sget credit score details.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id of risk calculation
	 * @return the SuccessAndErrorDetailsResource
	 */
	@GetMapping(value = "/{tenantId}/risk-calculation/{id}")
	public ResponseEntity<Object> getDetails(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id") Long id) {

		Optional<RiskCalculation> riskCalculationOptional = creditScoreCalculationService.getDetailsByRiskCalculationId(id);

		
		if (riskCalculationOptional.isPresent()) {
			return new ResponseEntity<>(riskCalculationOptional.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.NO_CONTENT);
		}
	}

}
