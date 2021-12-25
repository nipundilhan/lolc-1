package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.FinancialStatement;
import com.fusionx.lending.origination.domain.FinancialStatementDetail;
import com.fusionx.lending.origination.domain.FinancialStatementDetailNote;
import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.domain.FinancialStatementTemplateDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.CommonYesNoStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ListRecordPrimitiveValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.FinancialStatementDetailNoteRepository;
import com.fusionx.lending.origination.repository.FinancialStatementDetailRepository;
import com.fusionx.lending.origination.repository.FinancialStatementRepository;
import com.fusionx.lending.origination.repository.FinancialStatementTemplateDetailsRepository;
import com.fusionx.lending.origination.repository.FinancialStatementTemplateRepository;
import com.fusionx.lending.origination.resource.FinancialStatementDetailAddResource;
import com.fusionx.lending.origination.resource.FinancialStatementDetailMainResource;
import com.fusionx.lending.origination.resource.FinancialStatementDetailNoteRequest;
import com.fusionx.lending.origination.resource.FinancialStatementDetailUpdateMainResource;
import com.fusionx.lending.origination.resource.FinancialStatementDetailUpdateResource;
import com.fusionx.lending.origination.service.FinancialStatementDetailService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * API Service related to financial statement detail
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
 * 1        20-09-2021      -               FXL-818     Nipun Dilhan      Created
 * <p>
 *
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FinancialStatementDetailServiceImpl   extends MessagePropertyBase implements FinancialStatementDetailService{

	
	@Autowired
	private FinancialStatementTemplateDetailsRepository financialStatementTemplateDetailsRepository;
	@Autowired
	private FinancialStatementTemplateRepository financialStateTemplateRepository;
	@Autowired
	private FinancialStatementDetailRepository financialStatementDetailRepository;
	@Autowired
	private FinancialStatementDetailNoteRepository financialStatementDetailNoteRepository;
	@Autowired
	private FinancialStatementRepository financialStatementRepository;
	
	@Autowired
	private ValidateService validateService;
	
	
	@Override
	public FinancialStatement addFinancialStatementDetail(FinancialStatementDetailMainResource financialStatementDetailAddResource,Long financialStatementId ,String user ,String tenantId ) {
				
		Optional<FinancialStatement> financialStatementOptional = financialStatementRepository.findById(financialStatementId);
		if(!financialStatementOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "financialStatementId");
		}

		List<FinancialStatementDetailAddResource> statementDetailList = financialStatementDetailAddResource.getStatementDetailList();
		
		if((statementDetailList != null) && (!statementDetailList.isEmpty())) {
			int index = 0;
			for(FinancialStatementDetailAddResource resource :	statementDetailList) {
				
				Optional<FinancialStatementTemplateDetails> financialStatementTemplateDetailOptional = financialStatementTemplateDetailsRepository.findById(validateService.stringToLong(resource.getFinancailStatementTemplateDetailId())); 
				if(!financialStatementTemplateDetailOptional.isPresent()) {
					throw new DetailListValidateException(environment.getProperty("financialStatment.template.detail.not-find"),ServiceEntity.FINANCIAL_STATEMENT_TEMPLATE, ServicePoint.FINANCIAL_STATEMENT_DETAIL, index);
				}
				
				FinancialStatementDetail financialStatementDetail = new FinancialStatementDetail();
				financialStatementDetail.setTenantId(tenantId);
				financialStatementDetail.setFinancialStatement(financialStatementOptional.get());
				financialStatementDetail.setFinancialStatementTemplateDetail(financialStatementTemplateDetailOptional.get());
				
				if(resource.getAmount() == null || resource.getAmount().isEmpty()) {
					throw new ListRecordPrimitiveValidateException(environment.getProperty("common.invalid-value"),
							null, "statementDetailList", index,"amount");
				}
				
				financialStatementDetail.setAmount(validateService.stringToBigDecimal(resource.getAmount()));
//				financialStatementDetail.setComment(null);
				financialStatementDetail.setStatus(CommonStatus.ACTIVE);
				financialStatementDetail.setCreatedDate(validateService.getCreateOrModifyDate());
				financialStatementDetail.setCreatedUser(user);
				financialStatementDetail.setSyncTs(validateService.getCreateOrModifyDate());
				financialStatementDetail = financialStatementDetailRepository.save(financialStatementDetail);
				
				
				
				saveNoteListStatementDetail(resource.getNoteList(),user,tenantId,financialStatementDetail,index);				
				index++;
			}
			
		}
		
		return financialStatementOptional.get();
	}
	
	
	@Override
	public FinancialStatement updateFinancialStatementDetail(Long financialStatementId,FinancialStatementDetailUpdateMainResource financialStatementDetailUpdateMainResource,String tenantId,String user) {
		
		Optional<FinancialStatement> financialStatementOptional = financialStatementRepository.findById(financialStatementId);
		if(!financialStatementOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "financialStatementId");
		}
		
		List<FinancialStatementDetailUpdateResource> statementDetailList = financialStatementDetailUpdateMainResource.getStatementDetailList();
		if((statementDetailList != null) && (!statementDetailList.isEmpty())) {
			
			
			int indexRoot = 0;
			for(FinancialStatementDetailUpdateResource resource :	statementDetailList) {
				FinancialStatementDetail financialStatementDetail = null;
				if(resource.getFinancailStatementDetailId() != null) {
					
					if(resource.getFinancailStatementDetailId() == null || resource.getFinancailStatementDetailId().isEmpty()) {
						throw new ListRecordPrimitiveValidateException(environment.getProperty("common.invalid-value"),
								null, "statementDetailList", indexRoot,"financialStatementDetailId");
					}
					
					Optional<FinancialStatementDetail> financialStatementDetailOptional = financialStatementDetailRepository.findById(validateService.stringToLong(resource.getFinancailStatementDetailId()));
				
					if(!financialStatementDetailOptional.isPresent()) {
						throw new ListRecordPrimitiveValidateException(environment.getProperty(RECORD_NOT_FOUND),
								null, "statementDetailList", indexRoot,"financialStatementDetailId");
					}
					if(!(resource.getFinancailStatementTemplateDetailId()).equals(financialStatementDetailOptional.get().getFinancialStatementTemplateDetail().getId().toString())) {
						throw new ListRecordPrimitiveValidateException(environment.getProperty("common.invalid-value"),
								null, "statementDetailList", indexRoot,"financialStatementTemplateDetailId");
					}

					
					if(resource.getVersion() == null || resource.getVersion().isEmpty()) {
						throw new ListRecordPrimitiveValidateException(environment.getProperty("common.invalid-value"),
								null, "statementDetailList", indexRoot,"version");
					}
					if(!(resource.getVersion()).equals(financialStatementDetailOptional.get().getVersion().toString())) {
						throw new ListRecordPrimitiveValidateException(environment.getProperty("common.invalid-value"),
								null, "statementDetailList", indexRoot,"version");
					}
					
					
					financialStatementDetail = financialStatementDetailOptional.get();
				
				}else {
					Optional<FinancialStatementTemplateDetails> financialStatementTemplateDetailOptional = financialStatementTemplateDetailsRepository.findById(validateService.stringToLong(resource.getFinancailStatementTemplateDetailId())); 
					if(!financialStatementTemplateDetailOptional.isPresent()) {
						throw new ListRecordPrimitiveValidateException(environment.getProperty(RECORD_NOT_FOUND),
								null, "statementDetailList", indexRoot,"financialStatementTemplateDetailId");
					}
					
					
					financialStatementDetail = new FinancialStatementDetail();
					financialStatementDetail.setFinancialStatement(financialStatementOptional.get());
					financialStatementDetail.setFinancialStatementTemplateDetail(financialStatementTemplateDetailOptional.get());
					financialStatementDetail.setTenantId(tenantId);
					financialStatementDetail.setStatus(CommonStatus.ACTIVE);
					financialStatementDetail.setCreatedDate(validateService.getCreateOrModifyDate());
					financialStatementDetail.setCreatedUser(user);
				}
				
				
				if(resource.getAmount() == null || resource.getAmount().isEmpty()) {
					throw new ListRecordPrimitiveValidateException(environment.getProperty("common.invalid-value"),
							null, "statementDetailList", indexRoot,"amount");
				}
				
				financialStatementDetail.setAmount(validateService.stringToBigDecimal(resource.getAmount()));
//				financialStatementDetail.setComment(null);
				financialStatementDetail.setStatus(CommonStatus.ACTIVE);
				financialStatementDetail.setModifiedDate(validateService.getCreateOrModifyDate());
				financialStatementDetail.setModifiedUser(user);
				financialStatementDetail.setSyncTs(validateService.getCreateOrModifyDate());
				financialStatementDetail = financialStatementDetailRepository.save(financialStatementDetail);
				
				indexRoot++;
				
			}
		}
		
		return financialStatementOptional.get();
	
	
	}
	
	@Override
	public List<FinancialStatementDetail> getDetailsByFinancialStatementId(Long finanancialStatementId) {
			
		return  financialStatementDetailRepository.findAllByFinancialStatementId(finanancialStatementId);
	
	}
	
	@Override
	public FinancialStatementDetail updateNoteListStatementDetail(List<FinancialStatementDetailNoteRequest> noteList ,Long financialStatementDetailId,String user , String tenantId ){
		Optional<FinancialStatementDetail> financialStatementDetailOptional = financialStatementDetailRepository.findById(financialStatementDetailId);
		
		if(!financialStatementDetailOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "financialStatementId");
		}
		FinancialStatementDetail fsd = financialStatementDetailOptional.get();
		saveOrUpdateNoteList(noteList,user,tenantId,fsd,-1);
		
		return financialStatementDetailOptional.get();
	}
		
	public void saveNoteListStatementDetail(List<FinancialStatementDetailNoteRequest> noteList ,String user , String tenantId,FinancialStatementDetail fsd ,int parentIndex){
		saveOrUpdateNoteList(noteList,user,tenantId,fsd ,parentIndex);
	}
	

	
	
	
	public void saveOrUpdateNoteList(List<FinancialStatementDetailNoteRequest> noteList ,String user , String tenantId,FinancialStatementDetail fsd , int parentIndex) {
		if((noteList != null) && (!noteList.isEmpty())) {
			
			List<FinancialStatementDetailNote> detailNoteList = new ArrayList<>();
			int index = 0;
			for(FinancialStatementDetailNoteRequest noteRequest :	noteList) {
				
				FinancialStatementDetailNote financialStatementDetailNote = null;
				String message = "  ";
				if(parentIndex >= 0) {
					message = "statement detail index - " + parentIndex + " ";
				}
				if(noteRequest.getNoteId() == null) {
					

					
					financialStatementDetailNote = new FinancialStatementDetailNote();
					financialStatementDetailNote.setTenantId(tenantId);
					financialStatementDetailNote.setCreatedDate(validateService.getCreateOrModifyDate());
					financialStatementDetailNote.setCreatedUser(user);
					financialStatementDetailNote.setFinancialStatementDetail(fsd);
				}else {
					
					if(noteRequest.getNoteId().isEmpty()) {
						throw new DetailListValidateException(message.concat(environment.getProperty("financialStatment.detail.note.id-empty")),ServiceEntity.NOTE_ID, ServicePoint.FINANCIAL_STATEMENT_DETAIL_NOTE, index);
					}
					
					Optional<FinancialStatementDetailNote> financialStatementDetailNoteOpional = financialStatementDetailNoteRepository.findById(validateService.stringToLong(noteRequest.getNoteId()));
					if(!financialStatementDetailNoteOpional.isPresent()) {
						throw new DetailListValidateException(message.concat(environment.getProperty("financialStatment.detail.note.not-find")),ServiceEntity.NOTE_ID, ServicePoint.FINANCIAL_STATEMENT_DETAIL_NOTE, index);
					}
					financialStatementDetailNote = financialStatementDetailNoteOpional.get();
					
					if(noteRequest.getVersion() == null || noteRequest.getVersion().isEmpty()) {
						throw new DetailListValidateException(message.concat(environment.getProperty("common.invalid-value")),ServiceEntity.VERSION, ServicePoint.FINANCIAL_STATEMENT_DETAIL_NOTE, index);
					}
					if(!(noteRequest.getVersion()).equals(financialStatementDetailNote.getVersion().toString())) {
						throw new DetailListValidateException(message.concat(environment.getProperty("common.invalid-value")),ServiceEntity.VERSION, ServicePoint.FINANCIAL_STATEMENT_DETAIL_NOTE, index);
					}
					
					financialStatementDetailNote.setModifiedDate(validateService.getCreateOrModifyDate());
					financialStatementDetailNote.setModifiedUser(user);
				}
				
				
				financialStatementDetailNote.setItemName(noteRequest.getItemName());
				financialStatementDetailNote.setDescription(noteRequest.getDescription());
				if( (noteRequest.getNoOfItem() != null) && (!noteRequest.getNoOfItem().isEmpty())) {
					financialStatementDetailNote.setNoOfItems( validateService.stringToLong(noteRequest.getNoOfItem()) );
				}
				financialStatementDetailNote.setValue(validateService.stringToBigDecimal(noteRequest.getValue()));
				financialStatementDetailNote.setStatus(CommonStatus.ACTIVE);

				financialStatementDetailNote.setSyncTs(validateService.getCreateOrModifyDate());
				detailNoteList.add(financialStatementDetailNote);
				
				index++;
				
			}
			
			if((!detailNoteList.isEmpty())) {
				financialStatementDetailNoteRepository.saveAll(detailNoteList);
			}
			
			
			
		}
	}
	
	
	public FinancialStatement addFinancialStatementDetailWithValidations(FinancialStatementDetailMainResource financialStatementDetailAddResource,Long financialStatementId ,String user ,String tenantId ) {
		
		Optional<FinancialStatementTemplate> financialStatementTemplateOptional = financialStateTemplateRepository.findById(validateService.stringToLong(financialStatementDetailAddResource.getFinancailStatementTemplateId()));
		if(!financialStatementTemplateOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("financialStatment.template.not-find"), MESSAGE);
		}
		
		
		Optional<FinancialStatement> financialStatementOptional = financialStatementRepository.findById(financialStatementId);
		if(!financialStatementOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "financialStatementId");
		}
		
		List<FinancialStatementTemplateDetails> parentList = financialStatementTemplateDetailsRepository.findAllByFinancialStatementAndParentId(financialStatementTemplateOptional.get(), 0l);
		List<FinancialStatementDetailAddResource> statementDetailList = financialStatementDetailAddResource.getStatementDetailList();
		
		while(!parentList.isEmpty()) {
			List<Long> allChildhildIdList = new ArrayList<>();
			List<FinancialStatementTemplateDetails> allChildhildList = new ArrayList<>();
			
			for(FinancialStatementTemplateDetails parent :	parentList) {
				FinancialStatementDetailAddResource parentMatchRequest = null;
				
				
				for(FinancialStatementDetailAddResource request : statementDetailList) {
					if((parent.getId().toString()).equals(request.getFinancailStatementTemplateDetailId().trim())) {
						parentMatchRequest = request;
						break;
					}
				}
				
				if(parentMatchRequest ==  null) {
					throw new ValidateRecordException(environment.getProperty("financialStatment.template.detail.not-find-in-request"), MESSAGE);
				}
				
				if((CommonYesNoStatus.YES.toString()).equals(parent.getTotalValueRequired()) && 
					(parentMatchRequest.getAmount() == null)) {
						//throw exception					
					
				}
				
				if((CommonYesNoStatus.YES.toString()).equals(parent.getAdditionalNoteRequired()) && 
					((parentMatchRequest.getNoteList()== null) ||  (parentMatchRequest.getNoteList().isEmpty()))) {
						//throw exception					
					
				}
				
				
				
				
				
				BigDecimal total = BigDecimal.ZERO;
				BigDecimal parentAmount = validateService.stringToBigDecimal(parentMatchRequest.getAmount());
				
				List<FinancialStatementTemplateDetails> childList = financialStatementTemplateDetailsRepository.findAllByFinancialStatementAndParentId(financialStatementTemplateOptional.get(), parent.getFinancialStatementLevels().getId());
				
				if((childList != null) && (!childList.isEmpty())) {
				
					FinancialStatementDetailAddResource childMatchRequest = null;
					for(FinancialStatementTemplateDetails child : childList) {
						allChildhildList.add(child);
						allChildhildIdList.add(child.getFinancialStatementLevels().getId());
						
						for(FinancialStatementDetailAddResource request : statementDetailList) {
							if((child.getId().toString()).equals(request.getFinancailStatementTemplateDetailId().trim())) {
								childMatchRequest = request;
								break;
							}
						}
						
						if(childMatchRequest ==  null) {
							//throw exception
						}
						
						total = total.add(validateService.stringToBigDecimal(childMatchRequest.getAmount()));
					}
	
					
					
					if(parentAmount.compareTo(total) != 0) {
						//throw exception
					}
				
				}
				
				
				FinancialStatementDetail financialStatementDetail = new FinancialStatementDetail();
				financialStatementDetail.setTenantId(tenantId);
				financialStatementDetail.setFinancialStatement(financialStatementOptional.get());
				financialStatementDetail.setFinancialStatementTemplateDetail(parent);
				financialStatementDetail.setAmount(parentAmount);
//				financialStatementDetail.setComment(null);
				financialStatementDetail.setStatus(CommonStatus.ACTIVE);
				financialStatementDetail.setCreatedDate(validateService.getCreateOrModifyDate());
				financialStatementDetail.setCreatedUser(user);
				financialStatementDetail.setModifiedDate(validateService.getCreateOrModifyDate());
				financialStatementDetail.setModifiedUser(user);
				financialStatementDetail.setSyncTs(validateService.getCreateOrModifyDate());
				financialStatementDetailRepository.save(financialStatementDetail);
				
				
				
				
				
			}
			
			parentList = allChildhildList;
					
		}
		
		return financialStatementOptional.get();
	}
	

	
}
