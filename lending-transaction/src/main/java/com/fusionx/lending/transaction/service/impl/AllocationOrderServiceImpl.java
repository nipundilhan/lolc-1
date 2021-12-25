package com.fusionx.lending.transaction.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.AllocationOrder;
import com.fusionx.lending.transaction.domain.AllocationTemplate;
import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.enums.BankTransactionSubCodeStatus;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.enums.PostingType;
import com.fusionx.lending.transaction.exception.BackendDataException;
import com.fusionx.lending.transaction.exception.OtherException;
import com.fusionx.lending.transaction.exception.UserNotFound;
import com.fusionx.lending.transaction.repo.AllocationOrderRepository;
import com.fusionx.lending.transaction.repo.AllocationTemplateRepository;
import com.fusionx.lending.transaction.repo.BankTransactionCodeRepository;
import com.fusionx.lending.transaction.repo.BankTransactionSubCodeRepository;
import com.fusionx.lending.transaction.resource.AllocationOrderResource;
import com.fusionx.lending.transaction.service.AllocationOrderService;

@Component
@Transactional(rollbackFor = Exception.class)
public class AllocationOrderServiceImpl implements AllocationOrderService {
    @Autowired
    AllocationOrderRepository allocationOrderRepository;
    @Autowired
    BankTransactionCodeRepository bankTransactionCodeRepository;
    @Autowired
    BankTransactionSubCodeRepository bankTransactionSubCodeRepository;
    @Autowired
    private Environment environment;
    

    private String userNotFound = "common.user-not-found";
    
    @Autowired
	private AllocationTemplateRepository allocationTemplateRepository;

    @Override
    public List<AllocationOrder> findAll() {
        return allocationOrderRepository.findAll();
    }

    @Override
    public Optional<AllocationOrder> findById(Long id) {
        return allocationOrderRepository.findById(id);
    }

    @Override
    public Optional<AllocationOrder> findByReferenceCode(String code) {
        return allocationOrderRepository.findByReferenceCode(code);
    }

    /*@Override
    public List<AllocationOrder> findByAccountStatus(String accountStatus) {
        return allocationOrderRepository.findByAccountStatus(AccountStatusEnum.valueOf(accountStatus));
    }*/

    @Override
    public List<AllocationOrder> findByStatus(String status) {
        return allocationOrderRepository.findByStatus(status);
    }

    @Override
    public List<AllocationOrder> findByBankTransactionSubCode(String subCode) {
        return allocationOrderRepository.findByBankTransactionSubCode(subCode);
    }
    
    @Override
    public List<AllocationOrder> findByAllocationTemplateId(Long allocationTemplateId) {
        return allocationOrderRepository.findByAllocationTempId(allocationTemplateId);
    }

    @Override
    public AllocationOrder addAllocationOrder(String tenantId, AllocationOrderResource allocationOrderResource) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(userNotFound));

        if (allocationOrderRepository.existsByReferenceCode(allocationOrderResource.getReferenceCode())) {
            throw new BackendDataException("Reference Code", environment.getProperty("common.recorde-duplicate"));
        }
        
        Optional<AllocationTemplate> allocationTemplate = allocationTemplateRepository.findByIdAndStatus( Long.parseLong(allocationOrderResource.getAllocationTemplateId()), CommonStatus.ACTIVE);
        if(!allocationTemplate.isPresent()) {
        	throw new BackendDataException("Allocation Template", environment.getProperty("common.record-not-found"));
        }

        /*if (allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.ACTIVE.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.APPROVED_FINAL_WITHDRAWAL.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.BLOCK.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CANCEL.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CLOSE_CANCELLED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CLOSE_PENDING.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CLOSED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_BLOCK.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_DE_ACTIVATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_RE_ACTIVATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_STOP_PAYMENT.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_STOP_PAYMENT_REVERSAL.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.DE_ACTIVATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.DORMANT.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.RE_ACTIVATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.REJECTED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.STOP_PAYMENT.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.STOP_PAYMENT_REVERSAL.toString())) {*/

            Optional<BankTransactionCode> bankTransactionCode = bankTransactionCodeRepository.findByIdAndStatus(Long.parseLong(allocationOrderResource.getBankTransactionCodeId()), BankTransactionCodeStatus.ACTIVE);
            if (!bankTransactionCode.isPresent()) {
                throw new BackendDataException("Transaction Code", environment.getProperty("common.record-not-found"));
            } else {
                if (!bankTransactionCode.get().getDescription().equalsIgnoreCase(allocationOrderResource.getBankTransactionCodeDescription())
                        || !bankTransactionCode.get().getStatus().equals(BankTransactionCodeStatus.ACTIVE)) {
                    throw new BackendDataException("Transaction Code Description", "NOT_MATCH");
                }
            }

            Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository.findByIdAndBankTransactionCodeIdAndStatusAndPostingType(Long.parseLong(allocationOrderResource.getBankTransactionSubCodeId()), Long.parseLong(allocationOrderResource.getBankTransactionCodeId()), BankTransactionSubCodeStatus.ACTIVE, PostingType.CREDIT);
            if (!bankTransactionSubCode.isPresent()) {
                throw new BackendDataException("Transaction Sub Code", environment.getProperty("common.record-not-found"));
            } else {
                if (!bankTransactionSubCode.get().getDescription().equalsIgnoreCase(allocationOrderResource.getBankTransactionSubCodeDescription())
                        || !bankTransactionSubCode.get().getStatus().equals(BankTransactionSubCodeStatus.ACTIVE)) {
                    throw new BackendDataException("Transaction Sub Code Description", "NOT_MATCH");
                }
            }

            if (allocationOrderRepository.existsByAllocationTempIdAndBankTransactionSubId(Long.parseLong(allocationOrderResource.getAllocationTemplateId()), Long.parseLong(allocationOrderResource.getBankTransactionSubCodeId()))) {
                throw new BackendDataException("Record Duplicate for Template ID and Transaction Subcode", environment.getProperty("common.record-already-exists"));

            }

            if (allocationOrderRepository.existsByAllocationTempIdAndBankTransactionSubIdAndAllocationOrder(Long.parseLong(allocationOrderResource.getAllocationTemplateId()), Long.parseLong(allocationOrderResource.getBankTransactionSubCodeId()), Long.parseLong(allocationOrderResource.getAllocationOrder()))) {
                throw new BackendDataException("Record Duplicate for Template ID, Allocation Order and Transaction Subcode", environment.getProperty("common.record-already-exists"));
            }
            
            if (allocationOrderRepository.existsByAllocationTempIdAndAllocationOrder(Long.parseLong(allocationOrderResource.getAllocationTemplateId()), Long.parseLong(allocationOrderResource.getAllocationOrder()))) {
                throw new BackendDataException("Record Duplicate for Template ID and Allocation Order", environment.getProperty("common.record-already-exists"));
            }

            AllocationOrder allocationOrder = new AllocationOrder();
            allocationOrder.setTenantId(tenantId);
            allocationOrder.setReferenceCode(allocationOrderResource.getReferenceCode());
            //allocationOrder.setAccountStatus(AccountStatusEnum.valueOf(allocationOrderResource.getAccountStatus()));
            allocationOrder.setAllocationTemp(allocationTemplate.get());
            allocationOrder.setBankTransaction(bankTransactionCode.get());
            allocationOrder.setBankTransactionCode(bankTransactionCode.get().getCode());
            allocationOrder.setBankTransactionSub(bankTransactionSubCode.get());
            allocationOrder.setBankTransactionSubCode(bankTransactionSubCode.get().getSubCode());
            allocationOrder.setAllocationOrder(Long.parseLong(allocationOrderResource.getAllocationOrder()));
            allocationOrder.setStatus(allocationOrderResource.getStatus());
            allocationOrder.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
            allocationOrder.setCreatedDate(currentTimestamp);
            allocationOrder.setSyncTs(currentTimestamp);

            allocationOrder = allocationOrderRepository.save(allocationOrder);

            return allocationOrder;

        /*} else {
            throw new BackendDataException("Account Status", environment.getProperty("acc-status-not-found"));
        }*/
    }

    @Override
    public AllocationOrder updateAllocationOrder(String tenantId, AllocationOrderResource allocationOrderResource, Long id) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        if (LogginAuthentcation.getInstance().getUserName() == null
                || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(userNotFound));

        if (!allocationOrderRepository.existsById(id)) {
            throw new OtherException("Allocation ID not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (allocationOrderRepository.existsByReferenceCodeAndIdNot(allocationOrderResource.getReferenceCode(), id)) {
            throw new BackendDataException("Reference Code", environment.getProperty("common.recorde-duplicate"));
        }

        /*if (allocationOrderRepository.existsByAccountStatusAndBankTransactionIdAndBankTransactionSubIdAndAllocationOrderAndIdNot(AccountStatusEnum.valueOf(allocationOrderResource.getAccountStatus()), Long.parseLong(allocationOrderResource.getBankTransactionCodeId()), Long.parseLong(allocationOrderResource.getBankTransactionSubCodeId()), Long.parseLong(allocationOrderResource.getAllocationOrder()), id)) {
            throw new OtherException(environment.getProperty("common.recorde-duplicate"), HttpStatus.UNPROCESSABLE_ENTITY);
        }*/

        AllocationOrder allocationOrder = allocationOrderRepository.getOne(id);

        if (allocationOrderResource.getVersion() != null) {
            if (!allocationOrder.getVersion().equals(Long.parseLong(allocationOrderResource.getVersion())))
                throw new OtherException(environment.getProperty("common.invalid-version"), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            throw new OtherException(environment.getProperty("version.not-null"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        Optional<AllocationTemplate> allocationTemplate = allocationTemplateRepository.findByIdAndStatus( Long.parseLong(allocationOrderResource.getAllocationTemplateId()), CommonStatus.ACTIVE);
        if(!allocationTemplate.isPresent()) {
        	throw new BackendDataException("Allocation Template", environment.getProperty("common.record-not-found"));
        }

        /*if (allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.ACTIVE.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.APPROVED_FINAL_WITHDRAWAL.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.BLOCK.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CANCEL.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CLOSE_CANCELLED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CLOSE_PENDING.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CLOSED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_BLOCK.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_DE_ACTIVATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_RE_ACTIVATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_STOP_PAYMENT.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATE_STOP_PAYMENT_REVERSAL.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.CREATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.DE_ACTIVATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.DORMANT.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.RE_ACTIVATED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.REJECTED.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.STOP_PAYMENT.toString()) ||
                allocationOrderResource.getAccountStatus().equals(AccountStatusEnum.STOP_PAYMENT_REVERSAL.toString())) {*/

            Optional<BankTransactionCode> bankTransactionCode = bankTransactionCodeRepository.findByIdAndStatus(Long.parseLong(allocationOrderResource.getBankTransactionCodeId()), BankTransactionCodeStatus.ACTIVE);
            if (!bankTransactionCode.isPresent()) {
                throw new BackendDataException("Transaction Code", environment.getProperty("common.record-not-found"));
            } else {
                if (!bankTransactionCode.get().getDescription().equalsIgnoreCase(allocationOrderResource.getBankTransactionCodeDescription())
                        || !bankTransactionCode.get().getStatus().equals(BankTransactionCodeStatus.ACTIVE)) {
                    throw new BackendDataException("Transaction Code Description", "NOT_MATCH");
                }
            }

            Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository.findByIdAndBankTransactionCodeIdAndStatusAndPostingType(Long.parseLong(allocationOrderResource.getBankTransactionSubCodeId()), Long.parseLong(allocationOrderResource.getBankTransactionCodeId()), BankTransactionSubCodeStatus.ACTIVE, PostingType.CREDIT);
            if (!bankTransactionSubCode.isPresent()) {
                throw new BackendDataException("Transaction Sub Code", environment.getProperty("common.record-not-found"));
            } else {
                if (!bankTransactionSubCode.get().getDescription().equalsIgnoreCase(allocationOrderResource.getBankTransactionSubCodeDescription())
                        || !bankTransactionSubCode.get().getStatus().equals(BankTransactionSubCodeStatus.ACTIVE)) {
                    throw new BackendDataException("Transaction Sub Code Description", "NOT_MATCH");
                }
            }
    		
    		if(allocationOrderRepository.existsByAllocationTempIdAndBankTransactionSubIdAndIdNot(Long.parseLong(allocationOrderResource.getAllocationTemplateId()), Long.parseLong(allocationOrderResource.getBankTransactionSubCodeId()),id)) {
    			throw new BackendDataException("Record Duplicate for Template ID and Transaction Subcode",environment.getProperty("common.record-already-exists"));
    			
    		}

            if (allocationOrderRepository.existsByAllocationTempIdAndAllocationOrderAndIdNot(Long.parseLong(allocationOrderResource.getAllocationTemplateId()), Long.parseLong(allocationOrderResource.getAllocationOrder()), id)) {
                throw new BackendDataException("Record Duplicate for Template ID and Allocation Order", environment.getProperty("common.record-already-exists"));

            }

            if (allocationOrderRepository.existsByAllocationTempIdAndBankTransactionSubIdAndAllocationOrderAndIdNot(Long.parseLong(allocationOrderResource.getAllocationTemplateId()), Long.parseLong(allocationOrderResource.getBankTransactionSubCodeId()), Long.parseLong(allocationOrderResource.getAllocationOrder()), id)) {
                throw new BackendDataException("Record Duplicate for Template ID, Allocation Order and Transaction Subcode", environment.getProperty("common.record-already-exists"));
            }

            allocationOrder.setTenantId(tenantId);
            allocationOrder.setReferenceCode(allocationOrderResource.getReferenceCode());
            //allocationOrder.setAccountStatus(AccountStatusEnum.valueOf(allocationOrderResource.getAccountStatus()));
            allocationOrder.setAllocationTemp(allocationTemplate.get());
            allocationOrder.setBankTransaction(bankTransactionCode.get());
            allocationOrder.setBankTransactionCode(bankTransactionCode.get().getCode());
            allocationOrder.setBankTransactionSub(bankTransactionSubCode.get());
            allocationOrder.setBankTransactionSubCode(bankTransactionSubCode.get().getSubCode());
            allocationOrder.setAllocationOrder(Long.parseLong(allocationOrderResource.getAllocationOrder()));
            allocationOrder.setStatus(allocationOrderResource.getStatus());
            allocationOrder.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
            allocationOrder.setModifiedDate(currentTimestamp);
            allocationOrder.setSyncTs(currentTimestamp);

            allocationOrder = allocationOrderRepository.save(allocationOrder);

            return allocationOrder;

        /*} else {
            throw new BackendDataException("Account Status", environment.getProperty("common.record-not-found"));
        }*/

    }

    @Override
    public List<AllocationOrder> findByBankTransactionCode(String code) {
        return allocationOrderRepository.findByBankTransactionCode(code);
    }

}
