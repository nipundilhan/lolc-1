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
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.NetWorthAsset;
import com.fusionx.lending.origination.domain.NetWorthLiability;
import com.fusionx.lending.origination.resource.GuarantorDetailAddResource;
import com.fusionx.lending.origination.resource.GuarantorDetailUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.GuarantorNetWorthDetailService;

/**
 * Guarantor Details Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-04-2021   		         FX-6157    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/guarantor-net-worth")
@CrossOrigin(origins = "*")
public class GuarantorNetWorthDetailController extends MessagePropertyBase{
	
	@Autowired
	private GuarantorNetWorthDetailService guarantorNetWorthDetailService;
	
	/**
	 * get Guarantor Detail by guarantorId
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {guarantorId}
	 * @return Optional
	 **/
	@GetMapping(value = "/{tenantId}/{guarantorId}")
	public ResponseEntity<Object> getNetWorthByGuarantorId(@PathVariable(value = "tenantId", required = true) String tenantId,
														 @PathVariable(value = "guarantorId", required = true) Long guarantorId){
		
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Guarantor isPresentGuarantorDetail = guarantorNetWorthDetailService.getSummaryByGuarantorId(tenantId,guarantorId);
		if (isPresentGuarantorDetail!=null) {
			return new ResponseEntity<>(isPresentGuarantorDetail,HttpStatus.OK);
		}else {
			responseMessage.setMessages(environment.getProperty("record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get Guarantor Detail by guarantorId
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {guarantorId}
	 * @return Optional
	 **/
	/*@GetMapping(value = "/{tenantId}/all/{guarantorId}")
	public ResponseEntity<Object> getAllByGuarantorId(@PathVariable(value = "tenantId", required = true) String tenantId,
												      @PathVariable(value = "guarantorId", required = true) Long guarantorId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<GuarantorDetail> isPresentGuarantorDetail = guarantorNetWorthDetailService.getAllByGuarantorId(guarantorId);
		if(!isPresentGuarantorDetail.isEmpty()) {
			return new ResponseEntity<>(isPresentGuarantorDetail, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}*/
	
	/**
     * save Guarantor Detail
     * @param @PathVariable{tenantId}
     * @param @RequestBody{GuarantorDetailAddResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/{tenantId}/{id}")
	public ResponseEntity<Object> addGuarantorDetail(@PathVariable(value = "tenantId", required = true) String tenantId,
	  												 @PathVariable(value = "id", required = true) Long id,
			   									     @Valid @RequestBody GuarantorDetailAddResource guarantorDetailAddResource){
		
		Guarantor guarantorDetail = guarantorNetWorthDetailService.addGuarantorDetail(tenantId, guarantorDetailAddResource, id);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(guarantorDetail.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
		
	}
	
	/**
     * update Guarantor Detail
     * @param @PathVariable{tenantId}
     * @param @RequestBody{GuarantorDetailUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateGuarantorDetail(@PathVariable(value = "tenantId", required = true) String tenantId, 
									                     @PathVariable(value = "id", required = true) Long id, 
									                     @Valid @RequestBody GuarantorDetailUpdateResource guarantorDetailUpdateResource){
		
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		guarantorDetailUpdateResource.setId(id.toString());
		Guarantor guarantorDetail = guarantorNetWorthDetailService.updateGuarantorDetail(tenantId, guarantorDetailUpdateResource);
		successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), guarantorDetail.getId().toString());
		return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
	}
	
	/**
	 * Find Net Worth Asset by Guarantor ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {guarantorId}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/net-worth-asset/{guarantorId}")
	public ResponseEntity<Object> getNetWorthAssetByGuarantorId(@PathVariable(value = "tenantId", required = true) String tenantId,
										   			            @PathVariable(value = "guarantorId", required = true) Long guarantorId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<NetWorthAsset> isPresentNetWorthAsset = guarantorNetWorthDetailService.getNetWorthAssetByGuarantorId(tenantId, guarantorId);
		if(!isPresentNetWorthAsset.isEmpty()) {
			return new ResponseEntity<>(isPresentNetWorthAsset, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}	
	
	/**
	 * Find Net Worth Liability by Guarantor ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {guarantorId}
	 * @return List
	 **/
	@GetMapping(value = "/{tenantId}/net-worth-liability/{guarantorId}")
	public ResponseEntity<Object> getNetWorthLiabilityByGuarantorId(@PathVariable(value = "tenantId", required = true) String tenantId,
										   			                @PathVariable(value = "guarantorId", required = true) Long guarantorId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<NetWorthLiability> isPresentNetWorthLiability = guarantorNetWorthDetailService.getNetWorthLiabilityByGuarantorId(tenantId, guarantorId);
		if(!isPresentNetWorthLiability.isEmpty()) {
			return new ResponseEntity<>(isPresentNetWorthLiability, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}	

}
