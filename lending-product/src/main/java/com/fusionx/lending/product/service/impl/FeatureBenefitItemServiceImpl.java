package com.fusionx.lending.product.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitItem;
import com.fusionx.lending.product.domain.FeatureBenefitItemType;
import com.fusionx.lending.product.enums.Indicator;
import com.fusionx.lending.product.exception.OtherException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.FeatureBenefitItemRepository;
import com.fusionx.lending.product.repository.FeatureBenefitItemTypeRepository;
import com.fusionx.lending.product.resources.FeatureBenefitItemAddResource;
import com.fusionx.lending.product.resources.FeatureBenefitItemUpdateResource;
import com.fusionx.lending.product.service.FeatureBenefitItemHistoryService;
import com.fusionx.lending.product.service.FeatureBenefitItemService;
import com.fusionx.lending.product.service.FeatureBenefitItemTypeService;





@Component
@Transactional(rollbackFor = Exception.class)
public class FeatureBenefitItemServiceImpl extends MessagePropertyBase implements FeatureBenefitItemService {

	@Autowired
	private FeatureBenefitItemRepository repo;
		
	@Autowired
	private FeatureBenefitItemTypeService  featureBenefitItemTypeService;
    
	@Autowired
	private FeatureBenefitItemHistoryService  featureBenefitItemHistoryService;
    
    @Autowired
    FeatureBenefitItemTypeRepository featureBenefitItemTypeRepository;

    
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Environment environment;

	@Override
	public boolean exists(Long id) {
		try {
			return repo.existsById(id);
		} finally {

		}
	}
	private Long stringToLong(String str) {
		try {
			Long val = Long.parseLong(str);
			return val;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	
	private BigDecimal stringToBigDecimal(String str) {
		try {
			BigDecimal val = new BigDecimal(str);
			return val;
		}
		catch(Exception e) {
			return BigDecimal.ZERO;
		}
	}

	@Override
	public FeatureBenefitItem addFeatureBenefitItem(String tenantId,FeatureBenefitItemAddResource featureBenefitItemAddResource) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
		
		FeatureBenefitItem newFeatureBenefitItem = new FeatureBenefitItem();
		
		/*---------------------------------------Feature Benefit Item Type Validation----------------------------------------------------------------*/
		LoggerRequest.getInstance().logInfo("FeatureBenefitItem********************************Validate Feature Benefit Item Type*********************************************");
		
		Optional<FeatureBenefitItemType> isPresentFeatureBenefitItemType = featureBenefitItemTypeRepository.findByIdAndNameAndStatus(Long.parseLong(featureBenefitItemAddResource.getTypeId()), featureBenefitItemAddResource.getTypeName(), "ACTIVE");
        if(!isPresentFeatureBenefitItemType.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("FeatureBenefitItemType.invalid"), "typeId");
        	
        }
		
		
//		Boolean  featureBenefitItemTypeEsists = repo.existsByFeatureBenefitItemTypeId(Long.parseLong(featureBenefitItemAddResource.getTypeId()));
//
//        if (featureBenefitItemTypeEsists!= null && featureBenefitItemTypeEsists) 
//        	throw new ValidateRecordException(environment.getProperty("typeId.duplicate"),"typeId");
			
		/*------------------------------------------Validate Indicator-----------------------------------------------------*/
        LoggerRequest.getInstance().logInfo("FeatureBenefitItem********************************Validate Indicator*********************************************");
		if (featureBenefitItemAddResource.getIndicator().equalsIgnoreCase(Indicator.NO.toString())) {

			if (featureBenefitItemAddResource.getAmount() != null && !featureBenefitItemAddResource.getAmount().isEmpty()) {
				
				if(stringToBigDecimal(featureBenefitItemAddResource.getAmount()).compareTo(BigDecimal.ZERO) <= 0) {
					throw new ValidateRecordException(environment.getProperty("amount.invalid"), "amount");
				}

				newFeatureBenefitItem.setAmount(stringToBigDecimal(featureBenefitItemAddResource.getAmount()));
				
			} else {
				throw new ValidateRecordException(environment.getProperty("amount-required"), "amount");
			}
			
		} else {
			if (featureBenefitItemAddResource.getAmount() != null && !featureBenefitItemAddResource.getAmount().isEmpty()) 
				throw new ValidateRecordException(environment.getProperty("amount-not-required"), "amount");
			
		}
		
//        else {
//			if(featureBenefitItemAddResource.getAmount() == null
//						|| featureBenefitItemAddResource.getAmount().isEmpty()
//						|| stringToBigDecimal(featureBenefitItemAddResource.getAmount()).compareTo(BigDecimal.ZERO) <= 0) {
//				
//				throw new ValidateRecordException(environment.getProperty("amount-indicator-invalid"), "message");
//				
//			}
//		}
			
		/*----------------------------------------------------------------------------------------------------------------*/
		
        Optional<FeatureBenefitItem> isPresentFeatureBenefitItemCode = repo.findByCode(featureBenefitItemAddResource.getCode());
        if (isPresentFeatureBenefitItemCode.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		}
		newFeatureBenefitItem.setCode(featureBenefitItemAddResource.getCode());
		newFeatureBenefitItem.setName(featureBenefitItemAddResource.getName());
		newFeatureBenefitItem.setDescription(featureBenefitItemAddResource.getDescription());
		newFeatureBenefitItem.setFeatureBenefitItemType(featureBenefitItemTypeRepository.getOne(Long.parseLong(featureBenefitItemAddResource.getTypeId())));
		
		newFeatureBenefitItem.setIndicator(Indicator.valueOf(featureBenefitItemAddResource.getIndicator()));
		newFeatureBenefitItem.setTextual(featureBenefitItemAddResource.getTextual());
		newFeatureBenefitItem.setStatus(featureBenefitItemAddResource.getStatus());
		newFeatureBenefitItem.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		newFeatureBenefitItem.setSyncTs(currentTimestamp);
		newFeatureBenefitItem.setCreatedDate(currentTimestamp);
		newFeatureBenefitItem.setTenantId(tenantId);
		
		newFeatureBenefitItem = repo.save(newFeatureBenefitItem);		

		
		return newFeatureBenefitItem;
	}
	
	@Override
	public Optional<FeatureBenefitItem> findById(Long id) {
		
		Optional<FeatureBenefitItem> isPresentFeatureBenefitItems = repo.findById(id);
		if (isPresentFeatureBenefitItems.isPresent()) {
			return Optional.ofNullable(isPresentFeatureBenefitItems.get());
		}
		else {
			return Optional.empty();
		}
	}
	@Override
	public FeatureBenefitItem updateFeatureBenefitItem(String tenantId, FeatureBenefitItemUpdateResource featureBenefitItemUpdateResource) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<FeatureBenefitItem> isExists = repo.findById(stringToLong(featureBenefitItemUpdateResource.getId()));

		if (isExists.isPresent()) {

			FeatureBenefitItem featureBenefitItem = isExists.get();
			
			featureBenefitItemHistoryService.insertFeatureBenefitItemHistory(tenantId, featureBenefitItem, LogginAuthentcation.getInstance().getUserName());
		
			/*---------------------------------------Feature Benefit Item Type Validation----------------------------------------------------------------*/
			LoggerRequest.getInstance().logInfo("FeatureBenefitItem********************************Validate Feature Benefit Item Type*********************************************");
			
			Optional<FeatureBenefitItemType> isPresentFeatureBenefitItemType = featureBenefitItemTypeRepository.findByIdAndNameAndStatus(Long.parseLong(featureBenefitItemUpdateResource.getTypeId()), featureBenefitItemUpdateResource.getTypeName(), "ACTIVE");
	        if(!isPresentFeatureBenefitItemType.isPresent()) {
	        	throw new ValidateRecordException(environment.getProperty("FeatureBenefitItemType.invalid"), "typeId");
	        	
	        }
			
			
//			Boolean  featureBenefitItemTypeEsists = repo.existsByFeatureBenefitItemTypeId(Long.parseLong(featureBenefitItemAddResource.getTypeId()));
	//
//	        if (featureBenefitItemTypeEsists!= null && featureBenefitItemTypeEsists) 
//	        	throw new ValidateRecordException(environment.getProperty("typeId.duplicate"),"typeId");
				
			/*------------------------------------------Validate Indicator-----------------------------------------------------*/
	        LoggerRequest.getInstance().logInfo("FeatureBenefitItem********************************Validate Indicator*********************************************");
	        if (featureBenefitItemUpdateResource.getIndicator().equalsIgnoreCase(Indicator.NO.toString())) {

				if (featureBenefitItemUpdateResource.getAmount() != null && !featureBenefitItemUpdateResource.getAmount().isEmpty()) {
					
					if(stringToBigDecimal(featureBenefitItemUpdateResource.getAmount()).compareTo(BigDecimal.ZERO) <= 0) {
						throw new ValidateRecordException(environment.getProperty("amount.invalid"), "amount");
					}

					featureBenefitItem.setAmount(stringToBigDecimal(featureBenefitItemUpdateResource.getAmount()));
					
				} else {
					throw new ValidateRecordException(environment.getProperty("amount-required"), "amount");
				}
				
			} else {
				if (featureBenefitItemUpdateResource.getAmount() != null && !featureBenefitItemUpdateResource.getAmount().isEmpty()) 
					throw new ValidateRecordException(environment.getProperty("amount-not-required"), "amount");
				featureBenefitItem.setAmount(null);
			}
				
			/*----------------------------------------------------------------------------------------------------------------*/
	        LoggerRequest.getInstance().logInfo("FeatureBenefitItem********************************Validate Version*********************************************");
	        if (!featureBenefitItem.getVersion().equals(Long.parseLong(featureBenefitItemUpdateResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
	        
	        if (!(featureBenefitItemUpdateResource.getCode().equals(featureBenefitItem.getCode()))) {
				 
	            throw new ValidateRecordException(environment.getProperty("common.code-update"),"code");
			}
	        
	        featureBenefitItem.setIndicator(Indicator.valueOf(featureBenefitItemUpdateResource.getIndicator()));
	        featureBenefitItem.setName(featureBenefitItemUpdateResource.getName());
	        featureBenefitItem.setDescription(featureBenefitItemUpdateResource.getDescription());
	        featureBenefitItem.setFeatureBenefitItemType(featureBenefitItemTypeRepository.getOne(Long.parseLong(featureBenefitItemUpdateResource.getTypeId())));
	        featureBenefitItem.setTextual(featureBenefitItemUpdateResource.getTextual());
	        featureBenefitItem.setModifiedDate(currentTimestamp);
	        featureBenefitItem.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        featureBenefitItem.setStatus(featureBenefitItemUpdateResource.getStatus());
	        featureBenefitItem.setVersion(stringToLong(featureBenefitItemUpdateResource.getVersion()));
	        featureBenefitItem.setTenantId(tenantId);
		
	        featureBenefitItem = repo.saveAndFlush(featureBenefitItem);
	    	
			return featureBenefitItem;

			} else
				throw new OtherException(environment.getProperty(RECORD_NOT_FOUND));
	}
	
	@Override
	public List<FeatureBenefitItem> findAll() {

		return repo.findAll();	
	}
	
	@Override
	public List<FeatureBenefitItem> findByStatus(String status) {
		  
			return repo.findByStatus(status);	
	}
	@Override
	public List<FeatureBenefitItem> findByName(String name) {
		return repo.findByName(name);
	}
	@Override
	public List<FeatureBenefitItem> findByFeatureBenefitItemTypeCode(String featureBenefitItemTypeCode) {
		List<FeatureBenefitItem> featureBenefitItemAll = new ArrayList<>();
		List<FeatureBenefitItem> objs = (List<FeatureBenefitItem>) repo.findByFeatureBenefitItemTypeCode(featureBenefitItemTypeCode);
		
		
		for (FeatureBenefitItem featureBenefitItem : objs) {

			featureBenefitItem.setTypeName(featureBenefitItem.getFeatureBenefitItemType().getName());
			featureBenefitItem.setTypeId(featureBenefitItem.getFeatureBenefitItemType().getId().toString());
			featureBenefitItem.setTypeCode(featureBenefitItem.getFeatureBenefitItemType().getCode());
			
				
			featureBenefitItemAll.add(featureBenefitItem);
			
		}		
			return featureBenefitItemAll;	
	}
	@Override
	public Optional<FeatureBenefitItem> findByCode(String code) {
		Optional<FeatureBenefitItem> isPresentPaymentType = repo.findByCode(code);
		if (isPresentPaymentType.isPresent()) {
			return Optional.ofNullable(isPresentPaymentType.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FeatureBenefitItem> findByCodeAndItemTypeId(String code, Long typeId) {
		
		Optional<FeatureBenefitItem> isPresentFeatureBenefitItems = repo.findByCodeAndFeatureBenefitItemTypeId(code,typeId);
		if (isPresentFeatureBenefitItems.isPresent()) {
			return Optional.ofNullable(isPresentFeatureBenefitItems.get());
		}
		else {
			return Optional.empty();
		}
	}
  
}
