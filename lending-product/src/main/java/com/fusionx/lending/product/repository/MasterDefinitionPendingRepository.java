package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * API Service related to Lending Module Definition - Master Definition
 *
 * @author Dilki Kalansooriya (Inova)
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Version History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * .....................................................................................................................................<br/>
 * 1        12-July-2021	FXL-1			FXL-5				DilkiK (Inova)			Created			ChinthakaMa     16-Sep-2021
 * <p>
 */
@Repository
public interface MasterDefinitionPendingRepository extends JpaRepository<MasterDefinitionPending, Long> {

    Optional<MasterDefinitionPending> findByMasterDefinitionIdAndStatusAndApproveStatus(Long id, CommonStatus status, CommonApproveStatus approveStatus);

    List<MasterDefinitionPending> findAllByMasterDefinitionAndStatus(MasterDefinition masterDefinition, CommonStatus status);

}
