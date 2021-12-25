package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransactionEventAddResource {

    @JsonProperty("code")
    @NotBlank(message = "{code.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    private String code;


    @JsonProperty("description")
    @NotBlank(message="{accStatusDesc.not-null}")
    private String description;

    @JsonProperty("status")
    @NotBlank(message = "{status.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
