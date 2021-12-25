package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LoanApplicableRange;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.repository.LoanApplicableRangeRepository;
import com.fusionx.lending.product.resources.LoanApplicableRangeAddResource;
import com.fusionx.lending.product.resources.LoanApplicableRangeUpdateResource;
import com.fusionx.lending.product.service.LoanApplicableRangeHistoryService;
import com.fusionx.lending.product.service.LoanApplicableRangeService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Loan Applicable Range Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-07-2021             				Dilhan       Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class LoanApplicableRangeServiceImpl extends MessagePropertyBase implements LoanApplicableRangeService{
	
	@Autowired
	private LoanApplicableRangeRepository loanApplicableRangeRepository;
	
	@Autowired
	private LoanApplicableRangeHistoryService loanApplicableRangeHistoryService;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public List<LoanApplicableRange> getAll() {
		
		List<LoanApplicableRange> loanApplicableRanges = loanApplicableRangeRepository.findAll();
		return loanApplicableRanges;
	}

	@Override
	public Optional<LoanApplicableRange> getById(Long id) {
		
		Optional<LoanApplicableRange> isLoanApplicableRange = loanApplicableRangeRepository.findById(id);
		if(isLoanApplicableRange.isPresent()) {
			return Optional.ofNullable(isLoanApplicableRange.get());
		}
		else {
			return Optional.empty();
		}
		
	}

	@Override
	public Optional<LoanApplicableRange> getByCode(String code) {
		Optional<LoanApplicableRange> isLoanApplicableRange= loanApplicableRangeRepository.findByCode(code);
		if (isLoanApplicableRange.isPresent()) {
			return Optional.ofNullable(isLoanApplicableRange.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<LoanApplicableRange> getByName(String name) {
		return loanApplicableRangeRepository.findByNameContaining(name);
	}

	@Override
	public List<LoanApplicableRange> getByStatus(String status) {
		return loanApplicableRangeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	
	@Override
	public LoanApplicableRange addLoanApplicableRange(String tenantId, LoanApplicableRangeAddResource loanApplicableRangeResource) {
		
		Optional<LoanApplicableRange> isLoanApplicableRange = loanApplicableRangeRepository.findByCode(loanApplicableRangeResource.getCode());
		
		if(isLoanApplicableRange.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.LOAN_APPLICABLE_RANGE);
		}
		
		LoanApplicableRange loanApplicableRange = new LoanApplicableRange();
		loanApplicableRange.setTenantId(tenantId);
		loanApplicableRange.setCode(loanApplicableRangeResource.getCode());
		loanApplicableRange.setName(loanApplicableRangeResource.getName());
		loanApplicableRange.setStatus(CommonStatus.valueOf(loanApplicableRangeResource.getStatus()));
		loanApplicableRange.setCreatedDate(validationService.getCreateOrModifyDate());
		loanApplicableRange.setSyncTs(validationService.getCreateOrModifyDate());
	    loanApplicableRange.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	    loanApplicableRange.setMaximumAmount(validationService.stringToBigDecimal(loanApplicableRangeResource.getMaximumAmount()));
	    loanApplicableRange.setMinimumAmount(validationService.stringToBigDecimal(loanApplicableRangeResource.getMinimumAmount()));
	    loanApplicableRange.setDefaultAmount(validationService.stringToBigDecimal(loanApplicableRangeResource.getDefaultAmount()));
	    loanApplicableRange.setMaximumRate(validationService.stringToBigDecimal(loanApplicableRangeResource.getMaximumRate()));
	    loanApplicableRange.setMinimumRate(validationService.stringToBigDecimal(loanApplicableRangeResource.getMinimumRate()));
	    loanApplicableRange.setDefaultRate(validationService.stringToBigDecimal(loanApplicableRangeResource.getDefaultRate()));
	    
		return loanApplicableRangeRepository.save(loanApplicableRange);
	}

	@Override
	public LoanApplicableRange updateLoanApplicableRange(String tenantId, Long id,
			LoanApplicableRangeUpdateResource loanApplicableRangeResource) {
		
		Optional<LoanApplicableRange> isLoanApplicableRange = loanApplicableRangeRepository.findById(id);
		
		loanApplicableRangeHistoryService.saveHistory(tenantId, isLoanApplicableRange.get(), LogginAuthentcation.getInstance().getUserName());
		
		if(!isLoanApplicableRange.get().getVersion().equals(Long.parseLong(loanApplicableRangeResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.LOAN_APPLICABLE_RANGE);
		
		if(!isLoanApplicableRange.get().getCode().equalsIgnoreCase(loanApplicableRangeResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.LOAN_APPLICABLE_RANGE);
		
		LoanApplicableRange loanApplicableRange = isLoanApplicableRange.get();
		loanApplicableRange.setTenantId(tenantId);
		loanApplicableRange.setCode(loanApplicableRangeResource.getCode());
		loanApplicableRange.setName(loanApplicableRangeResource.getName());
		loanApplicableRange.setStatus(CommonStatus.valueOf(loanApplicableRangeResource.getStatus()));
		loanApplicableRange.setModifiedDate(validationService.getCreateOrModifyDate());
		loanApplicableRange.setSyncTs(validationService.getCreateOrModifyDate());
	    loanApplicableRange.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	    loanApplicableRange.setMaximumAmount(validationService.stringToBigDecimal(loanApplicableRangeResource.getMaximumAmount()));
	    loanApplicableRange.setMinimumAmount(validationService.stringToBigDecimal(loanApplicableRangeResource.getMinimumAmount()));
	    loanApplicableRange.setDefaultAmount(validationService.stringToBigDecimal(loanApplicableRangeResource.getDefaultAmount()));
	    loanApplicableRange.setMaximumRate(validationService.stringToBigDecimal(loanApplicableRangeResource.getMaximumRate()));
	    loanApplicableRange.setMinimumRate(validationService.stringToBigDecimal(loanApplicableRangeResource.getMinimumRate()));
	    loanApplicableRange.setDefaultRate(validationService.stringToBigDecimal(loanApplicableRangeResource.getDefaultRate()));
		
		return loanApplicableRangeRepository.saveAndFlush(loanApplicableRange);
	}

	
}

