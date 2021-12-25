package com.fusionx.lending.transaction.resource;

/**
 * Bank Transaction Code service
 *
 * @author Nisalak
 * @Dat 29-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   29-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BankTransactionSubCodeResource {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("codeId")
    private String codeId;

    @JsonProperty("bankTransSubCode")
    @NotBlank(message = "{bankTransSubCode.not-null}")
    @Size(max = 4, min = 4, message = "{bankTransSubCode.size}")
    private String subCode;

    @JsonProperty("description")
    @NotBlank(message = "{description.not-null}")
    @Size(max = 255, message = "{description.size}")
    private String description;

    @JsonProperty("status")
    @NotBlank(message = "{status.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;

    @JsonProperty("postingType")
    @NotBlank(message = "{postingType.not-null}")
    @Pattern(regexp = "CREDIT|DEBIT|REVERSAL", message = "{postingType.pattern}")
    private String postingType;

    @JsonProperty("allowDormant")
    @NotBlank(message = "{allowDormant.not-null}")
    @Pattern(regexp = "YES|NO", message = "{allowDormant.pattern}")
    private String allowDormant;

    @JsonProperty("transactionCategory")
    @NotBlank(message = "{transactionCategory.not-null}")
    @Pattern(regexp = "GENERAL|CHARGES_FEE", message = "{transactionCategory.pattern}")
    private String transactionCategory;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
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

    public String getPostingType() {
        return postingType;
    }

    public void setPostingType(String postingType) {
        this.postingType = postingType;
    }

    public String getAllowDormant() {
        return allowDormant;
    }

    public void setAllowDormant(String allowDormant) {
        this.allowDormant = allowDormant;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

}
