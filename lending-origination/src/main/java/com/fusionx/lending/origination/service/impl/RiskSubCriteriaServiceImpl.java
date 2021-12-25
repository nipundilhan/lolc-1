package com.fusionx.lending.origination.service.impl;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.SearchCriteria;
import com.fusionx.lending.origination.domain.RiskSubCriteria;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.SearchOperation;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.RiskSubCriteriaRepository;
import com.fusionx.lending.origination.resource.RiskSubCriteriaAddResource;
import com.fusionx.lending.origination.resource.RiskSubCriteriaUpdateResource;
import com.fusionx.lending.origination.sepecification.RiskSubCriteriaSpecification;
import com.fusionx.lending.origination.service.RiskSubCriteriaService;
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
public class RiskSubCriteriaServiceImpl implements RiskSubCriteriaService {

    private final RiskSubCriteriaRepository riskSubCriteriaRepository;

    private final Environment environment;

    @Autowired
    public RiskSubCriteriaServiceImpl(RiskSubCriteriaRepository riskSubCriteriaRepository, Environment environment) {
        this.riskSubCriteriaRepository = riskSubCriteriaRepository;
        this.environment = environment;
    }

    @Override
    public List<RiskSubCriteria> findAll() {
        return riskSubCriteriaRepository.findAll();
    }

    @Override
    public Optional<RiskSubCriteria> findById(Long id) {
        Optional<RiskSubCriteria> optionalRiskSubCriteria = riskSubCriteriaRepository.findById(id);
        if (optionalRiskSubCriteria.isPresent()) {
            return optionalRiskSubCriteria;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RiskSubCriteria> findByCode(String code) {
        Optional<RiskSubCriteria> optionalRiskSubCriteria = riskSubCriteriaRepository.findByCode(code);
        if (optionalRiskSubCriteria.isPresent()) {
            return optionalRiskSubCriteria;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<RiskSubCriteria> findByName(String name) {
        return riskSubCriteriaRepository.findByNameContaining(name);
    }

    @Override
    public List<RiskSubCriteria> findByStatus(String status) {
        return riskSubCriteriaRepository.findByStatus(status);
    }

    @Override
    public Long validateAndSaveRiskSubCriteria(String tenantId, String createdUser, RiskSubCriteriaAddResource riskSubCriteriaAddResource) {

        LoggerRequest.getInstance().logInfo("RiskSubCriteria validate Code and status");
        if (riskSubCriteriaRepository.existsByCodeAndStatus(riskSubCriteriaAddResource.getCode(), CommonStatus.ACTIVE.toString())) {
            throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
        }

        LoggerRequest.getInstance().logInfo("RiskSubCriteria save Risk Sub Criteria");
        RiskSubCriteria riskSubCriteria = saveRiskSubCriteria(tenantId, createdUser, riskSubCriteriaAddResource);

        return riskSubCriteria != null ? riskSubCriteria.getId() : null;
    }

    @Override
    public Boolean existsById(Long id) {
        return riskSubCriteriaRepository.existsById(id);
    }

    @Override
    public Long validateAndUpdateRiskSubCriteria(String tenantId, String createdUser, Long id, RiskSubCriteriaUpdateResource riskSubCriteriaUpdateResource) {

        LoggerRequest.getInstance().logInfo("RiskSubCriteria validate by id");
        Optional<RiskSubCriteria> optionalRiskSubCriteria = riskSubCriteriaRepository.findById(id);
        if (optionalRiskSubCriteria.isPresent()) {
            if (!optionalRiskSubCriteria.get().getVersion().equals(riskSubCriteriaUpdateResource.getVersion())) {
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
            }
        } else {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
        }

        LoggerRequest.getInstance().logInfo("RiskSubCriteria update Risk Sub Criteria");
        RiskSubCriteria riskSubCriteria = updateRiskSubCriteria(tenantId, createdUser, riskSubCriteriaUpdateResource, id);

        return riskSubCriteria != null ? riskSubCriteria.getId() : null;
    }

    @Override
    public Page<RiskSubCriteria> searchRiskSubCriteria(String code, String name, String status, Pageable pageable) {

        RiskSubCriteriaSpecification riskSubCriteriaSpecification = new RiskSubCriteriaSpecification();

        if (code != null && !code.isEmpty()) {
            riskSubCriteriaSpecification.add(new SearchCriteria("code", code, SearchOperation.LIKE));
        }
        if (name != null && !name.isEmpty()) {
            riskSubCriteriaSpecification.add(new SearchCriteria("name", name, SearchOperation.LIKE));
        }
        if (status != null && !status.isEmpty()) {
            riskSubCriteriaSpecification.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
        }

        return riskSubCriteriaRepository.findAll(riskSubCriteriaSpecification, pageable);
    }

    private RiskSubCriteria updateRiskSubCriteria(String tenantId, String createdUser, RiskSubCriteriaUpdateResource riskSubCriteriaUpdateResource, Long id) {

        RiskSubCriteria riskSubCriteria = riskSubCriteriaRepository.getOne(id);
        riskSubCriteria.setTenantId(tenantId);
        riskSubCriteria.setCode(riskSubCriteriaUpdateResource.getCode());
        riskSubCriteria.setName(riskSubCriteriaUpdateResource.getName());
        riskSubCriteria.setDescription(riskSubCriteriaUpdateResource.getDescription());
        riskSubCriteria.setStatus(riskSubCriteriaUpdateResource.getStatus());
        riskSubCriteria.setModifiedUser(createdUser);
        riskSubCriteria.setModifiedDate(getCreateOrModifyDate());
        riskSubCriteria.setSyncTs(getCreateOrModifyDate());

        return riskSubCriteriaRepository.saveAndFlush(riskSubCriteria);
    }

    private RiskSubCriteria saveRiskSubCriteria(String tenantId, String createdUser, RiskSubCriteriaAddResource riskSubCriteriaAddResource) {

        RiskSubCriteria riskSubCriteria = new RiskSubCriteria();
        riskSubCriteria.setTenantId(tenantId);
        riskSubCriteria.setCode(riskSubCriteriaAddResource.getCode());
        riskSubCriteria.setName(riskSubCriteriaAddResource.getName());
        riskSubCriteria.setDescription(riskSubCriteriaAddResource.getDescription());
        riskSubCriteria.setStatus(riskSubCriteriaAddResource.getStatus());
        riskSubCriteria.setCreatedUser(createdUser);
        riskSubCriteria.setCreatedDate(getCreateOrModifyDate());
        riskSubCriteria.setSyncTs(getCreateOrModifyDate());

        return riskSubCriteriaRepository.saveAndFlush(riskSubCriteria);
    }

    private Timestamp getCreateOrModifyDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }
}
