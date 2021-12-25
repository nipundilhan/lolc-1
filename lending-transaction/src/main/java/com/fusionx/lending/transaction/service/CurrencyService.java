package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.domain.Currency;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CurrencyService {

    public Optional<Currency> findById(Long currencyId);
}
