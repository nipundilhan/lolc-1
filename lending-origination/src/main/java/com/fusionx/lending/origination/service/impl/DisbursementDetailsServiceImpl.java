package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.DisbursementDetails;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.CommonListReferenceCodes;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.PayeeType;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.DisbursementDetailsRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.resource.BankBranchResponseResource;
import com.fusionx.lending.origination.resource.BankResponse;
import com.fusionx.lending.origination.resource.DisbursementAddRequestResource;
import com.fusionx.lending.origination.resource.DisbursementConditionsResponse;
import com.fusionx.lending.origination.resource.DisbursementDetailsRequestResource;
import com.fusionx.lending.origination.resource.PayModeResponse;
import com.fusionx.lending.origination.service.DisbursementDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Disbursement Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class DisbursementDetailsServiceImpl extends MessagePropertyBase implements  DisbursementDetailsService{
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private DisbursementDetailsRepository disbursementDetailsRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private CommonListRepository commonListRepository;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	@Override
	public List<DisbursementDetails> findByStatus(String tenantId, String status) {
		List<DisbursementDetails> disbursementDetails = disbursementDetailsRepository.findByStatus(status);
		for (DisbursementDetails disbursementDetailsNames : disbursementDetails) {
			
			setBankBranchName( tenantId,  disbursementDetailsNames);
			//CommonList commonList = getComnListName( disbursementDetailsNames.getPayMethod().getId().toString());
//			disbursementDetailsNames.setPayMethodName(commonList.getName());
//			disbursementDetailsNames.setPayMethodCode(commonList.getCode());
		}
		
		return disbursementDetails;
	}
	
	@Override
	public List<DisbursementDetails> findByCustomerId(String tenantId, Long id) {
		List<DisbursementDetails> disbursementDetails = disbursementDetailsRepository.findByCustomerId(id);
		for (DisbursementDetails disbursementDetailsNames : disbursementDetails) {
			
			setBankBranchName( tenantId,  disbursementDetailsNames);
			//CommonList commonList = getComnListName( disbursementDetailsNames.getPayMethod().getId().toString());
//			disbursementDetailsNames.setPayMethodName(commonList.getName());
//			disbursementDetailsNames.setPayMethodCode(commonList.getCode());
		}
		
		return disbursementDetails;
	}
	
	@Override
	public List<DisbursementDetails> findByLeadInfoId(String tenantId, Long id) {
		List<DisbursementDetails> disbursementDetails = disbursementDetailsRepository.findByLeadId(id);
		for (DisbursementDetails disbursementDetailsNames : disbursementDetails) {
			
			setBankBranchName( tenantId,  disbursementDetailsNames);
//			CommonList commonList = getComnListName( disbursementDetailsNames.getPayMethod().getId().toString());
//			disbursementDetailsNames.setPayMethodName(commonList.getName());
//			disbursementDetailsNames.setPayMethodCode(commonList.getCode());
		}
		
		return disbursementDetails;
	}
		
	/**
	 * Save and Update Disbursement Details
	 *
	 * @author Sanatha
	 * @param tenantId
	 * @param DisbursementAddRequestResource
	 * @return SuccessAndErrorDetailsResource
	 */
	@Override
	public void saveAndValidateDisbursementDetails(String tenantId, String createdUser, DisbursementAddRequestResource disbursementAddRequestResource) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveAndValidateDisbursementDetails>>>>>>******");
		Optional<Customer> relevantCustomerInfo = customerRepository.findById(stringToLong(disbursementAddRequestResource.getCustomerId()));
		if(!relevantCustomerInfo.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "customerId");
		}
		
		
		disbursementDetails(tenantId, createdUser, disbursementAddRequestResource.getDisbursementDetails(),relevantCustomerInfo.get(),null, disbursementAddRequestResource.getLoanAmount(), disbursementAddRequestResource.getDeductions(), disbursementAddRequestResource.getBalanceDisAmount());
		
		
	}
	

	private void disbursementDetails(String tenantId, String createdUser, List<DisbursementDetailsRequestResource> disbursementDetailsRequestResources,Customer customer, Long id,String loanAmount, String deductions, String balanceDisAmount) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN disbursementDetails>>>>>>******");
		DisbursementDetails disbursementDetails;
		
		
		Integer index=0;
		for(DisbursementDetailsRequestResource disbursementDetailsRequestResource : disbursementDetailsRequestResources) {
			DisbursementConditionsResponse disbursementConditionsResponse = null;
			//checking insert or update
			if(disbursementDetailsRequestResource.getId() != null && !disbursementDetailsRequestResource.getId().isEmpty()) {
				
				// checking Version
	 			if(disbursementDetailsRequestResource.getVersion() != null && !disbursementDetailsRequestResource.getVersion().isEmpty()) {	
					Optional<DisbursementDetails> relevantDisbursementDetails = disbursementDetailsRepository.findById(stringToLong(disbursementDetailsRequestResource.getId()));
					
					if(!relevantDisbursementDetails.isPresent()) {
		 				
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.DISBURSEMENT_DETAILS_ID, ServicePoint.DISBURSEMENT_DETAILS,index);
		 			}
					
					//checking Disbursement details related to Customer 
		 			if(!relevantDisbursementDetails.get().getCustomer().getId().equals(customer.getId())) {
		 				
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_DISBURSEMENT_DETAILS), ServiceEntity.DISBURSEMENT_DETAILS_ID, ServicePoint.DISBURSEMENT_DETAILS,index);
		 			}
		 			
		 			//checking Version mismatch
		 			if (!relevantDisbursementDetails.get().getVersion().equals(stringToLong(disbursementDetailsRequestResource.getVersion()))) {
		 				
		 				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.DISBURSEMENT_DETAILS,index);
		 			}
					
					disbursementDetails=relevantDisbursementDetails.get();
					disbursementDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					disbursementDetails.setModifiedDate(getCreateOrModifyDate());
	 			}else {
	 				throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.DISBURSEMENT_DETAILS,index);
	 			}
	 			
			}else {
				disbursementDetails= new DisbursementDetails();
				disbursementDetails.setTenantId(tenantId);
				disbursementDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				disbursementDetails.setCreatedDate(getCreateOrModifyDate());
				disbursementDetails.setLoanAmount(stringToBigDecimal(loanAmount));
				disbursementDetails.setDeductions(stringToBigDecimal(deductions));
				disbursementDetails.setBalanceDisbursementAmount(stringToBigDecimal(balanceDisAmount));
				disbursementDetails.setScheduleNo(index);
			}
			
			LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Lead Info ID  >>>>>>******");
			Optional<LeadInfo> relevantLeanInfo = leadInfoRepository.findById(stringToLong(disbursementDetailsRequestResource.getLeadInfoId()));
			if(!relevantLeanInfo.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.LEAD_INFO_ID, ServicePoint.DISBURSEMENT_DETAILS,index);
			}
			
			LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Lead Info ID and Customer >>>>>>******");
			if(!customer.getLead().getId().equals(stringToLong(disbursementDetailsRequestResource.getLeadInfoId()))){
				throw new DetailListValidateException(environment.getProperty(CUSTOMER_ID_LEAD_ID_INVALID), ServiceEntity.CUSTOMER_ID, ServicePoint.DISBURSEMENT_DETAILS,index);
			}
			
			LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Bank >>>>>>******");
			validateService.validateBank(tenantId, Long.parseLong(disbursementDetailsRequestResource.getBankId()), disbursementDetailsRequestResource.getBankCode(), ServiceEntity.BANK, ServicePoint.DISBURSEMENT_DETAILS,index);
			
			LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Bank Branch >>>>>>******");
			validateService.validateBankBranch(tenantId, Long.parseLong(disbursementDetailsRequestResource.getBankId()), Long.parseLong(disbursementDetailsRequestResource.getBankBranchId()), disbursementDetailsRequestResource.getBankBranchCode(), ServiceEntity.BANK_BRANCH, ServicePoint.DISBURSEMENT_DETAILS,index);
			
			LoggerRequest.getInstance().logInfo1("******<<<<<<  Validate Pay Method >>>>>>******");
			//CommonList payMethod = comnListValidation(disbursementDetailsRequestResource.getPayMethodId(), disbursementDetailsRequestResource.getPayMethodName(), CommonListReferenceCodes.PAY_METHODS, ServiceEntity.PAY_METHODS, ServicePoint.DISBURSEMENT_DETAILS,index);
			PayModeResponse payModeResponse = validateService.validatePayMode(tenantId, disbursementDetailsRequestResource.getPayMethodId(), disbursementDetailsRequestResource.getPayMethodName());
			
			if(disbursementDetailsRequestResource.getDisbursementConditionId()!= null && !disbursementDetailsRequestResource.getDisbursementConditionId().isEmpty()) {
				if(disbursementDetailsRequestResource.getDisbursementConditionName() == null || disbursementDetailsRequestResource.getDisbursementConditionName().isEmpty()) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "customerId");
				}
				disbursementConditionsResponse = validateService.validateDisbursementConditions(tenantId,disbursementDetailsRequestResource.getDisbursementConditionId(), disbursementDetailsRequestResource.getDisbursementConditionName());
				disbursementDetails.setDisbursementConditionId(validateService.stringToLong(disbursementDetailsRequestResource.getDisbursementConditionId()));
				disbursementDetails.setDisbursementConditionCode(disbursementConditionsResponse.getCode());
			}
			
//			LoggerRequest.getInstance().logInfo1("******<<<<<<  Validate Duplicate >>>>>>******");
//			Optional<DisbursementDetails> relevantDisbursementvalidation = disbursementDetailsRepository.findByCustomerIdAndBankIdAndBankBranchIdAndAccount(customer.getId(),Long.parseLong(disbursementDetailsRequestResource.getBankId()), Long.parseLong(disbursementDetailsRequestResource.getBankBranchId()),disbursementDetailsRequestResource.getAccount());
//			
//			if(relevantDisbursementvalidation.isPresent()) {
//				
//				if(disbursementDetailsRequestResource.getId() != null && !disbursementDetailsRequestResource.getId().isEmpty()) {
//					//omit same record update
//					if(!relevantDisbursementvalidation.get().getId().equals(stringToLong(disbursementDetailsRequestResource.getId()))) {
//					
//						throw new DetailListValidateException(environment.getProperty(CUSTOMER_DUPLICATE_DISBURSEMENT_DETAILS), ServiceEntity.CUSTOMER_ID, ServicePoint.DISBURSEMENT_DETAILS,index);
//					}
//					
//				}else {
//					
//					throw new DetailListValidateException(environment.getProperty(CUSTOMER_DUPLICATE_DISBURSEMENT_DETAILS), ServiceEntity.CUSTOMER_ID, ServicePoint.DISBURSEMENT_DETAILS,index);
//				}
//			}
			
			disbursementDetails.setCustomer(customer);
			disbursementDetails.setLead(relevantLeanInfo.get());
			disbursementDetails.setAmount(stringToBigDecimal(disbursementDetailsRequestResource.getAmount()));
			disbursementDetails.setPayModeId(validateService.stringToLong(disbursementDetailsRequestResource.getPayMethodId()));
			disbursementDetails.setPayModeCode(payModeResponse.getCode());
			disbursementDetails.setBankId(stringToLong(disbursementDetailsRequestResource.getBankId()));
			disbursementDetails.setBankCode(disbursementDetailsRequestResource.getBankCode());
			disbursementDetails.setBankBranchId(stringToLong(disbursementDetailsRequestResource.getBankBranchId()));
			disbursementDetails.setBankBranchCode(disbursementDetailsRequestResource.getBankBranchCode());
			disbursementDetails.setAccount(disbursementDetailsRequestResource.getAccount());
			disbursementDetails.setStatus(CommonStatus.valueOf(disbursementDetailsRequestResource.getStatus()));
			disbursementDetails.setPayeeType(PayeeType.valueOf(disbursementDetailsRequestResource.getPayeeType()));
			disbursementDetails.setPayeeName(disbursementDetailsRequestResource.getPayeeName());
			disbursementDetails.setComments(disbursementDetailsRequestResource.getComments());
			
			disbursementDetails.setSyncTs(getCreateOrModifyDate());
			disbursementDetailsRepository.saveAndFlush(disbursementDetails);
			
			index++;
		
		}
	}
	
	// String to Long
	private Long stringToLong(String value){
		return Long.parseLong(value);
	}
	
	// Get a created and modified date
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	// String to BigDecimal
	private BigDecimal stringToBigDecimal(String value) {
		if (value != null) {
			return new BigDecimal(value);
		} else {
			return BigDecimal.valueOf(0.0);
		}
	}

	/**
	 * @author Sanatha
	 * */
	private CommonList comnListValidation(String id, String name, CommonListReferenceCodes referenceCode, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN comnListValidation>>>>>>******");
		if(id !=null && !id.isEmpty()) {
			LoggerRequest.getInstance().logInfo1("******<<<<<<id>>>>>>******"+id);
			LoggerRequest.getInstance().logInfo1("******<<<<<<name>>>>>>******"+name);
			LoggerRequest.getInstance().logInfo1("******<<<<<<referenceCode>>>>>>******"+referenceCode.toString());
			
			Optional<CommonList> commonList=commonListRepository.findById(Long.parseLong(id));
			if(!commonList.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), serviceEntity, servicePoint,index);
			}
			
			LoggerRequest.getInstance().logInfo1("******<<<<<<commonList.get().getId()>>>>>>******"+commonList.get().getId());
			LoggerRequest.getInstance().logInfo1("******<<<<<<commonList.get().getName()>>>>>>******"+commonList.get().getName());
			LoggerRequest.getInstance().logInfo1("******<<<<<<commonList.get().getReferenceCode()>>>>>>******"+commonList.get().getReferenceCode());
			if(commonList.isPresent() && commonList.get().getName().equalsIgnoreCase(name) && commonList.get().getReferenceCode().equalsIgnoreCase(referenceCode.toString()) 
					&& commonList.get().getStatus().toString().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
				return commonList.get();
			}else {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), serviceEntity, servicePoint,index);
			}
		}
		return null;
	}

	/**
	 * @author Sanatha
	 * */
	private CommonList getComnListName(String id) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN getComnListName>>>>>>******");
 		
 		Optional<CommonList> commonList=commonListRepository.findById(Long.parseLong(id));
 		if(commonList.isPresent()) {
 			return commonList.get();
 		}else {
 			return null;
 		}
	}
	
	/**
	 * @author Sanatha
	 * */
	private void setBankBranchName(String tenantId, DisbursementDetails disbursementDetails) {
		
		BankResponse bankResponse = validateService.getBankName(tenantId, disbursementDetails.getBankId());
		disbursementDetails.setBankName(bankResponse.getBankName());
		
		BankBranchResponseResource bankBranchResponseResource = validateService.getBankBranchName(tenantId, disbursementDetails.getBankBranchId());
		disbursementDetails.setBankBranchName(bankBranchResponseResource.getBbrhName());
		
		
	}

	@Override
	public Optional<DisbursementDetails> findById(Long id) {
		Optional<DisbursementDetails> isDisbursementDetails = disbursementDetailsRepository.findById(id);
		if (isDisbursementDetails.isPresent()) {
			return Optional.ofNullable(isDisbursementDetails.get());
		} else {
			return Optional.empty();
		}
	}

}
