package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.exception.UserNotFound;
import com.fusionx.lending.product.resources.AddMainProduct;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.resources.UpdateProductRequest;
import com.fusionx.lending.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/product")	
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	Environment environment;
	
	@Autowired
	public ProductService productService;
	
	private static final String RECORD_NOT_FOUND = "Records not available";
	private String userNotFound="common.user-not-found";
	
	
	/**
	 * get all Product Details
	 * @param tenantId
	 * @return List<Type>
	 */
    @GetMapping(value = "/{tenantId}/all")
    public ResponseEntity<Object> getAllProducts(@PathVariable(value = "tenantId", required = true) String tenantId) {
    	SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
		List<Product> products = productService.findAll();
		int size = products.size();
		if (size > 0) {
			return new ResponseEntity<>((Collection<Product>) products,HttpStatus.OK);
		} 	
		else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}

    
    /**
     * getProduct by id
     * @param @PathVariable{tenantId}
     * @param @PathVariable{id}
     * @return Option
     */
 	@GetMapping(value = "/{tenantId}/{productId}")
 	public ResponseEntity<Object> getProductsById(@PathVariable(value = "tenantId", required = true) String tenantId,
												  @PathVariable(value = "productId", required = true) Long productId) {
 		SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
	 	Optional<Product> isPresentProduct = productService.findById(productId);
	 	if (isPresentProduct.isPresent()) {
	 		return new ResponseEntity<>(isPresentProduct.get(), HttpStatus.OK);
	 	} 
	 	else {
	 		responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
	 	}
 	}

	/**
	 * Gets the Products by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the ApprovalCategory by status
	 */	
	@GetMapping(value = "/{tenantId}/status/{status}")
	public ResponseEntity<Object> getProductsByStatus(
			@PathVariable(value = "tenantId", required = true) String tenantId,
			@PathVariable(value = "status", required = true) String status) {
		SuccessAndErrorDetails responseMessage=new SuccessAndErrorDetails();
		List<Product> product = productService.findByStatus(status);
		if (!product.isEmpty()) {
			return new ResponseEntity<>((Collection<Product>) product, HttpStatus.OK);
		} else {
			responseMessage.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
 	/**
     * save Product
     * @param @PathVariable{tenantId}
     * @param @RequestBody{productAddResource}
     * @return SuccessAndErrorDetailsDto
     */
 	@PostMapping(value = "/{tenantId}")
 	public ResponseEntity<Object> addProduct(@PathVariable(value = "tenantId", required = true) String tenantId,
											 @Valid @RequestBody AddMainProduct addProductRequest) {
 		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
 		Product product = productService.addProduct(tenantId, LogginAuthentcation.getInstance().getUserName(), addProductRequest);
 		SuccessAndErrorDetails successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.saved"),Long.toString(product.getId()));
		return new ResponseEntity<>(successAndErrorDetails,HttpStatus.CREATED);
 	}
 	
	/**
     * updateProduct
     * @param @PathVariable{tenantId}
     * @param @RequestBody{productUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "/{tenantId}/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value = "tenantId", required = true) String tenantId,
												@PathVariable(value = "id", required = true) Long id,
												@Valid @RequestBody UpdateProductRequest updateProductRequest) {
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) { 
 			throw new UserNotFound(environment.getProperty(userNotFound));
 		}
		
		SuccessAndErrorDetails successAndErrorDetails=new SuccessAndErrorDetails();
		Optional<Product> isPresentProduct = productService.findById(id);
		if(isPresentProduct.isPresent()) {
			Product product = productService.updateProduct(tenantId, LogginAuthentcation.getInstance().getUserName(), updateProductRequest, id);
			successAndErrorDetails = new SuccessAndErrorDetails(environment.getProperty("rec.updated"), product.getId().toString());
        	return new ResponseEntity<>(successAndErrorDetails,HttpStatus.OK);
		}
		else {
			successAndErrorDetails.setMessages(RECORD_NOT_FOUND);
			return new ResponseEntity<>(successAndErrorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * Search product.
	 *
	 * @param tenantId - the tenant id
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param status - the status
	 * @param pageable - the pageable
	 * @return the page
	 */
	@GetMapping(value = "/{tenantId}/search")
	public Page<Product> searchProduct(@PathVariable(value = "tenantId", required = true) String tenantId,
									   @RequestParam(value = "searchq", required = false) String searchq,
									   @RequestParam(value = "name", required = false) String name,
									   @RequestParam(value = "code", required = false) String code,
									   @RequestParam(value = "status", required = false) String status,
									   @PageableDefault(size = 10) Pageable pageable) {

		return productService.searchProductList(searchq, name, code, status, pageable);
	}
	
}
