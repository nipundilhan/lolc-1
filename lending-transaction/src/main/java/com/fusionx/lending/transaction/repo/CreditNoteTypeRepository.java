package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.CreditNoteType;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.enums.CommonStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * CreditNoteType Service
 * <p>
 * *******************************************************************************************************
 * ###   	Date         	Story Point   	Task No    		Author       	Description
 * -------------------------------------------------------------------------------------------------------
 * 1   	09-AUG-2021      		     					Sanatha      	Initial Development
 * <p>
 * *******************************************************************************************************
 */
@Repository
public interface CreditNoteTypeRepository extends JpaRepository<CreditNoteType, Long> {

    Optional<CreditNoteType> findByCode(String code);

    Optional<Collection<CreditNoteType>> findByNameContaining(String name);

    Optional<Collection<CreditNoteType>> findByStatus(String status);

    Optional<CreditNoteType> findByCodeAndIdNotIn(String code, Long id);
    
    Optional<CreditNoteType> findByIdAndStatus(Long id, String status);

}
