package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.domain.AllocationOrder;
import com.fusionx.lending.transaction.resource.AllocationOrderResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AllocationOrderService {
    List<AllocationOrder> findAll();

    Optional<AllocationOrder> findById(Long id);

    Optional<AllocationOrder> findByReferenceCode(String code);

    //List<AllocationOrder> findByAccountStatus(String accountStatus);

    List<AllocationOrder> findByStatus(String status);

    List<AllocationOrder> findByBankTransactionSubCode(String subCode);

    AllocationOrder addAllocationOrder(String tenantId, AllocationOrderResource allocationOrderResource);

    AllocationOrder updateAllocationOrder(String tenantId, AllocationOrderResource allocationOrderResource, Long id);

    List<AllocationOrder> findByBankTransactionCode(String code);
    
    List<AllocationOrder> findByAllocationTemplateId(Long allocationTemplateId);
}
