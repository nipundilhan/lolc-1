package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.StatementTemplate;
import com.fusionx.lending.origination.domain.StatementTemplateDetail;
import com.fusionx.lending.origination.domain.FinancialStatementType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.StatementTemplateDetailRepository;
import com.fusionx.lending.origination.repository.StatementTemplateRepository;
import com.fusionx.lending.origination.repository.StatementTypeRepository;
import com.fusionx.lending.origination.resource.StatementTemplateAddResource;
import com.fusionx.lending.origination.resource.StatementTemplateDetailAddResource;
import com.fusionx.lending.origination.service.StatementTemplateService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Statement Template Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-473	      FXL-426    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StatementTemplateServiceImpl implements StatementTemplateService {
	
	private final StatementTypeRepository statementTypeRepository;
	
	private final StatementTemplateRepository statementTemplateRepository;

	private final Environment environment;
	
	private final ValidateService validateService;
	
	private final StatementTemplateDetailRepository statementTemplateDetailRepository;
	
	
	@Autowired
    public StatementTemplateServiceImpl(StatementTypeRepository statementTypeRepository,
    									StatementTemplateRepository statementTemplateRepository, 
    									Environment environment, 
    									ValidateService validateService, 
    									StatementTemplateDetailRepository statementTemplateDetailRepository) {
		this.statementTypeRepository = statementTypeRepository;
        this.statementTemplateRepository = statementTemplateRepository;
        this.environment = environment;
        this.validateService = validateService;
        this.statementTemplateDetailRepository = statementTemplateDetailRepository;
    }
	
	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote add StatementTemplate
	 * @return ID of StatementTemplate
	 */
	@Override
    public Long validateAndSaveStatementTemplate(String tenantId, String createdUser, StatementTemplateAddResource statementTemplateAddResource) {
    	LoggerRequest.getInstance().logInfo("StatementTemplate validateAndSaveStatementTemplate");
        LoggerRequest.getInstance().logInfo("StatementTemplate validate Code and status");
        if (statementTemplateRepository.existsByCodeAndStatus(statementTemplateAddResource.getCode(), CommonStatus.ACTIVE.toString())) {
            throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
        }
        
        Optional<FinancialStatementType> statementType = statementTypeRepository.findById(statementTemplateAddResource.getStatementTypeId());
        if (statementType.isPresent()) {
            if (!statementType.get().getName().equals(statementTemplateAddResource.getStatementTypeName())) {
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "name");
            }
        } else {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
        }

        LoggerRequest.getInstance().logInfo("StatementTemplate initiate saveStatementTemplate ");
        StatementTemplate statementTemplate = saveStatementTemplate(tenantId, createdUser, statementTemplateAddResource, statementType.get());
        
        if(statementTemplateAddResource.getStatementTemplateDetails()!=null) {
        	LoggerRequest.getInstance().logInfo("StatementTemplate initiate saveStatementTemplateDetails ");
        	saveStatementTemplateDetails(tenantId, createdUser, statementTemplateAddResource.getStatementTemplateDetails(), statementTemplate);
        }

        return statementTemplate != null ? statementTemplate.getId() : null;
    }
	
	private StatementTemplate saveStatementTemplate(String tenantId, String createdUser, StatementTemplateAddResource statementTemplateAddResource, FinancialStatementType statementType) {
    	LoggerRequest.getInstance().logInfo("StatementTemplate inside saveStatementTemplate ");
    	
    	StatementTemplate statementTemplate = new StatementTemplate();
    	statementTemplate.setTenantId(tenantId);
    	statementTemplate.setStatementType(statementType); 
    	statementTemplate.setCode(statementTemplateAddResource.getCode());
    	statementTemplate.setName(statementTemplateAddResource.getName());
    	statementTemplate.setDescription(statementTemplateAddResource.getDescription());
    	statementTemplate.setStatus(statementTemplateAddResource.getStatus());
    	statementTemplate.setCreatedUser(createdUser);
    	statementTemplate.setCreatedDate(getCreateOrModifyDate());
    	statementTemplate.setSyncTs(getCreateOrModifyDate());

        return statementTemplateRepository.saveAndFlush(statementTemplate);
        
    }
	
	private void saveStatementTemplateDetails(String tenantId, String user, List<StatementTemplateDetailAddResource> statementTemplateDetailAddResourceList, StatementTemplate statementTemplate) {
		LoggerRequest.getInstance().logInfo("StatementTemplate inside saveStatementTemplateDetails ");
		
		for(StatementTemplateDetailAddResource statementTemplateDetailAddResource : statementTemplateDetailAddResourceList) {
			
			StatementTemplateDetail statementTemplateDetail = new StatementTemplateDetail();
			statementTemplateDetail.setTenantId(tenantId);
			statementTemplateDetail.setStatementTemplate(statementTemplate);
			statementTemplateDetail.setLevelId(validateService.stringToLong(statementTemplateDetailAddResource.getLevelId()));// need to validate in future
			statementTemplateDetail.setParentId(validateService.stringToLong(statementTemplateDetailAddResource.getParentLevelId()));
			statementTemplateDetail.setTotalValueRequired(statementTemplateDetailAddResource.getTotalValRequired());
			statementTemplateDetail.setFormulaEnabled(statementTemplateDetailAddResource.getFormulaEnabled());
			statementTemplateDetail.setFormula(statementTemplateDetailAddResource.getFormula());
			statementTemplateDetail.setAdditionalNoteRequired(statementTemplateDetailAddResource.getAdditionalNoteRequired());
			statementTemplateDetail.setStatus(statementTemplateDetailAddResource.getStatus());
			statementTemplateDetail.setCreatedUser(user);
			statementTemplateDetail.setCreatedDate(getCreateOrModifyDate());
			statementTemplateDetail.setSyncTs(getCreateOrModifyDate());
			
			statementTemplateDetailRepository.saveAndFlush(statementTemplateDetail);
			
		}
		
	}
	
	private Timestamp getCreateOrModifyDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }

}
