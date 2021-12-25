package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalDetail;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.LeadStatus;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.resource.*;
import com.fusionx.lending.origination.service.LeadInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/lead-info")
@CrossOrigin(origins = "*")
public class LeadInfoController extends MessagePropertyBase {

    private LeadInfoService leadInfoService;

    @Autowired
    public void setLeadInfoService(LeadInfoService leadInfoService) {
        this.leadInfoService = leadInfoService;
    }

    /**
     * Gets all LeadInfo.
     *
     * @param tenantId - the tenant id
     * @return Page<LeadInfo>
     */
    @GetMapping(value = "/{tenantId}/all")
    public Page<LeadInfo> getAllLeadInfo(@PathVariable(value = "tenantId") String tenantId, @PageableDefault(size = 10) Pageable pageable) {
        return leadInfoService.findAll(pageable);

    }

    /**
     * Gets the LeadInfo Detail by id.
     *
     * @param tenantId - the tenant id
     * @param id       - LeadInfo id
     * @return LeadInfo
     */
    @GetMapping(value = "/{tenantId}/{id}")
    public ResponseEntity<Object> getLeadInfoDetailById(@PathVariable(value = "tenantId") String tenantId, @PathVariable(value = "id") Long id) {

        LeadInfo leadInfo = leadInfoService.findDetailById(id);
        if (leadInfo != null) {
            return new ResponseEntity<>(leadInfo, HttpStatus.OK);
        } else {
            SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }


    @PostMapping(value = "/{tenantId}/finish")
    public ResponseEntity<Object> save(@PathVariable(value = "tenantId") String tenantId,
                                       @Valid @RequestBody ApprovalDataResource approvalDataResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));

        ApprovalDetail engineCapacity = leadInfoService.save(tenantId, approvalDataResource);
        SuccessAndErrorDetailsResource successDetailsDto = new SuccessAndErrorDetailsResource(environment.getProperty("rec.saved"), engineCapacity.getId().toString());
        return new ResponseEntity<>(successDetailsDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{tenantId}/approval-finish/{leadId}")
    public ResponseEntity<?> updateFinalApproval(@PathVariable(value = "tenantId") String tenantId,
                                                 @PathVariable(value = "leadId") Long leadId) {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Optional<LeadInfo> isDocumentRequest = leadInfoService.findById(leadId);

        if (isDocumentRequest.isPresent()) {
            leadInfoService.finalApprovalUpdate(tenantId, isDocumentRequest.get(), CommonStatus.APPROVED);

            responseMessage.setMessages(environment.getProperty(RECORD_UPDATED));
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get leadInfo by getByLeadStatus
     *
     * @param tenantId   the tenant id
     * @param leadStatus should be either <code>ACTIVE</code> or <code>INACTIVE</code>
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/lead-status/{leadStatus}")
    public ResponseEntity<Object> getByLeadStatus(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "leadStatus") String leadStatus) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        LeadStatus theLeadStatus = LeadStatus.getLeadStatus(leadStatus);

        if (theLeadStatus == null) {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }

        if (!LeadStatus.checkValueExists(LeadStatus.getLeadStatus(leadStatus))) {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }

        if (leadStatus.equals(CommonStatus.ACTIVE.toString()) || leadStatus.equals(CommonStatus.INACTIVE.toString())) {
            List<LeadInfo> leadInfo = leadInfoService.findByLeadStatus(leadStatus);
            int size = leadInfo.size();
            if (size > 0) {
                return new ResponseEntity<>(leadInfo, HttpStatus.OK);
            }
        }

        responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);

    }

    /**
     * update leadInfo Branch Assigned details
     *
     * @param tenantId                      the tenant id
     * @param leadInforBranchAssignResource the resource file
     * @return Long
     */
    @PutMapping(value = "/{tenantId}/assign-branch")
    public ResponseEntity<Object> updateAssignedBranch(@PathVariable(value = "tenantId") String tenantId,
                                                       @Valid @RequestBody LeadInforBranchAssignUpdateResource leadInforBranchAssignResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Long branchId = leadInfoService.updateAssignedBranch(tenantId, leadInforBranchAssignResource);

        if (branchId != null) {
            responseMessage.setValue(branchId);
            responseMessage.setMessages(environment.getProperty(RECORD_UPDATED));
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * update leadInfo Marketing Officer Assigned details
     *
     * @param tenantId                                      the tenant id
     * @param leadInforMarketingOfficerAssignUpdateResource the resource file
     * @return Long
     */
    @PutMapping(value = "/{tenantId}/assign-marketing-officer")
    public ResponseEntity<Object> updateAssignedMarketingOfficer(@PathVariable(value = "tenantId") String tenantId,
                                                                 @Valid @RequestBody LeadInforMarketingOfficerAssignUpdateResource leadInforMarketingOfficerAssignUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Long moId = leadInfoService.updateAssignedMarketingOfficer(tenantId, leadInforMarketingOfficerAssignUpdateResource);
        if (moId != null) {
            responseMessage.setValue(moId);
            responseMessage.setMessages(environment.getProperty(RECORD_UPDATED));
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * update leadInfo to current user details
     *
     * @param tenantId                                   the tenant id
     * @param leadInforAssignToCurrentUserUpdateResource the resource file
     * @return Long
     */
    @PutMapping(value = "/{tenantId}/assign-current-user")
    public ResponseEntity<Object> updateCurrentUserForMarketingOfficer(@PathVariable(value = "tenantId") String tenantId,
                                                                       @Valid @RequestBody LeadInforAssignToCurrentUserUpdateResource leadInforAssignToCurrentUserUpdateResource) {

        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        Long moId = leadInfoService.updateCurrentUserForMarketingOfficer(tenantId, leadInforAssignToCurrentUserUpdateResource);
        if (moId != null) {
            responseMessage.setValue(moId);
            responseMessage.setMessages(environment.getProperty(RECORD_UPDATED));
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get leadInfo by createdDate
     *
     * @param tenantId the tenant id
     * @param date     the date
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/date/{date}")
    public ResponseEntity<Object> getByCreatedDate(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "date") String date) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        List<LeadInfo> leadInfo = leadInfoService.getByDate(date);
        int size = leadInfo.size();
        if (size > 0) {
            return new ResponseEntity<>(leadInfo, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get leadInfo by CustomerName
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/customer-name/{name}")
    public ResponseEntity<Object> getByCustomerNameContaining(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "name") String name) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        List<LeadInfo> leadInfo = leadInfoService.getByCustomerNameContaining(name);
        int size = leadInfo != null ? leadInfo.size() : 0;
        if (size > 0) {
            return new ResponseEntity<>(leadInfo, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * get leadInfo by branchId
     *
     * @param tenantId the tenant id
     * @param branchId the branch id
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/branch/{branchId}")
    public ResponseEntity<Object> getByBranch(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "branchId") Long branchId) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();

        List<LeadInfo> leadInfo = leadInfoService.getByBranch(branchId);
        int size = leadInfo.size();
        if (size > 0) {
            return new ResponseEntity<>(leadInfo, HttpStatus.OK);
        } else {
            responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
            return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get leadInfo by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return List<Type>
     */
    @GetMapping(value = "/{tenantId}/status/{status}")
    public ResponseEntity<Object> getByStatus(
            @PathVariable(value = "tenantId") String tenantId,
            @PathVariable(value = "status") String status) {

        SuccessAndErrorDetailsResource responseMessage = new SuccessAndErrorDetailsResource();
        if (status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {

            List<LeadInfo> leadInfo = leadInfoService.getByStatus(status);

            int size = leadInfo.size();
            if (size > 0) {
                return new ResponseEntity<>(leadInfo, HttpStatus.OK);
            } else {
                responseMessage.setMessages(environment.getProperty(RECORD_NOT_FOUND));
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }
        } else {
            responseMessage.setMessages(RECORD_NOT_FOUND);
            throw new ValidateRecordException(environment.getProperty(COMMON_STATUS_PATTERN), "message");
        }
    }

    /**
     * get leadInfo by status
     *
     * @param @PathVariable{tenantId}
     * @param @PathVariable{status}
     * @return List<Type>
     */
    @PostMapping(value = "/{tenantId}/lead/{leadNo}/status-update")
    public String upadetFinalApprovalFlowStatus(@PathVariable(value = "tenantId") String tenantId,
                                                @PathVariable(value = "leadNo") Long leadNo,
                                                @Valid @RequestBody UpadetLeadFinalApprovalFlowStatus request) throws Exception {
        if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty()) {
            throw new UserNotFound(environment.getProperty(USER_NOT_FOUND));
        }
        return leadInfoService.upadetFinalApprovalFlowStatus(leadNo, request.isApprove());
    }
}
