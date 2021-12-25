package com.fusionx.lending.product.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.enums.CollectionTypeEnum;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.repository.OtherFeeTypeRepository;
import com.fusionx.lending.product.resources.BankTransactionCodeResponse;
import com.fusionx.lending.product.resources.BankTransactionSubCodeResponse;
import com.fusionx.lending.product.resources.OtherFeeTypeAddResource;
import com.fusionx.lending.product.resources.OtherFeeTypeUpdateResource;
import com.fusionx.lending.product.service.OtherFeeTypeHistoryService;
import com.fusionx.lending.product.service.OtherFeeTypeService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Other Fee Type Service Impl
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 * <p>
 * *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class OtherFeeTypeServiceImpl extends MessagePropertyBase implements OtherFeeTypeService {

    protected static final String REFUNDABLE_PERCENTAGE_NOT_NULL = "refundable-percentage-not-null";

    @Autowired
    private OtherFeeTypeRepository otherFeeTypeRepository;

    @Autowired
    private OtherFeeTypeHistoryService otherFeeTypeHistoryService;

    @Autowired
    private CommonListItemRepository commonListItemRepository;

    @Autowired
    private ValidationService validationService;

    /**
     * @return -JSON array of all Other Fee Type
     * @author Senitha
     * <p>
     * Find all Other Fee Type
     */
    @Override
    public List<OtherFeeType> getAll(String tenantId) {

        List<OtherFeeType> otherFeeTypes = otherFeeTypeRepository.findAll();
        for (OtherFeeType otherFeeType : otherFeeTypes) {
            setName(tenantId, otherFeeType);
        }
        return otherFeeTypes;

    }

    /**
     * @return -JSON array of Other Fee Type
     * @author Senitha
     * <p>
     * Find Other Fee Type by ID
     */
    @Override
    public Optional<OtherFeeType> getById(String tenantId, Long id) {

        Optional<OtherFeeType> isPresentOtherFeeType = otherFeeTypeRepository.findById(id);
        if (isPresentOtherFeeType.isPresent()) {
            OtherFeeType otherFeeType = isPresentOtherFeeType.get();
            setName(tenantId, otherFeeType);
            return Optional.ofNullable(otherFeeType);
        } else {
            return Optional.empty();
        }

    }

    /**
     * @return -JSON array of Other Fee Type
     * @author Senitha
     * <p>
     * Find Other Fee Type by code
     */
    @Override
    public Optional<OtherFeeType> getByCode(String tenantId, String code) {

        Optional<OtherFeeType> isPresentOtherFeeType = otherFeeTypeRepository.findByCode(code);
        if (isPresentOtherFeeType.isPresent()) {
            OtherFeeType otherFeeType = isPresentOtherFeeType.get();
            setName(tenantId, otherFeeType);
            return Optional.ofNullable(otherFeeType);
        } else {
            return Optional.empty();
        }

    }

    /**
     * @return -JSON array of Other Fee Type
     * @author Senitha
     * <p>
     * Find Other Fee Type by name
     */
    @Override
    public List<OtherFeeType> getByName(String tenantId, String name) {

        List<OtherFeeType> otherFeeTypes = otherFeeTypeRepository.findByNameContaining(name);
        for (OtherFeeType otherFeeType : otherFeeTypes) {
            setName(tenantId, otherFeeType);
        }
        return otherFeeTypes;

    }

    /**
     * @return -JSON array of Other Fee Type
     * @author Senitha
     * <p>
     * Find Other Fee Type by status
     */
    @Override
    public List<OtherFeeType> getByStatus(String tenantId, String status) {

        List<OtherFeeType> otherFeeTypes = otherFeeTypeRepository.findByStatus(CommonStatus.valueOf(status));
        for (OtherFeeType otherFeeType : otherFeeTypes) {
            setName(tenantId, otherFeeType);
        }
        return otherFeeTypes;

    }

    /**
     * @param - AddRecoveryFeeTypeResource
     * @return - Successfully saved
     * @author Senitha
     * <p>
     * Insert Other Fee Type
     */
    @Override
    public OtherFeeType addOtherFeeType(String tenantId, OtherFeeTypeAddResource otherFeeTypeAddResource) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");

        Optional<OtherFeeType> isPresentFeeChargeType = otherFeeTypeRepository.findByCode(otherFeeTypeAddResource.getCode());
        if (isPresentFeeChargeType.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty("common.unique"), ServiceEntity.CODE, EntityPoint.OTHER_FEE_TYPE);
        }

        LoggerRequest.getInstance().logInfo("OtherFeeType ******************************** Validate Type Of Fee Category *********************************************/");
        Optional<CommonListItem> isPresentFeeCategory = commonListItemRepository.findByIdAndNameAndReferenceCodeAndStatus(Long.parseLong(otherFeeTypeAddResource.getFeeCategoryId()),
                otherFeeTypeAddResource.getFeeCategoryName(), "FEECATEGORY", CommonStatus.ACTIVE);
        if (!isPresentFeeCategory.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty("feeCategoryId.invalid"), ServiceEntity.FEECATEGORYID, EntityPoint.OTHER_FEE_TYPE);
        }

        LoggerRequest.getInstance().logInfo("OtherFeeType ******************************** Validate Transaction Code *********************************************/");
        validationService.validateTransactionCode(tenantId, Long.parseLong(otherFeeTypeAddResource.getTransactionCodeId()), otherFeeTypeAddResource.getTransactionCode(),
                EntityPoint.OTHER_FEE_TYPE);

        LoggerRequest.getInstance().logInfo("OtherFeeType ******************************** Validate Transaction Sub Code *********************************************/");
        validationService.validateTransactionSubCode(tenantId, Long.parseLong(otherFeeTypeAddResource.getTransactionCodeId()),
                Long.parseLong(otherFeeTypeAddResource.getTransactionSubCodeId()), otherFeeTypeAddResource.getTransactionSubCode(), EntityPoint.OTHER_FEE_TYPE);

        if ((YesNo.YES.toString()).equals(otherFeeTypeAddResource.getRefundable())
                && (otherFeeTypeAddResource.getRefundablePercentage() == null)) {
            throw new ValidateRecordException(environment.getProperty(REFUNDABLE_PERCENTAGE_NOT_NULL), MESSAGE);

        }


        OtherFeeType otherFeeType = new OtherFeeType();
        otherFeeType.setTenantId(tenantId);
        otherFeeType.setCode(otherFeeTypeAddResource.getCode());
        otherFeeType.setName(otherFeeTypeAddResource.getName());
        otherFeeType.setDescription(otherFeeTypeAddResource.getDescription());
        otherFeeType.setFeeCategoryId(Long.parseLong(otherFeeTypeAddResource.getFeeCategoryId()));
        otherFeeType.setTransactionCodeId(Long.parseLong(otherFeeTypeAddResource.getTransactionCodeId()));
        otherFeeType.setTransactionSubCodeId(Long.parseLong(otherFeeTypeAddResource.getTransactionSubCodeId()));
        otherFeeType.setStatus(CommonStatus.valueOf(otherFeeTypeAddResource.getStatus()));
        otherFeeType.setSyncTs(currentTimestamp);
        otherFeeType.setCreatedDate(currentTimestamp);
        otherFeeType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        otherFeeType.setRefundable(otherFeeTypeAddResource.getRefundable() != null ? YesNo.valueOf(otherFeeTypeAddResource.getRefundable()) : YesNo.NO);
        otherFeeType.setRefundablePercentage(otherFeeTypeAddResource.getRefundablePercentage() != null ? validationService.stringToBigDecimal(otherFeeTypeAddResource.getRefundablePercentage()) : null);
        otherFeeType.setCollectionType(otherFeeTypeAddResource.getCollectionType() != null ? CollectionTypeEnum.valueOf(otherFeeTypeAddResource.getCollectionType()) : null);

        otherFeeType = otherFeeTypeRepository.save(otherFeeType);
        return otherFeeType;

    }

    /**
     * @param - UpdateRecoveryFeeTypeResource
     * @return - Successfully saved
     * @author Senitha
     * <p>
     * Update Other Fee Type
     */
    @Override
    public OtherFeeType updateOtherFeeType(String tenantId, OtherFeeTypeUpdateResource otherFeeTypeUpdateResource) {

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");

        Optional<OtherFeeType> isPresentFeeChargeType = otherFeeTypeRepository.findById(Long.parseLong(otherFeeTypeUpdateResource.getId()));
        if (!isPresentFeeChargeType.isPresent()) {
            throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        }

        if (!isPresentFeeChargeType.get().getVersion().equals(Long.parseLong(otherFeeTypeUpdateResource.getVersion()))) {
            throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.VERSION, EntityPoint.OTHER_FEE_TYPE);
        }

        LoggerRequest.getInstance().logInfo("OtherFeeType ******************************** Code Can Not Update *********************************************");
        if (!isPresentFeeChargeType.get().getCode().equals(otherFeeTypeUpdateResource.getCode())) {
            throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.OTHER_FEE_TYPE);
        }

        LoggerRequest.getInstance().logInfo("OtherFeeType ******************************** Validate Type Of Fee Category *********************************************/");
        Optional<CommonListItem> isPresentFeeCategory = commonListItemRepository.findByIdAndNameAndReferenceCodeAndStatus(Long.parseLong(otherFeeTypeUpdateResource.getFeeCategoryId()),
                otherFeeTypeUpdateResource.getFeeCategoryName(), "FEECATEGORY", CommonStatus.ACTIVE);
        if (!isPresentFeeCategory.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty("feeCategoryId.invalid"), ServiceEntity.FEECATEGORYID, EntityPoint.OTHER_FEE_TYPE);
        }

        LoggerRequest.getInstance().logInfo("OtherFeeType ******************************** Validate Transaction Code *********************************************/");
        validationService.validateTransactionCode(tenantId, Long.parseLong(otherFeeTypeUpdateResource.getTransactionCodeId()), otherFeeTypeUpdateResource.getTransactionCode(),
                EntityPoint.OTHER_FEE_TYPE);

        LoggerRequest.getInstance().logInfo("OtherFeeType ******************************** Validate Transaction Sub Code *********************************************/");
        validationService.validateTransactionSubCode(tenantId, Long.parseLong(otherFeeTypeUpdateResource.getTransactionCodeId()),
                Long.parseLong(otherFeeTypeUpdateResource.getTransactionSubCodeId()), otherFeeTypeUpdateResource.getTransactionSubCode(), EntityPoint.OTHER_FEE_TYPE);

        OtherFeeType otherFeeType = isPresentFeeChargeType.get();

        if ((YesNo.YES.toString()).equals(otherFeeTypeUpdateResource.getRefundable())
                && (otherFeeTypeUpdateResource.getRefundablePercentage() == null)) {
            throw new ValidateRecordException(environment.getProperty(REFUNDABLE_PERCENTAGE_NOT_NULL), MESSAGE);

        }


        LoggerRequest.getInstance().logInfo("OtherFeeType ******************************** Save Other Fee Type History *********************************************");
        otherFeeTypeHistoryService.insertOtherFeeTypeHistory(tenantId, otherFeeType);

        otherFeeType.setTenantId(tenantId);
        otherFeeType.setCode(otherFeeTypeUpdateResource.getCode());
        otherFeeType.setName(otherFeeTypeUpdateResource.getName());
        otherFeeType.setDescription(otherFeeTypeUpdateResource.getDescription());
        otherFeeType.setFeeCategoryId(Long.parseLong(otherFeeTypeUpdateResource.getFeeCategoryId()));
        otherFeeType.setTransactionCodeId(Long.parseLong(otherFeeTypeUpdateResource.getTransactionCodeId()));
        otherFeeType.setTransactionSubCodeId(Long.parseLong(otherFeeTypeUpdateResource.getTransactionSubCodeId()));
        otherFeeType.setStatus(CommonStatus.valueOf(otherFeeTypeUpdateResource.getStatus()));
        otherFeeType.setSyncTs(currentTimestamp);
        otherFeeType.setModifiedDate(currentTimestamp);
        otherFeeType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        otherFeeType.setRefundable(otherFeeTypeUpdateResource.getRefundable() != null ? YesNo.valueOf(otherFeeTypeUpdateResource.getRefundable()) : YesNo.NO);
        otherFeeType.setRefundablePercentage(otherFeeTypeUpdateResource.getRefundablePercentage() != null ? validationService.stringToBigDecimal(otherFeeTypeUpdateResource.getRefundablePercentage()) : null);
        otherFeeType.setCollectionType(otherFeeTypeUpdateResource.getCollectionType() != null ? CollectionTypeEnum.valueOf(otherFeeTypeUpdateResource.getCollectionType()) : null);

        otherFeeType = otherFeeTypeRepository.saveAndFlush(otherFeeType);
        return otherFeeType;

    }

    /**
     * @author Senitha
     * <p>
     * Set names from validations
     */
    private void setName(String tenantId, OtherFeeType otherFeeType) {

        Optional<CommonListItem> feeCategory = commonListItemRepository.findById(otherFeeType.getFeeCategoryId());
        otherFeeType.setFeeCategoryName(feeCategory.get().getName());

        BankTransactionCodeResponse bankTransactionCodeResponse = validationService.getTransactionCode(tenantId, otherFeeType.getTransactionCodeId(), EntityPoint.OTHER_FEE_TYPE);

        if (bankTransactionCodeResponse != null) {
            // throw new ValidateRecordException(environment.getProperty("invalid.transaction-code"), "transactionCode");

            otherFeeType.setTransactionCode(bankTransactionCodeResponse.getBankTransCode());
            otherFeeType.setTransactionCodeDesc(bankTransactionCodeResponse.getDescription());

            BankTransactionSubCodeResponse bankTransactionSubCodeResponse = validationService.getTransactionSubCode(tenantId, otherFeeType.getTransactionSubCodeId(), EntityPoint.OTHER_FEE_TYPE);
            otherFeeType.setTransactionSubCode(bankTransactionSubCodeResponse.getSubCode());
            otherFeeType.setTransactionSubCodeDesc(bankTransactionSubCodeResponse.getDescription());
        }

    }

}
