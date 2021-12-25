package com.fusionx.lending.product.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.domain.SubProductHistory;
import com.fusionx.lending.product.repository.SubPorductHistoryRepository;
import com.fusionx.lending.product.repository.SubProductRepository;
import com.fusionx.lending.product.service.SubProductHistoryService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Sub Product Service 
 * @author 	Sanatha
 * @Date    19-JUL-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	19-JUL-2021  	FXL-25      	FXL-25   		Sanatha     	 Initial Development.
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class SubProductHistoryServiceImpl extends MessagePropertyBase implements SubProductHistoryService{
	
	@Autowired
	private SubPorductHistoryRepository subPorductHistoryRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private SubProductRepository subProductRepository;

	@Override
	public void saveHistory(String tenantId, SubProduct subProduct, String historyCreatedUser) {
		LoggerRequest.getInstance().logInfo("SubProduct ### IN Save History");
		SubProductHistory subProductHistory = new SubProductHistory();
		
		Optional<SubProduct> subProd = subProductRepository.findById(subProduct.getId());
		
		subProductHistory.setTenantId(tenantId);
		subProductHistory.setCode(subProduct.getCode());
		subProductHistory.setName(subProduct.getName());
		subProductHistory.setSubProduct(subProd.get());
		if(subProduct.getProduct()!=null) {
			subProductHistory.setProductId(subProduct.getProduct().getId());
		}
		if(subProduct.getPredecessorId()!=null) {
			subProductHistory.setPredecessorId(subProduct.getPredecessorId());
		}
		subProductHistory.setMarketingStateId(subProduct.getMarketingStateId());
		subProductHistory.setFirstMarketedDate(subProduct.getFirstMarketedDate());
		subProductHistory.setLastMarketedDate(subProduct.getLastMarketedDate());
		subProductHistory.setStateTenureLength(subProduct.getStateTenureLength());
		if(subProduct.getStateTenurePeriodId()!=null) {
			subProductHistory.setStateTenurePeriodId(subProduct.getStateTenurePeriodId());
		}
		if(subProduct.getFeatureBenifitTemplate()!=null) {
			subProductHistory.setFeatureBenifitTemplateId(subProduct.getFeatureBenifitTemplate().getId());
		}
		if(subProduct.getEligibility()!=null) {
			subProductHistory.setEligibilityId(subProduct.getEligibility().getId());
		}
		if(subProduct.getInterestTemplate()!=null) {
			subProductHistory.setInterestTemplateId(subProduct.getInterestTemplate().getId());
		}
		if(subProduct.getRepayment()!=null) {
			subProductHistory.setRepaymentId(subProduct.getRepayment().getId());
		}
		if(subProduct.getCoreProductId()!=null) {
			subProductHistory.setCoreProductId(subProduct.getCoreProductId());
		}
		subProductHistory.setMaxPenalInterestRate(subProduct.getMaxPenalInterestRate());
		subProductHistory.setMiniPenalInterestRate(subProduct.getMiniPenalInterestRate());
		if(subProduct.getPenalInterest()!=null) {
			subProductHistory.setPenalInterest(subProduct.getPenalInterest().getId());
		}
		subProductHistory.setStatus(subProduct.getStatus());
		subProductHistory.setApproveStatus(subProduct.getApproveStatus());
		subProductHistory.setCreatedDate(subProduct.getCreatedDate());
		subProductHistory.setCreatedUser(subProduct.getCreatedUser());
		subProductHistory.setModifiedDate(subProduct.getModifiedDate());
		subProductHistory.setModifiedUser(subProduct.getModifiedUser());
		subProductHistory.setPendingApprovedDate(subProduct.getPendingApprovedDate());
		subProductHistory.setPendingApprovedUser(subProduct.getPendingApprovedUser());
		subProductHistory.setPendingRejectedDate(subProduct.getPendingRejectedDate());
		subProductHistory.setPendingRejectedUser(subProduct.getPendingRejectedUser());
		subProductHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		subProductHistory.setHistoryCreatedUser(historyCreatedUser);
		subProductHistory.setSubProductVersion(subProduct.getVersion());
		subProductHistory.setSyncTs(validationService.getCreateOrModifyDate());
		
		subPorductHistoryRepository.save(subProductHistory);
		
	}

}
