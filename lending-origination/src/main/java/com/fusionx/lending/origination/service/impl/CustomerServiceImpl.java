package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
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
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.ContactDetail;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.FacilityOtherProducts;
import com.fusionx.lending.origination.domain.IdentificationDetail;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.domain.LinkedPerson;
import com.fusionx.lending.origination.domain.MicroBprWorkflow;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.CustomerPendingStatus;
import com.fusionx.lending.origination.enums.LeadStatus;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.enums.WorkflowType;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.AddressDetailRepository;
import com.fusionx.lending.origination.repository.BlacklistDetailsRepository;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.ContactDetailRepository;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.FacilityOtherProductsRepository;
import com.fusionx.lending.origination.repository.IdentificationDetailRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.repository.LinkedPersonRepository;
import com.fusionx.lending.origination.repository.MicroBprWorkflowRepository;
import com.fusionx.lending.origination.resource.AddCustomerBasicInfoRequestResource;
import com.fusionx.lending.origination.resource.AddCustomerPersonIdentificationRequestResource;
import com.fusionx.lending.origination.resource.AddKeyPersonBasicInfoRequestResource;
import com.fusionx.lending.origination.resource.AddPersonAddressRequestResource;
import com.fusionx.lending.origination.resource.AddPersonContactRequestResource;
import com.fusionx.lending.origination.resource.AddPersonIdentificationRequestResource;
import com.fusionx.lending.origination.resource.AddRelationshipBasicInfoRequestResource;
import com.fusionx.lending.origination.resource.AddressDetailsResource;
import com.fusionx.lending.origination.resource.ContactDetailsResource;
import com.fusionx.lending.origination.resource.CustomerBasicInfoResponseResource;
import com.fusionx.lending.origination.resource.CustomerCodeResponce;
import com.fusionx.lending.origination.resource.CustomerCorporateBasicInfoRequestResource;
import com.fusionx.lending.origination.resource.CustomerIndividualInfoRequestResource;
import com.fusionx.lending.origination.resource.CustomerResource;
import com.fusionx.lending.origination.resource.FacilityOtherProductsResource;
import com.fusionx.lending.origination.resource.IdentificationDetailResource;
import com.fusionx.lending.origination.resource.LinkedPersonResource;
import com.fusionx.lending.origination.resource.PendingCustomerBasicInfoResponseResource;
import com.fusionx.lending.origination.resource.PerCommonList;
import com.fusionx.lending.origination.resource.PersonCodeResponce;
import com.fusionx.lending.origination.resource.PersonIndividualInfoRequestResource;
import com.fusionx.lending.origination.resource.ResponseCustomerAddressListResource;
import com.fusionx.lending.origination.resource.ResponseCustomerAddressResource;
import com.fusionx.lending.origination.resource.ResponseCustomerContactListResource;
import com.fusionx.lending.origination.resource.ResponseCustomerContactResource;
import com.fusionx.lending.origination.resource.ResponseCustomerIdentificationListResource;
import com.fusionx.lending.origination.resource.ResponseCustomerIdentificationResource;
import com.fusionx.lending.origination.resource.ResponseCustomerKeyPersonResource;
import com.fusionx.lending.origination.resource.ResponseCustomerRelationshipIdentificationResource;
import com.fusionx.lending.origination.resource.ResponseCustomerRelationshipResource;
import com.fusionx.lending.origination.resource.ResponseCustomerValueResource;
import com.fusionx.lending.origination.resource.UpdateCustomerBasicInfoRequestResource;
import com.fusionx.lending.origination.resource.UpdateCustomerPersonIdentificationRequestResource;
import com.fusionx.lending.origination.resource.UpdateCustomerResource;
import com.fusionx.lending.origination.resource.UpdateKeyPersonBasicInfoRequestResource;
import com.fusionx.lending.origination.resource.UpdateRelationshipBasicInfoRequestResource;
import com.fusionx.lending.origination.resource.ValidResource;
import com.fusionx.lending.origination.resource.WorkflowInitiationRequestResource;
import com.fusionx.lending.origination.resource.WorkflowRulesResource;
import com.fusionx.lending.origination.service.AddressDetailService;
import com.fusionx.lending.origination.service.BlacklistDetailsService;
import com.fusionx.lending.origination.service.ContactDetailService;
import com.fusionx.lending.origination.service.CustomerService;
import com.fusionx.lending.origination.service.IdentificationService;
import com.fusionx.lending.origination.service.LinkedPersonService;
import com.fusionx.lending.origination.service.NewFacilityService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Customer ServiceImpl
 * 
 ********************************************************************************************************
 * ### Date 		Story Point 	Task No 	Author 		Description
 * -------------------------------------------------------------------------------------------------------
 * 1 26-04-2021 								Thamokshi	Created
 * 
 * 2 04-08-2021      FXL-381        FXL-424     Piyumi      Modified
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl extends MessagePropertyBase implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ValidateService service;

	@Autowired
	IdentificationService identificationService;

	@Autowired
	IdentificationDetailRepository identificationDetailRepository;

	@Autowired
	ContactDetailService contactDetailService;

	@Autowired
	AddressDetailService addressDetailService;

	@Autowired
	LeadInfoRepository leadInfoRepository;

	@Autowired
	MicroBprWorkflowRepository microBprWorkflowRepository;

	@Autowired
	ValidateService validateService;

	@Autowired
	BlacklistDetailsService blacklistDetailsService;

	@Autowired
	LinkedPersonRepository linkedPersonRepository;

	@Autowired
	ContactDetailRepository contactDetailRepository;

	@Autowired
	AddressDetailRepository addressDetailRepository;

	@Autowired
	BlacklistDetailsRepository blacklistDetailRepository;

	@Autowired
	CommonListRepository commonListRepository;
	
	@Autowired
	private RemoteService remoteService;
	
	@Autowired
	private NewFacilityService newFacilityService;
	
	@Autowired
	private LinkedPersonService linkedPersonService;
	
	@Autowired
	private FacilityOtherProductsRepository facilityOtherProductsRepository;
	

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.comn.micro.bpr_rules.microBprWF";

	private static final String DOMAIN = "microBprWF";

	

	String pendingCustomerrId = null;
	String customerReferenceCode = null;
	PersonCodeResponce personCode = null;
	CustomerCodeResponce customerCode = null;
	

	/**
	 * 
	 * @param tenantId
	 * @param customerResource
	 * @return
	 */
	@Override
	public Customer save(String tenantId, @Valid CustomerResource customerResource) {

		Customer customer = new Customer();

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		if (!customerResource.getPerId().isEmpty() && !customerResource.getPenPerId().isEmpty())
			throw new ValidateRecordException(environment.getProperty("validate.perid-penperid"),	"message");

		customer.setTenantId(tenantId);
		LeadInfo lead = new LeadInfo();
		if (customerResource.getLeadId() == null || customerResource.getLeadId().isEmpty()) {

			lead.setTenantId(tenantId);
			lead.setStatus(CommonStatus.ACTIVE.toString());
			lead.setCreatedDate(getCreateOrModifyDate());
			lead.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			lead.setSyncTs(getCreateOrModifyDate());
			lead.setLeadStatus(LeadStatus.COMPLETED);
			leadInfoRepository.save(lead);
		} else {
			lead = leadInfoRepository.findById(Long.parseLong(customerResource.getLeadId())).get();
		}
		
		if(customerResource.getProductCategoryList() != null && !customerResource.getProductCategoryList().isEmpty()) {
			newFacilityService.saveOtherProducts(tenantId, customerResource.getProductCategoryList(), lead);
		}else {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "productCategoryList");
		}
		customer.setLead(lead);
		if (customerResource.getCustomerTypeId() != null && !customerResource.getCustomerTypeId().isEmpty()) {
			validateService.validatePersonCommonList(tenantId, customerResource.getCustomerTypeId(),customerResource.getCustomerType(), 
					CommonListReferenceCode.ORGANIZATIONTYPE.toString(), null,null,null,"customerTypeId");
		}
		
		if (customerResource.getPreferredLanguageId() != null && !customerResource.getPreferredLanguageId().isEmpty()) {
			validateService.validateLanguageById(tenantId, customerResource.getPreferredLanguageId(), customerResource.getPreferredLanguageDesc());
			customer.setPrefLangId(Long.parseLong(customerResource.getPreferredLanguageId()));
		}
		
		validateService.validateCommonBranch(tenantId, customerResource.getNearestBranchId(), customerResource.getNearestBranchName());
			customer.setNearestBranchId(Long.parseLong(customerResource.getNearestBranchId()));
		
		if(customerResource.getWithinBranchArea() != null && !customerResource.getWithinBranchArea().isEmpty()) {
			customer.setWithinBranchArea(CommonStatus.valueOf(customerResource.getWithinBranchArea()));
		}

		if (customerResource.getCustomerType().equals("INDIVIDUAL")) {
			
			if (customerResource.getFirstName() == null || customerResource.getFirstName().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "firstName");
			
			if (customerResource.getLastName() == null || customerResource.getLastName().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "lastName");
			
			if (customerResource.getTitleId() == null || customerResource.getTitleId().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "titleId");
			
			if (customerResource.getGenderId() == null || customerResource.getGenderId().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "genderId");
			
			if (customerResource.getFullName() == null || customerResource.getFullName().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "fullName");
			
			if (customerResource.getDateOfBirth() == null || customerResource.getDateOfBirth().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "dateOfBirth");
			
			validateService.validatePersonCommonList(tenantId,customerResource.getGenderId(),customerResource.getGender(),
					CommonListReferenceCode.GENDER.toString(), null, null, null, "genderId");
			
			validateService.validatePersonCommonList(tenantId,customerResource.getTitleId(),customerResource.getTitle(),
				CommonListReferenceCode.TITLE.toString(), null, null, null, "titleId");
		
			customer.setCustomerType(customerResource.getCustomerType());
			customer.setCustomerMainType(customerResource.getCustomerMainType());
			customer.setCustomerTypeId(Long.parseLong(customerResource.getCustomerTypeId()));
			if (customerResource.getCustomerId() != null && !customerResource.getCustomerId().isEmpty())
				customer.setCustomerId(Long.parseLong(customerResource.getCustomerId()));
			customer.setCustomerReference(customerResource.getCustomerReference());
			customer.setFirstName(customerResource.getFirstName());
			customer.setMiddleName(customerResource.getMiddleName());
			customer.setLastName(customerResource.getLastName());
			customer.setGender(customerResource.getGender());
			customer.setTitle(customerResource.getTitle());
			customer.setFullName(customerResource.getFullName());
			customer.setInitials(customerResource.getInitials());
			customer.setDateOfBirth(service.formatDate(customerResource.getDateOfBirth()));
			customer.setCusReferenceCode(customerResource.getCusReferenceCode());
			customer.setRef01(customerResource.getRef01());
			customer.setRef02(customerResource.getRef02());
			customer.setRef03(customerResource.getRef03());
			customer.setStatus(customerResource.getStatus());
			customer.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			customer.setCreatedDate(getCreateOrModifyDate());
			customer.setSyncTs(getCreateOrModifyDate());
			customer.setTitleId(Long.parseLong(customerResource.getTitleId()));
			customer.setGenderId(Long.parseLong(customerResource.getGenderId()));
			

			if (!customerResource.getPerId().isEmpty() && customerResource.getPenPerId().isEmpty()) {
				Optional<Customer> cusper = customerRepository.findByPerIdAndLead(customerResource.getPerId(), lead);
				if (cusper.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "message");
				}
				customer.setPerId(customerResource.getPerId());
				customer.setPerCode(customerResource.getPerCode());
			}
			if (!customerResource.getPenPerId().isEmpty() && customerResource.getPerId().isEmpty()) {
				Optional<Customer> cusper = customerRepository.findByPenPerIdAndLead(customerResource.getPenPerId(),
						lead);
				if (cusper.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "message");
				}
				ValidResource valid = validateService.validate(tenantId,
						Long.parseLong(customerResource.getPenPerId()));
				if (valid.value.equals("false")) {
					throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "penPerId");

				}
				customer.setPenPerId(customerResource.getPenPerId());

			}

			customer = customerRepository.save(customer);
			if (customerResource.getIdentification() != null && !customerResource.getIdentification().isEmpty()) {
				identificationService.setIdentification(customerResource.getIdentification(), tenantId,
						LogginAuthentcation.getInstance().getUserName(), customer,null);
			}else {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "identification");
			}

			if (customerResource.getContact() != null && !customerResource.getContact().isEmpty()) {
				contactDetailService.setContact(customerResource.getContact(), tenantId,
						LogginAuthentcation.getInstance().getUserName(), customer, null);
			}else {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "contact");
			}

			if (customerResource.getAddress() != null && !customerResource.getAddress().isEmpty()) {
				addressDetailService.serAddress(customerResource.getAddress(), tenantId,
						LogginAuthentcation.getInstance().getUserName(), customer, null);
			}else {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "address");
			}
			
			if(customerResource.getLinkedPersonList() != null && !customerResource.getLinkedPersonList().isEmpty())
				linkedPersonService.saveLinkedPerson(tenantId, customer, customerResource.getLinkedPersonList());
			
		} else if (customerResource.getCustomerType().equals("CORPORATE")) {
			
			if (customerResource.getCompanyName() == null || customerResource.getCompanyName().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"companyName");
			
			if (customerResource.getBrNumber() == null || customerResource.getBrNumber().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"brNumber");
			
			if (customerResource.getCorporateCategoryId() == null || customerResource.getCorporateCategoryId().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"corporateCategoryId");
			
			validateService.validateCorporateCategory(tenantId, customerResource.getCorporateCategoryId(),customerResource.getCorporateCategoryName(), ServicePoint.CUSTOMER, "CORPORATE_CATEGORY_ID");

			customer.setCustomerMainType(customerResource.getCustomerMainType());
			customer.setCustomerType(customerResource.getCustomerType());
			customer.setCustomerTypeId(Long.parseLong(customerResource.getCustomerTypeId()));
			customer.setBrNumber(customerResource.getBrNumber());
			customer.setReferenceNo(customerResource.getReferenceNo());
			customer.setTaxNo(customerResource.getTaxNo());
			customer.setCompanyName(customerResource.getCompanyName());
			customer.setRef01(customerResource.getRef01());
			customer.setRef02(customerResource.getRef02());
			customer.setRef03(customerResource.getRef03());
			customer.setStatus(customerResource.getStatus());
			customer.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			customer.setCreatedDate(getCreateOrModifyDate());
			customer.setSyncTs(getCreateOrModifyDate());
			customer.setContactPerson(customerResource.getContactPerson());
			customer.setCorporateCategoryId(Long.parseLong(customerResource.getCorporateCategoryId()));
			customer.setCorporateCategory(customerResource.getCorporateCategoryName());

			customer = customerRepository.save(customer);
			identificationService.setIdentification(customerResource.getIdentification(), tenantId,
					LogginAuthentcation.getInstance().getUserName(), customer,null);
			contactDetailService.setContact(customerResource.getContact(), tenantId,
					LogginAuthentcation.getInstance().getUserName(), customer, null);
			addressDetailService.serAddress(customerResource.getAddress(), tenantId,
					LogginAuthentcation.getInstance().getUserName(), customer, null);	
			if(customerResource.getLinkedPersonList() != null && !customerResource.getLinkedPersonList().isEmpty())
				linkedPersonService.saveLinkedPerson(tenantId, customer, customerResource.getLinkedPersonList());

		}
		// workflow call
		//approveOrGenerateWorkFlow(tenantId, customer);

		// customer integration
		if (!customerResource.getPerId().isEmpty() && customerResource.getPenPerId().isEmpty()) {
			createPerson(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName());
		}
		if (!customerResource.getPenPerId().isEmpty() && customerResource.getPerId().isEmpty()) {
			createPendingPerson(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName());
		}
		if (customerResource.getPerId().isEmpty() && customerResource.getPenPerId().isEmpty()) {
			pendingCustomerrId = addCommonCustomer(tenantId, customerResource);
			customerResource.setPenPerId(pendingCustomerrId);
		}
		if (customerResource.getIdentification() != null && !customerResource.getIdentification().isEmpty())
			createCustomerIdentification(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName(),pendingCustomerrId,customer);
		if (customerResource.getAddress()!= null && !customerResource.getAddress().isEmpty())
			createCustomerAddress(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName(),pendingCustomerrId,customer);
		if (customerResource.getContact()!= null && !customerResource.getContact().isEmpty())
			createCustomerContact(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName(),pendingCustomerrId,customer);
		if(customerResource.getLinkedPersonList() != null && !customerResource.getLinkedPersonList().isEmpty()) {
			if(customerResource.getCustomerType().equals("INDIVIDUAL")) {
				for(LinkedPersonResource linkedPerson : customerResource.getLinkedPersonList()) {
					createCustomerRelation(tenantId, linkedPerson , validateService.stringToLong(pendingCustomerrId));	
				}
			}else {
				for(LinkedPersonResource linkedPerson : customerResource.getLinkedPersonList()) {
					createCustomerKeyPerson(tenantId, linkedPerson , validateService.stringToLong(pendingCustomerrId));	
				}
			}
		}
		updateCustomer(customer, pendingCustomerrId, customerReferenceCode);

		return customer;
	}

	/**
	 * 
	 * @return List<Customer>
	 */
	@Override
	public List<Customer> getAll() {
		
		List<Customer> customerList =  customerRepository.findAll();
		for(Customer customers : customerList) {
			if(customers.getInternalCribStatusId()!=null) {
			Optional<CommonList> commonList=commonListRepository.findById(customers.getInternalCribStatusId());
			customers.setInternalCribStatus(commonList.get().getName());
			}
				
			if(customers.getExternalCribStatusId()!=null) {
			Optional<CommonList> commonList=commonListRepository.findById(customers.getExternalCribStatusId());
			customers.setExternalCribStatus(commonList.get().getName());
			}
		}
		return customerList;
		
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Optional<Customer> getById(Long id) {
		Optional<Customer> cusOp = customerRepository.findById(id);

		if (cusOp.isPresent()) {

			List<IdentificationDetail> identificationDetails = identificationDetailRepository
					.findByCustomerId(cusOp.get().getId());
			List<ContactDetail> contactDetails = contactDetailRepository.findByCustomerId(cusOp.get().getId());
			List<AddressDetail> addressDetails = addressDetailRepository.findByCustomerId(cusOp.get().getId());
			List<LinkedPerson> linkedPersonDetails = linkedPersonRepository.findByCustomerId(cusOp.get().getId());
			if(!linkedPersonDetails.isEmpty()) {
				for(LinkedPerson linkedPersonDetail :linkedPersonDetails) {
					
					List<IdentificationDetail> identificationDetailLinkedPerson = identificationDetailRepository.findByLinkPersonId(linkedPersonDetail.getId());
					linkedPersonDetail.setIdentificationDetails(identificationDetailLinkedPerson);
				}
			}
			if(cusOp.get().getInternalCribStatusId()!=null) {
			Optional<CommonList> commonList=commonListRepository.findById(cusOp.get().getInternalCribStatusId());
			cusOp.get().setInternalCribStatus(commonList.get().getName());
			}
			
			if(cusOp.get().getExternalCribStatusId()!=null) {
			Optional<CommonList> commonList=commonListRepository.findById(cusOp.get().getExternalCribStatusId());
			cusOp.get().setExternalCribStatus(commonList.get().getName());
			}
			cusOp.get().setIdentificationDetails(identificationDetails);
			cusOp.get().setContactDetails(contactDetails);
			cusOp.get().setAddressDetails(addressDetails);
			cusOp.get().setLinkedPersons(linkedPersonDetails);
			
		}
		return cusOp;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new Timestamp(now.getTime());
	}

	// Added by Ravishika - for applicant blacklist special approve workflow
	private void saveWorkflowDetaills(String tenantId, Customer customer, Long processId, WorkflowType workflowType) {

		MicroBprWorkflow microBprWorkflow = new MicroBprWorkflow();
		microBprWorkflow.setTenantId(tenantId);
		microBprWorkflow.setCustomer(customer);
		microBprWorkflow.setWorkflowProcessId(processId);
		microBprWorkflow.setWorkflowStatus(WorkflowStatus.ACTIVE);
		microBprWorkflow.setWorkflowType(workflowType);
		microBprWorkflow.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		microBprWorkflow.setCreatedDate(getCreateOrModifyDate());
		microBprWorkflow.setSyncTs(getCreateOrModifyDate());

		microBprWorkflowRepository.save(microBprWorkflow);
	}

	public void approveOrGenerateWorkFlow(String tenantId, Customer customers) {
		WorkflowRulesResource microBprWorkFlow = null;
		Long processId = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.BLACK_LIST_APPROVE);

		microBprWorkFlow = validateService.invokedMicroBprRule(WorkflowType.BLACK_LIST_APPROVE, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (microBprWorkFlow.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validateService.initiateMicroBprWorkFlow(workflowInitiationRequestResource, tenantId);

			if (processId != null) {

				saveWorkflowDetaills(tenantId, customers, processId, WorkflowType.BLACK_LIST_APPROVE);

			}
		}
	}


	/***
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public boolean existsById(Long id) {
		return customerRepository.existsById(id);
	}

	/**
	 * 
	 * @param tenantId
	 * @param userName
	 * @param id
	 * @param updateCustomerResource
	 * @return
	 */
	@Override
	public Customer update(String tenantId, String userName, Long id, @Valid UpdateCustomerResource customerResource) {
		Customer customer = new Customer();

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);


		Optional<Customer> cus=customerRepository.findById(id);
		if (!cus.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "message");
		}
		customer=cus.get();
		if (!(customerResource.getPerId().isEmpty() && customerResource.getPenPerId().isEmpty()))
			throw new ValidateRecordException("Both person id and pending person id values are not allowed.",
					"message");

		customer.setTenantId(tenantId);

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		if (!customerResource.getPerId().isEmpty() && !customerResource.getPenPerId().isEmpty())
			throw new ValidateRecordException(environment.getProperty("validate.perid-penperid"),	"message");

		customer.setTenantId(tenantId);
		LeadInfo lead = new LeadInfo();
		if (customerResource.getLeadId() == null || customerResource.getLeadId().isEmpty()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "leadId");

			
		} else {
			lead = leadInfoRepository.findById(Long.parseLong(customerResource.getLeadId())).get();
			Optional<Customer> cusper = customerRepository.findByIdAndLead(id, lead);
			if (!cusper.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("cannot-change"), "message");
			}
		}
		customer.setLead(lead);
		if (customerResource.getCustomerTypeId() !=  null && !customerResource.getCustomerTypeId().isEmpty()) {
			validateService.validatePersonCommonList(tenantId, customerResource.getCustomerTypeId(),customerResource.getCustomerType(), 
					CommonListReferenceCode.ORGANIZATIONTYPE.toString(), null,null,null,"customerTypeId");
		}
		
		if (customerResource.getPreferredLanguageId() != null && !customerResource.getPreferredLanguageId().isEmpty()) {
			validateService.validateLanguageById(tenantId, customerResource.getPreferredLanguageId(), customerResource.getPreferredLanguageDesc());
			customer.setPrefLangId(Long.parseLong(customerResource.getPreferredLanguageId()));
		}
		
		validateService.validateCommonBranch(tenantId, customerResource.getNearestBranchId(), customerResource.getNearestBranchName());
		customer.setNearestBranchId(Long.parseLong(customerResource.getNearestBranchId()));
		
		if(customerResource.getWithinBranchArea() != null && !customerResource.getWithinBranchArea().isEmpty()) {
			customer.setWithinBranchArea(CommonStatus.valueOf(customerResource.getWithinBranchArea()));
		}
		
		if(customerResource.getProductCategoryList() != null && !customerResource.getProductCategoryList().isEmpty()) {
			for(FacilityOtherProductsResource productsResource : customerResource.getProductCategoryList()) {
				
				if(productsResource.getId() !=null && !productsResource.getId().isEmpty()) {
					Optional<FacilityOtherProducts> isPresentfacilityOtherProduct = facilityOtherProductsRepository.findByleadInfoIdAndProductCategoryCodeIdAndIdNotIn(lead.getId() , validateService.stringToLong(productsResource.getProductId()), validateService.stringToLong(productsResource.getId()));
					
					if(isPresentfacilityOtherProduct.isPresent()) 
						throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "productCategory");
				}else {
					Optional<FacilityOtherProducts> isPresentfacilityOtherProduct = facilityOtherProductsRepository.findByleadInfoIdAndProductCategoryCodeId(lead.getId() , validateService.stringToLong(productsResource.getProductId()));
					
					if(isPresentfacilityOtherProduct.isPresent()) 
						throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "productCategory");
				}
				
			}
			newFacilityService.saveOtherProducts(tenantId, customerResource.getProductCategoryList(), lead);
		}

		if (customerResource.getCustomerType().equals("INDIVIDUAL")) {
			
			if (customerResource.getFirstName() == null || customerResource.getFirstName().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "firstName");
			
			if (customerResource.getLastName() == null || customerResource.getLastName().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "lastName");
			
			if (customerResource.getTitleId() == null || customerResource.getTitleId().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "titleId");
			
			if (customerResource.getGenderId() == null || customerResource.getGenderId().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "genderId");
			
			if (customerResource.getFullName() == null || customerResource.getFullName().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "fullName");
			
			if (customerResource.getDateOfBirth() == null || customerResource.getDateOfBirth().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "dateOfBirth");
			
			validateService.validatePersonCommonList(tenantId,customerResource.getGenderId(),customerResource.getGender(),
					CommonListReferenceCode.GENDER.toString(), null, null, null, "genderId");
			
			validateService.validatePersonCommonList(tenantId,customerResource.getTitleId(),customerResource.getTitle(),
				CommonListReferenceCode.TITLE.toString(), null, null, null, "titleId");

			customer.setCustomerType(customerResource.getCustomerType());
			customer.setCustomerMainType(customerResource.getCustomerMainType());
			customer.setCustomerTypeId(Long.parseLong(customerResource.getCustomerTypeId()));
			if (customerResource.getCustomerId() != null && !customerResource.getCustomerId().isEmpty())
				customer.setCustomerId(Long.parseLong(customerResource.getCustomerId()));
			customer.setCustomerReference(customerResource.getCustomerReference());
			customer.setFirstName(customerResource.getFirstName());
			customer.setMiddleName(customerResource.getMiddleName());
			customer.setLastName(customerResource.getLastName());
			customer.setGender(customerResource.getGender());
			customer.setTitle(customerResource.getTitle());
			customer.setFullName(customerResource.getFullName());
			customer.setInitials(customerResource.getInitials());
			customer.setDateOfBirth(service.formatDate(customerResource.getDateOfBirth()));
			customer.setCusReferenceCode(customerResource.getCusReferenceCode());
			customer.setRef01(customerResource.getRef01());
			customer.setRef02(customerResource.getRef02());
			customer.setRef03(customerResource.getRef03());
			customer.setStatus(customerResource.getStatus());
			customer.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			customer.setModifiedDate(getCreateOrModifyDate());
			customer.setSyncTs(getCreateOrModifyDate());

			if (!customerResource.getPerId().isEmpty() && customerResource.getPenPerId().isEmpty()) {
				Optional<Customer> cusper = customerRepository.findByPerIdAndLeadAndIdNotIn(customerResource.getPerId(), lead,id);
				if (cusper.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "message");
				}
				customer.setPerId(customerResource.getPerId());
				customer.setPerCode(customerResource.getPerCode());
			}
			if (!customerResource.getPenPerId().isEmpty() && customerResource.getPerId().isEmpty()) {
				Optional<Customer> cusper = customerRepository.findByPenPerIdAndLeadAndIdNotIn(customerResource.getPenPerId(),
						lead,id);
				if (cusper.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "message");
				}
				ValidResource valid = validateService.validate(tenantId,
						Long.parseLong(customerResource.getPenPerId()));
				if (valid.value.equals("false")) {
					throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "penPerId");

				}
				customer.setPenPerId(customerResource.getPenPerId());

			}


			customer = customerRepository.save(customer);
			if (!customerResource.getIdentification().isEmpty())
				identificationService.setIdentification(customerResource.getIdentification(), tenantId,
						LogginAuthentcation.getInstance().getUserName(), customer,null);

			if (!customerResource.getContact().isEmpty())
				contactDetailService.setContact(customerResource.getContact(), tenantId,
						LogginAuthentcation.getInstance().getUserName(), customer, null);

			if (!customerResource.getAddress().isEmpty())
				addressDetailService.serAddress(customerResource.getAddress(), tenantId,
						LogginAuthentcation.getInstance().getUserName(), customer, null);
			
			if(customerResource.getLinkedPersonList() != null && !customerResource.getLinkedPersonList().isEmpty())
				linkedPersonService.saveLinkedPerson(tenantId, customer, customerResource.getLinkedPersonList());

		} else if (customerResource.getCustomerType().equals("CORPORATE")) {
			
			if (customerResource.getCompanyName() == null || customerResource.getCompanyName().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"companyName");
			
			if (customerResource.getBrNumber() == null || customerResource.getBrNumber().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"brNumber");
			
			if (customerResource.getCorporateCategoryId() == null || customerResource.getCorporateCategoryId().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL),"corporateCategoryId");
			
			validateService.validateCorporateCategory(tenantId, customerResource.getCorporateCategoryId(),customerResource.getCorporateCategoryName(), ServicePoint.CUSTOMER, "CORPORATE_CATEGORY_ID");

			customer.setCustomerMainType(customerResource.getCustomerMainType());
			customer.setCustomerType(customerResource.getCustomerType());
			customer.setCustomerTypeId(Long.parseLong(customerResource.getCustomerTypeId()));
			customer.setBrNumber(customerResource.getBrNumber());
			customer.setReferenceNo(customerResource.getReferenceNo());
			customer.setTaxNo(customerResource.getTaxNo());
			customer.setCompanyName(customerResource.getCompanyName());
			customer.setRef01(customerResource.getRef01());
			customer.setRef02(customerResource.getRef02());
			customer.setRef03(customerResource.getRef03());
			customer.setStatus(customerResource.getStatus());
			customer.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			customer.setModifiedDate(getCreateOrModifyDate());
			customer.setSyncTs(getCreateOrModifyDate());
			customer.setContactPerson(customerResource.getContactPerson());
			customer.setCorporateCategoryId(Long.parseLong(customerResource.getCorporateCategoryId()));
			customer.setCorporateCategory(customerResource.getCorporateCategoryName());

			customer = customerRepository.save(customer);
			identificationService.setIdentification(customerResource.getIdentification(), tenantId,
					LogginAuthentcation.getInstance().getUserName(), customer,null);
			contactDetailService.setContact(customerResource.getContact(), tenantId,
					LogginAuthentcation.getInstance().getUserName(), customer, null);
			addressDetailService.serAddress(customerResource.getAddress(), tenantId,
					LogginAuthentcation.getInstance().getUserName(), customer, null);
			
			if(customerResource.getLinkedPersonList() != null && !customerResource.getLinkedPersonList().isEmpty())
				linkedPersonService.saveLinkedPerson(tenantId, customer, customerResource.getLinkedPersonList());

		}

		// customer integration
		if (!customerResource.getPerId().isEmpty() && customerResource.getPenPerId().isEmpty()) {
			createPerson(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName());
		}
		if (!customerResource.getPenPerId().isEmpty() && customerResource.getPerId().isEmpty()) {
			createPendingPerson(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName());
		}
		if (customerResource.getPerId().isEmpty() && customerResource.getPenPerId().isEmpty()) {
			pendingCustomerrId = addCommonCustomer(tenantId, customerResource);
			customerResource.setPenPerId(pendingCustomerrId);
		}
		if (customerResource.getIdentification()!= null && !customerResource.getIdentification().isEmpty())
			createCustomerIdentification(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName(),pendingCustomerrId,customer);
		if (customerResource.getAddress()!= null && !customerResource.getAddress().isEmpty())
			createCustomerAddress(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName(),pendingCustomerrId,customer);
		if (customerResource.getContact()!= null && !customerResource.getContact().isEmpty())
			createCustomerContact(tenantId, customerResource, LogginAuthentcation.getInstance().getUserName(),pendingCustomerrId,customer);
		if(customerResource.getLinkedPersonList() != null && !customerResource.getLinkedPersonList().isEmpty()) {
			if(customerResource.getCustomerType().equals("INDIVIDUAL")) {
				for(LinkedPersonResource linkedPerson : customerResource.getLinkedPersonList()) {
					updateCustomerRelation(tenantId, linkedPerson , validateService.stringToLong(pendingCustomerrId));	
				}
			}else {
				for(LinkedPersonResource linkedPerson : customerResource.getLinkedPersonList()) {
					updateCustomerKeyPerson(tenantId, linkedPerson , validateService.stringToLong(pendingCustomerrId));	
				}
			}
			
		}
		updateCustomer(customer, pendingCustomerrId, customerReferenceCode);

		return customer;

	}

	private String addCommonCustomer(String tenantId, CustomerResource customerResource) {
		AddCustomerBasicInfoRequestResource customerInfor = setCustomer(tenantId, customerResource);
		System.out.println(customerInfor);
		ResponseCustomerValueResource customerValue = service.createCustomer(tenantId, customerInfor,
				LogginAuthentcation.getInstance().getUserName());
		if (customerValue == null)
			throw new ValidateRecordException(environment.getProperty("guarntor.pending-guarantor_id"), "message");

		return customerValue.getValue().getPenCustomerId().toString();
	}

	private AddCustomerBasicInfoRequestResource setCustomer(String tenantId, CustomerResource customerResource) {
		AddCustomerBasicInfoRequestResource setCustomerDetail = new AddCustomerBasicInfoRequestResource();
		CustomerIndividualInfoRequestResource individualInfo = new CustomerIndividualInfoRequestResource();
		CustomerCorporateBasicInfoRequestResource coporateInfo = new CustomerCorporateBasicInfoRequestResource();

		personCode = service.generatePersonCode(tenantId, "PERSON");
		if (personCode == null)
			throw new ValidateRecordException(environment.getProperty("person.service-error"), "message");

		customerCode = service.generateCustomerCode(tenantId, "CUSTOMER");
		if (customerCode == null)
			throw new ValidateRecordException(environment.getProperty("customer.service-error"), "message");

		setCustomerDetail.setPerCode(personCode.getValue());
		setCustomerDetail.setCusReferenceCode(customerCode.getValue());
		setCustomerDetail.setCusStatus("ACTIVE");
		
		List<PerCommonList> organizationTypePerCommonList=remoteService.getPerCommonListByType(tenantId, "ORGANIZATIONTYPE", PerCommonList.class);
		if(organizationTypePerCommonList==null ) 
			throw new ValidateRecordException(environment.getProperty("validate.organization-type"),"message");
		
		for(PerCommonList recPerCommonList:organizationTypePerCommonList) {
			if(customerResource.getCustomerType().equals("INDIVIDUAL") && recPerCommonList.getPcmlsCode().equals("ORIN")) {
				setCustomerDetail.setCusOrganizationTypeCommonListId(recPerCommonList.getId().toString());
				setCustomerDetail.setCusOrganizationTypeDesc(recPerCommonList.getCmlsDesc());
				break;
			}
			if(customerResource.getCustomerType().equals("CORPORATE") && recPerCommonList.getPcmlsCode().equals("ORCO")) {
				setCustomerDetail.setCusOrganizationTypeCommonListId(recPerCommonList.getId().toString());
				setCustomerDetail.setCusOrganizationTypeDesc(recPerCommonList.getCmlsDesc());
				break;
			}
		}
		
		if(setCustomerDetail.getCusOrganizationTypeCommonListId()==null)
			throw new ValidateRecordException(environment.getProperty("validate.organization-type"),"message");	

		if (customerResource.getCustomerType().equals("INDIVIDUAL")) {
			individualInfo.setPerTitleCommonListId(customerResource.getTitleId());
			individualInfo.setPerTitleDesc(customerResource.getTitle());
			individualInfo.setPerFirstName(customerResource.getFirstName());
			individualInfo.setPerMiddleName(customerResource.getMiddleName());
			individualInfo.setPerLastName(customerResource.getLastName());
			individualInfo.setPerFullName(customerResource.getFullName());
			individualInfo.setPerInitials(customerResource.getInitials());
			individualInfo.setPerGenderCommonListId(customerResource.getGenderId());
			individualInfo.setPerGenderDesc(customerResource.getGender());
			individualInfo.setPerDateOfBirth(customerResource.getDateOfBirth().replaceAll("-", "/"));
			setCustomerDetail.setPerIndividualInfo(individualInfo);
		}

		if (customerResource.getCustomerType().equals("CORPORATE")) {
		
			coporateInfo.setPerCompanyName(customerResource.getCompanyName());
			coporateInfo.setPerCorporateCategoryCommonListId(customerResource.getCorporateCategoryId());
			coporateInfo.setPerCorporateCategoryDesc(customerResource.getCorporateCategoryName());
			coporateInfo.setPerBusinessRegNo(customerResource.getBrNumber());
			setCustomerDetail.setPerCorporateInfo(coporateInfo);
		}

		customerReferenceCode = customerCode.getValue();
		return setCustomerDetail;
	}

	private void createCustomerAddress(String tenantId, CustomerResource customerResource, String userName,String penCustomerrId,Customer customer) {
		List<ResponseCustomerAddressListResource> customerAddressList = new ArrayList<>();
		ResponseCustomerAddressListResource customerAddressListRecord = new ResponseCustomerAddressListResource();
		for (AddressDetailsResource addressDetailsResource : customerResource.getAddress()) {
			AddPersonAddressRequestResource customerAddressRequest = new AddPersonAddressRequestResource();
			customerAddressRequest.setSulpId(customerResource.getPerId());
			customerAddressRequest.setPsulpId(penCustomerrId);
			customerAddressRequest.setPaddAddressTypeCommonListId(addressDetailsResource.getAddressTypeId());
			customerAddressRequest.setPaddAddressTypeDesc(addressDetailsResource.getAddressTypeDesc());
			customerAddressRequest.setPaddAddress01(addressDetailsResource.getAddress1());
			customerAddressRequest.setPaddAddress02(addressDetailsResource.getAddress2());
			customerAddressRequest.setPaddAddress03(addressDetailsResource.getAddress3());
			customerAddressRequest.setPaddAddress04(addressDetailsResource.getAddress4());
			customerAddressRequest.setPaddAddressGeoLevelId(addressDetailsResource.getGeoLevelId());
			customerAddressRequest.setPaddAddressGeoLevelDesc(addressDetailsResource.getGeoLevelDesc());
			customerAddressRequest.setPaddAddressCountryId(addressDetailsResource.getCountryGeoId());
			customerAddressRequest.setPaddAddressCountryDesc(addressDetailsResource.getCountryGeoDesc());
			customerAddressRequest.setPaddAddressPostalCode(addressDetailsResource.getPostalCode());
			customerAddressRequest.setPaddStatus("ACTIVE");
			customerAddressRequest.setPaddId(addressDetailsResource.getPaddId());
			ResponseCustomerAddressResource cusAddressDetails = service.createCustomerAddress(tenantId,
					customerAddressRequest, userName);

			if (cusAddressDetails.getPaddAddressTypeCommonListId() != null)
				throw new ValidateRecordException(cusAddressDetails.getPaddAddressTypeCommonListId(), "addressTypeId");
			if (cusAddressDetails.getPaddAddressGeoLevelId() != null)
				throw new ValidateRecordException(cusAddressDetails.getPaddAddressGeoLevelId(), "geoLevelId");
			if (cusAddressDetails.getPaddAddressCountryId() != null)
				throw new ValidateRecordException(cusAddressDetails.getPaddAddressCountryId(), "countryGeoId");

			customerAddressListRecord.setPaddAddressTypeCommonListId(addressDetailsResource.getAddressTypeId());
			customerAddressListRecord.setPaddAddress01(addressDetailsResource.getAddress1());
			customerAddressListRecord.setPpaddId(cusAddressDetails.getValue().getPpaddId());
		}
		customerAddressList.add(customerAddressListRecord);

		List<AddressDetail> isPresentAddressDetail = addressDetailRepository.findByCustomerId(customer.getId());
		if(!isPresentAddressDetail.isEmpty()) {
			List<AddressDetail> updateAddressDetailList = new ArrayList<>();
			for(AddressDetail recAddressDetail:isPresentAddressDetail) {
				AddressDetail AddressDetailRec = recAddressDetail;
				for(ResponseCustomerAddressListResource responseCustomerAddressList:customerAddressList) {
					if(responseCustomerAddressList.getPaddAddressTypeCommonListId().equals(recAddressDetail.getAddressTypeId().toString()) &&
							responseCustomerAddressList.getPaddAddress01().equals(recAddressDetail.getAddress1())) {
						AddressDetailRec.setPpaddId(Long.parseLong(responseCustomerAddressList.getPpaddId()));
					}
				}
				updateAddressDetailList.add(AddressDetailRec);
			}
			addressDetailRepository.saveAll(updateAddressDetailList);
		}
	}

	private void createCustomerIdentification(String tenantId, CustomerResource customerResource, String userName,String penCustomerrId,Customer saveCustomer) {
		
		List<ResponseCustomerIdentificationListResource> customerIdentificationList = new ArrayList<>();
		ResponseCustomerIdentificationListResource customerIdentificationRecord = new ResponseCustomerIdentificationListResource();
	
		for (IdentificationDetailResource identificationDetailRec : customerResource.getIdentification()) {
			
			AddPersonIdentificationRequestResource customerIdentification = new AddPersonIdentificationRequestResource();
			
			customerIdentification.setSulpId(customerResource.getPerId());
			customerIdentification.setPsulpId(penCustomerrId);
			customerIdentification.setPidtIdentificationTypeId(identificationDetailRec.getIdentificationTypeId());
			customerIdentification.setPidtIdentificationTypeDesc(identificationDetailRec.getIdentificationType());
			customerIdentification.setPidtIdentificationNo(identificationDetailRec.getIdentificationNo());
			customerIdentification.setPidtExpiryDate(identificationDetailRec.getExpiryDate().replaceAll("-", "/"));
			customerIdentification.setPidtIssueDate(identificationDetailRec.getIssueDate().replaceAll("-", "/"));
			customerIdentification.setPidtStatus("ACTIVE");
			customerIdentification.setPidtId(identificationDetailRec.getPidtId());
			customerIdentification.setPpidtId(identificationDetailRec.getPpidtId());
			
			ResponseCustomerIdentificationResource cusIdentificationDetails = service.createCustomerIdentification(tenantId, customerIdentification, userName);

			if (cusIdentificationDetails.getPidtIdentificationNo() != null)
				throw new ValidateRecordException(cusIdentificationDetails.getPidtIdentificationNo(),"identificationNo");
			if (cusIdentificationDetails.getPidtIdentificationTypeDesc() != null)
				throw new ValidateRecordException(cusIdentificationDetails.getPidtIdentificationTypeDesc(),"identificationType");
			if (cusIdentificationDetails.getPidtIdentificationTypeId() != null)
				throw new ValidateRecordException(cusIdentificationDetails.getPidtIdentificationTypeId(),"identificationTypeId");
			if (cusIdentificationDetails.getPidtExpiryDate() != null)
				throw new ValidateRecordException(cusIdentificationDetails.getPidtExpiryDate(), "identificationNo");
			if (cusIdentificationDetails.getPidtIssueDate() != null)
				throw new ValidateRecordException(cusIdentificationDetails.getPidtIssueDate(), "identificationNo");

			customerIdentificationRecord.setIdentificationType(Long.parseLong(identificationDetailRec.getIdentificationTypeId()));
			customerIdentificationRecord.setIdentificationTypeDesc(identificationDetailRec.getIdentificationType());
			customerIdentificationRecord.setPidtIdentificationNo(identificationDetailRec.getIdentificationNo());
			customerIdentificationRecord.setPpidtId(cusIdentificationDetails.getValue().getPpidtId());
		}
		customerIdentificationList.add(customerIdentificationRecord);
		
		List<IdentificationDetail> isPresentIdentificationDetail = identificationDetailRepository.findByCustomerId(saveCustomer.getId());
		
		if(!isPresentIdentificationDetail.isEmpty()) {
			List<IdentificationDetail> updateIdentificationDetailList = new ArrayList<>();
			
			for(IdentificationDetail recIdentificationDetail:isPresentIdentificationDetail) {
				IdentificationDetail identificationDetailRec = recIdentificationDetail;
			
				for (ResponseCustomerIdentificationListResource responseCustomerIdentificationList:customerIdentificationList) {
					 
					if(responseCustomerIdentificationList.getIdentificationType()==recIdentificationDetail.getIdentificationTypeId() &&
					    responseCustomerIdentificationList.getIdentificationTypeDesc().equals(recIdentificationDetail.getIdentificationType()) &&
					    responseCustomerIdentificationList.getPidtIdentificationNo().equals(recIdentificationDetail.getIdentificationNo())) {
						 identificationDetailRec.setPpidtId(Long.parseLong(responseCustomerIdentificationList.getPpidtId()));
					 }
				}
				updateIdentificationDetailList.add(identificationDetailRec);
			}
			identificationDetailRepository.saveAll(updateIdentificationDetailList);
		}
	}

	private void createCustomerContact(String tenantId, CustomerResource customerResource, String userName,String penCustomerrId,Customer customer) {
		List<ResponseCustomerContactListResource> customerContactList = new ArrayList<>();	
		ResponseCustomerContactListResource customerContactListRecord = new ResponseCustomerContactListResource();
		for (ContactDetailsResource contactDetailsResource : customerResource.getContact()) {
			AddPersonContactRequestResource customerContactRequest = new AddPersonContactRequestResource();
			customerContactRequest.setSulpId(customerResource.getPerId());
			customerContactRequest.setPsulpId(penCustomerrId);
			customerContactRequest.setPconContactTypeId(contactDetailsResource.getContactTypeId());
			customerContactRequest.setPconContactTypeDesc(contactDetailsResource.getContactType());
			customerContactRequest.setPconValue(contactDetailsResource.getContactNo());
			customerContactRequest.setPconStatus("ACTIVE");
			customerContactRequest.setPconId(contactDetailsResource.getPconId());
			ResponseCustomerContactResource cusConatcDetails = service.createCustomerContact(tenantId,
					customerContactRequest, userName);

			if (cusConatcDetails.getPconContactTypeId() != null)
				throw new ValidateRecordException(cusConatcDetails.getPconContactTypeId(), "contactTypeId");
			if (cusConatcDetails.getPconValue() != null)
				throw new ValidateRecordException(cusConatcDetails.getPconValue(), "contactNo");

			customerContactListRecord.setPconContactTypeId(contactDetailsResource.getContactTypeId());
			customerContactListRecord.setPconValue(contactDetailsResource.getContactNo());
			customerContactListRecord.setPpconId(cusConatcDetails.getValue().getPpconId());
		}
		customerContactList.add(customerContactListRecord);
		
		List<ContactDetail> isPresentContactDetail = contactDetailRepository.findByCustomerId(customer.getId());
		if(!isPresentContactDetail.isEmpty()) {
			List<ContactDetail> updateContactDetail = new ArrayList<>();
			for(ContactDetail recContactDetail:isPresentContactDetail) {
				ContactDetail contactDetailRec = recContactDetail;
				for(ResponseCustomerContactListResource responseCustomerContactList:customerContactList ) {
					if(responseCustomerContactList.getPconContactTypeId().equals(recContactDetail.getContactTypeId().toString()) &&
					   responseCustomerContactList.getPconValue().equals(recContactDetail.getContactNo())) {
						contactDetailRec.setPpconId(Long.parseLong(responseCustomerContactList.getPpconId()));
					}
				}
				updateContactDetail.add(contactDetailRec);
			}
			contactDetailRepository.saveAll(updateContactDetail);
		}
	}

	private void createPerson(String tenantId, @Valid CustomerResource customerResource, String userName) {
		CustomerBasicInfoResponseResource customer = new CustomerBasicInfoResponseResource();
		customer = service.getPersonOrCustomer(tenantId, customerResource);
		if (customer.getCusReferenceCode() != null) {
			customerReferenceCode = customer.getCusReferenceCode();
			pendingCustomerrId = updateCustomer(tenantId, customerResource, customer, userName);
		} else
			pendingCustomerrId = addCommonCustomer(tenantId, customerResource);

	}

	private String updateCustomer(String tenantId, @Valid CustomerResource customerResource,CustomerBasicInfoResponseResource customer, String userName) {
		UpdateCustomerBasicInfoRequestResource customerDet = new UpdateCustomerBasicInfoRequestResource();
		CustomerIndividualInfoRequestResource individualInfo = new CustomerIndividualInfoRequestResource();
		CustomerCorporateBasicInfoRequestResource coporateInfo = new CustomerCorporateBasicInfoRequestResource();

		customerDet.setPerId(customer.getId().toString());
		customerDet.setPerCode(customer.getPerCode());
		customerDet.setCusReferenceCode(customer.getCusReferenceCode());
		customerDet.setCusOrganizationTypeCommonListId(customer.getCusOrganizationTypeCommonListId().toString());
		customerDet.setCusOrganizationTypeDesc(customer.getCusOrganizationTypeDesc());
		customerDet.setCusStatus(customer.getCusStatus().toString());

		if (customerResource.getCustomerType().equals("INDIVIDUAL")) {
			individualInfo.setPerTitleCommonListId(customerResource.getTitleId());
			individualInfo.setPerTitleDesc(customerResource.getTitle());
			individualInfo.setPerFirstName(customerResource.getFirstName());
			individualInfo.setPerMiddleName(customerResource.getMiddleName());
			individualInfo.setPerLastName(customerResource.getLastName());
			individualInfo.setPerFullName(customerResource.getFullName());
			individualInfo.setPerInitials(customerResource.getInitials());
			individualInfo.setPerGenderCommonListId(customerResource.getGenderId());
			individualInfo.setPerGenderDesc(customerResource.getGender());
			customerDet.setPerIndividualInfo(individualInfo);
		}

		if (customerResource.getCustomerType().equals("COPERATE")) {
			coporateInfo.setPerCompanyName(customerResource.getCompanyName());
			coporateInfo.setPerCorporateCategoryCommonListId(customerResource.getCorporateCategoryId());
			coporateInfo.setPerCorporateCategoryDesc(customerResource.getCorporateCategoryName());
			coporateInfo.setPerBusinessRegNo(customerResource.getBrNumber());
			customerDet.setPerCorporateInfo(coporateInfo);
		}

		ResponseCustomerValueResource customerResponce = service.updateCustomer(tenantId, customerDet, customer,userName);
		return customerResponce.getValue().getPenCustomerId().toString();
	}

	private void createPendingPerson(String tenantId, @Valid CustomerResource customerResource, String userName) {
		PendingCustomerBasicInfoResponseResource pendingCustomer = new PendingCustomerBasicInfoResponseResource();
		pendingCustomer = service.getPendingPersonOrCustomer(tenantId, customerResource);

		if (pendingCustomer.getPcusPendingStatus() != CustomerPendingStatus.PENDING
				&& pendingCustomer.getPcusPendingStatus() != null)
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "penPerId");

		if (pendingCustomer.getPcusReferenceCode() != null) {
			customerReferenceCode = pendingCustomer.getPcusReferenceCode();
			pendingCustomerrId = updatePendingCustomer(tenantId, customerResource, pendingCustomer, userName);
		} else
			pendingCustomerrId = addCommonCustomer(tenantId, customerResource);

	}

	private String updatePendingCustomer(String tenantId, CustomerResource customerResource,
		PendingCustomerBasicInfoResponseResource pendingCustomer, String userName) {
		UpdateCustomerBasicInfoRequestResource customerDet = new UpdateCustomerBasicInfoRequestResource();
		CustomerIndividualInfoRequestResource individualInfo = new CustomerIndividualInfoRequestResource();
		CustomerCorporateBasicInfoRequestResource coporateInfo = new CustomerCorporateBasicInfoRequestResource();

		customerDet.setPerId(pendingCustomer.getId().toString());
		customerDet.setPerCode(pendingCustomer.getPperCode());
		customerDet.setCusReferenceCode(pendingCustomer.getPcusReferenceCode());
		customerDet.setCusOrganizationTypeCommonListId(pendingCustomer.getPcusOrganizationTypeCommonListId().toString());
		customerDet.setCusOrganizationTypeDesc(pendingCustomer.getPcusOrganizationTypeDesc());
		customerDet.setCusStatus(pendingCustomer.getPcusStatus().toString());

		if (customerResource.getCustomerType().equals("INDIVIDUAL")) {
			individualInfo.setPerTitleCommonListId(customerResource.getTitleId());
			individualInfo.setPerTitleDesc(customerResource.getTitle());
			individualInfo.setPerFirstName(customerResource.getFirstName());
			individualInfo.setPerMiddleName(customerResource.getMiddleName());
			individualInfo.setPerLastName(customerResource.getLastName());
			individualInfo.setPerFullName(customerResource.getFullName());
			individualInfo.setPerInitials(customerResource.getInitials());
			individualInfo.setPerGenderCommonListId(customerResource.getGenderId());
			individualInfo.setPerGenderDesc(customerResource.getGender());
			customerDet.setPerIndividualInfo(individualInfo);
		}

		if (customerResource.getCustomerType().equals("COPERATE")) {
			coporateInfo.setPerCompanyName(customerResource.getCompanyName());
			coporateInfo.setPerCorporateCategoryCommonListId(customerResource.getCorporateCategoryId());
			coporateInfo.setPerCorporateCategoryDesc(customerResource.getCorporateCategoryName());
			coporateInfo.setPerBusinessRegNo(customerResource.getBrNumber());
			customerDet.setPerCorporateInfo(coporateInfo);
		}

		ResponseCustomerValueResource customerResponce = service.updatePendingCustomer(tenantId, customerDet,
				customerResource, userName);
		return customerResponce.getValue().getPenCustomerId().toString();
	}

	private void updateCustomer(Customer customer, String pendingCustomerrId2, String customerReferenceCode2) {
		Customer updateCustomer = customer;
		PendingCustomerBasicInfoResponseResource pendingCustomerBasicInfoResponseResource = service.getPendingCustomerDetail(customer.getTenantId(),pendingCustomerrId2);
		if(pendingCustomerBasicInfoResponseResource.getPcusPenPernId()!=null) {
			updateCustomer.setPenPerId(pendingCustomerBasicInfoResponseResource.getPcusPenPernId().toString());
			updateCustomer.setPerCode(pendingCustomerBasicInfoResponseResource.getPperCode());
		}
		updateCustomer.setPendingCustomerId(Long.parseLong(pendingCustomerrId2));
		updateCustomer.setCusReferenceCode(customerReferenceCode2);
		customerRepository.saveAndFlush(updateCustomer);
	}

	@Override
	public List<Customer> getByLeadId(Long leadId) {
		List<Customer> customerList = customerRepository.findByLeadId(leadId);
		for(Customer customers : customerList) {

			List<IdentificationDetail> identificationDetails = identificationDetailRepository
					.findByCustomerId(customers.getId());
			List<ContactDetail> contactDetails = contactDetailRepository.findByCustomerId(customers.getId());
			List<AddressDetail> addressDetails = addressDetailRepository.findByCustomerId(customers.getId());
			if(customers.getInternalCribStatusId()!=null) {
			Optional<CommonList> commonList=commonListRepository.findById(customers.getInternalCribStatusId());
			customers.setInternalCribStatus(commonList.get().getName());
			}
				
			if(customers.getExternalCribStatusId()!=null) {
			Optional<CommonList> commonList=commonListRepository.findById(customers.getExternalCribStatusId());
			customers.setExternalCribStatus(commonList.get().getName());
			}
			
			customers.setIdentificationDetails(identificationDetails);
			customers.setContactDetails(contactDetails);
			customers.setAddressDetails(addressDetails);
			
		}
		return customerList;
	}
	
	//piyumi
	private void createCustomerRelation(String tenantId,LinkedPersonResource linkedPersonResource, Long penCustomerId) {
		AddRelationshipBasicInfoRequestResource addRelationshipBasicInfoRequestResource = new AddRelationshipBasicInfoRequestResource();
		PersonIndividualInfoRequestResource perIndividualInfo = new PersonIndividualInfoRequestResource();
		ResponseCustomerRelationshipResource responseCustomerRelationshipResource = new ResponseCustomerRelationshipResource();
		
		PersonCodeResponce personCode = validateService.generatePersonCode(tenantId,"PERSON");
		if(personCode==null)
		    throw new ValidateRecordException(environment.getProperty("person.service-error"), "message");
		
		addRelationshipBasicInfoRequestResource.setPerCode(personCode.getValue());
		addRelationshipBasicInfoRequestResource.setCurStatus("ACTIVE");
		addRelationshipBasicInfoRequestResource.setCurDependentStatus("NO");
		addRelationshipBasicInfoRequestResource.setCurNomineeStatus("NO");
		perIndividualInfo.setPerTitleCommonListId(linkedPersonResource.getTitleId());
		perIndividualInfo.setPerTitleDesc(linkedPersonResource.getTitle());
		perIndividualInfo.setPerFirstName(linkedPersonResource.getFirstName());
		perIndividualInfo.setPerMiddleName(linkedPersonResource.getMiddleName());
		perIndividualInfo.setPerLastName(linkedPersonResource.getLastName());
		perIndividualInfo.setPerFullName(linkedPersonResource.getFullname());
		perIndividualInfo.setPerGenderCommonListId(linkedPersonResource.getGenderId());
		perIndividualInfo.setPerGenderDesc(linkedPersonResource.getGender());
		addRelationshipBasicInfoRequestResource.setPerIndividualInfo(perIndividualInfo);
		
		responseCustomerRelationshipResource = validateService.saveCommonCustomerRelationship(tenantId, LogginAuthentcation.getInstance().getUserName(),addRelationshipBasicInfoRequestResource,penCustomerId);
		
		Optional<LinkedPerson> isPresentLinkedPerson = linkedPersonRepository.findById(validateService.stringToLong(linkedPersonResource.getId()));
		if(isPresentLinkedPerson.isPresent()) {
			LinkedPerson updateLinkedPerson = isPresentLinkedPerson.get();
			updateLinkedPerson.setPculpId(responseCustomerRelationshipResource.getValue().getPculpId());
			linkedPersonRepository.saveAndFlush(updateLinkedPerson);
		}		
		createLinkedPersonIdentification(tenantId,validateService.stringToLong(responseCustomerRelationshipResource.getValue().getPculpId()),linkedPersonResource.getIdentificationDetailList(),penCustomerId);	
	}
	
	//piyumi
		private void createCustomerKeyPerson(String tenantId,LinkedPersonResource linkedPersonResource, Long penCustomerId) {
			AddKeyPersonBasicInfoRequestResource addKeyPersonBasicInfoRequestResource = new AddKeyPersonBasicInfoRequestResource();
			PersonIndividualInfoRequestResource perIndividualInfo = new PersonIndividualInfoRequestResource();
			ResponseCustomerKeyPersonResource responseCustomerKeyPersonResource = new ResponseCustomerKeyPersonResource();
			
			PersonCodeResponce personCode = validateService.generatePersonCode(tenantId,"PERSON");
			if(personCode==null)
			    throw new ValidateRecordException(environment.getProperty("person.service-error"), "message");
			
			addKeyPersonBasicInfoRequestResource.setPerCode(personCode.getValue());
			addKeyPersonBasicInfoRequestResource.setCkpStatus("ACTIVE");
			perIndividualInfo.setPerTitleCommonListId(linkedPersonResource.getTitleId());
			perIndividualInfo.setPerTitleDesc(linkedPersonResource.getTitle());
			perIndividualInfo.setPerFirstName(linkedPersonResource.getFirstName());
			perIndividualInfo.setPerMiddleName(linkedPersonResource.getMiddleName());
			perIndividualInfo.setPerLastName(linkedPersonResource.getLastName());
			perIndividualInfo.setPerFullName(linkedPersonResource.getFullname());
			perIndividualInfo.setPerGenderCommonListId(linkedPersonResource.getGenderId());
			perIndividualInfo.setPerGenderDesc(linkedPersonResource.getGender());
			addKeyPersonBasicInfoRequestResource.setPerIndividualInfo(perIndividualInfo);
			
			responseCustomerKeyPersonResource = validateService.saveCommonCustomerKeyPerson(tenantId, LogginAuthentcation.getInstance().getUserName(),addKeyPersonBasicInfoRequestResource,penCustomerId);
			
			Optional<LinkedPerson> isPresentLinkedPerson = linkedPersonRepository.findById(validateService.stringToLong(linkedPersonResource.getId()));
			if(isPresentLinkedPerson.isPresent()) {
				LinkedPerson updateLinkedPerson = isPresentLinkedPerson.get();
				updateLinkedPerson.setPculpId(responseCustomerKeyPersonResource.getValue().getPculpId());
				linkedPersonRepository.saveAndFlush(updateLinkedPerson);
			}		
			createLinkedPersonIdentification(tenantId,validateService.stringToLong(responseCustomerKeyPersonResource.getValue().getPculpId()),linkedPersonResource.getIdentificationDetailList(),penCustomerId);	
		}


	//piyumi
	private void createLinkedPersonIdentification(String tenantId, Long cusLinkPersonId2,List<IdentificationDetailResource> identificationDetailList,Long pendingCustomerId) {
			AddCustomerPersonIdentificationRequestResource addPersonIdentificationRequestResource = new AddCustomerPersonIdentificationRequestResource();
			
			for(IdentificationDetailResource identificationDetail : identificationDetailList) {
				addPersonIdentificationRequestResource.setPculpId(cusLinkPersonId2.toString());
				addPersonIdentificationRequestResource.setPidtIdentificationTypeId(identificationDetail.getIdentificationTypeId());
				addPersonIdentificationRequestResource.setPidtIdentificationTypeDesc(identificationDetail.getIdentificationType());
				addPersonIdentificationRequestResource.setPidtIdentificationNo(identificationDetail.getIdentificationNo());
				addPersonIdentificationRequestResource.setPidtStatus("ACTIVE");
				ResponseCustomerRelationshipIdentificationResource responseLinkedPersonRelationResource = validateService.saveCommonCustomerRelationshipIdentification(tenantId, LogginAuthentcation.getInstance().getUserName(),addPersonIdentificationRequestResource,pendingCustomerId);
			
				Optional<IdentificationDetail> isPresentIdentificationDetail = identificationDetailRepository.findById(validateService.stringToLong(identificationDetail.getId()));
				if(isPresentIdentificationDetail.isPresent()) {
					IdentificationDetail identification = isPresentIdentificationDetail.get();
					identification.setPpidtId(validateService.stringToLong(responseLinkedPersonRelationResource.getValue().getPpidtId()));
					identificationDetailRepository.saveAndFlush(identification);
				}	
			}
	}
	
	private void updateCustomerRelation(String tenantId,LinkedPersonResource linkedPersonResource, Long penCustomerId) {
		UpdateRelationshipBasicInfoRequestResource updateRelationshipBasicInfoRequestResource = new UpdateRelationshipBasicInfoRequestResource();
		PersonIndividualInfoRequestResource perIndividualInfo = new PersonIndividualInfoRequestResource();
		ResponseCustomerRelationshipResource responseCustomerRelationshipResource = new ResponseCustomerRelationshipResource();
		
		PersonCodeResponce personCode = validateService.generatePersonCode(tenantId,"PERSON");
		if(personCode==null)
		    throw new ValidateRecordException(environment.getProperty("person.service-error"), "message");
		
		updateRelationshipBasicInfoRequestResource.setPerCode(personCode.getValue());
		updateRelationshipBasicInfoRequestResource.setCurStatus("ACTIVE");
		updateRelationshipBasicInfoRequestResource.setCurDependentStatus("NO");
		updateRelationshipBasicInfoRequestResource.setCurNomineeStatus("NO");
		perIndividualInfo.setPerTitleCommonListId(linkedPersonResource.getTitleId());
		perIndividualInfo.setPerTitleDesc(linkedPersonResource.getTitle());
		perIndividualInfo.setPerFirstName(linkedPersonResource.getFirstName());
		perIndividualInfo.setPerMiddleName(linkedPersonResource.getMiddleName());
		perIndividualInfo.setPerLastName(linkedPersonResource.getLastName());
		perIndividualInfo.setPerFullName(linkedPersonResource.getFullname());
		perIndividualInfo.setPerGenderCommonListId(linkedPersonResource.getGenderId());
		perIndividualInfo.setPerGenderDesc(linkedPersonResource.getGender());
		updateRelationshipBasicInfoRequestResource.setPerIndividualInfo(perIndividualInfo);
		
		responseCustomerRelationshipResource = validateService.updateCustomerRelationship(tenantId,updateRelationshipBasicInfoRequestResource,penCustomerId,validateService.stringToLong(linkedPersonResource.getPculpId()) ,LogginAuthentcation.getInstance().getUserName());
		
		Optional<LinkedPerson> isPresentLinkedPerson = linkedPersonRepository.findById(validateService.stringToLong(linkedPersonResource.getId()));
		if(isPresentLinkedPerson.isPresent()) {
			LinkedPerson updateLinkedPerson = isPresentLinkedPerson.get();
			updateLinkedPerson.setPculpId(responseCustomerRelationshipResource.getValue().getPculpId());
			linkedPersonRepository.saveAndFlush(updateLinkedPerson);
		}		
		updateLinkedPersonIdentification(tenantId,validateService.stringToLong(responseCustomerRelationshipResource.getValue().getPculpId()),linkedPersonResource.getIdentificationDetailList(),penCustomerId);	
	}
	
	//piyumi
		private void updateCustomerKeyPerson(String tenantId,LinkedPersonResource linkedPersonResource, Long penCustomerId) {
			UpdateKeyPersonBasicInfoRequestResource updateKeyPersonBasicInfoRequestResource = new UpdateKeyPersonBasicInfoRequestResource();
			PersonIndividualInfoRequestResource perIndividualInfo = new PersonIndividualInfoRequestResource();
			ResponseCustomerKeyPersonResource responseCustomerKeyPersonResource = new ResponseCustomerKeyPersonResource();
			
			PersonCodeResponce personCode = validateService.generatePersonCode(tenantId,"PERSON");
			if(personCode==null)
			    throw new ValidateRecordException(environment.getProperty("person.service-error"), "message");
			
			updateKeyPersonBasicInfoRequestResource.setPerCode(personCode.getValue());
			updateKeyPersonBasicInfoRequestResource.setCurStatus("ACTIVE");
			updateKeyPersonBasicInfoRequestResource.setCurDependentStatus("NO");
			updateKeyPersonBasicInfoRequestResource.setCurNomineeStatus("NO");
			perIndividualInfo.setPerTitleCommonListId(linkedPersonResource.getTitleId());
			perIndividualInfo.setPerTitleDesc(linkedPersonResource.getTitle());
			perIndividualInfo.setPerFirstName(linkedPersonResource.getFirstName());
			perIndividualInfo.setPerMiddleName(linkedPersonResource.getMiddleName());
			perIndividualInfo.setPerLastName(linkedPersonResource.getLastName());
			perIndividualInfo.setPerFullName(linkedPersonResource.getFullname());
			perIndividualInfo.setPerGenderCommonListId(linkedPersonResource.getGenderId());
			perIndividualInfo.setPerGenderDesc(linkedPersonResource.getGender());
			updateKeyPersonBasicInfoRequestResource.setPerIndividualInfo(perIndividualInfo);
			
			responseCustomerKeyPersonResource = validateService.updateCustomerKeyPerson(tenantId,updateKeyPersonBasicInfoRequestResource,penCustomerId, validateService.stringToLong(linkedPersonResource.getPculpId()) ,LogginAuthentcation.getInstance().getUserName());
			
			Optional<LinkedPerson> isPresentLinkedPerson = linkedPersonRepository.findById(validateService.stringToLong(linkedPersonResource.getId()));
			if(isPresentLinkedPerson.isPresent()) {
				LinkedPerson updateLinkedPerson = isPresentLinkedPerson.get();
				updateLinkedPerson.setPculpId(responseCustomerKeyPersonResource.getValue().getPculpId());
				linkedPersonRepository.saveAndFlush(updateLinkedPerson);
			}		
			updateLinkedPersonIdentification(tenantId,validateService.stringToLong(responseCustomerKeyPersonResource.getValue().getPculpId()),linkedPersonResource.getIdentificationDetailList(),penCustomerId);	
		}


	//piyumi
	private void updateLinkedPersonIdentification(String tenantId, Long cusLinkPersonId2,List<IdentificationDetailResource> identificationDetailList,Long pendingCustomerId) {
		UpdateCustomerPersonIdentificationRequestResource updateCustomerPersonIdentificationRequestResource = new UpdateCustomerPersonIdentificationRequestResource();
			
			for(IdentificationDetailResource identificationDetail : identificationDetailList) {
				updateCustomerPersonIdentificationRequestResource.setPculpId(cusLinkPersonId2.toString());
				updateCustomerPersonIdentificationRequestResource.setPidtIdentificationTypeId(identificationDetail.getIdentificationTypeId());
				updateCustomerPersonIdentificationRequestResource.setPidtIdentificationTypeDesc(identificationDetail.getIdentificationType());
				updateCustomerPersonIdentificationRequestResource.setPidtIdentificationNo(identificationDetail.getIdentificationNo());
				updateCustomerPersonIdentificationRequestResource.setPidtStatus("ACTIVE");
				ResponseCustomerRelationshipIdentificationResource responseLinkedPersonRelationResource = validateService.updateLinkedPersonIdentification(tenantId,updateCustomerPersonIdentificationRequestResource,pendingCustomerId,cusLinkPersonId2,validateService.stringToLong(identificationDetail.getPpidtId()), LogginAuthentcation.getInstance().getUserName());
			
				Optional<IdentificationDetail> isPresentIdentificationDetail = identificationDetailRepository.findById(validateService.stringToLong(identificationDetail.getId()));
				if(isPresentIdentificationDetail.isPresent()) {
					IdentificationDetail identification = isPresentIdentificationDetail.get();
					identification.setPpidtId(validateService.stringToLong(responseLinkedPersonRelationResource.getValue().getPpidtId()));
					identificationDetailRepository.saveAndFlush(identification);
				}	
			}
	}

}

