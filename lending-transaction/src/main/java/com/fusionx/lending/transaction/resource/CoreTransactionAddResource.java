package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Common Add Resource
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   01-10-2021      		     			PasinduT      Created
 * <p>
 * *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CoreTransactionAddResource {

    @NotBlank(message = "{common.not-null}")  
    @Pattern(regexp = "CT01||CT02||CT03", message = "{core-transaction-method-code.pattern}")
    private String code;  
  

    @NotBlank(message = "{common.not-null}")
    @Size(max = 70, message = "{common-name.size}")
    private String name;

    @Size(max = 350, message = "{common-length01.size}")
    private String description;

    @NotBlank(message = "{status.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;    

    @NotBlank(message = "{common.not-null}")
    private String transactionCodeId;
    
    @NotBlank(message = "{common.not-null}")
    private String transactionCode;

    @NotBlank(message = "{common.not-null}")
    private String subTransactionCodeId;

    @NotBlank(message = "{common.not-null}")
    private String subTransactionCode;


    

    

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

    public String getTransactionCodeId() {
        return transactionCodeId;
    }

    public void setTransactionCodeId(String transactionCodeId) {
        this.transactionCodeId = transactionCodeId;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getSubTransactionCodeId() {
        return subTransactionCodeId;
    }

    public void setSubTransactionCodeId(String subTransactionCodeId) {
        this.subTransactionCodeId = subTransactionCodeId;
    }

    public String getSubTransactionCode() {
        return subTransactionCode;
    }

    public void setSubTransactionCode(String subTransactionCode) {
        this.subTransactionCode = subTransactionCode;
    }


}
