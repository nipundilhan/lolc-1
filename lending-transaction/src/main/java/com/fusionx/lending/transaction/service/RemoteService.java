package com.fusionx.lending.transaction.service;

import org.springframework.stereotype.Service;

@Service
public interface RemoteService {

    public Object checkIsExist(String tenantId, String id, String serviceUrl, Class<?> classObject);


}
