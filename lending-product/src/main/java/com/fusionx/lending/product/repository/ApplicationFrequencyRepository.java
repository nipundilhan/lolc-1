package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Application Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6511             Senitha Perera          Created
 * <p>
 */
@Repository
public interface ApplicationFrequencyRepository extends JpaRepository<ApplicationFrequency, Long> {

    Optional<ApplicationFrequency> findByCode(String code);

    List<ApplicationFrequency> findByStatus(CommonStatus status);

    List<ApplicationFrequency> findByName(String name);

    Optional<ApplicationFrequency> findByCodeAndIdNotIn(String code, Long id);
    
    Optional<ApplicationFrequency> findByIdAndStatus(Long id,CommonStatus status);
    
    Optional<ApplicationFrequency> findByIdAndNameAndStatus(Long id,String name,CommonStatus status);

}
