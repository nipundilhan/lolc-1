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
import com.fusionx.lending.product.domain.Segments;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetailsResource;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.SegmentsService;

/**
 * Segment Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-2880   	 FX-6515	Senitha      Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping("/segment")
@CrossOrigin("*")
public class SegmentsController extends MessagePropertyBase{
	
	@Autowired
	private SegmentsService segmentsService;
	
	/**
	 * @author Senitha
	 * 
	 * get all Segments
	 * @param @pathVariable{tenantId}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllSegments(@PathVariable(value = "tenantId", required = true) String tenantId){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Segments>isPresentSegments = segmentsService.getAll();
		if(!isPresentSegments.isEmpty()) {
			return new ResponseEntity<>((Collection<Segments>)isPresentSegments,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Segments by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getSegmentsById(@PathVariable(value = "tenantId", required = true) String tenantId,
												  @PathVariable(value = "id", required = true) Long id){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Segments> isPresentSegments = segmentsService.getById(id);
		if(isPresentSegments.isPresent()) {
			return new ResponseEntity<>(isPresentSegments.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Segments by code
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {code}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getSegmentsByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													@PathVariable(value = "code", required = true) String code){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<Segments> isPresentSegments = segmentsService.getByCode(code);
		if(isPresentSegments.isPresent()) {
			return new ResponseEntity<>(isPresentSegments.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Segments by Name
	 * @param @PathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping("/{tenantId}/name/{name}")
	public ResponseEntity<Object> getSegmentsByName(@PathVariable(value = "tenantId", required = true) String tenantId,
													@PathVariable(value = "name", required = true) String name){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<Segments>isPresentSegments = segmentsService.getByName(name);
		if(!isPresentSegments.isEmpty()) {
			return new ResponseEntity<>((Collection<Segments>)isPresentSegments,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * get Segments by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getSegmentsByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
										   			  @PathVariable(value = "status", required = true) String status){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<Segments> isPresentSegments = segmentsService.getByStatus(status);
			if(!isPresentSegments.isEmpty()) {
				return new ResponseEntity<>(isPresentSegments, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			throw new ValidateRecordException(environment.getProperty("common-status.pattern"), "status");
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
     * save Segments
     * @param @PathVariable{tenantId}
     * @param @RequestBody{AddBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addSegments(@PathVariable(value = "tenantId", required = true) String tenantId,
   									          @Valid @RequestBody AddBaseRequest addBaseRequest){
		
		Segments segments = segmentsService.addSegments(tenantId, addBaseRequest);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), segments.getCode() );
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
		
	}
	
	/**
	 * @author Senitha
	 * 
     * update Segments
     * @param @PathVariable{tenantId}
     * @param @RequestBody{UpdateBaseRequest}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateSegments(@PathVariable(value = "tenantId", required = true) String tenantId, 
							                     @PathVariable(value = "id", required = true) Long id, 
							                     @Valid @RequestBody UpdateBaseRequest updateBaseRequest){
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<Segments>isPresentSegments = segmentsService.getById(id);
		
		if(isPresentSegments.isPresent()) {
			updateBaseRequest.setId(id.toString());
			Segments segments = segmentsService.updateSegments(tenantId, updateBaseRequest);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), segments.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	

}
