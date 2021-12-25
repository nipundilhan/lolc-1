package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.FinancialStatementType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.StatementTypeRepository;
import com.fusionx.lending.origination.resource.StatementTypeAddResource;
import com.fusionx.lending.origination.resource.StatementTypeUpdateResource;
import com.fusionx.lending.origination.service.FinancialStatementTypeService;

/**
 * Statement Type Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-357	      FXL-427    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
public class FinancialStatementTypeServiceImpl implements FinancialStatementTypeService{
	
	private final StatementTypeRepository statementTypeRepository;

	private final Environment environment;
	 
	@Autowired
    public FinancialStatementTypeServiceImpl(StatementTypeRepository statementTypeRepository, Environment environment) {
        this.statementTypeRepository = statementTypeRepository;
        this.environment = environment;
    }
	
	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find all StatementType
	 * @return list of StatementType
	 */
	@Override
    public List<FinancialStatementType> findAll() {
        return statementTypeRepository.findAll();
    }

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by ID StatementType
	 * @return optional dataset of StatementType
	 */
	@Override
    public Optional<FinancialStatementType> findById(Long id) {
        Optional<FinancialStatementType> statementType = statementTypeRepository.findById(id);
        if (statementType.isPresent()) {
            return statementType;
        } else {
            return Optional.empty();
        }
    }

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by CODE StatementType
	 * @return optional dataset of StatementType
	 */
	@Override
    public Optional<FinancialStatementType> findByCode(String code) {
        Optional<FinancialStatementType> statementType = statementTypeRepository.findByCode(code);
        if (statementType.isPresent()) {
            return statementType;
        } else {
            return Optional.empty();
        }
    }

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by NAME StatementType
	 * @return list of StatementType
	 */
	@Override
    public List<FinancialStatementType> findByName(String name) {
        return statementTypeRepository.findByNameContaining(name);
    }

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by STATUS StatementType
	 * @return list of StatementType
	 */
	@Override
    public List<FinancialStatementType> findByStatus(String status) {
        return statementTypeRepository.findByStatus(status);
    }
    
	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote add StatementType
	 * @return ID of StatementType
	 */
	@Override
    public Long validateAndSaveStatementType(String tenantId, String createdUser, StatementTypeAddResource statementTypeAddResource) {
    	LoggerRequest.getInstance().logInfo("StatementType validateAndSaveStatementType");
        LoggerRequest.getInstance().logInfo("StatementType validate Code and status");
        if (statementTypeRepository.existsByCodeAndStatus(statementTypeAddResource.getCode(), CommonStatus.ACTIVE.toString())) {
            throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
        }

        LoggerRequest.getInstance().logInfo("StatementType initiate saveStatementType ");
        FinancialStatementType statementType = saveStatementType(tenantId, createdUser, statementTypeAddResource);

        return statementType != null ? statementType.getId() : null;
    }
	
	private FinancialStatementType saveStatementType(String tenantId, String createdUser, StatementTypeAddResource statementTypeAddResource) {
    	LoggerRequest.getInstance().logInfo("StatementType inside saveStatementType ");
    	FinancialStatementType statementType = new FinancialStatementType();
    	statementType.setTenantId(tenantId);
            
    	statementType.setCode(statementTypeAddResource.getCode());
    	statementType.setName(statementTypeAddResource.getName());
    	statementType.setDescription(statementTypeAddResource.getDescription());
    	statementType.setStatus(statementTypeAddResource.getStatus());
    	statementType.setCreatedUser(createdUser);
    	statementType.setCreatedDate(getCreateOrModifyDate());
    	statementType.setSyncTs(getCreateOrModifyDate());

        return statementTypeRepository.saveAndFlush(statementType);
        
    }

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote exist by ID StatementType
	 * @return Boolean value
	 */
	@Override
    public Boolean existsById(Long id) {
        return statementTypeRepository.existsById(id);
    }
    
    

    private Timestamp getCreateOrModifyDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }
    
    /**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote update StatementType
	 * @return ID of StatementType
	 */
    @Override
    public Long validateAndUpdateStatementType(String tenantId, String createdUser, Long id, StatementTypeUpdateResource statementTypeUpdateResource) {
    	LoggerRequest.getInstance().logInfo("StatementType validateAndUpdateStatementType");
        LoggerRequest.getInstance().logInfo("StatementType validate by id");
        Optional<FinancialStatementType> optionalStatementType = statementTypeRepository.findById(id);
        if (optionalStatementType.isPresent()) {
            if (!optionalStatementType.get().getVersion().equals(Long.parseLong(statementTypeUpdateResource.getVersion()))) {
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
            }
        } else {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
        }

        LoggerRequest.getInstance().logInfo("StatementType validate Code");
        Optional<FinancialStatementType> optionalStatementTypeByCode = statementTypeRepository.findByCodeAndIdNotIn(statementTypeUpdateResource.getCode(), id);
        if (optionalStatementTypeByCode.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
        }

        LoggerRequest.getInstance().logInfo("StatementType initiate updateStatementType");
        FinancialStatementType statementType = updateStatementType(tenantId, createdUser, statementTypeUpdateResource, id);

        return statementType != null ? statementType.getId() : null;
    }
    
    private FinancialStatementType updateStatementType(String tenantId, String createdUser, StatementTypeUpdateResource statementTypeUpdateResource, Long id) {
    	LoggerRequest.getInstance().logInfo("StatementType inside updateStatementType ");
    	FinancialStatementType statementType = statementTypeRepository.getOne(id);
    	statementType.setTenantId(tenantId);
    	statementType.setCode(statementTypeUpdateResource.getCode());
    	statementType.setName(statementTypeUpdateResource.getName());
    	statementType.setDescription(statementTypeUpdateResource.getDescription());
    	statementType.setStatus(statementTypeUpdateResource.getStatus());
    	statementType.setModifiedUser(createdUser);
    	statementType.setModifiedDate(getCreateOrModifyDate());
    	statementType.setSyncTs(getCreateOrModifyDate());

            return statementTypeRepository.saveAndFlush(statementType);
        
    }

}
