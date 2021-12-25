package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.domain.Currency;
import com.fusionx.lending.transaction.repo.CurrencyRepository;
import com.fusionx.lending.transaction.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional(rollbackFor = Exception.class)
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Optional<Currency> findById(Long currencyId) {
        return currencyRepository.findById(currencyId);
    }
}
