package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefProvince;
import com.fusionx.lending.product.domain.MasterDefState;

@Repository
public interface MasterDefStateRepository   extends JpaRepository<MasterDefState, Long> {

	List<MasterDefState> findAllByMasterDefProvince(MasterDefProvince masterDefProvince);
	
	@Query("SELECT mds.stateId  FROM MasterDefState mds  WHERE mds.masterDefProvince.id = :masterDefProvinceId")
	List<Long> findStateIdByMasterDefProvince(@Param("masterDefProvinceId")Long masterDefProvinceId);
}
