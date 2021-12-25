package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.transaction.domain.TransEventSubCode;
import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransSubCodeResponse extends TransEventSubCode {

    @JsonProperty("transactionEventId")
    private Long transEventId;
    @JsonProperty("transactionEvent")
    private String transEvent;


    @JsonProperty("bankTransactionCodeId")
    private Long bankTransCodeId;
    @JsonProperty("bankTransactionCode")
    private String bankTransCode;

    @JsonProperty("bankTransactionSubCodeId")
    private Long bankTransSubCodeId;
    @JsonProperty("bankTransactionSubCode")
    private String bankTransSubCode;

    public Long getTransEventId() {
        return transEventId;
    }

    public void setTransEventId(Long transEventId) {
        this.transEventId = transEventId;
    }

    public String getTransEvent() {
        return transEvent;
    }

    public void setTransEvent(String transEvent) {
        this.transEvent = transEvent;
    }

    public Long getBankTransCodeId() {
        return bankTransCodeId;
    }

    public void setBankTransCodeId(Long bankTransCodeId) {
        this.bankTransCodeId = bankTransCodeId;
    }

    public String getBankTransCode() {
        return bankTransCode;
    }

    public void setBankTransCode(String bankTransCode) {
        this.bankTransCode = bankTransCode;
    }

    public Long getBankTransSubCodeId() {
        return bankTransSubCodeId;
    }

    public void setBankTransSubCodeId(Long bankTransSubCodeId) {
        this.bankTransSubCodeId = bankTransSubCodeId;
    }

    public String getBankTransSubCode() {
        return bankTransSubCode;
    }

    public void setBankTransSubCode(String bankTransSubCode) {
        this.bankTransSubCode = bankTransSubCode;
    }


}
