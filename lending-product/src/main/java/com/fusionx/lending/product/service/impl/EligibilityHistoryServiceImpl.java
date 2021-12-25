package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityHistory;
import com.fusionx.lending.product.repository.EligibilityHistoryRepository;
import com.fusionx.lending.product.service.EligibilityHistoryService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * API Service related to Eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2021    	-               		             Menuka Jayasinghe		Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityHistoryServiceImpl implements EligibilityHistoryService {

    private EligibilityHistoryRepository eligibilityHistoryRepository;
    private ValidationService validationService;

    @Autowired
    public void setEligibilityHistoryRepository(EligibilityHistoryRepository eligibilityHistoryRepository) {
        this.eligibilityHistoryRepository = eligibilityHistoryRepository;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public void saveHistory(String tenantId, Eligibility eligibility, String historyCreatedUser) {

        EligibilityHistory eligibilityHistory = new EligibilityHistory();
        
       
       

        eligibilityHistory.setTenantId(tenantId);
        eligibilityHistory.setCode(eligibility.getCode());
        eligibilityHistory.setName(eligibility.getName());
        eligibilityHistory.setAgeEligiId(eligibility.getAgeEligibility().getId());
        eligibilityHistory.setEligibilityId(eligibility.getId());
        eligibilityHistory.setGuarantorEligibility(eligibility.getGuarantorEligibility());
        eligibilityHistory.setStatus(eligibility.getStatus());
        eligibilityHistory.setApproveStatus(eligibility.getApproveStatus());
        eligibilityHistory.setCreatedDate(eligibility.getCreatedDate());
        eligibilityHistory.setCreatedUser(eligibility.getCreatedUser());        
        eligibilityHistory.setModifiedDate(eligibility.getModifiedDate());
        eligibilityHistory.setModifiedUser(eligibility.getModifiedUser());
        eligibilityHistory.setPenApprovedDate(eligibility.getPenApprovedDate());
        eligibilityHistory.setPenApprovedUser(eligibility.getPenApprovedUser());
        eligibilityHistory.setPenRejectedDate(eligibility.getPenRejectedDate());
        eligibilityHistory.setPenRejectedUser(eligibility.getPenRejectedUser());
        eligibilityHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityHistory.setHistoryCreatedUser(historyCreatedUser!= null?historyCreatedUser:LogginAuthentcation.getInstance().getUserName());
        eligibilityHistory.setVersion(eligibility.getVersion());
        eligibilityHistory.setSyncTs(validationService.getCreateOrModifyDate());

        eligibilityHistoryRepository.save(eligibilityHistory);

    }

}
