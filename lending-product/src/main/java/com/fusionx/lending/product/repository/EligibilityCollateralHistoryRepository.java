package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.EligibilityCollateralHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * API Service related to Eligibility Collateral.
 *
 * @author Miyuru Wijesinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        01-07-2021    	-               FX-6889             Miyuru Wijesinghe          Created
 * <p>
 */

@Repository
public interface EligibilityCollateralHistoryRepository extends JpaRepository<EligibilityCollateralHistory, Long> {

}
