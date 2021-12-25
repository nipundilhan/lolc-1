package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.CheckList;
import com.fusionx.lending.product.resources.CheckListAddResource;
import com.fusionx.lending.product.resources.CheckListResponseResource;
import com.fusionx.lending.product.resources.CheckListUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Check List.
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
 * 1        26-10-2021      -               -           Rohan       Created
 * <p>
 */
@Service
public interface CheckListService {

    /**
     * saves the check list
     *
     * @param tenantId             the tenant id
     * @param checkListAddResource the add resource of CheckList
     * @return the check list
     */
    CheckList addCheckList(String tenantId, CheckListAddResource checkListAddResource);

    /**
     * Gets the Check list by id
     *
     * @param id the id of the record
     * @return the Check List
     */
    Optional<CheckList> getById(Long id);

    /**
     * update the check list
     *
     * @param tenantId                the tenant id
     * @param id                      the id of the record
     * @param checkListUpdateResource the update resource of CheckList
     * @return the CheckList
     */
    CheckList updateCheckList(String tenantId, Long id, CheckListUpdateResource checkListUpdateResource);

    /**
     * Returns the checklist by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of checklist
     */
    List<CheckList> getByStatus(String status);

    /**
     * Returns the checklist by lending account
     *
     * @param lendingAccountId the lending account id
     * @return the list of checklist
     */
    Optional<CheckList> getByLendingAccount(Long lendingAccountId);

    /**
     * Returns all checklists
     *
     * @return the list of checklist
     */
    List<CheckList> getAll();

    /**
     * convert CheckList entity to CheckListResponseResource
     *
     * @param checkList
     * @return CheckListResponseResource
     */
    CheckListResponseResource getCheckListResponseResource(CheckList checkList);
}
