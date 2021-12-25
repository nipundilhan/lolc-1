package com.fusionx.lending.transaction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.AllocationTemplateHistory;

@Repository
public interface AllocationTemplateHistoryRepository extends JpaRepository<AllocationTemplateHistory, Long>{

}
