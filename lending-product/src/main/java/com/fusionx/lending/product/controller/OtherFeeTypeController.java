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
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.OtherFeeTypeAddResource;
import com.fusionx.lending.product.resources.OtherFeeTypeUpdateResource;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.service.OtherFeeTypeService;

/**
 * Other Fee Type Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping("/other-fee-type")
@CrossOrigin("*")
public class OtherFeeTypeController extends MessagePropertyBase{

	@Autowired
	private OtherFeeTypeService otherFeeTypeService;
	
	/**
	 * @author Senitha
	 * 
	 * get all Recovery Fee Type
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllOtherFeeType(@PathVariable(value = "tenantId", required = true) String tenantId){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<OtherFeeType>isPresentOtherFeeType = otherFeeTypeService.getAll(tenantId);
		if(!isPresentOtherFeeType.isEmpty()) {
			return new ResponseEntity<>((Collection<OtherFeeType>)isPresentOtherFeeType,HttpStatus.OK);
		} else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Recovery Fee Type by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getOtherFeeTypeById(@PathVariable(value = "tenantId", required = true) String tenantId,
													   @PathVariable(value = "id", required = true) Long id){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<OtherFeeType> isPresentOtherFeeType = otherFeeTypeService.getById(tenantId, id);
		if(isPresentOtherFeeType.isPresent()) {
			return new ResponseEntity<>(isPresentOtherFeeType.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Recovery Fee Type by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getOtherFeeTypeByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	 @PathVariable(value = "code", required = true) String code){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<OtherFeeType> isPresentOtherFeeType = otherFeeTypeService.getByCode(tenantId, code);
		if(isPresentOtherFeeType.isPresent()) {
			return new ResponseEntity<>(isPresentOtherFeeType.get(), HttpStatus.OK);
		} else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Recovery Fee Type by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getOtherFeeTypeByName(@PathVariable(value = "tenantId", required = true) String tenantId,
													     @PathVariable(value = "name", required = true) String name){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<OtherFeeType> isPresentOtherFeeType = otherFeeTypeService.getByName(tenantId, name);
		if(!isPresentOtherFeeType.isEmpty()) {
			return new ResponseEntity<>(isPresentOtherFeeType, HttpStatus.OK);
		} else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Recovery Fee Type by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getOtherFeeTypeByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													       @PathVariable(value = "status", required = true) String status){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			
			List<OtherFeeType> isPresentOtherFeeType = otherFeeTypeService.getByStatus(tenantId, status);
			if(!isPresentOtherFeeType.isEmpty()) {
				return new ResponseEntity<>(isPresentOtherFeeType, HttpStatus.OK);
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
     * save Recovery Fee Type
     * @param @PathVariable{tenantId}
     * @param @RequestBody{OtherFeeTypeAddResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addOtherFeeType(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									   @Valid @RequestBody OtherFeeTypeAddResource otherFeeTypeAddResource){
		
		OtherFeeType otherFeeType = otherFeeTypeService.addOtherFeeType(tenantId, otherFeeTypeAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), otherFeeType.getCode());
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
		
	}
	
	/**
	 * @author Senitha
	 * 
     * update Recovery Fee Type
     * @param @PathVariable{tenantId}
     * @param @RequestBody{OtherFeeTypeUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateOtherFeeType(@PathVariable(value = "tenantId", required = true) String tenantId, 
									                  @PathVariable(value = "id", required = true) Long id, 
									                  @Valid @RequestBody OtherFeeTypeUpdateResource otherFeeTypeUpdateResource){
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<OtherFeeType>isPresentOtherFeeType = otherFeeTypeService.getById(tenantId, id);
		if(isPresentOtherFeeType.isPresent()) {
			otherFeeTypeUpdateResource.setId(id.toString());
			OtherFeeType otherFeeType = otherFeeTypeService.updateOtherFeeType(tenantId, otherFeeTypeUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), otherFeeType.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		} else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
}
