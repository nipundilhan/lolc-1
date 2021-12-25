package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.enums.CommonStatus;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * API Service related to Eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2021    	-               		             Menuka Jayasinghe		Created
 * <p>
 */
@Repository
public interface EligibilityPendingRepository extends JpaRepository<EligibilityPending, Long> {

    @Query("SELECT up FROM EligibilityPending up WHERE "
            + "("
            + ":searchq IS NOT NULL AND "
            + "("
            + "(upper(up.status) LIKE '%' || upper(:searchq) || '%') "
            + " OR (upper(up.eligibility.approveStatus) LIKE '%' || upper(:searchq) || '%') "
            + ") "
            + " AND "
            + "("
            + "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
            + " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.eligibility.approveStatus) = upper(:approveStatus)))"
            + ")"
            + " OR "
            + "("
            + ":searchq IS NULL AND "
            + "("
            + "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
            + " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.eligibility.approveStatus) = upper(:approveStatus)))"
            + ")"
            + ") "
            + ") "
    )
    Page<EligibilityPending> searchEligibilityPending(@Param("searchq") String searchq,
                                                      @Param("status") String status,
                                                      @Param("approveStatus") String approveStatus,
                                                      Pageable pageable);
    
    public Optional<EligibilityPending> findByIdAndStatus(Long id, CommonStatus status);

}
