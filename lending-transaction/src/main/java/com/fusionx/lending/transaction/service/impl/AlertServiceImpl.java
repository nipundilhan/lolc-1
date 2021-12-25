package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.resource.AlertRequest;
import com.fusionx.lending.transaction.service.AlertService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@Transactional(rollbackFor = Exception.class)
public class AlertServiceImpl implements AlertService {

    @Override
    public String alert(String tenantId, String userName, @Valid AlertRequest alertRequest) {
        // TODO Auto-generated method stub
        return null;
    }

}
