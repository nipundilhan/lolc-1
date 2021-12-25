package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.fusionx.lending.origination.domain.BusinessSubCenterEmpMap;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface BusinessSubCenterEmpMapRepository   extends JpaRepository<BusinessSubCenterEmpMap, Long> {

	
	public List<BusinessSubCenterEmpMap> findByStatus(CommonStatus status);

	public List<BusinessSubCenterEmpMap> findByBusinessSubCenterId(Long id);
	
}
