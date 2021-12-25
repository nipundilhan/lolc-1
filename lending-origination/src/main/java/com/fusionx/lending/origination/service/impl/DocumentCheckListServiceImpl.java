package com.fusionx.lending.origination.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.DocumentCheckList;
import com.fusionx.lending.origination.domain.DocumentCheckListDetails;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.YesNo;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.DocumentCheckListDetailsRepository;
import com.fusionx.lending.origination.repository.DocumentCheckListRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.resource.DocumentAddResource;
import com.fusionx.lending.origination.resource.DocumentCheckListAddResource;
import com.fusionx.lending.origination.resource.DocumentCheckListUpdateResource;
import com.fusionx.lending.origination.resource.DocumentTypeResponse;
import com.fusionx.lending.origination.resource.DocumentUpdateResource;
import com.fusionx.lending.origination.service.DocumentCheckListService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * API Service related to Cultivation Income Expenses.
 *
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
 * 1        30-09-2021      	             FXL-998    Dilhan                    Created
 * <p>
 *
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class DocumentCheckListServiceImpl extends MessagePropertyBase implements  DocumentCheckListService{
	
	@Autowired
	private DocumentCheckListRepository documentCheckListRepository;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	@Autowired
	private DocumentCheckListDetailsRepository documentCheckListDetailsRepository;
	
	@Autowired
	private ValidateService validateService;

	@Override
	public List<DocumentCheckList> getAll() {
		List<DocumentCheckList> documentCheckList = documentCheckListRepository.findAll();
		List<DocumentCheckList> documentCheckListAll = new ArrayList<>();
		List<DocumentCheckListDetails> documentCheckListDetails = new ArrayList<>();
		
		for(DocumentCheckList item : documentCheckList) {
			documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(item.getId());
			item.setDocumentCheckListDetails(documentCheckListDetails);
			documentCheckListAll.add(item);
		}
		return documentCheckListAll;
	}

	@Override
	public Optional<DocumentCheckList> getById(Long id) {
		Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListRepository.findById(id);
		if (isPresentDocumentCheckList.isPresent()) {
			DocumentCheckList documentCheckList = isPresentDocumentCheckList.get();
			List<DocumentCheckListDetails> documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(documentCheckList.getId());
			documentCheckList.setDocumentCheckListDetails(documentCheckListDetails);
			return isPresentDocumentCheckList;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<DocumentCheckList> getByStatus(String status) {
		List<DocumentCheckList> documentCheckList = documentCheckListRepository.findByStatus(CommonStatus.valueOf(status));
		List<DocumentCheckList> documentCheckListAll = new ArrayList<>();
		List<DocumentCheckListDetails> documentCheckListDetails = new ArrayList<>();
		
		for (DocumentCheckList item : documentCheckList) {
			documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(item.getId());
			item.setDocumentCheckListDetails(documentCheckListDetails);
			documentCheckListAll.add(item);
		}
		return documentCheckListAll;
	}

	@Override
	public DocumentCheckList addDocumentCheckList(String tenantId,
			DocumentCheckListAddResource documentCheckListAddResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<LeadInfo> leadInfo = leadInfoRepository.findByIdAndStatus(validateService.stringToLong(documentCheckListAddResource.getLeadId()), CommonStatus.ACTIVE.toString());
		if (!leadInfo.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("invalid.leadId"), "leadId");
		}
		DocumentCheckList documentCheckList = new DocumentCheckList();
		documentCheckList.setTenantId(tenantId);
		documentCheckList.setLeadInfo(leadInfo.get());
		documentCheckList.setDescription(documentCheckListAddResource.getDescription());
		documentCheckList.setDocumentRefNo(documentCheckListAddResource.getDocumentRefNo());
		DocumentTypeResponse documentTypeResponse = validateService.validateDocumentType(tenantId, documentCheckListAddResource.getDocumentCheckListDetId(), documentCheckListAddResource.getDocumentTypeName());
		if(documentTypeResponse != null) {
			documentCheckList.setDocumentTypeId(validateService.stringToLong(documentCheckListAddResource.getDocumentCheckListDetId()));
		}
		
		if(YesNo.YES.equals(YesNo.valueOf(documentCheckListAddResource.getCollected()))) {
			documentCheckList.setIsCollected(YesNo.valueOf(documentCheckListAddResource.getCollected()));
			
			if(documentCheckListAddResource.getCollectedDate() == null || documentCheckListAddResource.getCollectedDate().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty("collected.date"), "collectedDate");
			}else {
				documentCheckList.setCollectedDate(formatDate(documentCheckListAddResource.getCollectedDate()));
			}
		}
		
		else {
			documentCheckList.setIsCollected(YesNo.NO);
			documentCheckList.setCollectedDate(null);
		}
		
		documentCheckList.setIsMandatory(YesNo.valueOf(documentCheckListAddResource.getCollected()));
		documentCheckList.setStatus(CommonStatus.valueOf(documentCheckListAddResource.getStatus()));
		documentCheckList.setCreatedDate(validateService.getCreateOrModifyDate());
		documentCheckList.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		documentCheckList.setCollectedBy(LogginAuthentcation.getInstance().getUserName());
		documentCheckList.setSyncTs(validateService.getCreateOrModifyDate());
		
		documentCheckList = documentCheckListRepository.save(documentCheckList);
		

		Integer index = 0;
		if (documentCheckListAddResource.getDocumentDetails() != null && !documentCheckListAddResource.getDocumentDetails().isEmpty()) {

			for (DocumentAddResource documentAddResource : documentCheckListAddResource.getDocumentDetails()) {

				validateService.validateDocument(tenantId, documentAddResource.getDocumentId(),documentAddResource.getDocumentName(),
						ServicePoint.DOCUMENT_CHECKLIST_DETAILS, Constants.ORIGIN_CUSTOMER, index);

				Optional<DocumentCheckListDetails> isPresentDocumentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListIdAndDocumentIdAndStatus(documentCheckList.getId(),
								validateService.stringToLong(documentAddResource.getDocumentId()),CommonStatus.ACTIVE);
				if (isPresentDocumentCheckListDetails.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "documentId");
				}

				DocumentCheckListDetails documentCheckListDetails = new DocumentCheckListDetails();
				documentCheckListDetails.setTenantId(tenantId);
				documentCheckListDetails
						.setDocumentId(validateService.stringToLong(documentAddResource.getDocumentId()));
				documentCheckListDetails.setStatus(CommonStatus.valueOf(documentAddResource.getStatus()));
				documentCheckListDetails.setCreatedDate(validateService.getCreateOrModifyDate());
				documentCheckListDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
				
				documentCheckListDetails.setDocumentId(validateService.stringToLong(documentAddResource.getDocumentId()));
				documentCheckListDetails.setStatus(CommonStatus.valueOf(documentAddResource.getStatus()));
				documentCheckListDetails.setDocumentCheckList(documentCheckList);
				documentCheckListDetails.setSyncTs(validateService.getCreateOrModifyDate());
				
				documentCheckListDetailsRepository.saveAndFlush(documentCheckListDetails);
			}
		}

		index++;
		return documentCheckList;
	}

	@Override
	public DocumentCheckList updateDocumentCheckList(String tenantId, Long id,
			DocumentCheckListUpdateResource documentCheckListUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListRepository.findById(id);

		if (!isPresentDocumentCheckList.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		
		if(!isPresentDocumentCheckList.get().getVersion().equals(Long.parseLong(documentCheckListUpdateResource.getVersion())))
		    throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), VERSION);
		
		Optional<LeadInfo> leadInfo = leadInfoRepository.findByIdAndStatus(validateService.stringToLong(documentCheckListUpdateResource.getLeadId()), CommonStatus.ACTIVE.toString());
		if (!leadInfo.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "leadId");
		}
		
		
		DocumentCheckList documentCheckList = isPresentDocumentCheckList.get();
		
		documentCheckList
		.setStatus(CommonStatus.valueOf(documentCheckListUpdateResource.getStatus()));
       
        documentCheckList.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        documentCheckList.setModifiedDate(validateService.getCreateOrModifyDate());
        documentCheckList.setSyncTs(validateService.getCreateOrModifyDate());
        documentCheckList.setLeadInfo(leadInfo.get());
		documentCheckList.setDescription(documentCheckListUpdateResource.getDescription());
		documentCheckList.setDocumentRefNo(documentCheckListUpdateResource.getDocumentRefNo());
		if(YesNo.YES.equals(YesNo.valueOf(documentCheckListUpdateResource.getCollected()))) {
			documentCheckList.setIsCollected(YesNo.valueOf(documentCheckListUpdateResource.getCollected()));
			
			if(documentCheckListUpdateResource.getCollectedDate() == null || documentCheckListUpdateResource.getCollectedDate().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty("collected.date"), "collectedDate");
			}else {
				documentCheckList.setCollectedDate(formatDate(documentCheckListUpdateResource.getCollectedDate()));
			}
		}
		
		else {
			documentCheckList.setIsCollected(YesNo.NO);
			documentCheckList.setCollectedDate(null);
		}
        
        documentCheckList = documentCheckListRepository.saveAndFlush(documentCheckList);
		
    	if (documentCheckListUpdateResource.getDocumentDetails() != null&& !documentCheckListUpdateResource.getDocumentDetails().isEmpty()) {
			Integer index = 0;
			for (DocumentUpdateResource documentUpdateResource : documentCheckListUpdateResource.getDocumentDetails()) {

				validateService.validateDocument(tenantId, documentUpdateResource.getDocumentId(),documentUpdateResource.getDocumentName(),
						ServicePoint.DOCUMENT_CHECKLIST_DETAILS, Constants.ORIGIN_CUSTOMER, index);

				// update existing document
				if (documentUpdateResource.getId() != null && !documentUpdateResource.getId().isEmpty()) {

					Optional<DocumentCheckListDetails> isPresentDocumentCheckListDetails = documentCheckListDetailsRepository.findById(validateService.stringToLong(documentUpdateResource.getId()));
					if (!isPresentDocumentCheckListDetails.isPresent())
						throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND),ServiceEntity.ID, ServicePoint.DOCUMENT_CHECKLIST_DETAILS, index);

					Optional<DocumentCheckListDetails> isDuplicateDocumentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListIdAndDocumentIdAndStatusAndIdNotIn(
							documentCheckList.getId(),validateService.stringToLong(documentUpdateResource.getDocumentId()),
									CommonStatus.ACTIVE, isPresentDocumentCheckListDetails.get().getId());

					if (isDuplicateDocumentCheckListDetails.isPresent()) {
						throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE),ServiceEntity.DOCUMENT_ID, ServicePoint.DOCUMENT_CHECKLIST_DETAILS, index);
					}
					
					if(documentUpdateResource.getVersion() == null || documentUpdateResource.getVersion().isEmpty()) {	
						throw new DetailListValidateException(environment.getProperty(BLANK_VERSION),ServiceEntity.VERSION, ServicePoint.DOCUMENT_CHECKLIST_DETAILS, index);
					}

					if (!isPresentDocumentCheckListDetails.get().getVersion()
							.equals(Long.parseLong(documentUpdateResource.getVersion())))
						throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE),ServiceEntity.VERSION, ServicePoint.DOCUMENT_CHECKLIST_DETAILS, index);

					DocumentCheckListDetails documentCheckListDetails = isPresentDocumentCheckListDetails.get();
					documentCheckListDetails.setTenantId(tenantId);
					documentCheckListDetails
							.setDocumentId(validateService.stringToLong(documentUpdateResource.getDocumentId()));
					documentCheckListDetails.setDocumentCheckList(documentCheckList);
					documentCheckListDetails.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
					documentCheckListDetails.setModifiedDate(validateService.getCreateOrModifyDate());
					documentCheckListDetails.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
					documentCheckListDetails.setSyncTs(validateService.getSyncTs());
					documentCheckListDetailsRepository.saveAndFlush(documentCheckListDetails);

				} else {
					Optional<DocumentCheckListDetails> isPresentCultivationIncomeDocument = documentCheckListDetailsRepository
							.findByDocumentCheckListIdAndDocumentIdAndStatus(documentCheckList.getId(),
									validateService.stringToLong(documentUpdateResource.getDocumentId()),
									CommonStatus.ACTIVE);
					if (isPresentCultivationIncomeDocument.isPresent()) {
						throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE),ServiceEntity.DOCUMENT_ID, ServicePoint.DOCUMENT_CHECKLIST_DETAILS, index);
					}

					DocumentCheckListDetails documentCheckListDetails = new DocumentCheckListDetails();
					documentCheckListDetails.setTenantId(tenantId);
					documentCheckListDetails
							.setDocumentId(validateService.stringToLong(documentUpdateResource.getDocumentId()));
					documentCheckListDetails.setDocumentCheckList(documentCheckList);
					documentCheckListDetails.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
					documentCheckListDetails.setCreatedDate(validateService.getCreateOrModifyDate());
					documentCheckListDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
					documentCheckListDetails.setSyncTs(validateService.getSyncTs());
					documentCheckListDetailsRepository.saveAndFlush(documentCheckListDetails);
				}
			}
    	}
		
		return documentCheckList;
	}

	
	private Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		
	}
}
