package com.fusionx.lending.origination.repository;

import com.fusionx.lending.origination.domain.BusinessSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BusinessSubTypeRepository extends JpaRepository<BusinessSubType, Long>, JpaSpecificationExecutor<BusinessSubType> {

    Optional<BusinessSubType> findByCode(String code);

    List<BusinessSubType> findByNameContaining(String name);

    List<BusinessSubType> findByStatus(String status);

    Boolean existsByCodeAndStatus(String code, String status);

    Optional<BusinessSubType> findByCodeAndIdNotIn(String code, Long id);
}
