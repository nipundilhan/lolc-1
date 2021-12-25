package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.CreditNoteType;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.exception.CodeUniqueException;
import com.fusionx.lending.transaction.exception.NoRecordFoundException;
import com.fusionx.lending.transaction.exception.OptimisticLockException;
import com.fusionx.lending.transaction.repo.CreditNoteTypeRepository;
import com.fusionx.lending.transaction.resource.CreditNoteAddResource;
import com.fusionx.lending.transaction.resource.CreditNoteUpdateResource;
import com.fusionx.lending.transaction.service.CreditNoteTypeService;
import com.fusionx.lending.transaction.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * CreditNoteType Service
 * <p>
 * *******************************************************************************************************
 * ###   	Date         	Story Point   	Task No    		Author       	Description
 * -------------------------------------------------------------------------------------------------------
 * 1   	09-AUG-2021      		     					Sanatha      	Initial Development
 * <p>
 * *******************************************************************************************************
 */
@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class CreditNoteTypeServiceImpl extends MessagePropertyBase implements CreditNoteTypeService {

    @Autowired
    private CreditNoteTypeRepository repo;

    @Autowired
    private Environment environment;

    @Autowired
    private ValidationService validationService;

    @Override
    public List<CreditNoteType> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<CreditNoteType> findById(Long transCodeId) {
        Optional<CreditNoteType> creditNoteType = repo.findById(transCodeId);
        if (creditNoteType.isPresent())
            return Optional.ofNullable(creditNoteType.get());
        else
            return Optional.empty();
    }

    @Override
    public Optional<Collection<CreditNoteType>> findByStatus(String status) {
        Optional<Collection<CreditNoteType>> creditNoteType = repo.findByStatus(status);
        if (creditNoteType.isPresent())
            return Optional.ofNullable(creditNoteType.get());
        else
            return Optional.empty();
    }

    @Override
    public Optional<CreditNoteType> findByCode(String code) {
        Optional<CreditNoteType> creditNoteType = repo.findByCode(code);
        if (creditNoteType.isPresent())
            return Optional.ofNullable(creditNoteType.get());
        else
            return Optional.empty();
    }

    @Override
    public Optional<Collection<CreditNoteType>> findByName(String name) {
        Optional<Collection<CreditNoteType>> creditNoteType = repo.findByNameContaining(name);
        if (creditNoteType.isPresent())
            return Optional.ofNullable(creditNoteType.get());
        else
            return Optional.empty();
    }

    @Override
    public CreditNoteType addCreditNoteType(String tenantId, CreditNoteAddResource addBaseRequest) {


        Optional<CreditNoteType> isExists = repo.findByCode(addBaseRequest.getCode());
        if (isExists.isPresent())
            throw new CodeUniqueException(environment.getProperty("common.code-duplicate"));
        else {
            CreditNoteType creditNoteType = new CreditNoteType();
            creditNoteType.setTenantId(tenantId);
            creditNoteType.setCode(addBaseRequest.getCode());
            if (addBaseRequest.getDescription() != null) {
                creditNoteType.setDescription(addBaseRequest.getDescription());
            }
            creditNoteType.setStatus(addBaseRequest.getStatus());
            creditNoteType.setName(addBaseRequest.getName());
            creditNoteType.setCreatedDate(validationService.getCreateOrModifyDate());
            creditNoteType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
            creditNoteType.setSyncTs(validationService.getCreateOrModifyDate());
            creditNoteType = repo.saveAndFlush(creditNoteType);
            return creditNoteType;
        }

    }

    @Override
    public CreditNoteType updateCreditNoteType(String tenantId, CreditNoteUpdateResource addBaseRequest, Long id) {

        Optional<CreditNoteType> isPresentCreditNoteType = repo.findById(id);
        if (isPresentCreditNoteType.isPresent()) {

            Optional<CreditNoteType> isExists = repo.findByCodeAndIdNotIn(addBaseRequest.getCode(), id);
            if (isExists.isPresent())
                throw new CodeUniqueException(environment.getProperty("common.code-duplicate"));
            else {

                if (!isPresentCreditNoteType.get().getVersion().equals(Long.parseLong(addBaseRequest.getVersion())))
                    throw new OptimisticLockException(environment.getProperty("common-invalid.version"), "version");

                CreditNoteType creditNoteType = isPresentCreditNoteType.get();
                creditNoteType.setTenantId(tenantId);
                //creditNoteType.setCode(addBaseRequest.getCode());
                if (addBaseRequest.getDescription() != null) {
                    creditNoteType.setDescription(addBaseRequest.getDescription());
                }
                creditNoteType.setStatus(addBaseRequest.getStatus());
                creditNoteType.setName(addBaseRequest.getName());
                creditNoteType.setModifiedDate(validationService.getCreateOrModifyDate());
                creditNoteType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
                creditNoteType.setSyncTs(validationService.getCreateOrModifyDate());
                creditNoteType = repo.saveAndFlush(creditNoteType);
                return creditNoteType;
            }

        } else
            throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));

    }

}
