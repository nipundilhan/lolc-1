package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CheckList;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.DuplicateRecordException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CheckListRepository;
import com.fusionx.lending.product.resources.CheckListAddResource;
import com.fusionx.lending.product.resources.CheckListResponseResource;
import com.fusionx.lending.product.resources.CheckListUpdateResource;
import com.fusionx.lending.product.service.CheckListService;
import com.fusionx.lending.product.service.LendingAccountDetailService;
import com.fusionx.lending.product.service.RemoteService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to  check list
 *
 * @author Rohan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author       Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        26-10-2021      -               -           Rohan      Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CheckListServiceImpl extends MessagePropertyBase implements CheckListService {

    private CheckListRepository checkListRepository;
    private LendingAccountDetailService lendingAccountDetailService;
    private ValidationService validationService;
    private RemoteService remoteService;
    private CommonModuleProperties commonModuleProperties;

    @Autowired
    public void setCheckListRepository(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
    }

    @Autowired
    public void setLendingAccountDetailService(LendingAccountDetailService lendingAccountDetailService) {
        this.lendingAccountDetailService = lendingAccountDetailService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setRemoteService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Autowired
    public void setCommonModuleProperties(CommonModuleProperties commonModuleProperties) {
        this.commonModuleProperties = commonModuleProperties;
    }

    @Override
    public CheckList addCheckList(String tenantId, CheckListAddResource checkListAddResource) {
        Optional<CheckList> isPresentChekList = checkListRepository.findByLendingAccountDetail(
                lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(checkListAddResource.getLendingAccountDetailId())).get());

        if (isPresentChekList.isPresent())
            throw new DuplicateRecordException(environment.getProperty(COMMON_DUPLICATE), "lendingAccountDetailId");

        if (!remoteService.checkIsExist(tenantId, checkListAddResource.getDocumentCheckListDetailId(), commonModuleProperties.getLendingProduct().concat("/document-checklist-details/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "document checklist detail id");

        CheckList checkList = new CheckList();
        checkList.setTenantId(tenantId);
        checkList.setCheckMark(YesNo.valueOf(checkListAddResource.getCheckMark()));
        checkList.setRemarks(checkListAddResource.getRemarks());
        checkList.setStatus(CommonStatus.valueOf(checkListAddResource.getStatus()));
        checkList.setCreatedDate(validationService.getCreateOrModifyDate());
        checkList.setSyncTs(validationService.getCreateOrModifyDate());
        checkList.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        checkList.setLendingAccountDetail(lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(checkListAddResource.getLendingAccountDetailId())).get());
        checkList.setDocumentCheckListDetailId(Long.valueOf(checkListAddResource.getDocumentCheckListDetailId()));

        return checkListRepository.save(checkList);
    }

    @Override
    public Optional<CheckList> getById(Long id) {
        return checkListRepository.findById(id);
    }

    @Override
    public CheckList updateCheckList(String tenantId, Long id, CheckListUpdateResource checkListUpdateResource) {
        Optional<CheckList> isPresentCheckList = checkListRepository.findById(id);

        if (isPresentCheckList.isPresent()) {

            if (isPresentCheckList.get().getId() != checkListRepository.findByLendingAccountDetail(
                    lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(checkListUpdateResource.getLendingAccountDetailId())).get()).get().getId())
                throw new DuplicateRecordException(environment.getProperty(COMMON_DUPLICATE), "lendingAccountDetailId");

            if (!isPresentCheckList.get().getVersion().equals(Long.parseLong(checkListUpdateResource.getVersion())))
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");

            if (!remoteService.checkIsExist(tenantId, checkListUpdateResource.getDocumentCheckListDetailId(), commonModuleProperties.getLendingProduct().concat("/document-checklist-details/")))
                throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "document checklist detail id");

            CheckList checkList = isPresentCheckList.get();
            checkList.setTenantId(tenantId);
            checkList.setCheckMark(YesNo.valueOf(checkListUpdateResource.getCheckMark()));
            checkList.setRemarks(checkListUpdateResource.getRemarks());
            checkList.setStatus(CommonStatus.valueOf(checkListUpdateResource.getStatus()));
            checkList.setModifiedDate(validationService.getCreateOrModifyDate());
            checkList.setSyncTs(validationService.getCreateOrModifyDate());
            checkList.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
            checkList.setLendingAccountDetail(lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(checkListUpdateResource.getLendingAccountDetailId())).get());
            checkList.setDocumentCheckListDetailId(Long.valueOf(checkListUpdateResource.getDocumentCheckListDetailId()));

            return checkListRepository.saveAndFlush(checkList);

        } else
            throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");

    }

    @Override
    public List<CheckList> getByStatus(String status) {
        return checkListRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public Optional<CheckList> getByLendingAccount(Long lendingAccountId) {
        return checkListRepository.findByLendingAccountDetail(lendingAccountDetailService.getByLendingAccountDetailById(lendingAccountId).get());
    }

    @Override
    public List<CheckList> getAll() {
        return checkListRepository.findAll();
    }

    @Override
    public CheckListResponseResource getCheckListResponseResource(CheckList checkList) {
        return new CheckListResponseResource(
                checkList.getId().toString(),
                checkList.getCheckMark().toString(),
                checkList.getRemarks(),
                checkList.getTenantId(),
                checkList.getStatus().toString(),
                checkList.getLendingAccountDetail().getId().toString(),
                checkList.getDocumentCheckListDetailId().toString());
    }
}
