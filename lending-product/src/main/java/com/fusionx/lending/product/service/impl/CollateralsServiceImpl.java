package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.AuditData;
import com.fusionx.lending.product.domain.Collaterals;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.DuplicateRecordException;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.NoRecordFoundException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CollateralsRepository;
import com.fusionx.lending.product.resources.CollateralsAddResource;
import com.fusionx.lending.product.resources.CollateralsUpdateResource;
import com.fusionx.lending.product.service.CollateralsService;
import com.fusionx.lending.product.service.LendingAccountDetailService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Borrowers.
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
 * 1        26-10-2021          -               -           Thushan                  Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CollateralsServiceImpl extends MessagePropertyBase implements CollateralsService {

    private CollateralsRepository collateralsRepository;
    private LendingAccountDetailService lendingAccountDetailService;
    private ValidationService validationService;


    @Autowired
    public void setCollateralsRepository(CollateralsRepository collateralsRepository) {
        this.collateralsRepository = collateralsRepository;
    }

    @Autowired
    public void setLendingAccountDetailService(LendingAccountDetailService lendingAccountDetailService) {
        this.lendingAccountDetailService = lendingAccountDetailService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }


    /**
     * Returns the collaterals by lending account id
     *
     * @param lendingAccountId the id of the lending account
     * @return the Object of  collaterals.
     */
    @Override
    public Collaterals findByLendingAccountId(Long lendingAccountId) {
        LendingAccountDetail lendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(lendingAccountId).get();
        return collateralsRepository.findByLendingAccountDetail(lendingAccountDetail);
    }

    /**
     * Returns the collaterals by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of collaterals
     */
    @Override
    public List<Collaterals> findByStatus(String status) {
        return collateralsRepository.findByStatus(CommonStatus.valueOf(status));
    }

    /**
     * Gets the collaterals by id
     *
     * @param id the id of the record
     * @return the collaterals
     */
    @Override
    public Optional<Collaterals> findById(Long id) {
        return collateralsRepository.findById(id);
    }

    /**
     * Create collaterals into DB
     *
     * @param tenantId               the tenant id
     * @param collateralsAddResource the object to save
     * @return the saved collaterals.
     */
    @Override
    public Collaterals addCollaterals(String tenantId, CollateralsAddResource collateralsAddResource) {
        Collaterals collaterals = new Collaterals();
        LendingAccountDetail lendingAccountDetail;

        lendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(collateralsAddResource.getLendingAccountId())).get();
        Object collateralsRequestResponseResource =  validationService.getCollateralById(tenantId,"156");
        if (collateralsRequestResponseResource == null)
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "Collaterals ID");

        Boolean isPresentCollaterals = collateralsRepository.existsByLendingAccountDetail(lendingAccountDetail);
        if (isPresentCollaterals)
            throw new DuplicateRecordException(environment.getProperty(COMMON_DUPLICATE), "lending Account Detail Id");

        collaterals.setSequence(Long.valueOf(collateralsAddResource.getSequence()));
        collaterals.setStatus(CommonStatus.valueOf(collateralsAddResource.getStatus()));
        collaterals.setLendingAccountDetail(lendingAccountDetail);
        collaterals.setCollateralId(Long.valueOf(collateralsAddResource.getCollateralId()));
        collaterals.setSyncTs(validationService.getCreateOrModifyDate());
        collaterals.setTenantId(tenantId);
        collaterals.setLendingAccountDetail(lendingAccountDetail);
        collaterals.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        collaterals.setCreatedDate(validationService.getCreateOrModifyDate());
        return collateralsRepository.save(collaterals);
    }

    /**
     * updates the collaterals object by given object
     *
     * @param tenantId the tenant id
     * @param id  the id need to be update
     * @param collateralsUpdateResource the object which contains data
     * @return the updated collaterals.
     */
    @Override
    public Collaterals updateCollaterals(String tenantId, Long id, CollateralsUpdateResource collateralsUpdateResource) {

        Optional<Collaterals> isPresentCollaterals = collateralsRepository.findById(id);

        if (!isPresentCollaterals.get().getVersion().equals(Long.parseLong(collateralsUpdateResource.getVersion())))
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");

        Collaterals collaterals = isPresentCollaterals.get();
        collaterals.setTenantId(tenantId);
        collaterals.setSequence(Long.valueOf(collateralsUpdateResource.getSequence()));
        collaterals.setStatus(CommonStatus.valueOf(collateralsUpdateResource.getStatus()));
        collaterals.setModifiedDate(validationService.getCreateOrModifyDate());
        collaterals.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        collaterals.setSyncTs(validationService.getCreateOrModifyDate());
        return collateralsRepository.save(collaterals);
    }

    @Override
    public List<Collaterals> getAll() {
        return collateralsRepository.findAll();
    }
}
