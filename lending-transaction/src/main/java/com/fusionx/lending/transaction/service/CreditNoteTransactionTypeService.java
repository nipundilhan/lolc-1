package com.fusionx.lending.transaction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.domain.CreditNoteTransactionType;
import com.fusionx.lending.transaction.resource.CreditNoteTransactionTypeAddResource;
import com.fusionx.lending.transaction.resource.CreditNoteTransactionTypeUpdateResource;

@Service
public interface CreditNoteTransactionTypeService {
	
	 List<CreditNoteTransactionType> getAll();
	 
	 Optional<CreditNoteTransactionType> getById(Long id);
	 
	 List<CreditNoteTransactionType> getByStatus(String status);
	 
	 List<CreditNoteTransactionType> getByCreditNoteType(Long id);
	
	 Long create(CreditNoteTransactionTypeAddResource creditNoteTransactionTypeAddResource,String user , String tenantId);

	 Long update(CreditNoteTransactionTypeUpdateResource resource ,  Long creditNoteTransactionTypeId , String user , String tenantId);


}
