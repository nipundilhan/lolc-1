package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FacilityTax;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface FacilityTaxRepository extends JpaRepository<FacilityTax, Long>{

	public List<FacilityTax> findByFacilityParameterIdAndStatus(Long facilityParameterId, CommonStatus status);
	
	public List<FacilityTax> findByFacilityStructureIdAndStatus(Long facilityStructureId, CommonStatus status);
}
