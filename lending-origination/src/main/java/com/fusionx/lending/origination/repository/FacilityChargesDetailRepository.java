package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FacilityChargesDetail;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface FacilityChargesDetailRepository extends JpaRepository<FacilityChargesDetail, Long>{

	public List<FacilityChargesDetail> findByfacilityChargesIdAndStatus(Long facilityChargesId, CommonStatus status);
}
