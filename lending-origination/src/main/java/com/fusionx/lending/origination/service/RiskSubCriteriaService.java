package com.fusionx.lending.origination.service;

import com.fusionx.lending.origination.domain.RiskSubCriteria;
import com.fusionx.lending.origination.resource.RiskSubCriteriaAddResource;
import com.fusionx.lending.origination.resource.RiskSubCriteriaUpdateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RiskSubCriteriaService {

    List<RiskSubCriteria> findAll();

    Optional<RiskSubCriteria> findById(Long id);

    Optional<RiskSubCriteria> findByCode(String code);

    List<RiskSubCriteria> findByName(String name);

    List<RiskSubCriteria> findByStatus(String status);

    Long validateAndSaveRiskSubCriteria(String tenantId, String createdUser, RiskSubCriteriaAddResource riskSubCriteriaAddResource);

    Boolean existsById(Long id);

    Long validateAndUpdateRiskSubCriteria(String tenantId, String createdUser, Long id, RiskSubCriteriaUpdateResource riskSubCriteriaUpdateResource);

    Page<RiskSubCriteria> searchRiskSubCriteria(String code, String name, String status, Pageable pageable);
}
