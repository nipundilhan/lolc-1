package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.DisbursementConditions;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * API Service related toDisbursement Conditions.
 *
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        16-09-2020      -               FX-788              Dilhan                   Created
 * <p>
 */
@Repository
public interface DisbursementConditionsRepository extends JpaRepository<DisbursementConditions, Long>{

	public List<DisbursementConditions> findByStatus(CommonStatus status);

	public List<DisbursementConditions> findByNameContaining(String name);

	public Optional<DisbursementConditions> findByCode(String code);
	
	public Optional<DisbursementConditions> findByCodeAndIdNotIn(String code, Long id);
}
