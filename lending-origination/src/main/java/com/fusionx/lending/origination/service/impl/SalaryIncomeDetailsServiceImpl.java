package com.fusionx.lending.origination.service.impl;

/**
 * 	Salary Income Details Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.domain.SalaryIncomeDetails;
import com.fusionx.lending.origination.domain.SalaryIncomeDocuments;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.EmploymentTypeEnum;
import com.fusionx.lending.origination.enums.IncomeTypeEnum;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.SourceTypeEnum;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.IncomeSourceDetailsRepository;
import com.fusionx.lending.origination.repository.SalaryIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.SalaryIncomeDocumentsRepository;
import com.fusionx.lending.origination.resource.CustomerEmploymentUpdateRequestResource;
import com.fusionx.lending.origination.resource.DesignationResponse;
import com.fusionx.lending.origination.resource.DocumentAddResource;
import com.fusionx.lending.origination.resource.DocumentUpdateResource;
import com.fusionx.lending.origination.resource.EmployerResponse;
import com.fusionx.lending.origination.resource.PerCommonList;
import com.fusionx.lending.origination.resource.SalaryIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.SalaryIncomeDetailsListResource;
import com.fusionx.lending.origination.resource.SalaryIncomeDetailsUpdateResource;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.SalaryIncomeDetailsService;
import com.fusionx.lending.origination.service.ValidateService;




@Component
@Transactional(rollbackFor = Exception.class)
public class SalaryIncomeDetailsServiceImpl extends MessagePropertyBase implements SalaryIncomeDetailsService{
	
	
	private SalaryIncomeDetailsRepository salaryIncomeDetailsRepository;
	
	@Autowired
	public void setSalaryIncomeDetailsRepository(SalaryIncomeDetailsRepository salaryIncomeDetailsRepository) {
		this.salaryIncomeDetailsRepository = salaryIncomeDetailsRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	private IncomeSourceDetailsRepository incomeSourceDetailsRepository;
	
	@Autowired
	public void setIncomeSourceDetailsRepository(IncomeSourceDetailsRepository incomeSourceDetailsRepository) {
		this.incomeSourceDetailsRepository = incomeSourceDetailsRepository;
	}
	
	@Autowired
	RemoteService remoteService;
	
	private SalaryIncomeDocumentsRepository salaryIncomeDocumentsRepository;
	
	@Autowired
	public void setSalaryIncomeDocumentsRepository(SalaryIncomeDocumentsRepository salaryIncomeDocumentsRepository) {
		this.salaryIncomeDocumentsRepository = salaryIncomeDocumentsRepository;
	}

	@Override
	public Optional<SalaryIncomeDetails> findById(String tenantId ,Long id) {
		Optional<SalaryIncomeDetails> isPresentSalaryIncomeDetails = salaryIncomeDetailsRepository.findById(id);
		
		if (isPresentSalaryIncomeDetails.isPresent()) {
			return Optional.ofNullable(setSalaryIncomeDetails(tenantId,isPresentSalaryIncomeDetails.get()));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<SalaryIncomeDetails> findAll(String tenantId) {
		List<SalaryIncomeDetails> salaryIncomeDetailsList = salaryIncomeDetailsRepository.findAll();
		
		for (SalaryIncomeDetails incomeSourceDetail : salaryIncomeDetailsList) {
			setSalaryIncomeDetails(tenantId, incomeSourceDetail);
		}
		return salaryIncomeDetailsList;
	}


	@Override
	public List<SalaryIncomeDetails> findByStatus(String tenantId,String status) {
		
		List<SalaryIncomeDetails> salaryIncomeDetailsList = salaryIncomeDetailsRepository.findByStatus(CommonStatus.valueOf(status));
		
		for (SalaryIncomeDetails incomeSourceDetail : salaryIncomeDetailsList) {
			setSalaryIncomeDetails( tenantId ,incomeSourceDetail);
		}
		return 	salaryIncomeDetailsList;
	}
	
	@Override
	public Long save(String tenantId,SalaryIncomeDetailsAddResource salaryIncomeDetailsAddResource) {
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
        EmployerResponse employerResponse = null;
        Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository.findById(validateService.stringToLong(salaryIncomeDetailsAddResource.getIncomeSourceDetailsId()));
		if (!isPresentIncomeSourceDetails.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "incomeSourceDetailId");
		
		if(!IncomeTypeEnum.SALARY.toString().equals(isPresentIncomeSourceDetails.get().getIncomeType().toString()))
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_MATCH), "incomeSourceDetailId");
		
		if(salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList() != null && !salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList().isEmpty()) {
			Integer index=0;
			for(SalaryIncomeDetailsListResource salaryIncomeDetailsListResource : salaryIncomeDetailsAddResource.getSalaryIncomeDetailsList()) {
				
				DesignationResponse designationResponse= validateService.validateDesignation(tenantId, salaryIncomeDetailsListResource.getDesignationId());
				if(!designationResponse.getDesgName().equalsIgnoreCase(salaryIncomeDetailsListResource.getDesignationName()))
					throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.DESIGNATION, ServicePoint.SALARY_INCOME_DETAILS, index);
			
				validateService.validatePersonCommonList(tenantId, salaryIncomeDetailsListResource.getJobTypeId(), salaryIncomeDetailsListResource.getJobTypeDesc(), 
						CommonListReferenceCode.EMPLOYMENTCATEGORY.toString() ,index ,ServiceEntity.JOB_TYPE_ID, ServicePoint.SALARY_INCOME_DETAILS,null);
				
				if(SourceTypeEnum.PRIMARY.toString().equals(salaryIncomeDetailsListResource.getSourceType())) {
					Optional<SalaryIncomeDetails> isPresentPrimary = salaryIncomeDetailsRepository.findByIncomeSourceDetailIdAndSourceTypeAndStatus(isPresentIncomeSourceDetails.get().getId() , SourceTypeEnum.PRIMARY ,CommonStatus.ACTIVE);
					if(isPresentPrimary.isPresent())
						 throw new DetailListValidateException(getEnvironment().getProperty(COMMON_DUPLICATE), ServiceEntity.SOURCE_TYPE, ServicePoint.SALARY_INCOME_DETAILS ,index);
				}
				
				if(salaryIncomeDetailsListResource.getEmployerId() != null && !salaryIncomeDetailsListResource.getEmployerId().isEmpty()) {
					 employerResponse = validateService.validateEmployerById(tenantId, salaryIncomeDetailsListResource.getEmployerId(),salaryIncomeDetailsListResource.getEmployerName());
				}
			        SalaryIncomeDetails salaryIncomeDetails = new SalaryIncomeDetails();
			        salaryIncomeDetails.setTenantId(tenantId);
			        salaryIncomeDetails.setIncomeSourceDetail(isPresentIncomeSourceDetails.get());
			        salaryIncomeDetails.setEmploymentType(EmploymentTypeEnum.valueOf(salaryIncomeDetailsListResource.getEmploymentType()));
			        salaryIncomeDetails.setEmployerId(employerResponse != null ? employerResponse.getId():null);
			        salaryIncomeDetails.setEmployerName(salaryIncomeDetailsListResource.getEmployerName());
			        salaryIncomeDetails.setExperience(validateService.stringToInteger(salaryIncomeDetailsListResource.getExperience()));
			        salaryIncomeDetails.setDesignationId(validateService.stringToLong(designationResponse.getId()));
			        salaryIncomeDetails.setJobTypeId(validateService.stringToLong(salaryIncomeDetailsListResource.getJobTypeId()));
			        salaryIncomeDetails.setSourceType(SourceTypeEnum.valueOf(salaryIncomeDetailsListResource.getSourceType()));
			        salaryIncomeDetails.setStatus(CommonStatus.valueOf(salaryIncomeDetailsListResource.getStatus()));
			        salaryIncomeDetails.setSyncTs(validateService.getSyncTs());
			        salaryIncomeDetails.setCreatedDate(validateService.getCreateOrModifyDate());
			        salaryIncomeDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			        salaryIncomeDetails = salaryIncomeDetailsRepository.save(salaryIncomeDetails);
			        
			        if(salaryIncomeDetailsListResource.getSalaryIncomeDocumentList() != null && !salaryIncomeDetailsListResource.getSalaryIncomeDocumentList().isEmpty()) {
			        	
			        	for(DocumentAddResource documentAddResource : salaryIncomeDetailsListResource.getSalaryIncomeDocumentList()) {

							validateService.validateDocument(tenantId, documentAddResource.getDocumentId(),documentAddResource.getDocumentName(),ServicePoint.SALARY_INCOME_DETAILS,Constants.ORIGIN_CUSTOMER, index);
							
							Optional<SalaryIncomeDocuments> isPresentSalaryIncomeDocument = salaryIncomeDocumentsRepository.findBySalaryIncomeDetailsIdAndDocumentIdAndStatus(salaryIncomeDetails.getId(), validateService.stringToLong(documentAddResource.getDocumentId()), CommonStatus.ACTIVE);
							if(isPresentSalaryIncomeDocument.isPresent()) {
								throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "documentId");							
							}
							
							SalaryIncomeDocuments salaryIncomeDocuments = new SalaryIncomeDocuments();
							salaryIncomeDocuments.setTenantId(tenantId);
							salaryIncomeDocuments.setDocumentId(validateService.stringToLong(documentAddResource.getDocumentId()));
							salaryIncomeDocuments.setSalaryIncomeDetails(salaryIncomeDetails);
							salaryIncomeDocuments.setStatus(CommonStatus.valueOf(documentAddResource.getStatus()));
							salaryIncomeDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
							salaryIncomeDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
							salaryIncomeDocuments.setSyncTs(validateService.getSyncTs());
							salaryIncomeDocumentsRepository.saveAndFlush(salaryIncomeDocuments);
			        	}
			        }
				    
			        if(isPresentIncomeSourceDetails.get().getCustomer() != null &&  employerResponse != null) {  
				        addCustomerEmploymentDetails(tenantId , employerResponse.getCode() , salaryIncomeDetailsListResource ,isPresentIncomeSourceDetails.get().getCustomer().getPendingCustomerId(), LogginAuthentcation.getInstance().getUserName());
				     }
			        
				index++;
			}
	        return isPresentIncomeSourceDetails.get().getId();
	        
		}else
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "salaryIncomeDetailsList");
        

	}

	@Override
	public SalaryIncomeDetails update(String tenantId, Long id , SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		  EmployerResponse employerResponse = null;
		Optional<SalaryIncomeDetails> isPresentSalaryIncomeDetails = salaryIncomeDetailsRepository.findById(id);
		if (!isPresentSalaryIncomeDetails.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		
		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository.findById(isPresentSalaryIncomeDetails.get().getIncomeSourceDetail().getId());
			
		if(!IncomeTypeEnum.SALARY.toString().equals(isPresentIncomeSourceDetails.get().getIncomeType().toString()))
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_MATCH), "message");
					
		DesignationResponse designationResponse= validateService.validateDesignation(tenantId, salaryIncomeDetailsUpdateResource.getDesignationId());
		if(!designationResponse.getDesgName().equalsIgnoreCase(salaryIncomeDetailsUpdateResource.getDesignationName()))
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "designationName");
	
		validateService.validatePersonCommonList(tenantId, salaryIncomeDetailsUpdateResource.getJobTypeId(), salaryIncomeDetailsUpdateResource.getJobTypeDesc(), 
				CommonListReferenceCode.EMPLOYMENTCATEGORY.toString() , null ,null, null, "jobTypeId");
		
		if(SourceTypeEnum.PRIMARY.toString().equals(salaryIncomeDetailsUpdateResource.getSourceType())) {
			Optional<SalaryIncomeDetails> isPresentPrimary = salaryIncomeDetailsRepository.findByIncomeSourceDetailIdAndSourceTypeAndStatusAndIdNotIn(isPresentIncomeSourceDetails.get().getId() , SourceTypeEnum.PRIMARY ,CommonStatus.ACTIVE, id);
			if(isPresentPrimary.isPresent())
				throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "sourceType");
		}
		
		if(!isPresentSalaryIncomeDetails.get().getVersion().equals(Long.parseLong(salaryIncomeDetailsUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");

		if(salaryIncomeDetailsUpdateResource.getEmployerId() != null && !salaryIncomeDetailsUpdateResource.getEmployerId().isEmpty()) {
			 employerResponse = validateService.validateEmployerById(tenantId, salaryIncomeDetailsUpdateResource.getEmployerId(),salaryIncomeDetailsUpdateResource.getEmployerName());
		}
		    SalaryIncomeDetails salaryIncomeDetails = isPresentSalaryIncomeDetails.get();
		    salaryIncomeDetails.setTenantId(tenantId);
	        salaryIncomeDetails.setEmploymentType(EmploymentTypeEnum.valueOf(salaryIncomeDetailsUpdateResource.getEmploymentType()));
	        salaryIncomeDetails.setEmployerId(employerResponse != null ? employerResponse.getId():null);
	        salaryIncomeDetails.setEmployerName(salaryIncomeDetailsUpdateResource.getEmployerName());
	        salaryIncomeDetails.setExperience(validateService.stringToInteger(salaryIncomeDetailsUpdateResource.getExperience()));
	        salaryIncomeDetails.setDesignationId(validateService.stringToLong(designationResponse.getId()));
	        salaryIncomeDetails.setJobTypeId(validateService.stringToLong(salaryIncomeDetailsUpdateResource.getJobTypeId()));
	        salaryIncomeDetails.setSourceType(SourceTypeEnum.valueOf(salaryIncomeDetailsUpdateResource.getSourceType()));
	        salaryIncomeDetails.setStatus(CommonStatus.valueOf(salaryIncomeDetailsUpdateResource.getStatus()));
	        salaryIncomeDetails.setSyncTs(validateService.getSyncTs());
	        salaryIncomeDetails.setModifiedDate(validateService.getCreateOrModifyDate());
	        salaryIncomeDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        salaryIncomeDetails = salaryIncomeDetailsRepository.save(salaryIncomeDetails);
	        
	        
	        if(salaryIncomeDetailsUpdateResource.getSalaryIncomeDocumentList() != null && !salaryIncomeDetailsUpdateResource.getSalaryIncomeDocumentList().isEmpty()) {
	        	Integer index = 0;
	        	for(DocumentUpdateResource documentUpdateResource : salaryIncomeDetailsUpdateResource.getSalaryIncomeDocumentList()) {

					validateService.validateDocument(tenantId, documentUpdateResource.getDocumentId(),documentUpdateResource.getDocumentName(), ServicePoint.SALARY_INCOME_DOCUMENTS,Constants.ORIGIN_CUSTOMER, index);
					
					// update existing document
					if(documentUpdateResource.getId() != null && !documentUpdateResource.getDocumentId().isEmpty()) {
						
						Optional<SalaryIncomeDocuments> isPresentSalaryIncomeDocument = salaryIncomeDocumentsRepository.findById(validateService.stringToLong(documentUpdateResource.getId()));
						if(!isPresentSalaryIncomeDocument.isPresent())
							throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ID, ServicePoint.SALARY_INCOME_DOCUMENTS, index);									
						
						Optional<SalaryIncomeDocuments> isDuplicateSalaryIncomeDocument = salaryIncomeDocumentsRepository.findBySalaryIncomeDetailsIdAndDocumentIdAndStatusAndIdNotIn(salaryIncomeDetails.getId(),
								validateService.stringToLong(documentUpdateResource.getDocumentId()), CommonStatus.ACTIVE ,isPresentSalaryIncomeDocument.get().getId() );
						if(isDuplicateSalaryIncomeDocument.isPresent()) {
							throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.SALARY_INCOME_DOCUMENTS, index);						
						}
						
						if(!isPresentSalaryIncomeDocument.get().getVersion().equals(Long.parseLong(documentUpdateResource.getVersion())))
							throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, ServicePoint.SALARY_INCOME_DOCUMENTS, index);						
						
							SalaryIncomeDocuments salaryIncomeDocuments = isPresentSalaryIncomeDocument.get();
							salaryIncomeDetails.setTenantId(tenantId);
							salaryIncomeDocuments.setDocumentId(validateService.stringToLong(documentUpdateResource.getDocumentId()));
							salaryIncomeDocuments.setSalaryIncomeDetails(salaryIncomeDetails);
							salaryIncomeDocuments.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
							salaryIncomeDocuments.setModifiedDate(validateService.getCreateOrModifyDate());
							salaryIncomeDocuments.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
							salaryIncomeDocuments.setSyncTs(validateService.getSyncTs());
							salaryIncomeDocumentsRepository.saveAndFlush(salaryIncomeDocuments);
						
					}else {// update with new document
						Optional<SalaryIncomeDocuments> isPresentSalaryIncomeDocument = salaryIncomeDocumentsRepository.findBySalaryIncomeDetailsIdAndDocumentIdAndStatus(salaryIncomeDetails.getId(), 
								validateService.stringToLong(documentUpdateResource.getDocumentId()), CommonStatus.ACTIVE);
						if(isPresentSalaryIncomeDocument.isPresent()) {
							throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.SALARY_INCOME_DOCUMENTS, index);							
						}
						
						SalaryIncomeDocuments salaryIncomeDocuments = new SalaryIncomeDocuments();
						salaryIncomeDocuments.setTenantId(tenantId);
						salaryIncomeDocuments.setDocumentId(validateService.stringToLong(documentUpdateResource.getDocumentId()));
						salaryIncomeDocuments.setSalaryIncomeDetails(salaryIncomeDetails);
						salaryIncomeDocuments.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
						salaryIncomeDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
						salaryIncomeDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
						salaryIncomeDocuments.setSyncTs(validateService.getSyncTs());
						salaryIncomeDocumentsRepository.saveAndFlush(salaryIncomeDocuments);
					}
	        	}
	        }
	        return salaryIncomeDetails;
	}


	private SalaryIncomeDetails setSalaryIncomeDetails(String tenantId, SalaryIncomeDetails salaryIncomeDetails){				
		DesignationResponse designationResponse= validateService.validateDesignation(tenantId, salaryIncomeDetails.getDesignationId().toString());
		
		PerCommonList perCommonList= validateService.validatePersonCommonList(tenantId, salaryIncomeDetails.getJobTypeId().toString() , null, 
				CommonListReferenceCode.EMPLOYMENTCATEGORY.toString() , null ,ServiceEntity.JOB_TYPE_ID, ServicePoint.SALARY_INCOME_DETAILS,null);
		
		salaryIncomeDetails.setDesignation(designationResponse.getDesgName());		
		salaryIncomeDetails.setJobType(perCommonList.getCmlsDesc());		
		
		salaryIncomeDetails.setSalaryIncomeDocuments(salaryIncomeDocumentsRepository.findBySalaryIncomeDetailsId(salaryIncomeDetails.getId()));
		return salaryIncomeDetails;
		
	}

	@Override
	public List<SalaryIncomeDetails> findByIncomeSourceDetailsId(String tenantId, Long incomeSourceDetailsId) {
		List<SalaryIncomeDetails> salaryIncomeDetailsList = salaryIncomeDetailsRepository.findByIncomeSourceDetailId(incomeSourceDetailsId);
		
		for (SalaryIncomeDetails incomeSourceDetail : salaryIncomeDetailsList) {
			setSalaryIncomeDetails( tenantId ,incomeSourceDetail);
		}
		return 	salaryIncomeDetailsList;
	}
	
	private void addCustomerEmploymentDetails(String tenantId , String employerCode , SalaryIncomeDetailsListResource salaryIncomeDetailsListResource , Long customeId , String user) {
		
		CustomerEmploymentUpdateRequestResource customerEmploymentUpdateRequestResource = new CustomerEmploymentUpdateRequestResource();
		customerEmploymentUpdateRequestResource.setCueDesignationId(validateService.stringToLong(salaryIncomeDetailsListResource.getDesignationId()));
		customerEmploymentUpdateRequestResource.setCueDesignationDesc(salaryIncomeDetailsListResource.getDesignationName());
		customerEmploymentUpdateRequestResource.setCueEmployerCode(employerCode);
		customerEmploymentUpdateRequestResource.setCueEmployerName(salaryIncomeDetailsListResource.getEmployerName());
		customerEmploymentUpdateRequestResource.setCueEmploymentCategoryCommonListId(validateService.stringToLong(salaryIncomeDetailsListResource.getJobTypeId()));
		customerEmploymentUpdateRequestResource.setCueEmploymentCategoryDesc(salaryIncomeDetailsListResource.getJobTypeDesc());
//		customerEmploymentUpdateRequestResource.setCueEmploymentTypeCommonListId(null);
//		customerEmploymentUpdateRequestResource.setCueEmploymentTypeDesc(ASSET_REGISTR_AUTH_ID);
		customerEmploymentUpdateRequestResource.setCueWorkingYears(validateService.stringToLong(salaryIncomeDetailsListResource.getExperience()));
		customerEmploymentUpdateRequestResource.setCueStatus(salaryIncomeDetailsListResource.getStatus());
		
		//validateService.updateCustomerEmployment(tenantId, customerEmploymentUpdateRequestResource, customeId, user);
		
	}

}
