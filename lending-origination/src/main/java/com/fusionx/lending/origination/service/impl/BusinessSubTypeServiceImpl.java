package com.fusionx.lending.origination.service.impl;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.SearchCriteria;
import com.fusionx.lending.origination.domain.BusinessSubType;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.SearchOperation;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessSubTypeRepository;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.resource.BusinessSubTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessSubTypeUpdateResource;
import com.fusionx.lending.origination.sepecification.BusinessSubTypeSpecification;
import com.fusionx.lending.origination.service.BusinessSubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessSubTypeServiceImpl implements BusinessSubTypeService {

    private final BusinessSubTypeRepository businessSubTypeRepository;

    private final Environment environment;

    private final BusinessTypeRepository businessTypeRepository;

    @Autowired
    public BusinessSubTypeServiceImpl(BusinessSubTypeRepository businessSubTypeRepository, Environment environment,
                                      BusinessTypeRepository businessTypeRepository) {
        this.businessSubTypeRepository = businessSubTypeRepository;
        this.environment = environment;
        this.businessTypeRepository = businessTypeRepository;
    }

    @Override
    public List<BusinessSubType> findAll() {
        return businessSubTypeRepository.findAll();
    }

    @Override
    public Optional<BusinessSubType> findById(Long id) {
        Optional<BusinessSubType> businessSubType = businessSubTypeRepository.findById(id);
        if (businessSubType.isPresent()) {
            return businessSubType;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BusinessSubType> findByCode(String code) {
        Optional<BusinessSubType> businessSubType = businessSubTypeRepository.findByCode(code);
        if (businessSubType.isPresent()) {
            return businessSubType;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<BusinessSubType> findByName(String name) {
        return businessSubTypeRepository.findByNameContaining(name);
    }

    @Override
    public List<BusinessSubType> findByStatus(String status) {
        return businessSubTypeRepository.findByStatus(status);
    }

    @Override
    public Long validateAndSaveBusinessSubType(String tenantId, String createdUser, BusinessSubTypeAddResource businessSubTypeAddResource) {

        LoggerRequest.getInstance().logInfo("BusinessSubType validate business type id and name");
        if (!businessTypeRepository.existsByIdAndName(businessSubTypeAddResource.getBusinessTypeId(), businessSubTypeAddResource.getBusinessTypeName())) {
            throw new ValidateRecordException(environment.getProperty("common.unique"), "businessTypeId");
        }

        LoggerRequest.getInstance().logInfo("BusinessSubType validate Code and status");
        if (businessSubTypeRepository.existsByCodeAndStatus(businessSubTypeAddResource.getCode(), CommonStatus.ACTIVE.toString())) {
            throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
        }

        LoggerRequest.getInstance().logInfo("BusinessSubType save Business Sub Type");
        BusinessSubType businessSubType = saveBusinessSubType(tenantId, createdUser, businessSubTypeAddResource);

        return businessSubType != null ? businessSubType.getId() : null;
    }

    @Override
    public Boolean existsById(Long id) {
        return businessSubTypeRepository.existsById(id);
    }

    @Override
    public Long validateAndUpdateBusinessSubType(String tenantId, String createdUser, Long id, BusinessSubTypeUpdateResource businessSubTypeUpdateResource) {

        LoggerRequest.getInstance().logInfo("BusinessSubType validate by id");
        Optional<BusinessSubType> optionalBusinessSubType = businessSubTypeRepository.findById(id);
        if (optionalBusinessSubType.isPresent()) {
            if (!optionalBusinessSubType.get().getVersion().equals(businessSubTypeUpdateResource.getVersion())) {
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
            }
        } else {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
        }

        LoggerRequest.getInstance().logInfo("BusinessSubType validate Code");
        Optional<BusinessSubType> optionalBusinessTypeByCode = businessSubTypeRepository.findByCodeAndIdNotIn(businessSubTypeUpdateResource.getCode(), id);
        if (optionalBusinessTypeByCode.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
        }

        LoggerRequest.getInstance().logInfo("BusinessSubType update Business Sub Type");
        BusinessSubType businessSubType = updateBusinessSubType(tenantId, createdUser, businessSubTypeUpdateResource, id);

        return businessSubType != null ? businessSubType.getId() : null;
    }

    @Override
    public Page<BusinessSubType> searchBusinessSubType(Long businessTypeId, String code, String name, Pageable pageable) {

        BusinessSubTypeSpecification businessSubTypeSpecification = new BusinessSubTypeSpecification();

        if (businessTypeId != null) {
            businessSubTypeSpecification.add(new SearchCriteria("businessType", businessTypeId, SearchOperation.EQUAL));
        }
        if (code != null && !code.isEmpty()) {
            businessSubTypeSpecification.add(new SearchCriteria("code", code, SearchOperation.LIKE));
        }
        if (name != null && !name.isEmpty()) {
            businessSubTypeSpecification.add(new SearchCriteria("name", name, SearchOperation.LIKE));
        }

        return businessSubTypeRepository.findAll(businessSubTypeSpecification, pageable);
    }

    private BusinessSubType updateBusinessSubType(String tenantId, String createdUser, BusinessSubTypeUpdateResource businessSubTypeUpdateResource, Long id) {
        Optional<BusinessType> optionalBusinessType = businessTypeRepository.findById(businessSubTypeUpdateResource.getBusinessTypeId());

        if (optionalBusinessType.isPresent()) {
            BusinessSubType businessSubType = businessSubTypeRepository.getOne(id);
            businessSubType.setTenantId(tenantId);
            businessSubType.setBusinessType(optionalBusinessType.get());
            businessSubType.setCode(businessSubTypeUpdateResource.getCode());
            businessSubType.setName(businessSubTypeUpdateResource.getName());
            businessSubType.setDescription(businessSubTypeUpdateResource.getDescription());
            businessSubType.setStatus(businessSubTypeUpdateResource.getStatus());
            businessSubType.setModifiedUser(createdUser);
            businessSubType.setModifiedDate(getCreateOrModifyDate());
            businessSubType.setSyncTs(getCreateOrModifyDate());

            return businessSubTypeRepository.saveAndFlush(businessSubType);
        }
        return null;
    }

    private BusinessSubType saveBusinessSubType(String tenantId, String createdUser, BusinessSubTypeAddResource businessSubTypeAddResource) {
        Optional<BusinessType> optionalBusinessType = businessTypeRepository.findById(businessSubTypeAddResource.getBusinessTypeId());

        if (optionalBusinessType.isPresent()) {
            BusinessSubType businessSubType = new BusinessSubType();
            businessSubType.setTenantId(tenantId);
            businessSubType.setBusinessType(optionalBusinessType.get());
            businessSubType.setCode(businessSubTypeAddResource.getCode());
            businessSubType.setName(businessSubTypeAddResource.getName());
            businessSubType.setDescription(businessSubTypeAddResource.getDescription());
            businessSubType.setStatus(businessSubTypeAddResource.getStatus());
            businessSubType.setCreatedUser(createdUser);
            businessSubType.setCreatedDate(getCreateOrModifyDate());
            businessSubType.setSyncTs(getCreateOrModifyDate());

            return businessSubTypeRepository.saveAndFlush(businessSubType);
        }
        return null;
    }

    private Timestamp getCreateOrModifyDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }

}
