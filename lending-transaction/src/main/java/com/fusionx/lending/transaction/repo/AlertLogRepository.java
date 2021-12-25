package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.AlertLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertLogRepository extends JpaRepository<AlertLog, Long> {

}
