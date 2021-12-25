package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

@Service
public interface SerialTableService {

	long refernceNumberGeneration(Class<?> clz, String tenantId);

}
