package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.CreditNoteType;
import com.fusionx.lending.transaction.domain.WaiveOffType;
import com.fusionx.lending.transaction.domain.WaiveOffTypeHistory;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.CreditNoteTypeRepository;
import com.fusionx.lending.transaction.repo.WaiveOffTypeHistoryRepository;
import com.fusionx.lending.transaction.repo.WaiveOffTypeRepository;
import com.fusionx.lending.transaction.resource.WaiveOffTypeAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffTypeUpdateResource;
import com.fusionx.lending.transaction.service.ValidationService;
import com.fusionx.lending.transaction.service.WaiveOffTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(rollbackFor = Exception.class)
public class WaiveOffTypeServiceImpl extends MessagePropertyBase implements WaiveOffTypeService {

    @Autowired
    private WaiveOffTypeRepository waiveOffTypeRepository;
    @Autowired
    private CreditNoteTypeRepository creditNoteTypeRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private WaiveOffTypeHistoryRepository waiveOffTypeHistoryRepository;

    @Override
    public List<WaiveOffType> findAll() {
        return waiveOffTypeRepository.findAll();
    }

    @Override
    public Optional<WaiveOffType> findById(Long waiveOffTypeId) {
        return waiveOffTypeRepository.findById(waiveOffTypeId);
    }

    @Override
    public List<WaiveOffType> findByStatus(String status) {
        return waiveOffTypeRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public List<WaiveOffType> findByName(String name) {
        return waiveOffTypeRepository.findByNameContaining(name);
    }

    @Override
    public List<WaiveOffType> findByCode(String code) {
        return waiveOffTypeRepository.findByCode(code);
    }

    @Override
    public WaiveOffType addWaiveOffType(String tenantId, WaiveOffTypeAddResource waiveOffTypeAddResource) {
        Optional<WaiveOffType> optWaiveOffType = waiveOffTypeRepository.findByCodeAndStatus(waiveOffTypeAddResource.getCode(), CommonStatus.ACTIVE);
        if (optWaiveOffType.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
        }
        Optional<CreditNoteType> optCreditNoteType = creditNoteTypeRepository.findByIdAndStatus(Long.valueOf(waiveOffTypeAddResource.getCreditNoteTypeId()), CommonStatus.ACTIVE.toString());
        if (!optCreditNoteType.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "creditNoteTypeId");
        }

        WaiveOffType waiveOffType = new WaiveOffType();
        waiveOffType.setTenantId(tenantId);
        waiveOffType.setCode(waiveOffTypeAddResource.getCode());
        waiveOffType.setName(waiveOffTypeAddResource.getName());
        waiveOffType.setDescription(waiveOffTypeAddResource.getDescription());
        waiveOffType.setStatus(CommonStatus.valueOf(waiveOffTypeAddResource.getStatus()));
        waiveOffType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        waiveOffType.setCreatedDate(validationService.getCreateOrModifyDate());
        waiveOffType.setCreditNoteType(optCreditNoteType.get());
        waiveOffType.setSyncTs(validationService.getCreateOrModifyDate());
        waiveOffType = waiveOffTypeRepository.save(waiveOffType);
        return waiveOffType;
    }

    @Override
    public WaiveOffType updateWaiveOffType(String tenantId, Long id, WaiveOffTypeUpdateResource waiveOffTypeUpdateResource) {
        Optional<WaiveOffType> optWaiveOffType = waiveOffTypeRepository.findById(id);

        if (optWaiveOffType.isPresent()) {
            WaiveOffType theWaiveOffType = optWaiveOffType.get();

            if (!theWaiveOffType.getVersion().equals(Long.parseLong(waiveOffTypeUpdateResource.getVersion()))) {
                throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
            }

            if (!theWaiveOffType.getCode().equalsIgnoreCase(waiveOffTypeUpdateResource.getCode())) {
                throw new ValidateRecordException(environment.getProperty(COMMON_CODE_UPDATE), "code");
            }

            String theCode = theWaiveOffType.getCode();

            if(waiveOffTypeUpdateResource.getStatus().equals(CommonStatus.ACTIVE.toString())) {
            	Optional<WaiveOffType> fetchedWaiveOffType = waiveOffTypeRepository.findByCodeAndStatusAndIdNotIn(theCode, CommonStatus.ACTIVE, id);
                if (fetchedWaiveOffType.isPresent()) {
                    throw new ValidateRecordException(environment.getProperty(COMMON_CODE_UPDATE), "code");
                }
            }
            

            Optional<CreditNoteType> optCreditNoteType = creditNoteTypeRepository.findByIdAndStatus(Long.valueOf(waiveOffTypeUpdateResource.getCreditNoteTypeId()), CommonStatus.ACTIVE.toString());
            if (!optCreditNoteType.isPresent()) {
                throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "creditNoteTypeId");
            }

            WaiveOffType waiveOffType = optWaiveOffType.get();
            waiveOffType.setTenantId(tenantId);
            waiveOffType.setName(waiveOffTypeUpdateResource.getName());
            waiveOffType.setCode(waiveOffTypeUpdateResource.getCode());
            waiveOffType.setStatus(CommonStatus.valueOf(waiveOffTypeUpdateResource.getStatus()));
            waiveOffType.setDescription(waiveOffTypeUpdateResource.getDescription());
            waiveOffType.setModifiedDate(validationService.getCreateOrModifyDate());
            waiveOffType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
            waiveOffType.setCreditNoteType(optCreditNoteType.get());
            waiveOffType = waiveOffTypeRepository.save(waiveOffType);

            addWaiveOffTypeHistoryDetails(tenantId, waiveOffType);

            return waiveOffType;

        } else {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);

        }

    }

    private WaiveOffTypeHistory addWaiveOffTypeHistoryDetails(String tenantId, WaiveOffType waiveOffType) {
        WaiveOffTypeHistory waiveOffTypeHistory = new WaiveOffTypeHistory();
        waiveOffTypeHistory.setTenantId(tenantId);
        waiveOffTypeHistory.setCode(waiveOffType.getCode());
        waiveOffTypeHistory.setName(waiveOffType.getName());
        waiveOffTypeHistory.setDescription(waiveOffType.getDescription());
        waiveOffTypeHistory.setStatus(CommonStatus.valueOf(waiveOffType.getStatus().toString()));
        waiveOffTypeHistory.setCreatedUser(waiveOffType.getCreatedUser());
        waiveOffTypeHistory.setCreatedDate(waiveOffType.getCreatedDate());
        waiveOffTypeHistory.setCreditNoteType(waiveOffType.getCreditNoteType());
        waiveOffTypeHistory.setSyncTs(validationService.getCreateOrModifyDate());
        waiveOffTypeHistory.setWaiveOffTypeId(waiveOffType.getId());
        waiveOffTypeHistory.setModifiedUser(waiveOffType.getModifiedUser());
        waiveOffTypeHistory.setModifiedDate(waiveOffType.getModifiedDate());
        waiveOffTypeHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        waiveOffTypeHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        waiveOffTypeHistory.setVersion(waiveOffType.getVersion());
        return waiveOffTypeHistoryRepository.save(waiveOffTypeHistory);
    }

}
