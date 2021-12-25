package com.fusionx.lending.origination.service.impl;
/**
 * New Facility Service impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-06-2021                           MenukaJ        Created
 *    
 ********************************************************************************************************
 */
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.FacilityContractDetails;
import com.fusionx.lending.origination.domain.FacilityContractDetailsHistory;
import com.fusionx.lending.origination.domain.FacilityOtherProducts;
import com.fusionx.lending.origination.domain.FacilityOtherProductsHistory;
import com.fusionx.lending.origination.domain.FacilityParameter;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.FacilityTypes;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.FacilityContractDetailsHistoryRepository;
import com.fusionx.lending.origination.repository.FacilityContractDetailsRepository;
import com.fusionx.lending.origination.repository.FacilityOtherProductsHistoryRepository;
import com.fusionx.lending.origination.repository.FacilityOtherProductsRepository;
import com.fusionx.lending.origination.repository.FacilityParameterRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.resource.AddCustomerOnBoardProductRequestResource;
import com.fusionx.lending.origination.resource.AddCustomerOnBoardRequestResource;
import com.fusionx.lending.origination.resource.CustomerOnBoardResource;
import com.fusionx.lending.origination.resource.FacilityContractDetailsResource;
import com.fusionx.lending.origination.resource.FacilityOtherProductsResource;
import com.fusionx.lending.origination.resource.NewFacilityAddResource;
import com.fusionx.lending.origination.resource.NewFacilityUpdateResource;
import com.fusionx.lending.origination.resource.ProductCategoryCodeResponceResource;
import com.fusionx.lending.origination.resource.ResponseCustomerOnboardProductResource;
import com.fusionx.lending.origination.resource.ResponseCustomerOnboardProductUpdatedResource;
import com.fusionx.lending.origination.resource.ResponseCustomerOnboardingResource;
import com.fusionx.lending.origination.service.NewFacilityService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;
@Component
@Transactional(rollbackFor = Exception.class)
public class NewFacilityServiceImpl extends MessagePropertyBase implements NewFacilityService {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	@Autowired
	private FacilityContractDetailsRepository facilityContractRepository;
	
	@Autowired
	private FacilityOtherProductsRepository facilityOtherProductsRepository;
	
	@Autowired
	private FacilityContractDetailsHistoryRepository facilityContractDetailsHistoryRepository;
	
	@Autowired
	private FacilityOtherProductsHistoryRepository facilityOtherProductsHistoryRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RemoteService remoteService;
	
	@Autowired
	private FacilityParameterRepository facilityParameterRepository;
	
	@Value("${product-category}")
    private String urlProductCategory;

	@Override
	public Long saveFacility(String tenantId, Long leadId, NewFacilityAddResource newFacilityAddResource) {
		
		Boolean isTopUp = false;
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		if(newFacilityAddResource.getFacilityType().equalsIgnoreCase(FacilityTypes.TOPUP.toString())) {
			if(newFacilityAddResource.getAvailableBalance() == null || newFacilityAddResource.getAvailableBalance().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "availableBalance");
			if(newFacilityAddResource.getTotalDueAmount()== null || newFacilityAddResource.getTotalDueAmount().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "totalDueAmount");
			if(newFacilityAddResource.getTotalSettlementAmount()== null || newFacilityAddResource.getTotalSettlementAmount().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "totalSettlementAmount");
			
			if(newFacilityAddResource.getContractDetails() == null || newFacilityAddResource.getContractDetails().isEmpty()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_NOT_NULL), ServiceEntity.CONTRACTS, ServicePoint.NEW_FACILITY_CONT, 0);
			}
			isTopUp =true;
		}
		
		Optional<LeadInfo> isPresentLeadInfo = leadInfoRepository.findById(leadId);
		
		if(!isPresentLeadInfo.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "leadId");
		
		
		validateLoanAmount(leadId, newFacilityAddResource.getNewLoanAmount());
		
		LeadInfo leadInfo = isPresentLeadInfo.get();
		leadInfo.setTenantId(tenantId);
		leadInfo.setFacilityType(FacilityTypes.valueOf(newFacilityAddResource.getFacilityType()));
		leadInfo.setNewLoanAmount(new BigDecimal(newFacilityAddResource.getNewLoanAmount()));
		if(newFacilityAddResource.getTotalDueAmount() != null && !newFacilityAddResource.getTotalDueAmount().isEmpty())
			leadInfo.setTotalDueAmount(new BigDecimal(newFacilityAddResource.getTotalDueAmount()));
		if(newFacilityAddResource.getAvailableBalance() != null && !newFacilityAddResource.getAvailableBalance().isEmpty())
			leadInfo.setAvailableBalance(new BigDecimal(newFacilityAddResource.getAvailableBalance()));
		if(newFacilityAddResource.getTotalSettlementAmount() != null && !newFacilityAddResource.getTotalSettlementAmount().isEmpty())
			leadInfo.setTotalSettlementAmount(new BigDecimal(newFacilityAddResource.getTotalSettlementAmount()));
		leadInfo.setStatus(CommonStatus.valueOf(newFacilityAddResource.getStatus()).toString());
		leadInfo.setCreatedDate(validateService.getCreateOrModifyDate());
		leadInfo.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		leadInfo.setSyncTs(validateService.getCreateOrModifyDate());
		
		leadInfo = leadInfoRepository.save(leadInfo);
		
		saveFacilityContractDetails(tenantId, newFacilityAddResource.getContractDetails(), leadInfo, false);
		saveOtherProducts(tenantId, newFacilityAddResource.getOtherProducts(), leadInfo);
		
		//Customer on-board and product integration
		saveCustomerOnboarding(tenantId,leadId,LogginAuthentcation.getInstance().getUserName(),newFacilityAddResource);

		return leadInfo.getId();
	}


	private void saveFacilityContractDetails(String tenantId, List<FacilityContractDetailsResource> contractDetails, LeadInfo leadInfo, Boolean isTopUp) {
		if(contractDetails!= null && !contractDetails.isEmpty()) {
			Integer index=0;
			for(FacilityContractDetailsResource contractDetailsResource : contractDetails) {
				
				if(isTopUp) {
					if(contractDetailsResource.getContractNo() == null || contractDetailsResource.getContractNo().isEmpty())
						throw new DetailListValidateException(environment.getProperty(COMMON_NOT_NULL), ServiceEntity.CONTACT_NO, ServicePoint.NEW_FACILITY_CONT, index);
					if(contractDetailsResource.getContractStatus() == null || contractDetailsResource.getContractStatus().isEmpty())
						throw new DetailListValidateException(environment.getProperty(COMMON_NOT_NULL), ServiceEntity.CONTACT_STATUS, ServicePoint.NEW_FACILITY_CONT, index);
					if(contractDetailsResource.getContractStatusDes() == null || contractDetailsResource.getContractStatusDes().isEmpty())
						throw new DetailListValidateException(environment.getProperty(COMMON_NOT_NULL), ServiceEntity.CONTACT_STATUS_DEC, ServicePoint.NEW_FACILITY_CONT, index);
					if(contractDetailsResource.getLeseCode() == null || contractDetailsResource.getLeseCode().isEmpty())
						throw new DetailListValidateException(environment.getProperty(COMMON_NOT_NULL), ServiceEntity.LEASE_CODE, ServicePoint.NEW_FACILITY_CONT, index);
					if(contractDetailsResource.getStatus() == null || contractDetailsResource.getStatus().isEmpty())
						throw new DetailListValidateException(environment.getProperty(COMMON_NOT_NULL), ServiceEntity.STATUS, ServicePoint.NEW_FACILITY_CONT, index);
				}
				
				FacilityContractDetails facilityContractDetails = new FacilityContractDetails();
				
				if(contractDetailsResource.getId() != null && !contractDetailsResource.getId().isEmpty()) {
					Optional< FacilityContractDetails>  facilityContractDetail =  facilityContractRepository.findById(validateService.stringToLong(contractDetailsResource.getId()));
					if(facilityContractDetail.isPresent()) {
						facilityContractDetails = facilityContractDetail.get();
						saveFacilityContractDetailsHistory(facilityContractDetails);
						facilityContractDetails.setModifiedDate(validateService.getCreateOrModifyDate());
						facilityContractDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					} else {
						throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CONTRACT_DETAILS_ID, ServicePoint.FACILITY, index);
					}
				} else {
					facilityContractDetails.setTenantId(tenantId);
					facilityContractDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					facilityContractDetails.setCreatedDate(validateService.getCreateOrModifyDate());
					facilityContractDetails.setLeadInfo(leadInfo);
				}
				

				facilityContractDetails.setContractNo(contractDetailsResource.getContractNo());
				facilityContractDetails.setContractStatus(contractDetailsResource.getContractStatus());
				facilityContractDetails.setContractStatusDes(contractDetailsResource.getContractStatusDes());
				facilityContractDetails.setLeseCode(contractDetailsResource.getLeseCode());
				facilityContractDetails.setStatus(CommonStatus.valueOf(contractDetailsResource.getStatus()));
				facilityContractDetails.setSyncTs(validateService.getCreateOrModifyDate());
				
				facilityContractRepository.save(facilityContractDetails);
				index++;
			}
		}
	}
	
	@Override
	public void saveOtherProducts(String tenantId, List<FacilityOtherProductsResource> otherProducts, LeadInfo leadInfo) {
		
		if(otherProducts != null && !otherProducts.isEmpty()) {
			Integer index=0;
			for(FacilityOtherProductsResource otherProductsResource : otherProducts) {
				
				productCategoryValidate(tenantId, otherProductsResource.getProductId(), otherProductsResource.getProductName(), index);
				
				FacilityOtherProducts facilityOtherProducts = new FacilityOtherProducts();
				if(otherProductsResource.getId() != null && !otherProductsResource.getId().isEmpty()) {
					Optional<FacilityOtherProducts> facilityOtherProduct = facilityOtherProductsRepository.findById(validateService.stringToLong(otherProductsResource.getId()));
					if(facilityOtherProduct.isPresent()) {
						facilityOtherProducts = facilityOtherProduct.get();
						saveOtherProductHistory(facilityOtherProducts);
						facilityOtherProducts.setModifiedDate(validateService.getCreateOrModifyDate());
						facilityOtherProducts.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					} else {
						throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.OTHER_PRODUCT_DETAILS_ID, ServicePoint.FACILITY, index);
					}
				} else {
					facilityOtherProducts.setTenantId(tenantId);
					facilityOtherProducts.setCreatedDate(validateService.getCreateOrModifyDate());
					facilityOtherProducts.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					facilityOtherProducts.setLeadInfo(leadInfo);
				}
				if(otherProductsResource.getProductId() != null && !otherProductsResource.getProductId().isEmpty())
					facilityOtherProducts.setProductCategoryCodeId(validateService.stringToLong(otherProductsResource.getProductId()));
				facilityOtherProducts.setStatus(CommonStatus.valueOf(otherProductsResource.getStatus()));
				facilityOtherProducts.setSyncTs(validateService.getCreateOrModifyDate());
				facilityOtherProductsRepository.save(facilityOtherProducts);
				index++;
			}
		}
	}
	
	private void saveOtherProductHistory(FacilityOtherProducts facilityOtherProducts) {
		
		FacilityOtherProductsHistory facilityOtherProductsHistory =  new FacilityOtherProductsHistory();
		facilityOtherProductsHistory.setTenantId(facilityOtherProducts.getTenantId());
		facilityOtherProductsHistory.setLeadInfoId(facilityOtherProducts.getLeadInfo().getId());
		facilityOtherProductsHistory.setFacilityOtherProductsId(facilityOtherProducts.getId());
		facilityOtherProductsHistory.setProductCategoryCodeId(facilityOtherProducts.getProductCategoryCodeId());
		facilityOtherProductsHistory.setStatus(facilityOtherProducts.getStatus());
		facilityOtherProductsHistory.setCreatedUser(facilityOtherProducts.getCreatedUser());
		facilityOtherProductsHistory.setCreatedDate(facilityOtherProducts.getCreatedDate());
		facilityOtherProductsHistory.setModifiedDate(facilityOtherProducts.getModifiedDate());
		facilityOtherProductsHistory.setModifiedUser(facilityOtherProducts.getModifiedUser());
		facilityOtherProductsHistory.setVersion(facilityOtherProducts.getVersion());
		facilityOtherProductsHistory.setHcreatedDate(validateService.getCreateOrModifyDate());
		facilityOtherProductsHistory.setHcreatedUser(facilityOtherProducts.getCreatedUser());
		facilityOtherProductsHistory.setSyncTs(validateService.getCreateOrModifyDate());
		facilityOtherProductsHistory.setOnboardProductId(facilityOtherProducts.getOnboardProductId());
		facilityOtherProductsHistoryRepository.save(facilityOtherProductsHistory);
	}
	
	private void saveFacilityContractDetailsHistory(FacilityContractDetails facilityContractDetails) {
		FacilityContractDetailsHistory facilityContractDetailsHistory = new FacilityContractDetailsHistory();
		
		facilityContractDetailsHistory.setTenantId(facilityContractDetails.getTenantId());
		facilityContractDetailsHistory.setFacilityContractsDetId(facilityContractDetails.getId());
		facilityContractDetailsHistory.setLeadInfoId(facilityContractDetails.getLeadInfo().getId());
		facilityContractDetailsHistory.setCreatedUser(facilityContractDetails.getCreatedUser());
		facilityContractDetailsHistory.setCreatedDate(facilityContractDetails.getCreatedDate());
		facilityContractDetailsHistory.setModifiedDate(facilityContractDetails.getModifiedDate());
		facilityContractDetailsHistory.setModifiedUser(facilityContractDetails.getModifiedUser());
		facilityContractDetailsHistory.setContractNo(facilityContractDetails.getContractNo());
		facilityContractDetailsHistory.setContractStatus(facilityContractDetails.getContractStatus());
		facilityContractDetailsHistory.setContractStatusDes(facilityContractDetails.getContractStatusDes());
		facilityContractDetailsHistory.setLeseCode(facilityContractDetails.getLeseCode());
		facilityContractDetailsHistory.setStatus(facilityContractDetails.getStatus());
		facilityContractDetailsHistory.setSyncTs(validateService.getCreateOrModifyDate());
		facilityContractDetailsHistory.setHcreatedDate(validateService.getCreateOrModifyDate());
		facilityContractDetailsHistory.setHcreatedUser(LogginAuthentcation.getInstance().getUserName());
		
		facilityContractDetailsHistoryRepository.save(facilityContractDetailsHistory);
	}
	
	@Override
	public Long updateFacility(String tenantId, NewFacilityUpdateResource newFacilityUpdateResource, Long leadId) {
		
		Boolean isTopUp = false;
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		Optional<LeadInfo> isPresentLeadInfo = leadInfoRepository.findById(leadId);
		
		if(!isPresentLeadInfo.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		
		validateLoanAmount(leadId, newFacilityUpdateResource.getNewLoanAmount());
		
		if(!isPresentLeadInfo.get().getVersion().equals(Long.parseLong(newFacilityUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), VERSION);
		
		if(newFacilityUpdateResource.getFacilityType().equalsIgnoreCase(FacilityTypes.TOPUP.toString())) {
			if(newFacilityUpdateResource.getAvailableBalance() == null || newFacilityUpdateResource.getAvailableBalance().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "availableBalance");
			if(newFacilityUpdateResource.getTotalDueAmount()== null || newFacilityUpdateResource.getTotalDueAmount().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "totalDueAmount");
			if(newFacilityUpdateResource.getTotalSettlementAmount()== null || newFacilityUpdateResource.getTotalSettlementAmount().isEmpty())
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "totalSettlementAmount");
			if(newFacilityUpdateResource.getContractDetails() == null || newFacilityUpdateResource.getContractDetails().isEmpty()) {
				throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.CONTRACTS, ServicePoint.NEW_FACILITY_CONT, 0);
			}
			isTopUp = true;
		}
		
		LeadInfo leadInfo =isPresentLeadInfo.get();
		leadInfo.setFacilityType(FacilityTypes.valueOf(newFacilityUpdateResource.getFacilityType()));
		leadInfo.setNewLoanAmount(new BigDecimal(newFacilityUpdateResource.getNewLoanAmount()));
		if(newFacilityUpdateResource.getTotalDueAmount() != null && !newFacilityUpdateResource.getTotalDueAmount().isEmpty())
			leadInfo.setTotalDueAmount(new BigDecimal(newFacilityUpdateResource.getTotalDueAmount()));
		if(newFacilityUpdateResource.getAvailableBalance() != null && !newFacilityUpdateResource.getAvailableBalance().isEmpty())
			leadInfo.setAvailableBalance(new BigDecimal(newFacilityUpdateResource.getAvailableBalance()));
		if(newFacilityUpdateResource.getTotalSettlementAmount() != null && !newFacilityUpdateResource.getTotalSettlementAmount().isEmpty())
			leadInfo.setTotalSettlementAmount(new BigDecimal(newFacilityUpdateResource.getTotalSettlementAmount()));
		//leadInfo.setStatus(CommonStatus.valueOf(newFacilityUpdateResource.getStatus()));
		leadInfo.setModifiedDate(validateService.getCreateOrModifyDate());
		leadInfo.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		leadInfo.setSyncTs(validateService.getCreateOrModifyDate());
		
		leadInfo = leadInfoRepository.save(leadInfo);
		
		saveFacilityContractDetails(tenantId, newFacilityUpdateResource.getContractDetails(), leadInfo, isTopUp);
		saveOtherProducts(tenantId, newFacilityUpdateResource.getOtherProducts(), leadInfo);

		return leadInfo.getId();
	}
	
	@Override
	public List<LeadInfo> getAll() {
		List<LeadInfo> leadInfo = leadInfoRepository.findAll();
		List<LeadInfo> leadInfoList = new ArrayList<>();
		List<FacilityContractDetails> facilityContractDetails = new ArrayList<>();
		List<FacilityOtherProducts> facilityOtherProducts = new ArrayList<>();

		for (LeadInfo lead : leadInfo) {
			facilityContractDetails = facilityContractRepository.findByleadInfoId(lead.getId());
			lead.setFacilityContractDetails(facilityContractDetails);
			
			facilityOtherProducts = facilityOtherProductsRepository.findByleadInfoId(lead.getId());
			lead.setFacilityOtherProducts(facilityOtherProducts);
			leadInfoList.add(lead);
		}
		return leadInfoList;
	}

	@Override
	public Optional<LeadInfo> getById(Long id) {
		Optional<LeadInfo> isPresentleadInfo = leadInfoRepository.findById(id);
		if (isPresentleadInfo.isPresent()) {
			LeadInfo leadInfo = isPresentleadInfo.get();
			List<FacilityContractDetails> facilityContractDetails = facilityContractRepository.findByleadInfoId(isPresentleadInfo.get().getId());
			leadInfo.setFacilityContractDetails(facilityContractDetails);
			List<FacilityOtherProducts> facilityOtherProducts = facilityOtherProductsRepository.findByleadInfoId(isPresentleadInfo.get().getId());
			leadInfo.setFacilityOtherProducts(facilityOtherProducts);
			return isPresentleadInfo;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<LeadInfo> getByStatus(String status) {
		List<LeadInfo> leadInfo = leadInfoRepository.findByStatus(CommonStatus.valueOf(status));
		List<LeadInfo> leadInfoList = new ArrayList<>();
		List<FacilityContractDetails> facilityContractDetails = new ArrayList<>();
		List<FacilityOtherProducts> facilityOtherProducts = new ArrayList<>();

		for (LeadInfo lead : leadInfo) {
			facilityContractDetails = facilityContractRepository.findByleadInfoId(lead.getId());
			lead.setFacilityContractDetails(facilityContractDetails);
			
			facilityOtherProducts = facilityOtherProductsRepository.findByleadInfoId(lead.getId());
			lead.setFacilityOtherProducts(facilityOtherProducts);
			leadInfoList.add(lead);
		}
		return leadInfoList;
	}
	
	
	private void saveCustomerOnboarding(String tenantId, Long leadId, String userName, NewFacilityAddResource newFacilityAddResource) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.MONTH, 1);
		java.util.Date expiryDate = calendar.getTime();
		AddCustomerOnBoardRequestResource addCustomerOnBoardRequestResource = new AddCustomerOnBoardRequestResource();
		String pCusId = null;
		String onBoardReqId = null;
		
		List<Customer> isPresentCustomer = customerRepository.findByLeadIdOrderByLeadIdDesc(leadId);
		if(!isPresentCustomer.isEmpty()) {
			for(Customer recCustomer:isPresentCustomer) {
				pCusId = recCustomer.getPendingCustomerId().toString();
				break;
			}
		}else
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "leadId");
		
		CustomerOnBoardResource getCustomerOnBoardResource = validateService.getOnboardPendingCustomerDetail(tenantId,Long.parseLong(pCusId));
		if(getCustomerOnBoardResource==null) {		
			addCustomerOnBoardRequestResource.setCustomerOnBoardRequestExpireDate(sdf.format(expiryDate).toString()); 
			addCustomerOnBoardRequestResource.setCustomerOnBoardRequestStatus("INPROGRESS");
			addCustomerOnBoardRequestResource.setCustomerOnBoardPendingCustomerId(pCusId);
			ResponseCustomerOnboardingResource responseCustomerOnboardingResource = validateService.saveCustomerOnboarding(tenantId,addCustomerOnBoardRequestResource,userName);
			if(responseCustomerOnboardingResource.getCustomerOnBoardRequestExpireDate()!=null)
				throw new ValidateRecordException(responseCustomerOnboardingResource.getCustomerOnBoardRequestExpireDate(), "leadId");
			if(responseCustomerOnboardingResource.getCustomerOnBoardPendingCustomerId()!=null)
				throw new ValidateRecordException(responseCustomerOnboardingResource.getCustomerOnBoardPendingCustomerId(), "leadId");
			onBoardReqId = responseCustomerOnboardingResource.getValue().getOnBoardReqId();
		}
		onBoardReqId = getCustomerOnBoardResource.getId().toString();
		List<ResponseCustomerOnboardProductUpdatedResource> listResponseCustomerOnboardProductUpdatedResource = new ArrayList<>();
		for (FacilityOtherProductsResource recFacilityOtherProductsResource:newFacilityAddResource.getOtherProducts()) {
			ResponseCustomerOnboardProductUpdatedResource responseCustomerOnboardProductUpdatedResource = new ResponseCustomerOnboardProductUpdatedResource();
			AddCustomerOnBoardProductRequestResource addCustomerOnBoardProductRequestResource = new AddCustomerOnBoardProductRequestResource();
			addCustomerOnBoardProductRequestResource.setCustomerOnBoardRequestId(onBoardReqId);
			addCustomerOnBoardProductRequestResource.setProductPendingCustomerId(pCusId);
			addCustomerOnBoardProductRequestResource.setProductCategoryId(recFacilityOtherProductsResource.getProductId());
			addCustomerOnBoardProductRequestResource.setProductStatus("ACTIVE");
			ResponseCustomerOnboardProductResource responseCustomerOnboardProductResource = validateService.saveCustomerOnboardProduct(tenantId,addCustomerOnBoardProductRequestResource,userName);
			responseCustomerOnboardProductUpdatedResource.setLeadId(leadId);
			responseCustomerOnboardProductUpdatedResource.setProductId(Long.parseLong(recFacilityOtherProductsResource.getProductId()));
			responseCustomerOnboardProductUpdatedResource.setOnBoardProductId(Long.parseLong(responseCustomerOnboardProductResource.getValue().getOnBoardProductId()));
			listResponseCustomerOnboardProductUpdatedResource.add(responseCustomerOnboardProductUpdatedResource);
		}
		Optional<LeadInfo> isPresentLeadInfo = leadInfoRepository.findById(leadId);
		if(isPresentLeadInfo.isPresent()) {
			LeadInfo updateLeadInfo = isPresentLeadInfo.get();
			updateLeadInfo.setOnboardRequestId(Long.parseLong(onBoardReqId));
			leadInfoRepository.saveAndFlush(updateLeadInfo);
		}
		
		List<FacilityOtherProducts> isPresentFacilityOtherProducts = facilityOtherProductsRepository.findByleadInfoId(leadId);
		if(!isPresentFacilityOtherProducts.isEmpty()) {
			List<FacilityOtherProducts> updateListFacilityOtherProducts = new ArrayList<>();
			for(FacilityOtherProducts recFacilityOtherProducts:isPresentFacilityOtherProducts) {
				FacilityOtherProducts updateFacilityOtherProducts = recFacilityOtherProducts;
				for(ResponseCustomerOnboardProductUpdatedResource recResponseCustomerOnboardProductUpdatedResource:listResponseCustomerOnboardProductUpdatedResource) {
					if(recFacilityOtherProducts.getLeadInfo().equals(recResponseCustomerOnboardProductUpdatedResource.getLeadId()) && recFacilityOtherProducts.getProductCategoryCodeId().equals(recResponseCustomerOnboardProductUpdatedResource.getProductId())) {
						updateFacilityOtherProducts.setOnboardProductId(recResponseCustomerOnboardProductUpdatedResource.getOnBoardProductId());
						updateListFacilityOtherProducts.add(updateFacilityOtherProducts);
					}
				}
			}
			facilityOtherProductsRepository.saveAll(updateListFacilityOtherProducts);
		}
	}
	
	private void productCategoryValidate(String tenantId, String id, String name, Integer index) {
		
		ProductCategoryCodeResponceResource responce = (ProductCategoryCodeResponceResource)remoteService.checkIsExist(tenantId, id, urlProductCategory, ProductCategoryCodeResponceResource.class);
		
		if(responce != null) {
			if(!responce.getProductCategoryName().equalsIgnoreCase(name) || !responce.getProductCategoryStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
				throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.PRODUCT_ID, ServicePoint.NEW_FACILITY, index);
			}
		} else {
			throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.PRODUCT_ID, ServicePoint.NEW_FACILITY, index);
		}
	}
	
	private void validateLoanAmount(Long leadid, String amount) {
		
		BigDecimal newAmount = new BigDecimal(amount);
		
		Optional<FacilityParameter> facilityParameter	= facilityParameterRepository.findByLeadInfoId(leadid);
		
		if(facilityParameter.isPresent()) {
			if(!(facilityParameter.get().getLoanAmount().compareTo(newAmount) == 0))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "newLoanAmount");
		} else 
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "newLoanAmount");
		
	}
}
