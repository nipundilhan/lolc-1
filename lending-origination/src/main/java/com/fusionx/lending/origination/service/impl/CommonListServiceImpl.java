package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.resource.CommonListAddResource;
import com.fusionx.lending.origination.resource.CommonListUpdateResource;
import com.fusionx.lending.origination.service.CommonListService;

/**
 * Common list Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-12-2020      		     FX-5273	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class CommonListServiceImpl implements CommonListService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CommonListRepository commonListRepository;
	
	@Override
	public List<CommonList> findAll() {
		return commonListRepository.findAll();
	}

	@Override
	public Optional<CommonList> findById(Long id) {
		Optional<CommonList> commonList = commonListRepository.findById(id);
		if (commonList.isPresent()) {
			return Optional.ofNullable(commonList.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<CommonList> findByStatus(String status) {
		return commonListRepository.findByStatus(status);
	}

	@Override
	public List<CommonList> findByName(String name) {
		return commonListRepository.findByNameContaining(name);
	}

	@Override
	public List<CommonList> findByCode(String code) {
		return commonListRepository.findByCode(code);
	}

	@Override
	public List<CommonList> findByReferenceCode(String referenceCode) {
		return commonListRepository.findByReferenceCode(referenceCode);
	}

	@Override
	public Boolean existsById(Long id) {
		return commonListRepository.existsById(id);
	}

	@Override
	public Long saveAndValidateCommonList(String tenantId, String createdUser, CommonListAddResource commonListAddResource) {
		
		LoggerRequest.getInstance().logInfo("CommonList********************************Validate Code & Reference Code*********************************************");
		if(commonListRepository.existsByCodeAndReferenceCodeAndStatus(commonListAddResource.getCode(), commonListAddResource.getReferenceCode(), CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("CommonList********************************Save Common List*********************************************");
		CommonList commonList=saveCommonList(tenantId, createdUser, commonListAddResource);
		
		return commonList.getId();
	}

	private CommonList saveCommonList(String tenantId, String createdUser, CommonListAddResource commonListAddResource) {
		CommonList commonList = new CommonList();
		commonList.setTenantId(tenantId);
		commonList.setCode(commonListAddResource.getCode());
		commonList.setName(commonListAddResource.getName());
		commonList.setDescription(commonListAddResource.getDescription());
		commonList.setReferenceCode(commonListAddResource.getReferenceCode());
		commonList.setStatus(commonListAddResource.getStatus());
		commonList.setAttribute1(commonListAddResource.getAttribute1());
		commonList.setAttribute2(commonListAddResource.getAttribute2());
		commonList.setAttribute3(commonListAddResource.getAttribute3());
		commonList.setAttribute4(commonListAddResource.getAttribute4());
		commonList.setAttribute5(commonListAddResource.getAttribute5());
		commonList.setCreatedUser(createdUser);
		commonList.setCreatedDate(getCreateOrModifyDate());
		commonList.setSyncTs(getCreateOrModifyDate());
		commonList = commonListRepository.saveAndFlush(commonList);
		
		return commonList;
	}
	
	@Override
	public Long updateAndValidateCommonList(String tenantId, String createdUser, Long id, CommonListUpdateResource commonListUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("CommonList********************************Validate Version*********************************************");
		Optional<CommonList> isPresentCommonList = commonListRepository.findById(id);
		if(!isPresentCommonList.get().getVersion().equals(Long.parseLong(commonListUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("CommonList********************************Validate Code & Reference Code*********************************************");
		Optional<CommonList> isPresentCommonListByCode = commonListRepository.findByCodeAndReferenceCodeAndStatusAndIdNotIn(commonListUpdateResource.getCode(), commonListUpdateResource.getReferenceCode(), CommonStatus.ACTIVE.toString(), id);
		if (isPresentCommonListByCode.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("CommonList********************************Update Common List*********************************************");
		CommonList commonList=updateCommonList(createdUser, commonListUpdateResource, id);
		
		return commonList.getId();
	}
	
	private CommonList updateCommonList(String createdUser, CommonListUpdateResource commonListUpdateResource, Long id) {
		CommonList commonList = commonListRepository.getOne(id);
		commonList.setCode(commonListUpdateResource.getCode());
		commonList.setName(commonListUpdateResource.getName());
		commonList.setDescription(commonListUpdateResource.getDescription());
		commonList.setReferenceCode(commonListUpdateResource.getReferenceCode());
		commonList.setStatus(commonListUpdateResource.getStatus());
		commonList.setAttribute1(commonListUpdateResource.getAttribute1());
		commonList.setAttribute2(commonListUpdateResource.getAttribute2());
		commonList.setAttribute3(commonListUpdateResource.getAttribute3());
		commonList.setAttribute4(commonListUpdateResource.getAttribute4());
		commonList.setAttribute5(commonListUpdateResource.getAttribute5());
		commonList.setModifiedUser(createdUser);
		commonList.setModifiedDate(getCreateOrModifyDate());
		commonList.setSyncTs(getCreateOrModifyDate());
		commonList=commonListRepository.saveAndFlush(commonList);
		
		return commonList;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
}
