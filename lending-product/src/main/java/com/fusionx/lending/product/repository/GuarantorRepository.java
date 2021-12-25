package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.Guarantor;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Guarantor Detail Repository
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-10-2021      -		      -	        Thushan        Created
 *
 ********************************************************************************************************
 */
@Repository
public interface GuarantorRepository extends JpaRepository<Guarantor, Long> {

    Boolean existsByLendingAccountDetail(LendingAccountDetail lendingAccountDetail);

    List<Guarantor> findByStatus(CommonStatus status);

    Guarantor findByLendingAccountDetail(LendingAccountDetail lendingAccountDetail);
}
