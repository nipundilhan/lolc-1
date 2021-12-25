package com.fusionx.lending.transaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.transaction.core.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Table(name = "trans_event_acc_status")
@Data
public class TransEventAccStatus extends BaseEntity {


    @Column(name = "tenant_id")
    private String tenantId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "trans_event_id", nullable = true)
    private TransactionEvent transactionEvent;

    @Column(name = "trans_event_code")
    private String transEventCode;

    @Column(name = "acc_status")
    private String accStatus;

    @Column(name = "acc_status_desc")
    private String accStatusDesc;

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

    @Transient
    private Long tranEventId;

    public Long getTranEventId() {
        return transactionEvent != null ? transactionEvent.getId() : tranEventId;
    }

    public void setTranEventId(Long tranEventId) {
        this.tranEventId = tranEventId;
    }

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


}
