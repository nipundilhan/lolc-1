package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefCountryPending;
import com.fusionx.lending.product.domain.MasterDefinitionPending;

@Repository
public interface MasterDefCountryPendingRepository   extends JpaRepository<MasterDefCountryPending, Long> {
	
	List<MasterDefCountryPending> findAllByMasterDefinitionPending(MasterDefinitionPending masterDefinitionPending);

}
