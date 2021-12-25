package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefCountry;
import com.fusionx.lending.product.domain.MasterDefProvince;

@Repository
public interface MasterDefProvinceRepository  extends JpaRepository<MasterDefProvince, Long> {
	
	
	List<MasterDefProvince> findAllByMasterDefCountry(MasterDefCountry masterDefCountry);
	
	@Query("SELECT mdp.provinceId  FROM MasterDefProvince mdp  WHERE mdp.masterDefCountry.id = :masterDefCountryId")
	List<Long> findProvinceIdByMasterDefCountry(@Param("masterDefCountryId")Long masterDefCountryId);


}
