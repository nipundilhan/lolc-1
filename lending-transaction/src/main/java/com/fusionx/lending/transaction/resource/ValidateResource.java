package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

//@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValidateResource {
	
	public String getTransactionSubCode() {
		return transactionSubCode;
	}

	public void setTransactionSubCode(String transactionSubCode) {
		this.transactionSubCode = transactionSubCode;
	}

	@JsonProperty("message")
	private String message;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("version")
	private String version;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("subCode")
	private String subCode;
	
	@JsonProperty("benificiaryAccountId")
	private String benificiaryAccountId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("accountNoId")
	private String accountNoId;
	
	@JsonProperty("accounts")
	private String accounts;
	
	@JsonProperty("subProducts")
	private String subProducts;
	
	@JsonProperty("coreProducts")
	private String coreProducts;
	
	@JsonProperty("periods")
	private String periods;
	
	@JsonProperty("passbookNo")
	private String passbookNo;
	
	@JsonProperty("fromTransactionId")
	private String fromTransactionId;
	
	@JsonProperty("toTransactionId")
	private String toTransactionId;
	
	@JsonProperty("proprietaryBankTransCode")
	private String proprietaryBankTransCode;
	
	@JsonProperty("taxCodeId") 
	private String taxCodeId;

	@JsonProperty("taxCodeName") 
	private String taxCodeName;
	
	@JsonProperty("applicationFrequencyId") 
	private String applicationFrequencyId;
	
	@JsonProperty("charges")
	private String charges;
	
	@JsonProperty("accountStatusId")
	private String accountStatusId;
	
	@JsonProperty("accountStatusName")
	private String accountStatusName;
	
	@JsonProperty("creditNoteTypeId")
	private String creditNoteTypeId;
	
	@JsonProperty("waiveOffTypeId")
	private String waiveOffTypeId;
	
	@JsonProperty("transactionCodeId")
	private String transactionCodeId;
	
	@JsonProperty("transactionSubCodeId")
	private String transactionSubCodeId;
	
	@JsonProperty("transactionSubCode")
	private String transactionSubCode;
	
	@JsonProperty("waiveOffApprovalGroupId")
	private String waiveOffApprovalGroupId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("userName")
	private String userName;
	
	@JsonProperty("creditNoteTransactionTypeId")
	private String creditNoteTransactionTypeId;	

    public String getCreditNoteTransactionTypeId() {
		return creditNoteTransactionTypeId;
	}

	public void setCreditNoteTransactionTypeId(String creditNoteTransactionTypeId) {
		this.creditNoteTransactionTypeId = creditNoteTransactionTypeId;
	}

	/******************************/
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getProprietaryBankTransCode() {
        return proprietaryBankTransCode;
    }

    public void setProprietaryBankTransCode(String proprietaryBankTransCode) {
        this.proprietaryBankTransCode = proprietaryBankTransCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getBenificiaryAccountId() {
        return benificiaryAccountId;
    }

    public void setBenificiaryAccountId(String benificiaryAccountId) {
        this.benificiaryAccountId = benificiaryAccountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountNoId() {
        return accountNoId;
    }

    public void setAccountNoId(String accountNoId) {
        this.accountNoId = accountNoId;
    }

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public String getSubProducts() {
        return subProducts;
    }

    public void setSubProducts(String subProducts) {
        this.subProducts = subProducts;
    }

    public String getCoreProducts() {
        return coreProducts;
    }

    public void setCoreProducts(String coreProducts) {
        this.coreProducts = coreProducts;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getPassbookNo() {
        return passbookNo;
    }

    public void setPassbookNo(String passbookNo) {
        this.passbookNo = passbookNo;
    }

    public String getFromTransactionId() {
        return fromTransactionId;
    }

    public void setFromTransactionId(String fromTransactionId) {
        this.fromTransactionId = fromTransactionId;
    }

    public String getToTransactionId() {
        return toTransactionId;
    }

    public void setToTransactionId(String toTransactionId) {
        this.toTransactionId = toTransactionId;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getTaxCodeId() {
        return taxCodeId;
    }

    public void setTaxCodeId(String taxCodeId) {
        this.taxCodeId = taxCodeId;
    }

    public String getTaxCodeName() {
        return taxCodeName;
    }

    public void setTaxCodeName(String taxCodeName) {
        this.taxCodeName = taxCodeName;
    }

    public String getApplicationFrequencyId() {
        return applicationFrequencyId;
    }

    public void setApplicationFrequencyId(String applicationFrequencyId) {
        this.applicationFrequencyId = applicationFrequencyId;
    }

	public String getAccountStatusId() {
		return accountStatusId;
	}

	public void setAccountStatusId(String accountStatusId) {
		this.accountStatusId = accountStatusId;
	}

	public String getAccountStatusName() {
		return accountStatusName;
	}

	public void setAccountStatusName(String accountStatusName) {
		this.accountStatusName = accountStatusName;
	}

	public String getCreditNoteTypeId() {
		return creditNoteTypeId;
	}

	public void setCreditNoteTypeId(String creditNoteTypeId) {
		this.creditNoteTypeId = creditNoteTypeId;
	}

	public String getWaiveOffTypeId() {
		return waiveOffTypeId;
	}

	public void setWaiveOffTypeId(String waiveOffTypeId) {
		this.waiveOffTypeId = waiveOffTypeId;
	}

	public String getTransactionCodeId() {
		return transactionCodeId;
	}

	public void setTransactionCodeId(String transactionCodeId) {
		this.transactionCodeId = transactionCodeId;
	}

	public String getTransactionSubCodeId() {
		return transactionSubCodeId;
	}

	public void setTransactionSubCodeId(String transactionSubCodeId) {
		this.transactionSubCodeId = transactionSubCodeId;
	}

	public String getWaiveOffApprovalGroupId() {
		return waiveOffApprovalGroupId;
	}

	public void setWaiveOffApprovalGroupId(String waiveOffApprovalGroupId) {
		this.waiveOffApprovalGroupId = waiveOffApprovalGroupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	

}
