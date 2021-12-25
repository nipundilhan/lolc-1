package com.fusionx.lending.origination.repository;

import com.fusionx.lending.origination.domain.UserDaLevelMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DA Limit User Map Repository
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   05-05-2021      		     FX-6269	Amal       Created
 * <p>
 * *******************************************************************************************************
 */

@Repository
public interface UserDaLevelMapRepository extends JpaRepository<UserDaLevelMap, Long> {

    public List<UserDaLevelMap> findByStatus(String status);

    public List<UserDaLevelMap> findByDelegationAuthorityGroupIdAndDaLevel(Long groupId, String level);

    public Boolean existsByDelegationAuthorityGroupIdAndDaLevelAndUserIdAndStatus(Long group, String daLevel, Long userId, String status);

    public Boolean existsByDelegationAuthorityGroupIdAndDaLevelAndUserIdAndStatusAndIdNotIn(Long group, String daLevel, Long userId, String status, Long id);

}
