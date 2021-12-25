package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.Borrowers;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Borrowers Repository
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-10-2020   			   	        	Thushan    Created
 *
 ********************************************************************************************************
 */

@Repository
public interface BorrowersRepository extends JpaRepository<Borrowers, Long> {

    Boolean existsByLendingAccountIdAndCustomerId(LendingAccountDetail lendingAccountDetail, Long customerId);

    List<Borrowers> findByStatus(CommonStatus status);

    List<Borrowers> findAllByLendingAccountId(LendingAccountDetail ledgerDetail);

}
