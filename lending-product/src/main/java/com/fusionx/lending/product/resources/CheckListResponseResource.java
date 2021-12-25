package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Check list response Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-11-2021      		        -	     Rohan      Created
 *
 ********************************************************************************************************
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CheckListResponseResource {
    private String id;
    private String checkMark;
    private String remarks;
    private String tenantId;
    private String status;
    private String lendingAccountDetailId;
    private String documentCheckListDetailId;

    public CheckListResponseResource() {
    }

    public CheckListResponseResource(String id, String checkMark, String remarks, String tenantId, String status, String lendingAccountDetailId, String documentCheckListDetailId) {
        this.id = id;
        this.checkMark = checkMark;
        this.remarks = remarks;
        this.tenantId = tenantId;
        this.status = status;
        this.lendingAccountDetailId = lendingAccountDetailId;
        this.documentCheckListDetailId = documentCheckListDetailId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckMark() {
        return checkMark;
    }

    public void setCheckMark(String checkMark) {
        this.checkMark = checkMark;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLendingAccountDetailId() {
        return lendingAccountDetailId;
    }

    public void setLendingAccountDetailId(String lendingAccountDetailId) {
        this.lendingAccountDetailId = lendingAccountDetailId;
    }

    public String getDocumentCheckListDetailId() {
        return documentCheckListDetailId;
    }

    public void setDocumentCheckListDetailId(String documentCheckListDetailId) {
        this.documentCheckListDetailId = documentCheckListDetailId;
    }
}
