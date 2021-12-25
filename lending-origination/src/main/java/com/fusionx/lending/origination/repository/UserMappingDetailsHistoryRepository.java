package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.origination.domain.UserMappingDetailsHistory;

public interface UserMappingDetailsHistoryRepository extends JpaRepository<UserMappingDetailsHistory, Long> {

}
