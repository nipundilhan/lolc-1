package com.fusionx.lending.origination.repository;

import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskMainCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RiskMainCriteriaRepository extends JpaRepository<RiskMainCriteria, Long>, JpaSpecificationExecutor<RiskMainCriteria> {

    Optional<RiskMainCriteria> findByCode(String code);

    List<RiskMainCriteria> findByNameContaining(String name);

    List<RiskMainCriteria> findByStatus(String status);

    Boolean existsByCodeAndStatus(String code, String status);

    Optional<RiskMainCriteria> findByCodeAndIdNotIn(String code, Long id);
    
    public Optional<RiskMainCriteria> findByIdAndStatus(Long id, String status);
}
