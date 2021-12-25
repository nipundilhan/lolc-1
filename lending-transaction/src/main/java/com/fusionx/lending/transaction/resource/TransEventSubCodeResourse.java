package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransEventSubCodeResourse {


    @JsonProperty("transEventId")
    @NotBlank(message = "{transEventId.not-null}")
    private String transEventId;

    @JsonProperty("transEventCode")
    @NotBlank(message = "{transEventCode.not-null}")
    private String transEventCode;

    @JsonProperty("subCodeId")
    @NotBlank(message = "{subCodeId.not-null}")
    private String subCodeId;

    @JsonProperty("subCode")
    @NotBlank(message = "{subCode.not-null}")
    private String subCode;
	
	/*@JsonProperty("description")
	@NotBlank(message="{description.not-null}")
	@Size(max = 255, message = "{description.size}")
	private String subCodeDesc;*/

    @JsonProperty("currConversionRateType")
    @NotBlank(message = "{currConversionRateType.not-null}")
    @Pattern(regexp = "BUYING_RATE|SELLING_RATE|MID_RATE|SPECIAL_RATE1|SPECIAL_RATE2|BUY_SPREAD|SELL_SPREAD", message = "{currConversionRateType.pattern}")
    private String currConversionRateType;

    @JsonProperty("status")
    @NotBlank(message = "{status.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;

    @JsonProperty("bankTransactionCodeId")
    @NotBlank(message = "{bankTransactionCodeId.not-null}")
    private String bankTransactionCodeId;

    @JsonProperty("bankTransactionCode")
    @NotBlank(message = "{bankTransactionCode.not-null}")
    private String bankTransactionCode;


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

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubCodeId() {
        return subCodeId;
    }

    public void setSubCodeId(String subCodeId) {
        this.subCodeId = subCodeId;
    }

    public String getBankTransactionCodeId() {
        return bankTransactionCodeId;
    }

    public void setBankTransactionCodeId(String bankTransactionCodeId) {
        this.bankTransactionCodeId = bankTransactionCodeId;
    }

    public String getBankTransactionCode() {
        return bankTransactionCode;
    }

    public void setBankTransactionCode(String bankTransactionCode) {
        this.bankTransactionCode = bankTransactionCode;
    }

    public String getCurrConversionRateType() {
        return currConversionRateType;
    }

    public void setCurrConversionRateType(String currConversionRateType) {
        this.currConversionRateType = currConversionRateType;
    }

}
