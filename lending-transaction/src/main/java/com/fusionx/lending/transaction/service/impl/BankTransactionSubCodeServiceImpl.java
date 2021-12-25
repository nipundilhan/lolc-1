package com.fusionx.lending.transaction.service.impl;

/**
 * Bank Transaction Sub Code service
 *
 * @author Nisalak
 * @Dat 29-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   29-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.enums.BankTransactionSubCodeStatus;
import com.fusionx.lending.transaction.exception.CodeUniqueException;
import com.fusionx.lending.transaction.exception.NoRecordFoundException;
import com.fusionx.lending.transaction.exception.OptimisticLockException;
import com.fusionx.lending.transaction.repo.BankTransactionCodeRepository;
import com.fusionx.lending.transaction.repo.BankTransactionSubCodeRepository;
import com.fusionx.lending.transaction.resource.BankTransactionSubCodeResource;
import com.fusionx.lending.transaction.resource.BankTransactionSubCodeUpdateResource;
import com.fusionx.lending.transaction.service.BankTransactionSubCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;


@Component
@Transactional(rollbackFor = Exception.class)
public class BankTransactionSubCodeServiceImpl implements BankTransactionSubCodeService {

    @Autowired
    private BankTransactionCodeRepository bankTransactionCodeRepository;

    @Autowired
    private BankTransactionSubCodeRepository bankTransactionSubCodeRepository;

    @Autowired
    private Environment environment;

    @Override
    public Optional<BankTransactionSubCode> findById(Long transSubCodeId) {
        Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository.findById(transSubCodeId);
        if (bankTransactionSubCode.isPresent())
            return Optional.ofNullable(bankTransactionSubCode.get());
        else
            return Optional.empty();
    }

    @Override
    public List<BankTransactionSubCode> findByBankTransactionCodeId(Long transCodeId) {
        List<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository.findByBankTransactionCodeId(transCodeId);
        return bankTransactionSubCode;
    }

    @Override
    public List<BankTransactionSubCode> findByBankTransactionCodeIdAndStatus(Long transCodeId, String status) {
        List<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository.findByBankTransactionCodeIdAndStatus(transCodeId, BankTransactionSubCodeStatus.valueOf(status));
        return bankTransactionSubCode;
    }
    
    @Override
    public List<BankTransactionSubCode> findByBankTransactionCodeIdAndDescription(Long transCodeId, String description) {
        List<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository.findByBankTransactionCodeIdAndDescriptionContains(transCodeId, description);
        return bankTransactionSubCode;
    }

    @Override
    public Optional<BankTransactionSubCode> findByBankTransactionCodeIdAndSubCode(Long transCodeId, String subCode) {
        Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository.findByBankTransactionCodeIdAndSubCode(transCodeId, subCode);
        return bankTransactionSubCode;
    }

    @Override
    public BankTransactionSubCode saveBankTransactionSubCode(String tenantId, BankTransactionSubCodeResource bankTransactionSubCodeResource) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeRepository.findById(stringToLong(bankTransactionSubCodeResource.getCodeId()));
        if (isPresentBankTransactionCode.isPresent()) {
            Optional<BankTransactionSubCode> isPresentBankTransactionSubCode = bankTransactionSubCodeRepository.findByBankTransactionCodeIdAndSubCode(stringToLong(bankTransactionSubCodeResource.getCodeId()),
                    bankTransactionSubCodeResource.getSubCode());
            if (isPresentBankTransactionSubCode.isPresent())
                throw new CodeUniqueException(environment.getProperty("common.sub-code-duplicate"));
            else {
                BankTransactionSubCode bankTransactionSubCode = new BankTransactionSubCode();
                bankTransactionSubCode.setTenantId(tenantId);
                bankTransactionSubCode.setBankTransactionCode(isPresentBankTransactionCode.get());
                bankTransactionSubCode.setSubCode(bankTransactionSubCodeResource.getSubCode());
                bankTransactionSubCode.setDescription(bankTransactionSubCodeResource.getDescription());
                bankTransactionSubCode.setPostingType(bankTransactionSubCodeResource.getPostingType());
                bankTransactionSubCode.setAllowDormant(bankTransactionSubCodeResource.getAllowDormant());
                bankTransactionSubCode.setTransactionCategory(bankTransactionSubCodeResource.getTransactionCategory());
                bankTransactionSubCode.setStatus(bankTransactionSubCodeResource.getStatus());
                bankTransactionSubCode.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
                bankTransactionSubCode.setCreatedDate(currentTimestamp);
                bankTransactionSubCode.setSyncTs(currentTimestamp);
                bankTransactionSubCode.setVersion(stringToLong("0"));

                bankTransactionSubCode = bankTransactionSubCodeRepository.saveAndFlush(bankTransactionSubCode);
                return bankTransactionSubCode;
            }
        } else
            throw new NoRecordFoundException(environment.getProperty("bankTransCode.invalid-code"));
    }

    @Override
    public BankTransactionSubCode updateBankTransactionSubCode(String tenantId, BankTransactionSubCodeUpdateResource bankTransactionSubCodeUpdateResource) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        Optional<BankTransactionCode> isPresentBankTransactionCode = bankTransactionCodeRepository.findById(stringToLong(bankTransactionSubCodeUpdateResource.getCodeId()));
        if (isPresentBankTransactionCode.isPresent()) {
            Optional<BankTransactionSubCode> isPresentBankTransactionSubCode = bankTransactionSubCodeRepository.findById(stringToLong(bankTransactionSubCodeUpdateResource.getId()));
            if (isPresentBankTransactionSubCode.isPresent() && isPresentBankTransactionSubCode.get().getCodeId().equals(stringToLong(bankTransactionSubCodeUpdateResource.getCodeId()))) {
                if (isPresentBankTransactionSubCode.get().getSubCode().equals(bankTransactionSubCodeUpdateResource.getSubCode())) {
                    if (!isPresentBankTransactionSubCode.get().getVersion().equals(Long.parseLong(bankTransactionSubCodeUpdateResource.getVersion())))
                        throw new OptimisticLockException(environment.getProperty("common-invalid.version"), "version");


                    BankTransactionSubCode bankTransactionSubCode = getBankTransactionSubCodeById(stringToLong(bankTransactionSubCodeUpdateResource.getId()));
                    bankTransactionSubCode.setDescription(bankTransactionSubCodeUpdateResource.getDescription());
                    bankTransactionSubCode.setPostingType(bankTransactionSubCodeUpdateResource.getPostingType());
                    bankTransactionSubCode.setAllowDormant(bankTransactionSubCodeUpdateResource.getAllowDormant());
                    bankTransactionSubCode.setTransactionCategory(bankTransactionSubCodeUpdateResource.getTransactionCategory());
                    bankTransactionSubCode.setStatus(bankTransactionSubCodeUpdateResource.getStatus());
                    bankTransactionSubCode.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
                    bankTransactionSubCode.setModifiedDate(currentTimestamp);
                    bankTransactionSubCode.setVersion(stringToLong(bankTransactionSubCodeUpdateResource.getVersion()));
                    bankTransactionSubCode.setSyncTs(currentTimestamp);
                    bankTransactionSubCode = bankTransactionSubCodeRepository.saveAndFlush(bankTransactionSubCode);
                    return bankTransactionSubCode;
                } else
                    throw new CodeUniqueException(environment.getProperty("bankTransSubCode.invalid-code"));
            } else
                throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
        } else
            throw new NoRecordFoundException(environment.getProperty("bankTransCode.invalid-code"));
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
    private BankTransactionSubCode getBankTransactionSubCodeById(Long transSubCodeId) {
        Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository.findById(transSubCodeId);
        if (bankTransactionSubCode.isPresent())
            return bankTransactionSubCode.get();
        else
            return null;
    }

    @Override
    public List<BankTransactionSubCode> getAllBankTransactionSubCodes(String tenantId) {
        return bankTransactionSubCodeRepository.findAll();
    }

}
