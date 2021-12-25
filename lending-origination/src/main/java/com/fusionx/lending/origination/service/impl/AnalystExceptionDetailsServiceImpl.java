package com.fusionx.lending.origination.service.impl;
/**
 * 	Analyst Exception Details Service impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
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
import com.fusionx.lending.origination.domain.AnalystDetails;
import com.fusionx.lending.origination.domain.AnalystExceptionDetails;
import com.fusionx.lending.origination.domain.AnalystExceptionDocuments;
import com.fusionx.lending.origination.domain.AnalystExceptionWorkflow;
import com.fusionx.lending.origination.domain.ExceptionApprovalGroupType;
import com.fusionx.lending.origination.domain.ExceptionType;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.domain.UserMappingDetails;
import com.fusionx.lending.origination.enums.AnalystExceptionApprovalStatus;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.enums.WorkflowType;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.AnalystDetailsRepository;
import com.fusionx.lending.origination.repository.AnalystExceptionDetailsRepository;
import com.fusionx.lending.origination.repository.AnalystExceptionDocumentsRepository;
import com.fusionx.lending.origination.repository.AnalystExceptionWorkflowRepository;
import com.fusionx.lending.origination.repository.ExceptionApprovalGroupTypeRepository;
import com.fusionx.lending.origination.repository.ExceptionTypeRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.repository.UserMappingDetailsRepository;
import com.fusionx.lending.origination.resource.AnalystAddResource;
import com.fusionx.lending.origination.resource.AnalystExceptionDetailsResource;
import com.fusionx.lending.origination.resource.DocumentUpdateResource;
import com.fusionx.lending.origination.resource.AnalystUpdateResource;
import com.fusionx.lending.origination.resource.UserProfileResponse;
import com.fusionx.lending.origination.resource.WorkflowInitiationRequestResource;
import com.fusionx.lending.origination.service.AnalystExceptionDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class AnalystExceptionDetailsServiceImpl extends MessagePropertyBase implements AnalystExceptionDetailsService {
	
	private AnalystDetailsRepository analystDetailsRepository;
	
	@Autowired
	public void setAnalystDetailsRepository(AnalystDetailsRepository analystDetailsRepository) {
		this.analystDetailsRepository = analystDetailsRepository;
	}
	
	private AnalystExceptionDetailsRepository analystExceptionDetailsRepository;
	
	@Autowired
	public void setAnalystExceptionDetailsRepository(AnalystExceptionDetailsRepository analystExceptionDetailsRepository) {
		this.analystExceptionDetailsRepository = analystExceptionDetailsRepository;
	}
	
	private ExceptionTypeRepository exceptionTypeRepository;
	
	@Autowired
	public void setExceptionTypeRepository(ExceptionTypeRepository exceptionTypeRepository) {
		this.exceptionTypeRepository = exceptionTypeRepository;
	}
	
	private AnalystExceptionDocumentsRepository analystExceptionDocumentRepository;
	
	@Autowired
	public void setAnalystExceptionDocumentRepository(AnalystExceptionDocumentsRepository analystExceptionDocumentRepository) {
		this.analystExceptionDocumentRepository = analystExceptionDocumentRepository;
	}
	
	private LeadInfoRepository leadInfoRepository;
	
	@Autowired
	public void setLeadInfoRepository(LeadInfoRepository leadInfoRepository) {
		this.leadInfoRepository = leadInfoRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	private ExceptionApprovalGroupTypeRepository exceptionApprovalGroupTypeRepository;
	
	@Autowired
	public void setExceptionApprovalGroupTypeRepository(ExceptionApprovalGroupTypeRepository exceptionApprovalGroupTypeRepository) {
		this.exceptionApprovalGroupTypeRepository = exceptionApprovalGroupTypeRepository;
	}
	
	private UserMappingDetailsRepository userMappingDetailsRepository;
	
	@Autowired
	public void setUserMappingDetailsRepository(UserMappingDetailsRepository userMappingDetailsRepository) {
		this.userMappingDetailsRepository = userMappingDetailsRepository;
	}
	
	@Autowired
	private AnalystExceptionWorkflowRepository analystExceptionWorkflowRepository;
	
	@Override
	public List<AnalystDetails> findAll(String tenantId) {
		List<AnalystDetails> analystDetailList = analystDetailsRepository.findAll();
		
		for (AnalystDetails analystDetail : analystDetailList) {
			setAnalystDetails(tenantId, analystDetail);
		}
		return analystDetailList;
	}

	@Override
	public Optional<AnalystDetails> findById(String tenantId,Long id) {
		Optional<AnalystDetails> isPresentAnalystDetail = analystDetailsRepository.findById(id);
		
		if (isPresentAnalystDetail.isPresent()) {
			return Optional.ofNullable(setAnalystDetails(tenantId,isPresentAnalystDetail.get()));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<AnalystDetails> findByStatus(String tenantId, String status) {
		List<AnalystDetails> analystDetailList = analystDetailsRepository.findByStatus(CommonStatus.valueOf(status));
		
		for (AnalystDetails analystDetail : analystDetailList) {
			setAnalystDetails(tenantId,analystDetail);
		}
		return analystDetailList;
	}

	@Override
	public AnalystDetails add(String tenantId, AnalystAddResource analystAddResource) {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
			
		AnalystDetails analystDetails = new AnalystDetails();
		analystDetails.setTenantId(tenantId);
		analystDetails.setAnalystUserId(analystAddResource.getAnalystUserId());
		
		if("INTERNAL".equalsIgnoreCase(analystAddResource.getAnalystType())) {		
			UserProfileResponse analyst = validateService.validateUserProfileByUserId(tenantId, analystAddResource.getAnalystUserId(), analystAddResource.getAnalystUserName());
			analystDetails.setAnalystUserId(analyst.getId().toString());
		}
			Optional<LeadInfo> isPresentLeadInfo = leadInfoRepository.findById(validateService.stringToLong(analystAddResource.getLeadId()));
			
			if(!isPresentLeadInfo.isPresent())
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "leadId");
			
			
			analystDetails.setAnalystType(analystAddResource.getAnalystType());
			analystDetails.setLeadInfo(isPresentLeadInfo.get());
			analystDetails.setObservation(analystAddResource.getObservation());
			analystDetails.setStatus(CommonStatus.valueOf(analystAddResource.getStatus()));
			analystDetails.setCreatedDate(validateService.getCreateOrModifyDate());
			analystDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
			analystDetails.setSyncTs(validateService.getSyncTs());
			analystDetails = analystDetailsRepository.saveAndFlush(analystDetails);
			
			if(analystAddResource.getAnalystExceptionDetailList() != null && !analystAddResource.getAnalystExceptionDetailList().isEmpty()) {
				
				Integer indexExcp = 0;
				for(AnalystExceptionDetailsResource analystExceptionDetail: analystAddResource.getAnalystExceptionDetailList()) {
					
					ExceptionType exceptionType = validateExceptionType(validateService.stringToLong(analystExceptionDetail.getExceptionTypeId()), analystExceptionDetail.getExceptionTypeName());
					
					Optional<AnalystExceptionDetails> isPresentAnalystExceptionDetail = analystExceptionDetailsRepository.findByAnalystDetailIdAndExceptionTypeId(analystDetails.getId(),exceptionType.getId());
					if(isPresentAnalystExceptionDetail.isPresent()) {
							throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.EXCEPTION_TYPE_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);
					}
					
					UserProfileResponse userProfileResponse = validateService.validateUserProfileByUserId(tenantId, analystExceptionDetail.getAuthorizedUserId(), analystExceptionDetail.getAuthorizedUserName());
					
					validateExceptionAuthority(exceptionType.getId(),userProfileResponse.getId(), indexExcp);
					
					AnalystExceptionDetails analystExceptionDetails= new AnalystExceptionDetails();
					analystExceptionDetails.setTenantId(tenantId);
					analystExceptionDetails.setExceptionType(exceptionType);
					analystExceptionDetails.setAnalystDetail(analystDetails);
					analystExceptionDetails.setExceptionAuthorityId(userProfileResponse.getId());
					analystExceptionDetails.setExceptionDetails(analystExceptionDetail.getExceptionDetail());
					analystExceptionDetails.setStatus(CommonStatus.valueOf(analystExceptionDetail.getStatus()));
					analystExceptionDetails.setApprovalStatus(AnalystExceptionApprovalStatus.PENDING);
					analystExceptionDetails.setApprovalDate(validateService.getCreateOrModifyDate());
					analystExceptionDetails.setApprovalUser(LogginAuthentcation.getInstance().getUserName());
					analystExceptionDetails.setCreatedDate(validateService.getCreateOrModifyDate());
					analystExceptionDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
					analystExceptionDetails.setSyncTs(validateService.getSyncTs());
					analystExceptionDetails = analystExceptionDetailsRepository.saveAndFlush(analystExceptionDetails);
					
					if(analystExceptionDetail.getAnalystExceptionDocumentList() != null && !analystExceptionDetail.getAnalystExceptionDocumentList().isEmpty()) {
						
						for(DocumentUpdateResource analystExceptionDocument: analystExceptionDetail.getAnalystExceptionDocumentList()) {
							
							validateService.validateDocument(tenantId, analystExceptionDocument.getDocumentId(),analystExceptionDocument.getDocumentName(),ServicePoint.ANALYST_EXCEPTION_DETAILS , Constants.ORIGIN_CUSTOMER, indexExcp);
							
							Optional<AnalystExceptionDocuments> isPresentAnalystExceptionDocument = analystExceptionDocumentRepository.findByAnalystExceptionDetailIdAndDocumentIdAndStatus(analystExceptionDetails.getId(), validateService.stringToLong(analystExceptionDocument.getDocumentId()),CommonStatus.ACTIVE);
							if(isPresentAnalystExceptionDocument.isPresent()) {
								throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);							
							}
							
							AnalystExceptionDocuments analystExceptionDocuments = new AnalystExceptionDocuments();
							analystExceptionDocuments.setTenantId(tenantId);
							analystExceptionDocuments.setDocumentId(validateService.stringToLong(analystExceptionDocument.getDocumentId()));
							analystExceptionDocuments.setAnalystExceptionDetail(analystExceptionDetails);
							analystExceptionDocuments.setStatus(CommonStatus.valueOf(analystExceptionDocument.getStatus()));
							analystExceptionDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
							analystExceptionDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
							analystExceptionDocuments.setSyncTs(validateService.getSyncTs());
							analystExceptionDocumentRepository.saveAndFlush(analystExceptionDocuments);
				
						}
					}
					indexExcp++;
				}
			}
			
		return analystDetails;
	}

	@Override
	public AnalystDetails update(String tenantId, Long id, AnalystUpdateResource analystUpdateResource) {
		
			if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
	        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
			
			Optional<AnalystDetails> isPresentAnalystDetail = analystDetailsRepository.findById(id);
			
			AnalystDetails analystDetails = isPresentAnalystDetail.get();
			analystDetails.setAnalystUserId(analystUpdateResource.getAnalystUserId());
			
			if("INTERNAL".equalsIgnoreCase(analystUpdateResource.getAnalystType()))	{
				UserProfileResponse analyst = validateService.validateUserProfileByUserId(tenantId, analystUpdateResource.getAnalystUserId(), analystUpdateResource.getAnalystUserName());
				analystDetails.setAnalystUserId(analyst.getId().toString());
			}
			Optional<LeadInfo> isPresentLeadInfo = leadInfoRepository.findById(validateService.stringToLong(analystUpdateResource.getLeadId()));
			if(!isPresentLeadInfo.isPresent())
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "leadId");
			
			if(isPresentAnalystDetail.isPresent() && !isPresentAnalystDetail.get().getVersion().equals(Long.parseLong(analystUpdateResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "version");
			
			analystDetails.setTenantId(tenantId);
			analystDetails.setAnalystType(analystUpdateResource.getAnalystType());
			analystDetails.setLeadInfo(isPresentLeadInfo.get());
			analystDetails.setObservation(analystUpdateResource.getObservation());
			analystDetails.setStatus(CommonStatus.valueOf(analystUpdateResource.getStatus()));
			analystDetails.setModifiedDate(validateService.getCreateOrModifyDate());
			analystDetails.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
			analystDetails.setSyncTs(validateService.getSyncTs());
			analystDetails = analystDetailsRepository.saveAndFlush(analystDetails);
			
			if(analystUpdateResource.getAnalystExceptionDetailList() != null && !analystUpdateResource.getAnalystExceptionDetailList().isEmpty()) {
				
				Integer indexExcp = 0;
				for(AnalystExceptionDetailsResource analystExceptionDetail: analystUpdateResource.getAnalystExceptionDetailList()) {
					
					ExceptionType exceptionType = validateExceptionType(validateService.stringToLong(analystExceptionDetail.getExceptionTypeId()), analystExceptionDetail.getExceptionTypeName());
					// update existing exception type 
					if(analystExceptionDetail.getId() != null) {
						
						Optional<AnalystExceptionDetails> isPresentAnalystExceptionDetail = analystExceptionDetailsRepository.findByAnalystDetailIdAndId(id, validateService.stringToLong(analystExceptionDetail.getId()));
						if(!isPresentAnalystExceptionDetail.isPresent())
							throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ANALYST_EXCEPTION_DETAIL_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);
						
						if(!(isPresentAnalystExceptionDetail.get().getApprovalStatus().equals(AnalystExceptionApprovalStatus.PENDING) || isPresentAnalystExceptionDetail.get().getApprovalStatus().equals(AnalystExceptionApprovalStatus.REJECTED)))
							throw new DetailListValidateException(environment.getProperty(CAN_NOT_UPDATE), ServiceEntity.ANALYST_EXCEPTION_DETAIL_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);
						
						Optional<AnalystExceptionDetails> isDuplicateAnalystExceptionDetail = analystExceptionDetailsRepository.findByAnalystDetailIdAndExceptionTypeIdAndStatusAndIdNotIn(analystDetails.getId(),exceptionType.getId(), CommonStatus.ACTIVE ,validateService.stringToLong(analystExceptionDetail.getId()));
						if(isDuplicateAnalystExceptionDetail.isPresent()) {
								throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.EXCEPTION_TYPE_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);						
						}
						
						UserProfileResponse userProfileResponse = validateService.validateUserProfileByUserId(tenantId, analystExceptionDetail.getAuthorizedUserId(), analystExceptionDetail.getAuthorizedUserName());
						
						validateExceptionAuthority(exceptionType.getId(),userProfileResponse.getId(), indexExcp);
						
						if(!isPresentAnalystExceptionDetail.get().getVersion().equals(Long.parseLong(analystExceptionDetail.getVersion())))
							throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);						
					
						
						AnalystExceptionDetails analystExceptionDetails= isPresentAnalystExceptionDetail.get();
						analystExceptionDetails.setTenantId(tenantId);
						analystExceptionDetails.setExceptionType(exceptionType);
						analystExceptionDetails.setExceptionAuthorityId(userProfileResponse.getId());
						analystExceptionDetails.setExceptionDetails(analystExceptionDetail.getExceptionDetail());
						analystExceptionDetails.setStatus(CommonStatus.valueOf(analystExceptionDetail.getStatus()));
						analystExceptionDetails.setApprovalStatus(AnalystExceptionApprovalStatus.PENDING);
						analystExceptionDetails.setApprovalDate(validateService.getCreateOrModifyDate());
						analystExceptionDetails.setApprovalUser(LogginAuthentcation.getInstance().getUserName());
						analystExceptionDetails.setModifiedDate(validateService.getCreateOrModifyDate());
						analystExceptionDetails.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
						analystExceptionDetails.setSyncTs(validateService.getSyncTs());
						analystExceptionDetails = analystExceptionDetailsRepository.saveAndFlush(analystExceptionDetails);
						
						if(analystExceptionDetail.getAnalystExceptionDocumentList() != null && !analystExceptionDetail.getAnalystExceptionDocumentList().isEmpty()) {
							
							for(DocumentUpdateResource analystExceptionDocument: analystExceptionDetail.getAnalystExceptionDocumentList()) {
								
								validateService.validateDocument(tenantId, analystExceptionDocument.getDocumentId(),analystExceptionDocument.getDocumentName(), ServicePoint.ANALYST_EXCEPTION_DETAILS,Constants.ORIGIN_CUSTOMER, indexExcp);
								
								// update existing document
								if(analystExceptionDocument.getId() != null && !analystExceptionDocument.getId().isEmpty()) {	
									
									Optional<AnalystExceptionDocuments> isPresentAnalystExceptionDocument = analystExceptionDocumentRepository.findByAnalystExceptionDetailIdAndId(analystExceptionDetails.getId(), validateService.stringToLong(analystExceptionDocument.getId()));
									if(!isPresentAnalystExceptionDocument.isPresent())
										throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ANALYST_EXCEPTION_DOCUMENT_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);									
									
									Optional<AnalystExceptionDocuments> isDuplicateAnalystExceptionDocument = analystExceptionDocumentRepository.findByAnalystExceptionDetailIdAndDocumentIdAndStatusAndIdNotIn(analystExceptionDetails.getId(), validateService.stringToLong(analystExceptionDocument.getDocumentId()), CommonStatus.ACTIVE ,isPresentAnalystExceptionDocument.get().getId() );
									if(isDuplicateAnalystExceptionDocument.isPresent()) {
										throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);						
									}
									
									if(!isPresentAnalystExceptionDocument.get().getVersion().equals(Long.parseLong(analystExceptionDocument.getVersion())))
										throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.DOCUMENT_VERSION, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);						
	
									
									AnalystExceptionDocuments analystExceptionDocuments = isPresentAnalystExceptionDocument.get();
									analystExceptionDocuments.setTenantId(tenantId);
									analystExceptionDocuments.setDocumentId(validateService.stringToLong(analystExceptionDocument.getDocumentId()));
									analystExceptionDocuments.setAnalystExceptionDetail(analystExceptionDetails);
									analystExceptionDocuments.setStatus(CommonStatus.valueOf(analystExceptionDocument.getStatus()));
									analystExceptionDocuments.setModifiedDate(validateService.getCreateOrModifyDate());
									analystExceptionDocuments.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
									analystExceptionDocuments.setSyncTs(validateService.getSyncTs());
									analystExceptionDocumentRepository.saveAndFlush(analystExceptionDocuments);
									
								}else { // update with new document
								
									Optional<AnalystExceptionDocuments> isPresentAnalystExceptionDocument = analystExceptionDocumentRepository.findByAnalystExceptionDetailIdAndDocumentIdAndStatus(analystExceptionDetails.getId(), validateService.stringToLong(analystExceptionDocument.getDocumentId()),CommonStatus.ACTIVE);
									if(isPresentAnalystExceptionDocument.isPresent()) {
										throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);						
									}
									
									AnalystExceptionDocuments analystExceptionDocuments = new AnalystExceptionDocuments();
									analystExceptionDocuments.setTenantId(tenantId);
									analystExceptionDocuments.setDocumentId(validateService.stringToLong(analystExceptionDocument.getDocumentId()));
									analystExceptionDocuments.setAnalystExceptionDetail(analystExceptionDetails);
									analystExceptionDocuments.setStatus(CommonStatus.valueOf(analystExceptionDocument.getStatus()));
									analystExceptionDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
									analystExceptionDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
									analystExceptionDocuments.setSyncTs(validateService.getSyncTs());
									analystExceptionDocumentRepository.saveAndFlush(analystExceptionDocuments);
									
								}
							}
						}
						
					}else { // update with new exception type 
						Optional<AnalystExceptionDetails> isPresentAnalystExceptionDetail = analystExceptionDetailsRepository.findByAnalystDetailIdAndExceptionTypeId(analystDetails.getId(),exceptionType.getId());
						if(isPresentAnalystExceptionDetail.isPresent()) {
								throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.EXCEPTION_TYPE_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);						
						}
						
						UserProfileResponse userProfileResponse = validateService.validateUserProfileByUserId(tenantId, analystExceptionDetail.getAuthorizedUserId(), analystExceptionDetail.getAuthorizedUserName());

						validateExceptionAuthority(exceptionType.getId(),userProfileResponse.getId(), indexExcp);
						
						AnalystExceptionDetails analystExceptionDetails= new AnalystExceptionDetails();
						analystExceptionDetails.setTenantId(tenantId);
						analystExceptionDetails.setExceptionType(exceptionType);
						analystExceptionDetails.setAnalystDetail(analystDetails);
						analystExceptionDetails.setExceptionAuthorityId(userProfileResponse.getId());
						analystExceptionDetails.setExceptionDetails(analystExceptionDetail.getExceptionDetail());
						analystExceptionDetails.setStatus(CommonStatus.valueOf(analystExceptionDetail.getStatus()));
						analystExceptionDetails.setApprovalStatus(AnalystExceptionApprovalStatus.PENDING);
						analystExceptionDetails.setApprovalDate(validateService.getCreateOrModifyDate());
						analystExceptionDetails.setApprovalUser(LogginAuthentcation.getInstance().getUserName());
						analystExceptionDetails.setCreatedDate(validateService.getCreateOrModifyDate());
						analystExceptionDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
						analystExceptionDetails.setSyncTs(validateService.getSyncTs());
						analystExceptionDetails = analystExceptionDetailsRepository.saveAndFlush(analystExceptionDetails);
						
						if(analystExceptionDetail.getAnalystExceptionDocumentList() != null && !analystExceptionDetail.getAnalystExceptionDocumentList().isEmpty()) {
							
							for(DocumentUpdateResource analystExceptionDocument: analystExceptionDetail.getAnalystExceptionDocumentList()) {
								
								validateService.validateDocument(tenantId, analystExceptionDocument.getDocumentId(),analystExceptionDocument.getDocumentName(), ServicePoint.ANALYST_EXCEPTION_DETAILS,Constants.ORIGIN_CUSTOMER,  indexExcp);
								
								Optional<AnalystExceptionDocuments> isPresentAnalystExceptionDocument = analystExceptionDocumentRepository.findByAnalystExceptionDetailIdAndDocumentIdAndStatus(analystExceptionDetails.getId(), validateService.stringToLong(analystExceptionDocument.getDocumentId()), CommonStatus.ACTIVE);
								if(isPresentAnalystExceptionDocument.isPresent()) {
									throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, indexExcp);						
								}
								
								AnalystExceptionDocuments analystExceptionDocuments = new AnalystExceptionDocuments();
								analystExceptionDocuments.setTenantId(tenantId);
								analystExceptionDocuments.setDocumentId(validateService.stringToLong(analystExceptionDocument.getDocumentId()));
								analystExceptionDocuments.setAnalystExceptionDetail(analystExceptionDetails);
								analystExceptionDocuments.setStatus(CommonStatus.valueOf(analystExceptionDocument.getStatus()));
								analystExceptionDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
								analystExceptionDocuments.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
								analystExceptionDocuments.setSyncTs(validateService.getSyncTs());
								analystExceptionDocumentRepository.saveAndFlush(analystExceptionDocuments);
								
							}
						}
						
					}
					indexExcp++;
				}
			}
			
		return analystDetails;
	}
	
	private AnalystDetails setAnalystDetails(String tenantId,  AnalystDetails analystDetail){		
		analystDetail.setLeadId(analystDetail.getLeadInfo().getId());
		
		if("INTERNAL".equalsIgnoreCase(analystDetail.getAnalystType())) {
			UserProfileResponse analystUser = validateService.getUserProfileById(tenantId, analystDetail.getAnalystUserId());
			analystDetail.setAnalystUserName(analystUser.getUserName());
		}
		
		List<AnalystExceptionDetails> analystExceptionDetails = analystExceptionDetailsRepository.findByAnalystDetailId(analystDetail.getId());
		
		if(!analystExceptionDetails.isEmpty()) {				
			for(AnalystExceptionDetails analystExceptionDetail : analystExceptionDetails) {
				Optional<ExceptionType> exceptionType = exceptionTypeRepository.findById(analystExceptionDetail.getExceptionType().getId());			
				if(exceptionType.isPresent()) {
					analystExceptionDetail.setExceptionTypesId(exceptionType.get().getId());
					analystExceptionDetail.setExceptionTypesName(exceptionType.get().getName());
					
					UserProfileResponse authorityUser = validateService.getUserProfileById(tenantId, analystDetail.getAnalystUserId());
					analystExceptionDetail.setExceptionAuthorityUserName(authorityUser.getUserName());
					
					List<AnalystExceptionDocuments> analystExceptionDocuments = analystExceptionDocumentRepository.findByAnalystExceptionDetailId(analystExceptionDetail.getId());
					analystExceptionDetail.setAnalystExceptionDocuments(analystExceptionDocuments);					
				}
			}
			
			analystDetail.setAnalystExceptionDetails(analystExceptionDetails);
		}
		
		return analystDetail;
		
	}
	
	private ExceptionType validateExceptionType(Long id, String name) {
		
		Optional<ExceptionType> isPresentExceptionType = exceptionTypeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);		
		if(!isPresentExceptionType.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND),"exceptionTypeId");	
		return isPresentExceptionType.get();
	}
	
	@Override
	public Long sendToApproval(String tenantId , Long id) {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		List<AnalystExceptionDetails> analystExceptionDetailList = analystExceptionDetailsRepository.findByAnalystDetailIdAndApprovalStatus(id, AnalystExceptionApprovalStatus.PENDING);
		if(analystExceptionDetailList.isEmpty())
			throw new ValidateRecordException(environment.getProperty(PENDING_NOT_FOUND), "message");
		
		for(AnalystExceptionDetails analystExceptionDetail : analystExceptionDetailList) {
			
			Optional<AnalystExceptionWorkflow> isPresentAnalystExceptionWorkflow = analystExceptionWorkflowRepository.findByAnalystExceptionDetailIdAndWorkflowStatus(analystExceptionDetail.getId() , WorkflowStatus.ACTIVE);
			
			if(!isPresentAnalystExceptionWorkflow.isPresent()) {
				initiateWorkFlow(tenantId, analystExceptionDetail);
			}
		}
		
		return id;
	}
	
	public void initiateWorkFlow(String tenantId , AnalystExceptionDetails analystExceptionDetail) {
		WorkflowType workflowType=WorkflowType.ANALYST_EXCEPTION;
		if(workflowType!=null) {
			WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
			workflowInitiationRequestResource.setApprovalUser(analystExceptionDetail.getExceptionAuthorityId().toString());
			workflowInitiationRequestResource.setApprovWorkflowType(workflowType);
			Long processId = validateService.initiateMicroBprWorkFlow(workflowInitiationRequestResource,tenantId);
			
			AnalystExceptionWorkflow analystExceptionWorkflow = new AnalystExceptionWorkflow();
			analystExceptionWorkflow.setTenantId(tenantId);
			analystExceptionWorkflow.setWorkflowProcessId(processId);
			analystExceptionWorkflow.setWorkflowType(workflowType);
			analystExceptionWorkflow.setAnalystExceptionDetail(analystExceptionDetail);
			analystExceptionWorkflow.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			analystExceptionWorkflow.setWorkflowStatus(WorkflowStatus.ACTIVE);
			analystExceptionWorkflow.setCreatedDate(validateService.getCreateOrModifyDate());
			analystExceptionWorkflow.setSyncTs(validateService.getSyncTs());
			analystExceptionWorkflowRepository.saveAndFlush(analystExceptionWorkflow);
		}
		
	}	
	
	@Override
	public Long approveAnalystExceptionWorkFlow(String tenantId, Long analystExceptionDetailId, String workflowProcessId) {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		Optional<AnalystExceptionWorkflow> optionalAnalystExceptionWorkflow=analystExceptionWorkflowRepository.findByAnalystExceptionDetailIdAndWorkflowProcessId(analystExceptionDetailId, validateService.stringToLong(workflowProcessId));
		if(optionalAnalystExceptionWorkflow.isPresent()) {
			AnalystExceptionWorkflow analystExceptionWorkflow = optionalAnalystExceptionWorkflow.get();
			AnalystExceptionDetails analystExceptionDetails = analystExceptionDetailsRepository.getOne(analystExceptionDetailId);
			
			if(analystExceptionWorkflow.getWorkflowType().equals(WorkflowType.ANALYST_EXCEPTION)) {
				analystExceptionDetails.setApprovalStatus(AnalystExceptionApprovalStatus.APPROVED);
				analystExceptionDetails.setApprovalUser(LogginAuthentcation.getInstance().getUserName());
				analystExceptionDetails.setApprovalDate(validateService.getCreateOrModifyDate());
				analystExceptionDetailsRepository.saveAndFlush(analystExceptionDetails);
			}
			analystExceptionWorkflow.setWorkflowStatus(WorkflowStatus.COMPLETE);
			analystExceptionWorkflow.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			analystExceptionWorkflow.setModifiedDate(validateService.getCreateOrModifyDate());
			analystExceptionWorkflowRepository.saveAndFlush(analystExceptionWorkflow);
			
			validateService.approveWorkFlow(validateService.stringToLong(workflowProcessId), analystExceptionWorkflow.getWorkflowType(), LogginAuthentcation.getInstance().getUserName(),tenantId);
		}else
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "message");
		
		return analystExceptionDetailId;
	}
	
	@Override
	public Long rejectedAnalystExceptionWorkFlow(String tenantId, Long analystExceptionDetailId, String workflowProcessId) {
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		Optional<AnalystExceptionWorkflow> optionalAnalystExceptionWorkflow=analystExceptionWorkflowRepository.findByAnalystExceptionDetailIdAndWorkflowProcessId(analystExceptionDetailId, validateService.stringToLong(workflowProcessId));
		if(optionalAnalystExceptionWorkflow.isPresent()) {
			AnalystExceptionWorkflow analystExceptionWorkflow = optionalAnalystExceptionWorkflow.get();
			AnalystExceptionDetails analystExceptionDetails = analystExceptionDetailsRepository.getOne(analystExceptionDetailId);
			
			if(analystExceptionWorkflow.getWorkflowType().equals(WorkflowType.ANALYST_EXCEPTION)) {
				analystExceptionDetails.setApprovalStatus(AnalystExceptionApprovalStatus.REJECTED);
				analystExceptionDetails.setApprovalUser(LogginAuthentcation.getInstance().getUserName());
				analystExceptionDetails.setApprovalDate(validateService.getCreateOrModifyDate());
				analystExceptionDetailsRepository.saveAndFlush(analystExceptionDetails);
			}
			analystExceptionWorkflow.setWorkflowStatus(WorkflowStatus.REJECT);
			analystExceptionWorkflow.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			analystExceptionWorkflow.setModifiedDate(validateService.getCreateOrModifyDate());
			analystExceptionWorkflowRepository.saveAndFlush(analystExceptionWorkflow);
			validateService.abortedWorkFlow(validateService.stringToLong(workflowProcessId), analystExceptionWorkflow.getWorkflowType(), LogginAuthentcation.getInstance().getUserName(),tenantId);
		}else
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "message");
		
		return analystExceptionDetailId;
	}
	
	public void validateExceptionAuthority(Long exceptionTypeId, Long authorityId, Integer index) {
		
		Optional<ExceptionApprovalGroupType> isPresentExceptionApprovalGroupType = exceptionApprovalGroupTypeRepository.findByExceptionTypeIdAndStatus(exceptionTypeId ,CommonStatus.ACTIVE );
		if(!isPresentExceptionApprovalGroupType.isPresent())
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.EXCEPTION_TYPE_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, index);
		
		Optional<UserMappingDetails> isPresentAuthority = userMappingDetailsRepository.findByUserIdAndApprovalGroupIdAndStatus(authorityId, isPresentExceptionApprovalGroupType.get().getExceptionApprovalGroup().getId(), CommonStatus.ACTIVE);
		if(!isPresentAuthority.isPresent())
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.AUTHORITY_ID, ServicePoint.ANALYST_EXCEPTION_DETAILS, index);
		
	}

}
