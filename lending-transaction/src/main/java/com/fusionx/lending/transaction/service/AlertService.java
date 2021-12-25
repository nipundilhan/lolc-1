package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.resource.AlertRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public interface AlertService {

    public String alert(String tenantId, String userName, @Valid AlertRequest alertRequest);

}
