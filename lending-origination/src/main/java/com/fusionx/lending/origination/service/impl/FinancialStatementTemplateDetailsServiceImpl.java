package com.fusionx.lending.origination.service.impl;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.FinancialStatementLevel;
import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.domain.FinancialStatementTemplateDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.CommonYesNoStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.repository.FinancialStatementLevelRepository;
import com.fusionx.lending.origination.repository.FinancialStatementTemplateDetailsRepository;
import com.fusionx.lending.origination.repository.FinancialStatementTemplateRepository;
import com.fusionx.lending.origination.resource.FinancialStatementDetailsAddResource;
import com.fusionx.lending.origination.resource.FinancialStatementDetailsUpdateResource;
import com.fusionx.lending.origination.service.FinancialStatementTemplateDetailsHistoryService;
import com.fusionx.lending.origination.service.FinancialStatementTemplateDetailsService;
import com.fusionx.lending.origination.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional(rollbackFor = Exception.class)
public class FinancialStatementTemplateDetailsServiceImpl extends MessagePropertyBase
        implements FinancialStatementTemplateDetailsService {

    @Autowired
    private FinancialStatementTemplateRepository financialStatementRepository;

    @Autowired
    private FinancialStatementLevelRepository financialStatementLevelRepository;

    @Autowired
    private FinancialStatementTemplateDetailsRepository financialStatementDetailsRepository;

    @Autowired
    private ValidateService validationService;

    @Autowired
    private FinancialStatementTemplateDetailsHistoryService financialStatementDetailsHistoryService;

    @Override
    public FinancialStatementTemplateDetails addFinancialStatementDetails(String tenantId,
                                                                          FinancialStatementTemplate financialStatement,
                                                                          FinancialStatementDetailsAddResource financialStatementDetailsAddResource, int index) {


        LoggerRequest.getInstance().logInfo("FinancialStatementDetails addFinancialStatementDetails");

        Optional<FinancialStatementTemplate> financialStatementIsPresent = financialStatementRepository
                .findById(financialStatement.getId());

        Optional<FinancialStatementLevel> financialStatementLevel = financialStatementLevelRepository
                .findByIdAndNameContaining(Long.parseLong(financialStatementDetailsAddResource.getLevelId()),
                        financialStatementDetailsAddResource.getLevelName());
        if (!financialStatementLevel.isPresent()) {
            throw new DetailListValidateException(environment.getProperty("common.record-not-found"),
                    ServiceEntity.FINANCIAL_STATEMENT_LEVEL, ServicePoint.FINANCIAL_STATEMENT_TEMPLATE, index);
        }

        if (financialStatementDetailsAddResource.getFormulaEnabled().equals("YES")) {
            if (financialStatementDetailsAddResource.getFormula().isEmpty()) {
                throw new InvalidServiceIdException(environment.getProperty("common.not-null"),
                        ServiceEntity.FINANCIAL_STATEMENT_TEMPLATE_FORMULA);
            }
        }

        LoggerRequest.getInstance().logInfo("FinancialStatementDetails addFinancialStatementDetails 01");
        FinancialStatementTemplateDetails financialStatementDetails = new FinancialStatementTemplateDetails();
        financialStatementDetails.setTenantId(tenantId);
        financialStatementDetails.setFinancialStatement(financialStatementIsPresent.get());
        financialStatementDetails.setFinancialStatementLevels(financialStatementLevel.get());
        financialStatementDetails.setFieldName(financialStatementLevel.get().getName());
        if (financialStatementDetailsAddResource.getParentLevelId() != null
                || financialStatementDetailsAddResource.getParentLevelId().isEmpty()) {
            financialStatementDetails.setParentId(financialStatementDetailsAddResource.getParentLevelId());
        }
        financialStatementDetails.setTotalValueRequired(
                CommonYesNoStatus.valueOf(financialStatementDetailsAddResource.getTotalValRequired()));
        financialStatementDetails
                .setFormulaEnabled(CommonYesNoStatus.valueOf(financialStatementDetailsAddResource.getFormulaEnabled()));
        financialStatementDetails.setFormula(financialStatementDetailsAddResource.getFormula());
        financialStatementDetails.setAdditionalNoteRequired(
                CommonYesNoStatus.valueOf(financialStatementDetailsAddResource.getAdditionalNoteRequired()));
        if (financialStatementDetailsAddResource.getSequence() != null
                || financialStatementDetailsAddResource.getSequence() != "") {
            financialStatementDetails.setSequence(Long.parseLong(financialStatementDetailsAddResource.getSequence()));
        }
        financialStatementDetails.setStatus(CommonStatus.valueOf(financialStatementDetailsAddResource.getStatus()));
        financialStatementDetails.setCreatedDate(validationService.getCreateOrModifyDate());
        financialStatementDetails.setSyncTs(validationService.getCreateOrModifyDate());
        financialStatementDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        LoggerRequest.getInstance().logInfo("FinancialStatementDetails addFinancialStatementDetails 02");
        financialStatementDetailsRepository.save(financialStatementDetails);
        index++;
        return financialStatementDetails;

    }

    @Override
    public FinancialStatementTemplateDetails updateFinancialStatementDetails(String tenantId, Long id,
                                                                             FinancialStatementTemplate financialStatement,
                                                                             FinancialStatementDetailsUpdateResource financialStatementDetailsUpdateResource, int index) {

        LoggerRequest.getInstance().logInfo("FinancialStatementDetails updateFinancialStatementDetails");

        Optional<FinancialStatementTemplateDetails> isPresentFinancialStatementDetails = financialStatementDetailsRepository.findById(Long.parseLong(financialStatementDetailsUpdateResource.getId()));
        LoggerRequest.getInstance().logInfo("FinancialStatementDetails updateFinancialStatementDetails 01");

        financialStatementDetailsHistoryService.saveHistory(tenantId, isPresentFinancialStatementDetails.get(), LogginAuthentcation.getInstance().getUserName());
        LoggerRequest.getInstance().logInfo("FinancialStatementDetails updateFinancialStatementDetails 02");

        if (!isPresentFinancialStatementDetails.get().getVersion().equals(Long.parseLong(financialStatementDetailsUpdateResource.getVersion())))
            throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.FINANCIAL_STATEMENT_TEMPLATE, index);

        LoggerRequest.getInstance().logInfo("FinancialStatementDetails updateFinancialStatementDetails 03");

        String levelId = financialStatementDetailsUpdateResource.getLevelId();
        String levelName = financialStatementDetailsUpdateResource.getLevelName();

        Optional<FinancialStatementLevel> financialStatementLevel = financialStatementLevelRepository.findByIdAndNameContaining(Long.parseLong(levelId), levelName);

        if (!financialStatementLevel.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty("common.record-not-found"), ServiceEntity.FINANCIAL_STATEMENT_LEVEL);
        }

        if (financialStatementDetailsUpdateResource.getFormulaEnabled().equals("YES")) {
            if (financialStatementDetailsUpdateResource.getFormula().isEmpty())
                throw new InvalidServiceIdException(environment.getProperty("common.not-null"), ServiceEntity.FINANCIAL_STATEMENT_TEMPLATE_FORMULA);
        }

        LoggerRequest.getInstance().logInfo("FinancialStatementDetails updateFinancialStatementDetails 04");
        FinancialStatementTemplateDetails financialStatementDetails = isPresentFinancialStatementDetails.get();
        financialStatementDetails.setTenantId(tenantId);
        financialStatementDetails.setFinancialStatementLevels(financialStatementLevel.get());
        financialStatementDetails.setFieldName(financialStatementDetailsUpdateResource.getLevelName());

        if (financialStatementDetailsUpdateResource.getParentLevelId() != null || financialStatementDetailsUpdateResource.getParentLevelId().isEmpty()) {
            financialStatementDetails.setParentId(financialStatementDetailsUpdateResource.getParentLevelId());
        }
        financialStatementDetails.setTotalValueRequired(CommonYesNoStatus.valueOf(financialStatementDetailsUpdateResource.getTotalValRequired()));
        financialStatementDetails.setFormulaEnabled(CommonYesNoStatus.valueOf(financialStatementDetailsUpdateResource.getFormulaEnabled()));
        financialStatementDetails.setFormula(financialStatementDetailsUpdateResource.getFormula());
        financialStatementDetails.setAdditionalNoteRequired(CommonYesNoStatus.valueOf(financialStatementDetailsUpdateResource.getAdditionalNoteRequired()));
        financialStatementDetails.setSequence(Long.parseLong(financialStatementDetailsUpdateResource.getSequence()));
        financialStatementDetails.setStatus(CommonStatus.valueOf(financialStatementDetailsUpdateResource.getStatus()));
        financialStatementDetails.setModifiedDate(validationService.getCreateOrModifyDate());
        financialStatementDetails.setSyncTs(validationService.getCreateOrModifyDate());
        financialStatementDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        LoggerRequest.getInstance().logInfo("FinancialStatementDetails updateFinancialStatementDetails 05");
        return financialStatementDetailsRepository.save(financialStatementDetails);

    }

}
