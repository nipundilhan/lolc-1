package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.ProductHistory;

public interface ProductHistoryService {

	
	
	/**
     * Insert Product History details  
     * @author Venuki
     * 
     * @param ProductHistory  - All column detail of new ProductHistory type
     * @return      - Success message.
     * 
     */
	public ProductHistory addProductHistory(String tenantId, Product product,String cUser);
	
	


}
