package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.domain.LendingTransaction;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.LendingTransactionSearchEnum;
import com.fusionx.lending.product.enums.PostingTypeEnum;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.LendingTransactionRepository;
import com.fusionx.lending.product.resources.LeadInfoRequestResponseResource;
import com.fusionx.lending.product.resources.LendingTransactionAddResource;
import com.fusionx.lending.product.resources.TcCommonPaymentScheduleResponse;
import com.fusionx.lending.product.resources.TcHeaderRequestResponseResource;
import com.fusionx.lending.product.service.LendingAccountDetailService;
import com.fusionx.lending.product.service.LendingTransactionService;
import com.fusionx.lending.product.service.RemoteService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * API Service related to Lending Transaction.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #          Date            Story Point     Task No       Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        28-10-2021          -               -           Thushan                  Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class LendingTransactionServiceImpl extends MessagePropertyBase implements LendingTransactionService {

    private LendingTransactionRepository lendingTransactionRepository;
    private LendingAccountDetailService lendingAccountDetailService;
    private ValidationService validationService;

    private RemoteService remoteService;

    @Autowired
    public void setRemoteService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setLendingAccountDetailService(LendingAccountDetailService lendingAccountDetailService) {
        this.lendingAccountDetailService = lendingAccountDetailService;
    }

    @Autowired
    public void setLendingTransactionRepository(LendingTransactionRepository lendingTransactionRepository) {
        this.lendingTransactionRepository = lendingTransactionRepository;
    }

    /**
     * Returns the borrowers by lending account id
     *
     * @param lendingAccountId the id of the lending account
     * @return the Object of lending account.
     */
    @Override
    public List<LendingTransaction> findByLendingAccountId(Long lendingAccountId) {
        LendingAccountDetail lendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(lendingAccountId).get();
        return lendingTransactionRepository.findAllByLendingAccountId(lendingAccountDetail);
    }

    /**
     * Gets the lending transaction by id
     *
     * @param id the id of the lending transaction
     * @return the set of lending transaction
     */
    @Override
    public Optional<LendingTransaction> findById(Long id) {
        return lendingTransactionRepository.findById(id);
    }

    /**
     * Create lending Transaction into DB
     *
     * @param tenantId                      the tenant id
     * @param lendingTransactionAddResource the object to save
     * @return the saved lendingTransaction.
     */
    @Override
    public LendingTransaction addLendingTransaction(String tenantId, LendingTransactionAddResource lendingTransactionAddResource) {
        LendingTransaction lendingTransaction = new LendingTransaction();
        LendingAccountDetail lendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(lendingTransactionAddResource.getLendingAccountId())).get();

        lendingTransaction.setTransactionDate(validationService.StringToTimeStamp(lendingTransactionAddResource.getTransactionDate()));
        lendingTransaction.setValueDate(validationService.StringToTimeStamp(lendingTransactionAddResource.getValueDate()));
        lendingTransaction.setTransactionSubCode(lendingTransactionAddResource.getTransactionSubCode());
        lendingTransaction.setPostingType(PostingTypeEnum.valueOf(lendingTransactionAddResource.getPostingType()));
        lendingTransaction.setAmount(validationService.stringToBigDecimal(lendingTransactionAddResource.getAmount()));
        lendingTransaction.setCurrencyCode(lendingTransactionAddResource.getCurrencyCode());
        lendingTransaction.setCurrencyCodeNumeric(lendingTransactionAddResource.getCurrencyCodeNumeric());
        lendingTransaction.setCurrencyId(Long.valueOf(lendingTransactionAddResource.getCurrencyId()));
        lendingTransaction.setSequenceOrder(Long.valueOf(lendingTransactionAddResource.getSequenceOrder()));
        lendingTransaction.setReferenceId(Long.valueOf(lendingTransactionAddResource.getReferenceId()));
        lendingTransaction.setTenantId(lendingTransactionAddResource.getTenantId());
        lendingTransaction.setSyncTs(validationService.getCreateOrModifyDate());
        lendingTransaction.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        lendingTransaction.setCreatedDate(validationService.getCreateOrModifyDate());
        lendingTransaction.setLendingAccountId(lendingAccountDetail);
        lendingTransaction.setStatus(CommonStatus.valueOf(lendingTransactionAddResource.getStatus()));
        lendingTransaction.setTenantId(tenantId);
        return lendingTransactionRepository.save(lendingTransaction);
    }

    /**
     * Search lending transaction
     *
     * @param searchQ     the search query
     * @param searchParam the search parameters
     * @param pageable    the pageable
     * @return the pageable of lending transaction
     */
    @Override
    public Page<LendingTransaction> searchLendingTransactions(String searchQ, String searchParam, Pageable pageable) {

        Predicate<LendingTransaction> filterRequest = null;
        String finalSearchQ = (searchQ == null || searchQ.isEmpty()) ? "" : searchQ;

        if (finalSearchQ.equals("") && finalSearchQ.equals("") || searchParam == null) {
            filterRequest = lt -> lt.getId() != null;
        } else if (LendingTransactionSearchEnum.valueOf(searchParam).equals(LendingTransactionSearchEnum.POSTING_TYPE)) {
            filterRequest = finalSearchQ.equalsIgnoreCase("") ?
                    lt -> lt.getId() != null :
                    lt -> lt.getPostingType().toString().toLowerCase().contains(finalSearchQ.toLowerCase());
        } else if (LendingTransactionSearchEnum.valueOf(searchParam).equals(LendingTransactionSearchEnum.REFERENCE_ID)) {
            filterRequest = finalSearchQ.equalsIgnoreCase("") ?
                    lt -> lt.getId() != null :
                    lt -> lt.getReferenceId().toString().toLowerCase().contains(finalSearchQ.toLowerCase());
        } else if (LendingTransactionSearchEnum.valueOf(searchParam).equals(LendingTransactionSearchEnum.TRANSACTION_DATE)) {
            filterRequest = finalSearchQ.equalsIgnoreCase("") ?
                    lt -> lt.getId() != null :
                    lt -> lt.getTransactionDate().toString().toLowerCase().contains(finalSearchQ.toLowerCase());
        } else if (LendingTransactionSearchEnum.valueOf(searchParam).equals(LendingTransactionSearchEnum.TRANSACTION_SUB_CODE)) {
            filterRequest = finalSearchQ.equalsIgnoreCase("") ?
                    lt -> lt.getId() != null :
                    lt -> lt.getTransactionSubCode().toString().toLowerCase().contains(finalSearchQ.toLowerCase());
        } else if (LendingTransactionSearchEnum.valueOf(searchParam).equals(LendingTransactionSearchEnum.VALUE_DATE)) {
            filterRequest = finalSearchQ.equalsIgnoreCase("") ?
                    lt -> lt.getId() != null :
                    lt -> lt.getValueDate().toString().toLowerCase().contains(finalSearchQ.toLowerCase());
        }

        List<LendingTransaction> filterLendingTransactions = lendingTransactionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(LendingTransaction::getId))
                .filter(filterRequest)
                .collect(Collectors.toList());

        return new PageImpl<>(filterLendingTransactions,new PageRequest(pageable.getPageNumber(),pageable.getPageSize()),filterLendingTransactions.size());
    }


    @Override
    public void saveLendingTransaction(String tenantId, String lendingAccountId) {
        Optional<LendingAccountDetail> isPresentLendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(lendingAccountId));

        if (!isPresentLendingAccountDetail.isPresent())
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "Lending account id");

        // not created tc_hedder by ID API 2021-10-29
        TcHeaderRequestResponseResource tcHeaderRequestResponseResource = remoteService.getTcHeaderById(tenantId, isPresentLendingAccountDetail.get().getTcId().toString(), "serviceURL");
        if (tcHeaderRequestResponseResource == null)
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "TC id");

        LendingTransaction lendingTransaction = new LendingTransaction();
        lendingTransaction.setCurrencyId(Long.valueOf(tcHeaderRequestResponseResource.getCurrencyId()));
        lendingTransaction.setCurrencyCode(tcHeaderRequestResponseResource.getCurrencyCode());
        lendingTransaction.setCurrencyCodeNumeric(tcHeaderRequestResponseResource.getCurrencyCodeNumeric());
        lendingTransaction.setLendingAccountId(isPresentLendingAccountDetail.get());

        Predicate<TcCommonPaymentScheduleResponse> filterOnSequence = tcCommonPaymentScheduleResponse -> tcCommonPaymentScheduleResponse.getSequence() == 0;
        switch (tcHeaderRequestResponseResource.getCalculationMethod()) {
            case FIXD:
                lendingTransactionRepository.saveAll(tcHeaderRequestResponseResource.getTcAmortizePaymentDetail()
                        .stream()
                        .filter(filterOnSequence)
                        .map(tcCommonPaymentScheduleResponse -> getLendingTransactionEntity(lendingTransaction, tcCommonPaymentScheduleResponse))
                        .collect(Collectors.toList()));
                break;
            case STRU:
                lendingTransactionRepository.saveAll(tcHeaderRequestResponseResource.getTcStructuredPaymentSchedule()
                        .stream()
                        .filter(filterOnSequence)
                        .map(tcCommonPaymentScheduleResponse -> getLendingTransactionEntity(lendingTransaction, tcCommonPaymentScheduleResponse))
                        .collect(Collectors.toList()));
                break;
            case REVO:
                lendingTransactionRepository.saveAll(tcHeaderRequestResponseResource.getTcRevolvingPaymentDetails()
                        .stream()
                        .filter(filterOnSequence)
                        .map(tcCommonPaymentScheduleResponse -> getLendingTransactionEntity(lendingTransaction, tcCommonPaymentScheduleResponse))
                        .collect(Collectors.toList()));
                break;
            default:
        }

    }

    @Override
    public LendingTransaction getLendingTransactionEntity(LendingTransaction lendingTransaction, TcCommonPaymentScheduleResponse tcCommonPaymentScheduleResponse) {
        lendingTransaction.setTransactionDate(validationService.getCreateOrModifyDate());
        lendingTransaction.setValueDate(null);
        lendingTransaction.setTransactionSubCode(tcCommonPaymentScheduleResponse.getTransactionTypeCode());
        lendingTransaction.setPostingType(PostingTypeEnum.DUES);
        lendingTransaction.setAmount(tcCommonPaymentScheduleResponse.getAmount());
        lendingTransaction.setSequenceOrder(tcCommonPaymentScheduleResponse.getSequence());
        lendingTransaction.setReferenceId(null);
        lendingTransaction.setSyncTs(validationService.getCreateOrModifyDate());
        lendingTransaction.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        lendingTransaction.setCreatedDate(validationService.getCreateOrModifyDate());
        lendingTransaction.setTenantId(tcCommonPaymentScheduleResponse.getTenantId());
        return lendingTransaction;
    }

    @Override
    public List<LendingTransaction> getAll() {
        return lendingTransactionRepository.findAll();
    }
}

