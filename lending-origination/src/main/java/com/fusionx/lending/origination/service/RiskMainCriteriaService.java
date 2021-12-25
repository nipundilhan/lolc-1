package com.fusionx.lending.origination.service;

import com.fusionx.lending.origination.domain.RiskMainCriteria;
import com.fusionx.lending.origination.resource.RiskMainCriteriaAddResource;
import com.fusionx.lending.origination.resource.RiskMainCriteriaUpdateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RiskMainCriteriaService {

    List<RiskMainCriteria> findAll();

    Optional<RiskMainCriteria> findById(Long id);

    Optional<RiskMainCriteria> findByCode(String code);

    List<RiskMainCriteria> findByName(String name);

    List<RiskMainCriteria> findByStatus(String status);

    Long validateAndSaveRiskMainCriteria(String tenantId, String createdUser, RiskMainCriteriaAddResource riskMainCriteriaAddResource);

    Boolean existsById(Long id);

    Long validateAndUpdateRiskMainCriteria(String tenantId, String createdUser, Long id, RiskMainCriteriaUpdateResource riskMainCriteriaUpdateResource);

    Page<RiskMainCriteria> searchRiskMainCriteria(String code, String name, String status, Pageable pageable);
}
