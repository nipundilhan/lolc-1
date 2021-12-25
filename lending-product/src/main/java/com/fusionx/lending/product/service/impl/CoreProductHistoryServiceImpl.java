package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.AgeEligibilityHistory;
import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.domain.CoreProduct;
import com.fusionx.lending.product.domain.CoreProductHistory;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.repository.CoreProductHistoryRepository;
import com.fusionx.lending.product.repository.CoreProductRepository;
import com.fusionx.lending.product.service.CoreProductHistoryService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * API Service related to Age eligibility.
 *
 * @author Dilhan Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        25-09-2021      -               -           Dilhan Jayasinghe        Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CoreProductHistoryServiceImpl implements  CoreProductHistoryService{

	@Autowired
	CoreProductHistoryRepository coreProductHistoryRepository;
	
	@Autowired
    private ValidationService validationService;
	
	@Override
	public void saveHistory(String tenantId, CoreProduct coreProduct, String historyCreatedUser) {
		
		    CoreProductHistory coreProductHistory = new CoreProductHistory();

	        coreProductHistory.setTenantId(tenantId);
	        coreProductHistory.setDescription(coreProduct.getDescription());
	        coreProductHistory.setCoreProduct(coreProduct.getId());
	        coreProductHistory.setSalesAccessChannel(coreProduct.getSalesAccessChannel()!=null?coreProduct.getSalesAccessChannel().getId():null);
	        coreProductHistory.setServiceAccessChannel(coreProduct.getServiceAccessChannel()!=null?coreProduct.getServiceAccessChannel().getId():null);
	        coreProductHistory.setCurrencyId(coreProduct.getCurrencyId());
	        coreProductHistory.setCurrencyCode(coreProduct.getCurrencyCode());
	        coreProductHistory.setCurrencyCodeNumeric(coreProduct.getCurrencyCodeNumeric());
	        coreProductHistory.setFullPartialRepayment(coreProduct.getFullPartialRepayment());
	        coreProductHistory.setOverPayment(coreProduct.getOverPayment());
	        coreProductHistory.setOverPaymentApplicable(coreProduct.getOverPaymentApplicable());
	        coreProductHistory.setEarlyPaymentApplicable(coreProduct.getEarlyPaymentApplicable());
	        coreProductHistory.setComment(coreProduct.getComment());
	        coreProductHistory.setOtherFeeType(coreProduct.getOtherFeeType()!= null?coreProduct.getOtherFeeType().getId():null);
	        coreProductHistory.setCode(coreProduct.getCode());
	        coreProductHistory.setStatus(coreProduct.getStatus());
	        coreProductHistory.setTcsCsUrl(coreProduct.getTcsCsUrl());
	        coreProductHistory.setCreatedDate(coreProduct.getCreatedDate());
	        coreProductHistory.setCreatedUser(coreProduct.getCreatedUser());
	        coreProductHistory.setModifiedDate(coreProduct.getModifiedDate());
	        coreProductHistory.setModifiedUser(coreProduct.getModifiedUser());
	        coreProductHistory.setVersion(coreProduct.getVersion());
	        coreProductHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
	        coreProductHistory.setSyncTs(validationService.getCreateOrModifyDate());
	        coreProductHistory.setHistoryCreatedUser(historyCreatedUser);
	        coreProductHistory = coreProductHistoryRepository.saveAndFlush(coreProductHistory);
	}

}
