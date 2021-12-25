package com.fusionx.lending.origination.service.impl;

/**
 * LeadInfoServiceImpl
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * <p>
 * 1	  09-08-2021	FXL-380		 FXL-421	Piyumi		 Modified
 * *******************************************************************************************************
 */

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.*;
import com.fusionx.lending.origination.enums.*;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.RecordNotFoundException;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.*;
import com.fusionx.lending.origination.resource.*;
import com.fusionx.lending.origination.service.FacilityService;
import com.fusionx.lending.origination.service.FinalApprovalService;
import com.fusionx.lending.origination.service.LeadInfoService;
import com.fusionx.lending.origination.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Transactional(rollbackFor = Exception.class)
public class LeadInfoServiceImpl extends MessagePropertyBase implements LeadInfoService {

    @Autowired
    private LeadInfoRepository leadInfoRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IdentificationDetailRepository identificationDetailRepository;

    @Autowired
    private ContactDetailRepository contactDetailRepository;

    @Autowired
    private AddressDetailRepository addressDetailRepository;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private GuarantorRepository guarantorRepository;

    @Autowired
    private FacilityContractDetailsRepository facilityContractRepository;

    @Autowired
    private FacilityOtherProductsRepository facilityOtherProductsRepository;

    @Autowired
    private FinalApprovalService finalApprovalService;

    @Autowired
    private CreditAppCollateralDetailsRepository creditAppCollateralDetailsRepository;

    @Autowired
    private DisbursementDetailsRepository disbursementDetailsRepository;

    @Autowired
    private AdditionalDocumentRepository additionalDocumentRepository;

    @Autowired
    private ReferenceDetailsRepository referenceDetailsRepository;

    @Autowired
    private AnalystDetailsRepository analystDetailsRepository;

    @Autowired
    ValidateService validateService;

    @Value("${final-approval-bpm}")
    private String urlApprovalProcess;

    private Timestamp getCreateOrModifyDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }

    @Override
    public Boolean existsById(Long id) {
        return leadInfoRepository.existsById(id);
    }

    @Override
    public LeadInfo findDetailById(Long id) {
        Optional<LeadInfo> optionalLeadInfo = leadInfoRepository.findById(id);

        if (optionalLeadInfo.isPresent()) {
            LeadInfo leadInfo = optionalLeadInfo.get();
            List<Customer> customers = customerRepository.findByLeadId(leadInfo.getId());
            Long mainCustomerId = null;
            if (customers != null && !customers.isEmpty()) {
                for (Customer customer : customers) {

                    if (customer.getCustomerMainType().equalsIgnoreCase(CustomerMainType.MAIN.toString()))
                        mainCustomerId = customer.getId();

                    List<IdentificationDetail> identificationDetails = identificationDetailRepository.findByCustomerId(customer.getId());
                    List<ContactDetail> contactDetails = contactDetailRepository.findByCustomerId(customer.getId());
                    List<AddressDetail> addressDetails = addressDetailRepository.findByCustomerId(customer.getId());

                    customer.setIdentificationDetails(identificationDetails);
                    customer.setContactDetails(contactDetails);
                    customer.setAddressDetails(addressDetails);
                }
            }
            FacilityParameter facilityParameter = facilityService.findFacilityCalculationDetailByLeadId(leadInfo.getId());
            List<Guarantor> guarantors = guarantorRepository.findByLeadIdId(leadInfo.getId());

            if (guarantors != null && !guarantors.isEmpty()) {
                for (Guarantor guarantor : guarantors) {
                    List<IdentificationDetail> identificationDetails = identificationDetailRepository.findByGuarantorId(guarantor.getId());
                    List<ContactDetail> contactDetails = contactDetailRepository.findByGuarantorId(guarantor.getId());
                    List<AddressDetail> addressDetails = addressDetailRepository.findByGuarantorId(guarantor.getId());

                    guarantor.setIdentificationDetails(identificationDetails);
                    guarantor.setContactDetails(contactDetails);
                    guarantor.setAddressDetails(addressDetails);
                }
            }

            if (mainCustomerId != null) {
                List<DisbursementDetails> disbursementDetails = disbursementDetailsRepository.findByCustomerId(mainCustomerId);
                List<AdditionalDocument> additionalDocuments = additionalDocumentRepository.findByCustomerId(mainCustomerId);
                leadInfo.setAdditionalDocuments(additionalDocuments);
                leadInfo.setDisbursementDetails(disbursementDetails);
            }

            List<FacilityContractDetails> facilityContractDetails = facilityContractRepository.findByleadInfoId(leadInfo.getId());
            List<FacilityOtherProducts> facilityOtherProducts = facilityOtherProductsRepository.findByleadInfoId(leadInfo.getId());
            List<ReferenceDetails> referenceDetails = referenceDetailsRepository.findByLeadInfoId(leadInfo.getId());
            List<AnalystDetails> analystDetails = analystDetailsRepository.findByLeadInfoId(leadInfo.getId());

            leadInfo.setCustomers(customers);
            leadInfo.setFacilityParameter(facilityParameter);
            leadInfo.setGuarantors(guarantors);
            leadInfo.setFacilityContractDetails(facilityContractDetails);
            leadInfo.setFacilityOtherProducts(facilityOtherProducts);
            leadInfo.setReferenceDetails(referenceDetails);
            leadInfo.setAnalystDetails(analystDetails);
            return leadInfo;
        } else {
            return null;
        }
    }

    @Override
    public Optional<LeadInfo> findById(Long leadId) {
        Optional<LeadInfo> leadInforOpt = leadInfoRepository.findById(leadId);
        setTransientDetails(leadInforOpt.get());
        return leadInforOpt;
    }

    @Override
    public LeadInfo finalApprovalUpdate(String tenantId, LeadInfo leadInfo, CommonStatus commonStatus) {
        leadInfo.setStatus(commonStatus.toString());
        // added menukaj
        pendingApprovalAndFusionSubmit(leadInfo);
        updateFinal(leadInfo.getId(), commonStatus.equals(commonStatus.APPROVED) ? WorkflowStatus.COMPLETE : WorkflowStatus.REJECT);
        return leadInfoRepository.saveAndFlush(leadInfo);
    }

    private void updateFinal(Long id, Object object) {
    }

    @Override
    public ApprovalDetail save(String tenantId, @Valid ApprovalDataResource approvalDataResource) {

        DynaBPMStaticParamsResource bpmStaticParamsResource = new DynaBPMStaticParamsResource();

        Long processId = validateService.initiateWorkFlowDynamic(urlApprovalProcess, bpmStaticParamsResource, WorkflowType.FINAL_APPROVAL);
        return createApprovalDetail(tenantId, processId, WorkflowType.FINAL_APPROVAL, approvalDataResource);
    }

    private ApprovalDetail createApprovalDetail(String tenantId, Long processId, WorkflowType finalApproval,
                                                @Valid ApprovalDataResource approvalDataResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty("common.user-not-found"));

        ApprovalDetail approvalDetail = new ApprovalDetail();

        approvalDetail.setComment(approvalDataResource.getComment());
        approvalDetail.setStatus("PENDING");
        approvalDetail.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        approvalDetail.setCreatedDate(getCreateOrModifyDate());
        return null;
    }


    /**
     * @author MenukaJ
     */
    private void pendingApprovalAndFusionSubmit(LeadInfo leadInfo) {

        // FusionCreditAppraisalDetailsResource appraisalDetailsResource = new
        // FusionCreditAppraisalDetailsResource();
        PendingCutomerApproveRequestResource pendingCutomerApproveRequestResource = new PendingCutomerApproveRequestResource();
        List<PendingCustomerListResource> customerListResources = new ArrayList<>();
        PendingSuppliesApproveRequestResource pendingSuppliesApproveRequestResource = new PendingSuppliesApproveRequestResource();
        List<PendingSuppliesListResource> suppliesListResources = new ArrayList<>();
        List<Customer> customerList = null;
        List<Guarantor> guarantorList = null;
        AssertApproveRequestResource assertApproveRequestResource = new AssertApproveRequestResource();
        List<AssertListResource> assertListResources = new ArrayList<>();

        List<Customer> customers = customerRepository.findByLeadId(leadInfo.getId());

        if (customers != null && !customers.isEmpty()) {
            for (Customer customer : customers) {
                PendingCustomerListResource customerResource = new PendingCustomerListResource();
                if (customer.getPendingCustomerId() != null) {
                    customerResource.setPenCusId(customer.getPendingCustomerId().toString());
                    customerListResources.add(customerResource);
                }
            }
            pendingCutomerApproveRequestResource.setPenCusIds(customerListResources);
            if (customerListResources != null && !customerListResources.isEmpty())
                customerList = finalApprovalService.bulkPendingCusApproval(leadInfo.getTenantId(),
                        pendingCutomerApproveRequestResource);
        }

        List<Guarantor> guarantors = guarantorRepository.findByLeadIdId(leadInfo.getId());

        if (guarantors != null && !guarantors.isEmpty()) {
            PendingSuppliesListResource pendingSuppliesListResource = new PendingSuppliesListResource();
            for (Guarantor guarantor : guarantors) {
                if (guarantor.getPendingGuarantorId() != null) {
                    pendingSuppliesListResource.setPenSupId(guarantor.getPendingGuarantorId().toString());
                    suppliesListResources.add(pendingSuppliesListResource);
                }
            }
            pendingSuppliesApproveRequestResource.setPenSupIds(suppliesListResources);
            if (suppliesListResources != null && !suppliesListResources.isEmpty())
                guarantorList = finalApprovalService.bulkPendingSupApproval(leadInfo.getTenantId(),
                        pendingSuppliesApproveRequestResource);
        }

        if (customerList != null && !customerList.isEmpty()) {

            for (Customer customer : customerList) {
                List<CreditAppCollateralDetail> creditAppCollateralDetails = creditAppCollateralDetailsRepository
                        .findByCustomerId(customer.getCustomerId());

                if (creditAppCollateralDetails != null && !creditAppCollateralDetails.isEmpty()) {
                    for (CreditAppCollateralDetail creditAppCollateralDetail : creditAppCollateralDetails) {
                        AssertListResource assertListResource = new AssertListResource();
                        if (creditAppCollateralDetail.getAssetEntityId() != null)
                            assertListResource
                                    .setAssetEntityId(creditAppCollateralDetail.getAssetEntityId().toString());
                        if (creditAppCollateralDetail.getFinalValuationId() != null)
                            assertListResource
                                    .setFinalValuationId(creditAppCollateralDetail.getFinalValuationId().toString());
                        assertListResources.add(assertListResource);
                    }
                }
            }
            assertApproveRequestResource.setAssertList(assertListResources);
            if (assertListResources != null && !assertListResources.isEmpty())
                finalApprovalService.bulkAssertsApproval(leadInfo.getTenantId(), assertApproveRequestResource);
        }

        // finalApprovalService.saveCreditApperaslToFusion(leadInfo, customers,
        // guarantors);
    }

    @Override
    public Page<LeadInfo> findAll(Pageable pageable) {
        return leadInfoRepository.findAll(pageable);
    }

    @Override
    public List<LeadInfo> findByLeadStatus(String status) {
        List<LeadInfo> leadInforList = leadInfoRepository.findByLeadStatus(LeadStatus.valueOf(status));

        for (LeadInfo leadInfor : leadInforList) {
            setTransientDetails(leadInfor);
        }
        return leadInforList;
    }

    @Override
    public Long updateAssignedBranch(String tenantId,
                                     LeadInforBranchAssignUpdateResource leadInforBranchAssignResource) {

        validateService.validateCommonBranch(tenantId, leadInforBranchAssignResource.getBranchId(),
                leadInforBranchAssignResource.getBranchName());

        for (LeadListResource lead : leadInforBranchAssignResource.getLeadIdList()) {
            Long leadId = validateService.stringToLong(lead.getLeadId());
            Long version = validateService.stringToLong(lead.getVersion());

            Optional<LeadInfo> leadInforOpt = leadInfoRepository.findById(leadId);

            if (!leadInforOpt.isPresent())
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "LeadId");

            if (leadInforOpt.get().getVersion().equals(version))
                throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION);


            LeadInfo LeadInfo = leadInforOpt.get();
            LeadInfo.setBranchId(validateService.stringToLong(leadInforBranchAssignResource.getBranchId()));
            LeadInfo.setBranchName(leadInforBranchAssignResource.getBranchName());
            LeadInfo.setLeadStatus(LeadStatus.BR_ASSIGNED);
            LeadInfo.setModifiedDate(validateService.getCreateOrModifyDate());
            LeadInfo.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
            LeadInfo.setSyncTs(validateService.getSyncTs());
            leadInfoRepository.save(LeadInfo);

        }
        return validateService.stringToLong(leadInforBranchAssignResource.getBranchId());
    }

    @Override
    public Long updateAssignedMarketingOfficer(String tenantId,
                                               LeadInforMarketingOfficerAssignUpdateResource leadInforMarketingOfficerAssignResource) {

        String theUserId = leadInforMarketingOfficerAssignResource.getMarketingOfficerId();
        String theUserName = leadInforMarketingOfficerAssignResource.getMarketingOfficerName();

        validateService.validateUserProfileByUserId(tenantId, theUserId, theUserName);

        for (LeadListResource lead : leadInforMarketingOfficerAssignResource.getLeadIdList()) {
            Long leadId = validateService.stringToLong(lead.getLeadId());
            Long version = validateService.stringToLong(lead.getVersion());

            Optional<LeadInfo> leadInforOpt = leadInfoRepository.findById(leadId);

            if (!leadInforOpt.isPresent())
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "LeadId");

            if (leadInforOpt.get().getVersion().equals(version))
                throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION);

            LeadInfo LeadInfo = leadInforOpt.get();
            LeadInfo.setMarketingOfficerId(validateService.stringToLong(theUserId));
            LeadInfo.setLeadStatus(LeadStatus.MO_ASSIGNED);
            LeadInfo.setModifiedDate(validateService.getCreateOrModifyDate());
            LeadInfo.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
            LeadInfo.setSyncTs(validateService.getSyncTs());
            leadInfoRepository.save(LeadInfo);

        }
        return validateService.stringToLong(leadInforMarketingOfficerAssignResource.getMarketingOfficerId());
    }

    @Override
    public Long updateCurrentUserForMarketingOfficer(String tenantId,
                                                     LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserResource) {

        validateService.validateCommonBranch(tenantId, leadInforAssignToCurrentUserResource.getBranchId(),
                leadInforAssignToCurrentUserResource.getBranchName());

        String theUserId = leadInforAssignToCurrentUserResource.getMarketingOfficerId();
        String theUserName = leadInforAssignToCurrentUserResource.getMarketingOfficerName();

        validateService.validateUserProfileByUserId(tenantId, theUserId, theUserName);

        for (LeadListResource lead : leadInforAssignToCurrentUserResource.getLeadIdList()) {

            Long leadId = validateService.stringToLong(lead.getLeadId());
            Long version = validateService.stringToLong(lead.getVersion());

            Optional<LeadInfo> leadInforOpt = leadInfoRepository.findById(leadId);

            if (!leadInforOpt.isPresent())
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "LeadId");

            if (leadInforOpt.get().getVersion().equals(version))
                throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION);

            LeadInfo LeadInfo = leadInforOpt.get();
            LeadInfo.setBranchId(validateService.stringToLong(leadInforAssignToCurrentUserResource.getBranchId()));
            LeadInfo.setBranchName(leadInforAssignToCurrentUserResource.getBranchName());
            LeadInfo.setMarketingOfficerId(validateService.stringToLong(theUserId));
            LeadInfo.setLeadStatus(LeadStatus.MO_ASSIGNED);
            LeadInfo.setModifiedDate(validateService.getCreateOrModifyDate());
            LeadInfo.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
            LeadInfo.setSyncTs(validateService.getSyncTs());
            leadInfoRepository.save(LeadInfo);

        }
        return validateService.stringToLong(leadInforAssignToCurrentUserResource.getMarketingOfficerId());
    }

    @Override
    public List<LeadInfo> getByDate(String date) {

        String fromDateStr = date + " 00:00:00";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fromDate = null;
        Date toDate = null;
        try {

            fromDate = formatter.parse(fromDateStr);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromDate);
            calendar.add(Calendar.DATE, 1);
            toDate = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<LeadInfo> leadInforList = leadInfoRepository.findByCreatedDateBetween(fromDate, toDate);

        for (LeadInfo leadInfor : leadInforList) {
            setTransientDetails(leadInfor);
        }
        return leadInforList;
    }

    @Override
    public List<LeadInfo> getByCustomerNameContaining(String customerName) {
        List<LeadInfo> leadList = leadInfoRepository.findAll();
        List<Customer> customerList = customerRepository.findByFullNameContaining(customerName);

        if (!customerList.isEmpty()) {
            leadList.clear();
            for (Customer customer : customerList) {
                if (customer.getLead() != null) {
                    Optional<LeadInfo> leadInfo = leadInfoRepository.findById(customer.getLead().getId());

                    if (leadInfo.isPresent())
                        setTransientDetails(leadInfo.get());
                    leadList.add(leadInfo.get());
                }
            }
            return leadList;
        }

        return null;
    }

    @Override
    public List<LeadInfo> getByBranch(Long branchId) {
        List<LeadInfo> leadInforList = leadInfoRepository.findByBranchId(branchId);

        for (LeadInfo leadInfor : leadInforList) {
            setTransientDetails(leadInfor);
        }
        return leadInforList;
    }

    @Override
    public List<LeadInfo> getByStatus(String status) {
        List<LeadInfo> leadInforList = leadInfoRepository.findByStatus(CommonStatus.valueOf(status));

        for (LeadInfo leadInfor : leadInforList) {
            setTransientDetails(leadInfor);
        }
        return leadInforList;
    }

    public void setTransientDetails(LeadInfo leadInfo) {
        List<Customer> customerList = customerRepository.findByLeadId(leadInfo.getId());
        leadInfo.setCustomers(customerList);
        leadInfo.setLeadStatusDesc(LeadStatus.getDescriptionValue(leadInfo.getLeadStatus()));

    }

    @Override
    public List<LeadInfo> findByStatus(String status) {
        List<LeadInfo> leadInforList = leadInfoRepository.findByStatus(status);

        for (LeadInfo leadInfor : leadInforList) {
            setTransientDetails(leadInfor);
        }
        return leadInforList;
    }
    @Override
	public String upadetFinalApprovalFlowStatus(Long id, Boolean approve) {
		Optional<LeadInfo> optionalLeadInfo = leadInfoRepository.findById(id);
		LeadInfo leadInfo = null;
		if (optionalLeadInfo.isPresent()) {
			leadInfo = optionalLeadInfo.get();
			if(approve) {
				leadInfo.setStatus(CommonStatus.APPROVED.toString());
			}else {
				leadInfo.setStatus(CommonStatus.REJECT.toString());
			}
			leadInfo.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			leadInfo.setModifiedDate(validateService.getCreateOrModifyDate());
			leadInfo=leadInfoRepository.save(leadInfo);
		}else {
			throw new RecordNotFoundException(environment.getProperty(NOT_FOUND));
		}
		return leadInfo.getId()+"::"+leadInfo.getStatus();
	}
}
