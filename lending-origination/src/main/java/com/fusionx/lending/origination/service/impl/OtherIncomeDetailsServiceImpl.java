package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.Constants;
/**
 * 	Other Income Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-78  	     FXL-777       Dilki        Created
 *    
 ********************************************************************************************************
*/
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.domain.OtherIncomeDetailDocuments;
import com.fusionx.lending.origination.domain.OtherIncomeDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.IncomeTypeEnum;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.SourceTypeEnum;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.IncomeSourceDetailsRepository;
import com.fusionx.lending.origination.repository.OtherIncomeCategoryRepository;
import com.fusionx.lending.origination.repository.OtherIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.OtherIncomeDocumentsRepository;
import com.fusionx.lending.origination.resource.DocumentAddResource;
import com.fusionx.lending.origination.resource.DocumentUpdateResource;
import com.fusionx.lending.origination.resource.OtherIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeDetailsUpdateResource;
import com.fusionx.lending.origination.service.OtherIncomeDetailsService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class OtherIncomeDetailsServiceImpl extends MessagePropertyBase implements OtherIncomeDetailsService {

	@Autowired
	private OtherIncomeDetailsRepository otherIncomeDetailsRepository;

	@Autowired
	private IncomeSourceDetailsRepository incomeSourceDetailsRepository;

	@Autowired
	private OtherIncomeCategoryRepository otherIncomeCategoryRepository;

	@Autowired
	private OtherIncomeDocumentsRepository otherIncomeDocumentsRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private RemoteService remoteService;

	@Override
	public List<OtherIncomeDetails> getAll() {
		return otherIncomeDetailsRepository.findAll();
	}

	@Override
	public Optional<OtherIncomeDetails> getById(Long id) {
		Optional<OtherIncomeDetails> isPresentOtherIncomeDetails = otherIncomeDetailsRepository.findById(id);
		if (isPresentOtherIncomeDetails.isPresent()) {
			return Optional.ofNullable(isPresentOtherIncomeDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<OtherIncomeDetails> getByStatus(String status) {
		return otherIncomeDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public OtherIncomeDetails addOtherIncomeDetails(String tenantId,
			OtherIncomeDetailsAddResource otherIncomeDetailsAddResource) {

		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository
				.findById(validationService.stringToLong(otherIncomeDetailsAddResource.getIncomeSourceDetailsId()));
		if (!isPresentIncomeSourceDetails.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "incomeSourceDetailId");
		if (!IncomeTypeEnum.OTHER.toString().equals(isPresentIncomeSourceDetails.get().getIncomeType().toString()))
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_MATCH), "incomeSourceDetailId");

		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryRepository
				.findById(validationService.stringToLong(otherIncomeDetailsAddResource.getOtherIncomeTypeId()));
		if (!isPresentOtherIncomeCategory.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "otherIncomeTypeId");

		OtherIncomeDetails otherIncomeDetails = new OtherIncomeDetails();
		otherIncomeDetails.setTenantId(tenantId);
		otherIncomeDetails.setOtherIncomeTypeId(isPresentOtherIncomeCategory.get());
		otherIncomeDetails.setIncomeSourceDetailsId(isPresentIncomeSourceDetails.get());
		otherIncomeDetails.setSourceType(SourceTypeEnum.valueOf(otherIncomeDetailsAddResource.getSourceType()));
		otherIncomeDetails.setBehaviourCode(otherIncomeDetailsAddResource.getBehaviourCode());
		otherIncomeDetails
				.setBehaviourId(validationService.stringToLong(otherIncomeDetailsAddResource.getBehaviourId()));
		otherIncomeDetails.setIncomeLevelCode(otherIncomeDetailsAddResource.getIncomeLevelCode());
		otherIncomeDetails
				.setIncomeLevelId(validationService.stringToLong(otherIncomeDetailsAddResource.getIncomeLevelId()));
		otherIncomeDetails
				.setNoOfYearsEarned(validationService.stringToLong(otherIncomeDetailsAddResource.getNoOfYearsEarned()));
		otherIncomeDetails.setNoOfMonthsEarned(
				validationService.stringToLong(otherIncomeDetailsAddResource.getNoOfMonthsEarned()));
		otherIncomeDetails.setDescription(otherIncomeDetailsAddResource.getDescription());
		otherIncomeDetails.setStatus(CommonStatus.valueOf(otherIncomeDetailsAddResource.getStatus()));
		otherIncomeDetails.setSyncTs(validationService.getCreateOrModifyDate());

		otherIncomeDetailsRepository.save(otherIncomeDetails);

		if (otherIncomeDetailsAddResource.getOtherIncomeDocumentList() != null
				&& !otherIncomeDetailsAddResource.getOtherIncomeDocumentList().isEmpty()) {

			for (DocumentAddResource documentAddResource : otherIncomeDetailsAddResource.getOtherIncomeDocumentList()) {

				remoteService.existDocument(tenantId, documentAddResource.getDocumentId(), Constants.ORIGIN_CUSTOMER);

				Optional<OtherIncomeDetailDocuments> isPresentOtherIncomeDocument = otherIncomeDocumentsRepository
						.findByOtherIncomeDetailsIdAndDocumentIdAndStatus(otherIncomeDetails.getId(),
								validationService.stringToLong(documentAddResource.getDocumentId()),
								CommonStatus.ACTIVE);
				if (isPresentOtherIncomeDocument.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "documentId");
				}

				OtherIncomeDetailDocuments otherIncomeDocuments = new OtherIncomeDetailDocuments();
				otherIncomeDocuments.setTenantId(tenantId);
				otherIncomeDocuments.setDocumentId(validationService.stringToLong(documentAddResource.getDocumentId()));
				otherIncomeDocuments.setOtherIncomeDetails(otherIncomeDetails);
				otherIncomeDocuments.setStatus(CommonStatus.valueOf(documentAddResource.getStatus()));
				otherIncomeDocuments.setCreatedDate(validationService.getCreateOrModifyDate());
				otherIncomeDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
				otherIncomeDocuments.setSyncTs(validationService.getSyncTs());
				otherIncomeDocumentsRepository.saveAndFlush(otherIncomeDocuments);
			}
		}

		return otherIncomeDetails;

	}

	@Override
	public OtherIncomeDetails updateOtherIncomeDetails(String tenantId, Long id,
			OtherIncomeDetailsUpdateResource otherIncomeDetailsUpdateResource) {

		Optional<OtherIncomeDetails> isPresentOtherIncomeDetails = otherIncomeDetailsRepository.findById(id);

		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository
				.findById(validationService.stringToLong(otherIncomeDetailsUpdateResource.getIncomeSourceDetailsId()));
		if (!isPresentIncomeSourceDetails.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "incomeSourceDetailId");
		if (!IncomeTypeEnum.OTHER.toString().equals(isPresentIncomeSourceDetails.get().getIncomeType().toString()))
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_MATCH), "incomeSourceDetailId");

		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryRepository
				.findById(validationService.stringToLong(otherIncomeDetailsUpdateResource.getOtherIncomeTypeId()));
		if (!isPresentOtherIncomeCategory.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "otherIncomeTypeId");

		if (!isPresentOtherIncomeDetails.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			if (!isPresentOtherIncomeDetails.get().getVersion()
					.equals(Long.parseLong(otherIncomeDetailsUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			OtherIncomeDetails otherIncomeDetails = isPresentOtherIncomeDetails.get();
			otherIncomeDetails.setTenantId(tenantId);
			otherIncomeDetails.setOtherIncomeTypeId(isPresentOtherIncomeCategory.get());
			otherIncomeDetails.setIncomeSourceDetailsId(isPresentIncomeSourceDetails.get());
			otherIncomeDetails.setSourceType(SourceTypeEnum.valueOf(otherIncomeDetailsUpdateResource.getSourceType()));
			otherIncomeDetails.setBehaviourCode(otherIncomeDetailsUpdateResource.getBehaviourCode());
			otherIncomeDetails
					.setBehaviourId(validationService.stringToLong(otherIncomeDetailsUpdateResource.getBehaviourId()));
			otherIncomeDetails.setIncomeLevelCode(otherIncomeDetailsUpdateResource.getIncomeLevelCode());
			otherIncomeDetails.setIncomeLevelId(
					validationService.stringToLong(otherIncomeDetailsUpdateResource.getIncomeLevelId()));
			otherIncomeDetails.setNoOfYearsEarned(
					validationService.stringToLong(otherIncomeDetailsUpdateResource.getNoOfYearsEarned()));
			otherIncomeDetails.setNoOfMonthsEarned(
					validationService.stringToLong(otherIncomeDetailsUpdateResource.getNoOfMonthsEarned()));
			otherIncomeDetails.setDescription(otherIncomeDetailsUpdateResource.getDescription());
			otherIncomeDetails.setStatus(CommonStatus.valueOf(otherIncomeDetailsUpdateResource.getStatus()));
			otherIncomeDetails.setModifiedDate(validationService.getCreateOrModifyDate());
			otherIncomeDetails.setSyncTs(validationService.getCreateOrModifyDate());
			otherIncomeDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			otherIncomeDetailsRepository.save(otherIncomeDetails);

			if (otherIncomeDetailsUpdateResource.getOtherIncomeDocumentList() != null
					&& !otherIncomeDetailsUpdateResource.getOtherIncomeDocumentList().isEmpty()) {
				Integer index = 0;
				for (DocumentUpdateResource documentUpdateResource : otherIncomeDetailsUpdateResource
						.getOtherIncomeDocumentList()) {

					remoteService.existDocument(tenantId, documentUpdateResource.getDocumentId(), Constants.ORIGIN_CUSTOMER);

					// update existing document
					if (documentUpdateResource.getId() != null && !documentUpdateResource.getDocumentId().isEmpty()) {

						Optional<OtherIncomeDetailDocuments> isPresentOtherIncomeDocument = otherIncomeDocumentsRepository
								.findById(validationService.stringToLong(documentUpdateResource.getId()));
						if (!isPresentOtherIncomeDocument.isPresent())
							throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND),
									ServiceEntity.ID, ServicePoint.SALARY_INCOME_DOCUMENTS, index);

						Optional<OtherIncomeDetailDocuments> isDuplicateOtherIncomeDocument = otherIncomeDocumentsRepository
								.findByOtherIncomeDetailsIdAndDocumentIdAndStatusAndIdNotIn(otherIncomeDetails.getId(),
										validationService.stringToLong(documentUpdateResource.getDocumentId()),
										CommonStatus.ACTIVE, isPresentOtherIncomeDocument.get().getId());
						if (isDuplicateOtherIncomeDocument.isPresent()) {
							throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE),
									ServiceEntity.DOCUMENT_ID, ServicePoint.SALARY_INCOME_DOCUMENTS, index);
						}

						if (!isPresentOtherIncomeDocument.get().getVersion()
								.equals(Long.parseLong(documentUpdateResource.getVersion())))
							throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE),
									ServiceEntity.VERSION, ServicePoint.SALARY_INCOME_DOCUMENTS, index);

						OtherIncomeDetailDocuments otherIncomeDocuments = isPresentOtherIncomeDocument.get();
						otherIncomeDetails.setTenantId(tenantId);
						otherIncomeDocuments
								.setDocumentId(validationService.stringToLong(documentUpdateResource.getDocumentId()));
						otherIncomeDocuments.setOtherIncomeDetails(otherIncomeDetails);
						otherIncomeDocuments.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
						otherIncomeDocuments.setModifiedDate(validationService.getCreateOrModifyDate());
						otherIncomeDocuments.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
						otherIncomeDocuments.setSyncTs(validationService.getSyncTs());
						otherIncomeDocumentsRepository.saveAndFlush(otherIncomeDocuments);

					} else {// update with new document
						Optional<OtherIncomeDetailDocuments> isPresentOtherIncomeDocument = otherIncomeDocumentsRepository
								.findByOtherIncomeDetailsIdAndDocumentIdAndStatus(otherIncomeDetails.getId(),
										validationService.stringToLong(documentUpdateResource.getDocumentId()),
										CommonStatus.ACTIVE);
						if (isPresentOtherIncomeDocument.isPresent()) {
							throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE),
									ServiceEntity.DOCUMENT_ID, ServicePoint.SALARY_INCOME_DOCUMENTS, index);
						}

						OtherIncomeDetailDocuments otherIncomeDocuments = new OtherIncomeDetailDocuments();
						otherIncomeDocuments.setTenantId(tenantId);
						otherIncomeDocuments
								.setDocumentId(validationService.stringToLong(documentUpdateResource.getDocumentId()));
						otherIncomeDocuments.setOtherIncomeDetails(otherIncomeDetails);
						otherIncomeDocuments.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
						otherIncomeDocuments.setCreatedDate(validationService.getCreateOrModifyDate());
						otherIncomeDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
						otherIncomeDocuments.setSyncTs(validationService.getSyncTs());
						otherIncomeDocumentsRepository.saveAndFlush(otherIncomeDocuments);
					}
				}
			}
			return otherIncomeDetails;
		}

	}
}
