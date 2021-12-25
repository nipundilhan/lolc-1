package com.fusionx.lending.origination.repository;

import com.fusionx.lending.origination.domain.MicroBprWorkflow;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.enums.WorkflowType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MicroBprWorkflowRepository extends JpaRepository<MicroBprWorkflow, Long> {

    public Optional<MicroBprWorkflow> findByCustomerIdAndWorkflowTypeAndWorkflowStatus(Long customerId, WorkflowType workflowType, WorkflowStatus workflowStatus);

    public Optional<MicroBprWorkflow> findByCustomerIdAndWorkflowTypeAndWorkflowStatusAndWorkflowProcessId(Long customerId, WorkflowType workflowType, WorkflowStatus workflowStatus, Long workflowProcessId);

    public Page<MicroBprWorkflow> findByCustomerId(Long customerId, Pageable pageable);

    public Optional<MicroBprWorkflow> findByGuarantorIdAndWorkflowTypeAndWorkflowStatus(Long id,
                                                                                        WorkflowType workflowType, WorkflowStatus workflowStatus);


}
