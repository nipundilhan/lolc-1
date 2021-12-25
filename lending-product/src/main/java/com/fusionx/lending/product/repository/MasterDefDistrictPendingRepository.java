package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefDistrictPending;
import com.fusionx.lending.product.domain.MasterDefProvincePending;
import com.fusionx.lending.product.domain.MasterDefStatePending;

@Repository
public interface MasterDefDistrictPendingRepository   extends JpaRepository<MasterDefDistrictPending, Long> {
	
	List<MasterDefDistrictPending> findAllByMasterDefStatePending(MasterDefStatePending masterDefStatePending);

}
