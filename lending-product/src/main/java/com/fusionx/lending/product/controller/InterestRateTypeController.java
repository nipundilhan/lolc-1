package com.fusionx.lending.product.controller;

import java.util.Collection;
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

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.InterestRateType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.InterestRateTypeService;

/**
 * Interest Rate Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6601   	 FX-6508	Senitha      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping("/interest-rate-type")
@CrossOrigin("*")
public class InterestRateTypeController extends MessagePropertyBase{

	@Autowired
	private InterestRateTypeService interestRateTypeService;
	
	/**
	 * @author Senitha
	 * 
	 * get all Interest Rate Type
	 * @param @pathVariable{tenantId}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllInterestRateType(@PathVariable(value = "tenantId", required = true) String tenantId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<InterestRateType>isPresentInterestRateType = interestRateTypeService.getAll();
		if(!isPresentInterestRateType.isEmpty()) {
			return new ResponseEntity<>((Collection<InterestRateType>)isPresentInterestRateType,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Interest Rate Type by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getInterestRateTypeById(@PathVariable(value = "tenantId", required = true) String tenantId,
															  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<InterestRateType> isPresentInterestRateType = interestRateTypeService.getById(id);
		if(isPresentInterestRateType.isPresent()) {
			return new ResponseEntity<>(isPresentInterestRateType.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Interest Rate Type by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getInterestRateTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
																@PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<InterestRateType> isPresentInterestRateType = interestRateTypeService.getByCode(code);
		if(isPresentInterestRateType.isPresent()) {
			return new ResponseEntity<>(isPresentInterestRateType.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Interest Rate Type by Name
	 * @param @PathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/name/{name}")
	public ResponseEntity<Object> getInterestRateTypeByName(@PathVariable(value = "tenantId", required = true) String tenantId,
																@PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<InterestRateType>isPresentInterestRateType = interestRateTypeService.getByName(name);
		if(!isPresentInterestRateType.isEmpty()) {
			return new ResponseEntity<>((Collection<InterestRateType>)isPresentInterestRateType,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Interest Rate Type by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getInterestRateTypeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
										   			              @PathVariable(value = "status", required = true) String status){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<InterestRateType> isPresentInterestRateType = interestRateTypeService.getByStatus(status);
			if(!isPresentInterestRateType.isEmpty()) {
				return new ResponseEntity<>(isPresentInterestRateType, HttpStatus.OK);
			} else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			throw new ValidateRecordException(environment.getProperty("common-status.pattern"), "status");
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
     * save Interest Rate Type
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addInterestRateType(@PathVariable(value = "tenantId", required = true) String tenantId,
   									                  @Valid @RequestBody AddBaseRequest addBaseRequest){
		InterestRateType interestRateType = interestRateTypeService.addInterestRateType(tenantId, addBaseRequest);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), interestRateType.getCode() );
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
	 * @author Senitha
	 * 
     * update Interest Rate Type
     * @param @PathVariable{tenantId}
     * @param @RequestBody{UpdateBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateInterestRateType(@PathVariable(value = "tenantId", required = true) String tenantId, 
							                             @PathVariable(value = "id", required = true) Long id, 
							                             @Valid @RequestBody UpdateBaseRequest updateBaseRequest){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<InterestRateType>isPresentInterestRateType = interestRateTypeService.getById(id);
		
		if(isPresentInterestRateType.isPresent()) {
			updateBaseRequest.setId(id.toString());
			InterestRateType interestRateType = interestRateTypeService.updateInterestRateType(tenantId, updateBaseRequest);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), interestRateType.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
}
