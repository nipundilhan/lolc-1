package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.TaxCode;
import com.fusionx.lending.transaction.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Tax Code Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   11-10-2021      		     FXL-1130	Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@Repository
public interface TaxCodeRepository extends JpaRepository<TaxCode, Long> {

    List<TaxCode> findByStatus(CommonStatus status);

    Optional<TaxCode> findByCode(String code);

    Optional<TaxCode> findByNameContaining(String name);

    List<TaxCode> findByBankTransactionSubCodeNameContaining(String subTransactionType);

    List<TaxCode> findByBankTransactionCodeNameContaining(String transactionType);

}
