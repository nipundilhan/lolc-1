package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.domain.CultivationIncomeDetails;
import com.fusionx.lending.origination.domain.CultivationIncomeDocuments;
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.enums.CommonListReferenceCodes;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.SourceTypeEnum;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.CultivationCategoryRepository;
import com.fusionx.lending.origination.repository.CultivationIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.CultivationIncomeDocumentRepository;
import com.fusionx.lending.origination.repository.IncomeSourceDetailsRepository;
import com.fusionx.lending.origination.resource.CultivationIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.CultivationIncomeDetailsListResource;
import com.fusionx.lending.origination.resource.CultivationIncomeDetailsUpdateResource;
import com.fusionx.lending.origination.resource.DocumentAddResource;
import com.fusionx.lending.origination.resource.DocumentUpdateResource;
import com.fusionx.lending.origination.service.CultivationIncomeDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class CultivationIncomeDetailsServiceImpl extends MessagePropertyBase
		implements CultivationIncomeDetailsService {

	@Autowired
	private ValidateService validateService;

	@Autowired
	private IncomeSourceDetailsRepository incomeSourceDetailsRepository;

	@Autowired
	private CultivationCategoryRepository cultivationCategoryRepository;

	@Autowired
	private CommonListRepository commonListRepository;

	@Autowired
	private CultivationIncomeDetailsRepository cultivationIncomeDetailsRepository;

	@Autowired
	private CultivationIncomeDocumentRepository cultivationIncomeDocumentRepository;

	@Override
	public Optional<CultivationIncomeDetails> findById(String tenantId, Long id) {
		Optional<CultivationIncomeDetails> isPresentCultivationIncomeDetails = cultivationIncomeDetailsRepository
				.findById(id);

		if (isPresentCultivationIncomeDetails.isPresent()) {
			return Optional.ofNullable(setCultivationIncomeDetails(tenantId, isPresentCultivationIncomeDetails.get()));
		} else {
			return Optional.empty();
		}
	}

	private CultivationIncomeDetails setCultivationIncomeDetails(String tenantId,
			CultivationIncomeDetails cultivationIncomeDetails) {

		cultivationIncomeDetails.setCultivationIncomeDocuments(
				cultivationIncomeDocumentRepository.findByCultivationIncomeDetailsId(cultivationIncomeDetails.getId()));
		return cultivationIncomeDetails;
	}

	@Override
	public List<CultivationIncomeDetails> findAll(String tenantId) {
		List<CultivationIncomeDetails> cultivationIncomeDetailsList = cultivationIncomeDetailsRepository.findAll();

		for (CultivationIncomeDetails cultivationIncomeDetail : cultivationIncomeDetailsList) {
			setCultivationIncomeDetails(tenantId, cultivationIncomeDetail);
		}
		return cultivationIncomeDetailsList;
	}

	@Override
	public List<CultivationIncomeDetails> findByStatus(String tenantId, String status) {
		List<CultivationIncomeDetails> cultivationIncomeDetailsList = cultivationIncomeDetailsRepository
				.findByStatus(CommonStatus.valueOf(status));

		for (CultivationIncomeDetails cultivationIncomeDetail : cultivationIncomeDetailsList) {
			setCultivationIncomeDetails(tenantId, cultivationIncomeDetail);
		}
		return cultivationIncomeDetailsList;
	}

	@Override
	public Long saveCultivationIncomeDetails(String tenantId,
			CultivationIncomeDetailsAddResource cultivationIncomeDetailsAddResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");

		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository
				.findById(validateService.stringToLong(cultivationIncomeDetailsAddResource.getIncomeSourceDetailsId()));
		if (!isPresentIncomeSourceDetails.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "incomeSourceDetailId");

		if (cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList() != null
				&& !cultivationIncomeDetailsAddResource.getCultivationIncomeDetailsList().isEmpty()) {
			Integer index = 0;
			for (CultivationIncomeDetailsListResource item : cultivationIncomeDetailsAddResource
					.getCultivationIncomeDetailsList()) {

				CultivationCategory cultivationCategory = validateCultivationCategory(
						validateService.stringToLong(item.getCultivationCategoryId()),
						item.getCultivationCategoryName(), index);

				CommonList landOwnership = validateLandOwnership(
						validateService.stringToLong(item.getLandOwnershipId()), item.getLandOwnershipName(), index);

				CommonList plantOwnership = validatePlantOwnership(
						validateService.stringToLong(item.getPlantOwnershipId()), item.getPlantOwnershipName(), index);

				if (SourceTypeEnum.PRIMARY.toString().equals(item.getSourceType())) {
					Optional<CultivationIncomeDetails> isPresentPrimary = cultivationIncomeDetailsRepository
							.findByIncomeSourceDetailIdAndSourceTypeAndStatus(
									isPresentIncomeSourceDetails.get().getId(), SourceTypeEnum.PRIMARY,
									CommonStatus.ACTIVE);
					if (isPresentPrimary.isPresent())
						throw new DetailListValidateException(getEnvironment().getProperty(COMMON_DUPLICATE),
								ServiceEntity.SOURCE_TYPE, ServicePoint.CULTIVATION_INCOME_DETAILS, index);
				}

				CultivationIncomeDetails cultivationIncomeDetails = new CultivationIncomeDetails();
				cultivationIncomeDetails.setCultivationCategory(cultivationCategory);
				cultivationIncomeDetails.setCmnListLandOwnership(landOwnership);
				cultivationIncomeDetails.setCmnListPlantOwnership(plantOwnership);
				cultivationIncomeDetails.setDescription(item.getDescription());
				cultivationIncomeDetails.setIncomeSourceDetail(isPresentIncomeSourceDetails.get());
				cultivationIncomeDetails.setLandSize(item.getSizeOfLand());
				cultivationIncomeDetails.setNoOfEmployees(validateService.stringToInteger(item.getNoOfEmployees()));
				cultivationIncomeDetails.setNoOfYears(validateService.stringToInteger(item.getNoOfYears()));
				cultivationIncomeDetails.setTenantId(tenantId);
				cultivationIncomeDetails.setSourceType(SourceTypeEnum.valueOf(item.getSourceType()));
				cultivationIncomeDetails.setStatus(CommonStatus.valueOf(item.getStatus()));
				cultivationIncomeDetails.setSyncTs(validateService.getSyncTs());
				cultivationIncomeDetails.setCreatedDate(validateService.getCreateOrModifyDate());
				cultivationIncomeDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				cultivationIncomeDetails = cultivationIncomeDetailsRepository.save(cultivationIncomeDetails);

				if (item.getCultivationIncomeDocumentList() != null
						&& !item.getCultivationIncomeDocumentList().isEmpty()) {

					for (DocumentAddResource documentAddResource : item.getCultivationIncomeDocumentList()) {

						validateService.validateDocument(tenantId, documentAddResource.getDocumentId(),documentAddResource.getDocumentName(),
								ServicePoint.SALARY_INCOME_DETAILS, Constants.ORIGIN_CUSTOMER, index);

						Optional<CultivationIncomeDocuments> isPresentCultivationIncomeDocuments = cultivationIncomeDocumentRepository
								.findByCultivationIncomeDetailsIdAndDocumentIdAndStatus(
										cultivationIncomeDetails.getId(),
										validateService.stringToLong(documentAddResource.getDocumentId()),
										CommonStatus.ACTIVE);
						if (isPresentCultivationIncomeDocuments.isPresent()) {
							throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "documentId");
						}

						CultivationIncomeDocuments cultivationIncomeDocuments = new CultivationIncomeDocuments();
						cultivationIncomeDocuments.setTenantId(tenantId);
						cultivationIncomeDocuments
								.setDocumentId(validateService.stringToLong(documentAddResource.getDocumentId()));
						cultivationIncomeDocuments.setCultivationIncomeDetails(cultivationIncomeDetails);
						cultivationIncomeDocuments.setStatus(CommonStatus.valueOf(documentAddResource.getStatus()));
						cultivationIncomeDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
						cultivationIncomeDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
						cultivationIncomeDocuments.setSyncTs(validateService.getSyncTs());
						cultivationIncomeDocumentRepository.saveAndFlush(cultivationIncomeDocuments);
					}
				}

				index++;
			}

		}
		return isPresentIncomeSourceDetails.get().getId();
	}

	private CommonList validateLandOwnership(Long id, String name, Integer index) {

		Optional<CommonList> commonList = commonListRepository.findByIdAndNameAndStatus(id, name,
				CommonStatus.ACTIVE.toString());

		if (!commonList.isPresent()) {
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.LAND_OWNERSHIP_ID, ServicePoint.CULTIVATION_INCOME_DETAILS, index);
		}

		if (!commonList.get().getReferenceCode().toString()
				.equalsIgnoreCase(CommonListReferenceCodes.LAND_OWNERSHIP.toString())) {
			throw new DetailListValidateException(environment.getProperty("land.ownership-ref-code"),
					ServiceEntity.LAND_OWNERSHIP_REF_CODE, ServicePoint.CULTIVATION_INCOME_DETAILS, index);
		}

		return commonList.get();
	}

	private CommonList validatePlantOwnership(Long id, String name, Integer index) {

		Optional<CommonList> commonList = commonListRepository.findByIdAndNameAndStatus(id, name,
				CommonStatus.ACTIVE.toString());

		if (!commonList.isPresent()) {
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.PLANT_OWNERSHIP_ID, ServicePoint.CULTIVATION_INCOME_DETAILS, index);
		}

		if (!commonList.get().getReferenceCode().toString()
				.equalsIgnoreCase(CommonListReferenceCodes.PLANT_OWNERSHIP.toString())) {
			throw new DetailListValidateException(environment.getProperty("plant.ownership-ref-code"),
					ServiceEntity.PLANT_OWNERSHIP_REF_CODE, ServicePoint.CULTIVATION_INCOME_DETAILS, index);

		}

		return commonList.get();
	}

	private CultivationCategory validateCultivationCategory(Long id, String name, Integer index) {

		Optional<CultivationCategory> isPresentCultivationCategory = cultivationCategoryRepository
				.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);

		if (!isPresentCultivationCategory.isPresent())
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.CULTIVATION_CATEGORY_ID, ServicePoint.CULTIVATION_INCOME_DETAILS, index);

		return isPresentCultivationCategory.get();

	}

	@Override
	public CultivationIncomeDetails updateCultivationIncomeDetails(String tenantId, Long id,
			CultivationIncomeDetailsUpdateResource cultivationIncomeDetailsUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");

		Optional<CultivationIncomeDetails> isPresentCultivationIncomeDetails = cultivationIncomeDetailsRepository
				.findById(id);
		if (!isPresentCultivationIncomeDetails.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");

		if (SourceTypeEnum.PRIMARY.toString().equals(cultivationIncomeDetailsUpdateResource.getSourceType())) {
			Optional<CultivationIncomeDetails> isPresentPrimary = cultivationIncomeDetailsRepository
					.findByIncomeSourceDetailIdAndSourceTypeAndStatusAndIdNotIn(
							isPresentCultivationIncomeDetails.get().getIncomeSourceDetail().getId(),
							SourceTypeEnum.PRIMARY, CommonStatus.ACTIVE, id);
			if (isPresentPrimary.isPresent())
				throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "sourceType");
		}

		if (!isPresentCultivationIncomeDetails.get().getVersion()
				.equals(Long.parseLong(cultivationIncomeDetailsUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");

		CultivationCategory cultivationCategory = validateCultivationCategory(
				validateService.stringToLong(cultivationIncomeDetailsUpdateResource.getCultivationCategoryId()),
				cultivationIncomeDetailsUpdateResource.getCultivationCategoryName(), null);

		CommonList landOwnership = validateLandOwnership(
				validateService.stringToLong(cultivationIncomeDetailsUpdateResource.getLandOwnershipId()),
				cultivationIncomeDetailsUpdateResource.getLandOwnershipName(), null);

		CommonList plantOwnership = validatePlantOwnership(
				validateService.stringToLong(cultivationIncomeDetailsUpdateResource.getPlantOwnershipId()),
				cultivationIncomeDetailsUpdateResource.getPlantOwnershipName(), null);

		CultivationIncomeDetails cultivationIncomeDetails = isPresentCultivationIncomeDetails.get();
		cultivationIncomeDetails.setCultivationCategory(cultivationCategory);
		cultivationIncomeDetails.setCmnListLandOwnership(landOwnership);
		cultivationIncomeDetails.setCmnListPlantOwnership(plantOwnership);
		cultivationIncomeDetails.setDescription(cultivationIncomeDetailsUpdateResource.getDescription());
		cultivationIncomeDetails.setLandSize(cultivationIncomeDetailsUpdateResource.getSizeOfLand());
		cultivationIncomeDetails.setNoOfEmployees(
				validateService.stringToInteger(cultivationIncomeDetailsUpdateResource.getNoOfEmployees()));
		cultivationIncomeDetails
				.setNoOfYears(validateService.stringToInteger(cultivationIncomeDetailsUpdateResource.getNoOfYears()));
		cultivationIncomeDetails.setTenantId(tenantId);
		cultivationIncomeDetails
				.setSourceType(SourceTypeEnum.valueOf(cultivationIncomeDetailsUpdateResource.getSourceType()));
		cultivationIncomeDetails.setStatus(CommonStatus.valueOf(cultivationIncomeDetailsUpdateResource.getStatus()));
		cultivationIncomeDetails.setSyncTs(validateService.getSyncTs());
		cultivationIncomeDetails.setModifiedDate(validateService.getCreateOrModifyDate());
		cultivationIncomeDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		cultivationIncomeDetails = cultivationIncomeDetailsRepository.save(cultivationIncomeDetails);

		if (cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList() != null
				&& !cultivationIncomeDetailsUpdateResource.getCultivationIncomeDocumentList().isEmpty()) {
			Integer index = 0;
			for (DocumentUpdateResource documentUpdateResource : cultivationIncomeDetailsUpdateResource
					.getCultivationIncomeDocumentList()) {

				validateService.validateDocument(tenantId, documentUpdateResource.getDocumentId(),documentUpdateResource.getDocumentName(),
						ServicePoint.CULTIVATION_INCOME_DOCUMENTS, Constants.ORIGIN_CUSTOMER, index);

				// update existing document
				if (documentUpdateResource.getId() != null && !documentUpdateResource.getDocumentId().isEmpty()) {

					Optional<CultivationIncomeDocuments> isPresentCultivationIncomeDocument = cultivationIncomeDocumentRepository
							.findById(validateService.stringToLong(documentUpdateResource.getId()));
					if (!isPresentCultivationIncomeDocument.isPresent())
						throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND),
								ServiceEntity.ID, ServicePoint.CULTIVATION_INCOME_DOCUMENTS, index);

					Optional<CultivationIncomeDocuments> isDuplicateCultivationIncomeDocument = cultivationIncomeDocumentRepository
							.findByCultivationIncomeDetailsIdAndDocumentIdAndStatusAndIdNotIn(
									cultivationIncomeDetails.getId(),
									validateService.stringToLong(documentUpdateResource.getDocumentId()),
									CommonStatus.ACTIVE, isPresentCultivationIncomeDocument.get().getId());

					if (isDuplicateCultivationIncomeDocument.isPresent()) {
						throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE),
								ServiceEntity.DOCUMENT_ID, ServicePoint.CULTIVATION_INCOME_DOCUMENTS, index);
					}

					if (!isPresentCultivationIncomeDocument.get().getVersion()
							.equals(Long.parseLong(documentUpdateResource.getVersion())))
						throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE),
								ServiceEntity.VERSION, ServicePoint.CULTIVATION_INCOME_DOCUMENTS, index);

					CultivationIncomeDocuments cultivationIncomeDocuments = isPresentCultivationIncomeDocument.get();
					cultivationIncomeDocuments.setTenantId(tenantId);
					cultivationIncomeDocuments
							.setDocumentId(validateService.stringToLong(documentUpdateResource.getDocumentId()));
					cultivationIncomeDocuments.setCultivationIncomeDetails(cultivationIncomeDetails);
					cultivationIncomeDocuments.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
					cultivationIncomeDocuments.setModifiedDate(validateService.getCreateOrModifyDate());
					cultivationIncomeDocuments.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
					cultivationIncomeDocuments.setSyncTs(validateService.getSyncTs());
					cultivationIncomeDocumentRepository.saveAndFlush(cultivationIncomeDocuments);

				} else {// update with new document
					Optional<CultivationIncomeDocuments> isPresentCultivationIncomeDocument = cultivationIncomeDocumentRepository
							.findByCultivationIncomeDetailsIdAndDocumentIdAndStatus(cultivationIncomeDetails.getId(),
									validateService.stringToLong(documentUpdateResource.getDocumentId()),
									CommonStatus.ACTIVE);
					if (isPresentCultivationIncomeDocument.isPresent()) {
						throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE),
								ServiceEntity.DOCUMENT_ID, ServicePoint.SALARY_INCOME_DOCUMENTS, index);
					}

					CultivationIncomeDocuments cultivationIncomeDocuments = new CultivationIncomeDocuments();
					cultivationIncomeDocuments.setTenantId(tenantId);
					cultivationIncomeDocuments
							.setDocumentId(validateService.stringToLong(documentUpdateResource.getDocumentId()));
					cultivationIncomeDocuments.setCultivationIncomeDetails(cultivationIncomeDetails);
					cultivationIncomeDocuments.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
					cultivationIncomeDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
					cultivationIncomeDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
					cultivationIncomeDocuments.setSyncTs(validateService.getSyncTs());
					cultivationIncomeDocumentRepository.saveAndFlush(cultivationIncomeDocuments);
				}
			}
		}

		return cultivationIncomeDetails;
	}

	@Override
	public List<CultivationIncomeDetails> findByIncomeSourceDetailsId(String tenantId, Long incomeSourceDetailsId) {
		List<CultivationIncomeDetails> cultivationIncomeDetailsList = cultivationIncomeDetailsRepository
				.findByIncomeSourceDetailId(incomeSourceDetailsId);

		for (CultivationIncomeDetails cultivationIncomeDetail : cultivationIncomeDetailsList) {
			setCultivationIncomeDetails(tenantId, cultivationIncomeDetail);
		}
		return cultivationIncomeDetailsList;
	}

}
