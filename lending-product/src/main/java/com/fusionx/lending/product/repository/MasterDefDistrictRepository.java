package com.fusionx.lending.product.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefDistrict;
import com.fusionx.lending.product.domain.MasterDefProvince;
import com.fusionx.lending.product.domain.MasterDefState;


@Repository
public interface MasterDefDistrictRepository   extends JpaRepository<MasterDefDistrict, Long> {
	
	List<MasterDefDistrict> findAllByMasterDefState(MasterDefState masterDefState);
	
	
	@Query("SELECT mdd.districtId  FROM MasterDefDistrict mdd  WHERE mdd.masterDefState.id = :masterDefDistrictId")
	List<Long> findDistrictIdByMasterDefState(@Param("masterDefDistrictId")Long masterDefDistrictId);

}
