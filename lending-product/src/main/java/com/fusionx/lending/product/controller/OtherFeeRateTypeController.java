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
import com.fusionx.lending.product.domain.OtherFeeRateType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.OtherFeeRateTypeService;

/**
 * Other Fee Rate Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6605   	 FX-6510	Senitha      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping("/other-fee-rate-type")
@CrossOrigin("*")
public class OtherFeeRateTypeController extends MessagePropertyBase{

	@Autowired
	private OtherFeeRateTypeService otherFeeRateTypeService;
	
	/**
	 * @author Senitha
	 * 
	 * get all Other Fee Rate Type
	 * @param @pathVariable{tenantId}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllOtherFeeRateType(@PathVariable(value = "tenantId", required = true) String tenantId){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<OtherFeeRateType>isPresentOtherFeeRateType = otherFeeRateTypeService.getAll();
		if(!isPresentOtherFeeRateType.isEmpty()) {
			return new ResponseEntity<>((Collection<OtherFeeRateType>)isPresentOtherFeeRateType,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Other Fee Rate Type by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getOtherFeeRateTypeById(@PathVariable(value = "tenantId", required = true) String tenantId,
															  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<OtherFeeRateType> isPresentOtherFeeRateType = otherFeeRateTypeService.getById(id);
		if(isPresentOtherFeeRateType.isPresent()) {
			return new ResponseEntity<>(isPresentOtherFeeRateType.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Other Fee Rate Type by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getOtherFeeRateTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
																@PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<OtherFeeRateType> isPresentOtherFeeRateType = otherFeeRateTypeService.getByCode(code);
		if(isPresentOtherFeeRateType.isPresent()) {
			return new ResponseEntity<>(isPresentOtherFeeRateType.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Other Fee Rate Type by Name
	 * @param @PathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/name/{name}")
	public ResponseEntity<Object> getOtherFeeRateTypeByName(@PathVariable(value = "tenantId", required = true) String tenantId,
																@PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<OtherFeeRateType>isPresentOtherFeeRateType = otherFeeRateTypeService.getByName(name);
		if(!isPresentOtherFeeRateType.isEmpty()) {
			return new ResponseEntity<>((Collection<OtherFeeRateType>)isPresentOtherFeeRateType,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Other Fee Rate Type by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getOtherFeeRateTypeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
										   			              @PathVariable(value = "status", required = true) String status){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<OtherFeeRateType> isPresentOtherFeeRateType = otherFeeRateTypeService.getByStatus(status);
			if(!isPresentOtherFeeRateType.isEmpty()) {
				return new ResponseEntity<>(isPresentOtherFeeRateType, HttpStatus.OK);
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
     * save Other Fee Rate Type
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addOtherFeeRateType(@PathVariable(value = "tenantId", required = true) String tenantId,
   									          @Valid @RequestBody AddBaseRequest addBaseRequest){
		
		OtherFeeRateType otherFeeRateType = otherFeeRateTypeService.addOtherFeeRateType(tenantId, addBaseRequest);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), otherFeeRateType.getCode() );
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
		
	}
	
	/**
	 * @author Senitha
	 * 
     * update Other Fee Rate Type
     * @param @PathVariable{tenantId}
     * @param @RequestBody{UpdateBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateOtherFeeRateType(@PathVariable(value = "tenantId", required = true) String tenantId, 
							                     @PathVariable(value = "id", required = true) Long id, 
							                     @Valid @RequestBody UpdateBaseRequest updateBaseRequest){
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<OtherFeeRateType>isPresentOtherFeeRateType = otherFeeRateTypeService.getById(id);
		
		if(isPresentOtherFeeRateType.isPresent()) {
			updateBaseRequest.setId(id.toString());
			OtherFeeRateType otherFeeRateType = otherFeeRateTypeService.updateOtherFeeRateType(tenantId, updateBaseRequest);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), otherFeeRateType.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
}
