package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Check list Add Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-10-2021      		        -	     Rohan      Created
 *
 ********************************************************************************************************
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CheckListAddResource {

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "YES|NO", message = "{common-status.pattern}")
    private String checkMark;

    private String remarks;

    private String tenantId;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
    private String status;

    @NotBlank(message = "{common.not-null}")
    private String lendingAccountDetailId;

    @NotBlank(message = "{common.not-null}")
    private String documentCheckListDetailId;

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
