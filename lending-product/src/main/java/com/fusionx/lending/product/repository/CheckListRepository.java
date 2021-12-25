package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.CheckList;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Check list Repository
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-10-2021      		        -	    Rohan        Created
 *
 ********************************************************************************************************
 */
@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    Optional<CheckList> findByLendingAccountDetail(LendingAccountDetail lendingAccountDetail);

    Optional<CheckList> findByLendingAccountDetailAndStatus(LendingAccountDetail lendingAccountDetail, CommonStatus status);

    List<CheckList> findByStatus(CommonStatus status);

}
