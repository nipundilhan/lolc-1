package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
/**
 * Risk Authorities
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-338 		 FXL-682 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalCategoryProductDetails;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ApprovalCategoryProductDetailsService;

@RestController
@RequestMapping(value = "/approval-category-product-details")
@CrossOrigin(origins = "*")
public class ApprovalCategoryProductDetailsController extends MessagePropertyBase {

	@Autowired
	public ApprovalCategoryProductDetailsService approvalCategoryProductDetailsService;

	/**
	 * get all ApprovalCategoryProductDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllApprovalCategoryProductDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId) {

		List<ApprovalCategoryProductDetails> approvalCategoryProductDetails = approvalCategoryProductDetailsService
				.getAll();
		int size = approvalCategoryProductDetails.size();
		if (size > 0) {
			return new ResponseEntity<>(approvalCategoryProductDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryProductDetails by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getApprovalCategoryProductDetailsById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {

		Optional<ApprovalCategoryProductDetails> isPresentApprovalCategoryProductDetails = approvalCategoryProductDetailsService
				.getById(id);
		if (isPresentApprovalCategoryProductDetails.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategoryProductDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryProductDetails by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getApprovalCategoryProductDetailsByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {

		Optional<ApprovalCategoryProductDetails> isPresentApprovalCategoryProductDetails = approvalCategoryProductDetailsService
				.getByCode(code);
		if (isPresentApprovalCategoryProductDetails.isPresent()) {
			return new ResponseEntity<>(isPresentApprovalCategoryProductDetails.get(), HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryProductDetails by name
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getApprovalCategoryProductDetailsByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {

		List<ApprovalCategoryProductDetails> isPresentApprovalCategoryProductDetails = approvalCategoryProductDetailsService
				.getByName(name);
		if (!isPresentApprovalCategoryProductDetails.isEmpty()) {
			return new ResponseEntity<>(isPresentApprovalCategoryProductDetails, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get ApprovalCategoryProductDetails by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getApprovalCategoryProductDetailsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<ApprovalCategoryProductDetails> isPresentApprovalCategoryProductDetails = approvalCategoryProductDetailsService
					.getByStatus(status);
			int size = isPresentApprovalCategoryProductDetails.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentApprovalCategoryProductDetails, HttpStatus.OK);
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
	 * save ApprovalCategoryProductDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addApprovalCategoryProductDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody CommonAddResource commonAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}

		ApprovalCategoryProductDetails approvalCategoryProductDetails = approvalCategoryProductDetailsService
				.addApprovalCategoryProductDetails(tenantId, commonAddResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_CREATED), Long.toString(approvalCategoryProductDetails.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update ApprovalCategoryProductDetails
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateApprovalCategoryProductDetails(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody CommonUpdateResource commonUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UsernameNotFoundException(environment.getProperty(NOT_FOUND));
		}
		ApprovalCategoryProductDetails approvalCategoryProductDetails = approvalCategoryProductDetailsService
				.updateApprovalCategoryProductDetails(tenantId, id, commonUpdateResource);
		SuccessAndErrorDetailsResource successAndErrorDetails = new SuccessAndErrorDetailsResource(
				environment.getProperty(RECORD_UPDATED), approvalCategoryProductDetails.getId().toString());
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
	}
}
