package com.fusionx.lending.transaction.repo;

/**
 * Bank Transaction Code service
 *
 * @author Nisalak
 * @Dat 28-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   28-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


@Repository
public interface BankTransactionCodeRepository extends JpaRepository<BankTransactionCode, Long> {

    Optional<BankTransactionCode> findByCode(String code);

    Optional<Collection<BankTransactionCode>> findByStatus(BankTransactionCodeStatus status);

    Optional<BankTransactionCode> findByIdAndStatus(Long id, BankTransactionCodeStatus status);
    
    Optional<Collection<BankTransactionCode>> findByDescriptionContains(String description);

}
