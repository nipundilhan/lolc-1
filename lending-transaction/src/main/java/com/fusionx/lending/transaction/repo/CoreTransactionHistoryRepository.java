package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.CoreTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Core Transaction History Repository
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   04-10-2021   FXL-1052   	 FXL-1001	 Pasindu      Created
 * <p>
 * *******************************************************************************************************
 */


@Repository
public interface CoreTransactionHistoryRepository extends JpaRepository<CoreTransactionHistory, Long> {

}
