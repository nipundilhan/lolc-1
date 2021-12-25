package com.fusionx.lending.transaction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.LendingWorkflow;

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
 * 1        19-10-2021    	-                                   Dilhan           Created     19-10-2021     ChinthakaMa
 * <p>
 */
@Repository
public interface LendingWorkflowRepository extends JpaRepository<LendingWorkflow, Long> {

}