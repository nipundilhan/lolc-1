package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefCountryPending;
import com.fusionx.lending.product.domain.MasterDefProvincePending;
import com.fusionx.lending.product.domain.MasterDefStatePending;

@Repository
public interface MasterDefStatePendingRepository  extends JpaRepository<MasterDefStatePending, Long> {

	List<MasterDefStatePending> findAllByMasterDefProvincePending(MasterDefProvincePending masterDefProvincePending);
}
