package com.fusionx.lending.product.service.impl;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.ProductHistory;
import com.fusionx.lending.product.repository.ProductHistoryRepository;
import com.fusionx.lending.product.service.ProductHistoryService;
import com.fusionx.lending.product.service.ValidationService;


@Component
@Transactional(rollbackFor = Exception.class)
public class ProductHistoryServiceImpl implements ProductHistoryService {

	@Autowired
	private ProductHistoryRepository repo;
	
	@Autowired
	private ValidationService validationService;

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public ProductHistory addProductHistory(String tenantId, Product product,String cUser) {
				
			ProductHistory newProductHistory = new ProductHistory();
			
			newProductHistory.setProductId(product.getId());
			newProductHistory.setBrandId(product.getBrand().getId());
			newProductHistory.setProductGroupId(product.getProductGroup().getId());
			newProductHistory.setProductCode(product.getProductCode());
			newProductHistory.setProductName(product.getProductName());
			newProductHistory.setDescription(product.getDescription());
			newProductHistory.setCreatedDate(product.getCreatedDate());
			newProductHistory.setCreatedUser(product.getCreatedUser());
			newProductHistory.setModifiedDate(product.getModifiedDate());
			newProductHistory.setModifiedUser(product.getModifiedUser());
			newProductHistory.setStatus(product.getStatus().toString());
			newProductHistory.setVersion(product.getVersion());
			newProductHistory.setTenantId(product.getTenantId());

			newProductHistory.setHcreatedDate(validationService.getCreateOrModifyDate());
		    newProductHistory.setHcreatedUser(cUser);
		    newProductHistory.setSyncTs(validationService.getCreateOrModifyDate());
			newProductHistory = repo.saveAndFlush(newProductHistory);			
		       
			return newProductHistory;
	}

}
