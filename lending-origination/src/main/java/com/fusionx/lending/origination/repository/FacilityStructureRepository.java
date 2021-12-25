package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FacilityStructure;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface FacilityStructureRepository extends JpaRepository<FacilityStructure, Long>{

	public List<FacilityStructure> findByFacilityParameterIdAndStatus(Long facilityParameterId, CommonStatus status);
}
