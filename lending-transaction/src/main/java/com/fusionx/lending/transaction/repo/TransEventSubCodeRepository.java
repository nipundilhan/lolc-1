package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.TransEventSubCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransEventSubCodeRepository extends JpaRepository<TransEventSubCode, Long> {

    Optional<TransEventSubCode> findBybankTransactionSubCodeIdAndTransactionEventId(Long subId, Long eventId);

    Optional<TransEventSubCode> findByBankTransactionSubCodeIdAndTransactionEventIdAndBankTransactionCodeIdAndIdNotIn(Long subId, Long eventId, Long transCodeId, Long id);

    List<TransEventSubCode> findByStatus(String status);

    //List<TransEventSubCode> findByTransactionEventId(Long eventId);
    Optional<TransEventSubCode> findByTransactionEventId(Long eventId);

    Optional<TransEventSubCode> findByTransactionEventIdAndIdNotIn(Long eventId, Long id);

    Optional<TransEventSubCode> findByTransactionEventIdAndStatus(Long eventId, String status);

    /**
     * @author Senitha
     * @date 26-NOV-2020
     */
    Optional<TransEventSubCode> findByTransactionEventCode(String transEventCode);

}
