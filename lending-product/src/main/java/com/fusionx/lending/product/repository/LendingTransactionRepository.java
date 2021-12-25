package com.fusionx.lending.product.repository;


import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.domain.LendingTransaction;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendingTransactionRepository extends JpaRepository<LendingTransaction, Long> {

    List<LendingTransaction> findAllByLendingAccountId(LendingAccountDetail lendingAccountDetail);

    /*  @Query("SELECT up FROM LendingTransaction up WHERE "
            + "("
                + ":searchQ IS NOT NULL AND "
                    + "("
                        + "(upper(up.status) LIKE '%' || upper(:searchQ) || '%') "
                        + " OR (upper(up.approveStatus) LIKE '%' || upper(:searchQ) || '%') "
                    + ") "
                    + " AND "
                    + "("
                        + "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
                        + " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.approveStatus) = upper(:approveStatus)))"
                    + ")"
                    + " OR "
                    + "("
                        + ":searchQ IS NULL AND "
                        + "("
                        + "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
                        + " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.approveStatus) = upper(:approveStatus)))"
                    + ")"
                + ") "
            + ") "
    )
    Page<LendingTransaction> searchLendingTransactions(@Param("searchQ") String searchQ,
                                                       @Param("transactionSubCode") String transactionSubCode,
                                                       @Param("transactionDate") String transactionDate,
                                                       @Param("valueDate") String valueDate,
                                                       @Param("postingType") String postingType,
                                                       @Param("referenceId") String referenceId,
                                                       Pageable pageable);*/

}
