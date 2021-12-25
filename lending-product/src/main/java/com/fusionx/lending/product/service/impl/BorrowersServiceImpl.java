package com.fusionx.lending.product.service.impl;


import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.AuditData;
import com.fusionx.lending.product.domain.Borrowers;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.DuplicateRecordException;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.NoRecordFoundException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.BorrowersRepository;
import com.fusionx.lending.product.resources.BorrowersAddResource;
import com.fusionx.lending.product.resources.BorrowersUpdateResource;
import com.fusionx.lending.product.resources.CustomerRequestResponseResource;
import com.fusionx.lending.product.service.BorrowersService;
import com.fusionx.lending.product.service.LendingAccountDetailService;
import com.fusionx.lending.product.service.ValidationService;
import org.modelmapper.ModelMapper;
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
public class BorrowersServiceImpl extends MessagePropertyBase implements BorrowersService {

    private BorrowersRepository borrowersRepository;
    private LendingAccountDetailService lendingAccountDetailService;
    private ValidationService validationService;


    @Autowired
    public void setLendingAccountDetailService(LendingAccountDetailService lendingAccountDetailService) {
        this.lendingAccountDetailService = lendingAccountDetailService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setBorrowersRepository(BorrowersRepository borrowersRepository) {
        this.borrowersRepository = borrowersRepository;
    }

    /**
     * Returns the borrowers by lending account id
     *
     * @param lendingAccountId the id of the lending account
     * @return the Object of  borrowers.
     */
    @Override
    public List<Borrowers> findByLendingAccountId(Long lendingAccountId) {
        LendingAccountDetail lendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(lendingAccountId).get();
        return borrowersRepository.findAllByLendingAccountId(lendingAccountDetail);
    }

    /**
     * Returns the borrowers by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of borrowers
     */
    @Override
    public List<Borrowers> findByStatus(String status) {
        return borrowersRepository.findByStatus(CommonStatus.valueOf(status));
    }

    /**
     * Gets the borrowers by id
     *
     * @param id id the id of the record
     * @return the borrowers
     */
    @Override
    public Optional<Borrowers> findById(Long id) {
        return borrowersRepository.findById(id);
    }

    /**
     * Create Borrowers into DB
     *
     * @param tenantId             the tenant id
     * @param borrowersAddResource the object to save
     * @return the object created
     */
    @Override
    public Borrowers addBorrowers(String tenantId, BorrowersAddResource borrowersAddResource) {
        Borrowers borrowers = new Borrowers();
        LendingAccountDetail lendingAccountDetail;
        lendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(borrowersAddResource.getLendingAccountId())).get();


        Object customerRequestResponseResource =  validationService.getCustomerById(tenantId,borrowersAddResource.getCustomerId());
       if (customerRequestResponseResource == null)
           throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "Customer ID");

       CustomerRequestResponseResource isPresentCustomerWithLeadId = validationService.getCustomerByLeadId(tenantId,borrowersAddResource.getCustomerId(), String.valueOf(lendingAccountDetail.getApprovedLeadId()));
        if (isPresentCustomerWithLeadId == null)
            throw new  ValidateRecordException(environment.getProperty("common-invalid.id"), "Not found customer with lead id");

        Boolean isPresentBorrowers = borrowersRepository.existsByLendingAccountIdAndCustomerId(lendingAccountDetail, Long.valueOf(borrowersAddResource.getCustomerId()));
        if (isPresentBorrowers)
            throw new DuplicateRecordException(environment.getProperty(COMMON_DUPLICATE), "lending Account Detail Id and Customer Id");

        borrowers.setOwnershipType(OwnershipTypeEnum.valueOf(borrowersAddResource.getOwnershipType()));
        borrowers.setCustomerId(Long.valueOf(borrowersAddResource.getCustomerId()));
        borrowers.setStatus(CommonStatus.valueOf(borrowersAddResource.getStatus()));
        borrowers.setLendingAccountId(lendingAccountDetail);
        borrowers.setSyncTs(validationService.getCreateOrModifyDate());
        borrowers.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        borrowers.setCreatedDate(validationService.getCreateOrModifyDate());
        borrowers.setTenantId(tenantId);
        return borrowersRepository.save(borrowers);
    }

    /**
     * updates the borrowers object by given object
     *
     * @param tenantId                the tenant id
     * @param id                      the id need to be update
     * @param borrowersUpdateResource the object which contains data
     * @return the updated borrowers.
     */
    @Override
    public Borrowers updateBorrowers(String tenantId, Long id, BorrowersUpdateResource borrowersUpdateResource) {

        Optional<Borrowers> isPresentBorrowers = borrowersRepository.findById(id);

        if (!isPresentBorrowers.get().getVersion().equals(Long.parseLong(borrowersUpdateResource.getVersion())))
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");

        Borrowers borrowers = isPresentBorrowers.get();
        borrowers.setTenantId(tenantId);
        borrowers.setOwnershipType(OwnershipTypeEnum.valueOf(borrowersUpdateResource.getOwnershipType()));
        borrowers.setStatus(CommonStatus.valueOf(borrowersUpdateResource.getStatus()));
        borrowers.setModifiedDate(validationService.getCreateOrModifyDate());
        borrowers.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        borrowers.setSyncTs(validationService.getCreateOrModifyDate());
        return borrowersRepository.save(borrowers);
    }

    @Override
    public List<Borrowers> getAll() {
        return borrowersRepository.findAll();
    }


}
