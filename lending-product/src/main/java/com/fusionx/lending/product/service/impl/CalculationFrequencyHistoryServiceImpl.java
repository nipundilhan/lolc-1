package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CalculationFrequency;
import com.fusionx.lending.product.domain.CalculationFrequencyHistory;
import com.fusionx.lending.product.repository.CalculationFrequencyHistoryRepository;
import com.fusionx.lending.product.service.CalculationFrequencyHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * API Service related to Calculation Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  	Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6511       		Senitha Perera         		Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CalculationFrequencyHistoryServiceImpl implements CalculationFrequencyHistoryService {

    private CalculationFrequencyHistoryRepository calculationFrequencyHistoryRepository;

    @Autowired
    public void setCalculationFrequencyHistoryRepository(CalculationFrequencyHistoryRepository calculationFrequencyHistoryRepository) {
        this.calculationFrequencyHistoryRepository = calculationFrequencyHistoryRepository;
    }

    @Override
    public void insertCalculationFrequencyHistory(String tenantId, CalculationFrequency calculationFrequency) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        CalculationFrequencyHistory calculationFrequencyHistory = new CalculationFrequencyHistory();

        calculationFrequencyHistory.setTenantId(calculationFrequency.getTenantId());
        calculationFrequencyHistory.setCalculationFrequencyId(calculationFrequency.getId());
        calculationFrequencyHistory.setCode(calculationFrequency.getCode());
        calculationFrequencyHistory.setName(calculationFrequency.getName());
        calculationFrequencyHistory.setStatus(calculationFrequency.getStatus());
        calculationFrequencyHistory.setDescription(calculationFrequency.getDescription());
        calculationFrequencyHistory.setFrequencyTypeId(calculationFrequency.getFrequencyTypeId());
        calculationFrequencyHistory.setUnit(calculationFrequency.getUnit());
        calculationFrequencyHistory.setCreatedDate(calculationFrequency.getCreatedDate());
        calculationFrequencyHistory.setCreatedUser(calculationFrequency.getCreatedUser());
        calculationFrequencyHistory.setModifiedDate(calculationFrequency.getModifiedDate());
        calculationFrequencyHistory.setModifiedUser(calculationFrequency.getModifiedUser());
        calculationFrequencyHistory.setCalculationFrequencyVersion(calculationFrequency.getVersion());
        calculationFrequencyHistory.setSyncTs(currentTimestamp);
        calculationFrequencyHistory.setHistoryCreatedDate(currentTimestamp);
        calculationFrequencyHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        calculationFrequencyHistoryRepository.saveAndFlush(calculationFrequencyHistory);

    }

}
