package com.fusionx.lending.product.repository;

import java.util.List;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.BusinessSubCenter;
import com.fusionx.lending.product.enums.CommonStatus;


/**
 * API Service related to Business Sub Center.
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        28-08-2021      	             FXL-649          Nipun Dilhan      Created
 * <p>
 *
 */
@Repository
public interface BusinessSubCenterRepository  extends JpaRepository<BusinessSubCenter, Long> {
	
	Optional<BusinessSubCenter> findByCode(String code);
	
	boolean existsByCodeAndStatus(String code, String string);
	
	List<BusinessSubCenter> findByStatus(CommonStatus status);

	List<BusinessSubCenter> findByNameContaining(String name);

	Optional<BusinessSubCenter> findByCodeAndIdNotIn(String code, Long id);
	
	List<BusinessSubCenter> findAllByBusinessCenterId(Long id);

}
