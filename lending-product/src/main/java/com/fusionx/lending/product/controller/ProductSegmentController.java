package com.fusionx.lending.product.controller;

/**
 * Product Group Service
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
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
import com.fusionx.lending.product.domain.ProductSegment;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AddProductSegmentRequest;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.UpdateProductSegmentRequest;
import com.fusionx.lending.product.service.ProductSegmentService;


@RestController
@RequestMapping(value = "/product-segment")
@CrossOrigin(origins = "*")
public class ProductSegmentController {

	@Autowired
	Environment environment;
	
	@Autowired
	public ProductSegmentService productSegmentService;
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	
	/**
	 * get all Product Group Details
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllProductSegments(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<ProductSegment> productSegment = productSegmentService.findAll();
		int size = productSegment.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<ProductSegment>) productSegment,HttpStatus.OK);
		} 	
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
    
    /**
     * getProduct Segment by productSegmentId
     * @param @PathVariable{tenantId}
     * @param @PathVariable{productSegmentId}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{productSegmentId}")
 	public ResponseEntity<Object> getProductSegmentsById(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "productSegmentId", required = true) Long productSegmentId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<ProductSegment> isPresentProductSegment = productSegmentService.findById(productSegmentId);
	 	if (isPresentProductSegment.isPresent()) {
	 		return new ResponseEntity<>(isPresentProductSegment.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}

 	
 	/**
     * save Product Group
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ProductSegmentAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addProductSegment(@PathVariable(value = "tenantId", required = true) String tenantId,
 			                                              @Valid @RequestBody AddProductSegmentRequest addProductSegmentRequest) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		ProductSegment productSegment = productSegmentService.addProductSegment(tenantId, LogginAuthentcation.getInstance().getUserName(), addProductSegmentRequest);
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(productSegment.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
	/**
     * updateProduct Group
     * @param @PathVariable{tenantId}
     * @param @RequestBody{ProductSegmentUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateProductSegment(@PathVariable(value = "tenantId", required = true) String tenantId, 
			                                                 @PathVariable(value = "id", required = true) Long id, 
			                                                 @Valid @RequestBody UpdateProductSegmentRequest updateProductSegmentRequest) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<ProductSegment> isPresentProductSegment = productSegmentService.findById(id);
		if(isPresentProductSegment.isPresent()) {
			ProductSegment productSegment = productSegmentService.updateProductSegment(id,LogginAuthentcation.getInstance().getUserName(),tenantId, updateProductSegmentRequest);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), productSegment.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
    /**
     * getProduct Group by productId
     * @param @PathVariable{tenantId}
     * @param @PathVariable{productId}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/product/{productId}")
 	public ResponseEntity<Object> getProdSegmentsByproductId(@PathVariable(value = "tenantId", required = true) String tenantId, 
 			                                                  @PathVariable(value = "productId", required = true) Long productId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
 		List<ProductSegment> productSegment = productSegmentService.findByProductId(productId);	 	 
		int size = productSegment.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<ProductSegment>) productSegment,HttpStatus.OK);
		} else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}
	
}
