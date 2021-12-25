package com.fusionx.lending.origination.service.impl;

/**
 * Master Value paire Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *   1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fusionx.lending.origination.repository.MasterValuePaireRepository;
import com.fusionx.lending.origination.repository.ValuePaireDetailRepository;
import com.fusionx.lending.origination.resource.MasterValuePaireAddResource;
import com.fusionx.lending.origination.resource.MasterValuePaireUpdateResource;
import com.fusionx.lending.origination.resource.ValuePaireDetailAddResource;
import com.fusionx.lending.origination.resource.ValuePaireDetailUpdateResource;
import com.fusionx.lending.origination.service.MasterValuePaireService;
import com.fusionx.lending.origination.service.ValidateService;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.MasterValuePaire;
import com.fusionx.lending.origination.domain.ValuePaireDetail;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;

@Component
@Transactional(rollbackFor = Exception.class)
public class MasterValuePaireServiceImpl extends MessagePropertyBase implements MasterValuePaireService {

	@Autowired
	private ValidateService validationService;

	@Autowired
	private MasterValuePaireRepository masterValuePaireRepository;

	@Autowired
	private ValuePaireDetailRepository valuePaireDetailRepository;

	@Override
	public List<MasterValuePaire> findAll() {
		return masterValuePaireRepository.findAll();
	}

	@Override
	public Optional<MasterValuePaire> findById(Long id) {
		Optional<MasterValuePaire> isPresentMasterValue = masterValuePaireRepository.findById(id);
		if (isPresentMasterValue.isPresent()) {
			MasterValuePaire masterValuePaire = isPresentMasterValue.get();
			List<ValuePaireDetail> valuePaireDetail = valuePaireDetailRepository
					.findByMasterValuesId(masterValuePaire.getId());
			masterValuePaire.setValuePaireDetailList(valuePaireDetail);
			return isPresentMasterValue;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<MasterValuePaire> findByStatus(String status) {
		List<MasterValuePaire> isPresentMasterValue = masterValuePaireRepository.findByStatus(status);
		List<MasterValuePaire> valuePaire = new ArrayList<>();
		for (MasterValuePaire masterValuePaire : isPresentMasterValue) {
			List<ValuePaireDetail> valuepaireDetail = valuePaireDetailRepository
					.findByMasterValuesId(masterValuePaire.getId());
			masterValuePaire.setValuePaireDetailList(valuepaireDetail);
			valuePaire.add(masterValuePaire);
		}
		return valuePaire;
	}

	@Override
	public List<MasterValuePaire> findByName(String name) {
		List<MasterValuePaire> isPresentMasterValue = masterValuePaireRepository.findByNameContaining(name);
		List<MasterValuePaire> valuePaire = new ArrayList<>();
		for (MasterValuePaire masterValuePaire : isPresentMasterValue) {
			List<ValuePaireDetail> valuepaireDetail = valuePaireDetailRepository
					.findByMasterValuesId(masterValuePaire.getId());
			masterValuePaire.setValuePaireDetailList(valuepaireDetail);
			valuePaire.add(masterValuePaire);
		}
		return valuePaire;
	}

	@Override
	public Optional<MasterValuePaire> findByCode(String code) {
		Optional<MasterValuePaire> isPresentMasterValue = masterValuePaireRepository.findByCode(code);
		if (isPresentMasterValue.isPresent()) {
			MasterValuePaire masterValuePaire = isPresentMasterValue.get();
			List<ValuePaireDetail> valuepaireDetail = valuePaireDetailRepository
					.findByMasterValuesId(masterValuePaire.getId());
			masterValuePaire.setValuePaireDetailList(valuepaireDetail);
			return isPresentMasterValue;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return masterValuePaireRepository.existsById(id);
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return new Timestamp(now.getTime());
	}

	@Override
	public Long saveAndValidateValuePaire(String tenantId, String createdUser,
			MasterValuePaireAddResource masterValuePaireAddResource) {

		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Name*******************");
		if (masterValuePaireRepository.existsByName(masterValuePaireAddResource.getName()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "name");

		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Code*******************");
		if (masterValuePaireRepository.existsByCode(masterValuePaireAddResource.getCode()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");

		LoggerRequest.getInstance()
				.logInfo("NotificationProcessSetup************Validate Code length*******************");
		if (masterValuePaireAddResource.getCode().length() != 4)
			throw new ValidateRecordException(environment.getProperty("code-length"), "Invalid code length.");

		LoggerRequest.getInstance()
				.logInfo("NotificationProcessSetup************Validate Dupilcates*******************");
		if (masterValuePaireRepository.existsByCodeAndName(masterValuePaireAddResource.getCode(),
				masterValuePaireAddResource.getName()))
			throw new ValidateRecordException(environment.getProperty("typeAndProcess.unique"), "message");

		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save value paire*******************");
		MasterValuePaire masterValuePaire = saveValuePaire(tenantId, createdUser, masterValuePaireAddResource);

		LoggerRequest.getInstance()
				.logInfo("NotificationProcessSetup************Validate value paire details*******************");
		if (masterValuePaireAddResource.getType().equals("URL") && masterValuePaireAddResource.getValuePaireDetails() != null
				&& !masterValuePaireAddResource.getValuePaireDetails().isEmpty()) {

			for (ValuePaireDetailAddResource valuePaireDetail : masterValuePaireAddResource.getValuePaireDetails()) {

				LoggerRequest.getInstance()
						.logInfo("NotificationProcessSetup************Save value paire details*******************");
				saveValuePaireDetail(tenantId, createdUser, valuePaireDetail, masterValuePaire);
			}
		}

		return masterValuePaire.getId();
	}

	private MasterValuePaire saveValuePaire(String tenantId, String createdUser,
			MasterValuePaireAddResource masterValuePaireAddResource) {

		Boolean urlValiddation = Boolean.FALSE;
		if (masterValuePaireAddResource.getUrl() != null && masterValuePaireAddResource.getType().equals("URL")) {
			urlValiddation = validationService.validateBaseUrl(tenantId, masterValuePaireAddResource.getUrl(),
					MasterValuePaire.class);
		}

		MasterValuePaire masterValuePaire = new MasterValuePaire();
		masterValuePaire.setTenantId(tenantId);
		masterValuePaire.setCode(masterValuePaireAddResource.getCode());
		masterValuePaire.setName(masterValuePaireAddResource.getName());
		masterValuePaire.setDescription(masterValuePaireAddResource.getDescription());
		masterValuePaire.setType(masterValuePaireAddResource.getType());
		if (masterValuePaireAddResource.getType().equals("URL")) {
			masterValuePaire.setUrl(masterValuePaireAddResource.getUrl());
		}
		masterValuePaire.setStatus(masterValuePaireAddResource.getStatus());
		masterValuePaire.setCreatedUser(createdUser);
		masterValuePaire.setCreatedDate(getCreateOrModifyDate());
		masterValuePaire.setSyncTs(getCreateOrModifyDate());

		masterValuePaire = masterValuePaireRepository.saveAndFlush(masterValuePaire);
		return masterValuePaire;

	}

	private ValuePaireDetail saveValuePaireDetail(String tenantId, String createdUser,
			ValuePaireDetailAddResource valuePaireDetailAddResource, MasterValuePaire masterValuePaires) {
		ValuePaireDetail valuePaireDeail = new ValuePaireDetail();
		valuePaireDeail.setTenantId(tenantId);
		valuePaireDeail.setValue(valuePaireDetailAddResource.getValue());
		valuePaireDeail.setType(valuePaireDetailAddResource.getType());
		valuePaireDeail.setMasterValues(masterValuePaires);
		valuePaireDeail.setStatus(valuePaireDetailAddResource.getStatus());
		valuePaireDeail.setCreatedUser(createdUser);
		valuePaireDeail.setCreatedDate(getCreateOrModifyDate());
		valuePaireDeail.setSyncTs(getCreateOrModifyDate());

		valuePaireDeail = valuePaireDetailRepository.saveAndFlush(valuePaireDeail);
		return valuePaireDeail;

	}

//	@Override
//	public Long updateAndValidateValuePaire(String tenantId, String createdUser, Long id,
//			MasterValuePaireUpdateResource masterValuePaireUpdateResource) {
//
//		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Name*******************");
//		if (masterValuePaireRepository.existsByName(masterValuePaireUpdateResource.getName()))
//			throw new ValidateRecordException(environment.getProperty("common.unique"), "name");
//
//		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Code*******************");
//		if (masterValuePaireRepository.existsByCode(masterValuePaireUpdateResource.getCode()))
//			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
//
//		LoggerRequest.getInstance()
//				.logInfo("NotificationProcessSetup************Validate Code length*******************");
//		if (masterValuePaireUpdateResource.getCode().length() != 4)
//			throw new ValidateRecordException(environment.getProperty("code-length"), "Invalid code length.");
//
//		LoggerRequest.getInstance()
//				.logInfo("NotificationProcessSetup************Validate Dupilcates*******************");
//		if (masterValuePaireRepository.existsByCodeAndName(masterValuePaireUpdateResource.getCode(),
//				masterValuePaireUpdateResource.getName()))
//			throw new ValidateRecordException(environment.getProperty("typeAndProcess.unique"), "message");
//
//		LoggerRequest.getInstance()
//				.logInfo("NotificationProcessSetup************Update value paire*******************");
//		MasterValuePaire masterValuePaire = updateValuePaire(tenantId, createdUser, masterValuePaireUpdateResource, id);
//
//		LoggerRequest.getInstance()
//				.logInfo("NotificationProcessSetup************Validate and update detail paire*******************");
//		if (masterValuePaireUpdateResource.getValuePaireDetails() != null
//				&& !masterValuePaireUpdateResource.getValuePaireDetails().isEmpty()) {
//
//			for (ValuePaireDetailUpdateResource valuePaireDetail : masterValuePaireUpdateResource
//					.getValuePaireDetails()) {
//
//				LoggerRequest.getInstance()
//						.logInfo("NotificationProcessSetup************Save value paire details*******************");
//				updateValuePaireDetail(tenantId, createdUser, masterValuePaire, valuePaireDetail);
//			}
//
//		}
//
//		return masterValuePaire.getId();
//	}

	public MasterValuePaire updateValuePaire(String tenantId, String createdUser,
			MasterValuePaireUpdateResource masterValuePaireUpdateResource, Long id) {

		Optional<MasterValuePaire> masterValuePaire = masterValuePaireRepository.findById(id);

		if (masterValuePaire.isPresent()) {
			MasterValuePaire isPresentMasterValuePaire = masterValuePaire.get();

			if (!isPresentMasterValuePaire.getVersion()
					.equals(Long.parseLong(masterValuePaireUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentMasterValuePaire.getCode().equals(masterValuePaireUpdateResource.getCode()))
				throw new ValidateRecordException(environment.getProperty(COMMON_CODE_CAN_NOT_CHANGE), "code");

			Boolean urlValiddation = Boolean.FALSE;
			if (masterValuePaireUpdateResource.getUrl() != null
					&& masterValuePaireUpdateResource.getType().equals("URL")) {
				urlValiddation = validationService.validateBaseUrl(tenantId, masterValuePaireUpdateResource.getUrl(),
						MasterValuePaire.class);
			}
			isPresentMasterValuePaire.setTenantId(tenantId);
			isPresentMasterValuePaire.setCode(masterValuePaireUpdateResource.getCode());
			isPresentMasterValuePaire.setName(masterValuePaireUpdateResource.getName());
			isPresentMasterValuePaire.setDescription(masterValuePaireUpdateResource.getDescription());
			isPresentMasterValuePaire.setType(masterValuePaireUpdateResource.getType());
			if (masterValuePaireUpdateResource.getType() == "URL" && urlValiddation == true) {
				isPresentMasterValuePaire.setUrl(masterValuePaireUpdateResource.getUrl());
			}
			isPresentMasterValuePaire.setStatus(masterValuePaireUpdateResource.getStatus());
			isPresentMasterValuePaire.setModifiedUser(createdUser);
			isPresentMasterValuePaire.setModifiedDate(getCreateOrModifyDate());
			isPresentMasterValuePaire.setSyncTs(getCreateOrModifyDate());
			isPresentMasterValuePaire.setVersion(Long.parseLong(masterValuePaireUpdateResource.getVersion()));

			isPresentMasterValuePaire = masterValuePaireRepository.saveAndFlush(isPresentMasterValuePaire);

			for (ValuePaireDetailUpdateResource valuePaireDetailUpdateResource : masterValuePaireUpdateResource
					.getValuePaireDetails()) {
				if (valuePaireDetailUpdateResource.getId() != null
						&& Long.parseLong(valuePaireDetailUpdateResource.getId()) != 0) {
					updateValuePaireDetail(tenantId, createdUser,
							Long.parseLong(valuePaireDetailUpdateResource.getId()), isPresentMasterValuePaire,
							valuePaireDetailUpdateResource);
				} else {
					ValuePaireDetail valuePaireDeail = new ValuePaireDetail();
					valuePaireDeail.setTenantId(tenantId);
					valuePaireDeail.setValue(valuePaireDetailUpdateResource.getValue());
					valuePaireDeail.setType(valuePaireDetailUpdateResource.getType());
					valuePaireDeail.setMasterValues(isPresentMasterValuePaire);
					valuePaireDeail.setStatus(valuePaireDetailUpdateResource.getStatus());
					valuePaireDeail.setCreatedUser(createdUser);
					valuePaireDeail.setCreatedDate(getCreateOrModifyDate());
					valuePaireDeail.setSyncTs(getCreateOrModifyDate());
					valuePaireDetailRepository.saveAndFlush(valuePaireDeail);
				}
			}
			return isPresentMasterValuePaire;
		} else {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		}
	}

	private ValuePaireDetail updateValuePaireDetail(String tenantId, String createdUser, Long id,
			MasterValuePaire masterValuePaire, ValuePaireDetailUpdateResource valuePaireDetailUpdateResource) {

		Optional<ValuePaireDetail> isPresentValuePairDetail = valuePaireDetailRepository.findById(id);
		if (!isPresentValuePairDetail.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			ValuePaireDetail valuePaireDetail = isPresentValuePairDetail.get();
			valuePaireDetail.setTenantId(tenantId);
			valuePaireDetail.setValue(valuePaireDetailUpdateResource.getValue());
			valuePaireDetail.setType(valuePaireDetailUpdateResource.getType());
			valuePaireDetail.setMasterValues(masterValuePaire);
			valuePaireDetail.setStatus(valuePaireDetailUpdateResource.getStatus());
			valuePaireDetail.setModifiedUser(createdUser);
			valuePaireDetail.setModifiedDate(getCreateOrModifyDate());
			valuePaireDetail.setSyncTs(getCreateOrModifyDate());
			valuePaireDetail.setVersion(Long.parseLong(valuePaireDetailUpdateResource.getVersion()));

			valuePaireDetail = valuePaireDetailRepository.saveAndFlush(valuePaireDetail);
			return valuePaireDetail;
		}
	}
}
