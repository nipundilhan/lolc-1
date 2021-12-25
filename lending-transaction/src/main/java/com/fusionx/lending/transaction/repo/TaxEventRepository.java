package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.TaxEvent;
import com.fusionx.lending.transaction.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Tax Event
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   12-10-2021   FXL-1234      FXL-1131   Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@Repository
public interface TaxEventRepository extends JpaRepository<TaxEvent, Long> {

    List<TaxEvent> findByStatus(CommonStatus status);

    Optional<TaxEvent> findByCode(String code);

    Optional<TaxEvent> findByNameContaining(String name);

    Optional<TaxEvent> findByCodeAndStatus(String code, CommonStatus status);

}
