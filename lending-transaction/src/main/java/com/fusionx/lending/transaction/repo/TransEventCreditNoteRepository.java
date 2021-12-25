package com.fusionx.lending.transaction.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.TransEventCreditNote;

@Repository
public interface TransEventCreditNoteRepository extends JpaRepository<TransEventCreditNote, Long>{

	Optional<TransEventCreditNote> findByTransactionEventIdAndStatus(Long eventId, String status);
	
	Optional<TransEventCreditNote> findByCreditNoteTypeIdAndTransactionEventId(Long creditNoteId, Long eventId);
	
	Optional<TransEventCreditNote> findByCreditNoteTypeIdAndTransactionEventIdAndIdNotIn(Long creditNoteId, Long eventId, Long id);
	
	Optional<TransEventCreditNote> findByTransactionEventIdAndIdNotIn(Long eventId, Long id);
	
	Optional<Collection<TransEventCreditNote>> findByStatus(String status);
	
	List<TransEventCreditNote> findByCreditNoteTypeId(Long creditNoteType);
	
}
