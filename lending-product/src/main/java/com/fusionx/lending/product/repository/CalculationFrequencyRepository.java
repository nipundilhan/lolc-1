package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.CalculationFrequency;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
public interface CalculationFrequencyRepository extends JpaRepository<CalculationFrequency, Long> {

    Optional<CalculationFrequency> findByCode(String code);

    List<CalculationFrequency> findByName(String name);

    List<CalculationFrequency> findByStatus(CommonStatus status);

    Optional<CalculationFrequency> findByIdAndName(Long id, String name);

    Optional<CalculationFrequency> findByCodeAndIdNotIn(String code, Long id);
    
    Optional<CalculationFrequency> findByIdAndStatus(Long id,CommonStatus status);

}
