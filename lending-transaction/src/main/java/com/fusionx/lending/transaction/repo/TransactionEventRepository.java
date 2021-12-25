package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.TransactionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionEventRepository extends JpaRepository<TransactionEvent, Long> {

    List<TransactionEvent> findByStatus(String status);

    Optional<TransactionEvent> findByCode(String eventCode);
    
    List<TransactionEvent> findByDescriptionContains(String descriptions);
    
    Optional<TransactionEvent> findByIdAndCodeAndDescriptionContaining(Long id, String code, String description);

}
