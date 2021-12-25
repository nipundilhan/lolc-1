package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.resources.LeadInfoRequestResponseResource;
import com.fusionx.lending.product.resources.LendingAppraisalAdvanceSearchResponse;
import com.fusionx.lending.product.resources.SuccessAndErrorDetails;
import com.fusionx.lending.product.service.AppraisalService;
import com.fusionx.lending.product.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API Service related to Lending Appraisal Detail.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        13-10-2021      -               -           Thushan                  Created
 * <p>
 */
@RestController
@RequestMapping(value = "/lending-appraisal-detail")
@CrossOrigin(origins = "*")
public class AppraisalController extends MessagePropertyBase {

    private RemoteService remoteService;
    private CommonModuleProperties commonModuleProperties;
    private AppraisalService appraisalService;

    @Autowired
    public void setRemoteService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Autowired
    public void setCommonModuleProperties(CommonModuleProperties commonModuleProperties) {
        this.commonModuleProperties = commonModuleProperties;
    }

    @Autowired
    public void setAppraisalService(AppraisalService appraisalService) {
        this.appraisalService = appraisalService;
    }

    /**
     * retrieve lead information using lead id
     *
     * @param tenantId the tenant id
     * @param leadId   the lead id
     * @return get Lead info by lead id
     */
    @GetMapping(value = "/{tenantId}/leadId/{leadId}")
    public ResponseEntity<Object> getLeadInfo(@PathVariable(value = "tenantId") String tenantId,
                                              @PathVariable(value = "leadId") String leadId) {

        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        LeadInfoRequestResponseResource leadInfoRequestResponseResource = remoteService.getLeadInfoById(tenantId, leadId, commonModuleProperties.getLendingOrigination());
        if (leadInfoRequestResponseResource != null) {
            return new ResponseEntity<>(leadInfoRequestResponseResource, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * retrieve lead information advance search
     *
     * @param tenantId    the tenant id
     * @param searchKey   the search key
     * @param searchParam the search criteria
     * @return the list of Lending appraisal advance search response
     */
    @GetMapping(value = "/{tenantId}/search/{searchKey}/{searchParam}")
    public ResponseEntity<Object> getAllAppraisalBySearch(@PathVariable(value = "tenantId") String tenantId,
                                                          @PathVariable(value = "searchKey") String searchKey,
                                                          @PathVariable(value = "searchParam") String searchParam) {
        SuccessAndErrorDetails responseMessage = new SuccessAndErrorDetails();
        List<LendingAppraisalAdvanceSearchResponse> lendingAppraisalAdvanceSearchResponses = appraisalService.getAllAppraisalBySearch(tenantId, searchKey,searchParam);
        if (lendingAppraisalAdvanceSearchResponses.size() != 0) {
            return new ResponseEntity<>(lendingAppraisalAdvanceSearchResponses, HttpStatus.OK);
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

}
