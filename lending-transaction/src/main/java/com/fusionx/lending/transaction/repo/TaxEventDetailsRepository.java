package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.TaxEventDetails;
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
 * 1   13-10-2021   FXL-1234      FXL-1131   Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@Repository
public interface TaxEventDetailsRepository extends JpaRepository<TaxEventDetails, Long> {

    List<TaxEventDetails> findByStatus(CommonStatus status);

    List<TaxEventDetails> findByCode(String code);

    Optional<TaxEventDetails> findByNameContaining(String name);

    List<TaxEventDetails> findByTaxEventIdId(Long taxEventId);

}
