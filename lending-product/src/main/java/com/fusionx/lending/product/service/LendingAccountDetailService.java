package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.resources.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Lending Account Detail.
 *
 * @author Rohan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        07-10-2021      -               -           Rohan      Created
 * <p>
 */
@Service
public interface LendingAccountDetailService {


    /**
     * Returns the lendingAccountDetail by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of lendingAccountDetail
     */
    List<LendingAccountDetail> findByStatus(String status);

    /**
     * Returns the all lending account details by search text
     *
     * @param tenantId   the tenant id
     * @param searchText the keyword to search
     * @return liast of LendingAccountAdvanceSearchResponse
     */
    List<LendingAccountAdvanceSearchResponse> searchByLendingAccountNumber(String tenantId, String searchText);

    /**
     * Create the lendingAccountDetail
     *
     * @param tenantId                        the tenant id
     * @param lendingAccountDetailAddResource the lendingAccountDetailAddResource
     * @return lendingAccountDetail
     */
    LendingAccountDetail addLendingAccountDetail(String tenantId, LendingAccountDetailAddResource lendingAccountDetailAddResource);

    /**
     * Updates the lendingAccountDetail object by given object
     *
     * @param tenantId the tenant id
     * @param id the id need to be update
     * @param lendingAccountDetailUpdateResource the object which contains data
     * @return the updated lendingAccountDetail
     */
    LendingAccountDetail updateLendingAccountDetail(String tenantId, Long id, LendingAccountDetailUpdateResource lendingAccountDetailUpdateResource);

    /**
     * LendingAccountDetail Entity  convert to LendingAccountAdvanceSearchResponse
     *
     * @param lendingAccountDetail
     * @return LendingAccountAdvanceSearchResponse
     */
    LendingAccountAdvanceSearchResponse getLendingAccountAdvanceSearchResponse(String tenantId, LendingAccountDetail lendingAccountDetail);

    /**
     * retrieve lending account detail by lending account id
     *
     * @param tenantId  the tenant id
     * @param accountId the account id
     * @return LendingAccountDetailRequestResponseResource
     */
    LendingAccountDetailRequestResponseResource getAccountById(String tenantId, String accountId);

    /**
     * Update collection method using account id
     *
     * @param tenantId                    the tenant id
     * @param collectionMethodAddResource the collection method add recourse
     * @return LendingAccountDetailRequestResponseResource
     */
    LendingAccountDetail setCollectionMethod(String tenantId, CollectionMethodAddResource collectionMethodAddResource);

    /**
     * retrieve lending account by id
     *
     * @param lendingAccountDetailId
     * @return LendingAccountDetail
     */
    Optional<LendingAccountDetail> getByLendingAccountDetailById(Long lendingAccountDetailId);

    /**
     * Returns all lendingAccountDetail
     *
     * @return the list of lendingAccountDetail
     */
    List<LendingAccountDetail> getAll();
}
