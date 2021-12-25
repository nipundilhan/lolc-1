package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessRiskType;
import com.fusionx.lending.origination.domain.BusinessType;

/**
 * API Service related to Business Risk type.
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
 * 1        07-2021      	            	          Nipun Dilhan      Created
 * <p>
 *
 */

@Repository
public interface BusinessRiskTypeRepository  extends JpaRepository<BusinessRiskType, Long> {

	public List<BusinessRiskType> findByStatus(String status);

	public List<BusinessRiskType> findByNameContaining(String name);

	public Optional<BusinessRiskType> findByCode(String code);
	
	public Optional<BusinessRiskType> findByCodeAndIdNotIn(String code, Long id);
}
