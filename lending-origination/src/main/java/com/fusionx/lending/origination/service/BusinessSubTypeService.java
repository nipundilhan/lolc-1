package com.fusionx.lending.origination.service;

import com.fusionx.lending.origination.domain.BusinessSubType;
import com.fusionx.lending.origination.resource.BusinessSubTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessSubTypeUpdateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BusinessSubTypeService {

    List<BusinessSubType> findAll();

    Optional<BusinessSubType> findById(Long id);

    Optional<BusinessSubType> findByCode(String code);

    List<BusinessSubType> findByName(String name);

    List<BusinessSubType> findByStatus(String status);

    Long validateAndSaveBusinessSubType(String tenantId, String createdUser, BusinessSubTypeAddResource businessSubTypeAddResource);

    Boolean existsById(Long id);

    Long validateAndUpdateBusinessSubType(String tenantId, String createdUser, Long id, BusinessSubTypeUpdateResource businessSubTypeUpdateResource);

    Page<BusinessSubType> searchBusinessSubType(Long businessTypeId, String code, String name, Pageable pageable);
}
