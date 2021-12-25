package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AllocationOrderResource {
    @NotBlank(message = "{common.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
    private String referenceCode;

    //@NotBlank(message = "{common.not-null}")
    //private String accountStatus;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String bankTransactionCodeId;

    @NotBlank(message = "{common.not-null}")
    private String bankTransactionCodeDescription;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String bankTransactionSubCodeId;

    @NotBlank(message = "{common.not-null}")
    private String bankTransactionSubCodeDescription;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String allocationOrder;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;

    //@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String version;
    
    @NotBlank(message = "{common.not-null}")
    private String allocationTemplateId;

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    /*public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }*/

    public String getBankTransactionCodeId() {
        return bankTransactionCodeId;
    }

    public void setBankTransactionCodeId(String bankTransactionCodeId) {
        this.bankTransactionCodeId = bankTransactionCodeId;
    }


    public String getBankTransactionCodeDescription() {
        return bankTransactionCodeDescription;
    }

    public void setBankTransactionCodeDescription(String bankTransactionCodeDescription) {
        this.bankTransactionCodeDescription = bankTransactionCodeDescription;
    }

    public String getBankTransactionSubCodeId() {
        return bankTransactionSubCodeId;
    }

    public void setBankTransactionSubCodeId(String bankTransactionSubCodeId) {
        this.bankTransactionSubCodeId = bankTransactionSubCodeId;
    }

    public String getBankTransactionSubCodeDescription() {
        return bankTransactionSubCodeDescription;
    }

    public void setBankTransactionSubCodeDescription(String bankTransactionSubCodeDescription) {
        this.bankTransactionSubCodeDescription = bankTransactionSubCodeDescription;
    }

    public String getAllocationOrder() {
        return allocationOrder;
    }

    public void setAllocationOrder(String allocationOrder) {
        this.allocationOrder = allocationOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	public String getAllocationTemplateId() {
		return allocationTemplateId;
	}

	public void setAllocationTemplateId(String allocationTemplateId) {
		this.allocationTemplateId = allocationTemplateId;
	}
    
    

}
