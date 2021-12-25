package com.fusionx.lending.product.controller;

import java.util.ArrayList;

/**
 * Service Access Channel Controller Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021             				Nipun        Created
 *    
 ********************************************************************************************************
 */


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
import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.domain.ProductSegment;
import com.fusionx.lending.product.domain.ServiceAccessChannel;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.resources.TaxEventResponse;
import com.fusionx.lending.product.service.BrandService;
import com.fusionx.lending.product.service.ServiceAccessChannelService;
import com.fusionx.lending.product.service.ValidationService;

@RestController
@RequestMapping(value = "/service-access-channel")
@CrossOrigin(origins = "*")
public class ServiceAccessChannelController {
	
	@Autowired
	Environment environment;
	
	@Autowired
	private ServiceAccessChannelService serviceAccessChannelService;
	
	@Autowired
	private ValidationService validationService;
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	
    
	/**
	 * get all Service Access Channel
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllServiceAccessChannel(@PathVariable(value = "tenantId", required = true) String tenantId){
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<ServiceAccessChannel> serviceAccessChannels = serviceAccessChannelService.getAll();
		int size = serviceAccessChannels.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<ServiceAccessChannel>) serviceAccessChannels,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
	/**
	 * get ServiceAccessChannel by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getServiceAccessChannelByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ServiceAccessChannel> isPresentServiceAccessChannel = serviceAccessChannelService.getByCode(code);
		if(isPresentServiceAccessChannel.isPresent()) {
			return new ResponseEntity<>(isPresentServiceAccessChannel.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
 	@GetMapping(value = "/{tenantId}/{serviceAccessChannelId}")
 	public ResponseEntity<Object> getProductSegmentsById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "serviceAccessChannelId", required = true) Long serviceAccessChannelId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<ServiceAccessChannel> isServiceAccessChannel = serviceAccessChannelService.findById(serviceAccessChannelId);
	 	if (isServiceAccessChannel.isPresent()) {
	 		return new ResponseEntity<>(isServiceAccessChannel.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
    
	
	
	/**
	 * get ServiceAccessChannel by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getServiceAccessChannelByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ServiceAccessChannel> serviceAccessChannels = serviceAccessChannelService.getByName(name);
		
		int size = serviceAccessChannels.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<ServiceAccessChannel>) serviceAccessChannels,HttpStatus.OK);
		} 
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
 	/**
     * get ServiceAccessChannel by status
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return List
     */
 	@GetMapping(value = "/{tenantId}/status/{status}")
 	public ResponseEntity<Object> getServiceAccessChannelByStatus(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "status", required = true) String status) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		
 		if(status.equals("ACTIVE") || status.equals("INACTIVE")){
		 	List<ServiceAccessChannel> serviceAccessChannels = serviceAccessChannelService.getByStatus(status);
		 	int size = serviceAccessChannels.size();
		 	if (size>0) {
		 		return new ResponseEntity<>(serviceAccessChannels, HttpStatus.OK);
		 	} 
		 	else {
		 		responseMessage.setMessages(RECORD_NOT_FOUND);
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		 	}
 		}
 		else{
	 		responseMessage.setMessages(environment.getProperty("invalid-status"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
 	
 	
 	/**
     * save ServiceAccessChannel
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addServiceAccessChannel(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                          @Valid @RequestBody CommonAddResource commonAddResource) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails();
 		ServiceAccessChannel serviceAccessChannel = serviceAccessChannelService.addServiceAccessChannel(tenantId, commonAddResource);
	 	successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(serviceAccessChannel.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
 	
 	
	/**
     * update ServiceAccessChannel 
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateServiceAccessChannel(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                             @PathVariable(value = "id", required = true) Long id, 
			                                             @Valid @RequestBody CommonUpdateResource commonUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<ServiceAccessChannel> isPresentServiceAccessChannel = serviceAccessChannelService.findById(id);
		if(isPresentServiceAccessChannel.isPresent()) {
			ServiceAccessChannel serviceAccessChannel = serviceAccessChannelService.updateServiceAccessChannel(tenantId, id, commonUpdateResource);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), serviceAccessChannel.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
 	@GetMapping(value = "/{tenantId}/tax-event/{code}")
 	public ResponseEntity<Object> getTaxDetails(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "code", required = true) String code) {
 		
 		TaxEventResponse taxEvent = validationService.getTaxEvent(tenantId, code);
 		return new ResponseEntity<>(taxEvent, HttpStatus.OK);
 	}

    

}