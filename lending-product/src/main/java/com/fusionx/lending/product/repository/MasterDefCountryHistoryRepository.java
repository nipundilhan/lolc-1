package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterDefCountryHistory;

@Repository
public interface MasterDefCountryHistoryRepository   extends JpaRepository<MasterDefCountryHistory, Long> {

}
