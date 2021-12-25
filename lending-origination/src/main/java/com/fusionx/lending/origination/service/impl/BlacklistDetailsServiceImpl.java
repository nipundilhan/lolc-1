package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.AddressDetail;
import com.fusionx.lending.origination.domain.BlacklistDetail;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.ContactDetail;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.LinkedPerson;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BlacklistDetailsRepository;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.resource.BlacklistAddResource;
import com.fusionx.lending.origination.service.BlacklistDetailsService;

@Component
@Transactional(rollbackFor = Exception.class)
public class BlacklistDetailsServiceImpl extends MessagePropertyBase implements BlacklistDetailsService{

	@Autowired
	BlacklistDetailsRepository blacklistDetailsRepository;
	
	@Autowired
	CommonListRepository commonListRepository;
	@Override
	public void setBlacklist(BlacklistAddResource blacklist, String tenantId, String userName, Customer customer,
			Guarantor guarantor) {
		BlacklistDetail blacklistDetail=new BlacklistDetail();
		
		if(blacklist.getId()!=null  && !blacklist.getId().isEmpty() ) {
			Optional<BlacklistDetail> opIdentificationDetail = blacklistDetailsRepository.findById(Long.parseLong(blacklist.getId()));
			if(opIdentificationDetail.isPresent()) {
				blacklistDetail=opIdentificationDetail.get();
				blacklistDetail.setModifiedUser(userName);
				blacklistDetail.setModifiedDate(getCreateOrModifyDate());
			}else {
	        	throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "blacklist");

			}
		}
		if(!blacklist.getReasonId().isEmpty()) {
		Optional<CommonList> cmCommonList=commonListRepository.findByIdAndNameAndReferenceCodeAndStatus(Long.parseLong(blacklist.getReasonId()), blacklist.getReason(), "REASON", "ACTIVE");
		if(!cmCommonList.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-record"), "reasonId");

		}
				
		blacklistDetail.setTenantId(tenantId);;
		blacklistDetail.setStatus("ACTIVE");; 
		
		if(customer!=null)
			blacklistDetail.setCustomer(customer);;
		if(guarantor!=null)
			blacklistDetail.setGuarantor(guarantor);
		blacklistDetail.setReason(blacklist.getReason());
		blacklistDetail.setReasonId(Long.parseLong(blacklist.getReasonId()));
		blacklistDetail.setComment(blacklist.getComment());
		blacklistDetail.setSpecialApproval(blacklist.getSpecialApproval());
		
		blacklistDetail.setCreatedUser(userName);
		blacklistDetail.setCreatedDate(getCreateOrModifyDate());
		blacklistDetail.setSyncTs(getCreateOrModifyDate());
		blacklistDetailsRepository.save(blacklistDetail);
		}
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	
}
