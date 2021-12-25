package com.fusionx.lending.origination.repository;

import com.fusionx.lending.origination.domain.DaLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DA Limit Repository
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   05-05-2021      		     FX-6269	Amal       Created
 * <p>
 * *******************************************************************************************************
 */

@Repository
public interface DaLimitRepository extends JpaRepository<DaLimit, Long> {

    public List<DaLimit> findByStatus(String status);

    public List<DaLimit> findByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryId(Long groupId, String level, Long categoryId);

    //public Optional<DaLimit> findByCodeAndIdNotIn(String code, Long id);

    public Boolean existsByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryIdAndStatus(Long group, String daLevel, Long category, String status);

    public Boolean existsByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryIdAndStatusAndIdNotIn(Long group, String daLevel, Long category, String status, Long id);

    public Boolean existsByDelegationAuthorityGroupIdAndDaLevelAndStatus(Long group, String daLevel, String status);
}
