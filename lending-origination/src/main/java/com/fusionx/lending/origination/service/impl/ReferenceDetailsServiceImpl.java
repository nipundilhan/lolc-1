package com.fusionx.lending.origination.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ReferenceDetails;
import com.fusionx.lending.origination.domain.ReferenceDetailsContactInfo;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ReferenceDetailsContactInfoRepository;
import com.fusionx.lending.origination.repository.ReferenceDetailsRepository;
import com.fusionx.lending.origination.resource.ContactType;
import com.fusionx.lending.origination.resource.ReferenceDetailsAddResource;
import com.fusionx.lending.origination.resource.ReferenceDetailsContactInfoAddResource;
import com.fusionx.lending.origination.resource.ReferenceDetailsContactInfoUpdateResource;
import com.fusionx.lending.origination.resource.ReferenceDetailsUpdateResource;
import com.fusionx.lending.origination.service.ReferenceDetailsHistoryService;
import com.fusionx.lending.origination.service.ReferenceDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ReferenceDetailsServiceImpl extends MessagePropertyBase implements ReferenceDetailsService {

	@Autowired
	private ReferenceDetailsRepository referenceDetailsRepository;

	@Autowired
	private ReferenceDetailsContactInfoRepository referenceDetailsContactInfoRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private ReferenceDetailsHistoryService referenceDetailsHistoryService;

	@Override
	public List<ReferenceDetails> findAll() {
		List<ReferenceDetails> isPresentFinancialStatement = referenceDetailsRepository.findAll();
		for (ReferenceDetails referenceDetails : isPresentFinancialStatement) {
			List<ReferenceDetailsContactInfo> referenceDetailsDetail = referenceDetailsContactInfoRepository
					.findByReferenceDetailsId(referenceDetails.getId());
			referenceDetails.setReferenceDetailsContactInfo(referenceDetailsDetail);
		}
		return isPresentFinancialStatement;
	}

	@Override
	public Optional<ReferenceDetails> findById(Long id) {
		Optional<ReferenceDetails> isPresentFinancialStatement = referenceDetailsRepository.findById(id);
		if (isPresentFinancialStatement.isPresent()) {
			ReferenceDetails referenceDetails = isPresentFinancialStatement.get();
			List<ReferenceDetailsContactInfo> referenceDetailsContactInfo = referenceDetailsContactInfoRepository
					.findByReferenceDetailsId(referenceDetails.getId());
			referenceDetails.setReferenceDetailsContactInfo(referenceDetailsContactInfo);
			return isPresentFinancialStatement;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ReferenceDetails> findByStatus(String status) {
		List<ReferenceDetails> isPresentFinancialStatement = referenceDetailsRepository
				.findByStatus(CommonStatus.valueOf(status));
		List<ReferenceDetails> referenceDetailsList = new ArrayList<>();
		for (ReferenceDetails referenceDetails : isPresentFinancialStatement) {
			List<ReferenceDetailsContactInfo> referenceDetailsContactInfo = referenceDetailsContactInfoRepository
					.findByReferenceDetailsId(referenceDetails.getId());
			referenceDetails.setReferenceDetailsContactInfo(referenceDetailsContactInfo);
			referenceDetailsList.add(referenceDetails);
		}
		return referenceDetailsList;
	}

	@Override
	public ReferenceDetails addReferenceDetails(String tenantId,
			ReferenceDetailsAddResource referenceDetailsAddResource) {

		ReferenceDetails referenceDetails = new ReferenceDetails();
		referenceDetails.setTenantId(tenantId);
		referenceDetails.setName(referenceDetailsAddResource.getName());
		referenceDetails.setProfessionalStatus(referenceDetailsAddResource.getProfessionalStatus());
		referenceDetails.setBusinessEmployer(referenceDetailsAddResource.getBusinessEmployer());
		referenceDetails.setCurrentAddressLine1(referenceDetailsAddResource.getCurrentAddressLine1());
		referenceDetails.setCurrentAddressLine2(referenceDetailsAddResource.getCurrentAddressLine2());
		referenceDetails.setCurrentAddressLine3(referenceDetailsAddResource.getCurrentAddressLine3());
		referenceDetails.setCurrentAddressLine4(referenceDetailsAddResource.getCurrentAddressLine4());
		referenceDetails.setPermanentAddressLine1(referenceDetailsAddResource.getPermanentAddressLine1());
		referenceDetails.setPermanentAddressLine2(referenceDetailsAddResource.getPermanentAddressLine2());
		referenceDetails.setPermanentAddressLine3(referenceDetailsAddResource.getPermanentAddressLine3());
		referenceDetails.setPermanentAddressLine4(referenceDetailsAddResource.getPermanentAddressLine4());
		referenceDetails.setStatus(CommonStatus.valueOf(referenceDetailsAddResource.getStatus()));
		referenceDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		referenceDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		referenceDetails.setSyncTs(validationService.getCreateOrModifyDate());
		referenceDetailsRepository.save(referenceDetails);

		for (ReferenceDetailsContactInfoAddResource referenceDetailsContactInfoAddResource : referenceDetailsAddResource
				.getContactDetails()) {

//			if (referenceDetailsContactInfoAddResource.getId() != null) {
				ContactType contactType = validationService.validateContactType(tenantId,
						Long.parseLong(referenceDetailsContactInfoAddResource.getId()),
						referenceDetailsContactInfoAddResource.getCntpCode());

				ReferenceDetailsContactInfo referenceDetailsContactInfo = new ReferenceDetailsContactInfo();
				referenceDetailsContactInfo.setTenantId(tenantId);
				referenceDetailsContactInfo.setContactType(contactType.getId().toString());
				referenceDetailsContactInfo.setContactTypeCode(contactType.getCntpCode());
				referenceDetailsContactInfo.setContactNo(referenceDetailsContactInfoAddResource.getContactNo());
				referenceDetailsContactInfo.setReferenceDetails(referenceDetails);
				referenceDetailsContactInfo
						.setStatus(CommonStatus.valueOf(referenceDetailsContactInfoAddResource.getCntpStatus()));
				referenceDetailsContactInfo.setCreatedDate(validationService.getCreateOrModifyDate());
				referenceDetailsContactInfo.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				referenceDetailsContactInfo.setSyncTs(validationService.getCreateOrModifyDate());
				referenceDetailsContactInfoRepository.save(referenceDetailsContactInfo);
			}

//			else {
//				throw new ValidateRecordException(environment.getProperty("common.not-null"), "contactTypeId");
//			}
//		}
		return referenceDetails;
	}

	@Override
	public ReferenceDetails updateReferenceDetails(String tenantId, Long id,
			ReferenceDetailsUpdateResource referenceDetailsUpdateResource) {

		Optional<ReferenceDetails> isPresentReferenceDetails = referenceDetailsRepository.findById(id);

		referenceDetailsHistoryService.saveHistory(tenantId, isPresentReferenceDetails.get(),
				LogginAuthentcation.getInstance().getUserName());

		if (!isPresentReferenceDetails.get().getVersion()
				.equals(Long.parseLong(referenceDetailsUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION);

		ReferenceDetails referenceDetails = isPresentReferenceDetails.get();
		referenceDetails.setTenantId(tenantId);
		referenceDetails.setName(referenceDetailsUpdateResource.getName());
		referenceDetails.setProfessionalStatus(referenceDetailsUpdateResource.getProfessionalStatus());
		referenceDetails.setBusinessEmployer(referenceDetailsUpdateResource.getBusinessEmployer());
		referenceDetails.setCurrentAddressLine1(referenceDetailsUpdateResource.getCurrentAddressLine1());
		referenceDetails.setCurrentAddressLine2(referenceDetailsUpdateResource.getCurrentAddressLine2());
		referenceDetails.setCurrentAddressLine3(referenceDetailsUpdateResource.getCurrentAddressLine3());
		referenceDetails.setCurrentAddressLine4(referenceDetailsUpdateResource.getCurrentAddressLine4());
		referenceDetails.setPermanentAddressLine1(referenceDetailsUpdateResource.getPermanentAddressLine1());
		referenceDetails.setPermanentAddressLine2(referenceDetailsUpdateResource.getPermanentAddressLine2());
		referenceDetails.setPermanentAddressLine3(referenceDetailsUpdateResource.getPermanentAddressLine3());
		referenceDetails.setPermanentAddressLine4(referenceDetailsUpdateResource.getPermanentAddressLine4());
		referenceDetails.setStatus(CommonStatus.valueOf(referenceDetailsUpdateResource.getStatus()));
		referenceDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		referenceDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		referenceDetails.setModifiedDate(validationService.getCreateOrModifyDate());
		referenceDetails.setSyncTs(validationService.getCreateOrModifyDate());
		referenceDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		referenceDetailsRepository.saveAndFlush(referenceDetails);

		for (ReferenceDetailsContactInfoUpdateResource referenceDetailsContactInfoAddResource : referenceDetailsUpdateResource
				.getContactDetails()) {

			Optional<ReferenceDetailsContactInfo> isPresentReferenceDetailsContactInfo = referenceDetailsContactInfoRepository
					.findById(Long.parseLong(referenceDetailsContactInfoAddResource.getId()));
			if (!isPresentReferenceDetailsContactInfo.get().getVersion()
					.equals(Long.parseLong(referenceDetailsContactInfoAddResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			ContactType contactType = validationService.validateContactType(tenantId,
					Long.parseLong(referenceDetailsContactInfoAddResource.getId()),
					referenceDetailsContactInfoAddResource.getCntpCode());
			ReferenceDetailsContactInfo referenceDetailsContactInfo = isPresentReferenceDetailsContactInfo.get();
			referenceDetailsContactInfo.setTenantId(tenantId);
			referenceDetailsContactInfo.setContactType(contactType.getId().toString());
			referenceDetailsContactInfo.setContactTypeCode(contactType.getCntpCode());
			referenceDetailsContactInfo.setContactNo(referenceDetailsContactInfoAddResource.getContactNo());
			referenceDetailsContactInfo.setReferenceDetails(referenceDetails);
			referenceDetailsContactInfo
					.setStatus(CommonStatus.valueOf(referenceDetailsContactInfoAddResource.getStatus()));
			referenceDetailsContactInfo.setModifiedDate(validationService.getCreateOrModifyDate());
			referenceDetailsContactInfo.setSyncTs(validationService.getCreateOrModifyDate());
			referenceDetailsContactInfo.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			referenceDetailsContactInfoRepository.save(referenceDetailsContactInfo);
		}
		return referenceDetails;

	}

	@Override
	public List<ReferenceDetails> getByLeadId(Long leadInfoId) {

		List<ReferenceDetails> isPresentReferenceDetails = this.referenceDetailsRepository.findByLeadInfoId(leadInfoId);
		for (ReferenceDetails referenceDetails : isPresentReferenceDetails) {
			List<ReferenceDetailsContactInfo> referenceDetailsContactInfo = referenceDetailsContactInfoRepository
					.findByReferenceDetailsId(referenceDetails.getId());
			referenceDetails.setReferenceDetailsContactInfo(referenceDetailsContactInfo);
		}
		return isPresentReferenceDetails;
	}
}
