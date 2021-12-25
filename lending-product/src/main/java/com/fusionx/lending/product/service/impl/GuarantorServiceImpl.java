package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.AuditData;
import com.fusionx.lending.product.domain.Guarantor;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.DuplicateRecordException;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.NoRecordFoundException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.GuarantorRepository;
import com.fusionx.lending.product.repository.LendingAccountDetailRepository;
import com.fusionx.lending.product.resources.CustomerRequestResponseResource;
import com.fusionx.lending.product.resources.GuarantorAddResource;
import com.fusionx.lending.product.resources.GuarantorRequestResponseResource;
import com.fusionx.lending.product.resources.GuarantorUpdateResource;
import com.fusionx.lending.product.service.GuarantorService;
import com.fusionx.lending.product.service.LendingAccountDetailService;
import com.fusionx.lending.product.service.RemoteService;
import com.fusionx.lending.product.service.ValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Guarantor.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        0-10-2021      -               -           Thushan                     Created
 * <p>
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class GuarantorServiceImpl extends MessagePropertyBase implements GuarantorService {

    private GuarantorRepository guarantorRepository;
    private LendingAccountDetailService lendingAccountDetailService;
    private ValidationService validationService;


    @Autowired
    public void setGuarantorRepository(GuarantorRepository guarantorRepository) {
        this.guarantorRepository = guarantorRepository;
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
     * Returns the guarantor by lending account id
     *
     * @param lendingAccountId the id of the lending account
     * @return the Object of  guarantor.
     */
    @Override
    public Guarantor findByLendingAccountId(Long lendingAccountId) {
        LendingAccountDetail lendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(lendingAccountId).get();
        return guarantorRepository.findByLendingAccountDetail(lendingAccountDetail);
    }

    /**
     * Returns the guarantor by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of guarantor
     */
    @Override
    public List<Guarantor> findByStatus(String status) {
        return guarantorRepository.findByStatus(CommonStatus.valueOf(status));
    }

    /**
     *  Gets the guarantor by id
     *
     * @param id the id of the record
     * @return the set of guarantors
     */
    @Override
    public Optional<Guarantor> findById(Long id) {
        return guarantorRepository.findById(id);
    }

    /**
     *  Create guarantor into DB
     *
     * @param tenantId the tenant id
     * @param guarantorAddResource the object to save
     * @return the saved guarantor.
     */
    @Override
    public Guarantor addGuarantor(String tenantId, GuarantorAddResource guarantorAddResource) {
        Guarantor guarantor = new Guarantor();
        LendingAccountDetail lendingAccountDetail;

        lendingAccountDetail = lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(guarantorAddResource.getLendingAccountDetail())).get();

        Object customerRequestResponseResource =  validationService.getCustomerById(tenantId,guarantorAddResource.getCustomerId());
        if (customerRequestResponseResource == null)
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "Customer ID");

        Boolean isPresentGuarantor = guarantorRepository.existsByLendingAccountDetail(lendingAccountDetail);
        if (isPresentGuarantor)
            throw new DuplicateRecordException(environment.getProperty(COMMON_DUPLICATE), "lending Account Detail Id");

        guarantor.setSequence(Long.valueOf(guarantorAddResource.getSequence()));
        guarantor.setGuaranteePercentage(validationService.stringToBigDecimal(guarantorAddResource.getGuaranteePercentage()));
        guarantor.setValue(validationService.stringToBigDecimal(guarantorAddResource.getValue()));
        guarantor.setStatus(CommonStatus.valueOf(guarantorAddResource.getStatus()));
        guarantor.setBondNumber(guarantorAddResource.getBondNumber());
        guarantor.setLendingAccountDetail(lendingAccountDetail);
        guarantor.setSyncTs(validationService.getCreateOrModifyDate());
        guarantor.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        guarantor.setLendingAccountDetail(lendingAccountDetail);
        guarantor.setCreatedDate(validationService.getCreateOrModifyDate());
        guarantor.setCustomerId(Long.valueOf(guarantorAddResource.getCustomerId()));
        guarantor.setTenantId(tenantId);
        return guarantorRepository.save(guarantor);
    }

    /**
     * Updates the guarantor object by given object
     *
     * @param tenantId the tenant id
     * @param id the id need to be update
     * @param guarantorUpdateResource the object which contains data
     * @return the updated guarantor.
     */
    @Override
    public Guarantor updateGuarantor(String tenantId, Long id, GuarantorUpdateResource guarantorUpdateResource) {
        Optional<Guarantor> isPresentGuarantor = guarantorRepository.findById(id);

        if (!isPresentGuarantor.get().getVersion().equals(Long.parseLong(guarantorUpdateResource.getVersion())))
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");

        Guarantor guarantor = isPresentGuarantor.get();
        guarantor.setSequence(Long.valueOf(guarantorUpdateResource.getSequence()));
        guarantor.setGuaranteePercentage(validationService.stringToBigDecimal(guarantorUpdateResource.getGuaranteePercentage()));
        guarantor.setValue(validationService.stringToBigDecimal(guarantorUpdateResource.getValue()));
        guarantor.setBondNumber(guarantorUpdateResource.getBondNumber());
        guarantor.setStatus(CommonStatus.valueOf(guarantorUpdateResource.getStatus()));
        guarantor.setTenantId(tenantId);
        guarantor.setModifiedDate(validationService.getCreateOrModifyDate());
        guarantor.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        guarantor.setSyncTs(validationService.getCreateOrModifyDate());
        return guarantorRepository.save(guarantor);
    }

    @Override
    public List<Guarantor> getAll() {
        return guarantorRepository.findAll();
    }
}
