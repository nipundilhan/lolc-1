package com.fusionx.lending.origination.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Risk RatingLevelDetails
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-338 		 FXL-682 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.RiskRatingLevels;
import com.fusionx.lending.origination.domain.RiskRatingLevelDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.repository.RiskRatingLevelDetailsRepository;
import com.fusionx.lending.origination.resource.RiskRatingLevelDetailsAddResource;
import com.fusionx.lending.origination.service.RiskRatingLevelDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class RiskRatingLevelDetailsServiceImpl extends MessagePropertyBase implements RiskRatingLevelDetailsService {

	@Autowired
	private RiskRatingLevelDetailsRepository riskRatingLevelDetailsRepository;

	@Autowired
	private ValidateService validationService;

	@Override
	public RiskRatingLevelDetails addRiskRatingLevelDetails(String tenantId, RiskRatingLevels riskRatingLevels,
			RiskRatingLevelDetailsAddResource riskRatingLevelAddResource) {

		RiskRatingLevelDetails riskRatingLevelDetails = new RiskRatingLevelDetails();
		riskRatingLevelDetails.setTenantId(tenantId);
		riskRatingLevelDetails.setRiskRatingLevels(riskRatingLevels);
		riskRatingLevelDetails.setSequence(riskRatingLevelAddResource.getSequence());
		riskRatingLevelDetails.setLevel(riskRatingLevelAddResource.getLevel());
		riskRatingLevelDetails.setGrade(riskRatingLevelAddResource.getGrade());
		riskRatingLevelDetails.setStatus(CommonStatus.valueOf(riskRatingLevelAddResource.getStatus()));
		riskRatingLevelDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		riskRatingLevelDetails.setSyncTs(validationService.getCreateOrModifyDate());
		riskRatingLevelDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return riskRatingLevelDetailsRepository.save(riskRatingLevelDetails);

	}

}
