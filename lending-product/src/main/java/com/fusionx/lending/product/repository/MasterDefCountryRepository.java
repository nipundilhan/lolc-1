package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefCountry;
import com.fusionx.lending.product.domain.MasterDefinition;

@Repository
public interface MasterDefCountryRepository  extends JpaRepository<MasterDefCountry, Long> {
	
	List<MasterDefCountry> findAllByMasterDefinition(MasterDefinition masterDefinition);
	
	@Query("SELECT mdc.countryId  FROM MasterDefCountry mdc  WHERE mdc.masterDefinition.id = :masterDefinitionId")
	List<Long> findCountryIdByMasterDef(@Param("masterDefinitionId")Long masterDefinitionId);

}
