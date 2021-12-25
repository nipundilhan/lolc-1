package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.Collaterals;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollateralsRepository extends JpaRepository<Collaterals, Long> {

    Boolean existsByLendingAccountDetail(LendingAccountDetail lendingAccountDetail);

    List<Collaterals> findByStatus(CommonStatus status);

    Collaterals findByLendingAccountDetail(LendingAccountDetail lendingAccountDetail);
}
