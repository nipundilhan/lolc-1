package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BlacklistDetail;

@Repository
public interface BlacklistDetailsRepository extends JpaRepository<BlacklistDetail, Long>{

	List<BlacklistDetail> findByCustomerId(Long id);

}
