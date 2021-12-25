package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.resources.CommonListAddResource;
import com.fusionx.lending.product.resources.CommonListUpdateResource;
import com.fusionx.lending.product.service.CommonListItemHistoryService;
import com.fusionx.lending.product.service.CommonListItemService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Common List Item.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * ....................................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6514             Senitha Perera          Created         ChinthakaMa     16-Sep-2021
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CommonListItemServiceImpl extends MessagePropertyBase implements CommonListItemService {

    private CommonListItemRepository commonListRepository;
    private CommonListItemHistoryService commonListItemHistoryService;
    private ValidationService validationService;

    @Autowired
    public void setCommonListRepository(CommonListItemRepository commonListItemRepository) {
        this.commonListRepository = commonListItemRepository;
    }

    @Autowired
    public void setCommonListItemHistoryService(CommonListItemHistoryService commonListItemHistoryService) {
        this.commonListItemHistoryService = commonListItemHistoryService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public List<CommonListItem> findAll() {
        return commonListRepository.findAll();
    }

    @Override
    public Optional<CommonListItem> findById(Long id) {
        return commonListRepository.findById(id);
    }

    @Override
    public List<CommonListItem> findByStatus(String status) {
        return commonListRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public List<CommonListItem> findByName(String name) {
        return commonListRepository.findByNameContaining(name);
    }

    @Override
    public List<CommonListItem> findByCode(String code) {
        return commonListRepository.findByCode(code);
    }

    @Override
    public List<CommonListItem> findByReferenceCode(String referenceCode) {
        return commonListRepository.findByReferenceCode(referenceCode);
    }

    @Override
    public Boolean existsById(Long id) {
        return commonListRepository.existsById(id);
    }

    @Override
    public Long saveAndValidateCommonList(String tenantId, String createdUser, CommonListAddResource commonListAddResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");

        LoggerRequest.getInstance().logInfo("CommonList ******************************** Validate Code & Reference Code *********************************************");
        if (Boolean.TRUE.equals(commonListRepository.existsByCodeAndReferenceCodeAndStatus(commonListAddResource.getCode(), commonListAddResource.getReferenceCode(), CommonStatus.ACTIVE)))
            throw new InvalidServiceIdException(environment.getProperty("common.unique"), ServiceEntity.CODE, EntityPoint.COMMON_LIST_ITEM);

        java.sql.Timestamp currentDateTime = validationService.getCreateOrModifyDate();

        CommonListItem commonList = new CommonListItem();
        commonList.setTenantId(tenantId);
        commonList.setCode(commonListAddResource.getCode());
        commonList.setName(commonListAddResource.getName());
        commonList.setReferenceCode(commonListAddResource.getReferenceCode());
        commonList.setStatus(CommonStatus.valueOf(commonListAddResource.getStatus()));
        commonList.setAttribute1(commonListAddResource.getAttribute1());
        commonList.setAttribute2(commonListAddResource.getAttribute2());
        commonList.setAttribute3(commonListAddResource.getAttribute3());
        commonList.setAttribute4(commonListAddResource.getAttribute4());
        commonList.setAttribute5(commonListAddResource.getAttribute5());
        commonList.setCreatedUser(createdUser);
        commonList.setCreatedDate(currentDateTime);
        commonList.setSyncTs(currentDateTime);
        commonList = commonListRepository.saveAndFlush(commonList);

        return commonList.getId();
    }

    @Override
    public Long updateAndValidateCommonList(String tenantId, String createdUser, Long id, CommonListUpdateResource commonListUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");

        Optional<CommonListItem> isPresentCommonList = commonListRepository.findById(id);

        if (!isPresentCommonList.isPresent()) {
            throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        }

        if (!isPresentCommonList.get().getVersion().equals(Long.parseLong(commonListUpdateResource.getVersion())))
            throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.VERSION, EntityPoint.COMMON_LIST_ITEM);

        LoggerRequest.getInstance().logInfo("CommonList ******************************** Validate Code & Reference Code*********************************************");
        Optional<CommonListItem> isPresentCommonListByCode = commonListRepository.findByCodeAndReferenceCodeAndIdNotIn(commonListUpdateResource.getCode(), commonListUpdateResource.getReferenceCode(), id);

        if (isPresentCommonListByCode.isPresent())
            throw new InvalidServiceIdException(environment.getProperty("common.unique"), ServiceEntity.CODE, EntityPoint.COMMON_LIST_ITEM);

        LoggerRequest.getInstance().logInfo("CommonList ******************************** Code Can Not Update *********************************************");
        if (!isPresentCommonList.get().getCode().equals(commonListUpdateResource.getCode())) {
            throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.COMMON_LIST_ITEM);
        }

        CommonListItem commonList = isPresentCommonList.get();

        LoggerRequest.getInstance().logInfo("CommonList ******************************** Save Common List Item History *********************************************");
        commonListItemHistoryService.insertCommonListItemHistory(tenantId, commonList);

        java.sql.Timestamp currentDateTime = validationService.getCreateOrModifyDate();

        commonList.setCode(commonListUpdateResource.getCode());
        commonList.setName(commonListUpdateResource.getName());
        commonList.setReferenceCode(commonListUpdateResource.getReferenceCode());
        commonList.setStatus(CommonStatus.valueOf(commonListUpdateResource.getStatus()));
        commonList.setAttribute1(commonListUpdateResource.getAttribute1());
        commonList.setAttribute2(commonListUpdateResource.getAttribute2());
        commonList.setAttribute3(commonListUpdateResource.getAttribute3());
        commonList.setAttribute4(commonListUpdateResource.getAttribute4());
        commonList.setAttribute5(commonListUpdateResource.getAttribute5());
        commonList.setModifiedUser(createdUser);
        commonList.setModifiedDate(currentDateTime);
        commonList.setSyncTs(currentDateTime);
        commonList = commonListRepository.saveAndFlush(commonList);
        return commonList.getId();

    }
}
