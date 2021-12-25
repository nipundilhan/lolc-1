package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.WorkflowStatus;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * API Service related to Lending Workflow.
 *
 * @author Miyuru Wijesinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                      Description         Verified Date   Verified By
 * <br/>
 * .............................................................................................................................................<br/>
 * 1        01-07-2021    	-               FX-6889             Miyuru Wijesinghe           Created             16-Sep-2021     ChinthakaMa
 * <p>
 */
@Repository
public interface LendingWorkflowRepository extends JpaRepository<LendingWorkflow, Long> {

	Optional<LendingWorkflow> findByIdAndWorkflowStatus(Long id, WorkflowStatus active);

}