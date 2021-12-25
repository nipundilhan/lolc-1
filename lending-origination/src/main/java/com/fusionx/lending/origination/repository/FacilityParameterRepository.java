package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FacilityParameter;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface FacilityParameterRepository extends JpaRepository<FacilityParameter, Long>{

	public Optional<FacilityParameter> findByLeadInfoIdAndStatus(Long leadId, CommonStatus status);

	public Optional<FacilityParameter> findByLeadInfoId(Long leadId);
}
