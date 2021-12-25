package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransEventAccStatusResource {

    @JsonProperty("transEventId")
    @NotBlank(message = "{transEventId.not-null}")
    private String transEventId;

    @JsonProperty("transEventCode")
    @NotBlank(message = "{transEventCode.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    private String transEventCode;

    @JsonProperty("accStatus")
    @NotBlank(message = "{accStatus.not-null}")
    private String accStatus;

    @JsonProperty("accStatusDesc")
    //@NotBlank(message="{accStatusDesc.not-null}")
    private String accStatusDesc;

    @JsonProperty("status")
    @NotBlank(message = "{status.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;

    public String getTransEventId() {
        return transEventId;
    }

    public void setTransEventId(String transEventId) {
        this.transEventId = transEventId;
    }

    public String getTransEventCode() {
        return transEventCode;
    }

    public void setTransEventCode(String transEventCode) {
        this.transEventCode = transEventCode;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getAccStatusDesc() {
        return accStatusDesc;
    }

    public void setAccStatusDesc(String accStatusDesc) {
        this.accStatusDesc = accStatusDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
