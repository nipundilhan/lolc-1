package com.fusionx.lending.transaction.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.WaiveOffTransactionType;
import com.fusionx.lending.transaction.enums.CommonStatus;

@Repository
public interface WaiveOffTransactionTypeRepository extends JpaRepository<WaiveOffTransactionType, Long> {

	public List<WaiveOffTransactionType> findByStatus(CommonStatus status);
	
	public List<WaiveOffTransactionType> findByWaiveOffTypeId(Long waiveOffTypeId);
	
}
