package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.repo.Frequency;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface FrequencyService {

    Optional<Frequency> findById(Long id);
}
