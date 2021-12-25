package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.AccountStatus;
import com.fusionx.lending.product.domain.AccountStatusHistory;
import com.fusionx.lending.product.domain.AgeEligibility;
import com.fusionx.lending.product.domain.SalesAccessChannel;
import com.fusionx.lending.product.domain.SalesAccessChannelHistory;
import com.fusionx.lending.product.repository.AccountStatusHistoryRepository;
import com.fusionx.lending.product.repository.SalesAccessChannelHistoryRepository;
import com.fusionx.lending.product.service.AccountStatusHistoryService;
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
 * 1        15-09-2021      -               FXL-789     Dilhan      Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class AccountStatusHistoryServiceImpl implements AccountStatusHistoryService{
	
	@Autowired
	private AccountStatusHistoryRepository accountStatusHistoryRepository;
	
	@Autowired
	private ValidationService validationService;
	 
	@Override
	public void insertAccountStatusHistory(String tenantId, AccountStatus accountStatus, String historyCreatedUser) {
		   
		    AccountStatusHistory accountStatusHistory = new AccountStatusHistory();
	        
		    accountStatusHistory.setAccountStatusId(accountStatus.getId());
		    accountStatusHistory.setCode(accountStatus.getCode());
		    accountStatusHistory.setName(accountStatus.getName());
		    accountStatusHistory.setTenantId(accountStatus.getTenantId());
		    accountStatusHistory.setStatus(accountStatus.getStatus());
		    accountStatusHistory.setOrder(accountStatus.getOrder());
		    accountStatusHistory.setCreatedDate(accountStatus.getCreatedDate());
		    accountStatusHistory.setCreatedUser(accountStatus.getCreatedUser());
		    accountStatusHistory.setModifiedDate(accountStatus.getModifiedDate());
		    accountStatusHistory.setModifiedUser(accountStatus.getModifiedUser());
		    accountStatusHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		    accountStatusHistory.setSyncTs(validationService.getCreateOrModifyDate());
		    accountStatusHistory.setHistoryCreatedUser(historyCreatedUser);
		    accountStatusHistoryRepository.save(accountStatusHistory);
	}

 
}
