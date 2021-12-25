package com.fusionx.lending.origination.service.impl;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.SearchCriteria;
import com.fusionx.lending.origination.domain.RiskMainCriteria;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.SearchOperation;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.RiskMainCriteriaRepository;
import com.fusionx.lending.origination.resource.RiskMainCriteriaAddResource;
import com.fusionx.lending.origination.resource.RiskMainCriteriaUpdateResource;
import com.fusionx.lending.origination.sepecification.RiskMainCriteriaSpecification;
import com.fusionx.lending.origination.service.RiskMainCriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class RiskMainCriteriaServiceImpl implements RiskMainCriteriaService {

    private final RiskMainCriteriaRepository riskMainCriteriaRepository;

    private final Environment environment;

    @Autowired
    public RiskMainCriteriaServiceImpl(RiskMainCriteriaRepository riskMainCriteriaRepository, Environment environment) {
        this.riskMainCriteriaRepository = riskMainCriteriaRepository;
        this.environment = environment;
    }

    @Override
    public List<RiskMainCriteria> findAll() {
        return riskMainCriteriaRepository.findAll();
    }

    @Override
    public Optional<RiskMainCriteria> findById(Long id) {
        Optional<RiskMainCriteria> optionalRiskMainCriteria = riskMainCriteriaRepository.findById(id);
        if (optionalRiskMainCriteria.isPresent()) {
            return optionalRiskMainCriteria;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RiskMainCriteria> findByCode(String code) {
        Optional<RiskMainCriteria> businessSubType = riskMainCriteriaRepository.findByCode(code);
        if (businessSubType.isPresent()) {
            return businessSubType;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<RiskMainCriteria> findByName(String name) {
        return riskMainCriteriaRepository.findByNameContaining(name);
    }

    @Override
    public List<RiskMainCriteria> findByStatus(String status) {
        return riskMainCriteriaRepository.findByStatus(status);
    }

    @Override
    public Long validateAndSaveRiskMainCriteria(String tenantId, String createdUser, RiskMainCriteriaAddResource riskMainCriteriaAddResource) {

        LoggerRequest.getInstance().logInfo("RiskMainCriteria validate Code and status");
        if (riskMainCriteriaRepository.existsByCodeAndStatus(riskMainCriteriaAddResource.getCode(), CommonStatus.ACTIVE.toString())) {
            throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
        }

        LoggerRequest.getInstance().logInfo("RiskMainCriteria save Risk Main Criteria");
        RiskMainCriteria riskMainCriteria = saveRiskMainCriteria(tenantId, createdUser, riskMainCriteriaAddResource);

        return riskMainCriteria != null ? riskMainCriteria.getId() : null;
    }

    @Override
    public Boolean existsById(Long id) {
        return riskMainCriteriaRepository.existsById(id);
    }

    @Override
    public Long validateAndUpdateRiskMainCriteria(String tenantId, String createdUser, Long id, RiskMainCriteriaUpdateResource riskMainCriteriaUpdateResource) {

        LoggerRequest.getInstance().logInfo("RiskMainCriteria validate by id");
        Optional<RiskMainCriteria> optionalRiskMainCriteria = riskMainCriteriaRepository.findById(id);
        if (optionalRiskMainCriteria.isPresent()) {
            if (!optionalRiskMainCriteria.get().getVersion().equals(riskMainCriteriaUpdateResource.getVersion())) {
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
            }
        } else {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
        }

        LoggerRequest.getInstance().logInfo("RiskMainCriteria update Risk Main Criteria");
        RiskMainCriteria riskMainCriteria = updateRiskMainCriteria(tenantId, createdUser, riskMainCriteriaUpdateResource, id);

        return riskMainCriteria != null ? riskMainCriteria.getId() : null;
    }

    @Override
    public Page<RiskMainCriteria> searchRiskMainCriteria(String code, String name, String status, Pageable pageable) {

        RiskMainCriteriaSpecification riskMainCriteriaSpecification = new RiskMainCriteriaSpecification();

        if (code != null && !code.isEmpty()) {
            riskMainCriteriaSpecification.add(new SearchCriteria("code", code, SearchOperation.LIKE));
        }
        if (name != null && !name.isEmpty()) {
            riskMainCriteriaSpecification.add(new SearchCriteria("name", name, SearchOperation.LIKE));
        }
        if (status != null && !status.isEmpty()) {
            riskMainCriteriaSpecification.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
        }

        return riskMainCriteriaRepository.findAll(riskMainCriteriaSpecification, pageable);
    }

    private RiskMainCriteria updateRiskMainCriteria(String tenantId, String createdUser, RiskMainCriteriaUpdateResource riskMainCriteriaUpdateResource, Long id) {

        RiskMainCriteria riskMainCriteria = riskMainCriteriaRepository.getOne(id);
        riskMainCriteria.setTenantId(tenantId);
        riskMainCriteria.setCode(riskMainCriteriaUpdateResource.getCode());
        riskMainCriteria.setName(riskMainCriteriaUpdateResource.getName());
        riskMainCriteria.setDescription(riskMainCriteriaUpdateResource.getDescription());
        riskMainCriteria.setStatus(riskMainCriteriaUpdateResource.getStatus());
        riskMainCriteria.setModifiedUser(createdUser);
        riskMainCriteria.setModifiedDate(getCreateOrModifyDate());
        riskMainCriteria.setSyncTs(getCreateOrModifyDate());

        return riskMainCriteriaRepository.saveAndFlush(riskMainCriteria);
    }

    private RiskMainCriteria saveRiskMainCriteria(String tenantId, String createdUser, RiskMainCriteriaAddResource riskMainCriteriaAddResource) {

        RiskMainCriteria riskMainCriteria = new RiskMainCriteria();
        riskMainCriteria.setTenantId(tenantId);
        riskMainCriteria.setCode(riskMainCriteriaAddResource.getCode());
        riskMainCriteria.setName(riskMainCriteriaAddResource.getName());
        riskMainCriteria.setDescription(riskMainCriteriaAddResource.getDescription());
        riskMainCriteria.setStatus(riskMainCriteriaAddResource.getStatus());
        riskMainCriteria.setCreatedUser(createdUser);
        riskMainCriteria.setCreatedDate(getCreateOrModifyDate());
        riskMainCriteria.setSyncTs(getCreateOrModifyDate());

        return riskMainCriteriaRepository.saveAndFlush(riskMainCriteria);
    }

    private Timestamp getCreateOrModifyDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }
}
