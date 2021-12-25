package com.fusionx.lending.transaction.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.domain.TransEventAccStatus;
import com.fusionx.lending.transaction.domain.TransEventCreditNote;
import com.fusionx.lending.transaction.domain.TransEventSubCode;
import com.fusionx.lending.transaction.domain.TransactionEvent;
import com.fusionx.lending.transaction.resource.AddTransEventAccStatus;
import com.fusionx.lending.transaction.resource.AddTransEventSubCode;
import com.fusionx.lending.transaction.resource.TransEventCreditNoteAddResource;
import com.fusionx.lending.transaction.resource.TransEventCreditNoteUpdateResource;
import com.fusionx.lending.transaction.resource.TransSubCodeResponse;
import com.fusionx.lending.transaction.resource.TransactionEventAddResource;
import com.fusionx.lending.transaction.resource.TransactionEventUpdateResource;
import com.fusionx.lending.transaction.resource.UpdateTransEventAccStatus;
import com.fusionx.lending.transaction.resource.UpdateTransEventSubCode;

@Service
public interface TransactionEventService {

    public List<TransactionEvent> getAllTransactionEvents(Pageable pageable);

    public Optional<TransactionEvent> getTransactionEvents(Long id);

    public void save(String tenantId, AddTransEventSubCode addTransEventSubCode);

    List<TransEventSubCode> getAllTransEventSubCode(Pageable pageable);

    TransSubCodeResponse getTransEventSubCode(Long id);

    public void update(String tenantId, Long id, @Valid UpdateTransEventSubCode updateTransEventSubCode);

    public List<TransEventAccStatus> getAllTransEventAccStatus(Pageable pageable);

    public Optional<TransEventAccStatus> getTransEventAccStatus(Long id);

    public void saveAccStatus(String tenantId, @Valid AddTransEventAccStatus addTransEventAccStatus);

    void updateAccStatus(String tenantId, Long id, @Valid UpdateTransEventAccStatus updateTransEventSubCode);

    public List<?> findAll();

    public List<?> findAllSubCode();

    public List<?> findAllAccStatus();

    public List<TransSubCodeResponse> getTransEventSubCodeByStatus(String status);

    public List<TransactionEvent> getTransEventByStatus(String status);
    
    public List<TransactionEvent> getTransEventByDescription(String description);
    
    public Optional<TransactionEvent> getTransEventByCode(String code);

    public List<TransEventAccStatus> getTransEventAccStatusByStatus(String status);

    Optional<TransEventSubCode> findTransEventSubCode(Long id);

    public TransSubCodeResponse getTransEventSubCodeByeventCode(String eventCode);

    public List<TransEventAccStatus> getTransEventAccStatusByEventCode(String eventCode);

    /**
     * @author Senitha
     * @date 26-NOV-2020
     */
    Optional<TransEventSubCode> findTransEventSubCodeByEventCode(String transEventCode);
	
	
	/**
	 * @author Sanatha
	 * @date 09-Aug-2021
	 * */
	public void addTransactionEvent(String tenantId, @Valid TransactionEventAddResource transactionEventAddResource);
	
	public void updateTransactionEvent(String tenantId, TransactionEventUpdateResource transactionEventUpdateResource, Long id);
	
	public Optional<TransEventAccStatus> getTransEventAccStatusByEventCodeAndAccStatus(String accStatus,String transEventCode);
   
    /**
     * @author Sanatha
     * @date 09-Aug-2021
     */
    public void addTransactionEventAndCreditNote(String tenantId, TransEventCreditNoteAddResource transEventCreditNoteAddResource);
    
    public void updateTransactionEventAndCreditNote(String tenantId, TransEventCreditNoteUpdateResource transEventCreditNoteUpdateResource, Long id);
    
    public List<TransEventCreditNote> findAllCreditNote();
    
    public Optional<TransEventCreditNote> findCreditNoteById(Long id);
    
    public Optional<Collection<TransEventCreditNote>> findCreditNoteByStatus(String status);
    
    public List<TransEventCreditNote> findByCreditNoteId(Long creditNoteId);


}
