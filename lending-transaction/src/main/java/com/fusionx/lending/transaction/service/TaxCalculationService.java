package com.fusionx.lending.transaction.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.domain.TaxEvent;

@Service
public interface TaxCalculationService {
	
	Optional<TaxEvent> calculateTax(String taxEventCode);

}
