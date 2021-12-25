package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.TcHeader;
import com.fusionx.lending.product.enums.CommonStatus;

@Repository
public interface TcHeaderRepository  extends JpaRepository<TcHeader, Long> {

	List<TcHeader> findByLeadId(Long id);
	
	List<TcHeader> findByLeadIdAndStatus(Long id , CommonStatus status);
}
