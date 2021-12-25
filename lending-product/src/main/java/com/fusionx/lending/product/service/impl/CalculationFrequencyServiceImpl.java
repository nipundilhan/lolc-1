package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CalculationFrequency;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CalculationFrequencyRepository;
import com.fusionx.lending.product.resources.ApplicationFrequencyAddResource;
import com.fusionx.lending.product.resources.ApplicationFrequencyUpdateResource;
import com.fusionx.lending.product.resources.FrequencyResponse;
import com.fusionx.lending.product.service.CalculationFrequencyHistoryService;
import com.fusionx.lending.product.service.CalculationFrequencyService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * API Service related to Calculation Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>s
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
public class CalculationFrequencyServiceImpl extends MessagePropertyBase implements CalculationFrequencyService {

    private ValidationService validationService;

    private CalculationFrequencyRepository calculationFrequencyRepository;

    private CalculationFrequencyHistoryService calculationFrequencyHistoryService;

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setCalculationFrequencyRepository(CalculationFrequencyRepository calculationFrequencyRepository) {
        this.calculationFrequencyRepository = calculationFrequencyRepository;
    }

    @Autowired
    public void setCalculationFrequencyHistoryService(CalculationFrequencyHistoryService calculationFrequencyHistoryService) {
        this.calculationFrequencyHistoryService = calculationFrequencyHistoryService;
    }

    @Override
    public List<CalculationFrequency> getAll(String tenantId) {

        List<CalculationFrequency> calculationFrequencies = calculationFrequencyRepository.findAll();
        for (CalculationFrequency applicationFrequency : calculationFrequencies) {
            setFrequencyTypeName(tenantId, applicationFrequency);
        }
        return calculationFrequencies;
    }

    @Override
    public Optional<CalculationFrequency> getById(String tenantId, Long id) {
        Optional<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyRepository.findById(id);

        if (isPresentCalculationFrequency.isPresent()) {
            CalculationFrequency calculationFrequency = isPresentCalculationFrequency.get();
            setFrequencyTypeName(tenantId, calculationFrequency);
            return Optional.ofNullable(calculationFrequency);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CalculationFrequency> getByCode(String tenantId, String code) {
        Optional<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyRepository.findByCode(code);

        if (isPresentCalculationFrequency.isPresent()) {
            CalculationFrequency calculationFrequency = isPresentCalculationFrequency.get();
            setFrequencyTypeName(tenantId, calculationFrequency);
            return Optional.ofNullable(calculationFrequency);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<CalculationFrequency> getByName(String tenantId, String name) {

        List<CalculationFrequency> calculationFrequencies = calculationFrequencyRepository.findByName(name);
        for (CalculationFrequency applicationFrequency : calculationFrequencies) {
            setFrequencyTypeName(tenantId, applicationFrequency);
        }
        return calculationFrequencies;
    }

    @Override
    public List<CalculationFrequency> getByStatus(String tenantId, String status) {
        List<CalculationFrequency> calculationFrequencies = calculationFrequencyRepository.findByStatus(CommonStatus.valueOf(status));

        for (CalculationFrequency applicationFrequency : calculationFrequencies) {
            setFrequencyTypeName(tenantId, applicationFrequency);
        }
        return calculationFrequencies;
    }

    @Override
    public CalculationFrequency addCalculationFrequency(String tenantId, ApplicationFrequencyAddResource addApplicationFrequencyResource) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");

        Optional<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyRepository.findByCode(addApplicationFrequencyResource.getCode());
        if (isPresentCalculationFrequency.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty("common.unique"), ServiceEntity.CODE, EntityPoint.APPLICATION_FREQUENCY);
        }

        LoggerRequest.getInstance().logInfo("CalculationFrequency ******************************** Validate Frequency Type and Unit *********************************************/");
        validationService.validateFrequency(tenantId, Long.parseLong(addApplicationFrequencyResource.getFrequencyTypeId()), addApplicationFrequencyResource.getFrequencyTypeName(), Long.parseLong(addApplicationFrequencyResource.getUnit()),
                EntityPoint.CALCULATION_FREQUENCY);

        CalculationFrequency calculationFrequency = new CalculationFrequency();
        calculationFrequency.setTenantId(tenantId);
        calculationFrequency.setCode(addApplicationFrequencyResource.getCode());
        calculationFrequency.setName(addApplicationFrequencyResource.getName());
        calculationFrequency.setDescription(addApplicationFrequencyResource.getDescription());
        calculationFrequency.setFrequencyTypeId(Long.parseLong(addApplicationFrequencyResource.getFrequencyTypeId()));
        calculationFrequency.setUnit(Long.parseLong(addApplicationFrequencyResource.getUnit()));
        calculationFrequency.setStatus(CommonStatus.valueOf(addApplicationFrequencyResource.getStatus()));
        calculationFrequency.setSyncTs(currentTimestamp);
        calculationFrequency.setCreatedDate(currentTimestamp);
        calculationFrequency.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        calculationFrequency = calculationFrequencyRepository.save(calculationFrequency);
        return calculationFrequency;

    }

    @Override
    public CalculationFrequency updateCalculationFrequency(String tenantId, ApplicationFrequencyUpdateResource updateApplicationFrequencyResource) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currenTimestamp = new java.sql.Timestamp(now.getTime());

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");

        Optional<CalculationFrequency> isPresentCalculationFrequency = calculationFrequencyRepository.findById(Long.parseLong(updateApplicationFrequencyResource.getId()));
        if (isPresentCalculationFrequency.isPresent()) {

            if (!isPresentCalculationFrequency.get().getVersion().equals(Long.parseLong(updateApplicationFrequencyResource.getVersion()))) {
                throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.VERSION, EntityPoint.APPLICATION_FREQUENCY);
            }

            LoggerRequest.getInstance().logInfo("CalculationFrequency ******************************** Code Can Not Update *********************************************");
            if (!isPresentCalculationFrequency.get().getCode().equals(updateApplicationFrequencyResource.getCode())) {
                throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.APPLICATION_FREQUENCY);
            }

            LoggerRequest.getInstance().logInfo("CalculationFrequency ******************************** Validate Frequency Type and Unit *********************************************/");
            validationService.validateFrequency(tenantId, Long.parseLong(updateApplicationFrequencyResource.getFrequencyTypeId()), updateApplicationFrequencyResource.getFrequencyTypeName(), Long.parseLong(updateApplicationFrequencyResource.getUnit()),
                    EntityPoint.CALCULATION_FREQUENCY);

            CalculationFrequency calculationFrequency = isPresentCalculationFrequency.get();

            LoggerRequest.getInstance().logInfo("CalculationFrequency ******************************** Save Calculation Frequency History *********************************************");
            calculationFrequencyHistoryService.insertCalculationFrequencyHistory(tenantId, calculationFrequency);

            calculationFrequency.setTenantId(tenantId);
            calculationFrequency.setCode(updateApplicationFrequencyResource.getCode());
            calculationFrequency.setName(updateApplicationFrequencyResource.getName());
            calculationFrequency.setDescription(updateApplicationFrequencyResource.getDescription());
            calculationFrequency.setFrequencyTypeId(Long.parseLong(updateApplicationFrequencyResource.getFrequencyTypeId()));
            calculationFrequency.setUnit(Long.parseLong(updateApplicationFrequencyResource.getUnit()));
            calculationFrequency.setStatus(CommonStatus.valueOf(updateApplicationFrequencyResource.getStatus()));
            calculationFrequency.setSyncTs(currenTimestamp);
            calculationFrequency.setModifiedDate(currenTimestamp);
            calculationFrequency.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
            calculationFrequency = calculationFrequencyRepository.saveAndFlush(calculationFrequency);
            return calculationFrequency;

        } else {
            throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        }
    }

    private void setFrequencyTypeName(String tenantId, CalculationFrequency calculationFrequency) {

        FrequencyResponse frequencyResponse = validationService.getFrequency(tenantId, calculationFrequency.getFrequencyTypeId(), EntityPoint.APPLICATION_FREQUENCY);
        calculationFrequency.setFrequencyTypeName(frequencyResponse.getName());

    }

}
