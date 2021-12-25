package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Brand.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  	Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2020      -                            		Menuka Jayasinghe          Created
 * <p>
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findByStatus(CommonStatus status);

    List<Brand> findByNameContaining(String name);

    Optional<Brand> findByCode(String code);

    Optional<Brand> findByCodeAndIdNotIn(String code, Long id);

}
