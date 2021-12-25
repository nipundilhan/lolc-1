package com.fusionx.lending.product.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.ProductGroup;
import com.fusionx.lending.product.domain.ProductGroupHistory;
import com.fusionx.lending.product.repository.ProductGroupHistoryRepository;
import com.fusionx.lending.product.service.ProductGroupHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ProductGroupHistoryServiceImpl implements ProductGroupHistoryService {
	
	@Autowired
	private ProductGroupHistoryRepository productGroupHistoryRepository;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public void insertProductGroupHistory(String tenantId, ProductGroup productGroup, String historyCreatedUser) {
            
        ProductGroupHistory productGroupHistory = new ProductGroupHistory();
        productGroupHistory.setProductGroupId(productGroup.getId());
        productGroupHistory.setTenantId(productGroup.getTenantId());
        productGroupHistory.setCode(productGroup.getCode());
        productGroupHistory.setName(productGroup.getName());
        productGroupHistory.setDescription(productGroup.getDescription());
        productGroupHistory.setStatus(productGroup.getStatus().toString());
        productGroupHistory.setCreatedDate(productGroup.getCreatedDate());
        productGroupHistory.setCreatedUser(productGroup.getCreatedUser());
        productGroupHistory.setModifiedDate(productGroup.getModifiedDate());
        productGroupHistory.setModifiedUser(productGroup.getModifiedUser());
        productGroupHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        productGroupHistory.setHistoryCreatedUser(historyCreatedUser);
        productGroupHistory.setSyncTs(validationService.getCreateOrModifyDate());
        productGroupHistory.setVersion(productGroup.getVersion());
        productGroupHistoryRepository.saveAndFlush(productGroupHistory);
        
	}

}
