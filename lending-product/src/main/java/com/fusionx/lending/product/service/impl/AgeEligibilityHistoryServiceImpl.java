package com.fusionx.lending.product.service.impl;


import com.fusionx.lending.product.domain.AgeEligibility;
import com.fusionx.lending.product.domain.AgeEligibilityHistory;
import com.fusionx.lending.product.repository.AgeEligibilityHistoryRepository;
import com.fusionx.lending.product.service.AgeEligibilityHistoryService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


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
public class AgeEligibilityHistoryServiceImpl implements AgeEligibilityHistoryService {

    @Autowired
    private AgeEligibilityHistoryRepository ageEligibilityHistoryRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public void insertAgeEligibilityHistory(String tenantId, AgeEligibility ageEligibility, String historyCreatedUser) {

        AgeEligibilityHistory ageEligibilityHistory = new AgeEligibilityHistory();

        ageEligibilityHistory.setAgeEligibilityId(ageEligibility.getId());
        ageEligibilityHistory.setTenantId(ageEligibility.getTenantId());
        ageEligibilityHistory.setMinimumAge(ageEligibility.getMinimumAge());
        ageEligibilityHistory.setMaximumAge(ageEligibility.getMaximumAge());
        ageEligibilityHistory.setStatus(ageEligibility.getStatus());
        ageEligibilityHistory.setCreatedDate(ageEligibility.getCreatedDate());
        ageEligibilityHistory.setCreatedUser(ageEligibility.getCreatedUser());
        ageEligibilityHistory.setModifiedDate(ageEligibility.getModifiedDate());
        ageEligibilityHistory.setModifiedUser(ageEligibility.getModifiedUser());
        ageEligibilityHistory.setVersion(ageEligibility.getVersion());
        ageEligibilityHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        ageEligibilityHistory.setSyncTs(validationService.getCreateOrModifyDate());
        ageEligibilityHistory.setHistoryCreatedUser(historyCreatedUser);
        ageEligibilityHistoryRepository.saveAndFlush(ageEligibilityHistory);
    }

}
