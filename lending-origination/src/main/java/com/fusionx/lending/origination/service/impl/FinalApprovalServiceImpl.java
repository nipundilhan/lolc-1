package com.fusionx.lending.origination.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CreditAppCollateralDetail;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.FacilityContractDetails;
import com.fusionx.lending.origination.domain.FacilityParameter;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.AddressTypeCodes;
import com.fusionx.lending.origination.enums.ContactsTypeCodes;
import com.fusionx.lending.origination.enums.CustomerMainType;
import com.fusionx.lending.origination.enums.FacilityTypes;
import com.fusionx.lending.origination.enums.IdentificationTypeCodes;
import com.fusionx.lending.origination.exception.FusionException;
import com.fusionx.lending.origination.repository.AddressDetailRepository;
import com.fusionx.lending.origination.repository.ContactDetailRepository;
import com.fusionx.lending.origination.repository.CreditAppCollateralDetailsRepository;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.FacilityContractDetailsRepository;
import com.fusionx.lending.origination.repository.FacilityParameterRepository;
import com.fusionx.lending.origination.repository.GuarantorRepository;
import com.fusionx.lending.origination.repository.IdentificationDetailRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.resource.AssertApproveRequestResource;
import com.fusionx.lending.origination.resource.AssertDetailsResponceResource;
import com.fusionx.lending.origination.resource.ComnCustomerResponceResource;
import com.fusionx.lending.origination.resource.ComnSupplierResponceResource;
import com.fusionx.lending.origination.resource.PendingCustomerFinishResponce;
import com.fusionx.lending.origination.resource.PendingCutomerApproveRequestResource;
import com.fusionx.lending.origination.resource.PendingSuppliesApproveRequestResource;
import com.fusionx.lending.origination.resource.PendingSuppliesFinishResponce;
import com.fusionx.lending.origination.resource.PersonResponseAddressResource;
import com.fusionx.lending.origination.resource.PersonResponseContactResource;
import com.fusionx.lending.origination.resource.PersonResponseIdentificationResource;
import com.fusionx.lending.origination.service.FinalApprovalService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;
/**
 * Final Approval Service impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-06-2021                           MenukaJ        Created
 *    
 ********************************************************************************************************
 */


@Component
@Transactional(rollbackFor = Exception.class)
public class FinalApprovalServiceImpl extends MessagePropertyBase implements FinalApprovalService {
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private GuarantorRepository guarantorRepository;
	
	@Autowired
	private RemoteService remoteService;
	
	@Autowired
	private ContactDetailRepository contactDetailRepository;
	
	@Autowired
	private AddressDetailRepository addressDetailRepository;
	
	@Autowired
	private IdentificationDetailRepository identificationDetailRepository;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	@Autowired
	private FacilityParameterRepository facilityParameterRepository;
	
	@Autowired
	private CreditAppCollateralDetailsRepository creditAppCollateralDetailsRepository;
	
	@Autowired
	private FacilityContractDetailsRepository facilityContractDetailsRepository;

	@Value("${comn-customer}")
    private String urlCommonCustomer;
	
	@Value("${col-collateral-asset-detail}")
    private String urlAssetDetail;
	
	@Value("${comn-supplies-entities}")
	private String urlComnSuppliesEntities;
	
	@Override
	public List<Customer> bulkPendingCusApproval(String tenantId,
			PendingCutomerApproveRequestResource pendingCutomerApproveRequestResource) {
		
		List<Customer> customerList = new ArrayList<>();
		
		if(pendingCutomerApproveRequestResource.getPenCusIds() != null && !pendingCutomerApproveRequestResource.getPenCusIds().isEmpty()) {
			
			List<PendingCustomerFinishResponce> bulkFinishPendingCustomer = validateService.bulkFinishPendingCustomer(tenantId, pendingCutomerApproveRequestResource, LogginAuthentcation.getInstance().getUserName());
			
			if(bulkFinishPendingCustomer != null && !bulkFinishPendingCustomer.isEmpty()) {
				
				for (int i = 0; i < bulkFinishPendingCustomer.size(); i++) {
					Optional<Customer> isPresentCustomer= customerRepository.findByPendingCustomerId(bulkFinishPendingCustomer.get(i).getPenCusId());
					
					if(isPresentCustomer.isPresent()) {
						Customer customer = isPresentCustomer.get();
						customer.setCustomerId(bulkFinishPendingCustomer.get(i).getCustomerId());
						customer.setSyncTs(validateService.getCreateOrModifyDate());
						customer = customerRepository.saveAndFlush(customer);
						customerList.add(customer);
					}
				}
			}
		}
		return customerList;
	}

	@Override
	public List<Guarantor> bulkPendingSupApproval(String tenantId,
			PendingSuppliesApproveRequestResource pendingSuppliesApproveRequestResource) {
		
		List<Guarantor> guarantors = new ArrayList<>();
		
		if(pendingSuppliesApproveRequestResource.getPenSupIds() != null && !pendingSuppliesApproveRequestResource.getPenSupIds().isEmpty()) {
			
			List<PendingSuppliesFinishResponce> pendingSuppliesFinishResponce = validateService.bulkFinishPendingSupplies(tenantId, pendingSuppliesApproveRequestResource, LogginAuthentcation.getInstance().getUserName());
			
			if(pendingSuppliesFinishResponce != null && !pendingSuppliesFinishResponce.isEmpty()) {
				
				for (int i = 0; i < pendingSuppliesFinishResponce.size(); i++) {
					Optional<Guarantor> isPresentGuarantor= guarantorRepository.findByPendingGuarantorId(pendingSuppliesFinishResponce.get(i).getPenSupId());
					
					if(isPresentGuarantor.isPresent()) {
						Guarantor guarantor = isPresentGuarantor.get();
						guarantor.setGuarantorId(pendingSuppliesFinishResponce.get(i).getSuppliesId());
						guarantor.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
						guarantor.setModifiedDate(validateService.getCreateOrModifyDate());
						guarantor.setSyncTs(validateService.getCreateOrModifyDate());
						guarantor = guarantorRepository.saveAndFlush(guarantor);
						guarantors.add(guarantor);
					}
				}
			}
		}
		return guarantors;
	}

	@Override
	public void bulkAssertsApproval(String tenantId, AssertApproveRequestResource assertApproveRequestResource) {
		
		validateService.bulkFinishAssetEntity(tenantId, assertApproveRequestResource, LogginAuthentcation.getInstance().getUserName());
		
	}
}
