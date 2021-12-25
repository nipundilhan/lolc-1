package com.fusionx.lending.transaction.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.AllocationOrder;

@Repository
public interface AllocationOrderRepository extends JpaRepository<AllocationOrder, Long> {
    Optional<AllocationOrder> findById(Long id);

    Optional<AllocationOrder> findByReferenceCode(String code);

    //List<AllocationOrder> findByAccountStatus(AccountStatusEnum accountStatus);

    List<AllocationOrder> findByStatus(String status);

    List<AllocationOrder> findByBankTransactionSubCode(String subCode);

    Boolean existsByReferenceCode(String referenceCode);

    

    
    Boolean existsByReferenceCodeAndIdNot(String referenceCode, Long id);

    //Boolean existsByAccountStatusAndBankTransactionIdAndBankTransactionSubIdAndAllocationOrder(AccountStatusEnum accountStatus, Long bankTransactionId, Long subTransactionId, Long allocationOrder);

    //Boolean existsByAccountStatusAndBankTransactionIdAndBankTransactionSubIdAndAllocationOrderAndIdNot(AccountStatusEnum accountStatus, Long transactionId, Long subTransactionId, Long allocationOrder, Long id);

    //public List<AllocationOrder> findByAccountStatusOrderByAllocationOrderAsc(AccountStatusEnum accountStatus);

    List<AllocationOrder> findByBankTransactionCode(String code);

    Boolean existsByAllocationTempIdAndBankTransactionSubIdAndAllocationOrder(Long allocationTempId, Long subTransactionId, Long allocationId);

    Boolean existsByAllocationTempIdAndAllocationOrder(Long allocationTempId, Long allocationId);
    
    Boolean existsByAllocationTempIdAndBankTransactionSubId(Long allocationTempId, Long subTransactionId);
    
    Boolean existsByAllocationTempIdAndBankTransactionSubIdAndIdNot(Long allocationTempId, Long subTransactionId, Long id);

    Boolean existsByAllocationTempIdAndBankTransactionSubIdAndAllocationOrderAndIdNot(Long allocationTempId, Long subTransactionId, Long allocationId, Long id);
    Boolean existsByAllocationTempIdAndAllocationOrderAndIdNot(Long allocationTempId, Long allocationId, Long id);
    //boolean existsById(Long id);
    
    List<AllocationOrder> findByAllocationTempId(Long allocationTempId);
}
