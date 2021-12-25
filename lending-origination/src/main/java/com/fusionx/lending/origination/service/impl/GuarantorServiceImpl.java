package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.AddressDetail;
import com.fusionx.lending.origination.domain.BlacklistDetail;
import com.fusionx.lending.origination.domain.ContactDetail;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.IdentificationDetail;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.domain.MicroBprWorkflow;
import com.fusionx.lending.origination.domain.NetWorthAsset;
import com.fusionx.lending.origination.domain.NetWorthLiability;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.SuppliesPendingStatus;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.enums.WorkflowType;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.AddressDetailRepository;
import com.fusionx.lending.origination.repository.BlacklistDetailsRepository;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.ContactDetailRepository;
import com.fusionx.lending.origination.repository.GuarantorRepository;
import com.fusionx.lending.origination.repository.IdentificationDetailRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.repository.MicroBprWorkflowRepository;
import com.fusionx.lending.origination.repository.NetWorthAssetRepository;
import com.fusionx.lending.origination.repository.NetWorthLiabilityRepository;
import com.fusionx.lending.origination.resource.AddPersonAddressRequestResource;
import com.fusionx.lending.origination.resource.AddPersonContactRequestResource;
import com.fusionx.lending.origination.resource.AddPersonIdentificationRequestResource;
import com.fusionx.lending.origination.resource.AddSuppliesBasicInfoRequestResource;
import com.fusionx.lending.origination.resource.AddressDetailsResource;
import com.fusionx.lending.origination.resource.ContactDetailsResource;
import com.fusionx.lending.origination.resource.GuarantorAddResource;
import com.fusionx.lending.origination.resource.GuarantorCodeResponce;
import com.fusionx.lending.origination.resource.IdentificationDetailResource;
import com.fusionx.lending.origination.resource.PendingSuppliesBasicInfoResponseResource;
import com.fusionx.lending.origination.resource.PerCommonList;
import com.fusionx.lending.origination.resource.PersonCodeResponce;
import com.fusionx.lending.origination.resource.ResponseGuarantorAddressListResource;
import com.fusionx.lending.origination.resource.ResponseGuarantorAddressResource;
import com.fusionx.lending.origination.resource.ResponseGuarantorContactListResource;
import com.fusionx.lending.origination.resource.ResponseGuarantorContactResource;
import com.fusionx.lending.origination.resource.ResponseGuarantorIdentificationListResource;
import com.fusionx.lending.origination.resource.ResponseGuarantorIdentificationResource;
import com.fusionx.lending.origination.resource.ResponseGuarantorUpdateResource;
import com.fusionx.lending.origination.resource.ResponseGuarantorValueResource;
import com.fusionx.lending.origination.resource.SuppliesBasicInfoResponseResource;
import com.fusionx.lending.origination.resource.SuppliesIndividualInfoRequestResource;
import com.fusionx.lending.origination.resource.UpdateGuarantorResource;
import com.fusionx.lending.origination.resource.UpdateSuppliesBasicInfoRequestResource;
import com.fusionx.lending.origination.resource.ValidResource;
import com.fusionx.lending.origination.resource.WorkflowInitiationRequestResource;
import com.fusionx.lending.origination.resource.WorkflowRulesResource;
import com.fusionx.lending.origination.service.AddressDetailService;
import com.fusionx.lending.origination.service.BlacklistDetailsService;
import com.fusionx.lending.origination.service.ContactDetailService;
import com.fusionx.lending.origination.service.GuarantorService;
import com.fusionx.lending.origination.service.IdentificationService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class GuarantorServiceImpl extends MessagePropertyBase implements GuarantorService{
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	@Autowired
	LeadInfoRepository leadInfoRepository;
	
	@Autowired
	GuarantorRepository guarantorRepository;
	
	@Autowired
	IdentificationService identificationService;
	@Autowired
	ContactDetailService contactDetailService;
	
	@Autowired
	AddressDetailService addressDetailService;
	
	@Autowired
	MicroBprWorkflowRepository microBprWorkflowRepository;
	
	@Autowired
	IdentificationDetailRepository identificationDetailRepository;
	
	@Autowired
	ValidateService validateService;
	
	@Autowired
	ContactDetailRepository contactDetailRepository;
	
	@Autowired
	AddressDetailRepository addressDetailRepository;
	
	@Autowired
	BlacklistDetailsRepository blacklistDetailRepository;
	
	@Autowired
	private NetWorthAssetRepository netWorthAssetRepository;
	
	@Autowired
	private NetWorthLiabilityRepository netWorthLiabilityRepository;
	
	@Autowired
	private RemoteService remoteService;
	
	@Autowired
	CommonListRepository commonListRepository;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.comn.micro.bpr_rules.microBprWF";

	private static final String DOMAIN = "microBprWF";
	
	String pendingGuarantorId = null;
	String supReferenceCode = null;
	PersonCodeResponce personCode = null;
	GuarantorCodeResponce guarantorCode = null;

	@Autowired
	ValidateService service;
	
	@Autowired
	BlacklistDetailsService blacklistDetailsService;
	
	
	@Override
	public Guarantor addGuarantor(String tenantId, @Valid GuarantorAddResource guarantorAddResource) {
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		if(!guarantorAddResource.getPerId().isEmpty() && !guarantorAddResource.getPenPerId().isEmpty()) 
			throw new ValidateRecordException("Both person id and pending person id values are not allowed.", "message");

			Guarantor guarantor=new Guarantor();
			LeadInfo lead=new LeadInfo();
			/*if(guarantorAddResource.getLeadId()==null || guarantorAddResource.getLeadId().isEmpty()) {
			
			lead.setTenantId(tenantId);
			lead.setStatus("ACTIVE");
			lead.setCreatedDate(getCreateOrModifyDate());
			lead.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			lead.setSyncTs(getCreateOrModifyDate());
			leadInfoRepository.save(lead);
			}else {/**/
			Optional<LeadInfo> leadop=leadInfoRepository.findById(Long.parseLong(guarantorAddResource.getLeadId()));
			if(leadop.isPresent()) {
				lead=leadop.get();
			
			}else {
			    throw new ValidateRecordException(environment.getProperty("common.invalid"), "leadid");

			}
			
			if (guarantorAddResource.getOrganizationTypeId() != null && !guarantorAddResource.getOrganizationTypeId().isEmpty()) {
				validateService.validatePersonCommonList(tenantId, guarantorAddResource.getOrganizationTypeId(),guarantorAddResource.getOrganizationType(), 
						CommonListReferenceCode.ORGANIZATIONTYPE.toString(), null,null,null,"organizationTypeId");
			}
			
			if (guarantorAddResource.getGuarantorTypeId() != null && !guarantorAddResource.getGuarantorTypeId().isEmpty()) {
				validateService.validatePersonCommonList(tenantId, guarantorAddResource.getGuarantorTypeId(),guarantorAddResource.getGuarantorType(), 
						CommonListReferenceCode.GUARANTORTYPE.toString(), null,null,null,"gurantorTypeId");
			}
			
			if (guarantorAddResource.getRelationshipId() != null && !guarantorAddResource.getRelationshipId().isEmpty()) {
				validateService.validatePersonCommonList(tenantId, guarantorAddResource.getRelationshipId(),guarantorAddResource.getRelationshipToCus(), 
						CommonListReferenceCode.RELATIONSHIPTYPE.toString(), null,null,null,"relationshipId");
			}
			
			// validate gender
			if (guarantorAddResource.getGenderId() != null)
				validateService.validatePerCommonListDet(tenantId, guarantorAddResource.getGenderId(),guarantorAddResource.getGender(), ServicePoint.GUARANTOR, "GENDER");
	
			// validate title
			if (guarantorAddResource.getTitleId() != null)
				validateService.validatePerCommonListDet(tenantId, guarantorAddResource.getTitleId(),guarantorAddResource.getTitle(), ServicePoint.GUARANTOR, "TITLE");
			
			guarantor.setTitleId(Long.parseLong(guarantorAddResource.getTitleId()));
			guarantor.setTitle(guarantorAddResource.getTitle());
			guarantor.setGenderId(Long.parseLong(guarantorAddResource.getGenderId()));
			guarantor.setGender(guarantorAddResource.getGender());
			
		guarantor.setLeadId(lead);
		guarantor.setTenantId(tenantId);
		if(!guarantorAddResource.getGuarantorId().isEmpty())
		guarantor.setGuarantorId(Long.parseLong(guarantorAddResource.getGuarantorId()));
		guarantor.setFirstName(guarantorAddResource.getFirstName());
		guarantor.setMiddleName(guarantorAddResource.getMiddleName());
		guarantor.setLastName(guarantorAddResource.getLastName());
		guarantor.setFullName(guarantorAddResource.getFullName());
		guarantor.setInitials(guarantorAddResource.getInitials());
		guarantor.setDateOfBirth(service.formatDate(guarantorAddResource.getDateOfBirth()));
		guarantor.setRelationshipToCus(guarantorAddResource.getRelationshipToCus());
		
		if(guarantorAddResource.getPendingGuarantorId() != null)
			guarantor.setPendingGuarantorId(Long.parseLong(guarantorAddResource.getPendingGuarantorId()));
		
		guarantor.setMaritalStatus(guarantorAddResource.getMaritalStatus());
		guarantor.setSyncTs(getCreateOrModifyDate());
		guarantor.setStatus(guarantorAddResource.getStatus());
		guarantor.setCreatedDate(getCreateOrModifyDate());
		guarantor.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		
		if (!guarantorAddResource.getPerId().isEmpty() && guarantorAddResource.getPenPerId().isEmpty()) {
			Optional<Guarantor> cusper = guarantorRepository.findByPerIdAndLeadId(guarantorAddResource.getPerId(), lead);
			if (cusper.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "message");
			}
			guarantor.setPerId(guarantorAddResource.getPerId());
			guarantor.setPerCode(guarantorAddResource.getPerCode());
		}
		if (!guarantorAddResource.getPenPerId().isEmpty() && guarantorAddResource.getPerId().isEmpty()) {
			Optional<Guarantor> cusper = guarantorRepository.findByPenPerIdAndLeadId(guarantorAddResource.getPenPerId(),
					lead);
			if (cusper.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "message");
			}
			ValidResource valid = validateService.validate(tenantId,
					Long.parseLong(guarantorAddResource.getPenPerId()));
			if (valid.value.equals("false")) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "penPerId");

			}
			guarantor.setPenPerId(guarantorAddResource.getPenPerId());

		}
		guarantor=guarantorRepository.save(guarantor);
		
		if(guarantorAddResource.getIdentificationDetails() != null && !guarantorAddResource.getIdentificationDetails().isEmpty()) {
			identificationService.setIdentification(guarantorAddResource.getIdentificationDetails(),tenantId,LogginAuthentcation.getInstance().getUserName(),null,guarantor);
		}else {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"identificationDetails");
		}
		
		if(guarantorAddResource.getContactDetails()!= null && !guarantorAddResource.getContactDetails().isEmpty()) {
			contactDetailService.setContact(guarantorAddResource.getContactDetails(),tenantId,LogginAuthentcation.getInstance().getUserName(),null,guarantor);
		}else {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"contactDetails");
		}
		
		if(guarantorAddResource.getAddressDetails()!= null && !guarantorAddResource.getAddressDetails().isEmpty()) {
			addressDetailService.serAddress(guarantorAddResource.getAddressDetails(),tenantId,LogginAuthentcation.getInstance().getUserName(),null,guarantor);
		}else {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"addressDetails");
		}
		
		//call workflow
		//approveOrGenerateWorkFlow(tenantId, guarantor);
		
		//guarantor integration
		if(!guarantorAddResource.getPerId().isEmpty() && guarantorAddResource.getPenPerId().isEmpty()) {
			createPerson(tenantId,guarantor,guarantorAddResource,LogginAuthentcation.getInstance().getUserName());
		}
		if(!guarantorAddResource.getPenPerId().isEmpty() && guarantorAddResource.getPerId().isEmpty()) {
			createPendingPerson(tenantId,guarantor,guarantorAddResource,LogginAuthentcation.getInstance().getUserName());
		}
		if(guarantorAddResource.getPerId().isEmpty() && guarantorAddResource.getPenPerId().isEmpty()) {
			pendingGuarantorId = addCommonGuarantor(tenantId,guarantorAddResource);
			guarantorAddResource.setPenPerId(pendingGuarantorId);
		}
		if(guarantorAddResource.getIdentificationDetails()!= null && !guarantorAddResource.getIdentificationDetails().isEmpty()) {
			createGuarantorIdentification(tenantId,guarantorAddResource,LogginAuthentcation.getInstance().getUserName(),pendingGuarantorId,guarantor);
		}else {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"identificationDetails");
		}
			
		if(guarantorAddResource.getAddressDetails()!= null && !guarantorAddResource.getAddressDetails().isEmpty()) {
			createGuarantorAddress(tenantId,guarantorAddResource,LogginAuthentcation.getInstance().getUserName(),pendingGuarantorId,guarantor);
		}else {
			addressDetailService.serAddress(guarantorAddResource.getAddressDetails(),tenantId,LogginAuthentcation.getInstance().getUserName(),null,guarantor);
		}
			
		if(guarantorAddResource.getContactDetails()!= null && !guarantorAddResource.getContactDetails().isEmpty()) {
			createGuarantorContact(tenantId,guarantorAddResource,LogginAuthentcation.getInstance().getUserName(),pendingGuarantorId,guarantor);
		}else {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"contactDetails");
		}
			
		updateGuarantor(guarantor,pendingGuarantorId,supReferenceCode);

		return guarantor;
	}


	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	
	@Override
	public Optional<Guarantor> getById(Long id) {
		Optional<Guarantor> guarantor = guarantorRepository.findById(id);
		if (guarantor.isPresent()) {
			return Optional.ofNullable(guarantor.get());
		} else {
			return Optional.empty();
		}
	}
	

		
	private void saveWorkflowDetaills(String tenantId,Guarantor guarantor, Long processId, WorkflowType workflowType) {

        MicroBprWorkflow microBprWorkflow = new MicroBprWorkflow();
        microBprWorkflow.setTenantId(tenantId);
        microBprWorkflow.setGuarantor(guarantor);
        microBprWorkflow.setWorkflowProcessId(processId);
        microBprWorkflow.setWorkflowStatus(WorkflowStatus.ACTIVE);
        microBprWorkflow.setWorkflowType(workflowType);
        microBprWorkflow.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        microBprWorkflow.setCreatedDate(getCreateOrModifyDate());
        microBprWorkflow.setSyncTs(getCreateOrModifyDate());

        microBprWorkflowRepository.save(microBprWorkflow);
    }
	
	public void approveOrGenerateWorkFlow(String tenantId, Guarantor guarantors) {
		WorkflowRulesResource microBprWorkFlow = null;
		Long processId = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.BLACK_LIST_APPROVE);

		microBprWorkFlow = validateService.invokedMicroBprRule(WorkflowType.BLACK_LIST_APPROVE, DOMAIN_PATH, DOMAIN, tenantId);

		if(microBprWorkFlow.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validateService.initiateMicroBprWorkFlow(workflowInitiationRequestResource, tenantId);

				if(processId != null) {
			
					saveWorkflowDetaills(tenantId, guarantors, processId, WorkflowType.BLACK_LIST_APPROVE);
				}
		}
		
	}
	
	
	@Override
	public Guarantor findById(Long id) {
		Guarantor guarantor=new Guarantor();
		Optional<Guarantor> guarantors = guarantorRepository.findById(id);
		if(guarantors.isPresent()) {
			 guarantor=guarantors.get();
					
				List<IdentificationDetail> identificationDetails = identificationDetailRepository.findByGuarantorId(guarantor.getId());
				List<ContactDetail> contactDetails = contactDetailRepository.findByGuarantorId(guarantor.getId());
				List<AddressDetail> addressDetails = addressDetailRepository.findByGuarantorId(guarantor.getId());
				List<BlacklistDetail> blacklistDetails =blacklistDetailRepository.findByCustomerId(guarantor.getId());

				//List<ExternalCribDetails> guarantorExternalCribDetails = externalCribRepository.findByGuarantorId(guarantor.getId());
				List<NetWorthAsset> netWorthAssets = netWorthAssetRepository.findByGuarantorId(guarantor.getId());
				List<NetWorthLiability> netWorthLiabilitys = netWorthLiabilityRepository.findByGuarantorId(guarantor.getId());
					
				guarantor.setIdentificationDetails(identificationDetails);
				guarantor.setContactDetails(contactDetails);
				guarantor.setAddressDetails(addressDetails);
				guarantor.setBlacklistDetails(blacklistDetails);
				guarantor.setAssets(netWorthAssets);
				guarantor.setLiabilities(netWorthLiabilitys);
				//guarantor.setExternalCribDetails(guarantorExternalCribDetails);
			}
		return guarantor;
		
	}


	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return guarantorRepository.existsById(id);
	}


	@Override
	public Guarantor update(String tenantId, String userName, Long id,
			@Valid UpdateGuarantorResource updateGuarantorResource) {
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		if(!updateGuarantorResource.getPerId().isEmpty() && !updateGuarantorResource.getPenPerId().isEmpty()) 
			throw new ValidateRecordException("Both person id and pending person id values are not allowed.", "message");
		
		Optional<Guarantor> guarantors = guarantorRepository.findById(id);
		if (!guarantors.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "message");
		}
		
		if (!guarantors.get().getVersion().equals(Long.parseLong(updateGuarantorResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		
		if (updateGuarantorResource.getOrganizationTypeId() != null && !updateGuarantorResource.getOrganizationTypeId().isEmpty()) {
			validateService.validatePersonCommonList(tenantId, updateGuarantorResource.getOrganizationTypeId(),updateGuarantorResource.getOrganizationType(), 
					CommonListReferenceCode.ORGANIZATIONTYPE.toString(), null,null,null,"organizationTypeId");
		}
		
		if (updateGuarantorResource.getRelationshipId() != null && !updateGuarantorResource.getRelationshipId().isEmpty()) {
			validateService.validatePersonCommonList(tenantId, updateGuarantorResource.getRelationshipId(),updateGuarantorResource.getRelationshipToCus(), 
					CommonListReferenceCode.RELATIONSHIPTYPE.toString(), null,null,null,"relationshipId");
		}
	
		Guarantor guarantor=guarantors.get();
		LeadInfo lead=new LeadInfo();
		/*if(guarantorAddResource.getLeadId()==null || guarantorAddResource.getLeadId().isEmpty()) {
			
			lead.setTenantId(tenantId);
			lead.setStatus("ACTIVE");
			lead.setCreatedDate(getCreateOrModifyDate());
			lead.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			lead.setSyncTs(getCreateOrModifyDate());
			leadInfoRepository.save(lead);
			}else {/**/
			Optional<LeadInfo> leadop=leadInfoRepository.findById(Long.parseLong(updateGuarantorResource.getLeadId()));
			if(leadop.isPresent()) {
				lead=leadop.get();
				Optional<Guarantor> cusper = guarantorRepository.findByIdAndLeadId(id, lead);
				if (!cusper.isPresent()) {
					throw new ValidateRecordException(environment.getProperty("cannot-change"), "message");
				}
			
			}else {
			    throw new ValidateRecordException(environment.getProperty("common.invalid"), "leadId");

			}
			
			if (updateGuarantorResource.getGenderId() != null)
				validateService.validatePerCommonListDet(tenantId, updateGuarantorResource.getGenderId(),updateGuarantorResource.getGender(), ServicePoint.GUARANTOR, "GENDER");

			// validate title
			if (updateGuarantorResource.getTitleId() != null)
				validateService.validatePerCommonListDet(tenantId, updateGuarantorResource.getTitleId(),updateGuarantorResource.getTitle(), ServicePoint.GUARANTOR, "TITLE");
			guarantor.setTitleId(Long.parseLong(updateGuarantorResource.getTitleId()));
			guarantor.setTitle(updateGuarantorResource.getTitle());
			guarantor.setGenderId(Long.parseLong(updateGuarantorResource.getGenderId()));
			guarantor.setGender(updateGuarantorResource.getGender());
			
		guarantor.setLeadId(lead);
		guarantor.setTenantId(tenantId);
		if(!updateGuarantorResource.getGuarantorId().isEmpty())
		guarantor.setGuarantorId(Long.parseLong(updateGuarantorResource.getGuarantorId()));
		guarantor.setFirstName(updateGuarantorResource.getFirstName());
		guarantor.setMiddleName(updateGuarantorResource.getMiddleName());
		guarantor.setLastName(updateGuarantorResource.getLastName());
		guarantor.setFullName(updateGuarantorResource.getFullName());
		guarantor.setInitials(updateGuarantorResource.getInitials());
		guarantor.setDateOfBirth(service.formatDate(updateGuarantorResource.getDateOfBirth()));
		guarantor.setRelationshipToCus(updateGuarantorResource.getRelationshipToCus());
		
		if(!updateGuarantorResource.getPendingGuarantorId().isEmpty())
			guarantor.setPendingGuarantorId(Long.parseLong(updateGuarantorResource.getPendingGuarantorId()));
		
		guarantor.setMaritalStatus(updateGuarantorResource.getMaritalStatus());
		guarantor.setSyncTs(getCreateOrModifyDate());
		guarantor.setStatus("ACTIVE");
		guarantor.setModifiedDate(getCreateOrModifyDate());
		guarantor.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		

		if (!updateGuarantorResource.getPerId().isEmpty() && updateGuarantorResource.getPenPerId().isEmpty()) {
			Optional<Guarantor> cusper = guarantorRepository.findByPerIdAndLeadIdAndIdNotIn(updateGuarantorResource.getPerId(), lead,id);
			if (cusper.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "message");
			}
			guarantor.setPerId(updateGuarantorResource.getPerId());
			guarantor.setPerCode(updateGuarantorResource.getPerCode());
		}
		if (!updateGuarantorResource.getPenPerId().isEmpty() && updateGuarantorResource.getPerId().isEmpty()) {
			Optional<Guarantor> cusper = guarantorRepository.findByPenPerIdAndLeadIdAndIdNotIn(updateGuarantorResource.getPenPerId(),
					lead,id);
			if (cusper.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "message");
			}
			ValidResource valid = validateService.validate(tenantId,
					Long.parseLong(updateGuarantorResource.getPenPerId()));
			if (valid.value.equals("false")) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "penPerId");

			}
			guarantor.setPenPerId(updateGuarantorResource.getPenPerId());

		}
		guarantor=guarantorRepository.save(guarantor);
		identificationService.setIdentification(updateGuarantorResource.getIdentificationDetails(),tenantId,LogginAuthentcation.getInstance().getUserName(),null,guarantor);
		if(updateGuarantorResource.getContactDetails()!= null && !updateGuarantorResource.getContactDetails().isEmpty())
			contactDetailService.setContact(updateGuarantorResource.getContactDetails(),tenantId,LogginAuthentcation.getInstance().getUserName(),null,guarantor);
		if(updateGuarantorResource.getAddressDetails()!= null && !updateGuarantorResource.getAddressDetails().isEmpty())
			addressDetailService.serAddress(updateGuarantorResource.getAddressDetails(),tenantId,LogginAuthentcation.getInstance().getUserName(),null,guarantor);
		
		//call workflow
		//approveOrGenerateWorkFlow(tenantId, guarantor);
		
		//guarantor integration
		if(!updateGuarantorResource.getPerId().isEmpty() && updateGuarantorResource.getPenPerId().isEmpty()) {
			createPerson(tenantId,guarantor,updateGuarantorResource,LogginAuthentcation.getInstance().getUserName());
		}
		if(!updateGuarantorResource.getPenPerId().isEmpty() && updateGuarantorResource.getPerId().isEmpty()) {
			createPendingPerson(tenantId,guarantor,updateGuarantorResource,LogginAuthentcation.getInstance().getUserName());
		}
		if(updateGuarantorResource.getPerId().isEmpty() && updateGuarantorResource.getPenPerId().isEmpty()) {
			pendingGuarantorId = addCommonGuarantor(tenantId,updateGuarantorResource);
			updateGuarantorResource.setPenPerId(pendingGuarantorId);
		}
		if(updateGuarantorResource.getIdentificationDetails()!= null && !updateGuarantorResource.getIdentificationDetails().isEmpty())
			createGuarantorIdentification(tenantId,updateGuarantorResource,LogginAuthentcation.getInstance().getUserName(),pendingGuarantorId,guarantor);
		if(updateGuarantorResource.getAddressDetails()!= null && !updateGuarantorResource.getAddressDetails().isEmpty())
			createGuarantorAddress(tenantId,updateGuarantorResource,LogginAuthentcation.getInstance().getUserName(),pendingGuarantorId,guarantor);
		if(updateGuarantorResource.getContactDetails()!= null && !updateGuarantorResource.getContactDetails().isEmpty())
			createGuarantorContact(tenantId,updateGuarantorResource,LogginAuthentcation.getInstance().getUserName(),pendingGuarantorId,guarantor);
		updateGuarantor(guarantor,pendingGuarantorId,supReferenceCode);

		return guarantor;
		
	}
	
	
	private void createPerson(String tenantId,Guarantor guarantorTab,GuarantorAddResource guarantorAddResource, String updateUser) {
		SuppliesBasicInfoResponseResource supplies = new SuppliesBasicInfoResponseResource();
		supplies = service.getPersonOrSuplies(tenantId,guarantorAddResource);
		if(supplies.getSupReferenceCode()!=null) {
			supReferenceCode = supplies.getSupReferenceCode();
			pendingGuarantorId =  updateSupplies(tenantId,guarantorAddResource,supplies,updateUser);
		}
		else
			pendingGuarantorId = addCommonGuarantor(tenantId,guarantorAddResource);
	}

	
	private String updateSupplies(String tenantId, GuarantorAddResource guarantorAddResource,SuppliesBasicInfoResponseResource suppliesDet, String updateUser) {
		UpdateSuppliesBasicInfoRequestResource supplies = new UpdateSuppliesBasicInfoRequestResource();
		SuppliesIndividualInfoRequestResource individualInfo = new SuppliesIndividualInfoRequestResource();
		
		supplies.setPerCode(suppliesDet.getPerCode());
		supplies.setSupReferenceCode(suppliesDet.getSupReferenceCode());
		supplies.setSupOrganizationTypeCommonListId(suppliesDet.getSupOrganizationTypeCommonListId().toString());
		supplies.setSupOrganizationTypeDesc(suppliesDet.getSupOrganizationTypeDesc());
		supplies.setSupStatus(suppliesDet.getSupStatus().toString());
		supplies.setSupSuppliesType(suppliesDet.getSupSuppliesType().toString());
		
		individualInfo.setPerTitleCommonListId(guarantorAddResource.getTitleId());
		individualInfo.setPerTitleDesc(guarantorAddResource.getTitle());
		individualInfo.setPerFirstName(guarantorAddResource.getFirstName());
		individualInfo.setPerMiddleName(guarantorAddResource.getMiddleName());
		individualInfo.setPerLastName(guarantorAddResource.getLastName());
		individualInfo.setPerFullName(guarantorAddResource.getFullName());
		individualInfo.setPerInitials(guarantorAddResource.getInitials());
		individualInfo.setPerGenderCommonListId(guarantorAddResource.getGenderId());
		individualInfo.setPerGenderDesc(guarantorAddResource.getGender());
		individualInfo.setPerDateOfBirth(guarantorAddResource.getDateOfBirth().replaceAll("-", "/"));

		supplies.setPerIndividualInfo(individualInfo);
		ResponseGuarantorUpdateResource guarantorResponce = service.updateSuplies(tenantId,suppliesDet,supplies,updateUser);
		return guarantorResponce.getValue().getPenSuppliesId();
	}


	private void createPendingPerson(String tenantId,Guarantor guarantorTab, GuarantorAddResource guarantorAddResource,String updateUser) {
		PendingSuppliesBasicInfoResponseResource pendingSupplies = new PendingSuppliesBasicInfoResponseResource();
		pendingSupplies = service.getPendingPersonOrSuplies(tenantId,guarantorAddResource);
		
		if(pendingSupplies.getPsupPendingStatus()!=SuppliesPendingStatus.PENDING && pendingSupplies.getPsupPendingStatus()!=null)
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "penPerId");
		
		if(pendingSupplies.getPsupReferenceCode()!=null) {
			supReferenceCode = pendingSupplies.getPsupReferenceCode();
			pendingGuarantorId = updatePendingSupplies(tenantId,guarantorAddResource,pendingSupplies,updateUser);
		}
		else
			pendingGuarantorId = addCommonGuarantor(tenantId,guarantorAddResource);
	}
	

	private String updatePendingSupplies(String tenantId,GuarantorAddResource guarantorAddResource,PendingSuppliesBasicInfoResponseResource pendingSupplies,String updateUser) {
		UpdateSuppliesBasicInfoRequestResource updateSupplies = new UpdateSuppliesBasicInfoRequestResource();//
		SuppliesIndividualInfoRequestResource individualInfo = new SuppliesIndividualInfoRequestResource();
		
		//updateSupplies.setPerId(pendingSupplies.getPerId().toString());
		updateSupplies.setPerCode(pendingSupplies.getPperCode());
		updateSupplies.setSupReferenceCode(pendingSupplies.getPsupReferenceCode());
		updateSupplies.setSupOrganizationTypeCommonListId(pendingSupplies.getPsupOrganizationTypeCommonListId().toString());
		updateSupplies.setSupOrganizationTypeDesc(pendingSupplies.getPsupOrganizationTypeDesc());
		updateSupplies.setSupStatus(pendingSupplies.getPsupStatus().toString());
		updateSupplies.setSupSuppliesType(pendingSupplies.getPsupSuppliesType().toString());
		
		individualInfo.setPerTitleCommonListId(guarantorAddResource.getTitleId());
		individualInfo.setPerTitleDesc(guarantorAddResource.getTitle());
		individualInfo.setPerFirstName(guarantorAddResource.getFirstName());
		individualInfo.setPerMiddleName(guarantorAddResource.getMiddleName());
		individualInfo.setPerLastName(guarantorAddResource.getLastName());
		individualInfo.setPerFullName(guarantorAddResource.getFullName());
		individualInfo.setPerInitials(guarantorAddResource.getInitials());
		individualInfo.setPerGenderCommonListId(guarantorAddResource.getGenderId());
		individualInfo.setPerGenderDesc(guarantorAddResource.getGender());
		individualInfo.setPerDateOfBirth(guarantorAddResource.getDateOfBirth().replaceAll("-", "/"));

		updateSupplies.setPerIndividualInfo(individualInfo);
		ResponseGuarantorUpdateResource guarantorResponce = service.updatePendingSuplies(tenantId,pendingSupplies,updateSupplies,updateUser);
		return guarantorResponce.getValue().getPenSuppliesId();
	}


	private String addCommonGuarantor(String tenantId, GuarantorAddResource guarantorTab) {
		AddSuppliesBasicInfoRequestResource guarantorInfor =  setGuarantor(tenantId,guarantorTab);
		ResponseGuarantorValueResource guarantorValue = service.createGuarantor(tenantId,guarantorInfor,LogginAuthentcation.getInstance().getUserName());
		if(guarantorValue==null)
			throw new ValidateRecordException(environment.getProperty("guarntor.pending-guarantor_id"), "message");
		
		return guarantorValue.getValue().getPenSuppliesId().toString();
	}

	private AddSuppliesBasicInfoRequestResource setGuarantor(String tenantId, GuarantorAddResource guarantorTab) {
		AddSuppliesBasicInfoRequestResource setGuarantorDetail = new AddSuppliesBasicInfoRequestResource();
		SuppliesIndividualInfoRequestResource individualInfo = new SuppliesIndividualInfoRequestResource();
		
		personCode = service.generatePersonCode(tenantId,"PERSON");
		if(personCode==null)
		    throw new ValidateRecordException(environment.getProperty("person.service-error"), "message");
		
		guarantorCode = service.generateGuarantorCode(tenantId,"GUARANTOR");
		if(guarantorCode==null)
		    throw new ValidateRecordException(environment.getProperty("guarantor.service-error"), "message");
		
		List<PerCommonList> organizationTypePerCommonList=remoteService.getPerCommonListByType(tenantId, "ORGANIZATIONTYPE", PerCommonList.class);
		if(organizationTypePerCommonList==null ) 
			throw new ValidateRecordException(environment.getProperty("validate.organization-type"),"message");
		
		for(PerCommonList recPerCommonList:organizationTypePerCommonList) {
			if(recPerCommonList.getPcmlsCode().equals("ORIN")) {
				setGuarantorDetail.setSupOrganizationTypeCommonListId(recPerCommonList.getId().toString());
				setGuarantorDetail.setSupOrganizationTypeDesc(recPerCommonList.getCmlsDesc());
				break;
			}
		}
		
		setGuarantorDetail.setPerId("");
		setGuarantorDetail.setPenPerId("");
		setGuarantorDetail.setPerCode(personCode.getValue());
		setGuarantorDetail.setSupReferenceCode(guarantorCode.getValue());
		setGuarantorDetail.setSupSuppliesType("GUARANTOR");
		setGuarantorDetail.setSupOriginationMethodCommonListId("");
		setGuarantorDetail.setSupOriginationMethodDesc("");
		setGuarantorDetail.setSupSupplierTypeCommonListId("");
		setGuarantorDetail.setSupSupplierTypeDesc("");
		setGuarantorDetail.setSupLocationLongitude("");
		setGuarantorDetail.setSupLocationLatitude("");
		setGuarantorDetail.setSupStatus("ACTIVE");
		
		individualInfo.setPerTitleCommonListId(guarantorTab.getTitleId());
		individualInfo.setPerTitleDesc(guarantorTab.getTitle());
		individualInfo.setPerFirstName(guarantorTab.getFirstName());
		individualInfo.setPerMiddleName(guarantorTab.getMiddleName());
		individualInfo.setPerLastName(guarantorTab.getLastName());
		individualInfo.setPerFullName(guarantorTab.getFullName());
		individualInfo.setPerInitials(guarantorTab.getInitials());
		individualInfo.setPerPreferredName("");
		individualInfo.setPerOtherName("");
		individualInfo.setPerGenderCommonListId(guarantorTab.getGenderId());
		individualInfo.setPerGenderDesc(guarantorTab.getGender());
		individualInfo.setPerBirthPlace("");
		individualInfo.setPerDateOfBirth(guarantorTab.getDateOfBirth().replaceAll("-", "/"));
		individualInfo.setPerBirthCountryId("");
		individualInfo.setPerBirthCountryDesc("");
		individualInfo.setPerNationality1CommonListId("");
		individualInfo.setPerNationality1Desc("");
		individualInfo.setPerNationality2CommonListId("");
		individualInfo.setPerNationality2Desc("");
		individualInfo.setPerNationality3CommonListId("");
		individualInfo.setPerNationality3Desc("");
		individualInfo.setPerNationality1CountryId("");
		individualInfo.setPerNationality1CountryDesc("");
		individualInfo.setPerNationality2CountryId("");
		individualInfo.setPerNationality2CountryDesc("");
		individualInfo.setPerNationality3CountryId("");
		individualInfo.setPerNationality3CountryDesc("");
		individualInfo.setPerMaritalStatusCommonListId("");
		individualInfo.setPerMaritalStatusDesc("");
		individualInfo.setPerResident1CountryId("");
		individualInfo.setPerResident1CountryDesc("");
		individualInfo.setPerResident2CountryId("");
		individualInfo.setPerResident2CountryDesc("");
		individualInfo.setPerResident3CountryId("");
		individualInfo.setPerResident3CountryDesc("");
		individualInfo.setPerResidentStatusCommonListId("");
		individualInfo.setPerResidentStatusDesc("");
		individualInfo.setPerEducationalLevelCommonListId("");
		individualInfo.setPerEducationalLevelDesc("");

		setGuarantorDetail.setPerIndividualInfo(individualInfo);
		supReferenceCode = guarantorCode.getValue();
				
		return setGuarantorDetail;
	}
	
		
	private void createGuarantorIdentification(String tenantId, GuarantorAddResource guarantorAddResource,String createdUser, String penGuarantorId,Guarantor guarantor) {
		List<ResponseGuarantorIdentificationListResource> guarantorIdentificationList = new ArrayList<>();
		ResponseGuarantorIdentificationListResource guarantorIdentificationRecord = new ResponseGuarantorIdentificationListResource();
		for(IdentificationDetailResource IdentificationDetailRec : guarantorAddResource.getIdentificationDetails()) {
			AddPersonIdentificationRequestResource guarantorIdentification = new AddPersonIdentificationRequestResource();
			guarantorIdentification.setSulpId(guarantorAddResource.getPerId());
			guarantorIdentification.setPsulpId(penGuarantorId);
			guarantorIdentification.setPidtIdentificationTypeId(IdentificationDetailRec.getIdentificationTypeId());
			guarantorIdentification.setPidtIdentificationTypeDesc(IdentificationDetailRec.getIdentificationType());
			guarantorIdentification.setPidtIdentificationNo(IdentificationDetailRec.getIdentificationNo());
			guarantorIdentification.setPidtExpiryDate(IdentificationDetailRec.getExpiryDate().replaceAll("-", "/"));
			guarantorIdentification.setPidtIssueDate(IdentificationDetailRec.getIssueDate().replaceAll("-", "/"));
			guarantorIdentification.setPidtStatus("ACTIVE");
			guarantorIdentification.setPidtId(IdentificationDetailRec.getPidtId());
			guarantorIdentification.setPpidtId(IdentificationDetailRec.getPpidtId());
			ResponseGuarantorIdentificationResource guaIdentificationDetails = service.createGuarantorIdentification(tenantId,guarantorIdentification,createdUser);
			
			if(guaIdentificationDetails.getPidtIdentificationNo()!=null)
				throw new ValidateRecordException(guaIdentificationDetails.getPidtIdentificationNo(), "identificationNo");
			if(guaIdentificationDetails.getPidtIdentificationTypeDesc()!=null)
				throw new ValidateRecordException(guaIdentificationDetails.getPidtIdentificationTypeDesc(), "identificationType");
			if(guaIdentificationDetails.getPidtIdentificationTypeId()!=null)
				throw new ValidateRecordException(guaIdentificationDetails.getPidtIdentificationTypeId(), "identificationTypeId");
			if(guaIdentificationDetails.getPidtExpiryDate()!=null)
				throw new ValidateRecordException(guaIdentificationDetails.getPidtExpiryDate(), "identificationNo");
			if(guaIdentificationDetails.getPidtIssueDate()!=null)
				throw new ValidateRecordException(guaIdentificationDetails.getPidtIssueDate(), "identificationNo");
			
			guarantorIdentificationRecord.setIdentificationType(Long.parseLong(IdentificationDetailRec.getIdentificationTypeId()));
			guarantorIdentificationRecord.setIdentificationTypeDesc(IdentificationDetailRec.getIdentificationType());
			guarantorIdentificationRecord.setPidtIdentificationNo(IdentificationDetailRec.getIdentificationNo());
			guarantorIdentificationRecord.setPpidtId(guaIdentificationDetails.getValue().getPpidtId());
		}
		guarantorIdentificationList.add(guarantorIdentificationRecord);
		
		List<IdentificationDetail> isPresentIdentificationDetail = identificationDetailRepository.findByGuarantorId(guarantor.getId());
		if(!isPresentIdentificationDetail.isEmpty()) {
			List<IdentificationDetail> updateIdentificationDetailList = new ArrayList<>();
			for(IdentificationDetail recIdentificationDetail:isPresentIdentificationDetail) {
				IdentificationDetail identificationDetailRec = recIdentificationDetail;
				for (ResponseGuarantorIdentificationListResource responseGuarantorIdentificationList:guarantorIdentificationList) {
					 if(responseGuarantorIdentificationList.getIdentificationType()==recIdentificationDetail.getIdentificationTypeId() &&
					    responseGuarantorIdentificationList.getIdentificationTypeDesc().equals(recIdentificationDetail.getIdentificationType()) &&
						responseGuarantorIdentificationList.getPidtIdentificationNo().equals(recIdentificationDetail.getIdentificationNo())) {
						 identificationDetailRec.setPpidtId(Long.parseLong(responseGuarantorIdentificationList.getPpidtId()));
					 }
				}
				updateIdentificationDetailList.add(identificationDetailRec);
			}
			identificationDetailRepository.saveAll(updateIdentificationDetailList);
		}
	}
	


	private void createGuarantorAddress(String tenantId, GuarantorAddResource guarantorAddResource, String createdUser, String penGuarantorId,Guarantor guarantor) {
		List<ResponseGuarantorAddressListResource> guarantorAddressList = new ArrayList<>();
		ResponseGuarantorAddressListResource guarantorAddressListRecord = new ResponseGuarantorAddressListResource();
		for(AddressDetailsResource addressDetailsResource : guarantorAddResource.getAddressDetails()) {
			AddPersonAddressRequestResource guarantorAddressRequest = new AddPersonAddressRequestResource();
			guarantorAddressRequest.setSulpId(guarantorAddResource.getPerId());
			guarantorAddressRequest.setPsulpId(penGuarantorId);
			guarantorAddressRequest.setPaddAddressTypeCommonListId(addressDetailsResource.getAddressTypeId());
			guarantorAddressRequest.setPaddAddressTypeDesc(addressDetailsResource.getAddressTypeDesc());
			guarantorAddressRequest.setPaddAddress01(addressDetailsResource.getAddress1());
			guarantorAddressRequest.setPaddAddress02(addressDetailsResource.getAddress2());
			guarantorAddressRequest.setPaddAddress03(addressDetailsResource.getAddress3());
			guarantorAddressRequest.setPaddAddress04(addressDetailsResource.getAddress4());
			guarantorAddressRequest.setPaddAddressGeoLevelId(addressDetailsResource.getGeoLevelId());
			guarantorAddressRequest.setPaddAddressGeoLevelDesc(addressDetailsResource.getGeoLevelDesc());
			guarantorAddressRequest.setPaddAddressCountryId(addressDetailsResource.getCountryGeoId());
			guarantorAddressRequest.setPaddAddressCountryDesc(addressDetailsResource.getCountryGeoDesc());
			guarantorAddressRequest.setPaddAddressPostalCode(addressDetailsResource.getPostalCode());
			guarantorAddressRequest.setPaddStatus("ACTIVE");
			guarantorAddressRequest.setPaddId(addressDetailsResource.getPaddId());
			ResponseGuarantorAddressResource guaAddressDetails = service.createGuarantorAddress(tenantId,guarantorAddressRequest,createdUser);
			
			if(guaAddressDetails.getPaddAddressTypeCommonListId()!=null)
				throw new ValidateRecordException(guaAddressDetails.getPaddAddressTypeCommonListId(), "addressTypeId");
			if(guaAddressDetails.getPaddAddressGeoLevelId()!=null)
				throw new ValidateRecordException(guaAddressDetails.getPaddAddressGeoLevelId(), "geoLevelId");
			if(guaAddressDetails.getPaddAddressCountryId()!=null)
				throw new ValidateRecordException(guaAddressDetails.getPaddAddressCountryId(), "countryGeoId");
			
			guarantorAddressListRecord.setPaddAddressTypeCommonListId(addressDetailsResource.getAddressTypeId());
			guarantorAddressListRecord.setPaddAddress01(addressDetailsResource.getAddress1());
			guarantorAddressListRecord.setPpaddId(guaAddressDetails.getValue().getPpaddId());
		}
		guarantorAddressList.add(guarantorAddressListRecord);
		
		List<AddressDetail> isPresentAddressDetail = addressDetailRepository.findByGuarantorId(guarantor.getId());
		if(!isPresentAddressDetail.isEmpty()) {
			List<AddressDetail> updateAddressDetailList = new ArrayList<>();
			for(AddressDetail recAddressDetail:isPresentAddressDetail) {
				AddressDetail AddressDetailRec = recAddressDetail;
				for(ResponseGuarantorAddressListResource responseGuarantorAddressList:guarantorAddressList) {
					if(responseGuarantorAddressList.getPaddAddressTypeCommonListId().equals(recAddressDetail.getAddressTypeId().toString()) &&
							responseGuarantorAddressList.getPaddAddress01().equals(recAddressDetail.getAddress1())) {
						AddressDetailRec.setPpaddId(Long.parseLong(responseGuarantorAddressList.getPpaddId()));
					}
				}
				updateAddressDetailList.add(AddressDetailRec);
			}
			addressDetailRepository.saveAll(updateAddressDetailList);
		}
		
	}
		


	private void createGuarantorContact(String tenantId, GuarantorAddResource guarantorAddResource, String createdUser, String penGuarantorId,Guarantor guarantor) {
		List<ResponseGuarantorContactListResource> guarantorContactList = new ArrayList<>();
		ResponseGuarantorContactListResource guarantorContactListRecord = new ResponseGuarantorContactListResource();
		for(ContactDetailsResource contactDetailsResource : guarantorAddResource.getContactDetails()) {
			AddPersonContactRequestResource guarantorContactRequest = new AddPersonContactRequestResource();
			guarantorContactRequest.setSulpId(guarantorAddResource.getPerId());
			guarantorContactRequest.setPsulpId(penGuarantorId);
			guarantorContactRequest.setPconContactTypeId(contactDetailsResource.getContactTypeId());
			guarantorContactRequest.setPconContactTypeDesc(contactDetailsResource.getContactType());
			guarantorContactRequest.setPconValue(contactDetailsResource.getContactNo());
			guarantorContactRequest.setPconStatus("ACTIVE");
			guarantorContactRequest.setPconId(contactDetailsResource.getPconId());
			ResponseGuarantorContactResource guaConatcDetails = service.createGuarantorContact(tenantId,guarantorContactRequest,createdUser);
			
			if(guaConatcDetails.getPconContactTypeId()!=null)
				throw new ValidateRecordException(guaConatcDetails.getPconContactTypeId(), "contactTypeId");
			if(guaConatcDetails.getPconValue()!=null)
				throw new ValidateRecordException(guaConatcDetails.getPconValue(), "contactNo");
			
			guarantorContactListRecord.setPconContactTypeId(contactDetailsResource.getContactTypeId());
			guarantorContactListRecord.setPconValue(contactDetailsResource.getContactNo());
			guarantorContactListRecord.setPpconId(guaConatcDetails.getValue().getPpconId());
		}
		guarantorContactList.add(guarantorContactListRecord);		
		
		List<ContactDetail> isPresentContactDetail = contactDetailRepository.findByGuarantorId(guarantor.getId());
		if(!isPresentContactDetail.isEmpty()) {
			List<ContactDetail> updateContactDetail = new ArrayList<>();
			for(ContactDetail recContactDetail:isPresentContactDetail) {
				
				ContactDetail contactDetailRec = recContactDetail;
				for(ResponseGuarantorContactListResource responseGuarantorContactList:guarantorContactList ) {
					if(responseGuarantorContactList.getPconContactTypeId().equals(recContactDetail.getContactTypeId().toString()) &&
							responseGuarantorContactList.getPconValue().equals(recContactDetail.getContactNo())) {
						contactDetailRec.setPpconId(Long.parseLong(responseGuarantorContactList.getPpconId()));
					}
				}
				updateContactDetail.add(contactDetailRec);
			}
			contactDetailRepository.saveAll(updateContactDetail);
		}
	}


	private void updateGuarantor(Guarantor guarantor, String pendingGuarantorId2, String supReferenceCode2) {
		Guarantor updateGuarantor = guarantor;
		PendingSuppliesBasicInfoResponseResource pendingSuppliesBasicInfoResponseResource = service.getPendingSupliesDetail(guarantor.getTenantId(),pendingGuarantorId2);
		if(pendingSuppliesBasicInfoResponseResource.getPsupPendingPersonId()!=null) {
			updateGuarantor.setPenPerId(pendingSuppliesBasicInfoResponseResource.getPsupPendingPersonId().toString());
			updateGuarantor.setPerCode(pendingSuppliesBasicInfoResponseResource.getPperCode());
		}
		updateGuarantor.setPendingGuarantorId(Long.parseLong(pendingGuarantorId2));
		updateGuarantor.setSupReferenceCode(supReferenceCode2);
		guarantorRepository.saveAndFlush(updateGuarantor);
	}


	@Override
	public List<Guarantor> getByLeadId(Long id) {
		List<Guarantor> guarantors = guarantorRepository.findByLeadIdId(id);
		List<Guarantor> guarantorsAll = new ArrayList<>();
		if (!guarantors.isEmpty()) {
			for(Guarantor guarantor : guarantors) {
				
				List<IdentificationDetail> identificationDetails = identificationDetailRepository.findByGuarantorId(guarantor.getId());
				List<ContactDetail> contactDetails = contactDetailRepository.findByGuarantorId(guarantor.getId());
				List<AddressDetail> addressDetails = addressDetailRepository.findByGuarantorId(guarantor.getId());

				guarantor.setIdentificationDetails(identificationDetails);
				guarantor.setAddressDetails(addressDetails);
				guarantor.setContactDetails(contactDetails);
				
				guarantorsAll.add(guarantor);
			}
		} else {
			return new ArrayList<>();
		}
		return guarantorsAll;
	}
	
}
