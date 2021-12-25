package com.fusionx.lending.origination.repository;

import com.fusionx.lending.origination.domain.RiskMainCriteria;
import com.fusionx.lending.origination.domain.RiskSubCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RiskSubCriteriaRepository extends JpaRepository<RiskSubCriteria, Long>, JpaSpecificationExecutor<RiskSubCriteria> {

    Optional<RiskSubCriteria> findByCode(String code);

    List<RiskSubCriteria> findByNameContaining(String name);

    List<RiskSubCriteria> findByStatus(String status);

    Boolean existsByCodeAndStatus(String code, String status);

    Optional<RiskSubCriteria> findByCodeAndIdNotIn(String code, Long id);
    
    public Optional<RiskSubCriteria> findByIdAndStatus(Long id, String status);
}
