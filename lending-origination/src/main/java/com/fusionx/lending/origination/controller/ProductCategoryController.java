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
import com.fusionx.lending.origination.domain.ProductCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.ProductCategoryAddResource;
import com.fusionx.lending.origination.resource.ProductCategoryUpdateResource;
import com.fusionx.lending.origination.resource.SuccessAndErrorDetailsResource;
import com.fusionx.lending.origination.service.ProductCategoryService;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping(value = "/product-category")
@CrossOrigin(origins = "*")
@Timed
public class ProductCategoryController extends MessagePropertyBase{
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	
	
	@GetMapping("/{tenantId}/all")
	public ResponseEntity<Object> getAllProductCategory(@PathVariable(value = "tenantId", required = true) String tenantId){
		List<ProductCategory> list = productCategoryService.findAll();
	      if(!list.isEmpty()) 
          return new ResponseEntity<>(list, HttpStatus.OK);
      else {
          return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
      }
	}
	
	
	
	@GetMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> getProductCategoryById(@PathVariable(value = "tenantId", required = true) String tenantId,
													         	  @PathVariable(value = "id", required = true) Long id){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		
		Optional<ProductCategory> isPresentProductCategory = productCategoryService.findById(id);
		if(isPresentProductCategory.isPresent()) {
			return new ResponseEntity<>(isPresentProductCategory.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/code/{code}")
	public ResponseEntity<Object> getProductCategoryByCode(@PathVariable(value = "tenantId", required = true) String tenantId,
													   	            @PathVariable(value = "code", required = true) String code){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		
		Optional<ProductCategory> isPresentProductCategory = productCategoryService.findByCode(code);
		if(isPresentProductCategory.isPresent()) {
			return new ResponseEntity<>(isPresentProductCategory.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/name/{name}")
	public ResponseEntity<Object> getProductCategoryByName(@PathVariable(value = "tenantId", required = true) String tenantId,
															        @PathVariable(value = "name", required = true) String name){
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		
		List<ProductCategory> isPresentProductCategory = productCategoryService.findByName(name);
		if(!isPresentProductCategory.isEmpty()) {
			return new ResponseEntity<>(isPresentProductCategory, HttpStatus.OK);
		}
		else {
			responseMessage.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getProductCategoryByStatus(@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			
			List<ProductCategory> list = productCategoryService.findByStatus(status);
			if (!list.isEmpty())
				return new ResponseEntity<>(list, HttpStatus.OK);
			else {
				return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
			}
		}else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
	        throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
	}
	}
	
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<Object> addProductCategory(@PathVariable(value = "tenantId", required = true) String tenantId,
			   									       		  @Valid @RequestBody ProductCategoryAddResource productCategoryAddResource){
		ProductCategory productCategory = productCategoryService.save(tenantId, productCategoryAddResource);
		SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_CREATED), Long.toString(productCategory.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "{tenantId}/{id}")
	public ResponseEntity<Object> updateProductCategory(@PathVariable(value = "tenantId", required = true) String tenantId, 
												                 @PathVariable(value = "id", required = true) Long id, 
												                 @Valid @RequestBody ProductCategoryUpdateResource productCategoryUpdateResource){
		SuccessAndErrorDetailsResource successAndErrorDetailsResource=new SuccessAndErrorDetailsResource();
		Optional<ProductCategory>isPresentProductCategory = productCategoryService.findById(id);		
		if(isPresentProductCategory.isPresent()) {
			productCategoryUpdateResource.setId(id.toString());
			ProductCategory productCategory = productCategoryService.update(tenantId, productCategoryUpdateResource);
			successAndErrorDetailsResource = new SuccessAndErrorDetailsResource(getEnvironment().getProperty(RECORD_UPDATED), productCategory.getId().toString());
			return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.OK);
		}
		else {
			successAndErrorDetailsResource.setMessages(getEnvironment().getProperty(RECORD_NOT_FOUND));
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
