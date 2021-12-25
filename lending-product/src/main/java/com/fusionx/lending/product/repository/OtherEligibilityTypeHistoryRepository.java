package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.OtherEligibilityTypeHistory;

@Repository
public interface OtherEligibilityTypeHistoryRepository extends JpaRepository<OtherEligibilityTypeHistory, Long> {

}
