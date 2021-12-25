package com.fusionx.lending.product.repository;


import com.fusionx.lending.product.domain.AgeEligibility;/**
 * Age Eligibility service
 * @author 	MenukaJ
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021  						    MenukaJ      Created
 *
 ********************************************************************************************************
 */
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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
public interface AgeEligibilityRepository extends JpaRepository<AgeEligibility, Long> {

    List<AgeEligibility> findByStatus(CommonStatus status);

    Optional<AgeEligibility> findByIdAndStatus(Long ageEligibilityId, CommonStatus status);
}
