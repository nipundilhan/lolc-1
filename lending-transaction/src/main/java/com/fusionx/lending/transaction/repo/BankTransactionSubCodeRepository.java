package com.fusionx.lending.transaction.repo;

/**
 * Bank Transaction Code service
 *
 * @author Nisalak
 * @Dat 29-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   29-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.enums.AllowDormant;
import com.fusionx.lending.transaction.enums.BankTransactionSubCodeStatus;
import com.fusionx.lending.transaction.enums.PostingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BankTransactionSubCodeRepository extends JpaRepository<BankTransactionSubCode, Long> {

    List<BankTransactionSubCode> findByBankTransactionCodeId(Long transCodeId);

    Optional<BankTransactionSubCode> findByBankTransactionCodeIdAndSubCode(Long transCodeId, String code);

    List<BankTransactionSubCode> findByBankTransactionCodeIdAndStatus(Long transCodeId, BankTransactionSubCodeStatus status);

    List<BankTransactionSubCode> findByAllowDormantAndStatus(AllowDormant allowDormant, BankTransactionSubCodeStatus status);

    Optional<BankTransactionSubCode> findByIdAndBankTransactionCodeIdAndStatus(Long id, Long bankTrasactionId, BankTransactionSubCodeStatus status);

    Optional<BankTransactionSubCode> findByIdAndBankTransactionCodeIdAndStatusAndPostingType(Long id, Long bankTrasactionId, BankTransactionSubCodeStatus status, PostingType postingType);

    Optional<BankTransactionSubCode> findBySubCode(String bankTransactionSubCode);
    
    List<BankTransactionSubCode> findByBankTransactionCodeIdAndDescriptionContains(Long transCodeId, String description);
    
    Optional<BankTransactionSubCode> findByIdAndStatus(Long id, BankTransactionSubCodeStatus status);
}
