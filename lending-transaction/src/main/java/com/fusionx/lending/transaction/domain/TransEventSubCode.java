package com.fusionx.lending.transaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CurrConversionRateType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Table(name = "trans_event_sub_code")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TransEventSubCode extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 7679586L;


    @Column(name = "tenant_id")
    private String tenantId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "trans_event_id", nullable = true)
    private TransactionEvent transactionEvent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "trans_code_id", nullable = true)
    private BankTransactionCode bankTransactionCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "trans_sub_code_id", nullable = true)
    private BankTransactionSubCode bankTransactionSubCode;

    @Transient
    private Long transEventId;

    @Transient
    private Long transCodeId;

    @Transient
    private Long transSubCodeId;

/*	
	@Column(name="trans_event_id")
	private long transEventId; */

    @Transient
    private String transEventCode;

    @Transient
    private String transCode;

    @Transient
    private String transSubCode;

    @Transient
    private String transSubCodeDescription;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "curr_conversion_rate_type", length = 50, nullable = false)
    private CurrConversionRateType currConversionRateType;

    @Column(name = "status")
    private String status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Date modifiedDate;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public TransactionEvent getTransactionEvent() {
        return transactionEvent;
    }

    public void setTransactionEvent(TransactionEvent transactionEvent) {
        this.transactionEvent = transactionEvent;
    }

    public BankTransactionCode getBankTransactionCode() {
        return bankTransactionCode;
    }

    public void setBankTransactionCode(BankTransactionCode bankTransactionCode) {
        this.bankTransactionCode = bankTransactionCode;
    }

    public BankTransactionSubCode getBankTransactionSubCode() {
        return bankTransactionSubCode;
    }

    public void setBankTransactionSubCode(BankTransactionSubCode bankTransactionSubCode) {
        this.bankTransactionSubCode = bankTransactionSubCode;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CurrConversionRateType getCurrConversionRateType() {
        return currConversionRateType;
    }

    public void setCurrConversionRateType(CurrConversionRateType currConversionRateType) {
        this.currConversionRateType = currConversionRateType;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getTransEventId() {
        if (transactionEvent != null)
            return transactionEvent.getId();
        else
            return null;
    }

    public Long getTransCodeId() {
        if (bankTransactionCode != null)
            return bankTransactionCode.getId();
        else
            return null;
    }

    public Long getTransSubCodeId() {
        if (bankTransactionSubCode != null)
            return bankTransactionSubCode.getId();
        else
            return null;
    }

    public String getTransEventCode() {
        if (transactionEvent != null)
            return transactionEvent.getCode();
        else
            return null;
    }

    public void setTransEventCode(String transEventCode) {
        this.transEventCode = transEventCode;
    }

    public String getTransCode() {
        if (bankTransactionCode != null)
            return bankTransactionCode.getCode();
        else
            return null;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransSubCode() {
        if (bankTransactionSubCode != null)
            return bankTransactionSubCode.getSubCode();
        else
            return null;
    }

    public void setTransSubCode(String transSubCode) {
        this.transSubCode = transSubCode;
    }

    public String getTransSubCodeDescription() {
        if (bankTransactionSubCode != null)
            return bankTransactionSubCode.getDescription();
        else
            return null;
    }

    public void setTransSubCodeDescription(String transSubCodeDescription) {
        this.transSubCodeDescription = transSubCodeDescription;
    }


}
