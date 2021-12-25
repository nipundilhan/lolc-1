package com.fusionx.lending.origination.service.impl;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.domain.FinancialStatementTemplateDetails;
import com.fusionx.lending.origination.domain.FinancialStatementType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.FinancialStatementTemplateDetailsRepository;
import com.fusionx.lending.origination.repository.FinancialStatementTemplateRepository;
import com.fusionx.lending.origination.repository.StatementTypeRepository;
import com.fusionx.lending.origination.resource.FinancialStatementAddResource;
import com.fusionx.lending.origination.resource.FinancialStatementDetailsAddResource;
import com.fusionx.lending.origination.resource.FinancialStatementDetailsUpdateResource;
import com.fusionx.lending.origination.resource.FinancialStatementUpdateResource;
import com.fusionx.lending.origination.service.FinancialStatementTemplateDetailsService;
import com.fusionx.lending.origination.service.FinancialStatementTemplateHistoryService;
import com.fusionx.lending.origination.service.FinancialStatementTemplateService;
import com.fusionx.lending.origination.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Financial Statement Level Domain
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   27-08-2021      	FXL-338	  FXL-655	Dilki        Created
 * <p>
 * *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FinancialStatemntTemplateServiceImpl extends MessagePropertyBase implements FinancialStatementTemplateService {

    @Autowired
    private FinancialStatementTemplateRepository financialStatementRepository;

    @Autowired
    private FinancialStatementTemplateDetailsRepository financialStatementTemplateDetailsRepository;

    @Autowired
    private StatementTypeRepository statementTypeRepository;

    @Autowired
    private ValidateService validationService;

    @Autowired
    private FinancialStatementTemplateHistoryService financialStatementHistoryService;

    @Autowired
    private FinancialStatementTemplateDetailsService financialStatementDetailsService;

    @Override
    public List<FinancialStatementTemplate> getAll() {
        List<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementRepository.findAll();
        for (FinancialStatementTemplate financialStatement : isPresentFinancialStatement) {
            List<FinancialStatementTemplateDetails> financialStatementTemplateDetail = financialStatementTemplateDetailsRepository
                    .findByFinancialStatementId(financialStatement.getId());
            financialStatement.setFinancialStatementTemplateDetails(financialStatementTemplateDetail);
        }
        return isPresentFinancialStatement;
    }

    @Override
    public Optional<FinancialStatementTemplate> getById(Long id) {
        Optional<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementRepository.findById(id);
        if (isPresentFinancialStatement.isPresent()) {
            FinancialStatementTemplate financialStatementTemplate = isPresentFinancialStatement.get();
            List<FinancialStatementTemplateDetails> financialStatementTemplateDetail = financialStatementTemplateDetailsRepository
                    .findByFinancialStatementId(financialStatementTemplate.getId());
            financialStatementTemplate.setFinancialStatementTemplateDetails(financialStatementTemplateDetail);
            return isPresentFinancialStatement;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<FinancialStatementTemplate> getByCode(String code) {
        Optional<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementRepository
                .findByCode(code);
        if (isPresentFinancialStatement.isPresent()) {
            FinancialStatementTemplate financialStatementTemplate = isPresentFinancialStatement.get();
            List<FinancialStatementTemplateDetails> financialStatementTemplateDetail = financialStatementTemplateDetailsRepository
                    .findByFinancialStatementId(financialStatementTemplate.getId());
            financialStatementTemplate.setFinancialStatementTemplateDetails(financialStatementTemplateDetail);
            return isPresentFinancialStatement;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<FinancialStatementTemplate> getByName(String name) {
        Optional<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementRepository
                .findByNameContaining(name);
        if (isPresentFinancialStatement.isPresent()) {
            FinancialStatementTemplate financialStatementTemplate = isPresentFinancialStatement.get();
            List<FinancialStatementTemplateDetails> financialStatementTemplateDetail = financialStatementTemplateDetailsRepository
                    .findByFinancialStatementId(financialStatementTemplate.getId());
            financialStatementTemplate.setFinancialStatementTemplateDetails(financialStatementTemplateDetail);
            return isPresentFinancialStatement;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<FinancialStatementTemplate> getByStatus(String status) {
        List<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementRepository
                .findByStatus(CommonStatus.valueOf(status));
        List<FinancialStatementTemplate> financialStatementTemplateList = new ArrayList<>();
        for (FinancialStatementTemplate financialStatementTemplate : isPresentFinancialStatement) {
            List<FinancialStatementTemplateDetails> financialStatementTemplateDetail = financialStatementTemplateDetailsRepository
                    .findByFinancialStatementId(financialStatementTemplate.getId());
            financialStatementTemplate.setFinancialStatementTemplateDetails(financialStatementTemplateDetail);
            financialStatementTemplateList.add(financialStatementTemplate);
        }
        return financialStatementTemplateList;
    }

    @Override
    public FinancialStatementTemplate addFinancialStatement(String tenantId,
                                                            FinancialStatementAddResource financialStatementAddResource) {
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");

        LoggerRequest.getInstance().logInfo("FinancialStatement addFinancialStatement");

        Optional<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementRepository
                .findByCode(financialStatementAddResource.getCode());

        Optional<FinancialStatementType> isPresentFinancialStatementType = statementTypeRepository
                .findByIdAndNameAndStatus(Long.parseLong(financialStatementAddResource.getStatementTypeId()),
                        financialStatementAddResource.getStatementType(), CommonStatus.ACTIVE.toString());
        if (!isPresentFinancialStatementType.isPresent())
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "statementTypeId");

        if (isPresentFinancialStatement.isPresent())
            throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

        FinancialStatementTemplate financialStatement = new FinancialStatementTemplate();
        financialStatement.setTenantId(tenantId);
        financialStatement.setCode(financialStatementAddResource.getCode());
        financialStatement.setName(financialStatementAddResource.getName());
        financialStatement.setFinancialStatementType(isPresentFinancialStatementType.get());
        financialStatement.setStatementType(financialStatementAddResource.getStatementType());
        financialStatement.setDescription(financialStatementAddResource.getDescription());
        financialStatement.setStatus(CommonStatus.valueOf(financialStatementAddResource.getStatus()));
        financialStatement.setCreatedDate(validationService.getCreateOrModifyDate());
        financialStatement.setSyncTs(validationService.getCreateOrModifyDate());
        financialStatement.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        LoggerRequest.getInstance().logInfo("FinancialStatement addFinancialStatement 01");
        financialStatementRepository.save(financialStatement);
        LoggerRequest.getInstance().logInfo("FinancialStatement addFinancialStatement 02");

        int index = 0;
        for (FinancialStatementDetailsAddResource financialStatementDetailsAddResource : financialStatementAddResource
                .getLevels()) {
            LoggerRequest.getInstance().logInfo("FinancialStatement addFinancialStatement 03");
            financialStatementDetailsService.addFinancialStatementDetails(tenantId, financialStatement,
                    financialStatementDetailsAddResource, index);

            index++;
            LoggerRequest.getInstance().logInfo("FinancialStatement addFinancialStatement 04");
        }
        LoggerRequest.getInstance().logInfo("FinancialStatement addFinancialStatement 05");
        return financialStatement;

    }

    @Override
    public FinancialStatementTemplate updateFinancialStatement(String tenantId, Long id,
                                                               FinancialStatementUpdateResource financialStatementUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");

        LoggerRequest.getInstance().logInfo("FinancialStatement updateFinancialStatement");

//		Optional<FinancialStatementType> isPresentFinancialStatementType = statementTypeRepository
//				.findById(Long.parseLong(financialStatementUpdateResource.getStatementTypeId()));

        Optional<FinancialStatementType> isPresentFinancialStatementType = statementTypeRepository.findByIdAndNameAndStatus(Long.parseLong(financialStatementUpdateResource.getStatementTypeId()),
                financialStatementUpdateResource.getStatementType(), CommonStatus.ACTIVE.toString());

        if (!isPresentFinancialStatementType.isPresent())
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "statementTypeId");

        Optional<FinancialStatementTemplate> isPresentFinancialStatement = financialStatementRepository.findById(id);
        LoggerRequest.getInstance().logInfo("FinancialStatement updateFinancialStatement 01");
        financialStatementHistoryService.saveHistory(tenantId, isPresentFinancialStatement.get(), LogginAuthentcation.getInstance().getUserName());
        LoggerRequest.getInstance().logInfo("FinancialStatement updateFinancialStatement 02");

        if (!isPresentFinancialStatement.get().getVersion().equals(Long.parseLong(financialStatementUpdateResource.getVersion())))
            throw new ValidateRecordException(environment.getProperty("common-invalid.version"), "version");

        if (!isPresentFinancialStatement.get().getCode().equalsIgnoreCase(financialStatementUpdateResource.getCode())) {
            Optional<FinancialStatementTemplate> findByCodeOptional = financialStatementRepository.findByCode(financialStatementUpdateResource.getCode());
            if (findByCodeOptional.isPresent()) {
                throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
            }
        }

        LoggerRequest.getInstance().logInfo("FinancialStatement updateFinancialStatement 03");
        FinancialStatementTemplate financialStatement = isPresentFinancialStatement.get();
        financialStatement.setTenantId(tenantId);
        financialStatement.setCode(financialStatementUpdateResource.getCode());
        financialStatement.setName(financialStatementUpdateResource.getName());
        financialStatement.setFinancialStatementType(isPresentFinancialStatementType.get());
        financialStatement.setStatementType(financialStatementUpdateResource.getStatementType());
        financialStatement.setDescription(financialStatementUpdateResource.getDescription());
        financialStatement.setStatus(CommonStatus.valueOf(financialStatementUpdateResource.getStatus()));
        financialStatement.setModifiedDate(validationService.getCreateOrModifyDate());
        financialStatement.setSyncTs(validationService.getCreateOrModifyDate());
        financialStatement.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        LoggerRequest.getInstance().logInfo("FinancialStatement updateFinancialStatement 04");
        financialStatementRepository.saveAndFlush(financialStatement);
        LoggerRequest.getInstance().logInfo("FinancialStatement updateFinancialStatement 05");

        int index = 0;
        for (FinancialStatementDetailsUpdateResource financialStatementDetailsUpdateResource : financialStatementUpdateResource.getLevels()) {
            LoggerRequest.getInstance().logInfo("FinancialStatement updateFinancialStatement 06");
            financialStatementDetailsService.updateFinancialStatementDetails(tenantId, Long.parseLong(financialStatementDetailsUpdateResource.getId()), financialStatement,
                    financialStatementDetailsUpdateResource, index);
            index++;
            LoggerRequest.getInstance().logInfo("FinancialStatement updateFinancialStatement 07");
        }

        LoggerRequest.getInstance().logInfo("FinancialStatement updateFinancialStatement 08");
        return financialStatement;

    }

}
