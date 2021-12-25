package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.AuditData;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.DuplicateRecordException;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.LendingAccountDetailRepository;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.LendingAccountDetailService;
import com.fusionx.lending.product.service.RemoteService;
import com.fusionx.lending.product.service.ValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * API Service related to Lending Account Detail.
 *
 * @author Rohan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        07-10-2021      -               -           Rohan      Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class LendingAccountDetailServiceImpl extends MessagePropertyBase implements LendingAccountDetailService {

    private LendingAccountDetailRepository lendingAccountDetailRepository;
    private RemoteService remoteService;
    private CommonModuleProperties commonModuleProperties;
    private ValidationService validationService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setLendingAccountDetailRepository(LendingAccountDetailRepository lendingAccountDetailRepository) {
        this.lendingAccountDetailRepository = lendingAccountDetailRepository;
    }

    @Autowired
    public void setRemoteService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Autowired
    public void setCommonModuleProperties(CommonModuleProperties commonModuleProperties) {
        this.commonModuleProperties = commonModuleProperties;
    }

    /**
     * Returns the lendingAccountDetail by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of lendingAccountDetail
     */
    @Override
    public List<LendingAccountDetail> findByStatus(String status) {
        return lendingAccountDetailRepository.findByStatus(CommonStatus.valueOf(status));
    }


    /**
     * @param tenantId   the tenant id
     * @param searchText the keyword to search
     * @return list of LendingAccountAdvanceSearchResponse
     */
    @Override
    public List<LendingAccountAdvanceSearchResponse> searchByLendingAccountNumber(String tenantId, String searchText) {
        Predicate<LendingAccountDetail> fillerOnAccountNumber = searchText.equalsIgnoreCase("-1") ?
                lendingAccountDetail -> lendingAccountDetail.getAccountNumber() != null :
                lendingAccountDetail -> lendingAccountDetail.getAccountNumber().toLowerCase().contains(searchText.toLowerCase());
        return lendingAccountDetailRepository.getLendingAccountByTenantId(tenantId)
                .stream()
                .filter(fillerOnAccountNumber)
                .map(la -> getLendingAccountAdvanceSearchResponse(tenantId, la))
                .collect(Collectors.toList());

    }

    /**
     * Create the lendingAccountDetail
     *
     * @param tenantId                        the tenant id
     * @param lendingAccountDetailAddResource the lendingAccountDetailAddResource
     * @return lendingAccountDetail
     */
    @Override
    public LendingAccountDetail addLendingAccountDetail(String tenantId, LendingAccountDetailAddResource lendingAccountDetailAddResource) {
        LendingAccountDetail lendingAccountDetail = new LendingAccountDetail();
        AuditData auditData = new AuditData();

        if (lendingAccountDetailRepository.existsByApprovedLeadId(Long.valueOf(lendingAccountDetailAddResource.getApprovedLeadId())))
            throw new DuplicateRecordException(environment.getProperty(COMMON_DUPLICATE), "Duplicate account");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailAddResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "product");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailAddResource.getSubProductId(), commonModuleProperties.getLendingProduct().concat("/sub-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "sub product");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailAddResource.getBranchId(), commonModuleProperties.getComnCommon().concat("/comn-bank-branch/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "branch");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailAddResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "Me id");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailAddResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "currency");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailAddResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "Tc id");

/*        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailAddResource.getApprovedLeadId(), commonModuleProperties.getLendingOrigination().concat("/lead-info/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "approved lead id");*/

        if (!remoteService.checkIsExist(tenantId.concat("/validate"), lendingAccountDetailAddResource.getRecoveryAccountId(), commonModuleProperties.getCaseAccount().concat("/account/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "recovery account id");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailAddResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "document template id");

        auditData.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        auditData.setCreatedDate(validationService.getCreateOrModifyDate());

        lendingAccountDetail.setAccountNumber(lendingAccountDetailAddResource.getAccountNumber());
        lendingAccountDetail.setLoanAmount(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getLoanAmount()));
        lendingAccountDetail.setCollectionMethod(CollectionMethodEnum.valueOf(lendingAccountDetailAddResource.getCollectionMethod()));
        lendingAccountDetail.setInterestRate(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getInterestRate()));
        lendingAccountDetail.setTermNumber(Long.parseLong(lendingAccountDetailAddResource.getTermNumber()));
        lendingAccountDetail.setCalculationMethod(CalculateMethodEnum.valueOf(lendingAccountDetailAddResource.getCalculationMethod()));
        lendingAccountDetail.setRepaymentCriteria(RepaymentCriteriaEnum.valueOf(lendingAccountDetailAddResource.getRepaymentCriteria()));
        lendingAccountDetail.setNoOfPrePayment(Long.parseLong(lendingAccountDetailAddResource.getNoOfPrePayment()));
        lendingAccountDetail.setAmountPaidInAdvanced(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getAmountPaidInAdvanced()));
        lendingAccountDetail.setResidualValue(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getResidualValue()));
        lendingAccountDetail.setRewards(YesNo.valueOf(lendingAccountDetailAddResource.getRewards()));
        lendingAccountDetail.setRemarks(lendingAccountDetailAddResource.getRemarks());
        lendingAccountDetail.setLoanAmountWithTax(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getLoanAmountWithTax()));
        lendingAccountDetail.setTotalReceivable(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getTotalReceivable()));
        lendingAccountDetail.setDownPaymentAmount(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getDownPaymentAmount()));
        lendingAccountDetail.setLeaseFactor(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getLeaseFactor()));
        lendingAccountDetail.setChargeFactor(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getChargeFactor()));
        lendingAccountDetail.setTotalFactor(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getTotalFactor()));
        lendingAccountDetail.setIrr(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getIrr()));
        lendingAccountDetail.setDueDate(validationService.StringToTimeStamp(lendingAccountDetailAddResource.getDueDate()));
        lendingAccountDetail.setPenalInterestRate(validationService.stringToBigDecimal(lendingAccountDetailAddResource.getPenalInterestRate()));
        lendingAccountDetail.setGracePeriod(Long.parseLong(lendingAccountDetailAddResource.getGracePeriod()));
        lendingAccountDetail.setExpiryDate(validationService.StringToTimeStamp(lendingAccountDetailAddResource.getExpiryDate()));
        lendingAccountDetail.setCurrencyCode(lendingAccountDetailAddResource.getCurrencyCode());
        lendingAccountDetail.setCurrencyCodeNumeric(lendingAccountDetailAddResource.getCurrencyCodeNumeric());
        lendingAccountDetail.setTenantId(lendingAccountDetailAddResource.getTenantId());
        lendingAccountDetail.setStatus(CommonStatus.valueOf(lendingAccountDetailAddResource.getStatus()));
        lendingAccountDetail.setProductId(Long.valueOf(lendingAccountDetailAddResource.getProductId()));
        lendingAccountDetail.setSubProductId(Long.parseLong(lendingAccountDetailAddResource.getSubProductId()));
        lendingAccountDetail.setBranchId(Long.parseLong(lendingAccountDetailAddResource.getBranchId()));
        lendingAccountDetail.setMeId(Long.parseLong(lendingAccountDetailAddResource.getBranchId()));
        lendingAccountDetail.setCurrencyId(Long.parseLong(lendingAccountDetailAddResource.getCurrencyId()));
        lendingAccountDetail.setTcId(Long.parseLong(lendingAccountDetailAddResource.getTcId()));
        lendingAccountDetail.setApprovedLeadId(Long.parseLong(lendingAccountDetailAddResource.getApprovedLeadId()));
        lendingAccountDetail.setRecoveryAccountId(Long.parseLong(lendingAccountDetailAddResource.getRecoveryAccountId()));
        lendingAccountDetail.setDocumentTemplateId(Long.parseLong(lendingAccountDetailAddResource.getDocumentTemplateId()));
        lendingAccountDetail.setAccountStatus(AccountStatusEnum.valueOf(String.valueOf(AccountStatusEnum.CREATED)));
        lendingAccountDetail.setSyncTs(validationService.getCreateOrModifyDate());
        lendingAccountDetail.setAuditData(auditData);
        lendingAccountDetail = lendingAccountDetailRepository.save(lendingAccountDetail);
        //   guarantorService.addGuarantorDetailByLeadId(tenantId, lendingAccountDetailAddResource.getApprovedLeadId(), lendingAccountDetailAddResource.getAccountNumber());
        return lendingAccountDetail;
    }

    /**
     * Updates the lendingAccountDetail object by given object
     *
     * @param tenantId                           the tenant id
     * @param id                                 the id need to be update
     * @param lendingAccountDetailUpdateResource the object which contains data
     * @return
     */
    @Override
    public LendingAccountDetail updateLendingAccountDetail(String tenantId, Long id, LendingAccountDetailUpdateResource lendingAccountDetailUpdateResource) {

        Optional<LendingAccountDetail> isPresentLendingAccountDetail = lendingAccountDetailRepository.findById(id);

        if (!isPresentLendingAccountDetail.get().getVersion().equals(Long.parseLong(lendingAccountDetailUpdateResource.getVersion())))
            throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.LENDING_ACCOUNT_DETAIL);

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailUpdateResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "product");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailUpdateResource.getSubProductId(), commonModuleProperties.getLendingProduct().concat("/sub-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "sub product");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailUpdateResource.getBranchId(), commonModuleProperties.getComnCommon().concat("/comn-bank-branch/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "branch");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailUpdateResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "Me id");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailUpdateResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "currency");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailUpdateResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "Tc id");

        /*if (!remoteService.checkIsExist(tenantId, lendingAccountDetailUpdateResource.getApprovedLeadId(), commonModuleProperties.getLendingOrigination().concat("/lead-info/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "approved lead id");*/

        if (!remoteService.checkIsExist(tenantId.concat("/validate"), lendingAccountDetailUpdateResource.getRecoveryAccountId(), commonModuleProperties.getCaseAccount().concat("/account/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "recovery account id");

        if (!remoteService.checkIsExist(tenantId, lendingAccountDetailUpdateResource.getProductId(), commonModuleProperties.getLendingProduct().concat("/core-product/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "document template id");

        LendingAccountDetail lendingAccountDetail = isPresentLendingAccountDetail.get();

        lendingAccountDetail.setAccountNumber(lendingAccountDetailUpdateResource.getAccountNumber());
        lendingAccountDetail.setLoanAmount(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getLoanAmount()));
        lendingAccountDetail.setCollectionMethod(CollectionMethodEnum.valueOf(lendingAccountDetailUpdateResource.getCollectionMethod()));
        lendingAccountDetail.setInterestRate(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getInterestRate()));
        lendingAccountDetail.setTermNumber(Long.parseLong(lendingAccountDetailUpdateResource.getTermNumber()));
        lendingAccountDetail.setCalculationMethod(CalculateMethodEnum.valueOf(lendingAccountDetailUpdateResource.getCalculationMethod()));
        lendingAccountDetail.setRepaymentCriteria(RepaymentCriteriaEnum.valueOf(lendingAccountDetailUpdateResource.getRepaymentCriteria()));
        lendingAccountDetail.setNoOfPrePayment(Long.parseLong(lendingAccountDetailUpdateResource.getNoOfPrePayment()));
        lendingAccountDetail.setAmountPaidInAdvanced(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getAmountPaidInAdvanced()));
        lendingAccountDetail.setResidualValue(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getResidualValue()));
        lendingAccountDetail.setRewards(YesNo.valueOf(lendingAccountDetailUpdateResource.getRewards()));
        lendingAccountDetail.setRemarks(lendingAccountDetailUpdateResource.getRemarks());
        lendingAccountDetail.setLoanAmountWithTax(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getLoanAmountWithTax()));
        lendingAccountDetail.setTotalReceivable(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getTotalReceivable()));
        lendingAccountDetail.setDownPaymentAmount(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getDownPaymentAmount()));
        lendingAccountDetail.setLeaseFactor(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getLeaseFactor()));
        lendingAccountDetail.setChargeFactor(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getChargeFactor()));
        lendingAccountDetail.setTotalFactor(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getTotalFactor()));
        lendingAccountDetail.setIrr(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getIrr()));
        lendingAccountDetail.setDueDate(validationService.StringToTimeStamp(lendingAccountDetailUpdateResource.getDueDate()));
        lendingAccountDetail.setPenalInterestRate(validationService.stringToBigDecimal(lendingAccountDetailUpdateResource.getPenalInterestRate()));
        lendingAccountDetail.setGracePeriod(Long.parseLong(lendingAccountDetailUpdateResource.getGracePeriod()));
        lendingAccountDetail.setExpiryDate(validationService.StringToTimeStamp(lendingAccountDetailUpdateResource.getExpiryDate()));
        lendingAccountDetail.setCurrencyCode(lendingAccountDetailUpdateResource.getCurrencyCode());
        lendingAccountDetail.setCurrencyCodeNumeric(lendingAccountDetailUpdateResource.getCurrencyCodeNumeric());
        lendingAccountDetail.setTenantId(lendingAccountDetailUpdateResource.getTenantId());
        lendingAccountDetail.setStatus(CommonStatus.valueOf(lendingAccountDetailUpdateResource.getStatus()));
        lendingAccountDetail.setProductId(Long.valueOf(lendingAccountDetailUpdateResource.getProductId()));
        lendingAccountDetail.setSubProductId(Long.parseLong(lendingAccountDetailUpdateResource.getSubProductId()));
        lendingAccountDetail.setBranchId(Long.parseLong(lendingAccountDetailUpdateResource.getBranchId()));
        lendingAccountDetail.setMeId(Long.parseLong(lendingAccountDetailUpdateResource.getBranchId()));
        lendingAccountDetail.setCurrencyId(Long.parseLong(lendingAccountDetailUpdateResource.getCurrencyId()));
        lendingAccountDetail.setTcId(Long.parseLong(lendingAccountDetailUpdateResource.getTcId()));
        lendingAccountDetail.setApprovedLeadId(Long.parseLong(lendingAccountDetailUpdateResource.getApprovedLeadId()));
        lendingAccountDetail.setRecoveryAccountId(Long.parseLong(lendingAccountDetailUpdateResource.getRecoveryAccountId()));
        lendingAccountDetail.setDocumentTemplateId(Long.parseLong(lendingAccountDetailUpdateResource.getDocumentTemplateId()));
        lendingAccountDetail.setAccountStatus(AccountStatusEnum.valueOf(lendingAccountDetailUpdateResource.getAccountStatus()));
        lendingAccountDetail.getAuditData().setModifiedDate(validationService.getCreateOrModifyDate());
        lendingAccountDetail.getAuditData().setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        lendingAccountDetail.setSyncTs(validationService.getCreateOrModifyDate());
        return lendingAccountDetailRepository.save(lendingAccountDetail);
    }


    /**
     * this method responsible for LendingAccountDetail Entity
     * convert to LendingAccountAdvanceSearchResponse
     *
     * @param lendingAccountDetail
     * @return LendingAccountAdvanceSearchResponse
     */
    @Override
    public LendingAccountAdvanceSearchResponse getLendingAccountAdvanceSearchResponse(String tenantId, LendingAccountDetail lendingAccountDetail) {
        return new LendingAccountAdvanceSearchResponse(
                lendingAccountDetail.getAccountNumber(),
                remoteService.getProductById(tenantId, String.valueOf(lendingAccountDetail.getProductId()), commonModuleProperties.getLendingProduct()).getCode(),
                "loan app number",
                lendingAccountDetail.getAccountStatus().toString()
        );
    }

    /**
     * retrieve lending account detail by lending account id
     *
     * @param tenantId  the tenant id
     * @param accountId the account id
     * @return LendingAccountDetailRequestResponseResource
     */
    @Override
    public LendingAccountDetailRequestResponseResource getAccountById(String tenantId, String accountId) {
        LendingAccountDetailRequestResponseResource lendingAccountDetailRequest;
        ProductRequestResponseResource productRequest;
        BankBranchRequestResponseResource bankBranchRequest;
        LeadInfoRequestResponseResource leadInfoRequest;
        SubProductRequestResponseResource subProductRequest;
        Object bankAccountRequest;

        LendingAccountDetail lendingAccountDetail = lendingAccountDetailRepository.getByTenantIdAndId(tenantId, Long.valueOf(accountId));
        if (lendingAccountDetail == null) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "Lending Account Not Found");
        }

        lendingAccountDetailRequest = modelMapper.map(lendingAccountDetail, LendingAccountDetailRequestResponseResource.class);

        productRequest = remoteService.getProductById(tenantId, String.valueOf(lendingAccountDetail.getProductId()), commonModuleProperties.getLendingProduct());
        bankBranchRequest = remoteService.getBranchById(tenantId, String.valueOf(lendingAccountDetail.getBranchId()), commonModuleProperties.getComnCommon());
        leadInfoRequest = remoteService.getLeadInfoById(tenantId, String.valueOf(lendingAccountDetail.getApprovedLeadId()), commonModuleProperties.getLendingOrigination());
        subProductRequest = remoteService.getSubProductIdById(tenantId, String.valueOf(lendingAccountDetail.getSubProductId()), commonModuleProperties.getLendingProduct());
        bankAccountRequest = remoteService.getAccountById(tenantId, String.valueOf(lendingAccountDetail.getRecoveryAccountId()), commonModuleProperties.getCaseAccount());

        lendingAccountDetailRequest.setRecoveryAccount(bankAccountRequest);
        lendingAccountDetailRequest.setProduct(Collections.singletonList(productRequest));
        lendingAccountDetailRequest.setBranch(Collections.singletonList(bankBranchRequest));
        lendingAccountDetailRequest.setApprovedLead(Collections.singletonList(leadInfoRequest));
        lendingAccountDetailRequest.setSubProduct(Collections.singletonList(subProductRequest));

        return lendingAccountDetailRequest;
    }

    /**
     * Update collection method using account id
     *
     * @param tenantId                    the tenant id
     * @param collectionMethodAddResource the collection method add recourse
     * @return LendingAccountDetailRequestResponseResource
     */
    @Override
    public LendingAccountDetail setCollectionMethod(String tenantId, CollectionMethodAddResource collectionMethodAddResource) {

        if (collectionMethodAddResource.getCollectionMethod().equals(CollectionMethodEnum.DIRECT_CREDIT.toString())) {
            if (!collectionMethodAddResource.getTransactionAmount().equals("") || collectionMethodAddResource.getTransactionAmount() == null) {
                throw new ValidateRecordException(environment.getProperty("common.not-null"), "Transaction ammount");
            }
        }

        LendingAccountDetail lendingAccountDetail = lendingAccountDetailRepository.getByTenantIdAndId(tenantId, Long.valueOf(collectionMethodAddResource.getAccountId()));

        if (lendingAccountDetail == null) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "Lending Account Not Found");
        }

        lendingAccountDetail.setCollectionMethod(CollectionMethodEnum.valueOf(collectionMethodAddResource.getCollectionMethod()));
        return lendingAccountDetailRepository.save(lendingAccountDetail);
    }

    /**
     * retrieve lending account by id
     *
     * @param lendingAccountDetailId
     * @return LendingAccountDetail
     */
    @Override
    public Optional<LendingAccountDetail> getByLendingAccountDetailById(Long lendingAccountDetailId) {
        return lendingAccountDetailRepository.findById(lendingAccountDetailId);
    }

    @Override
    public List<LendingAccountDetail> getAll() {
        return lendingAccountDetailRepository.findAll();
    }

}
