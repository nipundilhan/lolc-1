package com.fusionx.lending.product.controller;

/**
 * Tax Profile Service
 * @author 	KilasiG
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-11-2019   FX-1545       FX-2175    KilasiG      Created
 *    
 ********************************************************************************************************
 */

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.product.Constants;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.TaxProfile;
import com.fusionx.lending.product.enums.TaxProfileStatus;
import com.fusionx.lending.product.exception.PageableLengthException;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AddTaxProfileRequestResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.resources.UpdateTaxProfileRequestResource;
import com.fusionx.lending.product.service.TaxProfileService;

@RestController
@RequestMapping(value = "/tax-profile")
@CrossOrigin(origins = "*")
public class TaxProfileController {

	@Autowired
	Environment environment;

	@Autowired
	public TaxProfileService taxProfileService;

	private String recordNotFound = "common.record-not-found";
	String pageableLength = "common.pageable-length";
	private String userNotFound = "common.user-not-found";

	/**
	 * get all tax Profile details
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 *
	 * @return List<Type>
	 */

	@GetMapping(value = "/{tenantId}/all")
	public Page<TaxProfile> getAllTaxProfiles(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PageableDefault(size = 10) Pageable pageable) {
		if (pageable.getPageSize() > Constants.MAX_PAGEABLE_LENGTH)
			throw new PageableLengthException(environment.getProperty(pageableLength));
		return taxProfileService.findAll(pageable);
	}

	/**
	 * get Tax Profiles by id
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{taxProfileId}
	 *
	 * @return Optional<TaxProfile> taxProfile
	 */
	@GetMapping(value = "/{tenantId}/{taxProfileId}")
	public ResponseEntity<Object> getTaxProfileById(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "taxProfileId", required = true) Long taxProfileId) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<TaxProfile> taxProfile = taxProfileService.findById(taxProfileId);
		if (taxProfile.isPresent()) {
			return new ResponseEntity<>(taxProfile.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Tax Profiles by status
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 *
	 * @return Optional<Collection<TaxProfile>> taxProfiles
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getTaxProfileByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals(TaxProfileStatus.ACTIVE.toString()) || status.equals(TaxProfileStatus.INACTIVE.toString())) {
			Optional<Collection<TaxProfile>> taxProfiles = taxProfileService.findByStatus(status);
			if (taxProfiles.isPresent()) {
				return new ResponseEntity<>(taxProfiles.get(), HttpStatus.OK);
			} else {
				responseMessage.setMessages(environment.getProperty(recordNotFound));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * addTaxProfileRequestResource save a Tax Profile
	 *
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{AddTaxProfileRequestResource}
	 *
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addTaxProfile(@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody AddTaxProfileRequestResource addTaxProfileRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		addTaxProfileRequestResource.setTaxProfileCreatedUser(LogginAuthentcation.getInstance().getUserName());

		TaxProfile taxProfile = taxProfileService.saveTaxProfile(tenantId, addTaxProfileRequestResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(
				environment.getProperty("common.saved"), Long.toString(taxProfile.getId()));
		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	/**
	 * update Tax Profile
	 *
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{taxProfileId}
	 * @param @RequestBody{UpdateTaxProfileRequestResource}
	 *
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{taxProfileId}")
	public ResponseEntity<Object> updateTaxProfile(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "taxProfileId", required = true) Long taxProfileId,
			@Valid @RequestBody UpdateTaxProfileRequestResource updateTaxProfileRequestResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		updateTaxProfileRequestResource.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		Optional<TaxProfile> isPresentTaxProfile = taxProfileService.findById(taxProfileId);
		if (isPresentTaxProfile.isPresent()) {
			updateTaxProfileRequestResource.setId(taxProfileId.toString());
			// updateTaxProfileRequestResource.setTenantId(tenantId); -- Commented by
			// Senitha on 01-05-2020
			TaxProfile taxProfile = taxProfileService.updateTaxProfile(tenantId, updateTaxProfileRequestResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(
					environment.getProperty("common.updated"), taxProfile.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
