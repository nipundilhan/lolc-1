package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * API Service related to Common List Item.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * ....................................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6514             Senitha Perera          Created         ChinthakaMa     16-Sep-2021
 * <p>
 */
@Repository
public interface CommonListItemRepository extends JpaRepository<CommonListItem, Long> {

    List<CommonListItem> findByStatus(CommonStatus status);

    List<CommonListItem> findByNameContaining(String name);

    List<CommonListItem> findByCode(String code);

    List<CommonListItem> findByReferenceCode(String referenceCode);

    Boolean existsByCodeAndReferenceCodeAndStatus(String code, String referenceCode, CommonStatus status);

    Optional<CommonListItem> findByCodeAndReferenceCodeAndIdNotIn(String code, String referenceCode, Long id);

    Optional<CommonListItem> findByIdAndNameAndReferenceCodeAndStatus(Long id, String name, String referenceCode, CommonStatus status);

    Optional<CommonListItem> findByIdAndNameAndStatus(Long id, String name, CommonStatus active);
    
    Optional<CommonListItem> findByIdAndReferenceCodeAndStatus(Long id, String referenceCode, CommonStatus status);


}
