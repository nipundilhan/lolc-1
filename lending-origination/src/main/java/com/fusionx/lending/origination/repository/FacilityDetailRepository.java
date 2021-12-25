package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FacilityDetail;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface FacilityDetailRepository extends JpaRepository<FacilityDetail, Long>{

	public List<FacilityDetail> findByFacilityParameterIdAndStatus(Long facilityParameterId, CommonStatus status);
}
