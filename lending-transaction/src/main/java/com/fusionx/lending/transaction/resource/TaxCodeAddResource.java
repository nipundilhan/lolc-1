package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Tax Code Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   11-10-2021      		     FXL-1130	Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TaxCodeAddResource {

    @NotBlank(message = "{common.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
    private String code;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 70, message = "{common-name.size}")
    private String name;

//    @Size(max = 350, message = "{common-length01.size}")
//    private String description;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String codeId;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String transactionTypeId;
    
    @NotBlank(message = "{common.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
    private String transactionTypeCode;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String subTransactionTypeId;
    
    @NotBlank(message = "{common.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
    private String subTransactionTypeCode;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(String transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getSubTransactionTypeId() {
        return subTransactionTypeId;
    }

    public void setSubTransactionTypeId(String subTransactionTypeId) {
        this.subTransactionTypeId = subTransactionTypeId;
    }

	public String getTransactionTypeCode() {
		return transactionTypeCode;
	}

	public void setTransactionTypeCode(String transactionTypeCode) {
		this.transactionTypeCode = transactionTypeCode;
	}

	public String getSubTransactionTypeCode() {
		return subTransactionTypeCode;
	}

	public void setSubTransactionTypeCode(String subTransactionTypeCode) {
		this.subTransactionTypeCode = subTransactionTypeCode;
	}

}
