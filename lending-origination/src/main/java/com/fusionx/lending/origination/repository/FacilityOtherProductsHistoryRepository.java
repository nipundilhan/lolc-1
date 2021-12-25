package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FacilityOtherProductsHistory;

@Repository
public interface FacilityOtherProductsHistoryRepository extends JpaRepository<FacilityOtherProductsHistory, Long> {

}
