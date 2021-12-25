package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.Document;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Document Repository
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   26-10-2021      		        -	    Rohan        Created
 * <p>
 * *******************************************************************************************************
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByLendingAccountDetail(LendingAccountDetail lendingAccountDetail);

    Optional<Document> findByLendingAccountDetailAndStatus(LendingAccountDetail lendingAccountDetail, CommonStatus status);

    List<Document> findByStatus(CommonStatus status);

}
