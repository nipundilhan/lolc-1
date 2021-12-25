package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.repo.Frequency;
import com.fusionx.lending.transaction.repo.FrequencyRepository;
import com.fusionx.lending.transaction.service.FrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Component
@Transactional(rollbackFor = Exception.class)
public class FrequencyServiceImpl implements FrequencyService {


    @Autowired
    private FrequencyRepository frequencyRepository;

    @Override
    public Optional<Frequency> findById(Long id) {
        return frequencyRepository.findById(id);
    }


}
