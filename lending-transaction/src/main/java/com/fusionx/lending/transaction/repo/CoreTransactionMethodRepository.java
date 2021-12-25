package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.CoreTransactionMethod;
import com.fusionx.lending.transaction.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * ServiceAccessChannel Repository
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   01-10-2021   FXL-1052   	FXL-1001  Pasindu Thanthree      Created
 * <p>
 * *******************************************************************************************************
 */

@Repository
public interface CoreTransactionMethodRepository extends JpaRepository<CoreTransactionMethod, Long> {

    public List<CoreTransactionMethod> findAll();

    public List<CoreTransactionMethod> findByNameContaining(String name);

    public List<CoreTransactionMethod> findByStatus(CommonStatus status);

    public Optional<CoreTransactionMethod> findByCode(String code);


}
