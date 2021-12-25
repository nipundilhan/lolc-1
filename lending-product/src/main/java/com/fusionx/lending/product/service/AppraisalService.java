package com.fusionx.lending.product.service;

import com.fusionx.lending.product.enums.AppraisalSearchEnum;
import com.fusionx.lending.product.resources.LendingAppraisalAdvanceSearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * API Service related to appraisal
 *
 * @author Thushan Jayasundara
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  	Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        16-10-2020      -                            		Thushan                        Created
 * <p>
 */
@Service
public interface AppraisalService {

    /**
     * Retrieve {@link LendingAppraisalAdvanceSearchResponse} by search
     *
     * @param tenantId            the tenant id
     * @param searchKey           the search key
     * @param appraisalSearchEnum the search criteria
     * @return the list of Lending appraisal advance search response
     */
    List<LendingAppraisalAdvanceSearchResponse> getAllAppraisalBySearch(String tenantId, String searchKey, String appraisalSearchEnum);

}
