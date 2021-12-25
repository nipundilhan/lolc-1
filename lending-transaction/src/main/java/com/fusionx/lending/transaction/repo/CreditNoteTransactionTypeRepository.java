package com.fusionx.lending.transaction.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.CreditNoteTransactionType;
import com.fusionx.lending.transaction.domain.CreditNoteType;
import com.fusionx.lending.transaction.enums.CommonStatus;

@Repository
public interface CreditNoteTransactionTypeRepository  extends JpaRepository<CreditNoteTransactionType, Long> {


    List<CreditNoteTransactionType> findByStatus(CommonStatus status);
    
    List<CreditNoteTransactionType> findByCreditNoteTypeId(Long id);
	
}


