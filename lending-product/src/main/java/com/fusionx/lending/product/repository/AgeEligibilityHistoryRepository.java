package com.fusionx.lending.product.repository;


import com.fusionx.lending.product.domain.AgeEligibilityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * API Service related to Age eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        17-07-2021      -               -           Menuka Jayasinghe      Created
 * <p>
 */
@Repository
public interface AgeEligibilityHistoryRepository extends JpaRepository<AgeEligibilityHistory, Long> {

}
