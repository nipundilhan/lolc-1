package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import com.fusionx.lending.product.resources.ContractNumberResponseResource;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.MasterDefinitionAddResource;
import com.fusionx.lending.product.resources.MasterDefinitionUpdateResource;


/**
 * API Service related to Lending Module Definition - Master Definition
 *
 * @author Dilki Kalansooriya (Inova)
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Version History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * .....................................................................................................................................<br/>
 * 1        12-July-2021	FXL-1			FXL-5				DilkiK (Inova)			Created			ChinthakaMa     16-Sep-2021
 * <p>
 */
@Service
public interface MasterDefinitionService {

    /**
     * Returns all master definition record
     *
     * @return the list of master definition
     */
    List<MasterDefinition> getAll();

    /**
     * Finds the master definition record by id
     *
     * @param id the id of the record
     * @return the master definition record
     */
    Optional<MasterDefinition> getById(Long id);

    /**
     * Find MasterDefinition by code
     *
     * @return JSON array of MasterDefinition
     */
    Optional<MasterDefinition> getByCode(String code);

    /**
     * Find MasterDefinition by status
     *
     * @return JSON array of MasterDefinition
     */
    List<MasterDefinition> getByStatus(String status);

    /**
     * Insert MasterDefinition
     *
     * @param tenantId                    the tenant id
     * @param masterDefinitionAddResource the resource file
     * @return Successfully saved
     */
    MasterDefinition addMasterDefinition(String tenantId,
                                         MasterDefinitionAddResource masterDefinitionAddResource);

    /**
     * Update MasterDefinition
     *
     * @param masterDefinitionUpdateResource the resource file
     * @return Successfully saved
     */
    MasterDefinition updateMasterDefinition(String tenantId, Long id,
                                            MasterDefinitionUpdateResource masterDefinitionUpdateResource);

    /**
     * Approve or Reject master definition record
     *
     * @param tenantId       the tenant id
     * @param workflowStatus the workflow status
     * @return Successfully saved
     * @author Dilki
     */
    boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);

    /**
     * Find Pending MasterDefinition by ID
     *
     * @return JSON array of MasterDefinition Pending
     * @author Dilki
     */
    Optional<MasterDefinitionPending> getByPendingId(Long pendingId);

    /**
     * retrieve contract number using account id
     *
     * @param tenantId  the tenant id
     * @param code the master definition code
     * @return ContractNumberResponseResource
     */
    ContractNumberResponseResource getNextContractNumberByAccountId(String tenantId, String code);


}
