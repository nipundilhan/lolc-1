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
public class DocumentAddResource {

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "YES|NO", message = "{common-status.pattern}")
    private String isPrinted;

    private String tenantId;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
    private String status;

    @NotBlank(message = "{common.not-null}")
    private String lendingAccountDetailId;

    @NotBlank(message = "{common.not-null}")
    private String documentId;

    public String getIsPrinted() {
        return isPrinted;
    }

    public void setIsPrinted(String isPrinted) {
        this.isPrinted = isPrinted;
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
