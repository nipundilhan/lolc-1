package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Add Schedule Event Resource
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   17-02-2020   FX-3038       FX-2961    SenithaP     Created
 * <p>
 * *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddScheduleEventResource {

    @NotBlank(message = "{common.not-null}")
    @Size(max = 70, message = "{common-name.size}")
    private String eventName;

    //@NotBlank(message = "{common.not-null}")
    private String relatedTxnEventId;

    //@NotBlank(message = "{common.not-null}")
    private String relatedTxnEventName;

    //@NotBlank(message = "{common.not-null}")
    private String relatedTxnEventCode;

    private String note;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
    private String code;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
    private String status;

    private String tenantId;

    public String getRelatedTxnEventCode() {
        return relatedTxnEventCode;
    }

    public void setRelatedTxnEventCode(String relatedTxnEventCode) {
        this.relatedTxnEventCode = relatedTxnEventCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getRelatedTxnEventId() {
        return relatedTxnEventId;
    }

    public void setRelatedTxnEventId(String relatedTxnEventId) {
        this.relatedTxnEventId = relatedTxnEventId;
    }

    public String getRelatedTxnEventName() {
        return relatedTxnEventName;
    }

    public void setRelatedTxnEventName(String relatedTxnEventName) {
        this.relatedTxnEventName = relatedTxnEventName;
    }

}
