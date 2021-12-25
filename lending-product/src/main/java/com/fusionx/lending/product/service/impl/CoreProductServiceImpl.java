package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CoreProduct;
import com.fusionx.lending.product.domain.CoreProductPending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.domain.SalesAccessChannel;
import com.fusionx.lending.product.domain.ServiceAccessChannel;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CoreProductPendingRepository;
import com.fusionx.lending.product.repository.CoreProductRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.OtherFeeTypeRepository;
import com.fusionx.lending.product.repository.SalesAccessChannelRepository;
import com.fusionx.lending.product.repository.ServiceAccessChannelRepository;
import com.fusionx.lending.product.resources.CoreProductAddResource;
import com.fusionx.lending.product.resources.CoreProductUpdateResource;
import com.fusionx.lending.product.resources.Currency;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.CoreProductHistoryService;
import com.fusionx.lending.product.service.CoreProductService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * API Service related to  Core Product
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
 * 1        23-09-2021      -               FXL-795     Dilhan                   Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CoreProductServiceImpl extends MessagePropertyBase implements CoreProductService{

	@Autowired
	private CoreProductRepository coreProductRepository;
	
	@Autowired
	private CoreProductPendingRepository coreProductPendingRepository;
	
	@Autowired
	private SalesAccessChannelRepository salesAccessChannelRepository;
	
	@Autowired
	private ServiceAccessChannelRepository serviceAccessChannelRepository;
	
	@Autowired
	private OtherFeeTypeRepository otherFeeTypeRepository;
	
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private CoreProductHistoryService coreProductHistoryService;
	
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";
	
	@Override
	public List<CoreProduct> findAll() {
		return coreProductRepository.findAll();
	}

	@Override
	public Optional<CoreProduct> findById(Long coreProductId) {
		Optional<CoreProduct> isCoreProduct = coreProductRepository.findById(coreProductId);

		if (isCoreProduct.isPresent()) {
			return Optional.ofNullable(isCoreProduct.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<CoreProduct> findByStatus(String status) {
		return coreProductRepository.findByStatus(CommonStatus.valueOf(status));
	}
	
	@Override
	public Optional<CoreProduct> getCoreProductByCode(String coreProductCode) {
		Optional<CoreProduct> isPresentCoreProduct = coreProductRepository.findByCode(coreProductCode);
		if (isPresentCoreProduct.isPresent()) {
			return Optional.ofNullable(isPresentCoreProduct.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public CoreProduct addCoreProduct(String tenantId, CoreProductAddResource coreProductAddResource) {

		Optional<OtherFeeType> otherFeeType = null;
		Optional<SalesAccessChannel> salesAccessChannel = null;
		Optional<ServiceAccessChannel> serviceAccessChannel = null;
		CoreProduct coreProduct = new CoreProduct();
		Optional<CoreProduct> isPresentCoreProduct = coreProductRepository.findByCode(coreProductAddResource.getCode());       
        if (isPresentCoreProduct.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.CORE_PRODUCT);
        
		
            if(coreProductAddResource.getSalesAccessChannelId() != null) {
            	salesAccessChannel = salesAccessChannelRepository.findByIdAndNameAndStatus(validationService.stringToLong(coreProductAddResource.getSalesAccessChannelId()), 
    					coreProductAddResource.getSalesAccessChannelName(), CommonStatus.ACTIVE);
            	if(!salesAccessChannel.isPresent()){
    				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "salesAccessChannelId");	
    			}else {
    				 coreProduct.setSalesAccessChannel(salesAccessChannel.get());
    			}
            }
			
            if(coreProductAddResource.getServiceAccessChannelId() != null) {
            	serviceAccessChannel = serviceAccessChannelRepository.findByIdAndNameAndStatus(validationService.stringToLong(coreProductAddResource.getServiceAccessChannelId()), 
    					coreProductAddResource.getServiceAccessChannelName(), CommonStatus.ACTIVE);
            	if(!serviceAccessChannel.isPresent()){
    				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "serviceAccessChannelId");	
    			}else {
    				coreProduct.setServiceAccessChannel(serviceAccessChannel.get());
    			}
            }
			
			if(coreProductAddResource.getFeeTypeId() != null) {
				otherFeeType = otherFeeTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(coreProductAddResource.getFeeTypeId()), 
						coreProductAddResource.getFeeTypeName(), CommonStatus.ACTIVE);
				if(!otherFeeType.isPresent()){
					throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "feeTypeId");	
				}else {
					coreProduct.setOtherFeeType(otherFeeType.get());
				}
			}
			
			 Currency currency = validationService.validateCurrency(tenantId, coreProductAddResource.getCurrencyId(), coreProductAddResource.getCurrencyName(), EntityPoint.CORE_PRODUCT);
			 
			 coreProduct.setTenantId(tenantId);
			 coreProduct.setDescription(coreProductAddResource.getDescription());
			 coreProduct.setTcsCsUrl(coreProductAddResource.getTcsCsUrl());
			 coreProduct.setCurrencyId(validationService.stringToLong(coreProductAddResource.getCurrencyId()));
			 coreProduct.setCurrencyCode(currency.getCurrencyCode());
			 coreProduct.setCurrencyCodeNumeric(currency.getCodeNumeric());
			 coreProduct.setFullPartialRepayment(coreProductAddResource.getFullPartialRepaymentAllow() != null?YesNo.valueOf(coreProductAddResource.getFullPartialRepaymentAllow()):YesNo.NO);
			 coreProduct.setOverPayment(coreProductAddResource.getOverPaymentAllow() != null?YesNo.valueOf(coreProductAddResource.getOverPaymentAllow()):YesNo.NO);
			 coreProduct.setOverPaymentApplicable(coreProductAddResource.getOverPaymentApplicable()!= null?YesNo.valueOf(coreProductAddResource.getOverPaymentApplicable()):null);
			 coreProduct.setEarlyPaymentApplicable(coreProductAddResource.getEarlyPaymentApplicable()!= null?YesNo.valueOf(coreProductAddResource.getEarlyPaymentApplicable()):null);
			 coreProduct.setComment(coreProductAddResource.getComment());
		     coreProduct.setCode(coreProductAddResource.getCode());
		     coreProduct.setStatus(CommonStatus.valueOf(coreProductAddResource.getStatus()));
		     coreProduct.setCreatedDate(validationService.getCreateOrModifyDate());
		     coreProduct.setSyncTs(validationService.getCreateOrModifyDate());
		     coreProduct.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		     coreProduct = coreProductRepository.save(coreProduct);
		     return coreProduct;
		     
	}

	@Override
	public CoreProduct updateCoreProduct(String tenantId, Long id,
			CoreProductUpdateResource coreProductUpdateResource) {
		
		Optional<CoreProduct> isPresentCoreProduct = coreProductRepository.findById(id);

		if (!isPresentCoreProduct.get().getVersion().equals(Long.parseLong(coreProductUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.CORE_PRODUCT);

		if (!isPresentCoreProduct.get().getCode().equalsIgnoreCase(coreProductUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE, EntityPoint.CORE_PRODUCT);

		approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),
				isPresentCoreProduct.get(), coreProductUpdateResource);

		return isPresentCoreProduct.get();
	}


	private void approveOrGenerateWorkFlow(String tenantId, String user, CoreProduct coreProduct,
			CoreProductUpdateResource coreProductUpdateResource) {

		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
				savePendingCoreProduct(tenantId, coreProduct, coreProductUpdateResource, lendingWorkflow, user);
			}
		} else {
			CoreProductPending coreProductPending = savePendingCoreProduct(tenantId, coreProduct,
					coreProductUpdateResource, lendingWorkflow, user);
			updateFeeChargeCap(coreProductPending, coreProduct, CommonApproveStatus.APPROVED, user);
		}
	}

	private void updateFeeChargeCap(CoreProductPending coreProductPending, CoreProduct coreProduct,
			CommonApproveStatus approved, String user) {
		
	}

	private CoreProductPending savePendingCoreProduct(String tenantId, CoreProduct coreProduct,
			CoreProductUpdateResource coreProductUpdateResource, LendingWorkflow lendingWorkflow, String user) {
		
		 Optional<OtherFeeType> otherFeeType = null;
		 Optional<SalesAccessChannel> salesAccessChannel = null;
		 Optional<ServiceAccessChannel> serviceAccessChannel = null;
		 if(coreProductUpdateResource.getSalesAccessChannelId() != null) {
         	salesAccessChannel = salesAccessChannelRepository.findByIdAndNameAndStatus(validationService.stringToLong(coreProductUpdateResource.getSalesAccessChannelId()), 
         			coreProductUpdateResource.getSalesAccessChannelName(), CommonStatus.ACTIVE);
         	if(!salesAccessChannel.isPresent()){
 				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "salesAccessChannelId");	
 			}
         }
			
         if(coreProductUpdateResource.getServiceAccessChannelId() != null) {
         	serviceAccessChannel = serviceAccessChannelRepository.findByIdAndNameAndStatus(validationService.stringToLong(coreProductUpdateResource.getServiceAccessChannelId()), 
         			coreProductUpdateResource.getServiceAccessChannelName(), CommonStatus.ACTIVE);
         	if(!serviceAccessChannel.isPresent()){
 				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "serviceAccessChannelId");	
 			}
         }
		
		if(coreProductUpdateResource.getFeeTypeId() != null) {
			otherFeeType = otherFeeTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(coreProductUpdateResource.getFeeTypeId()), 
					coreProductUpdateResource.getFeeTypeName(), CommonStatus.ACTIVE);
			if(!otherFeeType.isPresent()){
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "feeTypeId");	
			}
		}
		
		Currency currency = validationService.validateCurrency(tenantId, coreProductUpdateResource.getCurrencyId(), coreProductUpdateResource.getCurrencyName(), EntityPoint.CORE_PRODUCT);
		 
		CoreProductPending coreProductPending = new CoreProductPending();

		coreProductPending.setTenantId(tenantId);
		if (lendingWorkflow != null)
			coreProductPending.setLendingWorkflow(lendingWorkflow);
		
		coreProductPending.setCoreProduct(coreProduct);
		coreProductPending.setDescription(coreProductUpdateResource.getDescription());
		coreProductPending.setTcsCsUrl(coreProductUpdateResource.getTcsCsUrl());
		coreProductPending.setSalesAccessChannel(salesAccessChannel != null ?salesAccessChannel.get():null);
		coreProductPending.setServiceAccessChannel(serviceAccessChannel!= null ?serviceAccessChannel.get():null);
		coreProductPending.setCurrencyId(validationService.stringToLong(coreProductUpdateResource.getCurrencyId()));
		coreProductPending.setCurrencyCode(currency.getCurrencyCode());
		coreProductPending.setCurrencyCodeNumeric(currency.getCodeNumeric());
		coreProductPending.setFullPartialRepayment(coreProductUpdateResource.getFullPartialRepaymentAllow() != null?YesNo.valueOf(coreProductUpdateResource.getFullPartialRepaymentAllow()):YesNo.NO);
		coreProductPending.setOverPayment(coreProductUpdateResource.getOverPaymentAllow() != null?YesNo.valueOf(coreProductUpdateResource.getOverPaymentAllow()):YesNo.NO);
		coreProductPending.setOverPaymentApplicable(coreProductUpdateResource.getOverPaymentApplicable()!= null?YesNo.valueOf(coreProductUpdateResource.getOverPaymentApplicable()):null);
		coreProductPending.setEarlyPaymentApplicable(coreProductUpdateResource.getEarlyPaymentApplicable()!= null?YesNo.valueOf(coreProductUpdateResource.getEarlyPaymentApplicable()):null);
		coreProductPending.setComment(coreProductUpdateResource.getComment());
		coreProductPending.setOtherFeeType(otherFeeType!= null? otherFeeType.get():null);
		coreProductPending.setCode(coreProductUpdateResource.getCode());
		
		
		coreProductPending.setApproveStatus(CommonApproveStatus.PENDING);
		coreProductPending.setStatus(CommonStatus.valueOf(coreProductUpdateResource.getStatus()));
		coreProductPending.setCreatedDate(validationService.getCreateOrModifyDate());
		coreProductPending.setCreatedUser(user);
		coreProductPending.setSyncTs(validationService.getCreateOrModifyDate());

		return coreProductPendingRepository.save(coreProductPending);
	}

	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {
		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus = null;

		Optional<CoreProductPending> isPresentCoreProductPending = coreProductPendingRepository.findById(id);
		
		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
				.findById(isPresentCoreProductPending.get().getLendingWorkflow().getId());

		Optional<CoreProduct> coreProduct = coreProductRepository
				.findById(isPresentCoreProductPending.get().getCoreProduct().getId());

		if (!isPresentCoreProductPending.get().getApproveStatus().equals(CommonApproveStatus.PENDING))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
			if (lendingWorkflow.get().getCreatedUser()
					.equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
				throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
		}

		if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
			validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user,
					tenantId);
			approveStatus = CommonApproveStatus.APPROVED;
		} else {
			validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user,
					tenantId);
			approveStatus = CommonApproveStatus.REJECTED;
		}

		lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);

		updateCoreProduct(isPresentCoreProductPending.get(), coreProduct.get(), approveStatus, user);

		return true;
	}

	private void updateCoreProduct(CoreProductPending coreProductPending, CoreProduct crePrdct,
			CommonApproveStatus approveStatus, String user) {


		CoreProduct coreProduct = crePrdct;
		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {

			coreProductHistoryService.saveHistory(crePrdct.getTenantId(), crePrdct, user);
			
			coreProduct.setSalesAccessChannel(coreProductPending.getSalesAccessChannel());
			coreProduct.setServiceAccessChannel(coreProductPending.getServiceAccessChannel());
			coreProduct.setTcsCsUrl(coreProductPending.getTcsCsUrl());
			coreProduct.setCurrencyId(coreProductPending.getCurrencyId());
			coreProduct.setCurrencyCode(coreProductPending.getCurrencyCode());
			coreProduct.setCurrencyCodeNumeric(coreProductPending.getCurrencyCodeNumeric());
			coreProduct.setFullPartialRepayment(coreProductPending.getFullPartialRepayment());
			coreProduct.setOverPayment(coreProductPending.getOverPayment());
			coreProduct.setOverPaymentApplicable(coreProductPending.getOverPaymentApplicable());
			coreProduct.setEarlyPaymentApplicable(coreProductPending.getEarlyPaymentApplicable());
			coreProduct.setComment(coreProductPending.getComment());
			coreProduct.setDescription(coreProductPending.getDescription());
			coreProductPending.setOtherFeeType(coreProductPending.getOtherFeeType());
			
			
			coreProduct.setDescription(coreProductPending.getDescription());
			coreProduct.setStatus(coreProductPending.getStatus());
			coreProduct.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			coreProduct.setModifiedDate(validationService.getCreateOrModifyDate());
			coreProduct.setSyncTs(validationService.getCreateOrModifyDate());
			coreProductRepository.saveAndFlush(coreProduct);
			
			coreProductPending.setApprovedUser(user);
			coreProductPending.setApproveStatus(CommonApproveStatus.APPROVED);
			coreProductPending.setApprovedDate(validationService.getCreateOrModifyDate());
			coreProductPending.setSyncTs(validationService.getCreateOrModifyDate());
			coreProductPendingRepository.save(coreProductPending);
			
		}else {
			coreProductPending.setApprovedUser(user);
			coreProductPending.setApproveStatus(CommonApproveStatus.REJECTED);
			coreProductPending.setApprovedDate(validationService.getCreateOrModifyDate());
			coreProductPending.setSyncTs(validationService.getCreateOrModifyDate());
			coreProductPendingRepository.save(coreProductPending);
			
		}
		
	}

	@Override
	public Optional<CoreProductPending> getByPendingId(Long pendingId) {
		Optional<CoreProductPending> isPresentInterestCoreProductPending = coreProductPendingRepository
				.findById(pendingId);

		if (isPresentInterestCoreProductPending.isPresent()) {
			return Optional.ofNullable(isPresentInterestCoreProductPending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<CoreProduct> getByDescription(String desc) {
		return coreProductRepository.findByDescriptionContaining(desc);
	}

	@Override
	public Page<CoreProductPending> searchCoreProduct(String searchq, String status, String approveStatus,
			Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return coreProductPendingRepository.searchCoreProductPending(searchq, status, approveStatus, pageable);
	}

}
