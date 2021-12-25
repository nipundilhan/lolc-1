package com.fusionx.lending.transaction.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.domain.TaxEvent;
import com.fusionx.lending.transaction.domain.TaxEventDetails;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.TaxEventDetailsRepository;
import com.fusionx.lending.transaction.repo.TaxEventRepository;
import com.fusionx.lending.transaction.service.TaxCalculationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class TaxCalculationServiceImpl  extends MessagePropertyBase  implements TaxCalculationService{
	
    @Autowired
    TaxEventDetailsRepository taxEventDetailsRepository;
    @Autowired
    TaxEventRepository taxEventRepository;
	
    @Override
	public Optional<TaxEvent> calculateTax(String taxEventCode) {
		
		Optional<TaxEvent> taxEventOptional = taxEventRepository.findByCode(taxEventCode);
		if(!taxEventOptional.isPresent()){
			throw new ValidateRecordException( RECORD_NOT_FOUND ,"message");
		}
		
		TaxEvent taxEvent = taxEventOptional.get();
				
		List<TaxEventDetails> taxEventDetailsList = taxEventDetailsRepository.findByTaxEventIdId(taxEventOptional.get().getId());
		taxEvent.setTaxEventDetails(taxEventDetailsList);
		
		return Optional.of(taxEvent);
	}

}
