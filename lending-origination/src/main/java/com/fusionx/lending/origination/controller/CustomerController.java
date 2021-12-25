package com.fusionx.lending.origination.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.resource.CustomerResource;
import com.fusionx.lending.origination.resource.CustomerResponseResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.resource.UpdateCustomerResource;
import com.fusionx.lending.origination.service.CustomerService;
	

/**
 * Customer Controller
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No   	 Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-04-2021   							 		Thamokshi        Created
 *    
 * 	  2   04-08-2021      FXL-381        FXL-424     	Piyumi     	 Modified 
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/customer")
@CrossOrigin(origins = "*")
public class CustomerController extends MessagePropertyBase {


	private String userNotFound = "common.user-not-found";
	private String commonUpdated = "common.updated";
	private String recordNotFound = "common.record-not-found";
	
	@Autowired
	CustomerService service;
	
	/**
	 * 
	 * @param tenantId
	 * @param customerResource
	 * @return
	 */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> add(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									       		  @Valid @RequestBody CustomerResource customerResource){
		Customer customer = service.save(tenantId, customerResource);
		CustomerResponseResource customerResponseResource=new CustomerResponseResource();
		customerResponseResource.setCustomerId(customer.getId());
		customerResponseResource.setLeadId(customer.getLead().getId());
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), customerResponseResource);
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAll(@PathVariable(value = "tenantId", required = true) String tenantId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Customer>isPresentCultivationCategory = service.getAll();
		if(!isPresentCultivationCategory.isEmpty()) {
			return new ResponseEntity<>(isPresentCultivationCategory,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get cultivation category by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Customer> isPresentCultivationCategory = service.getById(id);
		if(isPresentCultivationCategory.isPresent()) {
			return new ResponseEntity<>(isPresentCultivationCategory.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> update(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "id", required = true) Long id,
			@Valid @RequestBody UpdateCustomerResource updateCustomerResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new UserNotFound(environment.getProperty(userNotFound));
		if (service.existsById(id)) {
			service.update(tenantId, LogginAuthentcation.getInstance().getUserName(), id, updateCustomerResource);
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(commonUpdated));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.OK);
		} else {
			SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty(recordNotFound));
			return new ResponseEntity<>(successDetailsDto, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@GetMapping("/{tenantId}/lead-id/{leadId}")
	public ResponseEntity<Object> getByLeadId(@PathVariable(value = "tenantId", required = true) String tenantId,@PathVariable(value = "leadId", required = true) Long leadId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Customer> cus = service.getByLeadId(leadId);
		if(!cus.isEmpty()) {
			return new ResponseEntity<>(cus,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

}
