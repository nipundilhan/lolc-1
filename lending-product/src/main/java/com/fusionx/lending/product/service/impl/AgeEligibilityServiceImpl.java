package com.fusionx.lending.product.service.impl;


import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.AgeEligibility;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.repository.AgeEligibilityRepository;
import com.fusionx.lending.product.resources.AgeEligibilityAddResource;
import com.fusionx.lending.product.resources.AgeEligibilityUpdateResource;
import com.fusionx.lending.product.service.AgeEligibilityHistoryService;
import com.fusionx.lending.product.service.AgeEligibilityService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Age eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        17-07-2021      -               -           Menuka Jayasinghe      Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class AgeEligibilityServiceImpl extends MessagePropertyBase implements AgeEligibilityService {
    @Autowired
    private AgeEligibilityRepository ageEligibilityRepository;

    @Autowired
    private AgeEligibilityHistoryService ageEligibilityHistoryService;

    @Autowired
    private ValidationService validationService;

    public static final String CAN_NOT_LESS_ZERO = "common.value-cannot-lessthan-zero";
    public static final String INVALID_MAX_AGE = "maximumAge.invalid";

    @Override
    public List<AgeEligibility> findAll() {
        return ageEligibilityRepository.findAll();
    }

    @Override
    public Optional<AgeEligibility> findById(Long id) {
        return ageEligibilityRepository.findById(id);
    }


    @Override
    public List<AgeEligibility> findByStatus(String status) {
        return ageEligibilityRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public AgeEligibility addAgeEligibility(String tenantId, AgeEligibilityAddResource ageEligibilityAddResource) {

        validateMinMaxAge(ageEligibilityAddResource.getMinimumAge(), ageEligibilityAddResource.getMaximumAge());

        AgeEligibility ageEligibility = new AgeEligibility();
        ageEligibility.setTenantId(tenantId);
        ageEligibility.setMinimumAge(validationService.stringToLong(ageEligibilityAddResource.getMinimumAge()));
        ageEligibility.setMaximumAge(validationService.stringToLong(ageEligibilityAddResource.getMaximumAge()));
        ageEligibility.setStatus(CommonStatus.valueOf(ageEligibilityAddResource.getStatus()));
        ageEligibility.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        ageEligibility.setCreatedDate(validationService.getCreateOrModifyDate());
        ageEligibility.setSyncTs(validationService.getCreateOrModifyDate());
        ageEligibility = ageEligibilityRepository.saveAndFlush(ageEligibility);
        return ageEligibility;
    }

    @Override
    public AgeEligibility updateAgeEligibility(String tenantId, AgeEligibility ageEligi, AgeEligibilityUpdateResource ageEligibilityUpdateResource, String username) {

        AgeEligibility ageEligibility = ageEligi;

        if (!ageEligibility.getVersion().equals(Long.parseLong(ageEligibilityUpdateResource.getVersion())))
            throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.AGE_ELIGIBILITY);

        ageEligibilityHistoryService.insertAgeEligibilityHistory(tenantId, ageEligibility, username);

        validateMinMaxAge(ageEligibilityUpdateResource.getMinimumAge(), ageEligibilityUpdateResource.getMaximumAge());

        ageEligibility.setMinimumAge(validationService.stringToLong(ageEligibilityUpdateResource.getMinimumAge()));
        ageEligibility.setMaximumAge(validationService.stringToLong(ageEligibilityUpdateResource.getMaximumAge()));
        ageEligibility.setStatus(CommonStatus.valueOf(ageEligibilityUpdateResource.getStatus()));
        ageEligibility.setModifiedUser(username);
        ageEligibility.setModifiedDate(validationService.getCreateOrModifyDate());
        ageEligibility.setSyncTs(validationService.getCreateOrModifyDate());
        ageEligibility = ageEligibilityRepository.saveAndFlush(ageEligibility);
        return ageEligibility;
    }

    private void validateMinMaxAge(String minAge, String maxAge) {
        if (validationService.stringToLong(minAge) < 0)
            throw new InvalidServiceIdException(environment.getProperty(CAN_NOT_LESS_ZERO), ServiceEntity.MINIMUM_AGE, EntityPoint.AGE_ELIGIBILITY);

        if (validationService.stringToLong(maxAge) < 0)
            throw new InvalidServiceIdException(environment.getProperty(CAN_NOT_LESS_ZERO), ServiceEntity.MAXIMUM_AGE, EntityPoint.AGE_ELIGIBILITY);

        if (validationService.stringToLong(maxAge) < validationService.stringToLong(minAge))
            throw new InvalidServiceIdException(environment.getProperty(INVALID_MAX_AGE), ServiceEntity.MAXIMUM_AGE, EntityPoint.AGE_ELIGIBILITY);
    }

}
