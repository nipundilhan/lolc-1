package com.fusionx.lending.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.PostingTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Lending Account Detail
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-10-2021      		                Thushan      Created
 *
 ********************************************************************************************************
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lending_transaction")
public class LendingTransaction extends BaseEntity {

    private static final long serialVersionUID = 65364566L;

    @Column(name = "transaction_date",  nullable = false)
    private Timestamp transactionDate;

    @Column(name = "value_date",  nullable = false)
    private Timestamp valueDate;

    @Column(name = "transaction_sub_code", length = 20, nullable = false)
    private String transactionSubCode;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "posting_type", length = 20)
    private PostingTypeEnum postingType;

    @Column(name = "amount", columnDefinition = "Decimal(38,2)", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency_code", length = 20)
    private String currencyCode;

    @Column(name = "currency_code_numeric", length = 3)
    private String currencyCodeNumeric;

    @Column(name = "sequence_order", length = 4)
    private Long sequenceOrder;

    @Column(name = "reference_id", length = 4)
    private Long referenceId;

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private CommonStatus status;

    @Column(name = "created_user", nullable=false)
    private String createdUser;

    @Column(name = "created_date", nullable=false)
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @Column(name = "currency_id", nullable = false)
    private Long currencyId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "lending_account_id", nullable = false)
    private LendingAccountDetail lendingAccountId;

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Timestamp getValueDate() {
        return valueDate;
    }

    public void setValueDate(Timestamp valueDate) {
        this.valueDate = valueDate;
    }

    public String getTransactionSubCode() {
        return transactionSubCode;
    }

    public void setTransactionSubCode(String transactionSubCode) {
        this.transactionSubCode = transactionSubCode;
    }

    public PostingTypeEnum getPostingType() {
        return postingType;
    }

    public void setPostingType(PostingTypeEnum postingType) {
        this.postingType = postingType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCodeNumeric() {
        return currencyCodeNumeric;
    }

    public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
        this.currencyCodeNumeric = currencyCodeNumeric;
    }

    public Long getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(Long sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public LendingAccountDetail getLendingAccountId() {
        return lendingAccountId;
    }

    public void setLendingAccountId(LendingAccountDetail lendingAccountId) {
        this.lendingAccountId = lendingAccountId;
    }
}
