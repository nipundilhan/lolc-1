package com.fusionx.lending.product.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.RepaymentType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.RepaymentTypeRepository;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.service.RepaymentTypeService;

@Component
@Transactional(rollbackFor = Exception.class)
public class RepaymentTypeServiceImpl extends MessagePropertyBase implements RepaymentTypeService{
	
	@Autowired
	private RepaymentTypeRepository repaymentTypeRepository;

	@Override
	public Optional<RepaymentType> findById(Long id) {
		Optional<RepaymentType> isPresentRepaymentType = repaymentTypeRepository.findById(id);
		if (isPresentRepaymentType.isPresent()) {
			return Optional.ofNullable(isPresentRepaymentType.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<RepaymentType> findAll() {
		
		return 	repaymentTypeRepository.findAll();
	}

	@Override
	public Optional<RepaymentType> findByCode(String code) {
		Optional<RepaymentType> isPresentRepaymentType = repaymentTypeRepository.findByCode(code);
		if (isPresentRepaymentType.isPresent()) {
			return Optional.ofNullable(isPresentRepaymentType.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<RepaymentType> findByName(String name) {
		return repaymentTypeRepository.findByName(name);
	}

	@Override
	public List<RepaymentType> findByStatus(String status) {
		
		return 	repaymentTypeRepository.findByStatus(status);
	}

	

	@Override
	public RepaymentType save(String tenantId, CommonAddResource commonAddResource) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
        
        Optional<RepaymentType> isPresentRepaymentType = repaymentTypeRepository.findByCode(commonAddResource.getCode());
        if (isPresentRepaymentType.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		}
        
        	
        RepaymentType repaymentType = new RepaymentType();
        repaymentType.setTenantId(tenantId);
        repaymentType.setCode(commonAddResource.getCode());
        repaymentType.setName(commonAddResource.getName());
        repaymentType.setDescription(commonAddResource.getDescription());
        repaymentType.setStatus(commonAddResource.getStatus());
        repaymentType.setSyncTs(currentTimestamp);
        repaymentType.setCreatedDate(currentTimestamp);
        repaymentType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        repaymentType = repaymentTypeRepository.save(repaymentType);
    	return repaymentType;
	}

	@Override
	public RepaymentType update(String tenantId, Long id, @Valid CommonUpdateResource commonUpdateResource) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<RepaymentType> isPresentRepaymentType = repaymentTypeRepository.findById(id);
		if (!isPresentRepaymentType.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        
		
		if(!isPresentRepaymentType.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		

		
		if (!(commonUpdateResource.getCode().equals(isPresentRepaymentType.get().getCode()))) {
			 
            throw new ValidateRecordException(environment.getProperty("common.code-update"),"code");
		}

		
		
		RepaymentType paymentType = isPresentRepaymentType.get();
		
		paymentType.setTenantId(tenantId);
		paymentType.setName(commonUpdateResource.getName());
		paymentType.setDescription(commonUpdateResource.getDescription());
		paymentType.setStatus(commonUpdateResource.getStatus());
		paymentType.setSyncTs(currentTimestamp);
		paymentType.setModifiedDate(currentTimestamp);
		paymentType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		paymentType = repaymentTypeRepository.saveAndFlush(paymentType);
    	return paymentType;
	}

}
