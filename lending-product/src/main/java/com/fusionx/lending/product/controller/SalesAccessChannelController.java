package com.fusionx.lending.product.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.SalesAccessChannel;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.SalesAccessChannelService;

/**
 * Sales Access Channel service
 * 
 * @author Piyumi
 * @Dat 07-07-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/sales-access-channel")
@CrossOrigin(origins = "*")
public class SalesAccessChannelController {

	@Autowired
	Environment environment;

	@Autowired
	public SalesAccessChannelService salesAccessChannelService;

	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound = "common.user-not-found";

	/**
	 * get all Sales Access Channel details
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
	@GetMapping(value = "/{tenantId}/all")
	public ResponseEntity<Object> getAllSalesAccessChannel(
			@PathVariable(value = "tenantId", required = true) String tenantId) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<SalesAccessChannel> salesAccessChannels = salesAccessChannelService.getAll();
		int size = salesAccessChannels.size();
		if (size > 0) {
			return new ResponseEntity<>(salesAccessChannels, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Sales Access Channel by id
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{id}
	 * @return Option
	 */
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getSalesAccessChannelById(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id) {

		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		Optional<SalesAccessChannel> isPresentSalesAccessChannel = salesAccessChannelService.findById(id);
		if (isPresentSalesAccessChannel.isPresent()) {
			return new ResponseEntity<>(isPresentSalesAccessChannel.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Sales Access Channel by code
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable           {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getSalesAccessChannelByCode(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "code", required = true) String code) {

		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<SalesAccessChannel> isPresentSalesAccessChannel = salesAccessChannelService.findByCode(code);
		if (isPresentSalesAccessChannel.isPresent()) {
			return new ResponseEntity<>(isPresentSalesAccessChannel.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Sales Access Channel by name
	 * 
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable{name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getSalesAccessChannelByName(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "name", required = true) String name) {

		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<SalesAccessChannel> isPresentSalesAccessChannel = salesAccessChannelService.findByName(name);
		if (!isPresentSalesAccessChannel.isEmpty()) {
			return new ResponseEntity<>(isPresentSalesAccessChannel, HttpStatus.OK);
		} else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * get Sales Access Channel by status
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{status}
	 * @return List
	 */
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getSalesAccessChannelByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();

		if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
			List<SalesAccessChannel> isPresentSalesAccessChannel = salesAccessChannelService.findByStatus(status);
			int size = isPresentSalesAccessChannel.size();
			if (size > 0) {
				return new ResponseEntity<>(isPresentSalesAccessChannel, HttpStatus.OK);
			} else {
				responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * save Sales Access Channel
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonAddResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PostMapping(value = "/{tenantId}")
	public ResponseEntity<Object> addSalesAccessChannel(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@Valid @RequestBody CommonAddResource commonAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(userNotFound));
		}

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		SalesAccessChannel salesAccessChannel = salesAccessChannelService.addSalesAccessChannel(tenantId,
				commonAddResource);
		successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),
				Long.toString(salesAccessChannel.getId()));
		return new ResponseEntity<>(successAndErrorDetails, HttpStatus.CREATED);
	}

	/**
	 * update Sales Access Channel
	 * 
	 * @param @PathVariable{tenantId}
	 * @param @RequestBody{CommonUpdateResource}
	 * @return SuccessAndErrorDetailsDto
	 */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateSalesAccessChannel(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody CommonUpdateResource commonUpdateResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty()) {
			throw new UserNotFound(environment.getProperty(userNotFound));
		}

		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
		Optional<SalesAccessChannel> isPresentSalesAccessChannel = salesAccessChannelService.findById(id);
		if (isPresentSalesAccessChannel.isPresent()) {
			SalesAccessChannel salesAccessChannel = salesAccessChannelService.updateSalesAccessChannel(tenantId, id,
					commonUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"),
					salesAccessChannel.getId().toString());
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.OK);
		} else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
