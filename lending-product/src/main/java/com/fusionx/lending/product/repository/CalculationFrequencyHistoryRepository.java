package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.CalculationFrequencyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * API Service related to Calculation Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  	Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6511       		Senitha Perera         		Created
 * <p>
 */
@Repository
public interface CalculationFrequencyHistoryRepository extends JpaRepository<CalculationFrequencyHistory, Long> {

}
