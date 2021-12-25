package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
/**
 * Risk RatingLevelDetails
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-338 		 FXL-682 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessRiskType;
import com.fusionx.lending.origination.domain.RiskRatingLevels;
import com.fusionx.lending.origination.resource.RiskRatingLevelAddResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.RiskRatingLevelsService;

@RestController
@RequestMapping(value = "/risk-rating-level")
@CrossOrigin(origins = "*")
public class RiskRatingLevelsController extends MessagePropertyBase {

	@Autowired
	Environment environment;

	@Autowired
	public RiskRatingLevelsService riskRatingLevelsService;

	/**
	 * get all RiskRatingLevels
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllRiskRatingLevels(
			@PathVariable(value = "tenantId", required = true) String tenantId) {

		List<RiskRatingLevels> riskRatingLevels = riskRatingLevelsService.getAll();
		int size = riskRatingLevels.size();
		if (size > 0) {
			return new ResponseEntity<>(riskRatingLevels, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get RiskRatingLevels by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getRiskRatingLevelsById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {

		Optional<RiskRatingLevels> isPresentRiskRatingLevels = riskRatingLevelsService.getById(id);
		if (isPresentRiskRatingLevels.isPresent()) {
			return new ResponseEntity<>(isPresentRiskRatingLevels.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get RiskRatingLevels by risk type id
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {riskTypeId}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/riskType/{riskTypeId}")
	public ResponseEntity<Object> getRiskRatingLevelsByRiskTypeId(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "riskTypeId", required = true)Long riskTypeId) {

		List<RiskRatingLevels> isPresentRiskRatingLevels = riskRatingLevelsService.getByRiskTypeId(riskTypeId);
		if (!isPresentRiskRatingLevels.isEmpty()) {
			return new ResponseEntity<>(isPresentRiskRatingLevels, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get RiskRatingLevels by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getRiskRatingLevelsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<RiskRatingLevels> isPresentRiskRatingLevels = riskRatingLevelsService.getByStatus(status);
			int size = isPresentRiskRatingLevels.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentRiskRatingLevels, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(COMMON_INVALID_VALUE));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * save RiskRatingLevels
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{RiskRatingLevelAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addRiskRatingLevels(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody RiskRatingLevelAddResource riskRatingLevelAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource();
		RiskRatingLevels riskRatingLevels = riskRatingLevelsService.addRiskRatingLevels(tenantId,
				riskRatingLevelAddResource);
		successAndErrorDetails = new SuccessAndErrorDetailsResource(environment.getProperty(RECORD_CREATED),
				Long.toString(riskRatingLevels.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

}
