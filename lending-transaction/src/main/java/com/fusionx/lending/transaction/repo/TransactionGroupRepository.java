package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.TransactionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionGroupRepository extends JpaRepository<TransactionGroup, Long> {

}
