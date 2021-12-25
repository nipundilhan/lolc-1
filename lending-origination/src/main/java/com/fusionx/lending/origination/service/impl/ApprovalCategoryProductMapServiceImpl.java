package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.ApprovalCategory;
import com.fusionx.lending.origination.domain.ApprovalCategoryProductMapping;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalCategoryProductMapRepository;
import com.fusionx.lending.origination.repository.ApprovalCategoryRepository;
import com.fusionx.lending.origination.resource.ApprovalCatProductMapAddProductResource;
import com.fusionx.lending.origination.resource.ApprovalCatProductMapAddResource;
import com.fusionx.lending.origination.resource.ApprovalCatProductMapUpdateResource;
import com.fusionx.lending.origination.service.ApprovalCategoryProductMapService;


/**
 * Approval Category Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalCategoryProductMapServiceImpl  extends MessagePropertyBase implements ApprovalCategoryProductMapService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ApprovalCategoryRepository approvalCategoryRepository;
	
	@Autowired
	private ApprovalCategoryProductMapRepository approvalCategoryProductMapRepository;
	
	@Override
	public Page<ApprovalCategoryProductMapping> findAll(Pageable pageable) {
		return approvalCategoryProductMapRepository.findAll(pageable);
	}

	@Override
	public Optional<ApprovalCategoryProductMapping> findById(Long id) {
		Optional<ApprovalCategoryProductMapping> approvalCategoryProductMapping = approvalCategoryProductMapRepository.findById(id);
		if (approvalCategoryProductMapping.isPresent()) {
			return Optional.ofNullable(approvalCategoryProductMapping.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalCategoryProductMapping> findByStatus(String status) {
		return approvalCategoryProductMapRepository.findByStatus(status);
	}
	
	@Override
	public Boolean existsById(Long id) {
		return approvalCategoryProductMapRepository.existsById(id);
	}

	@Override
	public List<ApprovalCategoryProductMapping> findAll() {
		return approvalCategoryProductMapRepository.findAll();
	}


	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	@Override
	public Page<ApprovalCategoryProductMapping> searchByApprovalCategory(String searchq, String approvalCatName, String approvalCatCode, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(approvalCatName==null || approvalCatName.isEmpty())
			approvalCatName=null;
		if(approvalCatCode==null || approvalCatCode.isEmpty())
			approvalCatCode=null;
		
		return approvalCategoryProductMapRepository.searchByApprovalCategory(searchq, approvalCatName, approvalCatCode, pageable);
	}
	
	@Override
	public void saveAndValidateApprovalCatProductMap(String tenantId, String createdUser,
			ApprovalCatProductMapAddResource approvalCatProductMapAddResource) {
		
		LoggerRequest.getInstance().logInfo("ApprovalCategoryMapProduct********************************Validate Approve Category*********************************************");
		Optional<ApprovalCategory> isPresentAppCat = approvalCategoryRepository.findByIdAndCodeAndStatus(Long.parseLong(approvalCatProductMapAddResource.getApprovalCatId()), 
				approvalCatProductMapAddResource.getApprovalCatCode(), CommonStatus.ACTIVE.toString());

		if (!isPresentAppCat.isPresent())
			throw new ValidateRecordException(environment.getProperty("invalid.approvalCategoryId"), "approvalCategoryId");

		
		Integer index=0;
		for (ApprovalCatProductMapAddProductResource mappingProducts : approvalCatProductMapAddResource.getProducts()) {
			
//			//validate for duplicate product under same approval Category
//			if(approvalCategoryProductMapRepository.existsByApprovalCategoryIdAndProductId(isPresentAppCat.get().getId(), 
//					Long.parseLong(mappingProducts.getProductId())))
//			throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.PRODUCT_ID,ServicePoint.APPROVAL_CAT_MAP_WITH_PRODUCT, index);
//			
			
			//validate for duplicate product mapping for same approval Category
			Boolean isExists = approvalCategoryProductMapRepository.existsByProductId(Long.parseLong(mappingProducts.getProductId()));
			if(isExists!=null && isExists)
				throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.PRODUCT_ID,ServicePoint.APPROVAL_CAT_MAP_WITH_PRODUCT, index);
			
			ApprovalCategoryProductMapping approvalCategoryProductMapping = new ApprovalCategoryProductMapping();
			approvalCategoryProductMapping.setTenantId(tenantId);
			approvalCategoryProductMapping.setApprovalCategory(isPresentAppCat.get());
			approvalCategoryProductMapping.setProductId(Long.parseLong(mappingProducts.getProductId()));
			approvalCategoryProductMapping.setProductCode(mappingProducts.getProductCode());
			
			if (mappingProducts.getProductName()!=null && !mappingProducts.getProductName().isEmpty())
			approvalCategoryProductMapping.setProductName(mappingProducts.getProductName());
			
			approvalCategoryProductMapping.setStatus(approvalCatProductMapAddResource.getStatus());
			approvalCategoryProductMapping.setCreatedUser(createdUser);
			approvalCategoryProductMapping.setCreatedDate(getCreateOrModifyDate());
			approvalCategoryProductMapping.setSyncTs(getCreateOrModifyDate());
			approvalCategoryProductMapping=approvalCategoryProductMapRepository.saveAndFlush(approvalCategoryProductMapping);
			
			
		}
		index++;
		
	
	}

	@Override
	public Long updateAndValidateApprovalCatProductMap(String tenantId, String createdUser, Long id,
			ApprovalCatProductMapUpdateResource approvalCatProductMapUpdateResource) {
			
				LoggerRequest.getInstance().logInfo("ApprovalCategoryMapProduct***********************Update ApprovalCategoryMapProduct*********************");
				ApprovalCategoryProductMapping approvalCategoryProductMapping=updateApprovalCategoryProductMapping(createdUser, approvalCatProductMapUpdateResource, id);
				
				return approvalCategoryProductMapping.getId();			
	}

	
	private ApprovalCategoryProductMapping updateApprovalCategoryProductMapping(String createdUser,
			ApprovalCatProductMapUpdateResource approvalCatProductMapUpdateResource, Long id) {
		
			ApprovalCategoryProductMapping approvalCategoryProductMap = approvalCategoryProductMapRepository.getOne(id);
		
			LoggerRequest.getInstance().logInfo("ApprovalCategoryMapProduct********************************Validate Version*********************************************");
			if(!approvalCategoryProductMap.getVersion().equals(Long.parseLong(approvalCatProductMapUpdateResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
//			LoggerRequest.getInstance().logInfo("ApprovalCategoryMapProduct***********************Validate Approval Category***********************************");
//			if(!approvalCategoryProductMap.getApprovalCategory().getId().equals(Long.parseLong(approvalCatProductMapUpdateResource.getApprovalCatId())))
//				throw new ValidateRecordException(environment.getProperty("invalid.approvalCategory"), "approvalCategoryId");

		approvalCategoryProductMap.setStatus(approvalCatProductMapUpdateResource.getStatus());
		approvalCategoryProductMap.setModifiedUser(createdUser);
		approvalCategoryProductMap.setModifiedDate(getCreateOrModifyDate());
		approvalCategoryProductMap.setSyncTs(getCreateOrModifyDate());
		approvalCategoryProductMap=approvalCategoryProductMapRepository.saveAndFlush(approvalCategoryProductMap);
		
		return approvalCategoryProductMap;
	}


}