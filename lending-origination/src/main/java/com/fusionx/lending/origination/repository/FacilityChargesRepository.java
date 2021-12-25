package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FacilityCharges;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface FacilityChargesRepository extends JpaRepository<FacilityCharges, Long>{

	public List<FacilityCharges> findByFacilityParameterIdAndStatus(Long facilityParameterId, CommonStatus status);
	
	public List<FacilityCharges> findByFacilityStructureIdAndStatus(Long facilityStructureId, CommonStatus status);
}
