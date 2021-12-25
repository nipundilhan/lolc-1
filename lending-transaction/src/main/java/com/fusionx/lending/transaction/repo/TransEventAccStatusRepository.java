package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.TransEventAccStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransEventAccStatusRepository extends JpaRepository<TransEventAccStatus, Long> {

    List<TransEventAccStatus> findByStatus(String status);

    Optional<TransEventAccStatus> findByAccStatusAndTransactionEventId(String status, Long id);

    Optional<TransEventAccStatus> findByAccStatusAndTransactionEventIdAndIdNotIn(String status, Long eventid, Long id);

	List<TransEventAccStatus> findByTransEventCode(String code);
	
	Optional<TransEventAccStatus> findByAccStatusAndTransEventCodeAndStatus(String accStatus, String transEventCode, String status);


}
