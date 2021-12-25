package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.resources.AddMainProduct;
import com.fusionx.lending.product.resources.UpdateProductRequest;
@Service
public interface ProductService {

	
	/**
     * Get All Product Info
     * @author Venuki
     * 
     * @return            - JSON Array of Product related  id
     * 
     */
	public List<Product> findAll();
	//public Page<Product> findAll(Pageable pageable);
	/**
     * Get Product passing by  id
     * @author Venuki
     * 
     * @param id   		  -  Id
     * @return            - JSON Array of Product related  id
     * 
     */
	
	public Optional<Product> findById(long id);
	
	/**
     * Get Product by passing status
     * @author Venuki
     * 
     * @param status   - status
     * @return         - JSON Array of Product status
     * 
     */
	
	public  List<Product> findByStatus(String status);
	
	
	/**
    * Get Product by passing name
    * @author Venuki
    * 
    * @param name   - name
    * @return        - JSON Array of Brand related  name
    * 
    */
	public List<Product> findByName(String name);
	
	/**
     * Find all Tier Band Information By tier Band Set Id
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of all LendProduct objects
	 * 
	 */
	public List<Product> findByBrandId(Long brandId);
	
	/**
     * Insert Product details 
     * @author Venuki
     * 
     * @param AddProductRequest1  - All column detail of new AddProductRequest type
     * @return      - Success message.
     * 
     */
	public Product addProduct(String tenantId, String createdUser, AddMainProduct addProductRequest);
	//public Product addProduct(String tenantId, String createdUser ,AddProductRequest1 addProductRequest);
	
	 /**
     * Update Product Details
     * @author Venuki
     * 
     * @param cmls     - UpdateProductRequest column detail for Update 
     * @return         - Success message.
     * 
     */
	public Product updateProduct( String tenantId, String modifiedUser, UpdateProductRequest updateProductRequest, Long id);
	
	 /**
     * Check whether there exists desired Product object 
     * @author Venuki
     * 
     * @param id   - Id
     * @return     - Boolean value
     * 
     */
	public boolean exists(long id);

	
	 /**
	    * Get Product passing by code
	    * @author Venuki
	    * 
	    * @param code   - code
	    * @return     - Product Object
	    * 
	    */
//  public Optional<Product> findByProductByCode(String code);
	
	/**
     * Search Products
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param status - the status
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<Product> searchProductList(String searchq, String name, String code, String status, Pageable pageable);
	
	
	

}
