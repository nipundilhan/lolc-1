package com.fusionx.lending.transaction.service.impl;

/**
 * Bank Transaction Code service
 *
 * @author Nisalak
 * @Dat 28-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   28-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.core.LoggerRequest;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.exception.CodeUniqueException;
import com.fusionx.lending.transaction.exception.NoRecordFoundException;
import com.fusionx.lending.transaction.exception.OptimisticLockException;
import com.fusionx.lending.transaction.repo.BankTransactionCodeRepository;
import com.fusionx.lending.transaction.resource.BankTransactionCodeResource;
import com.fusionx.lending.transaction.resource.BankTransactionCodeUpdateResource;
import com.fusionx.lending.transaction.service.BankTransactionCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Component
@Transactional(rollbackFor = Exception.class)
public class BankTransactionCodeServiceImpl implements BankTransactionCodeService {

    @Autowired
    private BankTransactionCodeRepository bankTransactionCodeRepository;

    @Autowired
    private Environment environment;

    @Override
    public List<BankTransactionCode> findAll() {
        return bankTransactionCodeRepository.findAll();
    }

    @Override
    public Optional<BankTransactionCode> findById(Long transCodeId) {
        Optional<BankTransactionCode> bankTransactionCode = bankTransactionCodeRepository.findById(transCodeId);
        if (bankTransactionCode.isPresent())
            return Optional.ofNullable(bankTransactionCode.get());
        else
            return Optional.empty();
    }

    @Override
    public Optional<Collection<BankTransactionCode>> findByStatus(String status) {
        Optional<Collection<BankTransactionCode>> bankTransactionCode = bankTransactionCodeRepository.findByStatus(BankTransactionCodeStatus.valueOf(status));
        if (bankTransactionCode.isPresent())
            return Optional.ofNullable(bankTransactionCode.get());
        else
            return Optional.empty();
    }
    
    @Override
    public Optional<Collection<BankTransactionCode>> findByDescription(String description) {
        Optional<Collection<BankTransactionCode>> bankTransactionCode = bankTransactionCodeRepository.findByDescriptionContains(description);
        if (bankTransactionCode.isPresent())
            return Optional.ofNullable(bankTransactionCode.get());
        else
            return Optional.empty();
    }

    @Override
    public Optional<BankTransactionCode> findByCode(String code) {
        Optional<BankTransactionCode> bankTransactionCode = bankTransactionCodeRepository.findByCode(code);
        if (bankTransactionCode.isPresent())
            return Optional.ofNullable(bankTransactionCode.get());
        else
            return Optional.empty();
    }

    @Override
    public BankTransactionCode saveBankTransactionCode(String tenantId, BankTransactionCodeResource bankTransactionCodeResource) {
        LoggerRequest.getInstance().logInfo("BankTransactionCode********************************Enter*********************************************");
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeRepository.findByCode(bankTransactionCodeResource.getCode());
        LoggerRequest.getInstance().logInfo("BankTransactionCode********************************findByCode*********************************************");
        if (isPresentBankTransactionCode.isPresent()) {
            LoggerRequest.getInstance().logInfo("BankTransactionCode********************************code-duplicate*********************************************");
            throw new CodeUniqueException(environment.getProperty("common.code-duplicate"));
        } else {
            BankTransactionCode bankTransactionCode = new BankTransactionCode();
            LoggerRequest.getInstance().logInfo("BankTransactionCode********************************Set Property*********************************************");
            bankTransactionCode.setTenantId(tenantId);
            bankTransactionCode.setCode(bankTransactionCodeResource.getCode());
            bankTransactionCode.setDescription(bankTransactionCodeResource.getDescription());
            bankTransactionCode.setStatus(bankTransactionCodeResource.getStatus());
            bankTransactionCode.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
            bankTransactionCode.setCreatedDate(currentTimestamp);
            bankTransactionCode.setSyncTs(currentTimestamp);
            bankTransactionCode = bankTransactionCodeRepository.saveAndFlush(bankTransactionCode);
            LoggerRequest.getInstance().logInfo("BankTransactionCode********************************End*********************************************");
            return bankTransactionCode;
        }
    }

    @Override
    public BankTransactionCode updateBankTransactionCode(String tenantId, BankTransactionCodeUpdateResource bankTransactionCodeUpdateResource) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeRepository.findById(stringToLong(bankTransactionCodeUpdateResource.getId()));
        if (isPresentBankTransactionCode.isPresent()) {
            if (bankTransactionCodeUpdateResource.getCode().equals(isPresentBankTransactionCode.get().getCode())) {
                if (!isPresentBankTransactionCode.get().getVersion().equals(Long.parseLong(bankTransactionCodeUpdateResource.getVersion())))
                    throw new OptimisticLockException(environment.getProperty("common-invalid.version"), "version");

                BankTransactionCode bankTransactionCode = getBankTransactionCodeById(stringToLong(bankTransactionCodeUpdateResource.getId()));
                bankTransactionCode.setDescription(bankTransactionCodeUpdateResource.getDescription());
                bankTransactionCode.setStatus(bankTransactionCodeUpdateResource.getStatus());
                bankTransactionCode.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
                bankTransactionCode.setModifiedDate(currentTimestamp);
                bankTransactionCode.setSyncTs(currentTimestamp);
                bankTransactionCode.setVersion(stringToLong(bankTransactionCodeUpdateResource.getVersion()));
                bankTransactionCode = bankTransactionCodeRepository.saveAndFlush(bankTransactionCode);
                return bankTransactionCode;
            } else
                throw new NoRecordFoundException(environment.getProperty("common.invalid-code"));
        } else
            throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
    }

    /**
     * String value convert to Long
     * @param String value
     * @return Long value
     */
    private Long stringToLong(String str) {
        return Long.parseLong(str);
    }

    /**
     * get branch Detail
     * @param branch Id
     * @return branch Object
     */
    private BankTransactionCode getBankTransactionCodeById(Long transCodeId) {
        Optional<BankTransactionCode> bankTransactionCode = bankTransactionCodeRepository.findById(transCodeId);
        if (bankTransactionCode.isPresent())
            return bankTransactionCode.get();
        else
            return null;
    }

}
