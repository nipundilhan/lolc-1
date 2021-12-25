package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.domain.ApplicationFrequencyHistory;
import com.fusionx.lending.product.repository.ApplicationFrequencyHistoryRepository;
import com.fusionx.lending.product.service.ApplicationFrequencyHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * API Service related to Application Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6511             Senitha Perera          Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class ApplicationFrequencyHistoryServiceImpl implements ApplicationFrequencyHistoryService {

    @Autowired
    private ApplicationFrequencyHistoryRepository applicationFrequencyHistoryRepository;

    @Override
    public void insertApplicationFrequencyHistory(String tenantId, ApplicationFrequency applicationFrequency) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        ApplicationFrequencyHistory applicationFrequencyHistory = new ApplicationFrequencyHistory();

        applicationFrequencyHistory.setTenantId(applicationFrequency.getTenantId());
        applicationFrequencyHistory.setApplicactionFrequencyId(applicationFrequency.getId());
        applicationFrequencyHistory.setCode(applicationFrequency.getCode());
        applicationFrequencyHistory.setName(applicationFrequency.getName());
        applicationFrequencyHistory.setStatus(applicationFrequency.getStatus());
        applicationFrequencyHistory.setDescription(applicationFrequency.getDescription());
        applicationFrequencyHistory.setFrequencyTypeId(applicationFrequency.getFrequencyTypeId());
        applicationFrequencyHistory.setUnit(applicationFrequency.getUnit());
        applicationFrequencyHistory.setCreatedDate(applicationFrequency.getCreatedDate());
        applicationFrequencyHistory.setCreatedUser(applicationFrequency.getCreatedUser());
        applicationFrequencyHistory.setModifiedDate(applicationFrequency.getModifiedDate());
        applicationFrequencyHistory.setModifiedUser(applicationFrequency.getModifiedUser());
        applicationFrequencyHistory.setApplicactionFrequencyVersion(applicationFrequency.getVersion());
        applicationFrequencyHistory.setSyncTs(currentTimestamp);
        applicationFrequencyHistory.setHistoryCreatedDate(currentTimestamp);
        applicationFrequencyHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        applicationFrequencyHistoryRepository.saveAndFlush(applicationFrequencyHistory);

    }

}
