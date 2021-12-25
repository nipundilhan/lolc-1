package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
public interface EligibilityRepository extends JpaRepository<Eligibility, Long> {

    List<Eligibility> findByStatus(CommonStatus status);

    List<Eligibility> findByNameContaining(String name);

    Optional<Eligibility> findByCode(String code);

    Optional<Eligibility> findByCodeAndIdNotIn(String code, Long id);

    Optional<Eligibility> findByIdAndStatus(Long id, CommonStatus status);

    Optional<Eligibility> findByIdAndNameAndStatus(Long id, String name,
                                                   CommonStatus active);
    
    Optional<Eligibility> findByIdAndName(Long id, String name);

}
