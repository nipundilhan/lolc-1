package com.fusionx.lending.origination.controller;

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

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.ProductCategoryProductMap;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.ProductCategoryProductMapAddResource;
import com.fusionx.lending.origination.resource.ProductCategoryProductMapUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ProductCategoryProductMapService;

@RestController
@RequestMapping(value = "/product-category-product-map")
@CrossOrigin(origins = "*")
public class ProductCategoryProductMapController extends MessagePropertyBase{
	
	@Autowired
	private ProductCategoryProductMapService productCategoryProductMapService;
	
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllProductCategoryProductMap(@PathVariable(value = "tenantId", required = true) String tenantId){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		List<ProductCategoryProductMap> isPresentProductCategoryProductMap = productCategoryProductMapService.getAll();
		if(!isPresentProductCategoryProductMap.isEmpty()) {
			return new ResponseEntity<>((Collection<ProductCategoryProductMap>)isPresentProductCategoryProductMap,HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getProductCategoryProductMapById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		Optional<ProductCategoryProductMap> isPresentProductCategoryProductMap = productCategoryProductMapService.getById(id);
		if(isPresentProductCategoryProductMap.isPresent()) {
			return new ResponseEntity<>(isPresentProductCategoryProductMap.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	

	
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getProductCategoryProductMapByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
													   			      @PathVariable(value = "status", required = true) String status){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<ProductCategoryProductMap> isPresentProductCategoryProductMap = productCategoryProductMapService.getByStatus(status);
			if(!isPresentProductCategoryProductMap.isEmpty()) {
				return new ResponseEntity<>(isPresentProductCategoryProductMap, HttpStatus.OK);
			}
			else {
				responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty("common-status.pattern"), "message");
		}
	}
	
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addProductCategoryProductMap(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									       		  @Valid @RequestBody ProductCategoryProductMapAddResource productCategoryProductMapAddResource){
		ProductCategoryProductMap productCategoryProductMap = productCategoryProductMapService.addProductCategoryProductMap(tenantId, productCategoryProductMapAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(productCategoryProductMap.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateProductCategoryProductMap(@PathVariable(value = "tenantId", required = true) String tenantId, 
												                 @PathVariable(value = "id", required = true) Long id, 
												                 @Valid @RequestBody ProductCategoryProductMapUpdateResource productCategoryProductMapUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<ProductCategoryProductMap>isPresentProductCategoryProductMap = productCategoryProductMapService.getById(id);		
		if(isPresentProductCategoryProductMap.isPresent()) {
			ProductCategoryProductMap productCategoryProductMap = productCategoryProductMapService.updateProductCategoryProductMap(tenantId, id, productCategoryProductMapUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), productCategoryProductMap.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
