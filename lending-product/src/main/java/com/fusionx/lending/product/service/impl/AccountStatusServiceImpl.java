package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.AccountStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.AccountStatusRepository;
import com.fusionx.lending.product.resources.AccountStatusAddResource;
import com.fusionx.lending.product.resources.AccountStatusUpdateResource;
import com.fusionx.lending.product.service.AccountStatusHistoryService;
import com.fusionx.lending.product.service.AccountStatusService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * API Service related to  Account Status
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        15-09-2021      -               FXL-789     Dilhan                   Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class AccountStatusServiceImpl extends MessagePropertyBase implements AccountStatusService{

	@Autowired
	private AccountStatusRepository accountStatusRepository;
	
	@Autowired
	private AccountStatusHistoryService accountStatusHistoryService;
	
	@Autowired
	private ValidationService validationService;
	
	@Override
	public List<AccountStatus> getAll() {
		return accountStatusRepository.findAll();
	}

	@Override
	public Optional<AccountStatus> findById(Long id) {
		Optional<AccountStatus> isPresentAccountStatus= accountStatusRepository.findById(id);
		if (isPresentAccountStatus.isPresent()) {
			return Optional.ofNullable(isPresentAccountStatus.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<AccountStatus> findByStatus(String status) {
		return accountStatusRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public Optional<AccountStatus> findByCode(String code) {
		Optional<AccountStatus> isPresentAccountStatus= accountStatusRepository.findByCode(code);
		if (isPresentAccountStatus.isPresent()) {
			return Optional.ofNullable(isPresentAccountStatus.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<AccountStatus> findByName(String name) {
		return accountStatusRepository.findByNameContaining(name);
	}

	@Override
	public AccountStatus addAccountStatus(String tenantId, AccountStatusAddResource accountStatusAddResource) {
		 Optional<AccountStatus> isPresentAccountStatus = accountStatusRepository.findByCode(accountStatusAddResource.getCode());
	        
	        if (isPresentAccountStatus.isPresent()) 
	        	throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.ACCOUNT_STATUS);
	        
	        AccountStatus accountStatus = new AccountStatus();
	        accountStatus.setTenantId(tenantId);
	        accountStatus.setCode(accountStatusAddResource.getCode());
	        accountStatus.setName(accountStatusAddResource.getName());
	        accountStatus.setDescription(accountStatusAddResource.getDescription());
	        accountStatus.setStatus(CommonStatus.valueOf(accountStatusAddResource.getStatus()));
	        accountStatus.setOrder(Integer.valueOf(accountStatusAddResource.getOrder()));
	        accountStatus.setCreatedDate(validationService.getCreateOrModifyDate());
	        accountStatus.setSyncTs(validationService.getCreateOrModifyDate());
	        accountStatus.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        
	        return accountStatusRepository.save(accountStatus);
	}

	@Override
	public AccountStatus updateAccountStatus(String tenantId, Long id,
			AccountStatusUpdateResource accountStatusUpdateResource) {
		
        Optional<AccountStatus> isPresentAccountStatus= accountStatusRepository.findById(id);
		
		if(isPresentAccountStatus.isPresent()) {
			
			if(!isPresentAccountStatus.get().getCode().equalsIgnoreCase(accountStatusUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.ACCOUNT_STATUS);
			
			if(!isPresentAccountStatus.get().getVersion().equals(Long.parseLong(accountStatusUpdateResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
			
			AccountStatus accountStatus = isPresentAccountStatus.get();
			accountStatusHistoryService.insertAccountStatusHistory(tenantId, accountStatus, LogginAuthentcation.getInstance().getUserName());
			
			
			accountStatus.setTenantId(tenantId);
			accountStatus.setName(accountStatusUpdateResource.getName());
			accountStatus.setDescription(accountStatusUpdateResource.getDescription());
			accountStatus.setStatus(CommonStatus.valueOf(accountStatusUpdateResource.getStatus()));
			accountStatus.setOrder(Integer.valueOf(accountStatusUpdateResource.getOrder()));
			accountStatus.setModifiedDate(validationService.getCreateOrModifyDate());
			accountStatus.setSyncTs(validationService.getCreateOrModifyDate());
			accountStatus.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        
	        return accountStatusRepository.saveAndFlush(accountStatus);
        
		}
		 else 
			throw new ValidateRecordException(environment.getProperty("record-not-found"),"message");
	}

}
