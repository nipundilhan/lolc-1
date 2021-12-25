package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.BrandRepository;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.service.BrandHistoryService;
import com.fusionx.lending.product.service.BrandService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * API Service related to Brand.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  	Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2020      -                            		Menuka Jayasinghe          Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class BrandServiceImpl extends MessagePropertyBase implements BrandService {

    private BrandRepository brandRepository;

    private ValidationService validationService;

    private BrandHistoryService brandHistoryService;

    @Autowired
    public void setBrandRepository(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setBrandHistoryService(BrandHistoryService brandHistoryService) {
        this.brandHistoryService = brandHistoryService;
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> getById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Optional<Brand> getByCode(String code) {
        return brandRepository.findByCode(code);
    }

    @Override
    public List<Brand> getByName(String name) {
        return brandRepository.findByNameContaining(name);
    }

    @Override
    public List<Brand> getByStatus(String status) {
        return brandRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public Brand addBrand(String tenantId, CommonAddResource commonAddResource) {

        Optional<Brand> isPresentBrand = brandRepository.findByCode(commonAddResource.getCode());

        if (isPresentBrand.isPresent())
            throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.BRAND);

        Brand brand = new Brand();
        brand.setTenantId(tenantId);
        brand.setCode(commonAddResource.getCode());
        brand.setName(commonAddResource.getName());
        brand.setDescription(commonAddResource.getDescription());
        brand.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
        brand.setCreatedDate(validationService.getCreateOrModifyDate());
        brand.setSyncTs(validationService.getCreateOrModifyDate());
        brand.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

        return brandRepository.save(brand);

    }

    @Override
    public Brand updateBrand(String tenantId, Long id, CommonUpdateResource commonUpdateResource) {

        Optional<Brand> isPresentBrand = brandRepository.findById(id);

        if (isPresentBrand.isPresent()) {
            brandHistoryService.saveHistory(tenantId, isPresentBrand.get(), LogginAuthentcation.getInstance().getUserName());

            if (!isPresentBrand.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
                throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.BRAND);

            if (!isPresentBrand.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
                throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE, EntityPoint.BRAND);

            Brand brand = isPresentBrand.get();
            brand.setTenantId(tenantId);
            brand.setName(commonUpdateResource.getName());
            brand.setDescription(commonUpdateResource.getDescription());
            brand.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
            brand.setModifiedDate(validationService.getCreateOrModifyDate());
            brand.setSyncTs(validationService.getCreateOrModifyDate());
            brand.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

            return brandRepository.saveAndFlush(brand);
        } else
            throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
    }

}
