package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Lending Account Detail Repository
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-10-2021      -		      -	        Rohan        Created
 *
 ********************************************************************************************************
 */
@Repository
public interface LendingAccountDetailRepository extends JpaRepository<LendingAccountDetail, Long> {

    @Query(value = "SELECT la FROM LendingAccountDetail la WHERE la.tenantId=?1")
    List<LendingAccountDetail> getLendingAccountByTenantId(String tenantId);

    @Query(value = "SELECT la FROM LendingAccountDetail la WHERE la.tenantId=?1 AND id=?2")
    LendingAccountDetail getByTenantIdAndId(String tenantId, Long id);

    List<LendingAccountDetail> findByStatus(CommonStatus status);

    Boolean existsByApprovedLeadId(Long approvedLeadId);
}
