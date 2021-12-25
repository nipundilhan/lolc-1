package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.FinancialStatementDocument;
import com.fusionx.lending.origination.domain.FinancialStatement;
import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.domain.FinancialStatementType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.FinancialStatementDocumentRepository;
import com.fusionx.lending.origination.repository.FinancialStatementTemplateRepository;
import com.fusionx.lending.origination.repository.FinancialStatementRepository;
import com.fusionx.lending.origination.repository.StatementTypeRepository;
import com.fusionx.lending.origination.resource.DocumentRefAddResource;
import com.fusionx.lending.origination.resource.FinanceStatementAddRequest;
import com.fusionx.lending.origination.resource.FinanceStatementUpdateRequest;
import com.fusionx.lending.origination.resource.UserProfileResponse;
import com.fusionx.lending.origination.service.FinanceStatementService;
import com.fusionx.lending.origination.service.ValidateService;
/**
 * API Service related to Financial Statement.
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        17-09-2021      -               -           Nipun Dilhan      Created
 * <p>
 *
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class FinancialStatementServiceImpl  extends MessagePropertyBase implements FinanceStatementService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private FinancialStatementTemplateRepository financialStatementTemplateRepository;	
	@Autowired
	private FinancialStatementRepository financialStatementRepository;	
	@Autowired
	private StatementTypeRepository statementTypeRepository;
	@Autowired
	private FinancialStatementDocumentRepository financialStatementDocumentRepository;
	
	@Autowired
	private ValidateService validateService;
	

	
	@Override
	public FinancialStatement addFinancialStatement(String tenantId,FinanceStatementAddRequest financeStatementAddRequest,String user) {
		
		Optional<Customer> customerOptional = customerRepository.findById(validateService.stringToLong(financeStatementAddRequest.getCustomerId()));
		if(!customerOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "customerId");
		}
		
		Optional<FinancialStatementTemplate> financialStatementTemplateOptional = financialStatementTemplateRepository.findById(validateService.stringToLong(financeStatementAddRequest.getStatementTemplateId()));
		if(!financialStatementTemplateOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.not-find"), MESSAGE);
		}
		if(!(financialStatementTemplateOptional.get().getName()).equals(financeStatementAddRequest.getStatementTemplateName())) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.name-incompatible"), MESSAGE);
		}
		if(!(CommonStatus.ACTIVE.toString()).equals(financialStatementTemplateOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.status-invalid"), MESSAGE);
		}
		
		Optional<FinancialStatementType> financialStatementTypeOptional = statementTypeRepository.findById(validateService.stringToLong(financeStatementAddRequest.getStatementTypeId()));
		if(!financialStatementTypeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.type.not-find"), MESSAGE);
		}
		if(!(financialStatementTypeOptional.get().getName()).equals(financeStatementAddRequest.getStatementTypeName())) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.type.name-incompatible"), MESSAGE);
		}
		if(!(CommonStatus.ACTIVE.toString()).equals(financialStatementTypeOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.status-invalid"), MESSAGE);
		}
		
		if(financialStatementTemplateOptional.get().getFinancialStatementType().getId() != financialStatementTypeOptional.get().getId()) {
			throw new ValidateRecordException(environment.getProperty("financialStatement.type.template-incompatible"), MESSAGE);
		}
		
		//UserProfileResponse userProfileResponse =validateService.validateUserProfileByUserId(tenantId,financeStatementAddRequest.getAuditByUserId(),financeStatementAddRequest.getAuditByUserName());
		
		Date fromDate = validationServiceStringToDate(financeStatementAddRequest.getFromDate());	
		Date toDate = validationServiceStringToDate(financeStatementAddRequest.getToDate());
		
		if(fromDate.compareTo(toDate) > 0) {
			throw new ValidateRecordException(environment.getProperty("comman.validate.from-less-than-to"), MESSAGE);
		}
		
		FinancialStatement financialStatement = new FinancialStatement();
		financialStatement.setTenantId(tenantId);
		financialStatement.setCustomer(customerOptional.get());
		financialStatement.setFinancialStatementType(financialStatementTypeOptional.get());
		financialStatement.setFinancialStatementTemplate(financialStatementTemplateOptional.get());
		financialStatement.setFromDate(fromDate != null ? dateToTimeStamp(fromDate) : null);
		financialStatement.setToDate(toDate != null ? dateToTimeStamp(toDate) : null);
//		financialStatement.setAuditedByUserId(userProfileResponse.getUserId());
		financialStatement.setAuditedBy(financeStatementAddRequest.getAuditByUserName());
		financialStatement.setNoOfReports(validateService.stringToLong(financeStatementAddRequest.getNoOfReports()));
		financialStatement.setStatus(CommonStatus.ACTIVE);
		financialStatement.setCreatedDate(validateService.getCreateOrModifyDate());
		financialStatement.setCreatedUser(user);
		financialStatement.setSyncTs(validateService.getCreateOrModifyDate());
		financialStatement =  financialStatementRepository.save(financialStatement);
	
		saveDocumentList(financeStatementAddRequest.getDocumentList(),financialStatement , user , tenantId);
		
		return financialStatement;
		
	}
	
	@Override
	public FinancialStatement updateFinancialStatement(Long id,String tenantId,FinanceStatementUpdateRequest financeStatementUpdateRequest,String user) {
		
		
		Optional<FinancialStatement> financialStatementOptional = financialStatementRepository.findById(id);
		if(!financialStatementOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "financialStatementId");
		}
		
		if(!(financialStatementOptional.get().getVersion()).equals(validateService.stringToLong(financeStatementUpdateRequest.getVersion()))) {

			throw new ValidateRecordException(environment.getProperty("common-invalid.versionV"), "message");
			
		}
		
		Optional<FinancialStatementTemplate> financialStatementTemplateOptional = financialStatementTemplateRepository.findById(validateService.stringToLong(financeStatementUpdateRequest.getStatementTemplateId()));
		if(!financialStatementTemplateOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.not-find"), MESSAGE);
		}
		if(!(financialStatementTemplateOptional.get().getName()).equals(financeStatementUpdateRequest.getStatementTemplateName())) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.name-incompatible"), MESSAGE);
		}
		if(!(CommonStatus.ACTIVE.toString()).equals(financialStatementTemplateOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.status-invalid"), MESSAGE);
		}
		
		Optional<FinancialStatementType> financialStatementTypeOptional = statementTypeRepository.findById(validateService.stringToLong(financeStatementUpdateRequest.getStatementTypeId()));
		if(!financialStatementTypeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.type.not-find"), MESSAGE);
		}
		if(!(financialStatementTypeOptional.get().getName()).equals(financeStatementUpdateRequest.getStatementTypeName())) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.name-incompatible"), MESSAGE);
		}
		if(!(CommonStatus.ACTIVE.toString()).equals(financialStatementTypeOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.status-invalid"), MESSAGE);
		}
		
		
		if(financialStatementTemplateOptional.get().getFinancialStatementType().getId() != financialStatementTypeOptional.get().getId()) {
			throw new ValidateRecordException(environment.getProperty("financialStatement.type.template-incompatible"), MESSAGE);
		}
		
		//UserProfileResponse userProfileResponse =validateService.validateUserProfileByUserId(tenantId,financeStatementUpdateRequest.getAuditByUserId(),financeStatementUpdateRequest.getAuditByUserName());
		
		Date fromDate = validationServiceStringToDate(financeStatementUpdateRequest.getFromDate());	
		Date toDate = validationServiceStringToDate(financeStatementUpdateRequest.getToDate());
		
		if(fromDate.compareTo(toDate) > 0) {
			throw new ValidateRecordException(environment.getProperty("comman.validate.from-less-than-to"), MESSAGE);
		}
		
		FinancialStatement financialStatement = financialStatementOptional.get();
		financialStatement.setFinancialStatementType(financialStatementTypeOptional.get());
		financialStatement.setFinancialStatementTemplate(financialStatementTemplateOptional.get());
		financialStatement.setFromDate(dateToTimeStamp(fromDate));
		financialStatement.setToDate(dateToTimeStamp(toDate));
//		financialStatement.setAuditedByUserId(userProfileResponse.getUserId());
		financialStatement.setAuditedBy(financeStatementUpdateRequest.getAuditByUserName());
		financialStatement.setNoOfReports(validateService.stringToLong(financeStatementUpdateRequest.getNoOfReports()));
		financialStatement.setStatus(CommonStatus.ACTIVE);
		financialStatement.setModifiedDate(validateService.getCreateOrModifyDate());
		financialStatement.setModifiedUser(user);
		
		financialStatement =  financialStatementRepository.save(financialStatement);

		
		return financialStatement;		
	}
	
	@Override
	public FinancialStatement updateDocuments(List<DocumentRefAddResource> docList ,Long financialStatementId, String user , String tenantId) {
		
		Optional<FinancialStatement> financialStatementOptional = financialStatementRepository.findById(financialStatementId);
		if(!financialStatementOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		}
		
		if(	(docList != null) && (!docList.isEmpty())) {
			int index = 0;
			for(DocumentRefAddResource docResource :docList) {
//				validateService.validateDocument(tenantId, docResource.getDocumentId(),docResource.getDocumentName(),ServicePoint.FINANCIAL_STATEMENT,Constants.ORIGIN_CUSTOMER, index);
				FinancialStatementDocument financialStatementDocument = null;
				if(docResource.getId()!=null) {
					Optional<FinancialStatementDocument> financialStatementDocumentOptional = financialStatementDocumentRepository.findById(validateService.stringToLong(docResource.getId()));
					if(!financialStatementDocumentOptional.isPresent()) {
						throw new DetailListValidateException(environment.getProperty("financialStatment.document.not-find"),ServiceEntity.ID, ServicePoint.FINANCIAL_STATEMENT, index);
					}
					if(!(financialStatementDocumentOptional.get().getVersion()).equals(validateService.stringToLong(docResource.getVersion()))) {
						throw new DetailListValidateException(environment.getProperty("common-invalid.versionV"),ServiceEntity.VERSION, ServicePoint.FINANCIAL_STATEMENT, index);
		
						
					}
					financialStatementDocument = financialStatementDocumentOptional.get();
				
				}else {
					
					financialStatementDocument = new FinancialStatementDocument();
					financialStatementDocument.setTenantId(tenantId);
					financialStatementDocument.setFinancialStatement(financialStatementOptional.get());
					financialStatementDocument.setCreatedDate(validateService.getCreateOrModifyDate());
					financialStatementDocument.setCreatedUser(user);
				}
				
				
				saveOrUpdateDocument(financialStatementDocument,docResource, user , tenantId);
				index = index+1;
			}
		}
		
		return financialStatementOptional.get();
	}
	
	
	public void saveDocumentList(List<DocumentRefAddResource> docList ,FinancialStatement financialStatement, String user , String tenantId) {
		if(	(docList != null) && (!docList.isEmpty())) {
			int index = 0;
			for(DocumentRefAddResource docResource :docList) {
				//validateService.validateDocument(tenantId, docResource.getDocumentId(),docResource.getDocumentName(),ServicePoint.FINANCIAL_STATEMENT,Constants.ORIGIN_CUSTOMER, index);
					
				FinancialStatementDocument financialStatementDocument = new FinancialStatementDocument();
				financialStatementDocument.setTenantId(tenantId);
				financialStatementDocument.setFinancialStatement(financialStatement);
				financialStatementDocument.setCreatedDate(validateService.getCreateOrModifyDate());
				financialStatementDocument.setCreatedUser(user);
						
				saveOrUpdateDocument(financialStatementDocument,docResource, user , tenantId);
				index = index+1;
			}
		}
		
	}

	public void saveOrUpdateDocument(FinancialStatementDocument fsd ,DocumentRefAddResource documentAddResource,String user , String tenantId) {
		
		FinancialStatementDocument financialStatementDocument = fsd;
		financialStatementDocument.setDocumentId(validateService.stringToLong(documentAddResource.getDocumentId()));
		financialStatementDocument.setDocumentName(documentAddResource.getDocumentName());
		financialStatementDocument.setDocumentRefId(validateService.stringToLong(documentAddResource.getDocumentRefId()));
		financialStatementDocument.setStatus(CommonStatus.ACTIVE);

		financialStatementDocument.setModifiedDate(validateService.getCreateOrModifyDate());
		financialStatementDocument.setModifiedUser(user);
		financialStatementDocument.setSyncTs(validateService.getCreateOrModifyDate());
			
		financialStatementDocumentRepository.save(financialStatementDocument);
	}
	
	
	
	public Date validationServiceStringToDate(String date){
		if(date != null) {
			return validateService.formatDate(date);
		}else {
			return null;
		}
	}
	
	public Timestamp dateToTimeStamp(Date dt) {
		if(dt != null) {
			return new Timestamp(dt.getTime());
		}else {
	        Calendar calendar = Calendar.getInstance();
	        java.util.Date now = calendar.getTime();
	        return new Timestamp(now.getTime());
		}
	}
}
