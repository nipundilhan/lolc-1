package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ApplicationFrequencyRepository;
import com.fusionx.lending.product.resources.ApplicationFrequencyAddResource;
import com.fusionx.lending.product.resources.ApplicationFrequencyUpdateResource;
import com.fusionx.lending.product.resources.FrequencyResponse;
import com.fusionx.lending.product.service.ApplicationFrequencyHistoryService;
import com.fusionx.lending.product.service.ApplicationFrequencyService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;


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
public class ApplicationFrequencyServiceImpl extends MessagePropertyBase implements ApplicationFrequencyService {

    @Autowired
    private ApplicationFrequencyRepository applicationFrequencyRepository;

    @Autowired
    private ApplicationFrequencyHistoryService applicationFrequencyHistoryService;

    @Autowired
    private ValidationService validationService;

    @Override
    public List<ApplicationFrequency> getAll(String tenantId) {
        List<ApplicationFrequency> applicationFrequencies = applicationFrequencyRepository.findAll();

        for (ApplicationFrequency applicationFrequency : applicationFrequencies) {
            setFrequencyTypeName(tenantId, applicationFrequency);
        }

        return applicationFrequencies;
    }

    @Override
    public Optional<ApplicationFrequency> getById(String tenantId, Long id) {
        Optional<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyRepository.findById(id);

        if (isPresentApplicationFrequency.isPresent()) {
            ApplicationFrequency applicationFrequency = isPresentApplicationFrequency.get();
            setFrequencyTypeName(tenantId, applicationFrequency);
            return Optional.ofNullable(applicationFrequency);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ApplicationFrequency> getByCode(String tenantId, String code) {
        Optional<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyRepository.findByCode(code);

        if (isPresentApplicationFrequency.isPresent()) {
            ApplicationFrequency applicationFrequency = isPresentApplicationFrequency.get();
            setFrequencyTypeName(tenantId, applicationFrequency);
            return Optional.ofNullable(applicationFrequency);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<ApplicationFrequency> getByName(String tenantId, String name) {

        List<ApplicationFrequency> applicationFrequencies = applicationFrequencyRepository.findByName(name);

        for (ApplicationFrequency applicationFrequency : applicationFrequencies) {
            setFrequencyTypeName(tenantId, applicationFrequency);
        }

        return applicationFrequencies;
    }

    @Override
    public List<ApplicationFrequency> getByStatus(String tenantId, String status) {

        List<ApplicationFrequency> applicationFrequencies = applicationFrequencyRepository.findByStatus(CommonStatus.valueOf(status));
        for (ApplicationFrequency applicationFrequency : applicationFrequencies) {
            setFrequencyTypeName(tenantId, applicationFrequency);
        }

        return applicationFrequencies;

    }

    @Override
    public ApplicationFrequency addApplicationFrequency(String tenantId, ApplicationFrequencyAddResource addApplicationFrequencyResource) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");

        Optional<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyRepository.findByCode(addApplicationFrequencyResource.getCode());
        if (isPresentApplicationFrequency.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty("common.unique"), ServiceEntity.CODE, EntityPoint.APPLICATION_FREQUENCY);
        }

        LoggerRequest.getInstance().logInfo("ApplicationFrequency ******************************** Validate Frequency Type and Unit *********************************************/");
        validationService.validateFrequency(tenantId, Long.parseLong(addApplicationFrequencyResource.getFrequencyTypeId()), addApplicationFrequencyResource.getFrequencyTypeName(), Long.parseLong(addApplicationFrequencyResource.getUnit()),
                EntityPoint.APPLICATION_FREQUENCY);

        ApplicationFrequency applicationFrequency = new ApplicationFrequency();
        applicationFrequency.setTenantId(tenantId);
        applicationFrequency.setCode(addApplicationFrequencyResource.getCode());
        applicationFrequency.setName(addApplicationFrequencyResource.getName());
        applicationFrequency.setDescription(addApplicationFrequencyResource.getDescription());
        applicationFrequency.setFrequencyTypeId(Long.parseLong(addApplicationFrequencyResource.getFrequencyTypeId()));
        applicationFrequency.setUnit(Long.parseLong(addApplicationFrequencyResource.getUnit()));
        applicationFrequency.setStatus(CommonStatus.valueOf(addApplicationFrequencyResource.getStatus()));
        applicationFrequency.setSyncTs(currentTimestamp);
        applicationFrequency.setCreatedDate(currentTimestamp);
        applicationFrequency.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        applicationFrequency = applicationFrequencyRepository.save(applicationFrequency);
        return applicationFrequency;

    }

    @Override
    public ApplicationFrequency updateApplicationFrequency(String tenantId, ApplicationFrequencyUpdateResource updateApplicationFrequencyResource) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currenTimestamp = new java.sql.Timestamp(now.getTime());

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");

        Optional<ApplicationFrequency> isPresentApplicationFrequency = applicationFrequencyRepository.findById(Long.parseLong(updateApplicationFrequencyResource.getId()));
        if (isPresentApplicationFrequency.isPresent()) {

            if (!isPresentApplicationFrequency.get().getVersion().equals(Long.parseLong(updateApplicationFrequencyResource.getVersion()))) {
                throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.VERSION, EntityPoint.APPLICATION_FREQUENCY);
            }

            LoggerRequest.getInstance().logInfo("ApplicationFrequency ******************************** Code Can Not Update *********************************************");
            if (!isPresentApplicationFrequency.get().getCode().equals(updateApplicationFrequencyResource.getCode())) {
                throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.APPLICATION_FREQUENCY);
            }

            LoggerRequest.getInstance().logInfo("ApplicationFrequency ******************************** Validate Frequency Type and Unit *********************************************/");
            validationService.validateFrequency(tenantId, Long.parseLong(updateApplicationFrequencyResource.getFrequencyTypeId()), updateApplicationFrequencyResource.getFrequencyTypeName(), Long.parseLong(updateApplicationFrequencyResource.getUnit()),
                    EntityPoint.APPLICATION_FREQUENCY);

            ApplicationFrequency applicationFrequency = isPresentApplicationFrequency.get();

            LoggerRequest.getInstance().logInfo("ApplicationFrequency ******************************** Save Application Frequency History *********************************************");
            applicationFrequencyHistoryService.insertApplicationFrequencyHistory(tenantId, applicationFrequency);

            applicationFrequency.setTenantId(tenantId);
            applicationFrequency.setCode(updateApplicationFrequencyResource.getCode());
            applicationFrequency.setName(updateApplicationFrequencyResource.getName());
            applicationFrequency.setDescription(updateApplicationFrequencyResource.getDescription());
            applicationFrequency.setFrequencyTypeId(Long.parseLong(updateApplicationFrequencyResource.getFrequencyTypeId()));
            applicationFrequency.setUnit(Long.parseLong(updateApplicationFrequencyResource.getUnit()));
            applicationFrequency.setStatus(CommonStatus.valueOf(updateApplicationFrequencyResource.getStatus()));
            applicationFrequency.setSyncTs(currenTimestamp);
            applicationFrequency.setModifiedDate(currenTimestamp);
            applicationFrequency.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
            applicationFrequency = applicationFrequencyRepository.saveAndFlush(applicationFrequency);
            return applicationFrequency;

        } else {
            throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        }
    }

    private void setFrequencyTypeName(String tenantId, ApplicationFrequency applicationFrequency) {
        FrequencyResponse frequencyResponse = validationService.getFrequency(tenantId, applicationFrequency.getFrequencyTypeId(), EntityPoint.APPLICATION_FREQUENCY);
        applicationFrequency.setFrequencyTypeName(frequencyResponse.getName());

    }

}
